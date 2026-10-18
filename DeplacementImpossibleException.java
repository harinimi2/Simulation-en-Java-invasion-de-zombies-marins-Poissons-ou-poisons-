// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 

public class DeplacementImpossibleException extends Exception {
    public DeplacementImpossibleException(String message) {
        super("Erreur Simulation Marine : " + message);
    }
}