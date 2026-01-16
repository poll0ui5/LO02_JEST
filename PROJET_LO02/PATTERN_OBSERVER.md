# 🔔 PATTERN OBSERVER/OBSERVABLE - PROJET JEST

## 📐 Architecture Observer/Observable

Le pattern **Observer/Observable** a été implémenté pour permettre une communication efficace entre le **Model** et les **Views** dans l'architecture MVC.

---

## 📂 Structure des Classes

```
model/
├── Observer.java          # Interface pour les observateurs (Views)
├── Observable.java        # Classe de base pour les objets observés (Model)
└── GameModel.java         # Modèle de jeu observable
```

---

## 🎯 Principe de Fonctionnement

### **1. Observable (Sujet)**
- Maintient une liste d'observers
- Notifie automatiquement tous les observers lors d'un changement d'état
- Méthodes : `addObserver()`, `removeObserver()`, `notifyObservers()`

### **2. Observer (Observateur)**
- Interface implémentée par les Views
- Méthode `update()` appelée lors des notifications
- Reçoit l'objet Observable et des données optionnelles

### **3. GameModel**
- Hérite d'Observable
- Encapsule l'état complet du jeu
- Notifie les observers à chaque changement

---

## 💻 Exemple d'Utilisation

### **Étape 1 : Créer le Model Observable**

```java
GameModel model = new GameModel();
```

### **Étape 2 : Créer une View Observer**

```java
public class GamePanel extends JPanel implements Observer {
    
    private GameModel model;
    
    public GamePanel(GameModel model) {
        this.model = model;
        model.addObserver(this);  // S'abonner aux notifications
    }
    
    @Override
    public void update(Observable observable, Object data) {
        // Réagir aux changements du modèle
        if (data != null) {
            String event = (String) data;
            switch (event) {
                case "JOUEURS_UPDATED":
                    afficherJoueurs();
                    break;
                case "OFFRES_UPDATED":
                    afficherOffres();
                    break;
                case "MANCHE_UPDATED":
                    afficherManche();
                    break;
                case "MESSAGE":
                    afficherMessage(model.getMessageActuel());
                    break;
            }
        }
        repaint();  // Redessiner l'interface
    }
}
```

### **Étape 3 : Modifier le Model**

```java
// Dans le Controller
model.setJoueurs(listeJoueurs);        // Notifie automatiquement
model.setNumeroManche(2);              // Notifie automatiquement
model.setMessage("Tour du joueur 1");  // Notifie automatiquement
```

---

## 🔄 Flux de Communication

```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│             │         │              │         │             │
│  Controller │────────▶│  GameModel   │────────▶│    View     │
│             │ modifie │ (Observable) │ notifie │ (Observer)  │
│             │         │              │         │             │
└─────────────┘         └──────────────┘         └─────────────┘
                              │                         │
                              │                         │
                              └─────────────────────────┘
                                   update() appelé
```

---

## 📋 Types d'Événements

Le `GameModel` notifie les observers avec différents types d'événements :

| Événement | Description |
|-----------|-------------|
| `JOUEURS_UPDATED` | Liste des joueurs modifiée |
| `PIOCHE_UPDATED` | Pioche modifiée |
| `TROPHEES_UPDATED` | Trophées modifiés |
| `VARIANTE_UPDATED` | Variante changée |
| `EXTENSION_UPDATED` | Extension changée |
| `MANCHE_UPDATED` | Numéro de manche changé |
| `OFFRES_UPDATED` | Offres des joueurs modifiées |
| `TOUR_TERMINE` | Tour de jeu terminé |
| `PARTIE_TERMINEE` | Partie terminée |
| `MESSAGE` | Nouveau message à afficher |

---

## ✅ Avantages du Pattern Observer

### **1. Découplage**
- Le Model ne connaît pas les Views
- Les Views ne connaissent pas les autres Views
- Communication via interface abstraite

### **2. Synchronisation Automatique**
- Toutes les Views sont mises à jour automatiquement
- Pas besoin d'appeler manuellement les méthodes de rafraîchissement
- Cohérence garantie entre les vues

### **3. Extensibilité**
- Facile d'ajouter de nouvelles Views
- Pas besoin de modifier le Model
- Respect du principe Open/Closed

### **4. Architecture MVC Pure**
- Respect strict de la séparation Model/View
- Le Controller orchestre, le Model notifie
- Les Views réagissent aux changements

---

## 🎓 Pour la Soutenance

**Question** : "Expliquez le pattern Observer dans votre projet"

**Réponse** : "J'ai implémenté le pattern Observer/Observable pour gérer la communication entre le Model et les Views. Le `GameModel` hérite d'`Observable` et maintient l'état du jeu. Les Views (comme `GamePanel`) implémentent `Observer` et s'abonnent au modèle. Quand le Controller modifie le modèle (par exemple `model.setJoueurs()`), le modèle notifie automatiquement toutes les vues enregistrées via la méthode `update()`. Cela garantit que toutes les interfaces (Terminal et Swing) restent synchronisées sans couplage fort."

---

## 📝 Exemple Complet

```java
// Dans le Controller
public class SwingController {
    private GameModel model;
    private MainFrame view;
    
    public SwingController(MainFrame view) {
        this.model = new GameModel();
        this.view = view;
        
        // Enregistrer les panels comme observers
        view.getGamePanel().setModel(model);
        view.getMenuPanel().setModel(model);
    }
    
    public void demarrerPartie() {
        // Initialiser les joueurs
        ArrayList<Joueur> joueurs = new ArrayList<>();
        joueurs.add(new JoueurHumain("Alice"));
        joueurs.add(new JoueurVirtuel("Bot1"));
        
        // Modifier le modèle → notifie automatiquement les views
        model.setJoueurs(joueurs);
        model.setVariante(new VarianteClassique());
        model.setNumeroManche(1);
        model.setMessage("Partie démarrée !");
    }
    
    public void jouerTour() {
        // Logique du tour...
        
        // Notifier les changements
        model.notifyOffresChanged();
        model.setMessage("Tour terminé");
    }
}
```

---

**Implémentation** : 16 janvier 2026  
**Auteurs** : Moss'Ab Mirande-Ney, Paul-Louis Ledoux  
**Version** : 2.0
