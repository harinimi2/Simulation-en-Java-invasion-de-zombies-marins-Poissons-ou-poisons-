// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 


public class MagicalPlant extends Ressource {
    // ici quantité du constructeur Ressource représente quantité qui distinguera des plantes exterminator des immunisator : ce sont les effets
    // si effet = -1, MagicalPlant est de type exterminator
    // si effet = 1, MagicalPlant est de type immunisator
    
    // les positions des MagicalPlants, on peut les retrouver par getColonne() ou getLigne() de Ressource

    /** Attribut pour l'identifiant personnalisé de la MagicalPlant */
    protected String idPerso;

    // Compteur statique pour tous les MagicalPlants
    // on y a accès par getNbRessources();

    /** int effet permettra de distinguer exterminator des immunisator : si effet = -1, MagicalPlant est de type Exterminator et si effet = 1, MagicalPlant est de type Immunisator */
    public MagicalPlant(int effet) {
        // effet = -1 pour Exterminator, effet = 1 pour Immunisator
        super("MagicalPlant", effet); // appel au constructeur de Ressource
        String prefixe = "";
        if(effet == -1) {
            prefixe = "E";
        }

        if(effet == 1) {
            prefixe = "I";
        }
        this.idPerso = prefixe + "." + this.getNbRessources();
    }
    /** Affiche position de la MagicalPlant et son type */
    public String toString() {
        String type ="";
        if(this.getQuantite() == -1) {
            type = "Exterminator";
        }
        
        if(this.getQuantite() == 1) {
            type = "Immunisator";
        }
        return String.format("MagicalPlant %s de type %s en position (%d,%d)", idPerso, type, this.getLigne(), this.getColonne());
    }

    /** On peut ajouter une méthode pour vérifier le type facilement */
    public boolean estImmunisante() { return this.getQuantite() == 1; } // pour savoir si c'est un Immunisator
}