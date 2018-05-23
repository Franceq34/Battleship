package fr.battleship.sources;
import java.util.*;

import fr.battleship.exceptions.LocationCoordException;

public abstract class Player {
	
	protected String name;
	
	protected List<Ship> tabShip;

	protected Hashtable<Integer, Integer> shipAvailable;
	
	protected List<Tir> shootHistory;

	protected List<Tir> shotReceived;
	
	public Player() {
		Hashtable<Integer, Integer> shipAvailable =  new Hashtable<Integer, Integer>(Parameter.getNbShip());
		this.tabShip = new ArrayList<Ship>();
		this.shipAvailable = shipAvailable;
		this.shootHistory = new ArrayList<Tir>();
		this.shotReceived = new ArrayList<Tir>();
	}

	public String getName() {
		return this.name;
	}
	
	public List<Ship> getTabShip() {
		return tabShip;
	}
	

	public void setTabShip(List<Ship> tabShip) {
		this.tabShip = tabShip;
	}

	public Hashtable<Integer, Integer> getShipAvailable() {
		return this.shipAvailable;
	}
	
	public void setShipAvailable(int taille, int nb) {
		this.shipAvailable.remove(taille);
		this.shipAvailable.put(taille, nb);
	}
	
	public int nbShipAvailable(int taille) {
		int res = 0;
		if(this.shipAvailable.containsKey(taille)){
			res = this.shipAvailable.get(taille);
		}
		return res;
	}
	
	public void removeShipAvailable(int taille){
		if(this.shipAvailable.containsKey(taille) && this.shipAvailable.get(taille)>0){
			this.setShipAvailable(taille, this.shipAvailable.get(taille) - 1);
		}
	}
	
	public List<Tir> getShootHistory() {
		return shootHistory;
	}
	
	public void addShootHistory(Tir t) {
		shootHistory.add(t);
	}
	
	public boolean alreadyShotAt(Coordonnee c){
		boolean res = false;
		for (Tir t : this.shootHistory) {
		    if(c.equals(t.getPosition())){
		    	res = true;
		    }
		}
		return res;
	}

	public void addShip(Ship ship){
		this.tabShip.add(ship);
	}

	public Ship getShip(Coordonnee c1){
		for (Ship ship : this.tabShip) {
			for (Coordonnee c2 : ship.getTabCoordonnee()) {
			    if(c1.equals(c2)){
			    	return ship;
			    }
			}
		}
		return null;
	}
	
	public boolean canPlace(Ship ship){
		boolean res = true;
		for (Coordonnee c1 : ship.getTabCoordonnee()) {
			for (Ship currentShip : this.getTabShip()) {
				for (Coordonnee c2 : currentShip.getTabCoordonnee()) {
				    if(c1.equals(c2)){
				    	res=false;
				    }
				}
			}
		}
		return res;
	}
	
	public int getShot(Coordonnee c){
		int res = -1;
		Ship shipTouched = this.getShip(c);
		//Renvoie 0 pour à l'eau, 1 pour touché, 2 pour coulé
		if(shipTouched != null){
			shipTouched.hit(c);
			if (shipTouched.isDestroyed()){
				res = 2;
			}
			else{
				res = 1;
			}
		}
		else{
			res = 0;
		}
		Tir t = new Tir(c, res);
		this.shotReceived.add(t);
		return res;
	}
	
	public boolean isDead(){
		boolean res = true;
		for (Ship ship : tabShip){
			if(!ship.isDestroyed()){
				res = false;
			}
		}
		return res;
	}
	
	public void showGrid(){
		System.out.print("  ");
		for(int i = 65; i<Parameter.getNbCoordX()+65; i++){
			System.out.print("  "+(char)i);
		}
		System.out.print("\n");
		for(int i=0; i<Parameter.getNbCoordY(); i++){
				System.out.print(i+1 + " ");
				if(i+1<10){
					System.out.print(" ");
				}
				Ship ship = null;
				for(int j=0; j<Parameter.getNbCoordX(); j++){
					Coordonnee c = null;
					try{
						c = new Coordonnee(j+1, i+1);
					} catch (LocationCoordException e) {
						System.out.println(e);
					}
					ship = this.getShip(c);
					if(ship != null){
						System.out.print("|"+ ship.length()+"_");
						
					}
					else{
						System.out.print("|__");
					}
			}
			System.out.println("|");
		}
		System.out.println("\n");
	}
	
	public void showShipLeft(){
		Set<Integer> keys = this.shipAvailable.keySet();
        for(Integer key : keys){
        	if(shipAvailable.get(key) != 0){
            	System.out.println( shipAvailable.get(key) + " bateaux de taille " + key );
        	}
        }
	}
	
	public void displayShots(){
		System.out.println("VOS TIRS :\n");
		System.out.print("  ");
		for(int i = 65; i<Parameter.getNbCoordX()+65; i++){
			System.out.print("  "+(char)i);
		}
		System.out.print("\n");
		for(int i=0; i<Parameter.getNbCoordY(); i++){
				System.out.print(i+1 + " ");
				if(i+1<10){
					System.out.print(" ");
				}
				for(int j=0; j<Parameter.getNbCoordX(); j++){
					Coordonnee c = null;
					try{
						c = new Coordonnee(j+1, i+1);
					} catch (LocationCoordException e) {
						System.out.println(e);
					}
					if(this.alreadyShotAt(c)){
						int resShot = getResShot(c);
						if(resShot==2 || resShot == 1){
							System.out.print("|o_");
						}
						else{
							System.out.print("|~_");
						}
					}
					else{
						System.out.print("|__");
					}
			}
			System.out.println("|");
		}
		System.out.println("\n");
	}
	
	public void displayShotsReceived(){
		System.out.println("LES TIRS ADVERSES :\n");
		System.out.print("  ");
		for(int i = 65; i<Parameter.getNbCoordX()+65; i++){
			System.out.print("  "+(char)i);
		}
		System.out.print("\n");
		for(int i=0; i<Parameter.getNbCoordY(); i++){
				System.out.print(i+1 + " ");
				if(i+1<10){
					System.out.print(" ");
				}
				for(int j=0; j<Parameter.getNbCoordX(); j++){
					Coordonnee c = null;
					try{
						c = new Coordonnee(j+1, i+1);
					} catch (LocationCoordException e) {
						System.out.println(e);
					}
					if(this.alreadyGetShotAt(c)){
						Ship ship = this.getShip(c);
						if(ship == null){
							System.out.print("|~_");
						}
						else if(ship.isDestroyed()){
							System.out.print("|x_");
						} else {
							System.out.print("|o_");
						}
					}
					else{
						System.out.print("|__");
					}
			}
			System.out.println("|");
		}
		System.out.println("\n");
	}

	public boolean alreadyGetShotAt(Coordonnee c){
		boolean res = false;
		for(Tir t: this.shotReceived){
			if(t.getPosition().equals(c)){
				res = true;
			}
		}
		return res;
	}
	
	public int getResShot(Coordonnee c){
		int res = -1;
		for(Tir t: shootHistory){
			if(t.getPosition().equals(c)){
				res = t.getResult();
			}
		}
		return res;
	}
	
	public void reset(){
		Hashtable<Integer, Integer> shipAvailable =  new Hashtable<Integer, Integer>(Parameter.getNbShip());
		this.tabShip = new ArrayList<Ship>();
		this.shipAvailable = shipAvailable;
		this.shootHistory = new ArrayList<Tir>();
		this.shotReceived = new ArrayList<Tir>();
	};

	abstract Ship askCoordsShip();
	
	abstract Coordonnee askCoordShot();
}
