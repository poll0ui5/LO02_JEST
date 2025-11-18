# Modifications apportées au projet Jest - Patterns de conception

## 📝 Fichiers MODIFIÉS

### 1. `Joueur.java`
**Ajouts :**
- `setDerniereCarteJouee(Carte carte)` : Setter protected pour les stratégies
- `accept(Visitor visitor)` : Méthode pour le pattern Visitor
- Documentation Javadoc complète

---

### 2. `Carte.java`
**Ajouts :**
- `accept(Visitor visitor)` : Méthode pour le pattern Visitor
- Documentation Javadoc améliorée

---

### 3. `Trophee.java`
**Transformation complète :**
- Attributs : `carte`, `condition`, `estGagne`, `estVisible`
- Constructeurs : simple et complet
- `verifierCondition(Joueur)` : Vérifie si condition remplie
- `accept(Visitor visitor)` : Méthode pour le pattern Visitor
- Getters/Setters complets
- `toString()` : Affichage formaté

---

## ✨ Fichiers CRÉÉS

### Pattern STRATEGY

#### 1. `Strategie.java` (interface)
- Définit le contrat pour les stratégies de jeu
- Méthode : `void jouer(JoueurVirtuel joueur)`

#### 2. `StrategieDefensive.java`
- Implémente Strategie
- Choisit la carte de **plus faible valeur**
- Comportement conservateur

#### 3. `StrategieOffensive.java`
- Implémente Strategie
- Choisit la carte de **plus forte valeur**
- Comportement agressif

#### 4. `JoueurVirtuel.java`
- Hérite de Joueur
- Attribut : `botStrategie : Strategie`
- Méthode : `setStrategie(Strategie)` pour changer de stratégie
- Délègue le jeu à la stratégie

#### 5. `JoueurHumain.java`
- Hérite de Joueur
- Représente un joueur humain
- Utilise le comportement par défaut

---

### Pattern VISITOR

#### 6. `Visitor.java` (interface)
- Définit le contrat pour les visiteurs
- Méthodes :
  - `void visit(Joueur joueur)`
  - `void visit(Carte carte)`
  - `void visit(Trophee trophee)`

#### 7. `VisitorScore.java`
- Implémente Visitor
- Calcule les scores des joueurs
- Stocke les résultats dans une Map
- Méthodes :
  - `getScore(String nomJoueur)` : Récupère le score d'un joueur
  - `getScoresJoueurs()` : Récupère tous les scores

---

## 🎯 Utilisation

### Pattern Strategy
```java
// Créer un bot avec stratégie offensive
JoueurVirtuel bot = new JoueurVirtuel("Bot1", new StrategieOffensive());

// Changer de stratégie en cours de jeu
bot.setStrategie(new StrategieDefensive());

// Le bot joue selon sa stratégie
bot.jouerCarte();
```

### Pattern Visitor
```java
// Calculer le score d'un joueur
VisitorScore calculateur = new VisitorScore();
joueur.accept(calculateur);
int score = calculateur.getScore(joueur.getNom());
```

---

## 📊 Résumé

- **3 fichiers modifiés** : Joueur, Carte, Trophee
- **7 fichiers créés** : 5 pour Strategy, 2 pour Visitor
- **2 patterns implémentés** : Strategy et Visitor
- **Code documenté** : Javadoc complète sur toutes les classes
