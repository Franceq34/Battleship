package fr.battleship.sources;

import java.util.Set;

import fr.battleship.exceptions.LocationCoordException;

import java.util.Random;


public abstract class IA extends Player {

	protected static Random ran = new Random();

	@Override
	abstract Coordonnee askCoordShot();
	
	@Override
	public Ship askCoordsShip() {
		int sizeToPut = this.getSizeAvailable();
		//On enleve la taille du bateau à la taille de la grille pour la coordonnee de debut car si le bateau est place en ligne il s'etendra vers la droite.
		int maxX = Parameter.getNbCoordX() - sizeToPut+1;
		int startCoordX = randInt(1, maxX);
		int maxY = Parameter.getNbCoordY() - sizeToPut+1;
		int startCoordY = randInt(1, maxY);
		Coordonnee startC = null;
		try{
			startC = new Coordonnee(startCoordX, startCoordY);
		} catch (LocationCoordException e) {
			System.out.println(e);
		}
		boolean isVertical = ran.nextBoolean();
		Coordonnee endC = null;
		if(isVertical){
			try{
				endC = new Coordonnee(startCoordX, startCoordY+sizeToPut-1);
			} catch (LocationCoordException e) {
				System.out.println(e);
			}
		} else {
			try{
				endC = new Coordonnee(startCoordX+sizeToPut-1, startCoordY);
			} catch (LocationCoordException e) {
				System.out.println(e);
			}
		}
		Ship ship = null;
		Ship tempShip = new Ship(startC, endC);
		if(this.canPlace(tempShip)){
			ship = tempShip;
			this.removeShipAvailable(sizeToPut);
			this.addShip(ship);
		}
		return ship;
	}
	
	public int getSizeAvailable(){
		int res = -1;
		boolean trouve = false;
		Set<Integer> keys = this.shipAvailable.keySet();
        for(Integer key : keys){
        	if(!trouve && this.shipAvailable.get(key) > 0){
        		res = key;
        		trouve = true;
        	}
        }
		return res;
	}
	
	public static int randInt(int min, int max) {
		int res = ran.nextInt((max - min) + 1) + min;
	    return res;
	}
}
