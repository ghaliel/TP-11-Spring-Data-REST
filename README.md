# 🏦 Gestion de Comptes Bancaires - Spring Data REST

## 📋 Description

Application Spring Boot démontrant l'utilisation de **Spring Data REST** pour gérer des comptes bancaires et des clients via une API REST automatique avec hypermedia HAL.

## 🚀 Technologies Utilisées

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2-0000BB?style=for-the-badge&logo=database&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-BC2220?style=for-the-badge&logo=lombok&logoColor=white)

### 📚 Dépendances Principales

- **Spring Boot Starter Data JPA** - Gestion de la persistance
- **Spring Boot Starter Data REST** - API REST automatique
- **H2 Database** - Base de données en mémoire
- **Lombok** - Réduction du code boilerplate
- **Spring Boot DevTools** - Rechargement automatique en développement

## ✨ Fonctionnalités

- ✅ API REST automatique avec Spring Data REST
- 🔍 Recherche personnalisée de comptes par type (COURANT/EPARGNE)
- 💾 Base de données H2 en mémoire
- 🌐 Console H2 accessible pour visualiser les données
- 📊 Relations OneToMany/ManyToOne entre Client et Compte
- 🎯 Projections personnalisées pour les vues
- 🔗 Hypermedia HAL pour la navigation
- 🆔 Exposition des identifiants dans les réponses JSON

## 📦 Structure du Projet

```
src/main/java/com/exemple/demo/
├── 📄 Application.java                    # Point d'entrée de l'application
├── 📁 Comptes/
│   ├── Client.java                        # Entité Client
│   ├── ClientProjection.java             # Projection pour Client
│   ├── Compte.java                        # Entité Compte
│   ├── CompteProjection1.java            # Projection 1 pour Compte
│   ├── CompteProjection2.java            # Projection 2 pour Compte
│   └── TypeCompte.java                   # Enum TypeCompte (COURANT/EPARGNE)
├── 📁 config/
│   └── RestConfiguration.java            # Configuration REST
├── 📁 Controllers/
│   └── CompteController.java             # Contrôleur personnalisé
└── 📁 repository/
    ├── ClientRepository.java             # Repository Client
    └── CompteRepository.java             # Repository Compte avec recherche par type
```

## 🛠️ Installation et Exécution

### Prérequis

- ☕ **Java 17** ou supérieur
- 📦 **Maven 3.6+**

### Étapes d'installation

#### 1️⃣ Cloner le projet
```bash
git clone <repository-url>
cd demo
```

#### 2️⃣ Compiler le projet
```bash
mvnw clean install
```
Ou sur Windows :
```cmd
mvnw.cmd clean install
```

#### 3️⃣ Lancer l'application
```bash
mvnw spring-boot:run
```
Ou sur Windows :
```cmd
mvnw.cmd spring-boot:run
```

#### 4️⃣ Accéder à l'application

- 🌐 **API REST** : [http://localhost:8082/api](http://localhost:8082/api)
- 💻 **Console H2** : [http://localhost:8082/h2-console](http://localhost:8082/h2-console)
  - **JDBC URL** : `jdbc:h2:mem:banque`
  - **Username** : `sa`
  - **Password** : *(laisser vide)*

## 🔌 Endpoints API

### 📊 Endpoints Principaux

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| 🔍 GET | `/api/comptes` | Liste tous les comptes |
| 🔍 GET | `/api/clients` | Liste tous les clients |
| 🔍 GET | `/api/comptes/{id}` | Détails d'un compte |
| 🔍 GET | `/api/clients/{id}` | Détails d'un client |
| ➕ POST | `/api/comptes` | Créer un nouveau compte |
| ➕ POST | `/api/clients` | Créer un nouveau client |
| ✏️ PUT | `/api/comptes/{id}` | Modifier un compte |
| ✏️ PATCH | `/api/comptes/{id}` | Mise à jour partielle |
| ❌ DELETE | `/api/comptes/{id}` | Supprimer un compte |

### 🔎 Endpoints de Recherche

| Méthode | Endpoint | Description | Exemple |
|---------|----------|-------------|---------|
| 🔍 GET | `/api/comptes/search` | Liste les recherches disponibles | - |
| 🔍 GET | `/api/comptes/search/byType?t=EPARGNE` | Filtrer par type de compte | `?t=COURANT` ou `?t=EPARGNE` |

### 📝 Exemple de Requêtes

#### Créer un client
```bash
curl -X POST http://localhost:8082/api/clients \
  -H "Content-Type: application/json" \
  -d '{"nom":"Mohamed","email":"mohamed@example.com"}'
```

#### Créer un compte
```bash
curl -X POST http://localhost:8082/api/comptes \
  -H "Content-Type: application/json" \
  -d '{
    "solde": 5000.0,
    "dateCreation": "2025-11-10",
    "type": "EPARGNE",
    "client": "http://localhost:8082/api/clients/1"
  }'
```

#### Rechercher des comptes d'épargne
```bash
curl http://localhost:8082/api/comptes/search/byType?t=EPARGNE
```

## 🏗️ Modèle de Données

### 👤 Entité Client

```java
@Entity
public class Client {
    @Id @GeneratedValue
    private Long id;
    private String nom;
    private String email;
    
    @OneToMany(mappedBy = "client")
    private List<Compte> comptes;
}
```

### 💳 Entité Compte

```java
@Entity
public class Compte {
    @Id @GeneratedValue
    private Long id;
    private double solde;
    private Date dateCreation;
    
    @Enumerated(EnumType.STRING)
    private TypeCompte type;
    
    @ManyToOne
    private Client client;
}
```

### 🎯 Types de Comptes

- 💰 **COURANT** : Compte courant pour les opérations quotidiennes
- 🏦 **EPARGNE** : Compte épargne pour économiser

## 🧪 Données de Test

L'application initialise automatiquement au démarrage :

| Client | Email | Comptes |
|--------|-------|---------|
| 👤 Amal | amal@example.com | 1 EPARGNE + 1 COURANT |
| 👤 Ali | ali@example.com | 1 EPARGNE |

**Total** : 2 clients et 3 comptes avec des soldes aléatoires

## ⚙️ Configuration

### 📄 application.properties

```properties
# Configuration H2
spring.datasource.url=jdbc:h2:mem:banque
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Console H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Serveur
server.port=8082

# Hibernate
spring.jpa.hibernate.ddl-auto=update

# Spring Data REST
spring.data.rest.base-path=/api
```

## 📚 Annotations Clés

| Annotation | Utilité |
|------------|---------|
| `@RepositoryRestResource` | Expose automatiquement le repository en REST avec HAL |
| `@RestResource(path="/byType")` | Personnalise le chemin REST d'une méthode de recherche |
| `@Projection` | Définit des vues personnalisées des entités |
| `@OneToMany` | Un client peut avoir plusieurs comptes |
| `@ManyToOne` | Plusieurs comptes appartiennent à un client |
| `@Enumerated(EnumType.STRING)` | Stocke l'enum en tant que String dans la BD |

## 🎓 Concepts Démontrés

### Spring Data REST
- ✅ Génération automatique d'API REST
- ✅ Hypermedia HAL (HATEOAS)
- ✅ Méthodes de recherche personnalisées
- ✅ Projections pour contrôler la sérialisation
- ✅ Configuration des relations

### JPA / Hibernate
- ✅ Entités avec annotations
- ✅ Relations bidirectionnelles OneToMany/ManyToOne
- ✅ Types énumérés
- ✅ Génération automatique de schéma

### Spring Boot
- ✅ Configuration par propriétés
- ✅ CommandLineRunner pour initialisation
- ✅ Inversion de contrôle (IoC)

## 🐛 Dépannage

### Erreur "Port 8082 déjà utilisé"
Modifiez le port dans `application.properties` :
```properties
server.port=8083
```

### Console H2 inaccessible
Vérifiez que la propriété est activée :
```properties
spring.h2.console.enabled=true
```

### Erreurs de compilation
Vérifiez que Lombok est correctement installé dans votre IDE.

## 📖 Documentation API

Une fois l'application lancée, accédez à la racine de l'API pour découvrir tous les endpoints :

🌐 [http://localhost:8082/api](http://localhost:8082/api)

Spring Data REST fournit automatiquement une documentation HAL explorable.


## Demonstration :
![Screenshot 2025-11-10 225204.png](../../Images/Screenshots/Screenshot%202025-11-10%20225204.png)


![Screenshot 2025-11-10 225217.png](../../Images/Screenshots/Screenshot%202025-11-10%20225217.png)



![Screenshot 2025-11-10 225225.png](../../Images/Screenshots/Screenshot%202025-11-10%20225225.png)



![Screenshot 2025-11-10 225235.png](../../Images/Screenshots/Screenshot%202025-11-10%20225235.png)



![Screenshot 2025-11-10 225244.png](../../Images/Screenshots/Screenshot%202025-11-10%20225244.png)


## 🎯 Améliorations Possibles

- 🔐 Ajouter Spring Security pour l'authentification
- 📊 Ajouter des endpoints pour statistiques (solde total, etc.)
- 🔄 Implémenter des transactions entre comptes
- 📧 Validation des emails avec annotations
- 🧪 Ajouter des tests unitaires et d'intégration
- 📄 Générer une documentation Swagger/OpenAPI
- 🚀 Containeriser avec Docker

## 👨‍💻 Auteur

**ghaliel**

---

## 📄 Licence

Ce projet est à usage éducatif dans le cadre d'un TP sur Spring Data REST.

---

⭐ **Si ce projet vous a aidé dans votre apprentissage, n'hésitez pas à lui donner une étoile !**

---

## 🆘 Support

Pour toute question ou problème :
1. Consultez la [documentation Spring Data REST](https://spring.io/projects/spring-data-rest)
2. Vérifiez les logs de l'application
3. Consultez la console H2 pour l'état de la base de données

---

**Happy Coding! 🚀**

