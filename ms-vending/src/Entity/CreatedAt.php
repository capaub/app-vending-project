<?php

namespace App\Entity;

use DateTime;
use Doctrine\ORM\Mapping as ORM;

trait CreatedAt
{
    /**
     * @var DateTime|null
     */
    #[ORM\Column(name: 'created_at', type: 'datetime', nullable: false, options: ['default' => 'CURRENT_TIMESTAMP'])]
    private ?DateTime $createdAt = null;

    /**
     * @return void
     */
    #[ORM\PrePersist]
    public function setCreatedAt(): void
    {
        if ($this->createdAt === null) {
            $this->createdAt = new DateTime();
        }
    }

    /**
     * @return DateTime|null
     */
    public function getCreatedAt(): ?DateTime
    {
        return $this->createdAt;
    }
}