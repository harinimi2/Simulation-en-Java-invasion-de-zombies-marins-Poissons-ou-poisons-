// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 

public abstract class Robot extends AgentMarin {

    /** final pour les zombies et les robots mais pour les poissons et les coraux ça ne le sera pas car ils peuvent se transformer en zombies */
    protected final String id; 

    /** Compteur de robots pour l'id */
    private static int cpt = 0;
    
    
    public Robot(int x, int y, Terrain t) {
        super(x,y,"R",t);
        cpt++;
        id = nom + "." + cpt; // exemple : "R.10"
    }

    public String toString() {
        return String.format("Robot %s en position (%d,%d)", id, this.x, this.y);
    }

    /** Le robot est par défaut toujours vivant, il ne peut jamais mourir même si il est attaqué par un zombie ou est dans une zone infectée */
    public void mourir() {
        // On laisse le corps de la méthode vide ou on met juste un message.
        // L'important est de NE PAS faire : this.estVivant = false;
        System.out.println(this.id + " est blindé, il ignore l'attaque !");
    }

    // polymorphisme 
    public abstract void agir() throws DeplacementImpossibleException;
    
    /** Pour savoir si le type du robot (Rmq: un RobotCleaner est un RobotScanner mais pas forcément l'inverse (hiérarchie à 3 niveaux)) */
    public abstract String estRobot();
}