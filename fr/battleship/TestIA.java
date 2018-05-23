package fr.battleship;

import fr.battleship.sources.Game;
import fr.battleship.sources.Hard;
import fr.battleship.sources.Medium;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import fr.battleship.sources.Beginner;
import fr.battleship.sources.IA;
import fr.battleship.sources.Player;

public class TestIA {
	
	private static int nbGames = 100;
	
	public static Game init(IA j1, IA j2){
		return new Game(j1, j2);
	}
	
	public static int launchTest(int levelJ1, int levelJ2){
		int resJ1 = 0;
		IA j1;
		IA j2;
		switch (levelJ1)
		{
		  case 0:
			  j1 = new Beginner();
			  break;
		  case 1:
			  j1 = new Medium();
			  break;
		  case 2:
			  j1 = new Hard();
			  break;
		  default:
			  j1 = new Beginner();
		}
		switch (levelJ2)
		{
		  case 0:
			  j2 = new Beginner();
			  break;
		  case 1:
			  j2 = new Medium();
			  break;
		  case 2:
			  j2 = new Hard();
			  break;
		  default:
			  j2 = new Beginner();
		}
		for(int i=0; i<nbGames; i++){
			Game game = init(j1, j2);
			game.placeShips();
			Player winner = game.start();
			if(winner.equals(j1)){
				resJ1 +=1;
			}
			game.reset();
		}
		return resJ1;
	}
	
	public static void main(String args[]){
		int res0vs1 = launchTest(0, 1);
		int res0vs2 = launchTest(0, 2);
		int res1vs2 = launchTest(1, 2);
        File f;
        FileWriter writer;
        BufferedWriter bw;
		f = new File(System.getProperty("user.dir")+ File.separator + "ai_proof.csv");
		try {
			writer = new FileWriter(f);
			bw = new BufferedWriter(writer);
			StringBuilder sb = new StringBuilder();
			sb.append("AI Name;score;AI Name2; score2");
			sb.append("\n");
			sb.append("Level Beginner");
			sb.append(";");
			sb.append(res0vs1);
			sb.append(";");
			sb.append("Level Medium");
			sb.append(";");
			sb.append(nbGames - res0vs1);
			sb.append("\n");
			
			sb.append("Level Beginner");
			sb.append(";");
			sb.append(res0vs2);
			sb.append(";");
			sb.append("Level Hard");
			sb.append(";");
			sb.append(nbGames - res0vs2);
			sb.append("\n");
			
			sb.append("Level Medium");
			sb.append(";");
			sb.append(res1vs2);
			sb.append(";");
			sb.append("Level Hard");
			sb.append(";");
			sb.append(nbGames - res1vs2);
			sb.append("\n");
			
			bw.write(sb.toString());
			bw.close();
			System.out.println("Fichier mis à jour.");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
}
	}

