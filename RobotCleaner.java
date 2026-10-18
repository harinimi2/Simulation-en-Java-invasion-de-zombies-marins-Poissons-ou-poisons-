// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 

public class RobotCleaner extends RobotScanner {

    public RobotCleaner(int x, int y, Terrain t) {
        super(x, y, t);
        this.nom = "RC";
    }

    public String estRobot() {
        return super.toString() + " de type Cleaner : Je scanne et je nettoie les zones infectées.";
    }


    /** Le RobotCleaner utilise le scan de son parent mais ajoute l'action de nettoyage : c'est un RobotScanner mais encore plus puissant, il peut nettoyer le poison émis par les coraux infectés ! */
    public void agir() throws DeplacementImpossibleException{
        // 1. On récupère la ressource AVANT ou APRÈS le déplacement
        // Pour être sûr, on va scanner la case où il se trouve APRÈS s'être déplacé
        this.deplacerAleatoirement(); 

        Ressource res = terrain.getCase(x, y);
    
        // Présence systématique comme avec le RobotScanner
        System.out.println("[INFO] Robot Cleaner " + this.id + " en zone (" + x + "," + y + ")");

        if (res instanceof Corail) {
            Corail c = (Corail) res;
            System.out.println("[SCAN] " + this.id + " analyse le Corail... Infection : " + c.getQuantite());
        
            if (c.getQuantite() > 0) {
                // ACTION DE NETTOYAGE
                c.setQuantite(0); 
                System.out.println("   >>> [ACTION] " + this.id + " : NETTOYAGE EFFECTUÉ ! Zone décontaminée.");
            }
        }
    }
}