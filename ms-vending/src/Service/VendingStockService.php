<?php

namespace App\Service;

use App\Document\Vending;
use App\Manager\EurekaClient;
use Doctrine\ODM\MongoDB\DocumentManager;
use Exception;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Contracts\HttpClient\Exception\ClientExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\DecodingExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\RedirectionExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\ServerExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\TransportExceptionInterface;
use Symfony\Contracts\HttpClient\HttpClientInterface;

class VendingStockService
{
    private EurekaClient $eurekaClient;
    private HttpClientInterface $httpClient;
    private DocumentManager $documentManager;

    public function __construct(DocumentManager $documentManager,EurekaClient $eurekaClient,
                                HttpClientInterface $httpClient)
    {
        $this->documentManager = $documentManager;
        $this->eurekaClient = $eurekaClient;
        $this->httpClient = $httpClient;
    }

    /**
     * @param int $iCompanyId
     * @return array
     */
    public function getTotalExternalQuantityByProducts(int $iCompanyId): array
    {
        $aggregation = $this->documentManager->createAggregationBuilder(Vending::class);

        $aggregation
            ->match()
            ->field('companyId')->equals($iCompanyId)
            ->unwind('$locations')
            ->unwind('$locations.spirals')
            ->match()
            ->field('locations.spirals.batchId')->notEqual(null)
            ->group()
            ->field('_id')->expression('$locations.spirals.batchId')
            ->field('total_quantity')->sum('$locations.spirals.quantity');

        $result = $aggregation->execute()->toArray();

        $response = [];
        foreach ($result as $item) {
            $batchResponse = $this->getBatchFromApi($item['_id']);
            $batchContent = json_decode($batchResponse->getContent(), true);

            if (!isset($batchContent['batch'])) {
                continue;
            }

            $batch = $batchContent['batch'];
            $goodsResponse = $this->getGoodsFromApi($batch['goodsId']);
            $goodsContent = json_decode($goodsResponse->getContent(), true);

            if (!isset($goodsContent['goods'])) {
                continue;
            }

            $goods = $goodsContent['goods'];
            $brand = $goods['brand'];
            $total_quantity = $item['total_quantity'];

            if (isset($response[$brand])) {
                $response[$brand]['total_quantity'] += $total_quantity;
            } else {
                $response[$brand] = [
                    'brand' => $brand,
                    'total_quantity' => $total_quantity
                ];
            }
        }

        return array_values($response);
    }

    /**
     * @param string $vendingId
     * @return array
     * @throws Exception
     */
    public function getTotalStockByProductForVending(string $vendingId): array
    {
        $aggregation = $this->documentManager->createAggregationBuilder(Vending::class);

        $aggregation
            ->match()
            ->field('_id')->equals($vendingId)
            ->unwind('$locations')
            ->unwind('$locations.spirals')
            ->match()
            ->field('locations.spirals.batchId')->notEqual(null)
            ->group()
            ->field('_id')->expression('$locations.spirals.batchId')
            ->field('total_quantity')->sum('$locations.spirals.quantity');

        $result = $aggregation->execute()->toArray();

        $response = [];
        foreach ($result as $item) {
            $batchResponse = $this->getBatchFromApi($item['_id']);
            $batchContent = json_decode($batchResponse->getContent(), true);

            if (!isset($batchContent['batch'])) {
                continue;
            }

            $batch = $batchContent['batch'];
            $goodsResponse = $this->getGoodsFromApi($batch['goodsId']);
            $goodsContent = json_decode($goodsResponse->getContent(), true);

            if (!isset($goodsContent['goods'])) {
                continue;
            }

            $goods = $goodsContent['goods'];
            $brand = $goods['brand'];
            $total_quantity = $item['total_quantity'];

            if (isset($response[$brand])) {
                $response[$brand]['total_quantity'] += $total_quantity;
            } else {
                $response[$brand] = [
                    'brand' => $brand,
                    'total_quantity' => $total_quantity
                ];
            }
        }

        return array_values($response);
    }

    /**
     * @param int $iGoodsId
     * @return JsonResponse
     * @throws ClientExceptionInterface
     * @throws DecodingExceptionInterface
     * @throws RedirectionExceptionInterface
     * @throws ServerExceptionInterface
     * @throws TransportExceptionInterface
     */
    public function getGoodsFromApi(int $iGoodsId): JsonResponse
    {
        $msProduct = $this->eurekaClient->getServiceUrl('MS-PRODUCT');
        if (!$msProduct) {
            return new JsonResponse(['URL du service MS-PRODUCT' => $msProduct, 400]);
        }

        $url = "$msProduct/api/goods/findById/{$iGoodsId}";

        try {
            $response = $this->httpClient->request('GET', $url);

            if ($response->getStatusCode() === 200) {
                return new JsonResponse([
                    'status' => 'success get goods',
                    'goods' => $response->toArray()
                ]);

            }

            return new JsonResponse(['status' => 'error: can\'t get goods', 400]);
        } catch (Exception $e) {
            return new JsonResponse([
                'status' => 'error',
                'message' => 'Erreur lors de la requête vers ' . $url . ': ' . $e->getMessage()
            ], 400);
        }
    }

    /**
     * @param int $batchId
     * @return JsonResponse
     * @throws ClientExceptionInterface
     * @throws DecodingExceptionInterface
     * @throws RedirectionExceptionInterface
     * @throws ServerExceptionInterface
     * @throws TransportExceptionInterface
     */
    public function getBatchFromApi(int $batchId): JsonResponse
    {
        $msProduct = $this->eurekaClient->getServiceUrl('MS-PRODUCT');
        if (!$msProduct) {
            return new JsonResponse(['URL du service MS-PRODUCT' => $msProduct, 400]);
        }

        $url = "$msProduct/api/batches/get/$batchId";
        try {
            $response = $this->httpClient->request('GET', $url);

            if ($response->getStatusCode() === 200) {
                return new JsonResponse([
                    'status' => 'success get batch',
                    'batch' => $response->toArray()
                ]);

            }

            return new JsonResponse(['status' => 'error: can\'t get batch', 400]);
        } catch (Exception $e) {
            return new JsonResponse([
                'status' => 'error',
                'message' => 'Erreur lors de la requête vers ' . $url . ': ' . $e->getMessage()
            ], 400);
        }
    }
}