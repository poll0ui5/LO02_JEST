# JEST

Jeu de cartes en Java (Swing).

## Lancer

```bash
cd PROJET_LO02
```

### Compiler
```bash
javac -d classes -sourcepath src src/fr/utt/lo02/jest/view/swing/MainFrame.java
```

### Copier les ressources (images)

**macOS / Linux :**
```bash
mkdir -p classes/resources/images
cp -r src/resources/images/* classes/resources/images/
```

**Windows (PowerShell) :**
```powershell
New-Item -ItemType Directory -Force -Path classes\resources\images | Out-Null
Copy-Item -Recurse -Force src\resources\images\* classes\resources\images\
```

### Interface graphique (Swing)
```bash
java -cp classes fr.utt.lo02.jest.view.swing.MainFrame
```

### Terminal
```bash
javac -d classes -sourcepath src src/fr/utt/lo02/jest/controller/Partie.java
java -cp classes fr.utt.lo02.jest.controller.Partie
```

## Règles

Chaque joueur pose 2 cartes (1 visible, 1 cachée). Les joueurs prennent ensuite les cartes des autres pour marquer des points.
