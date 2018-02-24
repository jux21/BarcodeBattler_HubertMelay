# Projet Barcode Battler Android MIAGE M2 MBDS par Cécile Melay et Julien HUBERT.

Le projet demande la permission Caméra au premier lancement.

On entre son pseudonyme puis on peut choisir de : 
- Capturer des créatures ou des équipements via le lecteur de code barre
- Parcourir la liste des créatures capturées et en sélectionner une
- Parcourir la liste des équipements capturés et en sélectionner un
- Lancer un combat entre 2 créatures capturées
Il est affiché le nombre de créatures et d'équipements que possède le joueur.

Capture de d'entités :
La fonction createEntity gère les codes barres EAN, EAN_8, EAN_13 et UPC_A pour la création de créatures.
Les codes barres de pharmacie CODE_39 et CODE_93 crées des potions et le CODE_128 du secteur du transport génère un équipement.
Le joueur pourra lorsqu'il aura compris quel type de code barre donne quelle entité rechercher ceux dont il a besoin pour compléter son deck.
Les entités ont des noms tirés aléatoirements dans des List, les créatures ont des noms composés depuis 2 List différentes.
Les caractéristiques des entités sont affectées en fonction des chiffres extraits du code barre.
Une photo est affecté à l'entité en fonction de son type (eau, air, feu, roche ...)

Listes des créatures : 
Une Listview présente la liste des créatures capturées par le joueur. On y retrouve la photo de la créature et quelques informations à son propos : point de vie, force, défense, vitesse, type, % de victoire)
On peut cliquer sur une des créature de la ListView pour accéder à l'ensemble des ses informations. Il est possible de relacher la créature dans la nature en cliquant sur le bouton "Libérer".

Listes des équipements : 
Une Listview présente la liste des équipement capturés par le joueur. On y retrouve quelques informations à son propos : type et bonus procuré.
TODO : libérer un équipement

Combat en local :
On choisi les 2 créatures qui vont combattre puis celle avec la vitesse la plus élevée aura le droit d'engager le combat. Une barre de vie dynamique reflète le pourcentage de vie restante des créature après chaque attaque.
Le perdant disparait de l'écran et son nombre de défaite est incrémenté. Quant au gagnant son nombre de victoire est incrémenté.
