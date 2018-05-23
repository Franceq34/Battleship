package fr.battleship.sources;

public class Tir {

	private Coordonnee position;
	private int result;
	
	public Tir(Coordonnee position, int result) {
		this.position = position;
		this.result = result;
	}
	
	public Coordonnee getPosition() {
		return position;
	}
	public void setPosition(Coordonnee position) {
		this.position = position;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	
}
