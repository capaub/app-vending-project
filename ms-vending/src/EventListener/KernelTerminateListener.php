<?php

namespace App\EventListener;

use App\Manager\EurekaClient;
use Symfony\Component\HttpKernel\Event\TerminateEvent;
use Symfony\Contracts\HttpClient\Exception\TransportExceptionInterface;

class KernelTerminateListener
{
    private EurekaClient $eurekaClient;

    /**
     * @param EurekaClient $eurekaClient
     */
    public function __construct(EurekaClient $eurekaClient)
    {
        $this->eurekaClient = $eurekaClient;
    }

    /**
     * @throws TransportExceptionInterface
     */
    public function onKernelTerminate(TerminateEvent $event): void
    {
        $this->eurekaClient->unregister();
    }
}