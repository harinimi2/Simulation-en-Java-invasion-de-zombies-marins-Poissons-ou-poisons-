import java.util.ArrayList;

/**
 * Exemple d'utilisation des classes <code>Terrain</code> 
 * et <code>Ressource</code>.
 * @author UL2IN002 (2026fev)
 * @author Christophe Marsala (2020oct-2022oct)
 */
public class TestTerrain {
	public static void main(String[] args) {
	
		// Exemple de création de terrain
		Terrain t = new Terrain(3,7);
		
		// Terrain initial : il est vide
		t.afficher(2);

		// Informations sur le terrain
		System.out.println(t);		

		// On créé une ressource...
		Ressource ress1 = new Ressource("Thon",5);
		System.out.println("\nCréation de ress1="+ress1);
		
		// ... et on la place sur le terrain
		if (t.setCase(2,3,ress1))
			System.out.println("Ajout de " +ress1+" effectuée !");
		else 
			System.out.println("Ajout de " +ress1+" impossible, la case est occupée !");
				
		// On créé une autre ressource...
		Ressource ress2 = new Ressource("Requin",4);
		System.out.println("\nCréation de ress2="+ress2);
		
		// ... et on essaie de la placer à la même place
		if (t.setCase(2,3,ress2))
			System.out.println("Ajout de " +ress2+" effectuée !");
		else
			System.out.println("Ajout de " +ress2+" impossible, la case est occupée !");
			
		// On essaie de placer la ressource à un autre endroit
		if (t.setCase(1,4,ress2))
			System.out.println("Ajout de " +ress2+" effectuée !");
		else
			System.out.println("Ajout de " +ress2+" impossible, la case est occupée !");		
		
		// Ajout d'une troisième ressource 
		if (t.caseEstVide(3,5)) {
			t.setCase(3,5,new Ressource("Raie",3));
		}
		
		System.out.println();
		
		// Affichage du terrain avec les ressources ajoutées
		t.afficher(6);

		// Informations sur le terrain
		System.out.println(t);
		
		System.out.println();		
		System.out.println("Contenu d'une case :");
		System.out.println("- Contenu de la case (2,3) : "+t.getCase(2,3));
		System.out.println("- Contenu de la case (1,4) : "+t.getCase(1,4));
		System.out.println("- Contenu de la case (1,1) : "+t.getCase(1,1));

		System.out.println();		
		System.out.println("Vidage d'une case :");
		System.out.println("- Vidage de la case (1,4) : "+t.viderCase(1,4));
		System.out.println("- Vidage de la case (1,1) : "+t.viderCase(1,1));
		
		System.out.println();
					
		// Affichage du terrain
		t.afficher(4);
		
		System.out.println("Liste des ressources présentes actuellement sur le terrain :");
		ArrayList<Ressource> liste = t.lesRessources();
		for (Ressource r : liste) {
			System.out.println("- "+r);
		}
		
	}

}
