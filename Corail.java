// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 

public class Corail extends Ressource {

    // ici quantité du constructeur Ressource représente quantité qui représente l'infection
    // Constantes pour la logique d'évolution (communes à tous les coraux)
    
    /** Si la quantité vaut 10 alors le corail se transforme en zombie */
    public static final int STADE_ZOMBIE = 10; // en cas de modification de cette cste ne pas oublier de changer dans zombie dans methode agir

    /** Dès que le corail est infecté (=1), il commence à émettre du poison */
    private static final int STADE_POISON = 1; 

    // son stade d'infection qui évoluera après le passage du zombie, c'est la fameuse quantité de Ressource:
    // on y aura accès grâce à getQuantite() et on pourra l'évoluer grâce à setQuantite() de Ressource
    
    // les positions des coraux, on peut les retrouver par getColonne() ou getLigne() de Ressource

    // Compteur statique pour tous les coraux
    // on y a accès par getNbRessources();

    /** Identifiant personnalisé (ex: "C.1") car Ressource possède déjà un ide */
    private String idPerso; 


    public Corail() {
        super("Corail",0); // car initialement, la quantité "infection" est 0 pour le corail
        this.idPerso = "C." + this.getNbRessources();
    }

    /** Méthode pour faire évoluer l'infection (appelée par la Simulation) */
    public void incrementerInfection() {
        // On modifie l'attribut quantité hérité de Ressource
        this.setQuantite(this.getQuantite() + 1);
    }

    /** Vérifie si le corail doit devenir un zombie */
    public boolean transformerEnZombie() {
        return this.getQuantite() >= STADE_ZOMBIE;
    }

    /** Vérifie si le corail commence à émettre du poison */
    public boolean emetPoison() {
        return this.getQuantite() >= STADE_POISON;
    }

    /** Méthode appelée à chaque tour de simulation pour les coraux infectés */
    public void evoluer() {
        if (this.getQuantite() > 0 && this.getQuantite() < 10) {
            this.incrementerInfection();
            System.out.println("L'infection progresse sur le " + this.idPerso + " : Niveau " + this.getQuantite());
        }
    }

    /** Affichage de la position du corail */
    public String toString() {
        return String.format("Corail %s en position (%d,%d)", idPerso, this.getColonne(), this.getLigne());
    }
}
