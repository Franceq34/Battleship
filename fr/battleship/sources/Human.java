package fr.battleship.sources;

import java.util.Scanner;

import fr.battleship.exceptions.LocationCoordException;
import fr.battleship.exceptions.WritingCoordException;

public class Human extends Player{

	private static Scanner sc = new Scanner(System.in);
	
	public Human(String name) {
		super();
		this.name = name;
	}

	public String getName(){
		return name;
	}
	
	@Override
	public void addShootHistory(Tir t) {
		shootHistory.add(t);
		if(t.getResult() == 0){
			System.out.println("Plouf, manqu� !");
		} else if(t.getResult() == 1){
			System.out.println("Touch� !");
		} else if(t.getResult() == 2){
			System.out.println("BOOM ! Vous avez d�truit un bateau !");
		}
	}

	@Override
	public Ship askCoordsShip() {
		Ship ship = null;
		boolean aligned = false;
		Coordonnee start = null;
		Coordonnee end = null;
		while (aligned == false){
			System.out.println("Entre les coordonn�es du d�but de votre nouveau bateau :");
			start = this.askCoord();
			System.out.println("Entre les coordonn�es de fin de votre bateau :");
			end = this.askCoord();
			aligned = start.aligned(end);
			if (aligned == false){
				System.out.println("Votre bateau ne peut �tre plac� en diagonale !");
			}
		}
		int dist = start.distance(end);
		if(nbShipAvailable(dist) > 0){
			Ship tempShip = new Ship(start, end);
			if(this.canPlace(tempShip)){
				ship = tempShip;
				this.removeShipAvailable(dist);
				this.addShip(ship);
			}
			else{
				System.out.println("Veuillez ne pas superposer vos bateaux !");
			}
		}
		else{
			System.out.println("Vous ne disposez pas de bateaux de cette taille !");
		}
		return ship;
	}
	
	public static String askName(){
		String name = sc.nextLine();
		return name;
	}
	
	public static int askMode(){
		int res;
		String choix = sc.nextLine();
		switch (choix)
		{
		  case "0":
			  	System.out.println("Vous allez affronter un ami !");
				res = 0;
			  	break;
		  case "1":
			    System.out.println("Vous allez affronter un ordinateur facile !");
				res = 1;
		    break;
		  case "2":
			    System.out.println("Vous allez affronter un ordinateur moyen !");
				res = 2;
		    break;
		  case "3":
			    System.out.println("Vous allez affronter un ordinateur difficile !");
				res = 3;
		    break;
		  default:
			    System.out.println("Veuillez enter un des choix propos�s");
			    res = askMode();
		}
		return res;
	}
	
	public Coordonnee askCoord() {
		String startCoord = sc.nextLine();
		Coordonnee start = null;
		try {
			start = new Coordonnee(startCoord);
		} catch ( WritingCoordException e) {
			System.out.println("Veuillez entrer une coordonn�e valide ! Exemple : A1");
			start = this.askCoord();
		} catch ( LocationCoordException e ) {
			System.out.println("Veuillez entrer une coordonn�e dans la grille ! La grille fait " + Parameter.getNbCoordX() + "x" + Parameter.getNbCoordY());
			start = this.askCoord();
		}
		return start;
	}

	@Override
	public Coordonnee askCoordShot() {
		System.out.println("Entrez votre coordonn�e de tir :");
		Coordonnee coord = this.askCoord();
		if(this.alreadyShotAt(coord)){
			System.out.println("Vous avez d�j� tir� ici !");
			coord = askCoordShot();
		}
		return coord;
	}

	public static boolean askContinuePlaying() {
	    System.out.println("Souhaitez vous rejouer ? [ 0: non | 1: oui]");
		boolean res = false;
		String choix = sc.nextLine();
		switch (choix)
		{
		  case "0":
			  	res = false;;
			  	break;
		  case "1":
			    res = true;
		  break;
		  default:
			    System.out.println("Veuillez enter un des choix propos�s");
			    res = askContinuePlaying();
		}
		return res;
	}
	
}
