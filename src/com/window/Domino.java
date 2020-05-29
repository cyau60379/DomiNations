package com.window;

/**
 * 
 * This class allows the manipulation of dominos.
 *
 */
public class Domino {
	private Tile 	domSq1, domSq2;
	private int 			number;
	

	public Domino(Tile domSq1, Tile domSq2, int number) {
		this.domSq1 = domSq1;
		this.domSq2 = domSq2;
		this.number = number;
	}

	public Tile getSq1() { 											//getter for the 1st square
		return domSq1;
	}
	
	public Tile getSq2() { 											//getter for the 2nd square
		return domSq2;
	}
	
	public int getNumber() { 												//getter for the domino number
		return number;
	}
	
	public void setSq1(Tile domSq1) {								//setter for the 1st square
		this.domSq1 = domSq1;
	}
	
	public void setSq2(Tile domSq2) {								//setter for the 2nd square
		this.domSq2 = domSq2;
	}
	
	public void setNumber(int n) {											//setter for the domino number
		this.number = n;
	}
	
	public boolean isPlayable(Player player, char currentOrientation, int x1, int y1, int x2, int y2, boolean forImpossible) {
		/*
		 * Checks if the selected position is valid.
		 */
		
		Tile[][] gameField = player.getGameField();
		int len = player.gameField.length;

		
		boolean isChoiceInField = false;
		boolean isFirstPosEmpty = false;
		boolean isSecondPosEmpty = false;
		boolean isNextToCastle = false;
		boolean areFieldsMatching = false;
		boolean isInBounds = false;
		
	    /* choice in field ? */
	    if ((x1 >= 0) && (x1 < len) 
	    		&& (y1 >= 0) && (y1 < len) 
	    		&& (x2 >= 0) && (x2 < len) 
	    		&& (y2 >= 0) && (y2 < len)) {
	    	isChoiceInField = true;
		}
	    
		if (isChoiceInField == true) {
			
			/* first pos empty ? */
		    if (gameField[x1][y1].getNumber() == 0) {
				isFirstPosEmpty = true;
			}
			
		    /* second pos empty ? */
		    if (gameField[x2][y2].getNumber() == 0) {
				isSecondPosEmpty = true;
			}
			
			/* next to castle ? */
			if (((x1 == Math.round(len / 2)) && (y1 == Math.round(len / 2) - 1)) 
					|| ((x1 == Math.round(len / 2) - 1) && (y1 == Math.round(len / 2)))
					|| ((x1 == Math.round(len / 2)) && (y1 == Math.round(len / 2) + 1))
					|| ((x1 == Math.round(len / 2) + 1) && (y1 == Math.round(len / 2))) 
					|| ((x2 == Math.round(len / 2)) && (y2 == Math.round(len / 2) - 1)) 
					|| ((x2 == Math.round(len / 2) - 1) && (y2 == Math.round(len / 2))) 
					|| ((x2 == Math.round(len / 2)) && (y2 == Math.round(len / 2) + 1)) 
					|| ((x2 == Math.round(len / 2) + 1) && (y2 == Math.round(len / 2)))) {
				isNextToCastle = true;
			}
			
			/* fields match ? */
			switch (currentOrientation) {
			
			case 'R':
				if ((y1 == 0) || (gameField[x1][y1 - 1].getNumber() == 99)) {
					if ((x1 == 0) || (gameField[x1 - 1][y1].getNumber() == 99)) {
						if ((gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0) 
								|| (gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0) 
								|| (gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else if ((x1 == len - 1) || (gameField[x1 + 1][y1].getNumber() == 99)) {			
						if ((gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
							areFieldsMatching = true;
						}			
					} else {			
						if ((gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
							areFieldsMatching = true;
						}		
					}	
				} else if ((y2 == len - 1) || (gameField[x2][y2 + 1].getNumber() == 99)) {
					if ((x1 == 0) || (gameField[x1 - 1][y1].getNumber() == 99)) {
						if ((gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else if ((x1 == len - 1) || (gameField[x1 + 1][y1].getNumber() == 99)) {
						if ((gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else {
						if ((gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
							areFieldsMatching = true;
						}
					}
				} else if ((x1 == 0) || (gameField[x1 - 1][y1].getNumber() == 99)) {
					if ((gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
						areFieldsMatching = true;
					}
				} else if ((x1 == len - 1) || (gameField[x1 + 1][y1].getNumber() == 99)) {
					if ((gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
						areFieldsMatching = true;
					}
				} else {
					if ((gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
						areFieldsMatching = true;
					}
				}
				break;
				
			case 'D':
				if ((x1 == 0) || (gameField[x1 - 1][y1].getNumber() == 99)) {
					if ((y1 == 0) || (gameField[x1][y1 - 1].getNumber() == 99)) {
						if ((gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else if ((y1 == len - 1) || (gameField[x1][y1 + 1].getNumber() == 99)) {
						if ((gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else {
						if ((gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
							areFieldsMatching = true;
						}
					}
				} else if ((x2 == len - 1) || (gameField[x2 + 1][y2].getNumber() == 99)) {
					if ((y1 == 0) || (gameField[x1][y1 - 1].getNumber() == 99)) {
						if ((gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else if ((y1 == len - 1) || (gameField[x1][y1 + 1].getNumber() == 99)) {
						if ((gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else {
						if ((gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)) {
							areFieldsMatching = true;
						}
					}
				} else if ((y1 == 0) || (gameField[x1][y1 - 1].getNumber() == 99)) {
					if ((gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
						areFieldsMatching = true;
					}
				} else if ((y1 == len - 1) || (gameField[x1][y1 + 1].getNumber() == 99)) {
					if ((gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)) {
						areFieldsMatching = true;
					}
				} else {
					if ((gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)) {
						areFieldsMatching = true;
					}
				}
				break;
				
			case 'L':
				if ((y2 == 0) || (gameField[x2][y2 - 1].getNumber() == 99)) {
					if ((x2 == 0) || (gameField[x2 - 1][y2].getNumber() == 99)) {
						if ((gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else if ((x2 == len - 1) || (gameField[x2 + 1][y2].getNumber() == 99)) {
						if ((gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else {
						if ((gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
							areFieldsMatching = true;
						}
					}
				} else if ((y1 == len - 1) || (gameField[x1][y1 + 1].getNumber() == 99)) {
					if ((x2 == 0) || (gameField[x2 - 1][y2].getNumber() == 99)) {
						if ((gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else if ((x2 == len - 1) || (gameField[x2 + 1][y2].getNumber() == 99)) {
						if ((gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else {
						if ((gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
							areFieldsMatching = true;
						}
					}
				} else if ((x2 == 0) || (gameField[x2 - 1][y2].getNumber() == 99)) {
					if ((gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
						areFieldsMatching = true;
					}
				} else if ((x2 == len - 1) || (gameField[x2 + 1][y2].getNumber() == 99)) {
					if ((gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
						areFieldsMatching = true;
					}
				} else {
					if ((gameField[x2 + 1][y2].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1 - 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
						areFieldsMatching = true;
					}
				}
				break;
				
			case 'U':
				if ((x2 == 0) || (gameField[x2 - 1][y2].getNumber() == 99)) {
					if ((y2 == 0) || (gameField[x2][y2 - 1].getNumber() == 99)) {
						if ((gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else if ((y2 == len - 1) || (gameField[x2][y2 + 1].getNumber() == 99)) {
						if ((gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else {
						if ((gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
							areFieldsMatching = true;
						}
					}
				} else if ((x1 == len - 1) || (gameField[x1 + 1][y1].getNumber() == 99)) {
					if ((y2 == 0) || (gameField[x2][y2 - 1].getNumber() == 99)) {
						if ((gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else if ((y2 == len - 1) || (gameField[x2][y2 + 1].getNumber() == 99)) {
						if ((gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)) {
							areFieldsMatching = true;
						}
					} else {
						if ((gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)
								|| (gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
								|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)) {
							areFieldsMatching = true;
						}
					}
				} else if ((y2 == 0) || (gameField[x2][y2 - 1].getNumber() == 99)) {
					if ((gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
						areFieldsMatching = true;
					}
				} else if ((y2 == len - 1) || (gameField[x2][y2 + 1].getNumber() == 99)) {
					if ((gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)) {
						areFieldsMatching = true;
					}
				} else {
					if ((gameField[x2][y2 + 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2 - 1][y2].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x2][y2 - 1].getField().compareTo(domSq2.getField()) == 0)
							|| (gameField[x1][y1 + 1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1 + 1][y1].getField().compareTo(domSq1.getField()) == 0)
							|| (gameField[x1][y1 - 1].getField().compareTo(domSq1.getField()) == 0)) {
						areFieldsMatching = true;
					}
				}
				break;
				
			default:
				break;
			}
			
			
			/* is out of bounds ? */
			int[] boundsList = new int[len];
			int[] boundsList2 = new int[len];
			
				boundsList[x1] = 1;
				boundsList[x2] = 1;
				for (int i = 0; i < gameField.length; i++) {
					for (int j = 0; j < gameField.length; j++) {
						if ((gameField[j][i].getNumber() != 0)
								&& (gameField[j][i].getNumber() != 99)) {
							boundsList[j] = 1;
						}
					}
				}

				boundsList2[y1] = 1;
				boundsList2[y2] = 1;
				for (int i = 0; i < gameField.length; i++) {
					for (int j = 0; j < gameField.length; j++) {
						if ((gameField[i][j].getNumber() != 0)
								&& (gameField[i][j].getNumber() != 99)) {
							boundsList2[j] = 1;
						}
					}
				}
			
			int checkSum = 0;
			int checkSum2 = 0;
			for (int k = 0; k < boundsList.length; k++) {
				if (boundsList[k] == 1) {
					checkSum++;
				}
				if (boundsList2[k] == 1) {
					checkSum2++;
				}
			}
			
			if ((checkSum <= (Math.round(len / 2) + 1)) && (checkSum2 <= (Math.round(len / 2) + 1))) {
				isInBounds = true;
			}
			
		}
			
			
		if ((isChoiceInField) 
				&& (isInBounds)
				&& (isFirstPosEmpty) 
				&& (isSecondPosEmpty) 
				&& ((isNextToCastle) || (areFieldsMatching))) {
			return true;
		} else {
			if (player.isHuman && forImpossible == false) {
				if (isChoiceInField == false) {
					System.out.println("That position is not in the field !");
				} else if (isFirstPosEmpty == false) {
					System.out.println("That position for sq1 is not available !");
				} else if (isSecondPosEmpty == false) {
					System.out.println("That position for sq2 is not available !");
				} else if ((isNextToCastle == false) && (areFieldsMatching == false)) {
					System.out.println("None of the fields match with those already placed !");
				} else if (isInBounds == false) {
					System.out.println("This position is out of bounds !");
				}
			}
			return false;
		}
	
	}
	
	public boolean isImpossibleToPlay(Player player) {
		/**
		 * Checks if the domino can be placed horizontally on the gameField.
		 */
		boolean isImpossible = true;
		for (int i = 0; i < player.gameField.length; i++) {
			for (int j = 0; j < player.gameField.length - 1; j++) {
				if (player.gameField[i][j].getNumber() != 99 && player.gameField[i][j + 1].getNumber() != 99) {
					if (this.isPlayable(player, 'R', i, j, i, j + 1, true)) {
						isImpossible = false;
					}
					if (this.isPlayable(player, 'L', i, j + 1, i, j, true)) {
						isImpossible = false;
					}
				}
			}
		}
		for (int j = 0; j < player.gameField.length; j++) {
			for (int i = 0; i < player.gameField.length - 1; i++) {
				if (player.gameField[i][j].getNumber() != 99 && player.gameField[i + 1][j].getNumber() != 99) {
					if (this.isPlayable(player, 'D', i, j, i + 1, j, true)) {
						isImpossible = false;
					}
					if (this.isPlayable(player, 'U', i + 1, j, i, j, true)) {
						isImpossible = false;
					}
				}
			}
		}
		return isImpossible;
	}
}
