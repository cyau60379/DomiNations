package com.window;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * This class allows the manipulation of domino squares.
 *
 */
public class Tile {
	
	private String 	field;													//the represented field
	private int 	nbCrowns;												//the number of crowns on the square
	private int 	number;
	
	public Tile() {}
	
	public Tile(String field, int nbCrowns, int number) {
		this.field = field;
		this.nbCrowns = nbCrowns;
		this.number = number;
	}
	
	public String getField() {												//getter for the field
		return field;
	}
	
	public int getNbCrowns() {												//getter for the number of crowns
		return nbCrowns;
	}
	
	public int getNumber() {												//getter for the square number
		return number;
	}
	
	public void setField(int number) {										//setter for the field
		FileReader reader = null;
		BufferedReader table = null;
		try {																//see Main for comments on reading a file
			String location = "src\\com\\window\\csv\\tiles.csv";
			reader = new FileReader(location); 
			table = new BufferedReader(reader);
			String line = table.readLine();
			while (true) {
				line = table.readLine();
				if (line == null) {
					break;
				} else {
					String[] information = line.split(",");
					if (Integer.parseInt(information[0]) == number) {		//obtains the field associated with the number
						this.field = information[1];
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("The file is untraceable.");
		} finally {
			try {
				table.close();
				reader.close();
			} catch (IOException e2) {
				e2.printStackTrace();
				System.out.println("The file is untraceable.");
			}
		}			
	}
	
	public void setNbCrowns(int number) {									//setter for the number of crowns
		FileReader reader = null;
		BufferedReader table = null;
		try {																//see Main for comments on reading a file
			String location = "src\\com\\window\\csv\\tiles.csv";
			reader = new FileReader(location);
			table = new BufferedReader(reader);
			String line = table.readLine();
			while (true) {
				line = table.readLine();
				if (line == null) {
					break;
				} else {
					String[] information = line.split(",");
					if (Integer.parseInt(information[0]) == number) {		//obtains the field associated with the number
						this.nbCrowns = Integer.parseInt(information[2]);
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("The file is untraceable.");
		} finally {
			try {
				table.close();
				reader.close();
			} catch (IOException e2) {
				e2.printStackTrace();
				System.out.println("The file is untraceable.");
			}
		}				
	}
	
	public void setNumber(int nb) { 										//setter for the square number
		this.number = nb;
	}
}
