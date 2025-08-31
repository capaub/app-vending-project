<?php

namespace App\Repository\MySQL;


use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\QueryBuilder;
use Exception;

abstract class AbstractRepository extends ServiceEntityRepository
{
    const ORDERED_BY = '`created_at`';
    const NB_ELTS_PER_PAGE = 25;

    /**
     * @param QueryBuilder $oQB
     * @param array $aCriterias
     * @return QueryBuilder
     */
    abstract protected function buildCriterias(QueryBuilder $oQB,array $aCriterias): QueryBuilder;

    /**
     * @param array $aCriterias
     * @param int $iOffset
     * @param int $iNbElts
     * @return array
     * @throws Exception
     */
    public function findByCriterias(array $aCriterias, int $iOffset = 0, int $iNbElts = self::NB_ELTS_PER_PAGE): array
    {
        $oQB = $this->createQueryBuilder('e');

        $this->buildCriterias($oQB, $aCriterias);

        $oQB->setFirstResult($iOffset)
            ->setMaxResults($iNbElts);

        return $oQB->getQuery()->getResult();
    }
}