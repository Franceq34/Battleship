package fr.battleship.exceptions;

public class LocationCoordException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public String toString(){
		return "Votre coordonn�e n'est pas dans la grille.";
	}
}
