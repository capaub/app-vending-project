<?php

namespace App\Service;


use App\Document\Vending;
use Doctrine\ODM\MongoDB\Aggregation\Aggregation;
use Doctrine\ODM\MongoDB\DocumentManager;
use Doctrine\ODM\MongoDB\MongoDBException;
use MongoDB\BSON\ObjectId;
use Throwable;

class VendingService
{
    private DocumentManager $documentManager;

    /**
     * @param DocumentManager $documentManager*
     */
    public function __construct(DocumentManager $documentManager)
    {
        $this->documentManager = $documentManager;
    }

    /**
     * @param string $sTray
     * @param string $sSpiral
     * @param string $sVendingId
     * @param int $iBatchId
     * @param int $iQuantity
     * @return void
     * @throws MongoDBException
     * @throws Throwable
     */
    public function updateSpiral(string $sTray, string $sSpiral, string $sVendingId, int $iBatchId, int $iQuantity): void
    {
        try {
            $vending = $this->documentManager->getRepository(Vending::class)->findOneBy(['_id' => new ObjectId($sVendingId)]);

            if (!$vending) {
                throw new \Exception("Vending machine not found.");
            }

            foreach ($vending->getLocations() as $location) {
                if ($location->getTray() === $sTray) {
                    foreach ($location->getSpirals() as $spiralItem) {
                        if ($spiralItem->getSpiral() === $sSpiral) {
                            $spiralItem->setQuantity($iQuantity);
                            $spiralItem->setBatchId($iBatchId);
                            error_log("Updated spiral with quantity: $iQuantity and batchId: $iBatchId");
                        }
                    }
                }
            }

            $this->documentManager->persist($vending);
            $this->documentManager->flush();
            error_log("Persisted changes to MongoDB successfully.");

        } catch (MongoDBException $e) {
            error_log("MongoDB Exception: " . $e->getMessage());
        } catch (Throwable $e) {
            error_log("General Exception: " . $e->getMessage());
        }
    }

    /**
     * @param string $sVendingId
     * @return void
     * @throws MongoDBException
     */
    public function cancelLastUpdatedSpiral(string $sVendingId): void
    {
        $vending = $this->documentManager->getRepository(Vending::class)
            ->findOneBy(['_id' => new ObjectId($sVendingId)]);

        if ($vending) {
            $this->documentManager->createQueryBuilder(Vending::class)
                ->findAndUpdate()
                ->field('_id')->equals(new ObjectId($sVendingId))
                ->field('location.spirals')->popLast()
                ->getQuery()
                ->execute();
        }
    }

    /**
     * @param int $iCompanyId
     * @return Aggregation
     */
    public function getTotalExternalQuantityByProducts(int $iCompanyId): Aggregation
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

        return $aggregation->getAggregation();
    }
}