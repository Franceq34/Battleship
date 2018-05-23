package fr.battleship.sources;
import java.util.Random;


public class Game {

	private Player j1;
	
	private Player j2;
    
	private Player currentPlayer;
	
	private Player startingPlayer;
	
	private boolean gameOver;
	
	private Player winner;
    
    private Random random;
	
	public Game(Player j1, Player j2) {
		this.j1 = j1;
		this.j2 = j2;
		this.gameOver = false;
		this.winner = null;
		this.random = new Random();
		boolean j1Begin = random.nextBoolean();
		if(j1Begin){
			this.currentPlayer = this.j1;
		}
		else{
			this.currentPlayer = this.j2;
		}
		this.startingPlayer = currentPlayer;
	}

	public Player getJ1() {
		return j1;
	}

	public void setJ1(Player j1) {
		this.j1 = j1;
	}

	public Player getJ2() {
		return j2;
	}

	public void setJ2(Player j2) {
		this.j2 = j2;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Player getStartingPlayer() {
		return currentPlayer;
	}
	
	public Player getOtherPlayer(){
		if(j1 == currentPlayer){
			return j2;
		}
		else{
			return j1;
		}
	}
	
	public boolean isIA(){
		return this.currentPlayer instanceof IA;
	}
	
	public void setCurrentPlayer(Player p){
		currentPlayer = p;
	}
	
	public void changeCurrentPlayer(){
		if (this.currentPlayer == this.j1){
			currentPlayer = j2;
		}
		else{
			currentPlayer = j1;
		}
	}
	
	public boolean isOver() {
		return this.gameOver;
	}
	
	public void end(){
		this.gameOver = true;
	}
	
	public Player getWinner(){
		return this.winner;
	}
	
	public void setWinner(Player winner){
		this.winner = winner;
	}
	
	public void showInfos(){
		System.out.println("Vos deux joueurs sont :\n"
				+ this.j1.getName() + " et " + this.j2.getName());
		try{
		    Thread.sleep(100);
		} 
		catch(InterruptedException ex){
		    Thread.currentThread().interrupt();
		}
		System.out.println(Parameter.show());
		try{
		    Thread.sleep(300);
		} 
		catch(InterruptedException ex){
		    Thread.currentThread().interrupt();
		}
		System.out.println("\nVoici la grille :");
		this.getCurrentPlayer().showGrid();
	}
	
	public void placeShips(){
		for(int k=0; k<2; k++){
			int nbShipLeft = Parameter.getNbShipTotal();
			if(this.currentPlayer instanceof Human){
				System.out.println("\n" + this.getCurrentPlayer().getName() + ", à toi de placer tes bateaux.");
			}
			while(nbShipLeft != 0){
				Ship ship = null;
				while(ship == null){
					if(this.currentPlayer instanceof Human){
						System.out.println("Il te reste " + nbShipLeft + " bateaux à placer.\n");
						this.currentPlayer.showShipLeft();
					}
					ship = this.currentPlayer.askCoordsShip();
				}
				if(this.currentPlayer instanceof Human){
					System.out.println("\nVoici ta nouvelle grille :");
					this.getCurrentPlayer().showGrid();
				}
				nbShipLeft -= 1;
			}
			this.changeCurrentPlayer();
		}
		if(this.currentPlayer instanceof Human || this.getOtherPlayer() instanceof Human){
			System.out.println("Les deux joueurs ont correctement placé leurs bateaux. La partie peut commencer.");
		}
	}
	
	public Player start(){
		while(!this.isOver()){
			if(this.currentPlayer instanceof Human){
				System.out.println("C'est au tour de " + this.getCurrentPlayer().getName());
				this.currentPlayer.displayShots();
				this.currentPlayer.displayShotsReceived();
			}
			Coordonnee coord = this.currentPlayer.askCoordShot();
			int resShoot = this.getOtherPlayer().getShot(coord);
			Tir tir = new Tir(coord, resShoot);
			this.getCurrentPlayer().addShootHistory(tir);
			if(resShoot == 2){
				if(this.getOtherPlayer().isDead()){
					this.end();
					this.setWinner(this.getCurrentPlayer());
				}
			}
		this.changeCurrentPlayer();
		}
		if(this.currentPlayer instanceof Human || this.getOtherPlayer() instanceof Human){
			System.out.println("Partie terminée ! Bravo au vainqueur, "+this.getWinner().getName());
		}
		return this.getWinner();
	}
	
	
	public void reset(){
		j1.reset();
		j2.reset();
		this.gameOver = false;
		this.winner = null;
		if(startingPlayer == currentPlayer){
			currentPlayer = getOtherPlayer();
		}
		startingPlayer = getOtherPlayer();
	
	}
}
