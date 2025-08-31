#!/bin/bash

# Execute les migrations
php /app/bin/console doctrine:migrations:diff --no-interaction
php /app/bin/console doctrine:migrations:migrate --no-interaction

# Démarre le serveur PHP en arrière-plan
php -S 0.0.0.0:8000 -t public &

# Attends quelques secondes pour m'assurer que le serveur est bien démarré
sleep 5

# Exécute la commande pour l'enregistrement auprès d'Eureka
php /app/bin/console app:eureka:register

# Garde le conteneur en cours d'exécution
tail -f /dev/null
