# Poubelle Malyraic

Cette application ,développée en java spring, est constituée de l'architecture de ramassages de poubelles d'utilisateur à vélo cargo au sein de la ville Malyraic. A l'aide du modèle MVC (Modèle Vue Conroleur), elle gère la liaison entre la base de données et la gestion des ramassages des poubelles.

## Dépendances

java 21
java spring
maven
mariadb

## Installation

java 21 : <https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.msi>
mariadb : <https://mariadb.org/download/?t=mariadb&p=mariadb&r=11.4.2&os=windows&cpu=x86_64&pkg=msi&mirror=icam>

## Lancement de l'application

Avant tout chose, il faut vérifier :

- les ports 8080 et 3306 sont libres

Remarque : j'utilise pour la gestion de ma base de données le port 3306.
pour le lancement de mon application java spring backend, j'utilise le port 8080.

Lancer l'application back avec cette commande :

```sh
 path/to/openjdk-21.0.2\bin\java.exe -jar .\target\poubelle-0.0.1-SNAPSHOT.jar
```
