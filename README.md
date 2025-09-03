# Vendo Logic — app-vending-project

**Suite microservices** pour gérer un parc de **distributeurs automatiques** (entreprises, sites/clients, produits, machines, intégrations externes, webapp).  
Stack principale : **Java 17 · Spring Boot 3 · Spring Cloud (Eureka, Config Server)**.

> Ce dépôt est la **refonte microservices** du monolithe PHP :  
> Legacy → https://github.com/capaub/Vendo_Logic

---

## ⚙️ Modules (ceux présents dans ce repo)

- **eureka-server-vending-app** — Service Discovery (Eureka)
- **config-server-vending-app** — Configuration centralisée (Spring Cloud Config)
- **ms-company** — gestion des entreprises / utilisateurs
- **ms-customer** — gestion des clients / sites d’installation
- **ms-product** — gestion des produits
- **ms-vending** — gestion des machines / emplacements
- **ms-external-api** — intégrations externes (ex : catalogues/données publiques)
- **ms-webapp** — application web (UI)
- **sprint-chart-js** — ressources front (charts/démo)

> La liste ci-dessus reprend exactement les dossiers de la racine du dépôt.

---

## 🧭 Architecture (vue simplifiée)

```mermaid
flowchart LR
  WebApp[ms-webapp] --> Company[ms-company]
  WebApp --> Customer[ms-customer]
  WebApp --> Product[ms-product]
  WebApp --> Vending[ms-vending]
  WebApp -.intégrations.-> Ext[ms-external-api]

  subgraph Infra
    Eureka[(Eureka)]
    Config[(Config Server)]
    DB[(DBs)]
  end

  Company --- DB
  Customer --- DB
  Product --- DB
  Vending --- DB

  Company --> Eureka
  Customer --> Eureka
  Product --> Eureka
  Vending --> Eureka

  Company --> Config
  Customer --> Config
  Product --> Config
  Vending --> Config
