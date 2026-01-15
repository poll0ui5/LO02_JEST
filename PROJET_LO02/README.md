# 🎮 JEST - Jeu de Cartes

Projet LO02 - Jeu de cartes Jest en Java avec architecture MVC stricte.

## 📂 Structure du Projet

```
PROJET_LO02/
├── src/              # Code source
│   ├── model/        # Logique métier
│   ├── view/         # Interfaces (Terminal + Swing)
│   └── controller/   # Contrôleurs
├── bin/              # Fichiers compilés
└── sauvegardes/      # Parties sauvegardées (.jest)
```

## 🚀 Lancer le Jeu

### 1️⃣ Compiler le projet

```bash
cd PROJET_LO02
javac -d bin -sourcepath src src/fr/utt/lo02/jest/view/swing/MainFrame.java
```

### 2️⃣ Copier les ressources (images)

**macOS / Linux :**
```bash
mkdir -p bin/resources/images
cp -r src/resources/images/* bin/resources/images/
```

**Windows (PowerShell) :**
```powershell
New-Item -ItemType Directory -Force -Path bin\resources\images | Out-Null
Copy-Item -Recurse -Force src\resources\images\* bin\resources\images\
```

### 3️⃣ Lancer l'interface graphique (Swing)

```bash
java -cp bin fr.utt.lo02.jest.view.swing.MainFrame
```

### 4️⃣ Lancer l'interface terminal

```bash
javac -d bin -sourcepath src src/fr/utt/lo02/jest/controller/terminal/TerminalController.java
java -cp bin fr.utt.lo02.jest.controller.terminal.TerminalController
```

## 📖 Règles du Jeu

Chaque joueur pose 2 cartes (1 visible, 1 cachée). Les joueurs prennent ensuite les cartes des autres pour marquer des points.

**Voir `REGLES_DU_JEU.txt` pour les règles complètes.**

## 🏗️ Architecture

Architecture MVC stricte avec 3 dossiers principaux :
- **model/** : Toute la logique métier (cartes, joueurs, stratégies, variantes, etc.)
- **view/** : Toutes les interfaces (Terminal + Swing)
- **controller/** : Tous les contrôleurs (TerminalController + SwingController)

**Voir `ARCHITECTURE.md` pour plus de détails.**
