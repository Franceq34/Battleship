package fr.battleship.sources;
import java.util.*;

public final class Parameter {

	private static final int nbCoordX = 10;
	
	private static final int nbCoordY = 10;

	private static Hashtable<Integer, Integer> nbShip = new Hashtable<Integer, Integer>();
	
	static{
		nbShip = new Hashtable<Integer, Integer>();
		nbShip.put(1, 1); //Nombre de bateaux de longueur 1
		nbShip.put(2, 1); //Nombre de bateaux de longueur 2
		nbShip.put(3, 2); //Nombre de bateaux de longueur 3
		nbShip.put(4, 0); //Nombre de bateaux de longueur 4
		nbShip.put(5, 1); //Nombre de bateaux de longueur 5
	}
	
	public static int getNbCoordX() {
		return nbCoordX;
	}
	public static int getNbCoordY() {
		return nbCoordY;
	}
	
	public static Hashtable<Integer, Integer> getNbShip() {
		return nbShip;
	}
	
	public static int getNbShipTotal(){
		int nbShip = 0;
		Set<Integer> keys = Parameter.nbShip.keySet();
        for(Integer key: keys){
            nbShip += Parameter.nbShip.get(key);
        }
        return nbShip;
	}
	
	public static String show(){
		String res = "Les paramètres de la partie sont :\n";
		res += "- carte de " + nbCoordX + "x" + nbCoordY + "\n";
        Set<Integer> keys = nbShip.keySet();
        for(Integer key: keys){
            res += "- " + nbShip.get(key) + " bateaux de taille "+ key +"\n";
        }
        return res;
	}
	
}
