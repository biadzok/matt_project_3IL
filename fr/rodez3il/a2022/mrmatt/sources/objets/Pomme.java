package fr.rodez3il.a2022.mrmatt.sources.objets;

import fr.rodez3il.a2022.mrmatt.sources.Niveau;

public class Pomme extends ObjetPlateau {

  /**
   * renvoie le caractère correspondant à l'objet
   */
  @Override
  public char afficher() {
    return '+';
  }

  /**
   * renvoie si l'objet est vide
   */
  public boolean estVide() {
    return false;
  }

  /**
   * renvoie si l'objet est marchable
   */
  public boolean estMarchable() {
    return true;
  }

  /**
   * renvoie si l'objet est poussable
   */
  public boolean estPoussable() {
    return false;
  }

  /**
   * renvoie si l'objet est glissant
   */
  public boolean estGlissant() {
    return false;
  }

  /**
   * implémente le patron Visiteur pour calculer l'état suivant du niveau en
   * cours.
   * 
   * @param niveau le niveau, x et y les coordonnées de la pomme
   */
  public void visiterPlateauCalculEtatSuivant(Niveau niveau, int x, int y) {
    plateau.etatSuivantVisiteur(this, x, y);
  }
}