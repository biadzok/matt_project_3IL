package fr.rodez3il.a2022.mrmatt.sources;

import fr.rodez3il.a2022.mrmatt.sources.*;
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

  private void Niveau(String chemin) {
    String fichier = Utils.lireFichier(chemin);
    String[] lignes = fichier.split("\n");
    
    int tailleX = Integer.parseInt(lignes[0]);
    int tailleY = Integer.parseInt(lignes[1]);
    pommesRestantes = 0;
    totalMouvements = 0;
    this.plateau = new ObjetPlateau[tailleX][tailleY];
    
    for(int i = 0; i < tailleX; i++) {
      for (int j = 0; j < tailleY; j++) {
        ObjetPlateau temp = depuisCharactere(lignes[i + 2].charAt(j));
        plateau[i][j] = temp;
        if (temp == 'H') {
          joueurX = i;
          joueurY = j;
        }
        if (temp == '+') {
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
      stringbuilder.append('\n');
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
  }

  // Joue la commande C passée en paramètres
  public boolean jouer(Commande c) {
  }

  /**
   * Affiche l'état final (gagné ou perdu) une fois le jeu terminé.
   */
  public void afficherEtatFinal() {
  }

  /**
   */
  public boolean estIntermediaire() {
  }

  // Code pour empêcher la compilation

}
