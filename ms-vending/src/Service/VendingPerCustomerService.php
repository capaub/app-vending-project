<?php

namespace App\Service;

use App\Entity\VendingPerCustomer;
use App\Repository\MySQL\VendingPerCustomerRepository;
use App\Service\Persistence\MongoDBVendingService;
use Doctrine\ORM\EntityManagerInterface;
use Exception;

class VendingPerCustomerService
{
    private EntityManagerInterface $entityManager;
    private VendingPerCustomerRepository $vendingPerCustomerRepository;
    private MongoDBVendingService  $mongoDBVendingService;

    /**
     * @param EntityManagerInterface $entityManager
     * @param VendingPerCustomerRepository $vendingLocationRepository
     * @param MongoDBVendingService $mongoDBVendingService
     */
    public function __construct(EntityManagerInterface $entityManager,
                                VendingPerCustomerRepository $vendingLocationRepository,
                                MongoDBVendingService $mongoDBVendingService
    ) {
        $this->entityManager = $entityManager;
        $this->vendingPerCustomerRepository = $vendingLocationRepository;
        $this->mongoDBVendingService = $mongoDBVendingService;
    }

    /**
     * @param VendingPerCustomer $oVendingPerCustomer
     * @return VendingPerCustomer
     */
    protected function assignVending(VendingPerCustomer $oVendingPerCustomer): VendingPerCustomer
    {
        $oVendingPerCustomer->setStatus(VendingPerCustomer::ASSIGNED);
        $this->entityManager->persist($oVendingPerCustomer);
        $this->entityManager->flush();
        return $oVendingPerCustomer;
    }

    /**
     * @param string $sVendingName
     * @param int $iCustomerId
     * @param string $iVendingId
     * @return VendingPerCustomer
     * @throws Exception
     */
    public function saveVendingPerCustomer(string $iVendingId, int $iCustomerId, string $sVendingName): VendingPerCustomer
    {

        $oVending = $this->mongoDBVendingService->updateVendingName($iVendingId, $sVendingName);

        $oVendingPerCustomer = new VendingPerCustomer($oVending->getVendingId(), $iCustomerId);
        return $this->assignVending($oVendingPerCustomer);

    }

    /**
     * @param int $iVendingPerCustomerId
     * @return VendingPerCustomer
     */
    public function find(int $iVendingPerCustomerId): VendingPerCustomer
    {
        return $this->vendingPerCustomerRepository->find($iVendingPerCustomerId);
    }

    /**
     * @param int $iCustomerId
     * @return array
     */
    public function findAllVending(int $iCustomerId): array
    {
        return $this->vendingPerCustomerRepository->findByCustomer($iCustomerId);
    }

    /**
     * @param array $aVendingIds
     * @return array
     */
    public function getAvailableVending(array $aVendingIds): array
    {
        return $this->vendingPerCustomerRepository->findAvailableVending($aVendingIds);
    }
}