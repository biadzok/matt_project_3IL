### réponse aux questions :

1. La classe ObjetPlateau est abstraite car elle ne contient que des promesses de traitement pour les différents objets du plateau et ne sera jamais utilisée directement en tant que classe.

2. La méthode échanger est privée car on ne veut pas pouvoir déplacer directement des cases du jeu sans qu'il n'y ait de traitement suivant la logique du jeu.

3. Les méthodes deplacementPossible et deplacer sont privées car le niveau doit gérer le déplacement du joueur en fonction de la commande entrée sans avoir à forcer de la logique à ce niveau dans le jeu. Ainsi le jeu peut simplement demander un déplacement et reçevoir l'information que le déplacement a été effectué et qu'il était bien possible.

4. Une implémentation utilisant la fonction instanceOf pour vérifier le type d'un objet depuis l'extérieur de l'objet va a l'encontre du paradigme de Liskov qui se doit d'être respecté en Java.

5. Du point de vue du niveau, les états enCours, etatIntermediaire et etatSuivant sont des informations à enregistrer tandis que du point de vue du jeu, ce sont des états du niveau qu'il faut gérer correctement. C'est donc au jeu d'implémenter la logique de gestion de ces états, il faut donc que ces méthodes soient publiques.


### Ressenti sur le projet

Le projet m'a entièrement permis de comprendre et mettre en application à la fois les raisons derrière l'utilisation du polymorphysme et des patrons visiteurs.
Ce n'était pas trop long et j'ai pu prendre le temps pour faire le sujet à mon rythme, et suffisament bien expliqué pour que je puisse facilement comprendre pourquoi certains outils du java sont utilisés.
J'aurais aimé un peu plus d'explications sur l'utilisation des fonctions de test et comment/pourquoi les utiliser (un exemple d'utilisation), car le sujet est très directif ce qui pousse à simplement coder tout en vérifiant que le code compile sans trop se soucier de l'execution du code qui suit avant tard dans le sujet.
Merci de votre choix de date de rendu du projet, pour un procrastinateur comme moi la date est parfaite.