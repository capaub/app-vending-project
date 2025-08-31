<?php

namespace App\Command;

use App\Manager\EurekaClient;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Output\OutputInterface;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Style\SymfonyStyle;
use Symfony\Contracts\HttpClient\Exception\TransportExceptionInterface;

class UnregisterEurekaCommand extends Command
{
    protected static $defaultName = 'app:eureka:unregister';
    private EurekaClient $eurekaClient;

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
        $this->setDescription('Désenregistre le service auprès d\'Eureka');
    }

    /**
     * @param InputInterface $input
     * @param OutputInterface $output
     * @return int
     */
    protected function execute(InputInterface $input, OutputInterface $output): int
    {
        $io = new SymfonyStyle($input, $output);

        try {
            $this->eurekaClient->unregister();
            $io->success('Service désenregistré avec succès.');
            return Command::SUCCESS;
        } catch (TransportExceptionInterface $e) {
            $io->error('Erreur lors du désenregistrement : ' . $e->getMessage());
            return Command::FAILURE;
        }
    }
}