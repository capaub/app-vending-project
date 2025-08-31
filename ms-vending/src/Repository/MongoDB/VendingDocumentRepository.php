<?php

namespace App\Repository\MongoDB;

use App\Document\Vending;
use Doctrine\Bundle\MongoDBBundle\Repository\ServiceDocumentRepository;
use Doctrine\Persistence\ManagerRegistry;

class VendingDocumentRepository extends ServiceDocumentRepository
{
    /**
     * @param ManagerRegistry $registry
     */
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Vending::class);
    }

}