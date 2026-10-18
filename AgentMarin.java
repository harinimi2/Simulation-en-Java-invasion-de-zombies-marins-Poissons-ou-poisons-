// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 

public abstract class AgentMarin implements Actionnable {
    /** Position/coordonnées de l'agent marin sur le terrain */
    protected int x, y; // position de l'agent marin (int car numéro de lignes et de colonnes du terrain)
    
    /** Car l'agent connait le terrain*/
    protected Terrain terrain; 

    /** Car il y aura plusieurs types d'agents marins */
    protected String nom; 

    /** Au début tout les agents sont vivants*/
    protected boolean estVivant = true; 


    // Ne pas oublier que d'apres le doc on a public nbLignes et nbColonnes dans la classe Terrain


    public AgentMarin(int lig, int col, String n, Terrain terrain) {
        
        this.terrain = terrain; // l'agent connaît le terrain dès sa création
        nom = n;

        // On affecte directement les coordonnées
        // La vérification de validité (limites et vide) est faite par la classe Simulation avant d'appeler ce constructeur
        x = lig;
        y = col; 
        
    }

    /** Calcul distance euclidienne
    */
    public double distance(int lig, int col) {
        // pour plus de lisibilité, j'utilise plusieurs variables intermédiaires pour calculer la distance
        int xres = (x - lig)*(x - lig);
        int yres = (y - col)*(y - col);
        double dist = Math.sqrt(xres + yres);
        return dist;
    
    }

    /** Déplacement des agents marins sur le terrain */
    public void seDeplacer(int lig, int col) throws DeplacementImpossibleException {

        // Validation des limites de la grille
        // On vérifie d'abord si on reste dans la grille pour éviter de sortir du tableau
        if (lig >= 1 && lig <= terrain.nbLignes && col >= 1 && col <= terrain.nbColonnes) {
            // des  agents peuvent être sur la même case
            x = lig;
            y = col;
            System.out.println("Agent " + this.toString() + " déplacé en (" + lig + "," + col + ")");
        }

        else {
            throw new DeplacementImpossibleException("Coordonnées hors limites : (" + lig + "," + col + ")");
        }
        
    }

    /** Déplacement aléatoire (automatisation) des agents marins sur le terrain */
    public void deplacerAleatoirement() throws DeplacementImpossibleException {
        // Calcul du mouvement (-1, 0, ou 1)
        int nvlLig = this.x + (int)(Math.random() * 3) - 1;
        int nvlCol = this.y + (int)(Math.random() * 3) - 1;

        // Les coordonnées restent strictement entre 1 et le max
        if (nvlLig < 1) nvlLig = 1;
        if (nvlCol < 1) nvlCol = 1;
        if (nvlLig > terrain.nbLignes) nvlLig = terrain.nbLignes;
        if (nvlCol > terrain.nbColonnes) nvlCol = terrain.nbColonnes;
        // On utilise la méthode de déplacement existante qui vérifie si la case est vide
        this.seDeplacer(nvlLig, nvlCol);
    }

    /** Accesseur pour que la Simulation puisse vérifier l'état */
    public boolean getEstVivant() {
        return estVivant;
    }

    /** Méthode pour tuer l'agent */
    public void mourir() {
        this.estVivant = false;
        System.out.println(this.toString() + " est mort.");
    }

    // Action des agents marins (polymorphisme) 
    public abstract void agir() throws DeplacementImpossibleException;

    /** Pour afficher logs */
    public abstract String toString();
    

}