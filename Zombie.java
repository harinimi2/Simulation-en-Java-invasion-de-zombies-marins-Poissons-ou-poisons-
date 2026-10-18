// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 

public class Zombie extends AgentMarin {
    
    /** Identifiant UNIQUE du zombie */
    private final String id; // final pour le zombie mais pour les poissons et les coraux ça ne le sera pas car ils peuvent se transformer en zombies
    
    /** Compteur static de zombies pour générer l'id */
    private static int cpt = 0;
    
    
    public Zombie(int x, int y, Terrain t) {
        super(x,y,"Z",t);
        cpt++;
        id = nom + "." + cpt; // exemple : "Z.10"
    }

    // Conditions : on fait d'abord attaquer le zombie si c'est un poisson devant lui (il faut mettre une condition sur la distance qui sépare le poisson du zombie)
    // Ensuite si l'attaque est annulé ça veut dire que le poisson possède une/des MagicalPlant(s) de type Exterminator
    // Ne pas oublier qu'il y a deux MagicalPlants : donc vérifier dans la fonction attaque le MagicalPlant est bien de type Exterminator

    
    /** Le zombie attaque le poisson qui est sur la même case que lui (distance inférieur à 1) (Remarque : le zombie n'attaque pas le robot, il l'ignore) */
    public void attaquer(Poisson victime) {
        // le zombie attaque que quand le poisson et lui sont proches
        if (this.distance(victime.x, victime.y) <= 1) {
            System.out.println(this.id + " tente d'attaquer " + victime.toString());

            // 2. Le zombie vérifie la défense du poisson
            if (victime.possedePlante()) {
                MagicalPlant p = victime.getPlanteEquipee();

                if (!p.estImmunisante()) {
                    System.out.println("RIP ! " + victime.toString() + " a utilisé un Exterminator. Le zombie est détruit ! BRAVO !");
                    // le zombie meurt
                    this.mourir();
                }
                else if (p.estImmunisante()) { // de type Immunisator
                    System.out.println("NONNNN ! La MagicalPlant était de type Immunisator contre le poison des coraux. Le zombie dévore le poisson !");
                    victime.mourir();
                }

                // Dans les deux cas, le poisson a utilisé sa plante
                victime.utiliserPlante();
            }
            else {
                System.out.println("MIAM ! Le poisson n'avait pas de plante. Il est dévoré par le zombie.");
                // le poisson meurt
                victime.mourir();
            }
        }
    }

    // attaque du zombie : le zombie élimine le poisson si le poisson ne possède pas de MagicalPlant et infecte la zone après son passage
    // le zombie n'infecte pas la zone actuelle il infecte que après son passage

    /** Agir : le zombie se déplace et infecte les corails APRÈS son passage */
    public void agir() throws DeplacementImpossibleException {
        // 1. UN SEUL déplacement au début du tour
        this.deplacerAleatoirement();

        // 2. Interaction avec le décor (CORAIL)
        Ressource r = terrain.getCase(this.x, this.y);
    
        if (r instanceof Corail) {
            Corail c = (Corail) r;
        
            // Sécurité contre la double infection / transformation infinie
            // On n'infecte que si le corail n'est pas encore transformable
            if (c.getQuantite() < 10) { 
                int infectionActuelle = c.getQuantite();
            
                // On limite le maximum à 10 pour éviter les scores énormes (ex: 11, 12...)
                int nouvelleInfection = Math.min(10, infectionActuelle + 2);
                c.setQuantite(nouvelleInfection);
            
                System.out.println(this.id + " propage l'infection en (" + x + "," + y + ") : Niveau " + nouvelleInfection);
            }
        }
    }

    public String toString() {
        return String.format("Zombie %s en position (%d,%d)", id, x, y);
    }
}