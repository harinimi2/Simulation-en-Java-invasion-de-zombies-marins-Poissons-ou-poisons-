// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 


public class Poisson extends AgentMarin {
    
    /** Identifiant UNIQUE du poisson */
    private String id; // final pour le zombie mais pour les poissons et les coraux ça ne le sera pas car ils peuvent se transformer en zombies
    
    /** Compteur static de zombies pour générer l'id */
    private static int cpt = 0;
    
    /** Le poisson ne peut posséder que une seule MagicalPlant */
    private MagicalPlant planteEquipee = null;

    public Poisson(int x, int y, Terrain t) {
        super(x,y,"P",t);
        cpt++;
        id = nom + "." + cpt; // exemple : "P.10"
    }

    /**
    * Constructeur par copie
    */
    public Poisson(Poisson autre) {
        super(autre.x, autre.y, "P", autre.terrain);
        this.id = "Copie_" + autre.id;
        this.planteEquipee = autre.planteEquipee;
        this.estVivant = autre.estVivant;
    }

    /** Accesseur pour savoir si le poisson possède une MagicalPlant */
    public boolean possedePlante() {
        return planteEquipee != null;
    }

    /** Pour savoir quel type de MagicalPlant le poisson possède : un Immunisator ? un Exterminator ? */
    public MagicalPlant getPlanteEquipee() {
        return planteEquipee;
    }

    /** Le poisson utilise la MagicalPlant si il est attaqué par un zombie ou si il est dans une zone infectée (Remarque : le poisson ne sait pas quel type de MagicalPlant il possède. Il le saura que lors de son usage.) */
    public void utiliserPlante() {
        this.planteEquipee = null;
    }

    /** Reaction du poisson face au zombie : le zombie élimine le poisson si le poisson ne possède pas de MagicalPlant + Réaction du poisson dans une zone infectée + Recherche de MagicalPlant dans les zones saines */
    public void agir() throws DeplacementImpossibleException{
        // Conditions : le zombie attaque si c'est un poisson devant lui (il faut mettre une condition sur la distance qui sépare le poisson du zombie)
        // le zombie attaque même s'il est dans une zone infectée
        // le zombie ne peut pas attaquer de robots (il l'ignore)
        // Vérifier que le poisson n'a pas de MagicalPlant

        // 1. Perception de la case actuelle
        Ressource ressourceCase = terrain.getCase(x, y); 
        boolean zoneEmpoisonnee = false;

        if (ressourceCase instanceof Corail) {
            Corail c = (Corail) ressourceCase;
            if (c.emetPoison()) {
                zoneEmpoisonnee = true;
            }
        }

        // 2. Gestion de la survie en zone infectée
        // Le poisson ne survit que s'il a l'Immunisator
        if (zoneEmpoisonnee) {
        // On utilise la méthode estImmunisante() de la classe MagicalPlant
            if (this.planteEquipee != null && this.planteEquipee.estImmunisante()) {
                System.out.println(this.id + " est protégé par son Immunisator !");
                // la plante est consommée pour prix de la protection 
                this.utiliserPlante(); 
            }
            else {
                System.out.println(this.id + " a succombé au poison (pas d'Immunisator).");
                this.mourir(); // le poisson est mort 
                return; 
            }
        } 
    
        // 3. Si la zone est saine, il essaie de ramasser une plante
        if (!possedePlante() && ressourceCase instanceof MagicalPlant) { 
            this.planteEquipee = (MagicalPlant) terrain.viderCase(x, y);  
           // le poisson ne sait pas encore si la MagicalPlant est un Exterminator ou un Immunisator
            System.out.println(this.id + " a ramassé une MagicalPlant");
        }
        

        // 4. Déplacement (à la fin)
        this.deplacerAleatoirement();
    
    }


    public String toString() {
        return String.format("Poisson %s en position (%d,%d)", id, x, y);
    }
}