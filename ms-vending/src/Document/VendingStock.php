<?php

namespace App\Document;

use Doctrine\ODM\MongoDB\Mapping\Annotations as ODM;
use Symfony\Component\Validator\Constraints as Assert;

#[ODM\EmbeddedDocument]
#[ODM\Index(keys: ['batchId' => 'asc'])]
class VendingStock
{
    use Timestampable;

    #[ODM\Field(type: 'string')]
    #[Assert\Length(
        max: 3,
        maxMessage: 'Longueur max autorisée : {{ limit }} caractères.'
    )]
    private string $spiral;

    #[ODM\Field(type: 'integer', nullable: true)]
    private ?int $quantity;

    #[ODM\Field(type: 'integer', nullable: true)]
    private ?int $batchId;

    /**
     * @param string $spiral
     * @param int|null $quantity
     * @param int|null $batchId
     */
    public function __construct(string $spiral, ?int $quantity = null, ?int $batchId = null)
    {
        $this->spiral = $spiral;
        $this->quantity = $quantity;
        $this->batchId = $batchId;
    }

    public function getSpiral(): string
    {
        return $this->spiral;
    }

    public function setSpiral(string $spiral): void
    {
        $this->spiral = $spiral;
    }

    public function getQuantity(): ?int
    {
        return $this->quantity;
    }

    public function setQuantity(?int $quantity): void
    {
        $this->quantity = $quantity;
    }

    public function getBatchId(): ?int
    {
        return $this->batchId;
    }

    public function setBatchId(?int $batchId): void
    {
        $this->batchId = $batchId;
    }
}