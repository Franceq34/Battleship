package fr.battleship.sources;
import java.util.*;

import fr.battleship.exceptions.LocationCoordException;

public class Ship {

	private Coordonnee startCoord;
	
	private Coordonnee endCoord;

	private List<Coordonnee> tabCoordonnee;
	
	private List<Coordonnee> touched;
	
	public Ship(Coordonnee startCoord, Coordonnee endCoord){
		this.startCoord = startCoord;
		this.endCoord = endCoord;
		this.tabCoordonnee = new ArrayList<Coordonnee>();
		int shipStartX = startCoord.getCoordX();
		int shipStartY = startCoord.getCoordY();
		int shipEndX = endCoord.getCoordX();
		int shipEndY = endCoord.getCoordY();
		if(startCoord.getCoordX() == endCoord.getCoordX()){
			if(shipStartY<shipEndY){
				for(int i=shipStartY; i<=shipEndY; i++){
					Coordonnee c = null;
					try{
						c = new Coordonnee(shipStartX, i);
					} catch (LocationCoordException e) {
						System.out.println(e);
					}
					this.tabCoordonnee.add(c);
				}
			}else{
				for(int i=shipEndY; i<=shipStartY; i++){
					Coordonnee c = null;
					try{
						c = new Coordonnee(shipStartX, i);
					} catch (LocationCoordException e) {
						System.out.println(e);
					}
					this.tabCoordonnee.add(c);
				}
			}
		}else{
			if(shipStartX<shipEndX){
				for(int i=shipStartX; i<=shipEndX; i++){
					Coordonnee c = null;
					try{
						c = new Coordonnee(i, shipStartY);
					} catch (LocationCoordException e) {
						System.out.println(e);
					}
					this.tabCoordonnee.add(c);
				}
			}else{
				for(int i=shipEndX; i<=shipStartX; i++){
					Coordonnee c = null;
					try{
						c = new Coordonnee(i, shipStartY);
					} catch (LocationCoordException e) {
						System.out.println(e);
					}
					this.tabCoordonnee.add(c);
				}
			}
		}
		
		this.touched = new ArrayList<Coordonnee>();
	}

	public List<Coordonnee> getTabCoordonnee() {
		return tabCoordonnee;
	}

	public List<Coordonnee> getTouched() {
		return touched;
	}

	public Coordonnee getStartCoord() {
		return startCoord;
	}

	public Coordonnee getEndCoord() {
		return endCoord;
	}
	
	public int length(){
		int startX = startCoord.getCoordX();
		int endX = endCoord.getCoordX();
		int startY = startCoord.getCoordY();
		int endY = endCoord.getCoordY();
		
		if(endX == startX){
			return Math.abs(startY - endY)+1;
		}
		else{
			return Math.abs(startX - endX)+1;
		}
	}
	
	public boolean isVertical(){
		return this.getStartCoord().getCoordX() == this.getEndCoord().getCoordX();
	}
	
	public boolean isHorizontal(){
		return !this.isVertical();
	}
	
	public void hit(Coordonnee coord){
		this.touched.add(coord);
	}
	
	public boolean isHit(){
		return touched.size() != 0;
	}
	
	public boolean isDestroyed(){
		return touched.size() == this.length();
	}
	
}
