<?php

namespace App\Document;

use Doctrine\ODM\MongoDB\Mapping\Annotations as ODM;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\Common\Collections\Collection;
use Doctrine\Common\Collections\ArrayCollection;

#[ODM\Document(collection: 'vending')]
class Vending
{
    use Timestampable;

    #[ODM\Id]
    private string $vendingId;

    #[ODM\Field(type: 'string')]
    #[Assert\Length(
        max: 50,
        maxMessage: 'Longueur max autorisée : {{ limit }} caractères.'
    )]
    private string $brand;

    #[ODM\Field(type: 'string')]
    #[Assert\Length(
        max: 50,
        maxMessage: 'Longueur max autorisée : {{ limit }} caractères.'
    )]
    private string $model;

    #[ODM\Field(type: 'string', nullable: true)]
    private ?string $name;

    #[ODM\Field(type: 'integer')]
    private int $nbMaxTray;

    #[ODM\Field(type: 'integer')]
    private int $nbMaxSpiral;

    #[ODM\Field(type: 'integer')]
    #[ODM\Index(keys: ['companyId' => 'asc'])]
    private int $companyId;

    #[ODM\EmbedMany(targetDocument: VendingLocation::class)]
    private Collection $locations;

    /**
     * @param string $brand
     * @param string $model
     * @param string|null $name
     * @param string $nbMaxTray
     * @param int $nbMaxSpiral
     * @param int $companyId
     */
    public function __construct(string $brand,
                                string $model,
                                ?string $name,
                                string $nbMaxTray,
                                int $nbMaxSpiral,
                                int $companyId)
    {
        $this->brand = $brand;
        $this->model = $model;
        $this->name = $name;
        $this->nbMaxTray = $nbMaxTray;
        $this->nbMaxSpiral = $nbMaxSpiral;
        $this->companyId = $companyId;
        $this->locations = new ArrayCollection();
    }

    public function getVendingId(): string
    {
        return $this->vendingId;
    }

    public function setVendingId(string $vendingId): void
    {
        $this->vendingId = $vendingId;
    }

    public function getBrand(): string
    {
        return $this->brand;
    }

    public function setBrand(string $brand): void
    {
        $this->brand = $brand;
    }

    public function getModel(): string
    {
        return $this->model;
    }

    public function setModel(string $model): void
    {
        $this->model = $model;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(?string $name): void
    {
        $this->name = $name;
    }

    public function getNbMaxTray(): int
    {
        return $this->nbMaxTray;
    }

    public function setNbMaxTray(int $nbMaxTray): void
    {
        $this->nbMaxTray = $nbMaxTray;
    }

    public function getNbMaxSpiral(): int
    {
        return $this->nbMaxSpiral;
    }

    public function setNbMaxSpiral(int $nbMaxSpiral): void
    {
        $this->nbMaxSpiral = $nbMaxSpiral;
    }

    public function getLocations(): Collection
    {
        return $this->locations;
    }

    public function setLocations(Collection $locations): void
    {
        $this->locations = $locations;
    }

    public function getCompanyId(): int
    {
        return $this->companyId;
    }

    public function setCompanyId(int $companyId): void
    {
        $this->companyId = $companyId;
    }
}