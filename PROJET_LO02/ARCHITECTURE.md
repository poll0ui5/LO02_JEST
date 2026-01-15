# 🏗️ ARCHITECTURE MVC STRICTE - PROJET JEST

## 📐 Pattern MVC (Model-View-Controller)

Ce projet respecte **strictement** l'architecture **MVC** avec seulement **3 dossiers principaux**.

---

## 📂 STRUCTURE ULTRA-SIMPLE

```
src/fr/utt/lo02/jest/
│
├── 📦 model/           # TOUT LE MODÈLE
│   ├── Carte.java
│   ├── CarteOffre.java
│   ├── CarteExtension.java
│   ├── Couleur.java
│   ├── Valeur.java
│   ├── JeuCartes.java
│   ├── Joueur.java
│   ├── JoueurHumain.java
│   ├── JoueurVirtuel.java
│   ├── Trophee.java
│   ├── Strategie.java
│   ├── StrategieOffensive.java
│   ├── StrategieDefensive.java
│   ├── Variante.java
│   ├── VarianteClassique.java
│   ├── VarianteSansTrophee.java
│   ├── VarianteDoubleMise.java
│   ├── Extension.java
│   ├── ExtensionCartesSpeciales.java
│   ├── Visitor.java
│   ├── VisitorScore.java
│   ├── EtatPartie.java
│   └── GestionnaireSauvegarde.java
│
├── 👁️ view/            # TOUTES LES VUES
│   ├── terminal/
│   │   └── TerminalView.java
│   └── swing/
│       ├── MainFrame.java
│       ├── MenuPanel.java
│       ├── GamePanel.java
│       └── ResultPanel.java
│
└── 🎮 controller/      # TOUS LES CONTRÔLEURS
    ├── terminal/
    │   └── TerminalController.java
    └── swing/
        └── SwingController.java
```

---

## 🎯 RÉPARTITION DES RESPONSABILITÉS

### **📦 MODEL (Modèle)**
Contient **TOUTE** la logique métier et les données :
- **Cartes** : `Carte`, `CarteOffre`, `CarteExtension`, `Couleur`, `Valeur`
- **Joueurs** : `Joueur`, `JoueurHumain`, `JoueurVirtuel`
- **Jeu** : `JeuCartes`, `Trophee`
- **Stratégies IA** : `Strategie`, `StrategieOffensive`, `StrategieDefensive`
- **Variantes** : `Variante`, `VarianteClassique`, `VarianteSansTrophee`, `VarianteDoubleMise`
- **Extensions** : `Extension`, `ExtensionCartesSpeciales`
- **Calcul scores** : `Visitor`, `VisitorScore`
- **Sauvegarde** : `EtatPartie`, `GestionnaireSauvegarde`

### **👁️ VIEW (Vue)**
Contient **TOUTES** les interfaces utilisateur :
- **Terminal** : `TerminalView` (affichage console + saisie clavier)
- **Swing** : `MainFrame`, `MenuPanel`, `GamePanel`, `ResultPanel`

### **🎮 CONTROLLER (Contrôleur)**
Contient **TOUTE** la logique de coordination :
- **Terminal** : `TerminalController` (gère le flux du jeu en mode console)
- **Swing** : `SwingController` (gère le flux du jeu en mode graphique)

---

## 🔄 FLUX DE DONNÉES

```
Controller (Terminal ou Swing)
    ↓ manipule
Model (Carte, Joueur, Strategie, Variante, etc.)
    ↓ affiche via
View (TerminalView ou Swing)
```

---

## 🎯 DESIGN PATTERNS UTILISÉS

### **1. MVC (Model-View-Controller)**
- **Séparation stricte** en 3 couches
- **Model** indépendant de l'interface
- **View** ne contient aucune logique métier
- **Controller** coordonne Model et View

### **2. Strategy Pattern**
- **Fichiers** : `Strategie.java`, `StrategieOffensive.java`, `StrategieDefensive.java`
- **Usage** : IA des bots (changement de comportement dynamique)

### **3. Visitor Pattern**
- **Fichiers** : `Visitor.java`, `VisitorScore.java`
- **Usage** : Calcul des scores selon les règles

### **4. Factory Pattern**
- **Fichiers** : `Variante.java`, `Extension.java`
- **Usage** : Création dynamique des modes de jeu et extensions

### **5. Template Method**
- **Fichier** : `Joueur.java` (classe abstraite)
- **Usage** : Définit le squelette des méthodes communes aux joueurs

---

## 🚀 POINTS D'ENTRÉE

### **Lancer le mode Terminal**
```bash
java fr.utt.lo02.jest.controller.terminal.TerminalController
```

### **Lancer le mode Swing**
```bash
java fr.utt.lo02.jest.view.swing.MainFrame
```

---

## ✅ AVANTAGES DE CETTE ARCHITECTURE

1. **Ultra-simple** : Seulement 3 dossiers principaux
2. **Clair** : Chaque fichier a sa place évidente
3. **MVC pur** : Séparation stricte des responsabilités
4. **Maintenable** : Facile de trouver et modifier du code
5. **Extensible** : Facile d'ajouter de nouvelles fonctionnalités

---

## 📝 CONVENTIONS DE NOMMAGE

- **Packages** : `model`, `view`, `controller` (3 dossiers seulement)
- **Classes** : `PascalCase` (ex: `TerminalController`, `SwingController`)
- **Méthodes** : `camelCase` (ex: `faireOffre()`, `choisirAdversaire()`)
- **Constantes** : `UPPER_SNAKE_CASE` (ex: `serialVersionUID`)

---

## 🎓 POUR LA SOUTENANCE

**Question** : "Expliquez votre architecture MVC"

**Réponse** : "J'ai implémenté une architecture MVC **stricte** avec seulement **3 dossiers** :
- **model/** : Contient TOUTE la logique métier (cartes, joueurs, stratégies, variantes, calcul scores, sauvegarde)
- **view/** : Contient TOUTES les interfaces (Terminal et Swing)
- **controller/** : Contient TOUS les contrôleurs (TerminalController et SwingController)

Cette structure est **ultra-simple** et respecte parfaitement le principe de **séparation des responsabilités**. Chaque couche est indépendante : je peux changer l'interface sans toucher au modèle, et vice-versa."

---

**Architecture mise à jour le** : 15 janvier 2026
**Structure** : MVC strict (3 dossiers uniquement)
