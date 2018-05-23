package fr.battleship.sources;

import java.util.ArrayList;
import java.util.Hashtable;

import fr.battleship.exceptions.LocationCoordException;

public class Hard extends IA {

	private Coordonnee lastShotHit;
	
	public Hard() {
		super();
		this.lastShotHit = null;
		this.name = "Ordinateur Difficile";
	}
	
	@Override
	public Coordonnee askCoordShot() {
		Coordonnee res = null;
		int coordX = -1;
		int coordY = -1;
		if(lastShotHit != null){
			ArrayList<Coordonnee> neighbors = null;
			try {
				neighbors = this.lastShotHit.getNeighbors();
				ArrayList<Coordonnee> coordAvailable = new ArrayList<Coordonnee>(); 
				for(Coordonnee key: neighbors){
					if(!this.alreadyShotAt(key)){
						coordAvailable.add(key);
					}
				}
				if(coordAvailable.size() == 0){
					lastShotHit = null;
					coordX = randInt(1, Parameter.getNbCoordX());
					coordY = randInt(1, Parameter.getNbCoordY());
				} else {
					int nbNeighbors = coordAvailable.size();
					int pos =  randInt(0, nbNeighbors-1);
					Coordonnee temp = coordAvailable.get(pos);
					coordX = temp.getCoordX();
					coordY = temp.getCoordY();
				}
			} catch (LocationCoordException e) {
				e.printStackTrace();
			}
		} else {
			coordX = randInt(1, Parameter.getNbCoordX());
			coordY = randInt(1, Parameter.getNbCoordY());
		}
		try {
			res = new Coordonnee(coordX, coordY);
			if(this.alreadyShotAt(res)){
				res = this.askCoordShot();
			}
		} catch (LocationCoordException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public void addShootHistory(Tir t) {
		shootHistory.add(t);
		if(t.getResult() == 1){
			lastShotHit = t.getPosition();
		}
	}
	
	@Override
	public void reset(){
		Hashtable<Integer, Integer> shipAvailable =  new Hashtable<Integer, Integer>(Parameter.getNbShip());
		this.tabShip = new ArrayList<Ship>();
		this.shipAvailable = shipAvailable;
		this.shootHistory = new ArrayList<Tir>();
	};
}
