# Utilise l'image PHP (de base - non optimisé)
FROM php:8.2-cli

# Identification du développeur
MAINTAINER capaub

# Installe les extensions nécessaires
RUN apt-get update && \
    apt-get install -y zip unzip git libssl-dev && \
    docker-php-ext-install pcntl pdo_mysql && \
    pecl install mongodb && \
    docker-php-ext-enable mongodb

# Définit le répertoire de travail
WORKDIR /app

# Copie tout le code source dans le conteneur
COPY . /app

# Copie le fichier composer pour installer les dépendances en premier
COPY composer.json composer.lock /app/

# Installe les dépendances Composer
RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer \
RUN composer install --no-dev --optimize-autoloader

# Copie le script de démarrage
COPY start.sh /app/start.sh

# Rend le script exécutable
RUN chmod +x /app/start.sh

# Exécute le script au démarrage du conteneur
CMD ["/app/start.sh"]





