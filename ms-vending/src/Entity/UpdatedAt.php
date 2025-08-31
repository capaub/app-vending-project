<?php

namespace App\Entity;

use DateTime;
use Doctrine\ORM\Mapping as ORM;

trait UpdatedAt
{
    /**
     * @var DateTime|null
     */
    #[ORM\Column(name: 'updated_at', type: 'datetime', nullable: false, options: ['default' => 'CURRENT_TIMESTAMP'])]
    private ?DateTime $updatedAt = null;

    /**
     * @return void
     */
    #[ORM\PrePersist]
    public function setUpdatedAt(): void
    {
        if ($this->updatedAt === null) {
            $this->updatedAt = new DateTime();
        }
    }

    /**
     * @return DateTime|null
     */
    public function getUpdatedAt(): ?DateTime
    {
        return $this->createdAt;
    }
}