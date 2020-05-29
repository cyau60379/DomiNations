package com.console;

import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
/**
 * 
 * This is the main program.
 *
 */
public class Main {

	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		
		System.out.print("Welcome to Domi'Nations !");
		
		int nbPlayers = 0;
		int nbKings = 4;
		
		/*
		 *  ========================================== Modes 
		 */
		
		boolean grandDuel = false;
		
		boolean dynasty = false;
		
		boolean harmony = false;
		int valueHarmony = 5;
		
		boolean middleEmpire = false;
		int valueMidEmpire = 10;
		
		boolean coop = false;
		
		boolean apocalypse = false;
		
		/*
		 *  ================================================
		 */
		
		while (true) {
			try {
				System.out.println();
				System.out.print("Choose the number of players [2 to 4]: ");
				nbPlayers = scan.nextInt(); 
				if (nbPlayers >= 2 && nbPlayers <= 4) {
					break;
				} else {
					System.out.println("Choose between 2 and 4 players !");
				}
			} catch (InputMismatchException e) {
				System.out.println("This is not an integer !");
				scan.next();
			}
		}
		int nbAI = 0;
		
		while (true) {
			try {
				System.out.println();
				System.out.print("Choose the number of AI [0 to " + nbPlayers + "]: ");
				nbAI = scan.nextInt(); 
				if (nbAI >= 0 && nbAI <= nbPlayers) {
					break;
				} else {
					System.out.println("Choose between 0 and " + nbPlayers + " AI !");
				}
			} catch (InputMismatchException e) {
				System.out.println("This is not an integer !");
				scan.next();
			}
		}
		
		int dynastyChoice;
		while (true) {
			System.out.println();
			System.out.println("Dynasty: You play 3 rounds. The winner is the one who has the higher total score at the end.");
			System.out.println("Would you play in Dynasty mode ? [0 = no / 1 = yes]");
			try {
				dynastyChoice = scan.nextInt(); 
				if (dynastyChoice == 1) {
					dynasty = true;
					break;
				} else if (dynastyChoice == 0) {
					break;
				} else {
					System.out.println("This is not possible !");
				}
			} catch (InputMismatchException e) {
				System.out.println("This is not an integer !");
				scan.next();
			}
		}
		
		
		int harmonyChoice;
		while (true) {
			System.out.println();
			System.out.println("Harmony: points will be added for those who complete their game field.");
			System.out.println("(The players choose the number of points added)");
			System.out.println("Would you play in Harmony mode ? [0 = no / 1 = yes]");
			try {
				harmonyChoice = scan.nextInt(); 
				if (harmonyChoice == 1) {
					harmony = true;
					System.out.print("How many points ?");
					int choice;
					while (true) {
						try {
							choice = scan.nextInt();
							valueHarmony = choice;
							break;
						} catch (InputMismatchException e) {
							System.out.println("This is not an integer !");
							scan.next();
						}
					}
					break;
				} else if (harmonyChoice == 0) {
					break;
				} else {
					System.out.println("This is not possible !");
				}
			} catch (InputMismatchException e) {
				System.out.println("This is not an integer !");
				scan.next();
			}
		}
		
		int midEmpire;
		while (true) {
			System.out.println();
			System.out.println("Middle Empire: points will be added for those who put their castle in the middle of their game field.");
			System.out.println("(The players choose the number of points added)");
			System.out.println("Would you play in Middle Empire mode ? [0 = no / 1 = yes]");
			try {
				midEmpire = scan.nextInt(); 
				if (midEmpire == 1) {
					middleEmpire = true;
					System.out.print("How many points for a Middle Empire ?");
					int choice;
					while (true) {
						try {
							choice = scan.nextInt();
							valueMidEmpire = choice;
							break;
						} catch (InputMismatchException e) {
							System.out.println("This is not an integer !");
							scan.next();
						}
					}
					break;
				} else if (midEmpire == 0) {
					break;
				} else {
					System.out.println("This is not possible !");
				}
			} catch (InputMismatchException e) {
				System.out.println("This is not an integer !");
				scan.next();
			}
		}
		
		ArrayList<Player> playerList = new ArrayList<Player>();
		
		Player player1 = new Player("Blue", 1, new Tile[9][9]);
		player1.newField();

		Player player2 = new Player("Pink", 1, new Tile[9][9]);
		player2.newField();
		
		Player player3 = new Player("Green", 1, new Tile[9][9]);
		player3.newField();
		
		Player player4 = new Player("Yellow", 1, new Tile[9][9]);
		player4.newField();
		
		
		AI ai1 = new AI("Blue", 1, new Tile[9][9]);
		ai1.newField();

		AI ai2 = new AI("Pink", 1, new Tile[9][9]);
		ai2.newField();
		
		AI ai3 = new AI("Green", 1, new Tile[9][9]);
		ai3.newField();
		
		AI ai4 = new AI("Yellow", 1, new Tile[9][9]);
		ai4.newField();
		
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
			int greatDuel;
			while (true) {
				System.out.println("Great Duel: You play with a 7x7 game field.");
				System.out.println("Would you play in Great Duel mode ? [0 = no / 1 = yes]");
				try {
					greatDuel = scan.nextInt();
					if (greatDuel == 1) {
						grandDuel = true;
						playerList.get(0).setGameField(new Tile[13][13]);
						playerList.get(0).newField();
						playerList.get(1).setGameField(new Tile[13][13]);
						playerList.get(1).newField();
						break;
					} else if (greatDuel == 0) {
						break;
					} else {
						System.out.println("This is not possible !");
						scan.next();
					}
				} catch (InputMismatchException e) {
					System.out.println("This is not an integer !");
					scan.next();
				}
			}
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
			
			int apocalypseChoice;
			while (true) {
				System.out.println();
				System.out.println("Apocalypse: 4 dominos are presented each turn, 1 will be erased.");
				System.out.println("Would you play in Apocalypse mode ? [0 = no / 1 = yes]");
				try {
					apocalypseChoice = scan.nextInt(); 
					if (apocalypseChoice == 1) {
						apocalypse = true;
						break;
					} else if (apocalypseChoice == 0) {
						break;
					} else {
						System.out.println("This is not possible !");
					}
				} catch (InputMismatchException e) {
					System.out.println("This is not an integer !");
					scan.next();
				}
			}
			
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
			
			
			int cooperation;
			while (true) {
				System.out.println();
				System.out.println("Cooperation: 2v2 mode. The team with the highest score win.");
				System.out.println("Would you play in Cooperation mode ? [0 = no / 1 = yes]");
				try {
					cooperation = scan.nextInt(); 
					if (cooperation == 1) {
						coop = true;
						int teamPlayer;
						ArrayList<Player> team1 = new ArrayList<Player>(Arrays.asList(playerList.get(0)));
						ArrayList<Player> team2 = new ArrayList<Player>();
						String team1Name = "";
						String team2Name = "";
						
						while (true) {
							System.out.println();
							System.out.println(playerList.get(0).king + ", who is your teammate ? [1 = " + playerList.get(1).king + ", 2 = " + playerList.get(2).king + ", 3 = " + playerList.get(3).king + "]");
							try {
								teamPlayer = scan.nextInt();
								scan.nextLine();
								if (teamPlayer == 1) {
									team1.add(playerList.get(1));
									team2.add(playerList.get(2));
									team2.add(playerList.get(3));
									System.out.println();
									System.out.print("Team 1 choose your name:");
									team1Name = scan.nextLine();
									break;
								} else if (teamPlayer == 2) {
									team1.add(playerList.get(2));
									team2.add(playerList.get(1));
									team2.add(playerList.get(3));
									System.out.println();
									System.out.print("Team 1 choose your name:");
									team1Name = scan.nextLine();
									break;
								} else if (teamPlayer == 3) {
									team1.add(playerList.get(3));
									team2.add(playerList.get(2));
									team2.add(playerList.get(1));
									System.out.println();
									System.out.print("Team 1 choose your name:");
									team1Name = scan.nextLine();
									break;
								} else {
									System.out.println("This is not possible !");
								}
							} catch (InputMismatchException e) {
								System.out.println("This is not an integer !");
								scan.next();
							}
						}
						for (Player p : team1) {
							p.setTeam(team1Name);
						}
						System.out.println();
						System.out.print("Team 2 choose your name:");
						team2Name = scan.nextLine();
						for (Player p : team2) {
							p.setTeam(team2Name);
						}
						break;
					} else if (cooperation == 0) {
						break;
					} else {
						System.out.println("This is not possible !");
					}
				} catch (InputMismatchException e) {
					System.out.println("This is not an integer !");
					scan.next();
				}
			}
		}
		
		
		HashMap<Integer, Domino> newMap = dominoMapCreation();
		if (dynasty) {
			for (int i = 0; i < 3; i++) {
				if (i > 0) {
					ArrayList<String> kingList = new ArrayList<String>();
					for (int j = 0; j < playerList.size(); j++) {
						kingList.add(playerList.get(j).getKing());
						int points = playerList.get(j).getPoints();
						System.out.println("The " + playerList.get(j).getKing() + " King currently has " + points + " points !");
						playerList.get(j).newField();
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
							playerList.set(j, ai);
						}
					}
				}
				game(newMap, playerList, nbKings, grandDuel, middleEmpire, valueMidEmpire, coop, harmony, valueHarmony, apocalypse);
			}
		} else {
			game(newMap, playerList, nbKings, grandDuel, middleEmpire, valueMidEmpire, coop, harmony, valueHarmony, apocalypse);
		}
	
		System.out.println("===================================================");
		System.out.println("=================== END OF GAME ===================");
		System.out.println("===================================================");
		
		winner(playerList, coop);
		
	}


	public static HashMap<Integer, Domino> dominoMapCreation() {
		/*
		 *  Creates a HashMap of the dominos from the .csv (Comma Separated Values) file. 
		 */
		
		HashMap<Integer, Domino> dominoHashMap = new HashMap<Integer, Domino>();
		FileReader reader = null;
		BufferedReader table = null;
		try {
			String location = "src\\csv\\dominos2.csv";								//dominos file
			reader = new FileReader(location); 									//reads the file 
			table = new BufferedReader(reader); 								//places the file's elements in the buffer
			String line = table.readLine(); 									//skips the first line
			while (true) {
				line = table.readLine(); 										//reads a line
				if (line == null) {
					break; 														//breaks the loop if empty line
				} else {
					String[] information = line.split(",");						//splits data with ',' into a string table
					int sq1 = Integer.parseInt(information[1]);					//converts string to integer
					int sq2 = Integer.parseInt(information[2]);
					Tile s1 = new Tile(" ", 0, sq1);
					s1.setField(sq1);
					s1.setNbCrowns(sq1);
					Tile s2 = new Tile(" ", 0, sq2);
					s2.setField(sq2);
					s2.setNbCrowns(sq2);
					int key = Integer.parseInt(information[0]); 				//key associated with the domino
					Domino dom = new Domino(s1, s2, key); 						//domino creation
					dominoHashMap.put(key, dom);
				}
			}
		} catch (IOException e) {
			e.printStackTrace(); 												//indicates where's the error and which method is problematic
			System.out.println("The file is untraceable."); 					//error message if untraceable file
		} finally { 															//executed in all cases
			try {
				table.close(); 													//closes the buffer reader
				reader.close(); 												//closes the file reader
			} catch (IOException e2) {
				e2.printStackTrace();
				System.out.println("The file is untraceable.");
			}
		}
		return dominoHashMap;
	}


	public static ArrayList<Domino> mixDom(HashMap<Integer, Domino> map, int nbPlayers, boolean grandDuel, boolean apocalypse) {
		/*
		 * Mixes the dominos and returns the right number of them depending on the number of players.
		 */

		int size = map.size(); 													//HashMap size
		ArrayList<Integer> keyList = new ArrayList<Integer>();  				//list of domino numbers filled with a for loop
		for (int j = 1; j <= size; j++) {
			keyList.add(j);
		}

		int nbDominos = 12 * nbPlayers; 										//number of dominos necessary for playing
		if (grandDuel) {
			nbDominos = 48;
		}
		if (apocalypse) {
			nbDominos = 12 * (nbPlayers + 1);
		}
		ArrayList<Domino> ingameDomList = new ArrayList<Domino>(); 				//list of in-game dominos

		for (int i = 0; i < nbDominos; i++) {
			int random = (int) (Math.random() * (size - 1)); 					//random integer between 0 and size - 1
			int index = keyList.get(random);
			ingameDomList.add(map.get(index)); 									//adds the domino in the list that will be returned
			size--;
			keyList.remove(random);
		}
		
		return ingameDomList; 													//returns the randomized list of dominos
	}

	
	public static ArrayDeque<Domino> nextTurnList(ArrayList<Domino> list, int nbPlayers, boolean apocalypse) {
		/*
		 * Picks the [nbKings] first dominos of the list and puts them in a dequeue.
		 */
		
		int nbKings = 0;
		if (nbPlayers == 2 || nbPlayers == 4 || apocalypse) {
			nbKings = 4;
		} else if (nbPlayers == 3) {
			nbKings = 3;
		}
		
		Iterator<Domino> iterator = list.iterator();
		int count = 0;
		ArrayDeque<Domino> playableDominosDeque = new ArrayDeque<Domino>(); 	//list for the playable dominos in the next turn
		while (iterator.hasNext() && count < nbKings) {
			Domino dominosIterator = iterator.next();
			playableDominosDeque.add(dominosIterator); 							//adds the domino in the aforementioned list
			count++;
		}
		for (int i = 0; i < nbKings; i++) {
			list.remove(0);
		}
		return sortQueue(playableDominosDeque);
	}

	
	public static ArrayDeque<Domino> sortQueue(ArrayDeque<Domino> queue) {
		/*
		 * Sorts a dequeue of dominos by their numbers.
		 */
		ArrayDeque<Domino> dominosDeque = new ArrayDeque<Domino>();
		ArrayList<Domino> dominosList = new ArrayList<Domino>(); 				//dominos list
		ArrayList<Integer> numbersList = new ArrayList<Integer>();  			//domino numbers list
		for (int i = 0; i < queue.size(); i++) {
			Domino dom = queue.poll();
			int number = dom.getNumber();
			numbersList.add(number);
			dominosList.add(dom);
			queue.add(dom);
		}
		try {
			int i = numbersList.size();
			while (i > 0) {														//loops on the size of the list
				int n = numbersList.indexOf(Collections.min(numbersList));
				Domino d = dominosList.get(n); 									//domino with the smallest number
				dominosDeque.add(d); 											//adds this domino in the dequeue
				numbersList.remove(n); 											//removes it from the list
				dominosList.remove(n);
				i -= 1;
			}
		} catch (NullPointerException e) { 										//error message if the list is empty
			System.out.println("Error, the list is empty !");
		}
		return dominosDeque; 													//returns the sorted dequeue
	}

	
	public static ArrayList<Object> playFirstTurn(ArrayList<Domino> list, ArrayList<Player> listPlayer, int nbKings, int countTurn,
			boolean middleEmpire, int valueMidEmpire, boolean coop, boolean apocalypse) {
		/*
		 * Takes the first dominos, displays them and draws the players' turn.
		 */
		
		@SuppressWarnings("unchecked")
		ArrayList<Player> playerList = (ArrayList<Player>) listPlayer.clone();
		
		int size = playerList.size();
		ArrayDeque<Domino> dominoDeque = nextTurnList(list, nbKings, apocalypse);
		ArrayList<Integer> listNum = dispDominos(dominoDeque); 	//displays the available dominos for this turn and returns the list of their numbers 
																				

		/* drawing of the players' turn */
		ArrayDeque<Player> playersDeque = new ArrayDeque<Player>();
		if (size == 2) {
			while (size > 0) {
				int random = (int) (Math.random() * (size - 1)); 				//random integer between 0 and size - 1
				playersDeque.add(playerList.get(random)); 						//adds a random player to the dequeue
				playersDeque.add(playerList.get(random));						//adds a second time for the second king
				playerList.remove(random); 
				size--;
			}
		} else {
			while (size > 0) {
				int random = (int) (Math.random() * (size - 1));
				playersDeque.add(playerList.get(random));
				playerList.remove(random);
				size--;
			}
		}
		int count = nbKings;
		if (apocalypse) {
			count ++;
		}
		Player[] playerTab = new Player[count];
		@SuppressWarnings("unchecked")
		ArrayList<Integer> temp = (ArrayList<Integer>) listNum.clone();

		for (int j = 0; j < nbKings; j++) {
			if (playersDeque.size() == 0) {
				int index = temp.indexOf(listNum.get(0));
				playerTab[index] = null;
			} else {
				Player player = playersDeque.poll();
				String king = player.getKing();
				if (player.isHuman == false) {
					AI ai = (AI) player;
					System.out.println();
					System.out.print(king + " King, choose the number of your domino for the next turn [see list]: ");
					int numDom = ai.researchDomino(dominoDeque, listPlayer, listNum, countTurn, middleEmpire, valueMidEmpire, coop);
					System.out.println(numDom);
					int order = temp.indexOf(numDom);
					int tberased = listNum.indexOf(numDom);
					listNum.remove(tberased); 								//(.remove takes for argument the index)
					playerTab[order] = player;								//this player will play in [order]st position next turn
					if (listNum.size() != 0) {								//prints the domino list if it is not empty
						System.out.println();
						System.out.println("Remaining dominos: " + listNum);
					}
				} else {
					int choice;
					while (true) {
						try {
							System.out.println();
							System.out.print(king + " King, choose the number of your domino for the next turn [see list]: ");
							choice = scan.nextInt();
							if (listNum.contains(choice)) {
								int order = temp.indexOf(choice);
								int tberased = listNum.indexOf(choice);
								listNum.remove(tberased); 								//(.remove takes for argument the index)
								playerTab[order] = player;								//this player will play in [order]st position next turn
								if (listNum.size() != 0) {								//prints the domino list if it is not empty
									System.out.println();
									System.out.println("Remaining dominos: " + listNum);
								}
								break;
							} else {
								System.out.println("This domino is not here !");
							}
						} catch (InputMismatchException e) {
							System.out.println("It is not an integer !");
							scan.next();
						}
					}
				}
			}
		}
		System.out.println();
		System.out.print("------------------------------------------------------->  Order for this turn: ");
		for (Player player : playerTab) {
			if (player != null) {
				System.out.print(player.getKing() + "  ");
			}
		}
		System.out.print("<-------------------------------------------------------");
		System.out.println();
		countTurn += 1;
		ArrayList<Object> resultList = new ArrayList<Object>(Arrays.asList(dominoDeque, playerTab, list, countTurn));

		return resultList;

	}

	
	public static ArrayList<Integer> dispDominos(ArrayDeque<Domino> queue) {
		/*
		 * Displays the available dominos for this turn and returns the list of their numbers.
		 */
		System.out.println();
		System.out.println("Available dominos :");
		ArrayList<Integer> listNum = new ArrayList<Integer>(); 					//list of the dominos' number

		for (int i = 0; i < queue.size(); i++) {
			Domino dom = queue.poll();
			int number = dom.getNumber();
			listNum.add(number);
			queue.add(dom);
			String sqDom1 = dom.getSq1().getField(); 							//gets the field of the domino's square 1
			String sqDom2 = dom.getSq2().getField(); 							//gets the field of the domino's square 2
			ArrayList<String> domPicture = new ArrayList<String>();
			domPicture.add(sqDom1 + " {" + dom.getSq1().getNbCrowns() + " crown(s)}");
			domPicture.add(sqDom2 + " {" + dom.getSq2().getNbCrowns() + " crown(s)}");
			System.out.print(number + "  ");
			System.out.println(domPicture);
		}
		return listNum;
	}

	
	public static ArrayList<Object> playNextTurn(ArrayList<Domino> list, ArrayList<Player> listPlayer, Player[] playerTab, ArrayDeque<Domino> lastTurnDeque, int nbKings, int countTurn, 
			boolean grandDuel, boolean middleEmpire, int valueMidEmpire, boolean coop, boolean apocalypse) {
		/*
		 * Choice of dominos for each player and determination of order for next turn.
		 */
		ArrayDeque<Domino> dominoDeque = nextTurnList(list, nbKings, apocalypse);
		ArrayList<Integer> listNum = dispDominos(dominoDeque);
		ArrayDeque<Player> playersQueue = new ArrayDeque<Player>();
		for (int i = 0; i < playerTab.length; i++) {
			if (playerTab[i] != null) {
				playersQueue.add(playerTab[i]);
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Integer> temp = (ArrayList<Integer>) listNum.clone();

		for (int j = 0; j < playerTab.length; j++) {
			if (playersQueue.size() == 0) {
				int index = temp.indexOf(listNum.get(0));
				playerTab[index] = null;
			} else {
				Player player = playersQueue.poll();
				String king = player.getKing();
				if (player.isHuman == false) {
					AI ai = (AI) player;
					System.out.println();
					System.out.print(king + " King, choose the number of your domino for the next turn [see list]: ");
					int numDom = ai.researchDomino(dominoDeque, listPlayer, listNum, countTurn, middleEmpire, valueMidEmpire, coop);
					System.out.println(numDom);
					int order = temp.indexOf(numDom);
					int tberased = listNum.indexOf(numDom);
					listNum.remove(tberased); 								//(.remove takes for argument the index)
					playerTab[order] = player;								//this player will play in [order]st position next turn
					
					lastTurnDeque.poll();
					ai.playDominoAI(grandDuel);

					if (listNum.size() != 0) {
						System.out.println();
						System.out.println("Remaining dominos: " + listNum);
					}
					
				} else {
					int choice;
					while (true) {
						try {
							System.out.println();
							System.out.print(king + " King, choose the number of your domino for the next turn [see list]: ");
							choice = scan.nextInt();
							if (listNum.contains(choice)) {
								int order = temp.indexOf(choice);
								int tberased = listNum.indexOf(choice);
								listNum.remove(tberased); 								//(.remove takes for argument the index)
								playerTab[order] = player;								//this player will play in [order]st position next turn
								if (listNum.size() != 0) {								//prints the domino list if it is not empty
									System.out.println();
									System.out.println("Remaining dominos: " + listNum);
								}
								break;
							} else {
								System.out.println("This domino is not here !");
							}
						} catch (InputMismatchException e) {
							System.out.println("It is not an integer !");
							scan.next();
						}
					}
					
					Domino d = lastTurnDeque.poll();
					playDomino(player, d, grandDuel);

					if (listNum.size() != 0) {
						System.out.println();
						System.out.println("Remaining dominos: " + listNum);
					}
				
				}
			}

		}
			
		countTurn += 1;
		System.out.println();
		System.out.print("-------------------------------------------------------> Order for this turn: ");
		for (Player player : playerTab) {
			if (player != null) {
				System.out.print(player.getKing() + "  ");
			}
		}
		System.out.print("<-------------------------------------------------------");
		System.out.println();
		
		ArrayList<Object> resultList = new ArrayList<Object>(Arrays.asList(dominoDeque, playerTab, list, countTurn));
		return resultList;
		
	}

	
	public static void playLastTurn(Player[] playerTab, ArrayDeque<Domino> lastTurnDeque, boolean grandDuel) {
		/*
		 * Placement of last dominos for each player.
		 */

		ArrayDeque<Player> playersQueue = new ArrayDeque<Player>();
		for (int i = 0; i < playerTab.length; i++) {
			if (playerTab[i] != null) {
				playersQueue.add(playerTab[i]);
			}
		}
		
		for (int j = 0; j < playerTab.length; j++) {
			if (playerTab[j] != null) {
				Player player = playersQueue.poll();
				if (player.isHuman == false) {
					AI ai = (AI) player;
					lastTurnDeque.poll();
					ai.playDominoAI(grandDuel);
				} else {
				Domino d = lastTurnDeque.poll();
				playDomino(player, d, grandDuel);
				}
			}
		}
	}

	
	public static void playDomino(Player player, Domino d, boolean grandDuel) {
		/*
		 * Allows the player to place a domino.
		 */
		
		Tile[][] gameField = player.getGameField();
		
		Tile ds1 = d.getSq1();
		Tile ds2 = d.getSq2();
		int sq1 = ds1.getNumber();
		int sq2 = ds2.getNumber();
		
		
		ArrayDeque<Character> orientations = new ArrayDeque<Character>(Arrays.asList('R', 'D', 'L', 'U'));
		char currentOrientation = orientations.poll();
		orientations.add(currentOrientation);
		
		int orientationChoice;
		int x1 = 0, y1 = 0, 
				x2 = 0, y2 = 0;
		
		coordsChoice: 															//names the loop, so that it can be targeted by a break command
		while (true) {
			
			orientationChoice:
				while (true) {
					try {
						player.printGameField(grandDuel);
						System.out.println();
						System.out.println("Domino " + d.getNumber() + " : " + sq1 + ", " + sq2 + "  --> " + currentOrientation);
						System.out.println();
						System.out.print("Enter 1 for clockwise rotation and 0 to confirm (enter 99 if you can't play the domino) [1,0,99]: ");
						orientationChoice = scan.nextInt();
						if (orientationChoice == 1) {
							currentOrientation = orientations.poll();
							orientations.add(currentOrientation);
						} else if (orientationChoice == 0) {
							break orientationChoice;
						} else if (orientationChoice == 99) {
							if (d.isImpossibleToPlay(player)) {
								System.out.println("This domino cannot be played, domino discarded");
								break coordsChoice;
							} else {
								System.out.println("This domino is playable !");
							}
						} else {
							System.out.println("Invalid number !");
						}
					} catch (InputMismatchException e) {
						System.out.println("This is not a number !");
						scan.next();
					}
				} 
			
			try {
				
				System.out.println("Square 1");
				System.out.print("x [0 to " + (player.getGameField().length - 1) + "]: ");
				x1 = scan.nextInt();
				System.out.print("y [0 to " + (player.getGameField().length - 1) + "]: ");
			    y1 = scan.nextInt();
			    
			    
			    switch (currentOrientation) {
				case 'R':
					x2 = x1;
					y2 = y1 + 1;
					break;
				case 'D':
					x2 = x1 + 1;
					y2 = y1;
					break;
				case 'L':
					x2 = x1;
					y2 = y1 - 1;
					break;
				case 'U':
					x2 = x1 - 1;
					y2 = y1;
					break;
				default:
					break;
			    }
				
				if (d.isPlayable(player, currentOrientation, x1, y1, x2, y2) == true) {
					gameField[x1][y1] = ds1;
					gameField[x2][y2] = ds2;
					player.completeLine();
					player.completeColumn();
					player.printGameField(grandDuel);
					break coordsChoice;
				}
			
			} catch (InputMismatchException e) {
				System.out.println("This is not a number !");
				scan.next();
			}
		}
	}

	
	public static void winner(ArrayList<Player> playerList, boolean coop) {		
		
		ArrayList<Integer> ptsList = new ArrayList<Integer>();
		ArrayList<String> kingList = new ArrayList<String>();
		
		for (Player player : playerList) {
			kingList.add(player.getKing());
			int points = player.getPoints();
			ptsList.add(points);
			System.out.println("The " + player.getKing() + " King has " + points + " points !");
		}
		int max = 0;
		if (coop) {
			HashMap<String, Integer> teamMap = new HashMap<String, Integer>();
			ArrayList<String> teamList = new ArrayList<String>();
			Player p = playerList.get(0);
			teamMap.put(p.getTeam(), p.getPoints());
			teamList.add(p.getTeam());
			int j = 0;
			for (int i = 1; i < playerList.size(); i++) {
				if (playerList.get(i).getTeam().compareTo(p.getTeam()) == 0) {
					int pts = teamMap.get(p.getTeam()); 
					teamMap.replace(playerList.get(i).getTeam(), playerList.get(i).getPoints() + pts);
				} else if (j == 0) {
					teamMap.put(playerList.get(i).getTeam(), playerList.get(i).getPoints());
					teamList.add(playerList.get(i).getTeam());
					j ++;
				} else {
					int pts = teamMap.get(playerList.get(i).getTeam());
					teamMap.replace(playerList.get(i).getTeam(), playerList.get(i).getPoints() + pts);
				}
			}
			String winnerTeam = "";
			for (int k = 0; k < teamMap.size(); k++) {
				if (teamMap.get(teamList.get(k)) >= max) {
					max = teamMap.get(teamList.get(k));
					winnerTeam = teamList.get(k);
				}
			}
			System.out.println();
			System.out.println("===============================================================> Victory for the " + winnerTeam + " Team with " + max + " points !");
			
		}
		for (int j = 0; j < playerList.size(); j++) {
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
		
		System.out.println();
		
		for (String king : winnerList) {
				System.out.println("===============================================================> Victory for the " + king + " King !");
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static void game(HashMap<Integer, Domino> map, ArrayList<Player> playerList, int nbKings,
			boolean grandDuel, boolean middleEmpire, int valueMidEmpire, boolean coop, boolean harmony, int valueHarmony, boolean apocalypse) {
		int countTurn = 0;
		ArrayList<Domino> randomizedList = mixDom(map, playerList.size(), grandDuel, apocalypse);
		ArrayList<Object> result = playFirstTurn(randomizedList, playerList, nbKings, countTurn, middleEmpire, valueMidEmpire, coop, apocalypse);
		ArrayDeque<Domino> dominoDeque = (ArrayDeque<Domino>) result.get(0);
		Player[] playerTab = (Player[]) result.get(1);
		ArrayList<Domino> dominosYetToBePlayedList = (ArrayList<Domino>) result.get(2);
		countTurn = (int) result.get(3);

		while (dominosYetToBePlayedList.size() != 0) {
			result = playNextTurn(dominosYetToBePlayedList, playerList, playerTab, dominoDeque, nbKings, countTurn, grandDuel, middleEmpire, valueMidEmpire, coop, apocalypse);
			dominoDeque = (ArrayDeque<Domino>) result.get(0);
			playerTab = (Player[]) result.get(1);
			dominosYetToBePlayedList = (ArrayList<Domino>) result.get(2);
			countTurn = (int) result.get(3);
		}
		
		playLastTurn(playerTab, dominoDeque, grandDuel);
		
		for (int i = 0; i < playerList.size(); i++) {
			if (middleEmpire) {
				Tile[][] gameField = playerList.get(i).getGameField();
				int indexEdge1 = Math.round(gameField.length / 4);
				int indexEdge2 = Math.round(3 * gameField.length / 4);
				if ((gameField[indexEdge1][indexEdge1].getNumber() != 99) 
						&& (gameField[indexEdge1][indexEdge2].getNumber() != 99)
						&& (gameField[indexEdge2][indexEdge1].getNumber() != 99)
						&& (gameField[indexEdge2][indexEdge2].getNumber() != 99)) {
					playerList.get(i).points += (playerList.get(i).nbPoints() + valueMidEmpire);
				} else {
					playerList.get(i).points += playerList.get(i).nbPoints();
				}
			} else {
				playerList.get(i).points += playerList.get(i).nbPoints();
			}
			if (harmony) {
				int count = 0; 
				harmonyPoints:
				for (int k = 0; k < playerList.get(i).getGameField().length; k++) {
					for (int l = 0; l < playerList.get(i).getGameField().length; l++) {
						if (playerList.get(i).getGameField()[k][l].getNumber() == 0) {
							break harmonyPoints;
						} else {
							count ++; 
						}
					}
				}
				if (count == playerList.get(i).getGameField().length * playerList.get(i).getGameField().length) {
					playerList.get(i).points += valueHarmony;
				}
			}
		}
	}
	
}
