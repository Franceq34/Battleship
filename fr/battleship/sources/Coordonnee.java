package fr.battleship.sources;
import java.util.ArrayList;
import java.util.regex.*;

import fr.battleship.exceptions.LocationCoordException;
import fr.battleship.exceptions.WritingCoordException;

public class Coordonnee {

	private int coordX;
	
	private int coordY;
	
	public Coordonnee(int x, int y) throws LocationCoordException{
		if(coordX > Parameter.getNbCoordX() || coordX < 0 || coordY > Parameter.getNbCoordY() || coordY < 0 ){
			throw new LocationCoordException();
		}
		this.coordX = x;
		this.coordY = y;
	}

	
	public Coordonnee(String lib) throws LocationCoordException, WritingCoordException{
		boolean isValid = Pattern.matches("([A-Z][0-9][0-9]?)", lib);
		if(!isValid){
			throw new WritingCoordException();
		}

		int coordY = Integer.parseInt(lib.replaceAll("\\D+",""));
		int coordX = (int)lib.replaceAll("\\d+","").toUpperCase().charAt(0) - 64;
			
		if(coordX > Parameter.getNbCoordX() || coordX <= 0 || coordY > Parameter.getNbCoordY() || coordY <= 0 ){
			throw new LocationCoordException();
		}
		this.coordY = coordY;
		this.coordX = coordX;
	}
	
	public int getCoordX(){
		return this.coordX;
	}
	
	public int getCoordY(){
		return this.coordY;
	}
	
	public boolean equals(Coordonnee c){
		if(this.coordX == c.coordX){
			if(this.coordY == c.coordY){
				return true;
			}
		}
		return false;
	}
	
	public int distance(Coordonnee c){
		int startX = this.getCoordX();
		int endX = c.getCoordX();
		int startY = this.getCoordY();
		int endY = c.getCoordY();
		
		if(endX == startX){
			return Math.abs(startY - endY)+1;
		}
		else{
			return Math.abs(startX - endX)+1;
		}
	}
	
	public boolean aligned(Coordonnee c){
		boolean res = false;
		int startX = this.getCoordX();
		int endX = c.getCoordX();
		int startY = this.getCoordY();
		int endY = c.getCoordY();
		if (startX == endX || startY == endY){
			res = true;
		}
		return res;
	}
	
	public ArrayList<Coordonnee> getNeighbors() throws LocationCoordException{
		ArrayList<Coordonnee> neighbors = new ArrayList<Coordonnee>();
		int coordX = this.coordX;
		int coordY = this.coordY;
		if(coordX-1 > 0){
			Coordonnee cLeft = new Coordonnee(coordX-1, coordY);
			neighbors.add(cLeft);
		}
		if(coordX+1 <= Parameter.getNbCoordX()){
			Coordonnee cRight = new Coordonnee(coordX+1, coordY);
			neighbors.add(cRight);
		}
		if(coordY-1 > 0){
			Coordonnee cUp = new Coordonnee(coordX, coordY-1);
			neighbors.add(cUp);
		}
		if(coordY+1 <= Parameter.getNbCoordY()){
			Coordonnee cDown = new Coordonnee(coordX, coordY+1);
			neighbors.add(cDown);
		}
		
		return neighbors;
		
	}
	
	
}
