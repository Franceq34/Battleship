package fr.battleship.sources;

import fr.battleship.exceptions.LocationCoordException;

public class Medium extends IA {
	
	public Medium() {
		super();
		this.name = "Ordinateur Moyen";
	}

	@Override
	public Coordonnee askCoordShot() {
		Coordonnee res = null;
		int coordX = randInt(1, Parameter.getNbCoordX());
		int coordY = randInt(1, Parameter.getNbCoordY());
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
}
