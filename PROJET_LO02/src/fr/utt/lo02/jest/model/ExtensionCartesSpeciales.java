package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Valeur;
import fr.utt.lo02.jest.model.Couleur;
import java.util.ArrayList;
import java.util.List;

/**
 * Extension "Cartes Spéciales" ajoutant des cartes bonus au jeu.
 * <p>
 * Cette extension ajoute 2 cartes spéciales :
 * <ul>
 * <li>Cinq de Pique : Carte très forte (valeur 5)</li>
 * <li>Cinq de Cœur : Carte risquée avec le Joker</li>
 * </ul>
 * </p>
 * 
 * 
 */
public class ExtensionCartesSpeciales implements Extension {
    
    private boolean active;
    private List<Carte> cartes;
    
    public ExtensionCartesSpeciales() {
        this.active = false;
        this.cartes = new ArrayList<>();
        initialiserCartes();
    }
    
    private void initialiserCartes() {
        // Note: On réutilise QUATRE comme valeur max existante
        // Dans une vraie extension, on ajouterait CINQ à l'enum Valeur
        // Pour simplifier, on crée des cartes "bonus" avec les valeurs existantes
        // mais avec un effet spécial géré dans le VisitorScore
        
        // Carte bonus : As de Pique supplémentaire (très convoité)
        cartes.add(new CarteExtension(Valeur.AS, Couleur.PIQUE, "As Doré", 
            "Vaut 6 points au lieu de 5 s'il est seul de sa couleur"));
        
        // Carte bonus : Quatre de Cœur supplémentaire
        cartes.add(new CarteExtension(Valeur.QUATRE, Couleur.COEUR, "Cœur Maudit",
            "Vaut -4 si vous avez le Joker, +4 sinon"));
    }

    @Override
    public String getNom() {
        return "Cartes Spéciales";
    }

    @Override
    public String getDescription() {
        return "Ajoute 2 cartes bonus avec des effets spéciaux : As Doré et Cœur Maudit.";
    }

    @Override
    public List<Carte> getCartesExtension() {
        return cartes;
    }

    @Override
    public boolean estActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
