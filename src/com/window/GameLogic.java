package com.window;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameLogic {

	int nbPlayers;
	int nbAI;
	int nbKings;

	boolean dynasty;
	boolean harmony;
	boolean middleEmpire;
	boolean greatDuel;
	boolean apocalypse;
	boolean coop;
	int gameFieldSize;

	int harmonyBonus;
	int middleEmpireBonus;
	int countTurn;
	int countDynasty;
	Domino d;
	ArrayDeque<Domino> dominoList;
	ArrayList<Player> playerList;
	ArrayDeque<Player> playersDeque;
	ArrayDeque<Domino> dominoDeque;
	ArrayDeque<Domino> lastTurnDeque;
	Player[] playerTab;
	ArrayList<Integer> listNum;
	ArrayList<Integer> temp = new ArrayList<Integer>();
	boolean isFirstTurn = true;
	boolean isLastTurn = false;
	boolean isPlaying = false;
	String team1Name = "Team Name 1";
	String team2Name = "Team Name 2";
	String teamComposition = "Blue-Pink / Green-Yellow";
	String winner = "";

	
	public GameLogic() {
		
		this.nbPlayers = 2;
		this.nbAI = 0;
		this.nbKings = 4;
		this.dynasty = false;
		this.harmony = false;
		this.middleEmpire = false;
		this.greatDuel = false;
		this.apocalypse = false;
		this.coop = false;
		this.gameFieldSize = 9;
		this.harmonyBonus = 5;
		this.middleEmpireBonus = 10;
		this.countTurn = 0;
		this.countDynasty = 0;
		this.dominoList = new ArrayDeque<Domino>();
		this.playerList = new ArrayList<Player>();
		this.playersDeque = new ArrayDeque<Player>();
		this.dominoDeque = new ArrayDeque<Domino>();
		this.lastTurnDeque = new ArrayDeque<Domino>();
		this.listNum = new ArrayList<Integer>();
		this.temp = new ArrayList<Integer>();
		this.isFirstTurn = true;
		this.isLastTurn = false;
		this.isPlaying = false;
	}


	public ImageView tileView(int tileID, int squareSize) {
		/*
		 * function to set a view of the tile on screen
		 */
		Image tile = null;
		ImageView tileView = null;
		try {
			switch (tileID) {
			case 1:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\field0.png"));
				break;
			case 2:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\field1.png"));
				break;
			case 3:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\forest0.png"));
				break;
			case 4:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\forest1.png"));
				break;
			case 5:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\sea0.png"));
				break;
			case 6:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\sea1.png"));
				break;
			case 7:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\meadow0.png"));
				break;
			case 8:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\meadow1.png"));
				break;
			case 9:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\meadow2.png"));
				break;
			case 10:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\mine0.png"));
				break;
			case 11:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\mine1.png"));
				break;
			case 12:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\mine2.png"));
				break;
			case 13:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\mountain0.png"));
				break;
			case 14:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\mountain1.png"));
				break;
			case 15:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\mountain2.png"));
				break;
			case 16:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\mountain3.png"));
				break;
			case 17:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\castle.png"));
				break;
			case 99:
				tile = new Image(new FileInputStream("src\\com\\window\\tiles\\out4.png"));
				break;
			default:
				System.out.println("error getting tileView");
			}
		} catch (FileNotFoundException e) {
			System.out.println("error getting tileView");
		}
		tileView = new ImageView(tile);
		tileView.setFitWidth(squareSize);
		tileView.setFitHeight(squareSize);
		return tileView;
	}

	public void gameInitialisation(VBox gameFieldBox, Pane thisTurnPane, Button rotate, Button impossible,
			int squareSize,int topLeftCornerX, int topLeftCornerY, ArrayList<Text> scores, ArrayList<Text> winners, VBox endScreenBox) {
		/*
		 * initialize all the elements necessary to play the game
		 */
		dominoList = new ArrayDeque<Domino>();
		playerList = new ArrayList<Player>();
		playersDeque = new ArrayDeque<Player>();
		dominoDeque = new ArrayDeque<Domino>();
		lastTurnDeque = new ArrayDeque<Domino>();
		listNum = new ArrayList<Integer>();
		countDynasty = 0;
		scores.clear();
		winners.clear();
		countTurn = 0;
		isFirstTurn = true;
		isLastTurn = false;
		isPlaying = false;
		endScreenBox.getChildren().clear();
		gameFieldBox.getChildren().clear();
		thisTurnPane.getChildren().clear();
		thisTurnPane.getChildren().addAll(rotate, impossible);
		
		if (nbPlayers == 2) {
			if (greatDuel) {
				gameFieldSize = 13;
			} else {
				gameFieldSize = 9;
			}
		} else if (nbPlayers == 3) {
			gameFieldSize = 9;
		} else {
			gameFieldSize = 9;
		}

		// creation of the players and AI to play
		Player player1 = new Player("Blue", 1, new Tile[9][9]);
		player1.newField();
		player1.newFieldPane(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, "Blue");

		Player player2 = new Player("Pink", 1, new Tile[9][9]);
		player2.newField();
		player2.newFieldPane(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, "Pink");

		Player player3 = new Player("Green", 1, new Tile[9][9]);
		player3.newField();
		player3.newFieldPane(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, "Green");

		Player player4 = new Player("Yellow", 1, new Tile[9][9]);
		player4.newField();
		player4.newFieldPane(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, "Yellow");

		AI ai1 = new AI("Blue", 1, new Tile[9][9]);
		ai1.newField();
		ai1.newFieldPane(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, "Blue");

		AI ai2 = new AI("Pink", 1, new Tile[9][9]);
		ai2.newField();
		ai2.newFieldPane(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, "Pink");

		AI ai3 = new AI("Green", 1, new Tile[9][9]);
		ai3.newField();
		ai3.newFieldPane(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, "Green");

		AI ai4 = new AI("Yellow", 1, new Tile[9][9]);
		ai4.newField();
		ai4.newFieldPane(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, "Yellow");
		

		//fill the player list following the number of players
		if (nbPlayers == 2) {

			if (nbAI == 2) {
				ai1.setNbKings(2);
				ai2.setNbKings(2);

				playerList.add(ai1);
				playerList.add(ai2);
			} else if (nbAI == 1) {
				player1.setNbKings(2);
				ai2.setNbKings(2);

				playerList.add(player1);
				playerList.add(ai2);
			} else {
				player1.setNbKings(2);
				player2.setNbKings(2);

				playerList.add(player1);
				playerList.add(player2);
			}
			if (greatDuel) {
				this.gameFieldSize = 13;
				playerList.get(0).setGameField(new Tile[13][13]);
				playerList.get(0).newField();
				playerList.get(0).newFieldPane(this.gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, playerList.get(0).getKing());
				playerList.get(1).setGameField(new Tile[13][13]);
				playerList.get(1).newField();
				playerList.get(1).newFieldPane(this.gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, playerList.get(1).getKing());	
			} 
			nbKings = 4;

		} else if (nbPlayers == 3) {
			if (nbAI == 3) {
				playerList.add(ai1);
				playerList.add(ai2);
				playerList.add(ai3);
			} else if (nbAI == 2) {
				playerList.add(player1);
				playerList.add(ai2);
				playerList.add(ai3);
			} else if (nbAI == 1) {
				playerList.add(player1);
				playerList.add(player2);
				playerList.add(ai3);
			} else {
				playerList.add(player1);
				playerList.add(player2);
				playerList.add(player3);
			}
			nbKings = 3;

		} else if (nbPlayers == 4) {
			if (nbAI == 4) {
				playerList.add(ai1);
				playerList.add(ai2);
				playerList.add(ai3);
				playerList.add(ai4);
			} else if (nbAI == 3) {
				playerList.add(player1);
				playerList.add(ai2);
				playerList.add(ai3);
				playerList.add(ai4);
			} else if (nbAI == 2) {
				playerList.add(player1);
				playerList.add(player2);
				playerList.add(ai3);
				playerList.add(ai4);
			} else if (nbAI == 1) {
				playerList.add(player1);
				playerList.add(player2);
				playerList.add(player3);
				playerList.add(ai4);
			} else {
				playerList.add(player1);
				playerList.add(player2);
				playerList.add(player3);
				playerList.add(player4);
			}
			//creation of teams if cooperation mode is selected
			if (coop) {
				ArrayList<Player> team1 = new ArrayList<Player>(Arrays.asList(playerList.get(0)));
				ArrayList<Player> team2 = new ArrayList<Player>();

				if (teamComposition.compareTo("Blue-Pink / Green-Yellow") == 0) {
					team1.add(playerList.get(1));
					team2.add(playerList.get(2));
					team2.add(playerList.get(3));
				} else if (teamComposition.compareTo("Blue-Green / Pink-Yellow") == 0) {
					team1.add(playerList.get(2));
					team2.add(playerList.get(1));
					team2.add(playerList.get(3));
				} else if (teamComposition.compareTo("Blue-Yellow / Pink-Green") == 0) {
					team1.add(playerList.get(3));
					team2.add(playerList.get(2));
					team2.add(playerList.get(1));
				}

				for (Player p : team1) {
					p.setTeam(team1Name);
				}
				for (Player p : team2) {
					p.setTeam(team2Name);
				}
			}
			nbKings = 4;
		}
		//add a null player in the table if apocalypse mode is selected
		if (apocalypse) {
			playerTab = new Player[nbKings + 1];
		} else {
			playerTab = new Player[nbKings];
		}
	}
	
	@SuppressWarnings("unchecked")
	public void beginningState(Pane nextTurnPane, Pane sndNextTurnPane, Text[] num, Button[] sndNum) {
		/*
		 * fill all the elements not yet filled
		 */
		ArrayList<Player> playerL = (ArrayList<Player>) playerList.clone();	
								//clone to avoid problems with functions using size of player list
		int count = playerList.size();
			//for two players: need a second pointer on each players
		if (count == 2) {
			while (playerL.size() > 0) {
				int random = (int) (Math.random() * count); // random integer between 0 and size - 1
				playersDeque.add(playerL.get(random)); // adds a random player to the dequeue
				playersDeque.add(playerL.get(random)); // adds a second time for the second king
				playerL.remove(random);
				count--;
			}
		} else {		//other cases (3 or 4 players)
			while (count > 0) {
				int random = (int) (Math.random() * count);
				playersDeque.add(playerL.get(random));
				playerL.remove(random);
				count--;
			}
		}
		
		HashMap<Integer, Domino> newMap = dominoMapCreation();	//creation of the deck
		mixDom(newMap, playerList.size());						//creation of the mixed list of domino for the game
		int count2 = 4;
		if ((nbKings == 3) && (apocalypse == false)) {
			count2 --;
		}
		for (int i = 0; i < count2; i++) {	 //add an empty domino at the end: this can let players play their last turn
			Tile t = new Tile("Out", 0, 99);
			Domino d = new Domino(t, t, 99);
			dominoList.add(d);
		}
		nextTurnList(nbKings);			//list for the next turn
		dispDominos(nextTurnPane, sndNextTurnPane, num, sndNum);					//print dominos for the next turn
		temp = (ArrayList<Integer>) listNum.clone();		
								//initialization of a temp list to erase elements of listNum when a domino is chosen
	}
	
	public void dynastyReset(VBox gameFieldBox, int gameFieldSize, int squareSize, int topLeftCornerX, int topLeftCornerY) {
		/*
		 * reset fields, booleans, AI dominos keep in memory, create a new random domino list
		 */
		gameFieldBox.getChildren().clear();
		countTurn = 0;
		isFirstTurn = true;
		isLastTurn = false;
		ArrayList<String> kingList = new ArrayList<String>();
		for (int j = 0; j < playerList.size(); j++) {
			kingList.add(playerList.get(j).getKing());
			int points = playerList.get(j).getPoints();
			System.out.println("The " + playerList.get(j).getKing() + " King currently has " + points + " points !");
			playerList.get(j).newField();
			playerList.get(j).newFieldPane(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, playerList.get(j).getKing());
			if (playerList.get(j).isHuman == false) {
				AI ai = (AI) playerList.get(j);
				ai.setDomino(new Domino(new Tile(), new Tile(), 0));
				ai.setDomino2(new Domino(new Tile(), new Tile(), 0));
				ai.setDominoNext(new Domino(new Tile(), new Tile(), 0));
				ai.setDominoNext2(new Domino(new Tile(), new Tile(), 0));
				ai.setDomPosition(new ArrayList<Integer>());
				ai.setDomPosition2(new ArrayList<Integer>());
				ai.setDomPositionNext(new ArrayList<Integer>());
				ai.setDomPositionNext2(new ArrayList<Integer>());
				ai.setCount(0);
				ai.setCount2Players(1);
				playerList.set(j, ai);
			}
		}
	}

	public HashMap<Integer, Domino> dominoMapCreation() {
		/*
		 * Creates a HashMap of the dominos from the .csv (Comma Separated Values) file.
		 */

		HashMap<Integer, Domino> dominoHashMap = new HashMap<Integer, Domino>();
		FileReader reader = null;
		BufferedReader table = null;
		try {
			String location = "src\\com\\window\\csv\\dominos2.csv"; 	// dominos file
			reader = new FileReader(location); 							// reads the file
			table = new BufferedReader(reader); 						// places the file's elements in the buffer
			String line = table.readLine(); 							// skips the first line
			while (true) {
				line = table.readLine(); 								// reads a line
				if (line == null) {
					break; 												// breaks the loop if empty line
				} else {
					String[] information = line.split(","); 			// splits data with ',' into a string table
					int sq1 = Integer.parseInt(information[1]); 		// converts string to integer
					int sq2 = Integer.parseInt(information[2]);
					Tile s1 = new Tile(" ", 0, sq1);
					s1.setField(sq1);
					s1.setNbCrowns(sq1);
					Tile s2 = new Tile(" ", 0, sq2);
					s2.setField(sq2);
					s2.setNbCrowns(sq2);
					int key = Integer.parseInt(information[0]); 	// key associated with the domino
					Domino dom = new Domino(s1, s2, key); 			// domino creation
					dominoHashMap.put(key, dom);
				}
			}
		} catch (IOException e) {
			e.printStackTrace(); // indicates where's the error and which method is problematic
			System.out.println("The file is untraceable."); // error message if untraceable file
		} finally { // executed in all cases
			try {
				table.close(); // closes the buffer reader
				reader.close(); // closes the file reader
			} catch (IOException e2) {
				e2.printStackTrace();
				System.out.println("The file is untraceable.");
			}
		}
		return dominoHashMap;
	}

	public void mixDom(HashMap<Integer, Domino> map, int nbPlayers) {
		/*
		 * Mixes the dominos and returns the right number of them depending on the
		 * number of players.
		 */

		int size = map.size(); // HashMap size
		ArrayList<Integer> keyList = new ArrayList<Integer>(); // list of domino numbers filled with a for loop
		for (int j = 1; j <= size; j++) {
			keyList.add(j);
		}

		int nbDominos = 12 * nbPlayers; // number of dominos necessary for playing
		if (greatDuel) {
			nbDominos = 48;
		}
		if (apocalypse) {
			nbDominos = 12 * (nbPlayers + 1);
		}

		for (int i = 0; i < nbDominos; i++) {
			int random = (int) (Math.random() * (size - 1)); // random integer between 0 and size - 1
			int index = keyList.get(random);
			dominoList.add(map.get(index)); // adds the domino in the list that will be returned
			size--;
			keyList.remove(random);
		}

	}

	public void nextTurnList(int nbPlayers) {
		/*
		 * Picks the [nbKings] first dominos of the list and puts them in a dequeue.
		 */

		int nbKings = 0;
		if (nbPlayers == 2 || nbPlayers == 4 || apocalypse) {
			nbKings = 4;
		} else if (nbPlayers == 3) {
			nbKings = 3;
		}

		lastTurnDeque.clear();		//clear queue to be sure all the dominos are played or discarded with apocalypse mode
		
		if (isFirstTurn == false) {			//for turns which are not the first one
			for (int i = 0; i < nbKings; i++) {
				lastTurnDeque.add(dominoDeque.poll());	//lastTurnDeque takes the dominos of dominoDeque played this turn
			}
		}
		
		if (isLastTurn == false) {			//for turns which are not the last one
			for (int i = 0; i < nbKings; i++) {
				dominoDeque.add(dominoList.poll());		//dominoDeque takes the dominos of dominoList chosen this turn
			}
			dominoDeque = sortQueue(dominoDeque);
		}
	}

	public ArrayDeque<Domino> sortQueue(ArrayDeque<Domino> queue) {
		/*
		 * Sorts a dequeue of dominos by their numbers.
		 */
		ArrayDeque<Domino> dominosDeque = new ArrayDeque<Domino>();
		ArrayList<Domino> dominosList = new ArrayList<Domino>(); 		// dominos list
		ArrayList<Integer> numbersList = new ArrayList<Integer>(); 		// domino numbers list
		for (int i = 0; i < queue.size(); i++) {
			Domino dom = queue.poll();
			int number = dom.getNumber();
			numbersList.add(number);
			dominosList.add(dom);
			queue.add(dom);
		}
		try {
			int i = numbersList.size();
			while (i > 0) { 												// loops on the size of the list
				int n = numbersList.indexOf(Collections.min(numbersList));
				Domino d = dominosList.get(n); 								// domino with the smallest number
				dominosDeque.add(d); 										// adds this domino in the dequeue
				numbersList.remove(n); 										// removes it from the list
				dominosList.remove(n);
				i -= 1;
			}
		} catch (NullPointerException e) { 									// error message if the list is empty
			System.out.println("Error, the list is empty !");
		}
		return dominosDeque; 												// returns the sorted dequeue
	}

	public void playFirstTurn(VBox gameFieldBox, int windowWidth, int windowHeight, Button[] sndNum) {
		/*
		 * first turn played by the AI
		 */
		Player player = playersDeque.poll();
		String king = player.getKing();
		player.setGameFieldForWindow(gameFieldBox, windowWidth, windowHeight);	//set the game field on the screen

		AI ai = (AI) player;
		int numDom = ai.researchDomino(dominoDeque, playerList, listNum, countTurn, middleEmpire,
						middleEmpireBonus, coop);

		int order = temp.indexOf(numDom);

		sndNum[order].setStyle("-fx-background-color: " + king);
		sndNum[order].setDisable(true);
		int tberased = listNum.indexOf(numDom);
		listNum.remove(tberased); 							// (.remove takes for argument the index)
		playerTab[order] = player; 							// this player will play in [order]st position next turn

	}

	@SuppressWarnings("unchecked")
	public void dispDominos(Pane nextTurnPane, Pane sndNextTurnPane, Text[] num, Button[] sndNum) {
		/*
		 * Displays the available dominos for this turn and returns the list of their
		 * numbers.
		 */
		
		listNum.clear();		//clear listNum to be sure it is empty: without this, problems could appear in apocalypse mode

		if (isFirstTurn == false) {					//for turns which are not the first one, print dominos of lastTurnQueue
			for (int i = 0; i < lastTurnDeque.size(); i++) {
				Domino dom = lastTurnDeque.poll();
				lastTurnDeque.add(dom);

				num[i].setStyle("-fx-background-color:#FFFFFF");
				num[i].setDisable(false);	
				num[i].setText("" + dom.getNumber());

				Tile tile1 = dom.getSq1();
				Tile tile2 = dom.getSq2();

				int tile1Num = tile1.getNumber();
				int tile2Num = tile2.getNumber();

				ImageView tile1View = tileView(tile1Num, 100);
				tile1View.setX(130);
				tile1View.setY(2 + (i * 100));

				ImageView tile2View = tileView(tile2Num, 100);
				tile2View.setX(230);
				tile2View.setY(2 + (i * 100));
				nextTurnPane.getChildren().addAll(tile1View, tile2View);

			}

		} else {		//print out tile for the first turn. erase the last dominos if the game restart
			for (int i = 0; i < 4; i++) {

				num[i].setDisable(false);	
				num[i].setText("" + 0);

				ImageView tile1View = tileView(99, 100);
				tile1View.setX(130);
				tile1View.setY(2 + (i * 100));

				ImageView tile2View = tileView(99, 100);
				tile2View.setX(230);
				tile2View.setY(2 + (i * 100));
				nextTurnPane.getChildren().addAll(tile1View, tile2View);

			}
			if ((nbKings == 3) && (apocalypse == false)) {
				num[3].setDisable(false);	
				num[3].setText("" + 0);

				ImageView tile1View = tileView(99, 100);
				tile1View.setX(130);
				tile1View.setY(2 + (3 * 100));

				ImageView tile2View = tileView(99, 100);
				tile2View.setX(230);
				tile2View.setY(2 + (3 * 100));
				sndNextTurnPane.getChildren().addAll(tile1View, tile2View);
			}
		}
		for (int i = 0; i < dominoDeque.size(); i++) { 		//print dominos of dominoDeque
			Domino dom = dominoDeque.poll();
			int number = dom.getNumber();
			dominoDeque.add(dom);

			sndNum[i].setText("" + number);
			sndNum[i].setStyle("-fx-background-color:#FFFFFF");
			sndNum[i].setDisable(false);

			listNum.add(number);
			Tile tile1 = dom.getSq1();
			Tile tile2 = dom.getSq2();

			int tile1Num = tile1.getNumber();
			int tile2Num = tile2.getNumber();

			ImageView tile1View = tileView(tile1Num, 100);
			tile1View.setX(130);
			tile1View.setY(2 + (i * 100));

			ImageView tile2View = tileView(tile2Num, 100);
			tile2View.setX(230);
			tile2View.setY(2 + (i * 100));
			sndNextTurnPane.getChildren().addAll(tile1View, tile2View);

		}
		temp = (ArrayList<Integer>) listNum.clone();		
						//clones to be sure temp is the same list as listNum at the beginning of this turn
	}


	public void winner(ArrayList<Text> scores, ArrayList<Text> winners) {
		/*
		 * after the game, this function finds the winner(s)
		 */

		ArrayList<Integer> ptsList = new ArrayList<Integer>();		//list of points
		ArrayList<String> kingList = new ArrayList<String>();		//list of kings

		for (Player player : playerList) {		//fills the two previous lists with the players informations
			kingList.add(player.getKing());
			int points = player.getPoints();
			ptsList.add(points);
			scores.add(new Text("The " + player.getKing() + " King has " + points + " points !"));
		}
		int max = 0;
		if (coop) {					//counts points for each team
			HashMap<String, Integer> teamMap = new HashMap<String, Integer>();	//map with team and score
			ArrayList<String> teamList = new ArrayList<String>();				//list of teams
			Player p = playerList.get(0);
			teamMap.put(p.getTeam(), p.getPoints());			//adds the team and the score of the first player 
			teamList.add(p.getTeam());							//adds the team of the first player
			int j = 0;
			for (int i = 1; i < playerList.size(); i++) {		
						/*
						 * for each player, if he is in the same team as the first player, his score is added at the first
						 * else, create another team if it is not yet done.
						 */
				if (playerList.get(i).getTeam().compareTo(p.getTeam()) == 0) {
					int pts = teamMap.get(p.getTeam());
					teamMap.replace(playerList.get(i).getTeam(), playerList.get(i).getPoints() + pts);
				} else if (j == 0) {
					teamMap.put(playerList.get(i).getTeam(), playerList.get(i).getPoints());
					teamList.add(playerList.get(i).getTeam());
					j++;
				} else {
					int pts = teamMap.get(playerList.get(i).getTeam());
					teamMap.replace(playerList.get(i).getTeam(), playerList.get(i).getPoints() + pts);
				}
			}
			String winnerTeam = ""; //finds the winner team(s)
			if (teamMap.get(teamList.get(0)) >= teamMap.get(teamList.get(1))) {
				max = teamMap.get(teamList.get(0));
				winnerTeam = teamList.get(0);
			} else if (teamMap.get(teamList.get(0)) <= teamMap.get(teamList.get(1))) {
				max = teamMap.get(teamList.get(1));
				winnerTeam = teamList.get(1);
			} else {
				max = teamMap.get(teamList.get(0));
				winnerTeam = teamList.get(0) + " team & the " + teamList.get(1);
			}
			winners.add(new Text(winnerTeam + " Team with " + max + " points !")); //adds to the text list

		}
		for (int j = 0; j < playerList.size(); j++) {	//finds the winner(s)
			if (ptsList.get(j) >= max) {
				max = ptsList.get(j);
			}
		}

		ArrayList<String> winnerList = new ArrayList<String>();
		for (int k = 0; k < playerList.size(); k++) {
			if (ptsList.get(k) == max) {
				winnerList.add(kingList.get(k));
			}
		}

		for (String king : winnerList) {
			winners.add(new Text(king + " King !"));	//adds to text list
		}

	}

	public void endGame() {
		/*
		 * counts points with modes if selected
		 */

		for (int i = 0; i < playerList.size(); i++) {
			if (middleEmpire) {				//verifies the castle is in the middle of the kingdom and adds points if true
				Tile[][] gameField = playerList.get(i).getGameField();
				int indexEdge1 = Math.round(gameField.length / 4);
				int indexEdge2 = Math.round(3 * gameField.length / 4);
				if ((gameField[indexEdge1][indexEdge1].getNumber() != 99)
						&& (gameField[indexEdge1][indexEdge2].getNumber() != 99)
						&& (gameField[indexEdge2][indexEdge1].getNumber() != 99)
						&& (gameField[indexEdge2][indexEdge2].getNumber() != 99)) {
					playerList.get(i).points += (playerList.get(i).nbPoints() + middleEmpireBonus);
				} else {
					playerList.get(i).points += playerList.get(i).nbPoints();
				}
			} else {
				playerList.get(i).points += playerList.get(i).nbPoints();
			}
			if (harmony) {			//adds points if the kingdom is full
				int count = 0;
				harmonyPoints: for (int k = 0; k < playerList.get(i).getGameField().length; k++) {
					for (int l = 0; l < playerList.get(i).getGameField().length; l++) {
						if (playerList.get(i).getGameField()[k][l].getNumber() == 0) {
							break harmonyPoints;
						} else {
							count++;
						}
					}
				}
				if (count == playerList.get(i).getGameField().length * playerList.get(i).getGameField().length) {
					playerList.get(i).points += harmonyBonus;
				}
			}
		}
	}
}
