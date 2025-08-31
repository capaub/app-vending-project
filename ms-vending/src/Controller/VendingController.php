<?php

namespace App\Controller;

use App\Document\Vending;
use App\Document\VendingLocation;
use App\Document\VendingStock;
use App\Service\Persistence\MongoDBVendingService;
use App\Service\VendingPerCustomerService;
use App\Service\VendingService;
use App\Service\VendingStockService;

use Doctrine\ODM\MongoDB\MongoDBException;
use Exception;
use stdClass;
use Doctrine\Common\Collections\ArrayCollection;

use MongoDB\BSON\ObjectId;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Serializer\SerializerInterface;

use Symfony\Contracts\HttpClient\Exception\ClientExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\DecodingExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\RedirectionExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\ServerExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\TransportExceptionInterface;
use Throwable;

class VendingController
{
    private MongoDBVendingService $mongoDBVendingService;
    private SerializerInterface $serializer;
    private VendingStockService $vendingStockService;
    private VendingPerCustomerService $vendingPerCustomerService;
    private VendingService $vendingService;

    /**
     * @param MongoDBVendingService $mongoDBVendingService
     * @param SerializerInterface $serializer
     * @param VendingStockService $vendingStockService
     * @param VendingPerCustomerService $vendingPerCustomerService
     * @param VendingService $vendingService
     */
    public function __construct(MongoDBVendingService     $mongoDBVendingService,
                                SerializerInterface       $serializer,
                                VendingStockService       $vendingStockService,
                                VendingPerCustomerService $vendingPerCustomerService,
                                VendingService            $vendingService)
    {
        $this->mongoDBVendingService = $mongoDBVendingService;
        $this->serializer = $serializer;
        $this->vendingStockService = $vendingStockService;
        $this->vendingPerCustomerService = $vendingPerCustomerService;
        $this->vendingService = $vendingService;
    }

    /**
     * @param Request $request
     * @return JsonResponse
     * @throws Exception
     */
    #[Route('/php/mongoDB/createVending', methods: ['POST'])]
    public function createVending(Request $request): JsonResponse
    {
        $data = json_decode($request->getContent(), true);

        if (empty($data['brand'])
            || empty($data['model'])
            || !isset($data['nbMaxTray'])
            || !isset($data['nbMaxSpiral'])
            || !isset($data['companyId'])
        ) {
            return new JsonResponse(['status' => 'error', 'message' => 'Données manquantes'], 400);
        }
        try {
            $oVending = new Vending(
                $data['brand'],
                $data['model'],
                null,
                intval($data['nbMaxTray']),
                intval($data['nbMaxSpiral']),
                intval($data['companyId']),
            );
            $aLocations = new ArrayCollection();
            for ($iTrayIndex = 0; $iTrayIndex < $data['nbMaxTray']; $iTrayIndex++) {
                $oVendingLocation = new VendingLocation(chr(65 + $iTrayIndex));
                $aSpirals = new ArrayCollection();
                for ($iSpiralIndex = 1; $iSpiralIndex <= $data['nbMaxSpiral']; $iSpiralIndex++) {
                    $oVendingStock = new VendingStock(chr(65 + $iTrayIndex) . $iSpiralIndex, null, null);
                    $aSpirals->add($oVendingStock);
                }
                $oVendingLocation->setSpirals($aSpirals);
                $aLocations->add($oVendingLocation);
            }
            $oVending->setLocations($aLocations);
            $this->mongoDBVendingService->saveVending($oVending);

            $aVending = $this->mongoDBVendingService->findAllByCompany($data['companyId']);

            $vendingJson = $this->serializer->serialize($aVending, 'json');

            return new JsonResponse($vendingJson, 200, [], true);
        } catch (Exception $e) {
            return new JsonResponse(['status' => 'error', 'message' => $e->getMessage()], 500);
        }
    }

    /**
     * @param int $iCompanyId
     * @return JsonResponse
     */
    #[Route('/php/mongoDB/getAllVending/{iCompanyId}', methods: ['GET'])]
    public function getAllVending(int $iCompanyId): JsonResponse
    {
        $aVendingList = $this->mongoDBVendingService->findAllByCompany($iCompanyId);
        $vendingJson = $this->serializer->serialize($aVendingList ?: [], 'json');

        return new JsonResponse($vendingJson, 200, [], true);
    }

    /**
     * @param string $iVendingId
     * @return JsonResponse
     */
    #[Route('/php/buildVending/{iVendingId}', methods: ['GET'])]
    public function buildVending(string $iVendingId): JsonResponse
    {
        try {
            if ($this->mongoDBVendingService->findOneBy(["_id" => new ObjectId($iVendingId)])) {
                $oVending = $this->mongoDBVendingService->findOneBy(["_id" => new ObjectId($iVendingId)]);

                $sVendingTags = $oVending->getName();

                $aDataVendingStock = self::getVendingData($iVendingId)->getContent();
                $aJsonDataVendingStock = json_decode($aDataVendingStock, true);

                $vendingJson = [
                    'vendingTags' => $sVendingTags,
                    'dataVendingStock' => !empty($aJsonDataVendingStock) ? $aJsonDataVendingStock : new stdClass(),
                    'vendingId' => $oVending->getVendingId(),
                    'nbVendingTray' => $oVending->getNbMaxTray(),
                    'nbVendingSpiral' => $oVending->getNbMaxSpiral()
                ];

                return new JsonResponse($vendingJson, 200);
            }

            return new JsonResponse(['status' => 'error build vending'], 400);
        } catch (\Exception $e) {
            return new JsonResponse(['status' => 'error', 'la' => $e->getMessage()], 500);
        }
    }

    /**
     * @param string $iVendingId
     * @return array
     * @throws ClientExceptionInterface
     * @throws DecodingExceptionInterface
     * @throws RedirectionExceptionInterface
     * @throws ServerExceptionInterface
     * @throws TransportExceptionInterface
     */
    #[Route('/testVending/{iVendingId}', methods: ['GET'])]
    public function getVendingData(string $iVendingId): JsonResponse
    {
        $vendingDocument = $this->mongoDBVendingService->findOneBy(['_id' => new ObjectId($iVendingId)]);

        if (!$vendingDocument) {
            return [];
        }

        $dataVendingStock = [];

        foreach ($vendingDocument->getLocations() as $location) {
            foreach ($location->getSpirals() as $spiral) {
                if ($spiral->getBatchId()) {
                    $batchResponse = $this->vendingStockService->getBatchFromApi($spiral->getBatchId());
                    $batchContent = json_decode($batchResponse->getContent(), true);
                    $batch = $batchContent['batch'];

                    $goodsResponse = $this->vendingStockService->getGoodsFromApi($batch['goodsId']);
                    $goodsContent = json_decode($goodsResponse->getContent(), true);
                    $goods = $goodsContent['goods'];

                    if (is_array($goods) && !empty($goods)) {
                        $dataVendingStock[$spiral->getSpiral()] = [
                            'batchId' => $batch['id'],
                            'dlc' => $batch['dlc'],
                            'qrCodePath' => $batch['qrCodePath'],
                            'quantity' => $spiral->getQuantity(),
                            'barcode' => $goods['barcode'],
                            'brand' => $goods['brand'],
                            'imgUrl' => $goods['imgUrl']
                        ];
                    }
                }
            }
        }

        return new JsonResponse($this->serializer->normalize($dataVendingStock,'json'), 200);
    }

    /**
     * @param int $iCompanyId
     * @return JsonResponse
     */
    #[Route('/php/mongoDB/getAvailableVending/{iCompanyId}', methods: ['GET'])]
    public function getAvailableVending(int $iCompanyId): JsonResponse
    {
        $aVendingIds = $this->mongoDBVendingService->getVendingIds($iCompanyId);
        $aAvailableVendingIds = $this->vendingPerCustomerService->getAvailableVending($aVendingIds);

        $aAvailableVendingObjectIds = array_map(function ($id) {
            return new ObjectId($id);
        }, $aAvailableVendingIds);

        $aAvailableVending = $this->serializer->serialize(
            $this->mongoDBVendingService->findBy(['_id' => ['$in' => $aAvailableVendingObjectIds]])
            , 'json');

        return new JsonResponse($aAvailableVending, 200, [], true);
    }

    /**
     * @param string $sLocation
     * @param string $sVendingId
     * @param int $iBatchId
     * @param int $iQuantity
     * @return JsonResponse
     * @throws MongoDBException
     * @throws Throwable
     */
    #[Route('/php/batch/addToVending/{sLocation}/{sVendingId}/{iBatchId}/{iQuantity}', methods: ['POST'])]
    public function addStockToVending(string $sLocation, string $sVendingId, int $iBatchId, int $iQuantity): JsonResponse
    {
        $sTray = substr($sLocation, 0, 1);

        $this->vendingService->updateSpiral($sTray, $sLocation, $sVendingId, $iBatchId, $iQuantity);
        return new JsonResponse( "status:ok", 200, [], true);
    }

    /**
     * @param string $sVendingId
     * @return JsonResponse
     * @throws MongoDBException
     */
    #[Route('/php/batch/removeLastUpdatedStock/{sVendingId}', methods: ['POST'])]
    public function removeLastStock(string $sVendingId): JsonResponse
    {
        $this->vendingService->cancelLastUpdatedSpiral($sVendingId);
        return new JsonResponse( "status:ok", 200, [], true);
    }

    /**
     * @param int $iCompanyId
     * @return JsonResponse
     */
    #[Route('/php/mongo/chart/extTotalProduct/{iCompanyId}', methods: ['GET'])]
    public function getExtTotalProduct(int $iCompanyId): JsonResponse
    {
        $aResult = $this->vendingStockService->getTotalExternalQuantityByProducts($iCompanyId);
        $response = [];
        foreach ($aResult as $item) {
            $response[$item['_id']] = $item['total_quantity'];
        }

        return new JsonResponse($response,200);
    }

    /**
     * @param string $sVendingId
     * @return JsonResponse
     * @throws Exception
     */
    #[Route('/php/mongo/chart/vendingStock/{sVendingId}', methods: ['GET'])]
    public function getVendingStockChart(string $sVendingId): JsonResponse
    {
        $aResult = $this->vendingStockService->getTotalStockByProductForVending($sVendingId);

        return new JsonResponse($aResult,200);
    }
}