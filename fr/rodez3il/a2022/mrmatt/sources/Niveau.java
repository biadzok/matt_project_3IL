package fr.rodez3il.a2022.mrmatt.sources;

import fr.rodez3il.a2022.mrmatt.sources.Utils;
import fr.rodez3il.a2022.mrmatt.sources.objets.*;

public class Niveau {

  // Les objets sur le plateau du niveau
  private ObjetPlateau[][] plateau;
  // Position du joueur
  private int joueurX;
  private int joueurY;
  private int pommesRestantes;
  private int totalMouvements;
  private boolean partieFinie;
  private boolean boolIntermediaire;

  // Autres attributs que vous jugerez nécessaires...

  /**
   * Constructeur public : crée un niveau depuis un fichier.
   * 
   * @param chemin .....
   * @author .............
   */

  public Niveau(String chemin) {
    String fichier = Utils.lireFichier(chemin);
    String[] lignes = fichier.split("\n");

    int tailleX = Integer.parseInt(lignes[0]);
    int tailleY = Integer.parseInt(lignes[1]);
    pommesRestantes = 0;
    totalMouvements = 0;
    partieFinie = false;
    boolIntermediaire = false;
    this.plateau = new ObjetPlateau[tailleY][tailleX];

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
   * ................
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
   */
  public void etatSuivantVisiteur(Rocher r, int x, int y) {
    if (r.etat == EtatRocher.FIXE && x < plateau.length - 1 && plateau[x + 1][y].estVide()) {
      r.etat = EtatRocher.CHUTE;
    }

    if (r.etat == EtatRocher.CHUTE) {
      if (x == plateau.length - 1) {
        r.etat = EtatRocher.FIXE;
      }
      else {
        if (plateau[x + 1][y].estVide()) {
          echanger(x, y, x + 1, y);
        } else {
          // si le joueur est sous le rocher, la partie est finie !
          if (joueurX == x + 1 && joueurY == y) {
            partieFinie = true;
            r.etat = EtatRocher.FIXE;
            System.out.println("position : " + x + " / " + y);
          }
          else {
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
                else
                  r.etat = EtatRocher.FIXE;
              }
            }
            else
              r.etat = EtatRocher.FIXE;
          }
        }
      }
    }

    if (r.etat == EtatRocher.CHUTE)
      boolIntermediaire = true;
  }

  // comptage du nombre de pommes;
  public void etatSuivantVisiteur(Pomme p, int x, int y) {
    pommesRestantes++;
  }

  /**
   * Calcule l'état suivant du niveau.
   * ........
   * 
   * @author Marin Bailhe
   */
  public void etatSuivant() {
    boolIntermediaire = false;
    pommesRestantes = 0;
    for (int x = plateau.length - 1; x >= 0; x--) {
      for (int y = plateau[x].length - 1; y >= 0; y--) {
        plateau[x][y].visiterPlateauCalculEtatSuivant(this, x, y);
      }
    }
  }

  // Illustrez les Javadocs manquantes lorsque vous coderez ces méthodes !

  public boolean enCours() {
    return (!partieFinie);
  }

  // Joue la commande C passée en paramètres
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
        break;
      case QUITTER:
        partieFinie = true;
        break;
      default:
    }
    if (estDeplacable) {
      deplacer(deltaX, deltaY);
    }
    return estDeplacable;
  }

  private boolean deplacementPossible(int x, int y) {
    boolean res = true;
    if (x < 0 || x >= plateau.length || y < 0 || y >= plateau[0].length)
      res = false;
    else {
      res = plateau[x][y].estMarchable();
    }
    return res;
  }

  public void deplacer(int deltaX, int deltaY) {
    plateau[joueurX + deltaX][joueurY + deltaY] = plateau[joueurX][joueurY];
    ObjetPlateau temp = new Vide();
    plateau[joueurX][joueurY] = temp;
    joueurX = joueurX + deltaX;
    joueurY = joueurY + deltaY;
  }

  /**
   * Affiche l'état final (gagné ou perdu) une fois le jeu terminé.
   */
  public void afficherEtatFinal() {
    if (pommesRestantes == 0)
      System.out.println("Bravo vous avez fini le niveau !");
    else {
      if (pommesRestantes > 0)
        System.out.println("Mince ! retentez votre chance !");
      else
        System.out.println("dysfonctionnement, merci d'en prévenir le developpeur.");
    }
  }

  /**
   */
  public boolean estIntermediaire() {
    return boolIntermediaire;
  }
}
