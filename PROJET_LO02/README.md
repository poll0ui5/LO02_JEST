# JEST

Jeu de cartes en Java.

## Lancer

```bash
cd PROJET_LO02
```

**Interface graphique :**
```bash
java --module-path "lib/javafx-sdk-25.0.1/lib" --add-modules javafx.controls,javafx.graphics,javafx.fxml -cp bin fr.utt.lo02.jest.view.gui.JestApp
```

**Terminal :**
```bash
java -cp bin fr.utt.lo02.jest.controller.Partie
```

**Si pas compilé :**
```bash
javac --module-path "lib/javafx-sdk-25.0.1/lib" --add-modules javafx.controls,javafx.graphics,javafx.fxml -encoding UTF-8 -d bin src/fr/utt/lo02/jest/*/*.java src/fr/utt/lo02/jest/*/*/*.java
```

## Règles

Chaque joueur pose 2 cartes (1 visible, 1 cachée). Les joueurs prennent ensuite les cartes des autres pour marquer des points.
