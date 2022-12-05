package fr.rodez3il.a2022.mrmatt.sources.objets;

import fr.rodez3il.a2022.mrmatt.sources.Niveau;

public class Rocher extends ObjetPlateau {

  public EtatRocher etat = EtatRocher.FIXE;

  /**
   * renvoie le caractère correspondant à l'objet
   */
  @Override
  public char afficher() {
    return '*';
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
    return false;
  }

  /**
   * renvoie si l'objet est poussable
   */
  public boolean estPoussable() {
    return true;
  }

  /**
   * renvoie si l'objet est glissant
   */
  public boolean estGlissant() {
    return true;
  }

  /**
   * implémente le patron Visiteur pour calculer l'état suivant du niveau
   * cours.
   */
  @Override
  public void visiterPlateauCalculEtatSuivant(Niveau plateau, int x, int y) {
    plateau.etatSuivantVisiteur(this, x, y);
  }
}