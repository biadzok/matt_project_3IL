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
    this.plateau = new ObjetPlateau[tailleY][tailleX];

    for (int i = 0; i < tailleY; i++) {
      for (int j = 0; j < tailleX; j++) {
        ObjetPlateau temp = ObjetPlateau.depuisCaractere(lignes[i + 2].charAt(j));
        plateau[i][j] = temp;
        if (temp.afficher() == 'H') {
          joueurX = j;
          joueurY = i;
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

  // TODO : patron visiteur du Rocher...
  public void etatSuivantVisiteur(Rocher r, int x, int y) {

  }

  /**
   * Calcule l'état suivant du niveau.
   * ........
   * 
   * @author
   */
  public void etatSuivant() {
    // TODO
  }

  // Illustrez les Javadocs manquantes lorsque vous coderez ces méthodes !

  public boolean enCours() {
    // temp anti error
    return false;
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
        deltaX++;
        break;
      case ANNULER:
        break;
      case QUITTER:
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
    if (y < 0 || y >= plateau.length || x < 0 || x >= plateau[0].length)
      res = false;
    else {
      res = plateau[y][x].estMarchable();
    }
    return res;
  }

  public void deplacer(int deltaX, int deltaY) {
    plateau[joueurY + deltaY][joueurX + deltaX] = plateau[joueurY][joueurX];
    ObjetPlateau temp = new Vide();
    plateau[joueurY][joueurX] = temp;
    joueurX = joueurX + deltaX;
    joueurY = joueurY + deltaY;
  }

  /**
   * Affiche l'état final (gagné ou perdu) une fois le jeu terminé.
   */
  public void afficherEtatFinal() {
  }

  /**
   */
  public boolean estIntermediaire() {
    // temp anti error
    return false;
  }
}
