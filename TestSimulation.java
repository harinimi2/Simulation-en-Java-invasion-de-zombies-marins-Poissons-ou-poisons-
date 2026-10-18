// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 

public class TestSimulation {
    public static void main(String[] args) throws DeplacementImpossibleException {
        System.out.println("Projet : Invasion de zombies marins : poissons ou poisons ?");
        System.out.println("par Harini MINA 21404896 Groupe 7 ");
        System.out.println("");
        System.out.println("==========================================");
        System.out.println("   LANCEMENT DE LA SIMULATION COMPLETE    ");
        System.out.println("==========================================\n");

        // Paramètres de la simulation :
        int nbLignes = 8;
        int nbColonnes = 8;
        int nbRessources = 35; // Nombre de coraux et plantes au total
        int nbAgents = 20;      // Nombre de poissons, zombies et robots
        int nbEtapes = 20;     // Nombre de tours de jeu

        // 1. Initialisation de la simulation
        // Le constructeur va s'occuper de créer le Terrain, 
        // de placer les Coraux, les Plantes, les Poissons, etc.
        Simulation sim = Simulation.getInstance(nbLignes, nbColonnes, nbRessources, nbAgents, nbEtapes);
        
        // 2. Lancement
        // Cette méthode contient la boucle for (nbEtapesMax)
        // Elle va gérer les agir(), les évolutions de coraux, 
        // les transformations en zombies et les attaques.
        sim.lancer();
        

        System.out.println("\n==========================================");
        System.out.println("        FIN DE LA SIMULATION              ");
        System.out.println("==========================================");
    }

    //Rmq : on affiche que les ressources dans terrain pour des questions de lisibilités puisque j'autorise plusieurs agents sur une même case
    // Par contre pour ressources, j'ai mis la restriction qu'une case ne peut pas avoir plusieurs ressources avec le test de caseestvide avant de placer les ressources sinon je décrémente i
}