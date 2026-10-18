// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 

public class OutilsSimulation {

    // Attributs statiques (Constantes)
    /** Si la quantité vaut 10 alors le corail se transforme en zombie */
    public static final int STADE_POISON = 1; 

    /** Dès que le corail est infecté (=1), il commence à émettre du poison */
    public static final int STADE_ZOMBIE = 10; 

    // Méthodes statiques
     /** Lancé de dé (pour les déplacements aléatoires notamment) : pour simplifier la simulation on retourne tuple de random */
    public static int[] de(int nbLignes, int nbColonnes) {
        int l = (int)((Math.random() * nbLignes) + 1);
        int c = (int)((Math.random() * nbColonnes) + 1);
        return new int[]{l, c};
    }

    /** Calcule une probabilité simple */
    public static boolean chanceDe(double seuil) {
        return Math.random() < seuil;
    }
}