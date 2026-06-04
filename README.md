# Têla — gestion d'atelier de couture

Têla est une application mobile Android destinée aux tailleurs de Maroua. Cette première étape pose une base Jetpack Compose propre et navigable pour la démonstration devant le jury.

## Étape 1 livrée

- Initialisation d'un projet Android Kotlin/Jetpack Compose.
- Utilisation de ressources XML uniquement pour éviter les fichiers binaires dans les demandes d'extraction.
- Création d'une identité visuelle inspirée des maquettes : marron, orange couture, beige clair et cartes arrondies.
- Mise en place d'une navigation basse avec quatre sections : Accueil, Clients, Commandes et Profil.
- Ajout de données de démonstration locales en mémoire pour présenter le parcours métier sans attendre la base de données.

## Écrans disponibles

1. **Accueil** : message de bienvenue, recherche, statistiques, commandes récentes et catégories.
2. **Clients** : liste de clients avec téléphone et nombre de commandes.
3. **Commandes** : détail d'une commande avec mesures et avancement.
4. **Profil** : profil du tailleur, statistiques atelier et menus de configuration.

## Prochaines étapes recommandées

1. Ajouter Room pour stocker les clients, mesures et commandes localement.
2. Créer les formulaires d'ajout et de modification de client.
3. Créer les formulaires de commande et de mesures.
4. Rendre les étapes d'avancement réellement modifiables.
5. Préparer un jeu de données de démonstration stable pour la soutenance.

## Lancement prévu

Lorsque l'environnement Android est disponible :

```bash
./gradlew :app:assembleDebug
```

Dans l'environnement actuel, le SDK Android et l'accès aux dépôts Google/Gradle ne sont pas disponibles, donc le build ne peut pas encore être exécuté localement.
