<?php

namespace App\Controller;

use App\Service\Persistence\MongoDBVendingService;
use App\Service\VendingPerCustomerService;
use App\Service\VendingStockService;
use DateTime;
use stdClass;
use Exception;
use MongoDB\BSON\ObjectId;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Serializer\SerializerInterface;

class VendingPerCustomerController extends AbstractController
{

    private MongoDBVendingService $mongoDBVendingService;
    private VendingPerCustomerService $vendingPerCustomerService;
    private SerializerInterface $serializer;
    private const DLC_TIMEOUT_ALERT = '+3 weeks';
    private const QUANTITY_LIMIT_ALERT = 1;
    private VendingStockService $vendingStockService;

    /**
     * @param SerializerInterface $serializer
     * @param MongoDBVendingService $mongoDBVendingService
     * @param VendingPerCustomerService $vendingPerCustomerService
     * @param VendingStockService $vendingStockService
     */
    public function __construct(SerializerInterface       $serializer,
                                MongoDBVendingService     $mongoDBVendingService,
                                VendingPerCustomerService $vendingPerCustomerService,
                                VendingStockService       $vendingStockService)
    {
        $this->serializer = $serializer;
        $this->mongoDBVendingService = $mongoDBVendingService;
        $this->vendingPerCustomerService = $vendingPerCustomerService;
        $this->vendingStockService = $vendingStockService;
    }

    /**
     * @param Request $request
     * @return JsonResponse
     * @throws Exception
     */
    #[Route('/php/mongoDB/vendings-data/by-customers', methods: ['POST'])]
    public function fetchVendingInfoForCustomer(Request $request): JsonResponse
    {
        $data = json_decode($request->getContent(), true);

        $aVending = [];
        $aVendingAlert = [];
        $aCustomerAlert = [];

        if (is_array($data)) {
            foreach ($data as $responseKey => $customerId) {

                $vendingDocuments = json_decode($this->findAllVendingByCustomer($customerId)->getContent());
                $serializedVendingArray = [];

                if (!empty($vendingDocuments)) {
                    foreach ($vendingDocuments as $iVendingId => $oVending) {
                        $normalizedVending = $this->normalizeVending($oVending);
                        $serializedVendingArray[$iVendingId] = $this->serializer->normalize($normalizedVending);

                        foreach ($oVending->locations as $location) {
                            foreach ($location->spirals as $spiral) {
                                $batchId = $spiral->batchId;
                                $quantity = $spiral->quantity;

                                if ($batchId !== null) {
                                    $batchData = json_decode($this->vendingStockService->getBatchFromApi($batchId)->getContent(), true);

                                    if ($batchData) {
                                        $dlcDate = new DateTime($batchData['batch']['dlc']);
                                        $alertDate = (new DateTime())->modify(self::DLC_TIMEOUT_ALERT);

                                        if ($dlcDate < $alertDate) {
                                            $aVendingAlert[$iVendingId] = $aVendingAlert[$iVendingId] ?? '';
                                            $aVendingAlert[$iVendingId] .= empty($aVendingAlert[$iVendingId]) ? 'dlc' : ' dlc';
                                        }
                                    }
                                }

                                if ($quantity != null && $quantity < self::QUANTITY_LIMIT_ALERT) {
                                    $aVendingAlert[$iVendingId] = $aVendingAlert[$iVendingId] ?? '';
                                    $aVendingAlert[$iVendingId] .= empty($aVendingAlert[$iVendingId]) ? 'rupture' : ' rupture';
                                }
                            }
                        }

                        if (isset($aVendingAlert[$iVendingId])) {
                            $aCustomerAlert[$customerId] = 'alert';
                        }
                    }

                    $aVending[$customerId] = $serializedVendingArray;
                } else {
                    // Renvoie un objet vide `{}` pour les clients sans machines
                    $aVending[$customerId] = new stdClass();
                }
            }

            $responseData = [
                "vending" => !empty($aVending) ? $aVending : new stdClass(),
                "status" => !empty($aVendingAlert) ? $aVendingAlert : new stdClass(),
                "customersStatus" => !empty($aCustomerAlert) ? $aCustomerAlert : new stdClass()
            ];

            return new JsonResponse($responseData, 200);
        }

        return new JsonResponse(['status' => 'error', 'message' => 'Invalid data format'], 400);
    }

    /**
     * @param $oVending
     * @return mixed
     */
    private function normalizeVending($oVending): mixed
    {
        $oVendingArray = $this->serializer->normalize($oVending);

        // Convertir `locations` en tableau de listes
        if (isset($oVendingArray['locations'])) {
            $oVendingArray['locations'] = array_values($oVendingArray['locations']);

            foreach ($oVendingArray['locations'] as &$location) {
                if (isset($location['spirals'])) {
                    // Convertir `spirals` en tableau de listes
                    $location['spirals'] = array_values($location['spirals']);
                }
            }
        }

        return $oVendingArray;
    }

    /**
     * @param int $iCustomerId
     * @return JsonResponse
     */
    #[Route('/test/{iCustomerId}', methods: ['GET'])]
    public function findAllVendingByCustomer(int $iCustomerId): JsonResponse
    {
        $aVending = [];

        $aVendingPerCustomer = $this->vendingPerCustomerService->findAllVending($iCustomerId);

        foreach ($aVendingPerCustomer as $oVendingPerCustomer) {

            $iVendingId = $oVendingPerCustomer->getVendingId();

            $oVending = $this->mongoDBVendingService->findOneBy(["_id" => new ObjectId($iVendingId)]);

            $aVending[(string)$iVendingId] = $this->serializer->normalize($oVending);
        }
        return new JsonResponse(($aVending), 200);
    }

    /**
     * @param int $iCustomerId
     * @return JsonResponse
     */
    #[Route('/php/vendingListByCustomer/{iCustomerId}', methods: ['GET'])]
    public function getVendingsByCustomer(int $iCustomerId): JsonResponse
    {
        $aVending = [];

        $aVendingPerCustomer = $this->vendingPerCustomerService->findAllVending($iCustomerId);

        foreach ($aVendingPerCustomer as $oVendingPerCustomer) {

            $iVendingId = $oVendingPerCustomer->getVendingId();

            $sVendingName = $this->mongoDBVendingService->findOneBy(["_id" => new ObjectId($iVendingId)])->getName();

            $aVending[(string)$iVendingId] = $sVendingName;
        }
        return new JsonResponse(($aVending), 200);
    }

    /**
     * @param string $iVendingId
     * @param int $iCustomerId
     * @param string $sVendingName
     * @return JsonResponse
     * @throws Exception
     */
    #[Route('/php/addVendingToCustomer/{iVendingId}/{iCustomerId}/{sVendingName}', methods: ['POST'])]
    public function addVendingToCustomer(string $iVendingId, int $iCustomerId, string $sVendingName): JsonResponse
    {
        if ($iVendingId && $sVendingName != null) {
            $this->vendingPerCustomerService->saveVendingPerCustomer($iVendingId, $iCustomerId, $sVendingName);

            $requestData = json_encode([$iCustomerId]);
            $request = new Request([], [], [], [], [], [], $requestData);

            return $this->fetchVendingInfoForCustomer($request);
        }
        return new JsonResponse(['status' => 'error', 'message' => "?"], 500);
    }
}