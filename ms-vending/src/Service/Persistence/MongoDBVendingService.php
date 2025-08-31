<?php

namespace App\Service\Persistence;

use App\Document\Vending;
use Doctrine\ODM\MongoDB\DocumentManager;
use Doctrine\ODM\MongoDB\LockException;
use Doctrine\ODM\MongoDB\Mapping\MappingException;
use Exception;
use MongoDB\BSON\ObjectId;
use Throwable;

class MongoDBVendingService
{
    private DocumentManager $documentManager;

    /**
     * @param DocumentManager $documentManager
     */
    public function __construct(DocumentManager $documentManager)
    {
        $this->documentManager = $documentManager;
    }

    /**
     * @param Vending $oVending
     * @return Vending
     * @throws Exception
     */
    public function saveVending(Vending $oVending): Vending
    {
        try {
            $this->documentManager->persist($oVending);
            $this->documentManager->flush();
            return $oVending;
        }  catch (Throwable $e) {
            throw new Exception("Could not save entity: " . $e->getMessage());
        }
    }

    /**
     * @param string $iVendingId
     * @param string $sVendingName
     * @return Vending
     * @throws LockException
     * @throws MappingException
     * @throws Exception
     */
    public function updateVendingName(string $iVendingId, string $sVendingName): Vending
    {
        $oVending = $this->documentManager
            ->getRepository(Vending::class)->find(new ObjectId($iVendingId));

        $oVending->setName($sVendingName);

        try {
            $this->documentManager->persist($oVending);
            $this->documentManager->flush();
            return $oVending;
        }  catch (Throwable $e) {
            throw new Exception("Could not save entity: " . $e->getMessage());
        }
    }

    /**
     * @param array $criteria
     * @return Vending|null
     */
    public function findOneBy(array $criteria): ?Vending
    {
        return $this->documentManager->getRepository(Vending::class)->findOneBy($criteria);
    }

    /**
     * @param int $iCompanyId
     * @return array
     */
    public function findAllByCompany(int $iCompanyId): array
    {
        return $this->documentManager->getRepository(Vending::class)->findBy(['companyId' => $iCompanyId]);
    }

    /**
     * @param array $aCriteria
     * @return array
     */
    public function findBy(array $aCriteria): array
    {
        return $this->documentManager->getRepository(Vending::class)->findBy($aCriteria);
    }

 //  /**
 //   * @param $iCleanVendingId
 //   * @return array
 //   * @throws Exception
 //   */
 //  private static function getVendingData($iCleanVendingId): array
 //  {
 //      $aVendingLocation = VendingLocationDocumentRepository::findAllForOne($iCleanVendingId);
 //      $aDataVendingLocation = [];
 //      foreach ($aVendingLocation as $oVendingLocation) {
 //          $aDataVendingLocation[$oVendingLocation->getLocation()] = $oVendingLocation->getId();
 //      }
 //      $aDataVendingStock = [];
 //      foreach ($aDataVendingLocation as $sLocation => $iVendingLocationId) {
 //          $aVendingStockCriterias = ['vending_location_id' => $iVendingLocationId];
 //          $aVendingStock = VendingStockRepository::findBy($aVendingStockCriterias);
 //          foreach ($aVendingStock as $oVendingStock) {
 //              $oBatch = self::getBatchFromApi($oVendingStock->getBatchId());
 //              if ($oBatch) {
 //                  $oGoods = self::getGoodsFromApi($oBatch['goods_id']);
 //              }
 //              if ($oGoods) {
 //                  $aLocationInfo = [
 //                      'batch_id' => $oBatch['id'],
 //                      'dlc' => $oBatch['dlc'],
 //                      'qr_code' => $oBatch['qr_code'],
 //                      'quantity' => $oVendingStock->getQuantity(),
 //                      'barcode' => $oGoods['barcode'],
 //                      'brand' => $oGoods['brand'],
 //                      'img' => $oGoods['img'],
 //                      'nutri-score' => $oGoods['nutri_score']
 //                  ];
 //                  $aDataVendingStock[$sLocation] = $aLocationInfo;
 //              }
 //          }
 //      }
 //      return $aDataVendingStock;
 //  }

    /**
     * @param int $iCompanyId
     * @return array
     */
    public function getVendingIds(int $iCompanyId): array
    {
        $aVendingList = $this->findAllByCompany($iCompanyId);
        $aVendingIds = [];
        foreach ($aVendingList as $oVending) {
            $aVendingIds[] = $oVending->getVendingId();
        }
        return $aVendingIds;
    }

}