// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 

public interface Actionnable {
    /** Méthode obligeant tout agent à définir son action de tour */
    public void agir() throws DeplacementImpossibleException;
    // pas besoin de mettre abstract car interface est déjà implicitement abstraite

    /** Indique si l'objet est toujours actif/vivant dans la simulation */
    public boolean getEstVivant();

    /** Force l'objet à se terminer (mort ou destruction) */
    public void mourir();

}