<?php

namespace App\Repository\MySQL;

use App\Entity\VendingPerCustomer;
use Doctrine\DBAL\ArrayParameterType;
use Doctrine\ORM\QueryBuilder;
use Doctrine\Persistence\ManagerRegistry;

class VendingPerCustomerRepository extends AbstractRepository
{
    const TABLE = '`vending_per_customer`';
    const ORDERED_BY = '`status`';

    /**
     * @param ManagerRegistry $registry
     */
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, VendingPerCustomer::class);
    }

    /**
     * @param QueryBuilder $oQB
     * @param array $aCriterias
     * @return QueryBuilder
     */
    public function buildCriterias(QueryBuilder $oQB, array $aCriterias): QueryBuilder
    {
        if (!empty($aCriterias['magic-search'])) {
            $oQB->andWhere(
                $oQB->expr()->orX(
                    $oQB->expr()->like('e.id', ':magicSearch'),
                    $oQB->expr()->like('e.location', ':magicSearch'),
                    $oQB->expr()->like('e.vending', ':magicSearch')
                )
            )
                ->setParameter('magicSearch', '%' . $aCriterias['magic-search'] . '%');
        }

        if (!empty($aCriterias['status'])) {
            $oQB->andWhere('e.status = :status')
                ->setParameter('status', $aCriterias['status']);
        }
        if (!empty($aCriterias['customer_id'])) {
            $oQB->andWhere('e.customer_id = :customer_id')
                ->setParameter('customer_id', $aCriterias['customer_id']);
        }
        if (!empty($aCriterias['install_date'])) {
            $oQB->andWhere('e.install_date = :installDate')
                ->setParameter('installDate', $aCriterias['install_date']);
        }

        if ((!empty($aCriterias['from']))) {
            $oQB->andWhere('e.created_at >= :from')
                ->setParameter('from', $aCriterias['from']);
        }
        if ((!empty($aCriterias['to']))) {
            $oQB->andWhere('e.created_at <= :to')
                ->setParameter('to', $aCriterias['to']);
        }

        return $oQB;
    }

    /**
     * @param int $iCustomerId
     * @return array
     */
    public function findByCustomer(int $iCustomerId): array
    {
        $oQB = $this->createQueryBuilder('e');

        $oQB->where('e.customerId = :customerId')
            ->setParameter('customerId', $iCustomerId);

        return $oQB->getQuery()->getResult();
    }

    /**
     * @param array $aVendingIds
     * @return array
     */
    public function findAvailableVending(array $aVendingIds): array
    {
        $oQB = $this->createQueryBuilder('vpc');
        $oQB->select('vpc.vendingId')
            ->where('vpc.status = 1')
            ->andWhere('vpc.vendingId IN (:vendingIds)')
            ->setParameter('vendingIds', $aVendingIds, ArrayParameterType::STRING);
        $result = $oQB->getQuery()->getScalarResult();

        $idsToExclude = array_column($result, 'vendingId');
        $availableVendingIds = array_diff($aVendingIds, $idsToExclude);

        return array_values($availableVendingIds);
    }
}