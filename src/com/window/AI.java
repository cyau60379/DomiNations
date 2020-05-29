package com.window;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class AI extends Player {

	private ArrayList<Integer> domPosition;
	private ArrayList<Integer> domPositionNext;
	private Domino domino;
	private Domino dominoNext;

	private ArrayList<Integer> domPosition2;
	private ArrayList<Integer> domPositionNext2;
	private Domino domino2;
	private Domino dominoNext2;

	private int count2Players;
	private int count;
	private boolean discard;

	public AI(String king, int nbKings, Tile[][] gameField) {
		super(king, nbKings, gameField);
		this.isHuman = false;
		this.domPosition = new ArrayList<Integer>();
		this.domPositionNext = new ArrayList<Integer>();
		this.domPosition2 = new ArrayList<Integer>();
		this.domPositionNext2 = new ArrayList<Integer>();
		this.count2Players = 1;
		this.count = 0;
		this.domino = new Domino(new Tile(), new Tile(), 0);
		this.domino2 = new Domino(new Tile(), new Tile(), 0);
		this.setDiscard(false);
	}

	public Domino getDomino() {
		return domino;
	}

	public void setDomino(Domino d) {
		this.domino = d;
	}
	
	public int getCount2Players() {
		return count2Players;
	}
	
	public void setCount2Players(int c) {
		this.count2Players = c;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int c) {
		this.count = c;
	}

	public Domino getDomino2() {
		return domino2;
	}

	public void setDomino2(Domino d) {
		this.domino2 = d;
	}

	public ArrayList<Integer> getDomPosition() {
		return domPosition;
	}

	public void setDomPosition(ArrayList<Integer> l) {
		this.domPosition = l;
	}

	public ArrayList<Integer> getDomPosition2() {
		return domPosition2;
	}

	public void setDomPosition2(ArrayList<Integer> l) {
		this.domPosition2 = l;
	}

	public Domino getDominoNext() {
		return dominoNext;
	}

	public void setDominoNext(Domino d) {
		this.dominoNext = d;
	}

	public Domino getDominoNext2() {
		return dominoNext2;
	}

	public void setDominoNext2(Domino d) {
		this.dominoNext2 = d;
	}

	public ArrayList<Integer> getDomPositionNext() {
		return domPositionNext;
	}

	public void setDomPositionNext(ArrayList<Integer> l) {
		this.domPositionNext = l;
	}

	public ArrayList<Integer> getDomPositionNext2() {
		return domPositionNext2;
	}

	public void setDomPositionNext2(ArrayList<Integer> l) {
		this.domPositionNext2 = l;
	}

	public int researchDomino(ArrayDeque<Domino> queue, ArrayList<Player> listPlayers, ArrayList<Integer> listNum,
			int countTurn, boolean middleEmpire, int valueMidEmpire, boolean coop) {

		/*
		 * function to find the domino played by the AI next turn
		 */
		int size = queue.size();

		ArrayList<Domino> dom = new ArrayList<Domino>(); // list of all the dominos
		ArrayList<Domino> domPlayable = new ArrayList<Domino>(); // dominos which can be played

		for (int i = 0; i < size; i++) { // fill dom with all the dominos and domPlayable with the playable ones
			Domino d = queue.poll();
			if (listNum.contains(d.getNumber())) {
				if (d.isImpossibleToPlay(this) == false) { // only in domPlayable if there are empty squares around the
															// castle or if there is another place to play it
					domPlayable.add(d);
				}
				dom.add(d);
			}
			queue.add(d);
		}
		
		
		if (domPlayable.size() == 0) { // any places to put the dominos
			if (this.nbKings == 1) {
				this.domPositionNext = null; // the domino will be deleted
			} else {
				if (this.count2Players == 1) {
					this.domPositionNext = null; // the domino will be deleted
				} else {
					this.domPositionNext2 = null; // the domino will be deleted
				}
			}

			ArrayList<Integer> maxPerPlayer = new ArrayList<Integer>(); // max of pts for each player
			ArrayList<Domino> dominosPlayer = new ArrayList<Domino>(); // number of the domino associeted with the max
																		// for each player

			for (Player p : listPlayers) {
				ArrayList<Integer> maxPerDom = new ArrayList<Integer>(); // max pts for each domino
				ArrayList<Domino> dominos = new ArrayList<Domino>(); // domino associated
				if (coop) {
					if (this.getTeam().compareTo(p.getTeam()) != 0) {
						for (Domino d : dom) {
							ArrayList<ArrayList<Integer>> pos = p.playablePositions(d);
							ArrayList<Integer> pts = new ArrayList<Integer>();
							if (pos != null) { // if a playable position exists

								for (ArrayList<Integer> position : pos) { // for each position, the domino is put on the
																			// game
									// field and the points are calculated
									Tile[][] tab = p.getGameField();
									tab[position.get(0)][position.get(1)] = d.getSq1();
									tab[position.get(2)][position.get(3)] = d.getSq2();
									p.setGameField(tab);
									pts.add(p.nbPoints());
									tab[position.get(0)][position.get(1)] = new Tile("Empty", 0, 0);
									tab[position.get(2)][position.get(3)] = new Tile("Empty", 0, 0);
									// remove the domino because it is not played, just test
									p.setGameField(tab);
								}
								int max = maxi(pts);
								maxPerDom.add(max); // max added
							} else {
								maxPerDom.add(0); // show that it is not playable
							}
							dominos.add(d);
						}
						int maxDom = maxi(maxPerDom);
						int indexMax = maxPerDom.indexOf(maxDom);
						maxPerPlayer.add(maxDom); // max added
						dominosPlayer.add(dominos.get(indexMax)); // take the domino which give the max
					}
				} else {
					for (Domino d : dom) {
						ArrayList<ArrayList<Integer>> pos = p.playablePositions(d);
						ArrayList<Integer> pts = new ArrayList<Integer>();
						if (pos != null) { // if a playable position exists
							for (ArrayList<Integer> position : pos) { // for each position, the domino is put on the
																		// game field and the points are calculated
								Tile[][] tab = p.getGameField();
								tab[position.get(0)][position.get(1)] = d.getSq1();
								tab[position.get(2)][position.get(3)] = d.getSq2();
								p.setGameField(tab);
								pts.add(p.nbPoints());
								tab[position.get(0)][position.get(1)] = new Tile("Empty", 0, 0);
								tab[position.get(2)][position.get(3)] = new Tile("Empty", 0, 0);
								// remove the domino because it is not played, just test
								p.setGameField(tab);
							}

							int max = maxi(pts);
							maxPerDom.add(max); // max added
						} else {
							maxPerDom.add(0); // show that it is not playable
						}
						dominos.add(d);
					}
					int maxDom = maxi(maxPerDom);
					int indexMax = maxPerDom.indexOf(maxDom);
					maxPerPlayer.add(maxDom); // max added
					dominosPlayer.add(dominos.get(indexMax)); // take the domino which give the max
				}
			}

			int theMax = maxi(maxPerPlayer);
			int index = maxPerPlayer.indexOf(theMax); // idem for all the players
			if (this.nbKings == 1) {
				this.dominoNext = dominosPlayer.get(index);
				return this.dominoNext.getNumber();
			} else {
				if (this.count2Players == 1) {
					this.dominoNext = dominosPlayer.get(index);
					return this.dominoNext.getNumber();
				} else {
					this.dominoNext2 = dominosPlayer.get(index);
					return this.dominoNext2.getNumber();
				}
			}

		} else {
			if ((listNum.size() == 3) && (listNum.indexOf(domPlayable.get(0).getNumber()) >= 3) && (count == 1) && (nbKings == 2)) {
				setDiscard(true);
			} else if ((listNum.size() == 4) && (listNum.indexOf(domPlayable.get(0).getNumber()) >= 2) && (count == 1) && (nbKings == 2)) {
				setDiscard(true);
			}
			return this.chooseDomino(domPlayable, countTurn, middleEmpire, valueMidEmpire);
		}
	}

	public int chooseDomino(ArrayList<Domino> domPlayable, int countTurn, boolean middleEmpire, int valueMidEmpire) {
		/*
		 * function to find the domino played by the AI next turn but if it exists one
		 * which is playable
		 */
		ArrayList<ArrayList<Integer>> domPos = new ArrayList<ArrayList<Integer>>();
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		ArrayList<Integer> maxPerDom = new ArrayList<Integer>();

		if (this.nbKings == 1) {
			if ((countTurn >= 1) && (this.domPosition != null)) { // put the domino played this turn if it is playable
				Tile sq1 = this.domino.getSq1();
				Tile sq2 = this.domino.getSq2();
				this.gameField[domPosition.get(0)][domPosition.get(1)] = sq1;
				this.gameField[domPosition.get(2)][domPosition.get(3)] = sq2;
			}
		} else {
			if ((countTurn >= 1 || this.domino.getNumber() != 0) && (this.domPosition != null)) {
				// put the domino played this turn if it is playable
				Tile sq1 = this.domino.getSq1();
				Tile sq2 = this.domino.getSq2();
				this.gameField[domPosition.get(0)][domPosition.get(1)] = sq1;
				this.gameField[domPosition.get(2)][domPosition.get(3)] = sq2;
			}
			if ((countTurn >= 1 || this.domino2.getNumber() != 0) && (this.domPosition2 != null)) { 
				// put the domino played this turn if it is playable
				Tile sq1 = this.domino2.getSq1();
				Tile sq2 = this.domino2.getSq2();
				this.gameField[domPosition2.get(0)][domPosition2.get(1)] = sq1;
				this.gameField[domPosition2.get(2)][domPosition2.get(3)] = sq2;
			}
		}

		for (Domino d : domPlayable) {
			ArrayList<ArrayList<Integer>> pos = this.playablePositions(d);
			ArrayList<Integer> pts = new ArrayList<Integer>();
			if (pos != null) { // if a playable position exists
				for (ArrayList<Integer> position : pos) { // for each position, the domino is put on the game field and
															// the points are calculated
					this.gameField[position.get(0)][position.get(1)] = d.getSq1();
					this.gameField[position.get(2)][position.get(3)] = d.getSq2();

					if (middleEmpire) {
						int count = 0;
						for (int k = 0; k < position.size(); k++) {
							if ((position.get(k) >= Math.round(this.gameField.length / 4))
									&& (position.get(k) <= Math.round(3 * this.gameField.length / 4))) {
								count++;
							}
						}
						if (count == 4) {
							pts.add((this.nbPoints() + valueMidEmpire));
						} else {
							pts.add(this.nbPoints()); 
						}
					} else {
						pts.add(this.nbPoints());
					}

					this.gameField[position.get(0)][position.get(1)] = new Tile("Empty", 0, 0);
					this.gameField[position.get(2)][position.get(3)] = new Tile("Empty", 0, 0);
				}
				int max = maxi(pts); // max points of the possible positions
				int index = pts.indexOf(max);
				maxPerDom.add(max); // max added
				domPos.add(pos.get(index)); // position added
			} else {
				maxPerDom.add(0); // add 0 to show that the domino cannot be put on the game field
				domPos.add(null); // will be erased if it is chosen
			}
			dominos.add(d);

		}
		int theMax = maxi(maxPerDom);
		int index = maxPerDom.indexOf(theMax);

		if (this.nbKings == 1) { // for 3 and 4 players
			this.dominoNext = dominos.get(index); // dominoNext change
			ArrayList<Integer> positions = domPos.get(index);
			this.domPositionNext = positions; // positionNext too

			if (countTurn == 0) { // for the first turn, to put it on the game field to choose the next one
				this.domino = this.dominoNext;
				this.domPosition = this.domPositionNext;
			}

			if (countTurn >= 1 && (this.domPosition != null)) { // remove because it is not yet played
				this.gameField[domPosition.get(0)][domPosition.get(1)] = new Tile("Empty", 0, 0);
				this.gameField[domPosition.get(2)][domPosition.get(3)] = new Tile("Empty", 0, 0);
			}
			return dominoNext.getNumber();

		} else { // for 2 players

			if (this.count2Players == 1) {
				this.dominoNext = dominos.get(0); // dominoNext change
				ArrayList<Integer> positions = domPos.get(0);
				this.domPositionNext = positions; // positionNext too

				if (countTurn == 0) { // for the first turn, to put it on the game field to choose the next one
					this.domino = this.dominoNext;
					this.domPosition = this.domPositionNext;
					this.count2Players = 2;
				}

				if (countTurn >= 1) { // remove because it is not yet played
					if (this.domPosition != null) {
						this.gameField[domPosition.get(0)][domPosition.get(1)] = new Tile("Empty", 0, 0);
						this.gameField[domPosition.get(2)][domPosition.get(3)] = new Tile("Empty", 0, 0);
					}
					if (this.domPosition2 != null) {
						this.gameField[domPosition2.get(0)][domPosition2.get(1)] = new Tile("Empty", 0, 0);
						this.gameField[domPosition2.get(2)][domPosition2.get(3)] = new Tile("Empty", 0, 0);
					}
				}
				return dominoNext.getNumber();

			} else {
				this.dominoNext2 = dominos.get(index); // dominoNext change
				ArrayList<Integer> positions = domPos.get(index);
				
				if (discard) {
					this.domPositionNext2 = null; // positionNext too
				} else {
					this.domPositionNext2 = positions; // positionNext too
				}
				
				if (countTurn == 0) { // for the first turn, to put it on the game field to choose the next one
					this.domino2 = this.dominoNext2;
					this.domPosition2 = this.domPositionNext2;
					if (this.domino.getNumber() < this.domino2.getNumber()) { // to play the correct domino next turn
						this.count2Players = 1;
					} else {
						this.count2Players = 2;
					}
				}

				if (countTurn >= 1) { // remove because it is not yet played
					if (this.domPosition != null) {
						this.gameField[domPosition.get(0)][domPosition.get(1)] = new Tile("Empty", 0, 0);
						this.gameField[domPosition.get(2)][domPosition.get(3)] = new Tile("Empty", 0, 0);
					}
					if (this.domPosition2 != null) {
						this.gameField[domPosition2.get(0)][domPosition2.get(1)] = new Tile("Empty", 0, 0);
						this.gameField[domPosition2.get(2)][domPosition2.get(3)] = new Tile("Empty", 0, 0);
					}
				}
				return dominoNext2.getNumber();
			}
		}

	}

	public void playDominoAI(boolean duel, int gameFieldSize, int topLeftCornerX, int topLeftCornerY, int squareSize) {
		/*
		 * function to let the AI play
		 */
		if (this.nbKings == 1) {
			Tile sq1 = this.domino.getSq1();
			Tile sq2 = this.domino.getSq2();
			if (this.domPosition != null) { // if the domino can be put on the game field
				this.gameField[this.domPosition.get(0)][this.domPosition.get(1)] = sq1;
				this.gameField[this.domPosition.get(2)][this.domPosition.get(3)] = sq2;
				this.putTile(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, this.domPosition.get(0), this.domPosition.get(1), sq1.getNumber());
				this.putTile(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, this.domPosition.get(2), this.domPosition.get(3), sq2.getNumber());
				this.completeLine(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize);
				this.completeColumn(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize);
			}
			this.domino = this.dominoNext; // replace the domino already put on
			this.domPosition = this.domPositionNext; // replace it position
		} else {

			if (this.count2Players == 1) {
				Tile sq11 = this.domino.getSq1();
				Tile sq21 = this.domino.getSq2();
				if (this.domPosition != null) { // if the domino can be put on the game field
					this.gameField[this.domPosition.get(0)][this.domPosition.get(1)] = sq11;
					this.gameField[this.domPosition.get(2)][this.domPosition.get(3)] = sq21;
					this.putTile(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, this.domPosition.get(0), this.domPosition.get(1), sq11.getNumber());
					this.putTile(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, this.domPosition.get(2), this.domPosition.get(3), sq21.getNumber());
					this.completeLine(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize);
					this.completeColumn(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize);
					
				}
				this.domino = this.dominoNext; // replace the domino already put on
				this.domPosition = this.domPositionNext; // replace it position
				this.count2Players = 2;
				this.count++;
			} else {
				Tile sq12 = this.domino2.getSq1();
				Tile sq22 = this.domino2.getSq2();
				if (this.domPosition2 != null) { // if the domino can be put on the game field
					this.gameField[this.domPosition2.get(0)][this.domPosition2.get(1)] = sq12;
					this.gameField[this.domPosition2.get(2)][this.domPosition2.get(3)] = sq22;
					this.putTile(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, this.domPosition2.get(0), this.domPosition2.get(1), sq12.getNumber());
					this.putTile(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, this.domPosition2.get(2), this.domPosition2.get(3), sq22.getNumber());
					this.completeLine(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize);
					this.completeColumn(gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize);
					
				}
				this.domino2 = this.dominoNext2; // replace the domino already put on
				this.domPosition2 = this.domPositionNext2; // replace it position
				this.count2Players = 1;
				this.count++;
			}
			if (this.count == 2) {
				if (this.domino.getNumber() < this.domino2.getNumber()) { // to play the correct domino next turn
					this.count2Players = 1;
				} else {
					this.count2Players = 2;
				}
				this.count = 0;
				this.discard = false;
			}
		}
	}

	public static int maxi(ArrayList<Integer> list) {
		/*
		 * function to take the max of a list
		 */
		int max = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) > max) {
				max = list.get(i);
			}
		}
		return max;
	}

	public boolean isDiscard() {
		return discard;
	}

	public void setDiscard(boolean discard) {
		this.discard = discard;
	}
}
