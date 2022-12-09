package fr.rodez3il.a2022.mrmatt.sources;

import fr.rodez3il.a2022.mrmatt.sources.Utils;
import fr.rodez3il.a2022.mrmatt.sources.objets.*;

public class Niveau {

  // Les objets sur le plateau du niveau
  private ObjetPlateau[][] plateau;

  // Position du joueur
  private int joueurX;
  private int joueurY;

  // copie du tableau et anciennes coordonnées du joueur
  // pour pouvoir annuler le dernier mouvement
  private ObjetPlateau[][] copiePlateau;
  private int oldJoueurX;
  private int oldJoueurY;

  private int pommesRestantes;
  private int totalMouvements;

  // booléens déterminant l'état du jeu
  private boolean partieFinie;
  private boolean boolIntermediaire;

  // Autres attributs que vous jugerez nécessaires...

  /**
   * Constructeur public : crée un niveau depuis un fichier.
   * 
   * @param chemin le chemin d'accès au fichier texte contenant le niveau
   * @author Marin Bailhe
   */

  public Niveau(String chemin) {
    String fichier = Utils.lireFichier(chemin);
    String[] lignes = fichier.split("\n");
    int tailleX = Integer.parseInt(lignes[0]);
    int tailleY = Integer.parseInt(lignes[1]);

    // assignation des attributs du niveau
    pommesRestantes = 0;
    totalMouvements = 0;
    partieFinie = false;
    boolIntermediaire = false;
    copiePlateau = null;
    this.plateau = new ObjetPlateau[tailleY][tailleX];

    // assignation des cases du tableau
    for (int i = 0; i < tailleY; i++) {
      for (int j = 0; j < tailleX; j++) {
        ObjetPlateau temp = ObjetPlateau.depuisCaractere(lignes[i + 2].charAt(j));
        plateau[i][j] = temp;
        if (temp.afficher() == 'H') {
          joueurX = i;
          joueurY = j;
        }
        if (temp.afficher() == '+') {
          pommesRestantes++;
        }
      }
    }
  }

  /**
   * échange les objets source et destination
   */
  private void echanger(int sourceX, int sourceY, int destinationX, int destinationY) {
    // potentielle inversion x / y
    ObjetPlateau temp = plateau[sourceX][sourceY];
    plateau[sourceX][sourceY] = plateau[destinationX][destinationY];
    plateau[destinationX][destinationY] = temp;
  }

  /**
   * Produit une sortie du niveau sur la sortie standard.
   *
   */
  public void afficher() {
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < plateau.length; i++) {
      for (int j = 0; j < plateau[0].length; j++) {
        stringBuilder.append(plateau[i][j].afficher());
      }
      stringBuilder.append('\n');
    }

    System.out.println(stringBuilder.toString());
    System.out.println("pommes restantes : " + pommesRestantes);
    System.out.println("total de déplacements : " + totalMouvements);
  }

  /**
   * retour du patron visiteur lors de la visite d'un rocher
   * 
   * @author Marin Bailhe
   */
  public void etatSuivantVisiteur(Rocher r, int x, int y) {

    // mise en état de chute du rocher si les conditions sont remplies
    if (r.etat == EtatRocher.FIXE && x < plateau.length - 1 && plateau[x + 1][y].estVide()) {
      r.etat = EtatRocher.CHUTE;
    }

    if (r.etat == EtatRocher.CHUTE) {
      // si le rocher est tout en bas du terrain, il devient fixe
      if (x == plateau.length - 1) {
        r.etat = EtatRocher.FIXE;
      } else {
        // on échange directement s'il y a simplement du vide sous le rocher
        if (plateau[x + 1][y].estVide()) {
          echanger(x, y, x + 1, y);
        } else {
          // si le joueur est sous le rocher, la partie est finie !
          if (joueurX == x + 1 && joueurY == y) {
            partieFinie = true;
            r.etat = EtatRocher.FIXE;
          } else {
            // gestion des cas lorsqu'on est sur un autre rocher
            if (plateau[x + 1][y].estGlissant()) {
              // glissement gauche du rocher
              if ((plateau[x][y - 1].estVide())
                  && (plateau[x + 1][y - 1].estVide() || (joueurX == x + 1 && joueurY == y - 1))) {
                echanger(x, y, x, y - 1);
              } else {
                // glissement droit du rocher
                if ((plateau[x][y + 1].estVide())
                    && (plateau[x + 1][y + 1].estVide() || (joueurX == x + 1 && joueurY == y + 1)))
                  echanger(x, y, x, y + 1);
                else // lorsque le rocher ne peut pas tomber ou que ce soit
                  r.etat = EtatRocher.FIXE;
              }
            } else // lorsque le rocher tombe sur un objet non glissant
              r.etat = EtatRocher.FIXE;
          }
        }
      }
    }

    // le jeu continue de bouger les rochers si au moins l'un d'entre eux
    // est en état de chute
    if (r.etat == EtatRocher.CHUTE)
      boolIntermediaire = true;
  }

  /**
   * retour du patron visiteur permettant de compter les pommes;
   * 
   * @author Marin Bailhe
   */
  public void etatSuivantVisiteur(Pomme p, int x, int y) {
    pommesRestantes++;
  }

  /**
   * Calcule l'état suivant du niveau.
   * 
   * @author Marin Bailhe, Philippe Roussille
   */
  public void etatSuivant() {
    // le jeu ne continue pas de jouer tant qu'on n'a pas trouvé de rocher en chute
    boolIntermediaire = false;
    // remise à zéro des pommes pour le comptage qui va suivre
    pommesRestantes = 0;
    for (int x = plateau.length - 1; x >= 0; x--) {
      for (int y = plateau[x].length - 1; y >= 0; y--) {
        plateau[x][y].visiterPlateauCalculEtatSuivant(this, x, y);
      }
    }
    // si on n'a trouvé aucune pomme sur le plateau
    if (pommesRestantes == 0)
      partieFinie = true;
  }

  /**
   * renvoie si la la partie est finie ou non
   *
   * @authors Marin Bailhe
   */
  public boolean enCours() {
    return (!partieFinie);
  }

  /**
   * change l'état du niveau en fonction de la commande entrée par l'utilisateur.
   *
   * @param c la commande entrée par l'utilisateur
   * @return vrai si un déplacement est possible, faux sinon
   * @authors Marin Bailhe
   */
  public boolean jouer(Commande c) {
    boolean estDeplacable = false;
    int deltaX = 0;
    int deltaY = 0;
    switch (c) {
      case HAUT:
        estDeplacable = deplacementPossible(joueurX - 1, joueurY);
        deltaX--;
        break;
      case GAUCHE:
        estDeplacable = deplacementPossible(joueurX, joueurY - 1);
        deltaY--;
        break;
      case BAS:
        estDeplacable = deplacementPossible(joueurX + 1, joueurY);
        deltaX++;
        break;
      case DROITE:
        estDeplacable = deplacementPossible(joueurX, joueurY + 1);
        deltaY++;
        break;
      case ANNULER:
        if (copiePlateau != null) {
          // récupération et copie des informations du tour précédent
          plateau = Utils.cloneTableau(copiePlateau);
          joueurX = oldJoueurX;
          joueurY = oldJoueurY;
          totalMouvements--;
          // on réinitialise la copie pour ne pas annuler en boucle la même action
          copiePlateau = null;
        }
        break;
      case QUITTER:
        partieFinie = true;
        break;
      default:
    }
    if (estDeplacable) {
      // récupération des information du tableau courant pour les réutiliser
      // s'il y a une annulation du mouvement
      copiePlateau = Utils.cloneTableau(plateau);
      oldJoueurX = joueurX;
      oldJoueurY = joueurY;
      // déplacement du joueur
      deplacer(deltaX, deltaY);
    }
    return estDeplacable;
  }

  /**
   * renvoie si le joueur peut faire le déplacement qu'il a demandé
   *
   * @authors Marin Bailhe
   */
  private boolean deplacementPossible(int x, int y) {
    boolean res = true;
    // cas de sortie des limites du terrain
    if (x < 0 || x >= plateau.length || y < 0 || y >= plateau[0].length)
      res = false;
    else {
      // herbe, vide, pomme
      if (plateau[x][y].estMarchable()) {
        res = true;
      } else {
        // configuration joueur - rocher - vide
        int deltaX = x - joueurX;
        int deltaY = y - joueurY;
        res = (deltaX == 0 && plateau[x][y].estPoussable() && plateau[x][y + deltaY].estVide());
      }
    }
    return res;
  }

  /**
   * déplace le joueur à la position demandée
   *
   * @authors Marin Bailhe
   */
  public void deplacer(int deltaX, int deltaY) {
    // cas d'une configuration joueur - rocher - vide
    if (plateau[joueurX + deltaX][joueurY + deltaY].estPoussable()) {
      echanger(joueurX + deltaX, joueurY + deltaY, joueurX + deltaX * 2, joueurY + deltaY * 2);
      echanger(joueurX + deltaX, joueurY + deltaY, joueurX, joueurY);
    } else {
      // autres cas
      plateau[joueurX + deltaX][joueurY + deltaY] = plateau[joueurX][joueurY];
      ObjetPlateau temp = new Vide();
      plateau[joueurX][joueurY] = temp;
    }
    joueurX = joueurX + deltaX;
    joueurY = joueurY + deltaY;
    totalMouvements++;
  }

  /**
   * Affiche l'état final (gagné ou perdu) une fois le jeu terminé
   * avec son message de fin
   *
   * @authors Marin Bailhe
   */
  public void afficherEtatFinal() {
    if (pommesRestantes == 0)
      System.out.println("Bravo vous avez fini le niveau en " + totalMouvements
          + " déplacements !\nEtait-ce le mieux que vous pouvez faire ?");
    else {
      if (pommesRestantes > 0)
        System.out.println("Mince ! retentez votre chance !");
      else
        System.out.println("dysfonctionnement, merci d'en prévenir le developpeur.");
    }
  }

  /**
   * revoie si le plateau est dans un état intermédiaire ou non
   *
   *
   * @authors Marin Bailhe
   */
  public boolean estIntermediaire() {
    return boolIntermediaire;
  }
}
