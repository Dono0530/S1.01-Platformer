## 📖 Description
Un petit jeu de plateforme en 2D basé sur des niveaux définis dans des fichiers textes.  
Le joueur incarne un personnage capable de se déplacer horizontalement, de grimper à des échelles et de trouver la sortie pour terminer chaque niveau.  

Le jeu propose un ensemble de sprites de **52x52 pixels**, avec la possibilité de choisir un thème graphique (par défaut ou personnalisé).  

---

## 🕹️ Fonctionnalités principales
- Déplacements dynamiques du personnage :
  - `→` : avancer d’une case vers la droite (orientation droite).
  - `←` : changer l’orientation vers la gauche sans avancer.
  - Monter et descendre les échelles lorsqu’elles sont accessibles.
- Gestion des niveaux via des fichiers `.data`.
- Séparation en couches pour l’affichage (terrain et personnage).
- Bouton permettant de **recommencer le niveau**.
- Possibilité de **changer de thème graphique**.

---

## 📂 Structure des niveaux
Chaque niveau est défini dans un fichier texte nommé :  niveau_XX.data où `XX` est le numéro du niveau sur deux chiffres.  

### Légende des symboles :
- `=` → sol  
- `#` → échelle  
- `+` → position initiale du joueur  
- `@` → sortie  

⚠️ La position du joueur (`+`) ne sert qu’au démarrage : le personnage est ensuite géré séparément.  

---

## 🚀 Objectif
Atteindre la sortie `@` en utilisant les déplacements et les échelles pour franchir les obstacles.  

---

## 📸 Exemple d’affichage
<img width="1260" height="560" alt="{D165A37B-C7E0-4C67-B5F0-A9475AED199B}" src="https://github.com/user-attachments/assets/64a988b2-4c0a-441a-84df-850931fd0087" />

<img width="1260" height="584" alt="{798EA72E-0DF7-43EA-A681-D6E8EEE0D979}" src="https://github.com/user-attachments/assets/2f35d6ed-7774-4042-957d-7853cd410041" />


---

## 🔄 Relancer un niveau
Un bouton dans l’interface permet de recommencer à tout moment le niveau en cours.  

---

## 🎨 Personnalisation
- Les sprites (52x52) sont facilement remplaçables.  
- L’utilisateur peut choisir un thème graphique depuis l’interface.  

---

