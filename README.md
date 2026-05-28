# Smart Energy Manager

Application Java/JavaFX de gestion et de suivi des consommations énergétiques de bâtiments.

Projet réalisé dans le cadre du module II.1102 — Algorithmique et Programmation Java (ISEP, A.U. 2025-2026).

**Auteurs :** Louis, Clémence, Eliarisoa

---

## Fonctionnalités

- **Gestion des organisations** : créer, modifier, supprimer une organisation et consulter ses bâtiments
- **Gestion des bâtiments** : Maison, Appartement, Bureau, Local commercial — avec création, modification, suppression et clonage
- **Relevés de consommation** : saisie manuelle (Électricité, Gaz, Eau, Climatisation), import CSV, génération de données de test
- **Tableau de bord** : consommation du jour / mois / année, coût estimé, bâtiment le plus consommateur, alertes, indicateurs de performance
- **Graphiques** : courbe de tendance, histogramme par période, répartition par type d'énergie (StackedBarChart par bâtiment)
- **Comparaison multi-bâtiments** : visualisation côte à côte par période ou par type d'énergie, type dominant, pic de consommation
- **Historique** : tableau filtrable par organisation, bâtiment, type d'énergie et période
- **Export CSV** : export des relevés d'un bâtiment ou de toute une organisation

---

## Technologies

- Java 21
- JavaFX 21
- Maven
- Persistance fichiers CSV (pas de base de données)

---

## Lancer le projet

```bash
./mvnw javafx:run
```

> Sur Windows : `.\mvnw.cmd javafx:run`

---

## Structure

```
src/
  main/
    java/com/isep/ead/
      controllers/   # Contrôleurs JavaFX (CRUD, dashboard, graphiques...)
      dao/           # Couche d'accès aux données (générique, CSV)
      models/        # Modèles : Building, Energy, Organization...
      utils/         # CSVHandler, SceneManager
      templates/     # Composants réutilisables (cartes bâtiment/organisation)
      widgets/       # Popups, formulaires
    resources/       # Fichiers FXML (vues)
*.csv                # Données persistées localement
```

---

## Format d'import CSV (énergie)

Pour importer des relevés via le bouton **📂 Import CSV** sur la vue d'un bâtiment :

```
date,type,quantite,prix_unitaire[,extra]
2026-01-15,Electricity,350.5,0.18,false
2026-01-15,Gas,120.0,0.09
2026-01-15,Water,45.0,0.004
2026-01-15,Climatisation,80.0,0.20,22.0
```

- `type` : `Electricity`, `Gas`, `Water`, `Climatisation`
- `extra` (optionnel) : heures creuses pour Electricity, eau chaude pour Water, température cible pour Climatisation

