package france.quentin;

import fr.battleship.sources.Game;
import fr.battleship.sources.Beginner;
import fr.battleship.sources.Hard;
import fr.battleship.sources.Medium;
import fr.battleship.sources.Player;
import fr.battleship.sources.Human;

public class Battleship{
	
	public static Game init(){
		System.out.println("Bienvenue dans Battleship !");
		System.out.println("Entrez le nom du premier joueur :");
		String nomJ1 = Human.askName();
		Human j1 = new Human(nomJ1);
		Player j2;
		System.out.println("Souhaitez vous jouer contre un ami ou l'ordinateur ?\n[ 0: ami | 1: ordinateur facile | 2: ordinateur moyen | 3: ordinateur difficile ]");
		int choix = Human.askMode();
		switch (choix)
		{
		  case 0:
			System.out.println("Entrez le nom du second joueur :");
			String nomJ2 = Human.askName();
			j2 = new Human(nomJ2);
		    break;
		  case 1:
			j2 = new Beginner();
		    break;
		  case 2:
			j2 = new Medium();
		    break;
		  case 3:
			j2 = new Hard();
		    break;
		   default:
			System.out.println("Entrez le nom du second joueur :");
			String nameJ2 = Human.askName();
			j2 = new Human(nameJ2);
		}
		return new Game(j1, j2);
	}
	
	public static void main(String args[]){
		boolean continuePlaying = true;
		Game game = init();
		int winsJ1 = 0;
		int winsJ2 = 0;
		Player j1 = game.getJ1();
		while(continuePlaying){
			game.showInfos();
			game.placeShips();
			Player winner = game.start();
			if(winner == j1){
				winsJ1 += 1;
			} else {
				winsJ2 += 1;
			}
			game.reset();
			continuePlaying = Human.askContinuePlaying();
		}
		System.out.println("Le joueur 1 a gagné un total de " + winsJ1 +" fois, et l'adversaire "+ winsJ2 +" fois.");
   }
}
