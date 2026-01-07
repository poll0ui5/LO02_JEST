# 📖 RÈGLES DU JEU JEST

## 🎯 Objectif du jeu

**Jest** est un jeu de cartes stratégique où vous devez collecter des cartes pour marquer des points. Le but est d'avoir le **meilleur score** en fin de partie en remportant des **trophées** et en collectant les bonnes cartes dans votre **Jest** (votre collection personnelle).

---

## 🃏 Composition du jeu

### Cartes de base (17 cartes)
- **4 couleurs** : ♠️ Pique, ♣️ Trèfle, ♦️ Carreau, ♥️ Cœur
- **4 valeurs** par couleur : 1, 2, 3, 4
- **1 Joker** (carte spéciale)

### Hiérarchie des cartes
1. **Valeur** : 4 > 3 > 2 > 1
2. **Couleur** (en cas d'égalité) : ♠️ Pique > ♣️ Trèfle > ♦️ Carreau > ♥️ Cœur

---

## 🎮 Déroulement d'une partie

### 1️⃣ Configuration initiale

**Nombre de joueurs** : 3 ou 4 joueurs (humains ou bots)

**Distribution des trophées** :
- **3 joueurs** : 2 trophées sont placés face visible au centre
- **4 joueurs** : 1 trophée est placé face visible au centre

Les trophées sont des cartes tirées de la pioche qui détermineront les objectifs de la partie.

### 2️⃣ Déroulement d'un tour (manche)

Chaque tour se déroule en **5 étapes** :

#### Étape 1 : Distribution
- Chaque joueur reçoit **2 cartes** de la pioche

#### Étape 2 : Création des offres
- Chaque joueur doit créer une **offre** avec ses 2 cartes :
  - **1 carte face visible** (tout le monde la voit)
  - **1 carte face cachée** (personne ne la voit)

#### Étape 3 : Détermination de l'ordre de jeu
- Le joueur avec la **meilleure carte visible** commence
- En cas d'égalité sur la valeur, c'est la couleur qui départage

#### Étape 4 : Phase de prise
Le joueur actif :
1. **Choisit un adversaire**
2. **Prend une carte** dans l'offre de cet adversaire (visible ou cachée)
3. **Ajoute cette carte à son Jest** (sa collection)

#### Étape 5 : Changement de joueur actif
- Le joueur dont on a pris une carte devient le **joueur actif**
- **SAUF** s'il a déjà joué ce tour
- Dans ce cas, on passe au joueur avec la meilleure offre parmi ceux qui n'ont pas encore joué

Le tour se termine quand **tous les joueurs ont joué**.

### 3️⃣ Fin de partie

La partie se termine quand :
- **La pioche est vide** (plus assez de cartes pour distribuer)
- Tous les joueurs ont joué leur dernier tour

---

## 🏆 Calcul des scores

### Attribution des trophées

Pour chaque trophée, on compte qui possède le plus de cartes correspondantes :

- **Si le trophée est un 2 de Pique** → Le joueur avec le plus de **2** dans son Jest remporte le trophée
- **Si le trophée est un Joker** → Le joueur avec le plus de **Cœurs** dans son Jest remporte le trophée

Le trophée est alors ajouté au Jest du gagnant.

### Calcul du score final

Le score est calculé selon les règles suivantes :

1. **Cartes de la couleur majoritaire** : +1 point par carte
2. **Cartes des autres couleurs** : -1 point par carte
3. **Joker** : +2 points
4. **Bonus/Malus** selon la variante choisie

Le joueur avec le **score le plus élevé** gagne la partie !

---

## 🎲 Les 3 modes de jeu (Variantes)

### 1. Mode Classique ⭐

**Règles standard** :
- 2 trophées pour 3 joueurs
- 1 trophée pour 4 joueurs
- 2 cartes distribuées par tour
- Offres simultanées
- Pas de règles spéciales

**Idéal pour** : Découvrir le jeu et comprendre les mécaniques de base.

---

### 2. Mode Sans Trophée 🚫

**Modifications** :
- **Aucun trophée** n'est distribué
- **Toutes les 17 cartes** sont jouées
- Le score dépend **uniquement** des cartes collectées

**Stratégie** :
- Plus de tours de jeu
- Accent sur la collecte de cartes d'une même couleur
- Moins de hasard, plus de contrôle

**Idéal pour** : Les joueurs qui veulent un jeu plus long et plus stratégique.

---

### 3. Mode Double Mise 💰

**Modifications** :
- **3 trophées** pour tous (plus de cartes en jeu)
- **Bonus de fin** : +3 points pour le joueur avec le plus de ♠️ Piques
- **Malus de fin** : -3 points pour le joueur avec le plus de ♦️ Carreaux

**Stratégie** :
- Collecter des Piques pour le bonus
- Éviter les Carreaux pour ne pas avoir le malus
- Plus de trophées = plus d'opportunités
- Risque/récompense plus élevé

**Idéal pour** : Les joueurs expérimentés qui veulent plus de rebondissements.

---

## 🤖 Stratégies des Bots

Le jeu propose 2 types de bots avec des stratégies différentes :

### Bot Offensif ⚔️
- **Offre** : Montre sa **meilleure carte** pour jouer en premier
- **Cible** : Attaque le joueur avec la **meilleure offre visible**
- **Prise** : Prend la **carte visible** (plus prévisible mais souvent meilleure)

### Bot Défensif 🛡️
- **Offre** : Montre sa **plus faible carte** pour passer inaperçu
- **Cible** : Attaque le joueur avec la **pire offre visible**
- **Prise** : Prend la **carte cachée** (risqué mais peut surprendre)

---

## 💡 Conseils stratégiques

### Pour bien jouer :

1. **Observez les trophées** : Adaptez votre stratégie en fonction des trophées visibles
2. **Gérez votre offre** : Décidez si vous voulez jouer en premier (carte haute visible) ou en dernier (carte basse visible)
3. **Anticipez** : Essayez de deviner ce que cachent vos adversaires
4. **Couleur majoritaire** : Concentrez-vous sur une couleur pour maximiser vos points
5. **Timing** : Parfois, il vaut mieux prendre une carte cachée pour surprendre

### Erreurs à éviter :

- ❌ Collectionner trop de couleurs différentes (malus)
- ❌ Ignorer les trophées (ils rapportent beaucoup de points)
- ❌ Toujours prendre la carte visible (trop prévisible)
- ❌ Ne pas adapter sa stratégie selon la variante

---

## 🏗️ Architecture technique (Patterns utilisés)

Le projet utilise plusieurs **design patterns** :

### 1. **MVC (Model-View-Controller)**
- `Partie` = Contrôleur principal
- `Terminal` / `Swing` = Vues
- `Joueur`, `Carte`, etc. = Modèles

### 2. **Strategy Pattern**
- `Strategie` interface
- `StrategieOffensive` et `StrategieDefensive` implémentations
- Permet de changer le comportement des bots dynamiquement

### 3. **Visitor Pattern**
- `VisitorScore` pour calculer les scores
- Sépare l'algorithme de calcul des objets

### 4. **Template Method**
- `Joueur` classe abstraite
- `JoueurHumain` et `JoueurVirtuel` implémentations concrètes

---

## 📊 Exemple de partie

### Configuration
- 3 joueurs : Alice (humain), Bot1 (offensif), Bot2 (défensif)
- Variante : Classique
- Trophées : 3 de Pique, Joker

### Tour 1
1. Distribution : Chacun reçoit 2 cartes
2. Offres :
   - Alice : 4♠️ visible, ?♥️ cachée
   - Bot1 : 3♣️ visible, ?♦️ cachée
   - Bot2 : 1♦️ visible, ?♠️ cachée
3. Alice commence (meilleure carte visible)
4. Alice prend la carte cachée de Bot1 → 2♦️
5. Bot1 prend la carte visible de Bot2 → 1♦️
6. Bot2 prend la carte cachée d'Alice → 2♥️

### Fin de partie
- Alice : 5 cartes (3♠️, 2♠️, 4♠️, 1♠️, Joker) = Majorité Pique
- Score Alice : +5 (Piques) +2 (Joker) = **7 points**
- Alice remporte le trophée 3♠️ et le Joker
- **Alice gagne !**

---

## 🎓 Résumé rapide

1. **But** : Avoir le meilleur score en collectant des cartes
2. **Tours** : Distribution → Offres → Prises → Changement de joueur
3. **Trophées** : Remportés par majorité de valeur/couleur
4. **Score** : Couleur majoritaire = +1, autres = -1, Joker = +2
5. **3 variantes** : Classique, Sans Trophée, Double Mise
6. **Stratégie** : Observer, anticiper, adapter

**Bonne partie !** 🎉
