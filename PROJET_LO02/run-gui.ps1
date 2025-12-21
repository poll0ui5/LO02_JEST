# Script de lancement de l'interface graphique Jest pour Windows
# Utilisation: .\run-gui.ps1

$JAVAFX_PATH = ".\lib\javafx-sdk-21.0.1\lib"

# Vérifier si JavaFX est installé
if (-not (Test-Path $JAVAFX_PATH)) {
    Write-Host "JavaFX non trouvé dans $JAVAFX_PATH" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Pour installer JavaFX:" -ForegroundColor Cyan
    Write-Host "1. Téléchargez JavaFX SDK depuis: https://gluonhq.com/products/javafx/" -ForegroundColor White
    Write-Host "2. Extrayez le fichier dans .\lib\" -ForegroundColor White
    Write-Host "3. Assurez-vous que le chemin soit: .\lib\javafx-sdk-21.0.1\lib" -ForegroundColor White
    Write-Host ""
    Write-Host "Ou utilisez cette commande pour télécharger automatiquement:" -ForegroundColor Cyan
    Write-Host 'Invoke-WebRequest -Uri "https://download2.gluonhq.com/openjfx/21.0.1/openjfx-21.0.1_windows-x64_bin-sdk.zip" -OutFile ".\lib\javafx-sdk.zip"' -ForegroundColor White
    Write-Host 'Expand-Archive -Path ".\lib\javafx-sdk.zip" -DestinationPath ".\lib\" -Force' -ForegroundColor White
    exit 1
}

# Compiler si nécessaire
if (-not (Test-Path "bin\fr\utt\lo02\jest\view\gui\JestApp.class")) {
    Write-Host "Compilation de l'interface graphique..." -ForegroundColor Yellow
    
    # Créer le dossier bin s'il n'existe pas
    if (-not (Test-Path "bin")) {
        New-Item -ItemType Directory -Path "bin" | Out-Null
    }
    
    # Compiler tous les fichiers Java
    javac --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.graphics `
          -encoding UTF-8 -d bin `
          src/fr/utt/lo02/jest/model/*.java `
          src/fr/utt/lo02/jest/strategy/*.java `
          src/fr/utt/lo02/jest/visitor/*.java `
          src/fr/utt/lo02/jest/variante/*.java `
          src/fr/utt/lo02/jest/extension/*.java `
          src/fr/utt/lo02/jest/sauvegarde/*.java `
          src/fr/utt/lo02/jest/view/*.java `
          src/fr/utt/lo02/jest/view/gui/*.java `
          src/fr/utt/lo02/jest/controller/*.java `
          src/fr/utt/lo02/jest/test/*.java
    
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Erreur de compilation!" -ForegroundColor Red
        exit 1
    }
    
    # Copier les ressources si elles existent
    if (Test-Path "src\resources") {
        Copy-Item -Path "src\resources" -Destination "bin\" -Recurse -Force
    }
    
    Write-Host "Compilation réussie!" -ForegroundColor Green
}

# Lancer l'application
Write-Host "Lancement de Jest (Interface Graphique)..." -ForegroundColor Cyan
java --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.graphics `
     -cp bin fr.utt.lo02.jest.view.gui.JestApp
