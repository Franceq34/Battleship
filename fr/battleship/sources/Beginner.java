package fr.battleship.sources;

import fr.battleship.exceptions.LocationCoordException;

public class Beginner extends IA {

	public Beginner() {
		super();
		this.name = "Ordinateur Débutant";
	}

	@Override
	public Coordonnee askCoordShot() {
		Coordonnee res = null;
		int coordX = randInt(1, Parameter.getNbCoordX());
		int coordY = randInt(1, Parameter.getNbCoordY());
		try {
			res = new Coordonnee(coordX, coordY);
		} catch (LocationCoordException e) {
			e.printStackTrace();
		}
		return res;
	}

}
