<?php

namespace App\Document;

use DateTime;
use Doctrine\ODM\MongoDB\Mapping\Annotations as ODM;

trait Timestampable
{
    #[ODM\Field(type: 'date')]
    private ?DateTime $createdAt = null;

    #[ODM\Field(type: 'date')]
    private ?DateTime $updatedAt = null;

    #[ODM\PrePersist]
    public function onPrePersist(): void
    {
        $this->createdAt = new DateTime();
    }

    #[ODM\PreUpdate]
    public function onPreUpdate(): void
    {
        $this->updatedAt = new DateTime();
    }

    public function getCreatedAt(): ?DateTime
    {
        return $this->createdAt;
    }

    public function getUpdatedAt(): ?DateTime
    {
        return $this->updatedAt;
    }
}
