package com.window;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;


public class ConfirmationWindow {

	static boolean answer;
	
	public static boolean display(String title, String message) {	//static so that it can be called without an instantiation
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);			//blocks event from being delivered to other windows
		window.setTitle(title);
		window.setMinHeight(160);
		window.setMinWidth(325);
		
		
		Label label = new Label();
		label.setText(message);
		
		Button yesButton = new Button("Confirm");
		yesButton.setOnAction(e -> {
			answer = true; 
			window.close();
		});
		
		Button noButton = new Button("Cancel");
		noButton.setOnAction(e -> {
			answer = false; 
			window.close();
		});
		
		HBox buttonsBox = new HBox(20);								//spacing in argument
		buttonsBox.getChildren().addAll(yesButton, noButton);
		buttonsBox.setAlignment(Pos.CENTER);
		
		
		VBox layout = new VBox(15);									//spacing in argument
		layout.getChildren().addAll(label, buttonsBox);
		layout.setAlignment(Pos.CENTER);
		layout.setStyle("-fx-font: normal 30px 'Script MT Bold'");
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();										//displays the window and waits for it to be closed before returning to the caller
		
		return answer;		
		
	}

}
