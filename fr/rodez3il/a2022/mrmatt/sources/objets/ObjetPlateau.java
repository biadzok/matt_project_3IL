package fr.rodez3il.a2022.mrmatt.sources.objets;

import fr.rodez3il.a2022.mrmatt.sources.Niveau;

public abstract class ObjetPlateau {
  /**
   * Fabrique des objets
   * 
   * @param chr le symbole à produire
   * @return la classe ObjetPlateau correspondante
   */
  public static ObjetPlateau depuisCaractere(char chr) {
    ObjetPlateau nouveau = null;
    switch (chr) {
      case '-':
        nouveau = new Herbe();
        break;
      case '+':
        nouveau = new Pomme();
        break;
      case '*':
        nouveau = new Rocher();
        break;
      case ' ':
        nouveau = new Vide();
        break;
      case '#':
        nouveau = new Mur();
        break;
      case 'H':
        nouveau = new Joueur();
        break;
    }
    return nouveau;
  }

  /// Autres fonctions à réaliser ici...
  /**
   * renvoie le caractère correspondant à l'objet
   */
  public abstract char afficher();

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
   * @param niveau le niveau
   */
  public void visiterPlateauCalculEtatSuivant(Niveau niveau, int x, int y) {
  }
}
