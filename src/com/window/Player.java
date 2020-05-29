package com.window;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

/**
 * 
 * This class allows the manipulation of players.
 *
 */
public class Player {
	
	protected String king;
	protected int nbKings;
	protected Tile[][] gameField;
	protected boolean isHuman;
	protected int points;
	protected String team;
	protected Pane gameFieldPane;
	protected Text kingText;
	protected Text pointsText;
	
	public Player(String king, int nbKings, Tile[][] gameField) {
		this.king = king;
		this.nbKings = nbKings;
		this.gameField = gameField;
		this.isHuman = true;
		this.points = 0;
		this.team = "";
		this.gameFieldPane = new Pane();
		this.kingText = new Text(king + " King");
		this.pointsText = new Text(points + " points");
	}
	
	public String getKing() {
		return king;
	}
	
	public int getNbKings() {
		return nbKings;
	}
	
	public Tile[][] getGameField() {
		return gameField;
	}
	
	public int getPoints() {
		return points;
	}
	
	public String getTeam() {
		return team;
	}
	
	public Pane getGameFieldPane() {
		return gameFieldPane;
	}
	
	public Text getKingText() {
		return kingText;
	}
	
	public Text getPointsText() {
		return pointsText;
	}
	
	public void setPoints(int p) {
		this.points = p;
	}

	public void setKing(String k) {
		this.king = k;
	}
	
	public void setNbKings(int nb) {
		this.nbKings = nb;
	}
	
	public void setGameField(Tile[][] gameF) {
		this.gameField = gameF;
	}

	public void setTeam(String k) {
		this.team = k;
	}
	
	public void setGameFieldPane(Pane gameFieldP) {
		this.gameFieldPane = gameFieldP;
	}
	
	public void setPointsText() {
		this.pointsText = new Text(this.points + this.nbPoints() + " points");
	}

	public void newField() {
		for (int i = 0; i < gameField.length; i++) {
			for (int j = 0; j < gameField.length; j++) {
				gameField[i][j] = new Tile("Empty", 0, 0);
			}
		}
		int index = Math.round(gameField.length / 2);
		gameField[index][index] = new Tile("Castle", 0, 17);												//the castle is put in the center of the gameField
	}
	
	public void newFieldPane(int gameFieldSize, int topLeftCornerX, int topLeftCornerY, int squareSize, String king) {
		gameFieldPane.getChildren().clear();
		
		for (int i = 0; i <= gameFieldSize; i++) {
			//vertical
			gameFieldPane.getChildren().add(new Line(topLeftCornerX + i * squareSize, topLeftCornerY, topLeftCornerX + i * squareSize, topLeftCornerY + gameFieldSize * squareSize));
			//horizontal
			gameFieldPane.getChildren().add(new Line(topLeftCornerX, topLeftCornerY + i * squareSize, topLeftCornerX + gameFieldSize * squareSize, topLeftCornerY + i * squareSize));
		}
		
		Line line1 = new Line(topLeftCornerX, topLeftCornerY, topLeftCornerX + gameFieldSize * squareSize, topLeftCornerY);
		Line line2 = new Line(topLeftCornerX + gameFieldSize * squareSize, topLeftCornerY, topLeftCornerX + gameFieldSize * squareSize, topLeftCornerY + gameFieldSize * squareSize);
		Line line3 = new Line(topLeftCornerX + gameFieldSize * squareSize, topLeftCornerY + gameFieldSize * squareSize, topLeftCornerX, topLeftCornerY + gameFieldSize * squareSize);
		Line line4 = new Line(topLeftCornerX, topLeftCornerY + gameFieldSize * squareSize, topLeftCornerX, topLeftCornerY);
		
		if (king.compareTo("Blue") == 0) {
			line1.setStroke(Color.DEEPSKYBLUE);
			line2.setStroke(Color.DEEPSKYBLUE);
			line3.setStroke(Color.DEEPSKYBLUE);
			line4.setStroke(Color.DEEPSKYBLUE);
		} else if (king.compareTo("Pink") == 0) {
			line1.setStroke(Color.DEEPPINK);
			line2.setStroke(Color.DEEPPINK);
			line3.setStroke(Color.DEEPPINK);
			line4.setStroke(Color.DEEPPINK);
		} else if (king.compareTo("Green") == 0) {
			line1.setStroke(Color.LAWNGREEN);
			line2.setStroke(Color.LAWNGREEN);
			line3.setStroke(Color.LAWNGREEN);
			line4.setStroke(Color.LAWNGREEN);
		} else if (king.compareTo("Yellow") == 0) {
			line1.setStroke(Color.YELLOW);
			line2.setStroke(Color.YELLOW);
			line3.setStroke(Color.YELLOW);
			line4.setStroke(Color.YELLOW);
		}
		
		line1.setStrokeLineCap(StrokeLineCap.ROUND);
		line2.setStrokeLineCap(StrokeLineCap.ROUND);
		line3.setStrokeLineCap(StrokeLineCap.ROUND);
		line4.setStrokeLineCap(StrokeLineCap.ROUND);
		
		line1.setStrokeType(StrokeType.OUTSIDE);
		line2.setStrokeType(StrokeType.OUTSIDE);
		line3.setStrokeType(StrokeType.OUTSIDE);
		line4.setStrokeType(StrokeType.OUTSIDE);
		
		line1.setStrokeWidth(10);
		line2.setStrokeWidth(10);
		line3.setStrokeWidth(10);
		line4.setStrokeWidth(10);
		
		gameFieldPane.getChildren().addAll(line1, line2, line3, line4);
		
		Image tile = null;
		ImageView tileView = null;
		try {
			tile = new Image(new FileInputStream("src\\com\\window\\tiles\\castle.png"));
		} catch (FileNotFoundException e) {
			System.out.println("error getting tileView");
		}
		tileView = new ImageView(tile);
		tileView.setFitWidth(squareSize);
		tileView.setFitHeight(squareSize);
		int xImage = (squareSize * Math.round(gameFieldSize / 2)) + topLeftCornerX;
		int yImage = (squareSize * Math.round(gameFieldSize / 2)) + topLeftCornerY;
		ImageView a = tileView;
		a.setX(xImage);
		a.setY(yImage);
		gameFieldPane.getChildren().add(a);
	}
	
	public ImageView tileView(int tileID, int squareSize) {
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
	
	public void putTile(int gameFieldSize, int topLeftCornerX, int topLeftCornerY, int squareSize, int x, int y, int tileNum) {
		ImageView tileView = this.tileView(tileNum, squareSize);
		tileView.setFitWidth(squareSize);
		tileView.setFitHeight(squareSize);
		int xImage = (squareSize * y) + topLeftCornerX;
		int yImage = (squareSize * x) + topLeftCornerY;
		ImageView a = tileView;
		a.setX(xImage);
		a.setY(yImage);
		gameFieldPane.getChildren().add(a);
	}
	
	public void printGameField(boolean duel) {
		/*
		 * Prints the selected game field.
		 */
		System.out.println();
		System.out.print("   " + king + " King's Kingdom : ");
		System.out.println("(" + (this.nbPoints() + this.points) + " pts)");
		if (duel) {
			System.out.println("    0  1  2  3  4  5  6  7  8  9  10 11 12");
			System.out.println("   _______________________________________");
		} else {
			System.out.println("     0  1  2  3  4  5  6  7  8");
			System.out.println("    ___________________________");
		}


		for (int i = 0; i < gameField.length; i++) {
			if (i >= 10) {
				System.out.print((i) + " | ");
			} else {
				System.out.print((i) + "  | ");
			}
			for (int j = 0; j < gameField[i].length; j++) {
				System.out.print(gameField[i][j].getNumber());
				if (gameField[i][j].getNumber() >= 10) {
					System.out.print(" ");
				} else {
					System.out.print("  ");
				}
				
			}
			if (i == 0) {
				System.out.print("  	     	    	0 = Empty square    		7 = Meadow (0 crown)		16 = Mountain (3 crowns)");
			} else if (i == 1) {
				System.out.print("			17 = Castle         		8 = Meadow (1 crown)									");
			} else if (i == 2) {
				System.out.print("			99 = Out of bounds  		9 = Meadow (2 crowns)									");
			} else if (i == 3) {
				System.out.print("			1 = Fields (0 crown)		10 = Mine (0 crown)										");
			} else if (i == 4) {
				System.out.print("			2 = Fields (1 crown)		11 = Mine (1 crown)										");
			} else if (i == 5) {
				System.out.print("			3 = Forest (0 crown)		12 = Mine (2 crowns)									");
			} else if (i == 6) {
				System.out.print("			4 = Forest (1 crown)		13 = Mountain (0 crown)									");
			} else if (i == 7) {
				System.out.print("			5 = Sea (0 crowns)  		14 = Mountain (1 crown)									");
			} else if (i == 8) {
				System.out.print("			6 = Sea (1 crowns)  		15 = Mountain (2 crowns)								");
			}
			System.out.println();
		}
		System.out.println();
	}

	
	
	
	public void completeLine(int gameFieldSize, int topLeftCornerX, int topLeftCornerY, int squareSize) {
		int[] tab = new int[gameField.length];
		for (int line = 0; line < gameField.length; line++) {
			for (int i = 0; i < gameField.length; i++) {
				if ((gameField[line][i].getNumber() != 0) 
						&& (gameField[line][i].getNumber() != 99)) {
					tab[i] = 1;					
				}
			}
		}
		
		int count = 0;
		for (int k = 0; k < tab.length; k++) {
			count += tab[k];
		}
		
		if (count == Math.round(gameField.length / 2) + 1) {
			for (int i = 0; i < gameField.length; i++) {
				for (int j = 0; j < tab.length; j++) {
					if (tab[j] == 0) {
						gameField[i][j] = new Tile("Out", 0, 99);
						Image tile = null;
						ImageView tileView = null;
						try {
							tile = new Image(new FileInputStream("src\\com\\window\\tiles\\out4.png"));
						} catch (FileNotFoundException e) {
							System.out.println("error getting tileView");
						}
						tileView = new ImageView(tile);
						tileView.setFitWidth(squareSize);
						tileView.setFitHeight(squareSize);
						int xImage = (squareSize * j) + topLeftCornerX;
						int yImage = (squareSize * i) + topLeftCornerY;
						ImageView a = tileView;
						a.setX(xImage);
						a.setY(yImage);
						gameFieldPane.getChildren().add(a);
					}
				}
			}
			
		}
	}
	
	public void completeColumn(int gameFieldSize, int topLeftCornerX, int topLeftCornerY, int squareSize) {
		int[] tab = new int[gameField.length];
		for (int column = 0; column < gameField.length; column++) {
			for (int i = 0; i < gameField.length; i++) {
				if ((gameField[i][column].getNumber() != 0) 
						&& (gameField[i][column].getNumber() != 99)) {
					tab[i] = 1;					
				}
			}
		}

		int count = 0;
		for (int k = 0; k < tab.length; k++) {
			count += tab[k];
		}
		
		if (count == Math.round(gameField.length / 2) + 1) {
			for (int i = 0; i < gameField.length; i++) {
				for (int j = 0; j < tab.length; j++) {
					if (tab[j] == 0) {
						gameField[j][i] = new Tile("Out", 0, 99);
						Image tile = null;
						ImageView tileView = null;
						try {
							tile = new Image(new FileInputStream("src\\com\\window\\tiles\\out4.png"));
						} catch (FileNotFoundException e) {
							System.out.println("error getting tileView");
						}
						tileView = new ImageView(tile);
						tileView.setFitWidth(squareSize);
						tileView.setFitHeight(squareSize);
						int xImage = (squareSize * i) + topLeftCornerX;
						int yImage = (squareSize * j) + topLeftCornerY;
						ImageView a = tileView;
						a.setX(xImage);
						a.setY(yImage);
						gameFieldPane.getChildren().add(a);
					}
				}
			}
			
		}
	}
	
	public int nbPoints() {
		/**
		 * Calculates the number of points for a player.
		 */

		int nbPoints = 0;														//points of the player
		
		try {
			Tile[][] gameF = gameField;
			int nbCrowns = 0;														//number of crowns of the squares of one type of field
			int count = 0;															//number of squares with the same field
			ArrayList<Integer> begin = this.firstSquarePlayed();						//first square of the playable field
			int i = begin.get(0);
			int j = begin.get(1);
	
			ArrayDeque<ArrayList<Integer>> waiting = new ArrayDeque<ArrayList<Integer>>(); 		//dequeue with the squares that have not yet been visited
			ArrayDeque<ArrayList<Integer>> sameField = new ArrayDeque<ArrayList<Integer>>();	//same but with the same field as the most recent one 
			HashSet<ArrayList<Integer>> dejaVu = new HashSet<ArrayList<Integer>>();				//HashSet with visited squares 
			ArrayList<Integer> next = new ArrayList<Integer>();									//position of the squares for the next turn
			
			while (true) {															//start again until the 2 queues are empty
				int num = gameF[i][j].getNumber();
				Tile ds = new Tile();
				ds.setNbCrowns(num);
				nbCrowns += ds.getNbCrowns();										//increment of the 2 counts needed
				count ++;
				
				dejaVu.add(new ArrayList<Integer>(Arrays.asList(i, j))); 			//add the square to the HashSet of visited squares
				try {
				if ((i == gameF.length - 1) || (gameF[i + 1][j].getNumber() == 99)) {
					
					/* first case: down side (verification that the squares are not already in the set dejaVu and = 0) */
	
					if ((j == 0) || (gameF[i][j - 1].getNumber() == 99)) {
						
						/* case 1.1: left hand and down side */
						
						if ((gameF[i][j + 1].getField().compareTo(gameF[i][j].getField()) == 0) 
								&& (gameF[i][j].getNumber() != 0)
								&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j + 1))) == false)) {
							
							sameField.add(new ArrayList<Integer>(Arrays.asList(i, j + 1)));
							
						} else if ((gameF[i][j + 1].getField().compareTo(gameF[i][j].getField()) != 0)
								&& (gameF[i][j + 1].getNumber() != 0)
								&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j + 1))) == false)) {
							
							waiting.add(new ArrayList<Integer>(Arrays.asList(i, j + 1)));
						}
					} else if ((j == gameF.length - 1) || (gameF[i][j + 1].getNumber() == 99)) {
						
						/* case 1.2: right hand and down side */
						
						if ((gameF[i][j - 1].getField().compareTo(gameF[i][j].getField()) == 0) 
								&& (gameF[i][j].getNumber() != 0)
								&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j - 1))) == false)) {
							
							sameField.add(new ArrayList<Integer>(Arrays.asList(i, j - 1)));
							
						} else if ((gameF[i][j - 1].getField().compareTo(gameF[i][j].getField()) != 0)
								&& (gameF[i][j - 1].getNumber() != 0)
								&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j - 1))) == false)) {
							
							waiting.add(new ArrayList<Integer>(Arrays.asList(i, j - 1)));
						}
					} else {
						
						/* case 1.3: down side only */
						
						if ((gameF[i][j - 1].getField().compareTo(gameF[i][j].getField()) == 0)
								&& (gameF[i][j].getNumber() != 0)
								&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j - 1))) == false)) {
							
							sameField.add(new ArrayList<Integer>(Arrays.asList(i, j - 1)));
							
						} else if ((gameF[i][j - 1].getField().compareTo(gameF[i][j].getField()) != 0)
								&& (gameF[i][j - 1].getNumber() != 0)
								&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j - 1))) == false)) {
							
							waiting.add(new ArrayList<Integer>(Arrays.asList(i, j - 1)));
						
						}
						if ((gameF[i][j + 1].getField().compareTo(gameF[i][j].getField()) == 0)
								&& (gameF[i][j].getNumber() != 0)
								&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j + 1))) == false)) {
							
							sameField.add(new ArrayList<Integer>(Arrays.asList(i, j + 1)));
							
						} else if ((gameF[i][j + 1].getField().compareTo(gameF[i][j].getField()) != 0)
								&& (gameF[i][j + 1].getNumber() != 0)
								&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j + 1))) == false)) {
							
							waiting.add(new ArrayList<Integer>(Arrays.asList(i, j + 1)));
						}
					} 
				} else if ((j == 0) || (gameF[i][j - 1].getNumber() == 99)) {
					
					/* second case: left hand side (verification that the squares are not already in the set dejaVu and = 0) */
	
					if ((gameF[i][j + 1].getField().compareTo(gameF[i][j].getField()) == 0) 
							&& (gameF[i][j].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j + 1))) == false)) {
						
						sameField.add(new ArrayList<Integer>(Arrays.asList(i, j + 1)));
						
					} else if ((gameF[i][j + 1].getField().compareTo(gameF[i][j].getField()) != 0) 
							&& (gameF[i][j + 1].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j + 1))) == false)) {
						
						waiting.add(new ArrayList<Integer>(Arrays.asList(i, j + 1)));
						
					}
					if ((gameF[i + 1][j].getField().compareTo(gameF[i][j].getField()) == 0)
							&& (gameF[i][j].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i + 1, j))) == false)) {
						
						sameField.add(new ArrayList<Integer>(Arrays.asList(i + 1, j)));
						
					} else if ((gameF[i + 1][j].getField().compareTo(gameF[i][j].getField()) != 0)
							&& (gameF[i + 1][j].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i + 1, j))) == false)) {
						
						waiting.add(new ArrayList<Integer>(Arrays.asList(i + 1, j)));
					}
	
				} else if ((j == gameF.length - 1) || (gameF[i][j + 1].getNumber() == 99)) {
					
					/* third case: right hand side (verification that the squares are not already in the set dejaVu and = 0) */
	
					if ((gameF[i][j - 1].getField().compareTo(gameF[i][j].getField()) == 0 )
							&& (gameF[i][j].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j - 1))) == false)) {
						
						sameField.add(new ArrayList<Integer>(Arrays.asList(i, j - 1)));
						
					} else if ((gameF[i][j - 1].getField().compareTo(gameF[i][j].getField()) != 0)
							&& (gameF[i][j - 1].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j - 1))) == false)) {
						
						waiting.add(new ArrayList<Integer>(Arrays.asList(i, j - 1)));
						
					}
					if ((gameF[i + 1][j].getField().compareTo(gameF[i][j].getField()) == 0)
							&& (gameF[i][j].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i + 1, j))) == false)) {
						
						sameField.add(new ArrayList<Integer>(Arrays.asList(i + 1, j)));
						
					} else if ((gameF[i + 1][j].getField().compareTo(gameF[i][j].getField()) != 0) 
							&& (gameF[i + 1][j].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i + 1, j))) == false)) {
						
						waiting.add(new ArrayList<Integer>(Arrays.asList(i + 1, j)));
					}
	
				} else {
					
					/* fourth case: anywhere in the field that was not treated previously */
	
					if ((gameF[i][j - 1].getField().compareTo(gameF[i][j].getField()) == 0) 
							&& (gameF[i][j].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j - 1))) == false)) {
						
						sameField.add(new ArrayList<Integer>(Arrays.asList(i, j - 1)));
						
					} else if ((gameF[i][j - 1].getField().compareTo(gameF[i][j].getField()) != 0) 
							&& (gameF[i][j - 1].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j - 1))) == false)) {
						
						waiting.add(new ArrayList<Integer>(Arrays.asList(i, j - 1)));
						
					} 
					if ((gameF[i][j + 1].getField().compareTo(gameF[i][j].getField()) == 0)
							&& (gameF[i][j].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j + 1))) == false)) {
						
						sameField.add(new ArrayList<Integer>(Arrays.asList(i, j + 1)));
						
					} else if ((gameF[i][j + 1].getField().compareTo(gameF[i][j].getField()) != 0)
							&& (gameF[i][j + 1].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i, j + 1))) == false)) {
						
						waiting.add(new ArrayList<Integer>(Arrays.asList(i, j + 1)));
						
					} 
					if ((gameF[i + 1][j].getField().compareTo(gameF[i][j].getField()) == 0)
							&& (gameF[i][j].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i + 1, j))) == false)) {
						
						sameField.add(new ArrayList<Integer>(Arrays.asList(i + 1, j)));
						
					} else if ((gameF[i + 1][j].getField().compareTo(gameF[i][j].getField()) != 0)
							&& (gameF[i + 1][j].getNumber() != 0)
							&& (dejaVu.contains(new ArrayList<Integer>(Arrays.asList(i + 1, j))) == false)) {
						
						waiting.add(new ArrayList<Integer>(Arrays.asList(i + 1, j)));
					}
				}
				
				if (sameField.size() != 0 && waiting.size() != 0) {						//if there is a square with the same field as the current one
					next = sameField.poll();
					while (dejaVu.contains(next) && sameField.size() != 0) { 			//already contained in the set dejaVu ?
						next = sameField.poll();
					}
					if (dejaVu.contains(next)) {									//if it is the case
						next = waiting.poll();
						while (dejaVu.contains(next) && waiting.size() != 0) { 			//already contained in the set dejaVu ?
							next = waiting.poll();
						}
						if (dejaVu.contains(next)) {									//if it is the case
							nbPoints += count * nbCrowns;  								//add points gained
							count = 0;													//count for the next type of field
							nbCrowns = 0;												//same for crowns
							break;
						} else {
							nbPoints += count * nbCrowns;  								//add points gained
							count = 0;													//count for the next type of field
							nbCrowns = 0;												//same for crowns
							i = next.get(0);
							j = next.get(1);
						}
					} else {
						i = next.get(0);
						j = next.get(1);
					}

				} else if (sameField.size() == 0 && waiting.size() != 0) {
					next = waiting.poll();
					while (dejaVu.contains(next) && waiting.size() != 0) { 				//already contained in the set dejaVu ?
						next = waiting.poll();
					}
					if (dejaVu.contains(next)) {									//if it is the case
						nbPoints += count * nbCrowns;  								//add points gained
						count = 0;													//count for the next type of field
						nbCrowns = 0;												//same for crowns
						break;
					} else {
						nbPoints += count * nbCrowns;  								//add points gained
						count = 0;													//count for the next type of field
						nbCrowns = 0;												//same for crowns
						i = next.get(0);
						j = next.get(1);
					}
				} else if (sameField.size() != 0 && waiting.size() == 0) {
						next = sameField.poll();
						while (dejaVu.contains(next) && sameField.size() != 0) { 				//already contained in the set dejaVu ?
							next = sameField.poll();
						}
						if (dejaVu.contains(next)) {									//if it is the case
							nbPoints += count * nbCrowns;  								//add points gained
							count = 0;													//count for the next type of field
							nbCrowns = 0;												//same for crowns
							break;
						} else {
							i = next.get(0);
							j = next.get(1);
						}
				} else {
					nbPoints += count * nbCrowns;  								//add points gained
					count = 0;													//count for the next type of field
					nbCrowns = 0;												//same for crowns
					break;
				}
				} catch (NullPointerException e) {
					System.out.println("a problem happened"); 
					System.out.println(ds.getNumber());
					System.out.println(i);
					System.out.println(j);
					break;
				}
			} 
		} catch (IndexOutOfBoundsException e) {
			return 0;
		}
		return nbPoints;														//the points of the player are returned
	}

	public ArrayList<Integer> firstSquarePlayed() {
		/**
		 * Searches for the first square with a domino square and returns its position
		 */
		
		Tile[][] gf = gameField;
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		determination: 
		for (int i = 0; i < gf.length; i++) {
			for (int j = 0; j < gf.length; j++) {
				if (gf[i][j].getNumber() > 0 && gf[i][j].getNumber() < 17) {
					list.add(i);
					list.add(j);
					break determination;
				}
			}
		}
		return list;
	}

	public ArrayList<ArrayList<Integer>> playablePositions(Domino d) {
		ArrayList<ArrayList<Integer>> positions = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < gameField.length; i++) {
			for (int j = 0; j < gameField.length - 1; j++) {
				if (this.gameField[i][j].getNumber() != 99 && this.gameField[i][j + 1].getNumber() != 99) {
					if (d.isPlayable(this, 'R', i, j, i, j + 1, true)) {
						positions.add(new ArrayList<Integer>(Arrays.asList(i, j, i, j + 1)));
					}
					if (d.isPlayable(this, 'L', i, j + 1, i, j, true)) {
						positions.add(new ArrayList<Integer>(Arrays.asList(i, j + 1, i, j)));
					}
				}
			}
		}
		for (int j = 0; j < gameField.length; j++) {
			for (int i = 0; i < gameField.length - 1; i++) {
				if (this.gameField[i][j].getNumber() != 99 && this.gameField[i + 1][j].getNumber() != 99) {
					if (d.isPlayable(this, 'D', i, j, i + 1, j, true)) {
						positions.add(new ArrayList<Integer>(Arrays.asList(i, j, i + 1, j)));
					}
					if (d.isPlayable(this, 'U', i + 1, j, i, j, true)) {
						positions.add(new ArrayList<Integer>(Arrays.asList(i + 1, j, i, j)));
					}
				}
			}
		}
		if (positions.size() == 0) {
			positions = null;
		}
		return positions;
	}
	
	public void setGameFieldForWindow(VBox gameFieldBox,int windowWidth, int windowHeight) {
		
		this.setPointsText();
		gameFieldBox.getChildren().clear();
		gameFieldBox.setAlignment(Pos.CENTER);
		gameFieldBox.setPrefSize(Math.round(windowWidth / 2), windowHeight - 50);
		gameFieldBox.getChildren().addAll(this.kingText, this.pointsText, this.gameFieldPane);

	}
}
