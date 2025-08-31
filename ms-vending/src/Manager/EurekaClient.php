<?php

namespace App\Manager;

use Exception;
use Symfony\Component\Serializer\Encoder\XmlEncoder;
use Symfony\Contracts\HttpClient\Exception\ClientExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\RedirectionExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\ServerExceptionInterface;
use Symfony\Contracts\HttpClient\Exception\TransportExceptionInterface;
use Symfony\Contracts\HttpClient\HttpClientInterface;

class EurekaClient
{
    private HttpClientInterface $httpClient;
    private string $eurekaServerUrl = "http://eurekaserver:9091/eureka/";
    private string $appName = "VENDING-SERVICE";
    private string $instanceId = "vending-service-php:8000";
    private array $instanceInfo = [
        "instance" => [
            "instanceId" => "vending-service-php:8000",
            "hostName" => "vending-service",
            "app" => "VENDING-SERVICE",
            "ipAddr" => "vending-service",
            "port" => ["$" => 8000, "@enabled" => "true"],
            "vipAddress" => "VENDING-SERVICE",
            "dataCenterInfo" => [
                "@class" => 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
                "name" => "MyOwn"
            ]
        ]
    ];

    /**
     * @param HttpClientInterface $httpClient
     */
    public function __construct(HttpClientInterface $httpClient)
    {
        $this->httpClient = $httpClient;
    }

    /**
     * @return void
     * @throws ClientExceptionInterface
     * @throws RedirectionExceptionInterface
     * @throws ServerExceptionInterface
     * @throws TransportExceptionInterface
     */
    public function register(): void
    {
        $url = $this->eurekaServerUrl . "apps/" . $this->appName;
        try {
            $response = $this->httpClient->request('POST', $url, [
                'json' => $this->instanceInfo
            ]);

            if ($response->getStatusCode() == 204) {
                echo "Service enregistré avec succès dans Eurekaka.";
            } else {
                echo "Erreur lors de l'enregistrement du service kaka.";
                echo $response->getContent(false);
            }
        } catch (Exception $e) {
            echo "Erreur lors de l'enregistrement : " . $e->getMessage();
        }
    }

    /**
     * @return void
     * @throws TransportExceptionInterface
     */
    public function sendHeartbeat(): void
    {
        $url = $this->eurekaServerUrl . "apps/" . $this->appName . "/" . $this->instanceId;
        try {
            $response = $this->httpClient->request('PUT', $url);

            if ($response->getStatusCode() === 200) {
                echo "Heartbeat envoyé avec succès.";
            } else {
                echo "Erreur lors de l'envoi du heartbeat." . $response->getContent();
            }
        } catch (Exception $e) {
            echo "Erreur lors de l'envoi du heartbeat : -> probleme " . $e->getMessage();
        }
    }

    /**
     * @param string $appName
     * @return string|null
     * @throws ClientExceptionInterface
     * @throws RedirectionExceptionInterface
     * @throws ServerExceptionInterface
     * @throws TransportExceptionInterface
     */
    public function getServiceUrl(string $appName): ?string
    {
        $url = "http://eurekaserver:9091/eureka/apps/" . strtoupper($appName);

        try {
            $response = $this->httpClient->request('GET', $url);
            if ($response->getStatusCode() !== 200) {
                return null;
            }

            $encoder = new XmlEncoder();
            $content = $response->getContent();

            $data = $encoder->decode($content, 'xml');

            if (!isset($data['instance'])) {
                echo "Erreur : données XML inattendues.\n";
                return null;
            }

            $instance = $data['instance'];
            $hostName = $instance['hostName'] ?? null;

            $port = $instance['port'] ?? null;
            if (is_array($port) && isset($port['#'])) {
                $port = $port['#'];
            }

            if ($hostName && $port) {
                return "http://$hostName:$port";
            }

            return null;
        } catch (Exception $e) {
            echo "Erreur lors de la récupération de l'URL du service : " . $e->getMessage();
            return null;
        }
    }

    /**
     * @return void
     * @throws TransportExceptionInterface
     */
    public function unregister(): void
    {
        $url = $this->eurekaServerUrl . "apps/" . $this->appName . "/" . $this->instanceId;
        try {
            $response = $this->httpClient->request('DELETE', $url);

            if ($response->getStatusCode() === 200) {
                echo "Service désenregistré avec succès.";
            } else {
                echo "Erreur lors du désenregistrement ici" . $response->getContent();
            }
        } catch (Exception $e) {
            echo "Erreur lors du désenregistrement : " . $e->getMessage();
        }
    }

}