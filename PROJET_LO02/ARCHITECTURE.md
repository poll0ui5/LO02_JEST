# 🏗️ ARCHITECTURE DU PROJET JEST

## 📐 Pattern MVC (Model-View-Controller)

Ce projet respecte strictement l'architecture **MVC** avec une séparation claire entre les modes **Terminal** et **Swing**.

---

## 📂 STRUCTURE DES DOSSIERS

```
src/fr/utt/lo02/jest/
│
├── 🎮 controller/              # CONTRÔLEURS (Logique de jeu)
│   ├── terminal/
│   │   └── TerminalController.java    # Contrôleur pour le mode console
│   └── swing/
│       └── SwingController.java       # Contrôleur pour l'interface graphique
│
├── 👁️ view/                    # VUES (Interface utilisateur)
│   ├── terminal/
│   │   └── TerminalView.java          # Vue console (affichage + saisie)
│   └── swing/
│       ├── MainFrame.java             # Fenêtre principale
│       ├── MenuPanel.java             # Panel du menu
│       ├── GamePanel.java             # Panel de jeu
│       └── ResultPanel.java           # Panel des résultats
│
├── 📦 model/                   # MODÈLE (Données et logique métier)
│   ├── Carte.java                     # Classe de base des cartes
│   ├── CarteOffre.java                # Carte avec visibilité
│   ├── Couleur.java                   # Enum des couleurs
│   ├── Valeur.java                    # Enum des valeurs
│   ├── JeuCartes.java                 # Pioche de cartes
│   ├── Joueur.java                    # Classe abstraite joueur
│   ├── JoueurHumain.java              # Joueur humain
│   ├── JoueurVirtuel.java             # Bot IA
│   └── Trophee.java                   # Gestion des trophées
│
├── 🧠 strategy/                # PATTERN STRATEGY (IA des bots)
│   ├── Strategie.java                 # Interface
│   ├── StrategieOffensive.java        # Stratégie agressive
│   └── StrategieDefensive.java        # Stratégie prudente
│
├── 🎲 variante/                # PATTERN FACTORY (Modes de jeu)
│   ├── Variante.java                  # Interface
│   ├── VarianteClassique.java         # Mode classique
│   ├── VarianteSansTrophee.java       # Mode sans trophées
│   └── VarianteDoubleMise.java        # Mode avec bonus/malus
│
├── ✨ extension/               # PATTERN FACTORY (Extensions)
│   ├── Extension.java                 # Interface
│   ├── ExtensionCartesSpeciales.java  # Cartes spéciales
│   └── CarteExtension.java            # Carte avec effet spécial
│
├── 🧮 visitor/                 # PATTERN VISITOR (Calcul des scores)
│   ├── Visitor.java                   # Interface
│   └── VisitorScore.java              # Calcul des scores
│
├── 💾 sauvegarde/              # SÉRIALISATION JAVA
│   ├── EtatPartie.java                # État complet du jeu
│   └── GestionnaireSauvegarde.java    # Gestion fichiers .jest
│
└── 🧪 test/                    # TESTS
    └── TestJest.java                  # Tests unitaires
```

---

## 🔄 FLUX DE DONNÉES

### **Mode Terminal**

```
TerminalController (controller/terminal/)
    ↓ utilise
TerminalView (view/terminal/)
    ↓ affiche/lit
Console (System.in/out)
```

### **Mode Swing**

```
SwingController (controller/swing/)
    ↓ utilise
MainFrame → MenuPanel/GamePanel/ResultPanel (view/swing/)
    ↓ affiche
Interface Graphique (Swing)
```

### **Modèle (partagé par les 2 modes)**

```
Controller (Terminal ou Swing)
    ↓ manipule
Model (Joueur, Carte, JeuCartes, etc.)
    ↓ utilise
Strategy, Variante, Extension, Visitor
```

---

## 🎯 DESIGN PATTERNS UTILISÉS

### **1. MVC (Model-View-Controller)**
- **Model** : Classes métier (`Carte`, `Joueur`, `JeuCartes`)
- **View** : Interfaces utilisateur (Terminal, Swing)
- **Controller** : Logique de jeu (`TerminalController`, `SwingController`)

### **2. Strategy Pattern**
- **Interface** : `Strategie`
- **Implémentations** : `StrategieOffensive`, `StrategieDefensive`
- **Usage** : IA des bots

### **3. Visitor Pattern**
- **Interface** : `Visitor`
- **Implémentation** : `VisitorScore`
- **Usage** : Calcul des scores selon les règles

### **4. Factory Pattern**
- **Interfaces** : `Variante`, `Extension`
- **Usage** : Création dynamique des modes de jeu et extensions

### **5. Template Method**
- **Classe abstraite** : `Joueur`
- **Méthodes abstraites** : `faireOffre()`, `choisirAdversaire()`, `prendreCarteDansOffre()`
- **Implémentations** : `JoueurHumain`, `JoueurVirtuel`

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

## 📝 CONVENTIONS DE NOMMAGE

- **Packages** : `lowercase` (ex: `controller`, `view`, `model`)
- **Classes** : `PascalCase` (ex: `TerminalController`, `SwingController`)
- **Méthodes** : `camelCase` (ex: `faireOffre()`, `choisirAdversaire()`)
- **Constantes** : `UPPER_SNAKE_CASE` (ex: `serialVersionUID`)

---

## 🔧 DÉPENDANCES

- **Java** : JDK 8+
- **Swing** : Inclus dans le JDK (javax.swing)
- **Aucune dépendance externe** : Projet 100% Java standard

---

## 📊 SÉPARATION DES RESPONSABILITÉS

### **Controller**
- Gère le flux du jeu (tours, manches, fin de partie)
- Coordonne Model et View
- Gère la sauvegarde/chargement

### **View**
- **Terminal** : Affichage console + lecture clavier
- **Swing** : Interface graphique + gestion événements

### **Model**
- Données du jeu (cartes, joueurs, scores)
- Logique métier (règles, calculs)
- Indépendant de l'interface

---

## ✅ AVANTAGES DE CETTE ARCHITECTURE

1. **Séparation claire** : Terminal et Swing sont isolés
2. **Réutilisabilité** : Le Model est partagé par les 2 interfaces
3. **Maintenabilité** : Facile de modifier une interface sans toucher l'autre
4. **Extensibilité** : Facile d'ajouter une nouvelle interface (ex: Web)
5. **Testabilité** : Chaque couche peut être testée indépendamment

---

## 🎓 POUR LA SOUTENANCE

**Question** : "Pourquoi avoir séparé Terminal et Swing dans des packages différents ?"

**Réponse** : "Pour respecter le principe de **Single Responsibility** et faciliter la maintenance. Chaque mode a son propre contrôleur et sa propre vue, mais ils partagent le même modèle. Cela permet de modifier l'interface Terminal sans risquer de casser l'interface Swing, et vice-versa. De plus, si on voulait ajouter une interface Web, on créerait simplement `controller/web/` et `view/web/` sans toucher au code existant."

---

**Architecture mise à jour le** : 14 janvier 2026
