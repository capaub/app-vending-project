<?php

namespace App\Command;

use App\Manager\EurekaClient;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Output\OutputInterface;
use Symfony\Component\Console\Style\SymfonyStyle;
use Symfony\Contracts\HttpClient\Exception\ClientExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\RedirectionExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\ServerExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\TransportExceptionInterface;

class RegisterEurekaCommand extends Command
{
    protected static $defaultName = 'app:eureka:register';
    private EurekaClient $eurekaClient;
    private bool $isRunning = true;

    /**
     * @param EurekaClient $eurekaClient
     */
    public function __construct(EurekaClient $eurekaClient)
    {
        parent::__construct();
        $this->eurekaClient = $eurekaClient;
    }

    /**
     * @return void
     */
    protected function configure(): void
    {
        $this
            ->setDescription('Enregistre le service auprès d\'Eureka et démarre les heartbeats');
    }

    /**
     * @param InputInterface $input
     * @param OutputInterface $output
     * @return int
     * @throws ClientExceptionInterface
     * @throws RedirectionExceptionInterface
     * @throws ServerExceptionInterface
     * @throws TransportExceptionInterface
     */
    protected function execute(InputInterface $input, OutputInterface $output): int
    {
        $io = new SymfonyStyle($input, $output);

        $io->info("Tentative d'enregistrement auprès d'Eureka...");
        try {
            $this->eurekaClient->register();
            $io->success("Service enregistré avec succès !");
        } catch (\Exception $e) {
            $io->error("Erreur lors de l'enregistrement : " . $e->getMessage());
            return Command::FAILURE;
        }

        if (function_exists('pcntl_signal')) {
            pcntl_signal(SIGTERM, [$this, 'stop']);
            pcntl_signal(SIGINT, [$this, 'stop']);
        }

        $io->info("Envoi périodique de heartbeats...");
        while ($this->isRunning) {
            try {
                $this->eurekaClient->sendHeartbeat();
                $io->info("Heartbeat envoyé.");
            } catch (TransportExceptionInterface $e) {
                $io->error("Erreur lors de l'envoi du heartbeat : => " . $e->getMessage());
            }

            sleep(30);

            if (function_exists('pcntl_signal_dispatch')) {
                pcntl_signal_dispatch();
            }
        }

        return Command::SUCCESS;
    }

    /**
     * @return void
     * @throws TransportExceptionInterface
     */
    public function stop(): void
    {
        $this->isRunning = false;
        $this->eurekaClient->unregister();
        echo "Service désenregistré proprement.\n";
    }
}