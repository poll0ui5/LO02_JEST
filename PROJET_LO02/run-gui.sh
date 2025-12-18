#!/bin/bash
# Script de lancement de l'interface graphique Jest

JAVAFX_PATH="/tmp/javafx-sdk-21.0.1/lib"

# Vérifier si JavaFX est installé
if [ ! -d "$JAVAFX_PATH" ]; then
    echo "JavaFX non trouvé. Téléchargement..."
    curl -L -o /tmp/javafx-sdk.zip "https://download2.gluonhq.com/openjfx/21.0.1/openjfx-21.0.1_osx-aarch64_bin-sdk.zip"
    unzip -o /tmp/javafx-sdk.zip -d /tmp/
fi

# Compiler si nécessaire
if [ ! -f "classes/fr/utt/lo02/jest/view/gui/JestApp.class" ]; then
    echo "Compilation..."
    mkdir -p classes
    javac -d classes -cp "$JAVAFX_PATH/*" -sourcepath src src/fr/utt/lo02/jest/view/gui/JestApp.java
    cp -r src/resources classes/
fi

# Lancer l'application
echo "Lancement de Jest..."
java --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.graphics -cp classes fr.utt.lo02.jest.view.gui.JestApp
