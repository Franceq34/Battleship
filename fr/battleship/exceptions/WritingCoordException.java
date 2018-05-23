package fr.battleship.exceptions;

public class WritingCoordException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public String toString(){
		return "Votre coordonnée n'est pas valide.";
	}
}
