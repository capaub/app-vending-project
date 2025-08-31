<?php

namespace App\Document;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ODM\MongoDB\Mapping\Annotations as ODM;
use Symfony\Component\Validator\Constraints as Assert;

#[ODM\EmbeddedDocument]
class VendingLocation
{
    #[ODM\Field(type: 'string')]
    #[Assert\Length(
        max: 1,
        maxMessage: 'Longueur max autorisée : {{ limit }} caractères.'
    )]
    private string $tray;

    #[ODM\EmbedMany(targetDocument: VendingStock::class)]
    private Collection $spirals;

    /**
     * @param string $tray
     */
    public function __construct(string $tray)
    {
        $this->tray = $tray;
        $this->spirals = new ArrayCollection();
    }

    public function getTray(): string
    {
        return $this->tray;
    }

    public function setTray(string $tray): void
    {
        $this->tray = $tray;
    }

    public function getSpirals(): Collection
    {
        return $this->spirals;
    }

    public function setSpirals(Collection $spirals): void
    {
        $this->spirals = $spirals;
    }
}