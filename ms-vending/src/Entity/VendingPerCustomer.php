<?php

namespace App\Entity;

use App\Repository\MySQL\VendingPerCustomerRepository;
use DateTime;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: VendingPerCustomerRepository::class)]
#[ORM\Table(
    name: 'vending_per_customer',
    indexes: [new ORM\Index(name: 'fk_php_vending_per_customer_customer_id_idx', columns: ['customer_id'])],
    uniqueConstraints: [new ORM\UniqueConstraint(columns: ['vending_id', 'customer_id'])]
)]
#[ORM\HasLifecycleCallbacks]
class VendingPerCustomer
{

    #[ORM\Id]
    #[ORM\GeneratedValue(strategy: "IDENTITY")]
    #[ORM\Column(type: 'integer', unique: true, options: ['unsigned' => true])]
    private int $id;

    #[ORM\Column(type: 'string', length: 1)]
    private string $status;

    #[ORM\Column(name: 'install_date', type: 'datetime', options: ['default' => 'CURRENT_TIMESTAMP'])]
    private DateTime $installData;

    #[ORM\Column(name: 'removal_date', type: 'datetime', nullable: true)]
    private DateTime $removalData;

    #[ORM\Column(name: 'vending_id', type: 'string')]
    private String $vendingId;

    #[ORM\Column(name: 'customer_id', type: 'integer')]
    private int $customerId;

    const NOT_ASSIGNED = '0';
    const ASSIGNED = '1';

    // TODO gerer le status des machines pour pouvoir retirer une machine de chez un client

    const STATUS = [
        self::NOT_ASSIGNED => 'disponible',
        self::ASSIGNED => 'indisponible',
    ];

    /**
     * @param string $sVendingId
     * @param int $iCustomerId
     * @param string $sStatus
     */
    public function __construct(string $sVendingId,
                                int     $iCustomerId,
                                string  $sStatus = self::NOT_ASSIGNED)
    {
        $this->vendingId = $sVendingId;
        $this->customerId = $iCustomerId;
        $this->status = $sStatus;
        $this->installData = new DateTime('now');
    }

    public function getId(): int
    {
        return $this->id;
    }

    public function setId(int $id): void
    {
        $this->id = $id;
    }

    public function getStatus(): string
    {
        return $this->status;
    }

    public function setStatus(string $status): void
    {
        $this->status = $status;
    }

    public function getInstallData(): DateTime
    {
        return $this->installData;
    }

    public function setInstallData(DateTime $installData): void
    {
        $this->installData = $installData;
    }

    public function getRemovalData(): DateTime
    {
        return $this->removalData;
    }

    public function setRemovalData(DateTime $removalData): void
    {
        $this->removalData = $removalData;
    }

    public function getVendingId(): string
    {
        return $this->vendingId;
    }

    public function setVendingId(string $vendingId): void
    {
        $this->vendingId = $vendingId;
    }

    public function getCustomerId(): int
    {
        return $this->customerId;
    }

    public function setCustomerId(int $customerId): void
    {
        $this->customerId = $customerId;
    }

}
