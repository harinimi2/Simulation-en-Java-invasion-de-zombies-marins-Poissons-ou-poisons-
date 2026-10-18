// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 

public class RobotScanner extends Robot {

    public RobotScanner(int x, int y, Terrain t) {
        super(x,y,t);
        this.nom = "RS";
    }

    /** Affichage du type du robot */
    public String estRobot() {
        return super.toString() + "de type Scanner: Je scanne les zones infectées";
    }

    /** Le robot scanne juste le terrain pour repérer les zones infectées */
    // Pour MagicalPlant, le scanner les ignore. Il scanne que les coraux
    public void agir() throws DeplacementImpossibleException {
        // 1. Déplacement
        this.deplacerAleatoirement();

        // 2. MESSAGE SYSTÉMATIQUE : pour montrer que le robot est bien actif
        System.out.println("[INFO] " + this.id + " est en patrouille sur la case (" + x + "," + y + ")");

        // 3. SCAN DE LA CASE ACTUELLE
        Ressource res = terrain.getCase(x, y); 
        if (res instanceof Corail) {
            Corail c = (Corail) res;
            System.out.println("[SCAN] " + this.id + " : Analyse du Corail (" + x + "," + y + ") | Infection : " + c.getQuantite() + "/10");
            if (c.emetPoison()) {
                System.out.println("  !!! ALERTE : Zone hautement infectée détectée par " + this.id);
            }
        } else if (res == null) {
            System.out.println("[SCAN] " + this.id + " : BONNE NOUVELLE ! La case est saine.");
        }
    }
}