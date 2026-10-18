// Projet : Invasion de zombies marins : poissons ou poisons ?

// Harini MINA 21404896 Groupe 7 

// je choisis d'utiliser liste car il y aura des zombies qui vont générer et des agentsMarins qui vont disparaître donc la taille de la liste est dynamique
import java.util.ArrayList;

public class Simulation {

    private Terrain terrain;

    /** Liste des agents */
    private ArrayList<AgentMarin> agents; 

    /** Liste des ressources */
    private ArrayList<Ressource> ressources; 

    /** Nombre d'étapes de la simulation : on vérifiera que c'est bien un entier positif grâce à l'exception IllegalArgumentException */
    private int nbEtapesMax; 

    /** L'attribut de classe (statique) */
    // Pas final car on l'initialise dans le constructeur
    private static Simulation instance = null; // pour le singleton


    /** Constructeur privé pour le Singleton */
    private Simulation(int lignes, int colonnes, int m, int n, int nbEtapes) {
        this.terrain = new Terrain(lignes, colonnes);
        this.agents = new ArrayList<>();
        this.ressources = new ArrayList<>();
        this.nbEtapesMax = nbEtapes;
        instance = this;

        // on place les m ressources (les ressources ne peuvent pas être présentes sur la même case)
        for (int i = 0; i < m; i++) {
            int[] coord = OutilsSimulation.de(terrain.nbLignes, terrain.nbColonnes);

            // On vérifie la case AVANT de créer l'objet // pour éviter bug cpt
            if (!terrain.caseEstVide(coord[0], coord[1])) {
                i--; // case occupée, on retente
                continue;
            }

            Ressource r; // ressource à placer

            // proba
            if (OutilsSimulation.chanceDe(0.80)) { // 80% de chance d'avoir une plante
                // On choisit au hasard entre Exterminator et Immunisator
                if (OutilsSimulation.chanceDe(0.50)) {
                    r = new MagicalPlant(-1); // Exterminator

                } else {
                    r = new MagicalPlant(1); // Immunisator
                }

            } else { // 20% de chance d'avoir un corail
                r = new Corail();
            }

            //PLACEMENT SUR LE TERRAIN
            if (terrain.setCase(coord[0], coord[1], r)) {
                this.ressources.add(r); 
                System.out.println("Placement initial de : " + r.toString());
            } else {
                i--; // On recommence car la case était occupée
            }

        }

        // 3. AFFICHAGE INITIAL UNIQUE (donc en dehors de la boucle)
            System.out.println("\n====================================");
            System.out.println("===== ÉTAT INITIAL DU TERRAIN =====");
            System.out.println("====================================");
            this.terrain.afficher(6); 
            System.out.println("Ressources placées: " + ressources.size());
            System.out.println("Agents placés: " + agents.size());
            System.out.println("====================================\n");

        // on place les n agents
        // Génération des n agents
        
        for (int i = 0; i < n; i++) {
            int[] coord = OutilsSimulation.de(terrain.nbLignes, terrain.nbColonnes); // On lance le dé pour chaque agent
            
            // On vérifie la case AVANT de créer l'objet // pour éviter bug avec le cpt
            if (!terrain.caseEstVide(coord[0], coord[1])) {
                i--; // case occupée, on retente
                continue;
            }

            AgentMarin am = null; // On initialise l'agent à null

            // proba
            if (OutilsSimulation.chanceDe(0.20)){ 
                // 20% de chance d'avoir un Robot
                if (OutilsSimulation.chanceDe(0.50)) {
                    am = new RobotScanner(coord[0], coord[1], terrain); 
                }   else {
                    am = new RobotCleaner(coord[0], coord[1], terrain);
                }
        

            } else if (OutilsSimulation.chanceDe(0.50)) { 
                // 50% de chance d'avoir un Zombie 
                am = new Zombie(coord[0], coord[1], terrain);

            } else { 
                // 70% de chance d'avoir un Poisson (le reste)
                am = new Poisson(coord[0], coord[1], terrain);
            }

            // AJOUT À LA LISTE 
            // On n'ajoute l'agent que si l'emplacement était valide (case vide)
            // car mon constructeur AgentMarin vérifie déjà la case !
            if (am != null && am.x != 0 && am.y != 0) {
                agents.add(am);
                System.out.println("Placement inital de : " + am.toString());
            }
            else {
                i--; // on décrémente i pour retenter le placement de cet agent
            }
        }
    }

    /** * MÉTHODE UNIQUE pour créer ou récupérer la simulation
     * On l'appelle une seule fois au début du programme.
     */
    public static Simulation getInstance(int lignes, int colonnes, int m, int n, int nbEtapes) {
        if (instance == null) {
            instance = new Simulation(lignes, colonnes, m, n, nbEtapes);
        }
        return instance;
    }

    /** Pour récupérer l'instance plus tard sans remettre les chiffres */
    public static Simulation getInstance() {
        return instance;
    }

    /** Gestion de la simulation : le top départ de notre simulation (on y utilise d'ailleurs des exceptions déjà existantes dans Java) */
    public void lancer() throws DeplacementImpossibleException { 
        // utilisation des exceptions déjà existantes dans Java et des miennes
        try {
            if (nbEtapesMax <= 0) {
                // On lance une exception standard de Java
                throw new IllegalArgumentException("Erreur : Le nombre de tours doit être positif.");
            }

            for (int etape = 0; etape < nbEtapesMax; etape++) {
                System.out.println("\n========== TOUR " + (etape + 1) + " ==========");

                // 1. PHASE DES RESSOURCES (Coraux et Plantes)
                ArrayList<Zombie> nouveauxZombies = new ArrayList<>();
                ArrayList<Ressource> ressourcesASupprimer = new ArrayList<>();

                for (Ressource r : ressources) {
                    if (r instanceof Corail) {
                        Corail c = (Corail) r;
                
                        // Le corail évolue (l'infection progresse s'il est touché) 
                        c.evoluer(); 

                        // Vérification : est-ce que le corail devient un zombie ? 
                        if (c.transformerEnZombie()) {
                            System.out.println("!!! ALERTE : Le " + c.toString() + " se transforme en Zombie !");
                    
                            // On crée un nouveau zombie à l'emplacement du corail 
                            Zombie nz = new Zombie(c.getLigne(), c.getColonne(), terrain);
                            nouveauxZombies.add(nz);
                    
                            // On libère la case du terrain et on marque la ressource pour suppression
                            terrain.viderCase(c.getLigne(), c.getColonne());
                            ressourcesASupprimer.add(c);
                        }
                    }
                }

                // Mise à jour des listes pour les transformations
                ressources.removeAll(ressourcesASupprimer);
                agents.addAll(nouveauxZombies);

                // 2. PHASE D'ACTION DES AGENTS (Poissons, Robots, Zombies)
                for (AgentMarin a : agents) {
                    if (a.getEstVivant()) {
                        // RobotScanner scanne, RobotCleaner nettoie, Zombie infecte au passage 
        
                        a.agir(); // agir() appellera seDeplacer() qui peut lever l'exception DéplacementImpossibleException
                    }
                }

                // 3. PHASE D'ATTAQUE DES ZOMBIES
                // On parcourt la liste pour que chaque zombie attaque les poissons sur sa case 
                for (AgentMarin a : agents) {
                    if (a instanceof Zombie && a.getEstVivant()) {
                        Zombie z = (Zombie) a;
                        for (AgentMarin cible : agents) {
                            // Un zombie n'attaque que les poissons vivants sur la même case 
                            if (cible instanceof Poisson && cible.getEstVivant()) {
                                if (z.distance(cible.x, cible.y) == 0) {
                                    z.attaquer((Poisson) cible);
                                }
                            }
                        }
                    }
                }

                // 4. j'ai enlevé affichage du terrain car cela noye le terminal et devient vite illisible 
                // et de plus pour des questions de précisions je préfère les print car on connait les positions exactes (coordonnées des cases)


                // 5. NETTOYAGE DES MORTS
                // On retire les poissons dévorés ou empoisonnés, et les zombies détruits 
                agents.removeIf(a -> !a.getEstVivant());

                // 5. AFFICHAGE DU BILAN DU TOUR
                System.out.println("Bilan : " + agents.size() + " agents en vie.");
        
                // Optionnel : terrain.afficher();
        
                // Arrêt si plus personne ne peut agir
                if (agents.isEmpty()) {
                    System.out.println("Fin de la simulation : extinction des agents.");
                    break;
                }
            }

            System.out.println("\n==========================================");
            System.out.println("=====    ÉTAT FINAL DU TERRAIN      =====");
            System.out.println("==========================================");
            this.terrain.afficher(6); 
            
            System.out.println("\n========== RÉSULTAT FINAL ==========");
    
            int nbPoissons = 0;
            int nbZombies = 0;
            int nbRobots = 0;

            for (AgentMarin a : agents) {
                if (a instanceof Poisson) nbPoissons++;
                else if (a instanceof Zombie) nbZombies++;
                else if (a instanceof Robot) nbRobots++; // Compte RS et RC
            }

            if (nbPoissons > 0 && nbZombies == 0) {
                System.out.println("VICTOIRE : La Nature a triomphé !");
            } 
            else if (nbPoissons == 0 && nbZombies > 0) {
                System.out.println("DÉFAITE : L'apocalypse Zombie est totale.");
            } 
            else if (nbPoissons == 0 && nbZombies == 0) {
                // les robots sont les derniers debout
                System.out.println("FIN DU MONDE : Seuls les Robots continuent de patrouiller dans un océan vide.");
            }
            else {
                System.out.println("SANS ISSU POUR L'INSTANT: La guerre fait toujours rage.");
            }

            System.out.println("Stats : " + nbPoissons + " Poissons, " + nbZombies + " Zombies, " + nbRobots + " Robots.");
        }

        catch (IllegalArgumentException e) {
            System.out.println("Arrêt de la simulation : " + e.getMessage());
        }
    }
}

