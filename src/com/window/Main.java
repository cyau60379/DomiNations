package com.window;

import java.io.FileInputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;


import javafx.application.*;
import javafx.beans.value.*;
import javafx.animation.*;
import javafx.stage.*;
import javafx.util.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.geometry.*;


public class Main extends Application {

	GameLogic gameLogic = new GameLogic();
	AnimationTimer gameLoop;
	int windowWidth = 1600;
	int windowHeight = 900;

	Stage window;
	Scene titleScreenScene, mainMenuScene, gameModeScene, explanationScene, endScreenScene;

	Scene gameScene;

	ArrayDeque<Character> orientations = new ArrayDeque<Character>(Arrays.asList('D', 'L', 'U', 'R'));
	char currentOrient = 'R';

	Text gameStateText = new Text("Choose a domino");

	Text[] num = { new Text(""), new Text(""), new Text(""), new Text("") };

	Button[] secondNum = { new Button(""), new Button(""), new Button(""), new Button("") };
	boolean[] isSndNumDisabled = {false, false, false, false};
	Button rotateButton = new Button("Rotate");
	Button impossibleButton = new Button("Impossible to play ?");
	Button continueTurn = new Button("Continue");
	Button exitGame = new Button("Back to menu");
	
	int x1;
	int y1;
	int x2;
	int y2;

	int squareSize;
	int topLeftCornerX;
	int topLeftCornerY;

	Pane gameRoot = new Pane();
	Pane thisTurnPane = new Pane();
	HBox nextTurnsDeckBox = new HBox();
	HBox topBox = new HBox(10);
	VBox gameFieldBox = new VBox();
	VBox dominoBox = new VBox();
	BorderPane gameBorderPane = new BorderPane();
	Pane nextTurnPane = new Pane();
	Pane secondNextTurnPane = new Pane();
	Text kingText = new Text(" ");
	Text pointsText = new Text(" ");
	CheckBox fastModeCheckBox = new CheckBox("Fast Mode");
	
	Text fastModeExpl = new Text("Fast Mode: Eliminates additional delay when only AI are playing");
	Text dynastyExpl = new Text("Dynasty: 3 rounds, points are summed at the end");
	Text harmonyExpl = new Text("Harmony: Additional points are awarded if there are no holes in the game field");
	Text middleEmpireExpl = new Text("Middle Empire: Additional points are awarded if the castle is in the center of the game field");
	Text greatDuelExpl = new Text("Great Duel (2 players only): Game field is 7x7 instead of 5x5");
	Text apocalypseExpl = new Text("Apocalypse (3 players only): 4 dominos are available each turn, but the one that isn't chosen is discarded");
	Text cooperationExpl = new Text("Cooperation (4 players only): Play in teams! Choose your team names and composition and play!");
	Button backButton2 = new Button("Back");
	
	VBox explanationBox = new VBox(10);
	Pane explanationRoot = new Pane();
	
	int countAITurn = 0;
	Text text1 = new Text("End of Game!");
	Text text2 = new Text("Scores: ");
	ArrayList<Text> scores = new ArrayList<Text>();
	ArrayList<Text> winners = new ArrayList<Text>();
	Text text3 = new Text("Victory for ");
	
	Button backButton3 = new Button("Back to main menu");
	
	VBox endScreenBox = new VBox(5);
	StackPane endScreenRoot = new StackPane();
	

	public static void main(String[] args) {
		launch(args);		//application to launch the graphic interface
	}

	@Override
	public void start(Stage primaryStage) throws Exception {		//function which initializes the window (here primaryStage)

		// font settings
		String style = "-fx-font: normal 35px 'Script MT Bold'";

		// images
		Image blackScreen = new Image(new FileInputStream("src\\com\\window\\images\\blackScreen.png"));
		Image studio = new Image(new FileInputStream("src\\com\\window\\images\\studio.png"));
		Image background = new Image(new FileInputStream("src\\com\\window\\images\\background.png"));
		Image titleCard = new Image(new FileInputStream("src\\com\\window\\images\\title4.png"));
		Image cloud = new Image(new FileInputStream("src\\com\\window\\images\\cloud.png"));
		Image background2 = new Image(new FileInputStream("src\\com\\window\\images\\background2.png"));

		// get screen size
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		int windowWidth = (int) primaryScreenBounds.getWidth();
		int windowHeight = (int) primaryScreenBounds.getHeight();
//		int windowWidth = 1600;
//		int windowHeight = 900;
		System.out.println("Screen Resolution: " + windowWidth + "x" + windowHeight);

		// sizes and positions based on screen size
		topLeftCornerX = Math.round(windowWidth / 16);
		topLeftCornerY = Math.round(windowHeight / 16);
		squareSize = Math.round(windowWidth / 24);

		// window
		window = primaryStage;
		// set settings of the window (size, title, ...)
		window.setWidth(windowWidth);
		window.setHeight(windowHeight);
		window.setTitle("DomiNations");
		window.setResizable(false);
		window.initStyle(StageStyle.UNDECORATED);
		//executes the closeGame method instead of closing the window
		window.setOnCloseRequest(e -> {
			e.consume();
			closeGame();
		});

		/*
		 * ======================================================= Title screen layout
		 */

		// widgets

		// black screen
		ImageView blackScreenView = new ImageView(blackScreen);
		blackScreenView.setFitWidth(windowWidth);
		blackScreenView.setFitHeight(windowHeight);
		blackScreenView.setX(0);
		blackScreenView.setY(0);
		FadeTransition blackScreenFadeIn = new FadeTransition(Duration.seconds(1), blackScreenView); // fade in
		blackScreenFadeIn.setFromValue(0);
		blackScreenFadeIn.setToValue(1);
		FadeTransition blackScreenFadeOut = new FadeTransition(Duration.seconds(1), blackScreenView); // fade out
		blackScreenFadeOut.setFromValue(1);
		blackScreenFadeOut.setToValue(0);

		// studio (fade transition of the name of the studio. 1s of appearance, 1s of disappearance)
		ImageView studioView = new ImageView(studio);
		studioView.setFitWidth(windowWidth);
		studioView.setFitHeight(windowHeight);
		studioView.setX(0);
		studioView.setY(0);
		FadeTransition studioFadeIn = new FadeTransition(Duration.seconds(1), studioView); // fade in
		studioFadeIn.setFromValue(0);
		studioFadeIn.setToValue(1);
		FadeTransition studioFadeOut = new FadeTransition(Duration.seconds(1), studioView); // fade out
		studioFadeOut.setFromValue(1);
		studioFadeOut.setToValue(0);

		// background (fade transition of the name of the studio. 1s of appearance)
		ImageView backgroundView1 = new ImageView(background);
		backgroundView1.setFitWidth(windowWidth);
		backgroundView1.setFitHeight(windowHeight);
		backgroundView1.setX(0);
		backgroundView1.setY(0);
		FadeTransition backgroundFadeIn = new FadeTransition(Duration.seconds(1), backgroundView1); // fade in
		backgroundFadeIn.setFromValue(0);
		backgroundFadeIn.setToValue(1);

		// title card (fade transition of the name of the studio. 0.5s of appearance)
		ImageView titleCardView = new ImageView(titleCard);
		int titleCardWidth = (int) Math.round(windowWidth * 0.8);
		int titleCardHeight = (int) Math.round(windowHeight * 0.6);
		titleCardView.setFitWidth(titleCardWidth);
		titleCardView.setFitHeight(titleCardHeight);
		titleCardView.setX(Math.round((windowWidth / 2) - (titleCardWidth / 2)));
		titleCardView.setY(Math.round((windowHeight / 2) - (titleCardHeight / 2)));
		FadeTransition titleCardFadeIn = new FadeTransition(Duration.seconds(.5), titleCardView); // fade in
		titleCardFadeIn.setFromValue(0);
		titleCardFadeIn.setToValue(1);

		// blinking text (composition of two fade transitions during an indefinite time)
		Text continueText = new Text("Click to continue...");
		continueText.setFont(Font.font("Script MT Bold", FontPosture.REGULAR, 50));
		continueText.setX(Math.round((windowWidth * 0.37)));
		continueText.setY(Math.round((windowHeight * 0.9)));
		FadeTransition continueTextFadeIn = new FadeTransition(Duration.seconds(.5), continueText); // fade in
		continueTextFadeIn.setFromValue(0);
		continueTextFadeIn.setToValue(1);
		FadeTransition continueTextBlink = new FadeTransition(Duration.seconds(.7), continueText); // blink
		continueTextBlink.setFromValue(1);
		continueTextBlink.setToValue(0);
		continueTextBlink.setCycleCount(Timeline.INDEFINITE);
		continueTextBlink.setAutoReverse(true);

		// clouds (translation from the left corner of the screen to the right corner during an indefinite time)
		ImageView cloudView1 = new ImageView(cloud);
		cloudView1.setX(-500);
		cloudView1.setY(-100);
		KeyValue cloud1XKV = new KeyValue(cloudView1.xProperty(), windowWidth + 500);
		KeyFrame cloud1KF = new KeyFrame(Duration.seconds(10), cloud1XKV);

		Timeline cloudTimeline1 = new Timeline(); // timeline used for translation
		cloudTimeline1.getKeyFrames().add(cloud1KF);
		cloudTimeline1.setCycleCount(Timeline.INDEFINITE);
		cloudTimeline1.setAutoReverse(false);

		ImageView cloudView2 = new ImageView(cloud);
		cloudView2.setX(-500);
		cloudView2.setY(-150);
		cloudView2.setScaleX(.7);
		cloudView2.setScaleY(.7);
		KeyValue cloud2XKV = new KeyValue(cloudView2.xProperty(), windowWidth + 500);
		KeyFrame cloud2KF = new KeyFrame(Duration.seconds(8), cloud2XKV);

		Timeline cloudTimeline2 = new Timeline();
		cloudTimeline2.getKeyFrames().add(cloud2KF);
		cloudTimeline2.setCycleCount(Timeline.INDEFINITE);
		cloudTimeline2.setAutoReverse(false);

		ImageView cloudView3 = new ImageView(cloud);
		cloudView3.setX(-500);
		cloudView3.setY(-50);
		cloudView3.setScaleX(.4);
		cloudView3.setScaleY(.4);
		KeyValue cloud3XKV = new KeyValue(cloudView3.xProperty(), windowWidth + 500);
		KeyFrame cloud3KF = new KeyFrame(Duration.seconds(9), cloud3XKV);

		Timeline cloudTimeline3 = new Timeline();
		cloudTimeline3.getKeyFrames().add(cloud3KF);
		cloudTimeline3.setCycleCount(Timeline.INDEFINITE);
		cloudTimeline3.setAutoReverse(false);

		// pane : add all widgets here
		Pane titleScreenRoot = new Pane();
		titleScreenRoot.getChildren().addAll(blackScreenView, studioView, backgroundView1, titleCardView, continueText,
				cloudView1, cloudView2, cloudView3);

		// parallel transition: all transitions played at the same time
		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(continueTextBlink, cloudTimeline1, cloudTimeline2, cloudTimeline3);

		// sequential transition: transitions played one after the other in order
		SequentialTransition sequentialTransition = new SequentialTransition();
		sequentialTransition.getChildren().addAll(blackScreenFadeIn, studioFadeIn, studioFadeOut, backgroundFadeIn,
				blackScreenFadeOut, titleCardFadeIn, continueTextFadeIn, parallelTransition);
		sequentialTransition.play();

		titleScreenScene = new Scene(titleScreenRoot, windowWidth, windowHeight);

		// changes to main menu when a click is detected in the scene
		titleScreenScene.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			window.setScene(mainMenuScene);
		});

		/*
		 * ======================================================= Main menu layout
		 */

		// widgets
		
		//title of the game placed on the top of the screen
		ImageView titleView = new ImageView(titleCard);
		int titleWidth = (int) Math.round(windowWidth * 0.6);
		int titleHeight = (int) Math.round(windowHeight * 0.4);
		titleView.setFitWidth(titleWidth);
		titleView.setFitHeight(titleHeight);
		titleView.setX(Math.round((windowWidth / 2) - (titleWidth / 2)));
		titleView.setY(0);

		//button to go to the play menu
		Button playButton1 = new Button("Play");
		playButton1.setOnAction(e -> window.setScene(gameModeScene));

		//button to exit the game
		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> {
			e.consume();
			closeGame();
		});

		//background of the scene
		ImageView backgroundView2 = new ImageView(background);
		backgroundView2.setFitWidth(windowWidth);
		backgroundView2.setFitHeight(windowHeight);
		backgroundView2.setX(0);
		backgroundView2.setY(0);

		//Box to place button automatically one under the last one 
		VBox buttonsBox = new VBox(15);
		buttonsBox.setAlignment(Pos.CENTER);
		buttonsBox.getChildren().addAll(playButton1, exitButton);

		//Pane to put automatically the first behind the second
		StackPane backgroundAndButtons = new StackPane();
		backgroundAndButtons.getChildren().addAll(backgroundView2, buttonsBox);

		//Pane to place the background with buttons and the title
		Pane mainMenuRoot = new Pane();
		mainMenuRoot.setStyle(style);
		mainMenuRoot.getChildren().addAll(backgroundAndButtons, titleView);

		//creation of the scene
		mainMenuScene = new Scene(mainMenuRoot, windowWidth, windowHeight);

		/*
		 * ======================================================== Game mode layout
		 */

		// widgets

		//background
		ImageView backgroundView4 = new ImageView(background);
		backgroundView4.setOpacity(.4);
		backgroundView4.setFitWidth(windowWidth);
		backgroundView4.setFitHeight(windowHeight);
		backgroundView4.setX(0);
		backgroundView4.setY(0);

		//check box for the AI fast mode
		fastModeCheckBox.setDisable(true);
		
		//check box for the dynasty mode 
		CheckBox dynastyCheckBox = new CheckBox("Dynasty");

		//check box for the harmony mode 
		CheckBox harmonyCheckBox = new CheckBox("Harmony; bonus points : ");
		TextField harmonyBonusTextField = new TextField("" + gameLogic.harmonyBonus);
		harmonyBonusTextField.setDisable(true);
		harmonyBonusTextField.setMaxWidth(70);
		harmonyBonusTextField.textProperty().addListener(new ChangeListener<String>() { 
					// prevents from entering anything other than a number
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,1}")) { 	// "\\d{0,1}" = regex that means any number from 0 to 1 digit
					harmonyBonusTextField.setText(oldValue);
				}
			}
		});
		harmonyCheckBox.setOnAction(e -> { // enables/disables the text field depending on the checkbox
			if (harmonyCheckBox.isSelected()) {
				harmonyBonusTextField.setDisable(false);
			} else {
				harmonyBonusTextField.setDisable(true);
			}
		});

		//check box for the middle empire mode
		CheckBox middleEmpireCheckBox = new CheckBox("Middle Empire; bonus points : ");
		TextField middleEmpireBonusTextField = new TextField("" + gameLogic.middleEmpireBonus);
		middleEmpireBonusTextField.setDisable(true);
		middleEmpireBonusTextField.setMaxWidth(90);
		middleEmpireBonusTextField.textProperty().addListener(new ChangeListener<String>() { 
							// prevents from entering anything other than a number
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,3}")) { 	// "\\d{0,3}" = regex that means any number from 0 to 3 digits
					middleEmpireBonusTextField.setText(oldValue);
				}
			}
		});
		middleEmpireCheckBox.setOnAction(e -> { // enables/disables the text field depending on the checkbox
			if (middleEmpireCheckBox.isSelected()) {
				middleEmpireBonusTextField.setDisable(false);
			} else {
				middleEmpireBonusTextField.setDisable(true);
			}
		});

		//check box for the great duel mode (2 players)
		CheckBox greatDuelCheckBox = new CheckBox("Great Duel (2 players only)");

		//check box for the apocalypse mode (3 players)
		CheckBox apocalypseCheckBox = new CheckBox("Apocalypse (3 players only)");
		apocalypseCheckBox.setDisable(true);
		
		//check box for the cooperation mode (4 players)
		CheckBox coopCheckBox = new CheckBox("Cooperation (4 players only)");
		coopCheckBox.setDisable(true);
		
		//field to give a name of the teams
		TextField coopTeam1TextField = new TextField("" + gameLogic.team1Name);
		coopTeam1TextField.setDisable(true);
		coopTeam1TextField.setMaxWidth(520);
		TextField coopTeam2TextField = new TextField("" + gameLogic.team2Name);
		coopTeam2TextField.setDisable(true);
		coopTeam2TextField.setMaxWidth(520);
		
		//box to choose the team composition for the game
		ChoiceBox<String> teamCompositionChoiceBox = new ChoiceBox<String>();
		teamCompositionChoiceBox.getItems().addAll("Blue-Pink / Green-Yellow", "Blue-Green / Pink-Yellow",
				"Blue-Yellow / Pink-Green");
		teamCompositionChoiceBox.setDisable(true);
		coopCheckBox.setOnAction(e -> { // enables/disables the text fields depending on the checkbox
			if (coopCheckBox.isSelected()) {
				coopTeam1TextField.setDisable(false);
				coopTeam2TextField.setDisable(false);
				teamCompositionChoiceBox.setDisable(false);
				teamCompositionChoiceBox.setValue("" + gameLogic.teamComposition);
			} else {
				coopTeam1TextField.setDisable(true);
				coopTeam2TextField.setDisable(true);
				teamCompositionChoiceBox.setDisable(true);

			}
		});

		//place the team composition on the screen
		VBox coopTeamsBox = new VBox(10);
		coopTeamsBox.setAlignment(Pos.CENTER_LEFT);
		coopTeamsBox.getChildren().addAll(coopTeam1TextField, coopTeam2TextField, teamCompositionChoiceBox);

		HBox coopBox = new HBox(10);
		coopBox.setAlignment(Pos.CENTER_LEFT);
		coopBox.getChildren().addAll(coopCheckBox, coopTeamsBox);

		//place the choice of the number of players
		Label nbPlayersText1 = new Label("Number of players : ");
		Label nbPlayersText2 = new Label("" + gameLogic.nbPlayers);

		Label nbAIText1 = new Label("Number of AI players : ");
		Label nbAIText2 = new Label("" + gameLogic.nbAI);

		//buttons to choose
		
		Button plusButton = new Button("+");
		plusButton.setStyle("-fx-font: normal 20px 'Script MT Bold'");
		plusButton.setPrefHeight(45);
		plusButton.setPrefWidth(45);
		plusButton.setCursor(Cursor.HAND);
		plusButton.setOnAction(e -> {
			if (gameLogic.nbPlayers < 4) {
				gameLogic.nbPlayers++;
				nbPlayersText2.setText("" + gameLogic.nbPlayers);
				if (gameLogic.nbAI != gameLogic.nbPlayers) {
					fastModeCheckBox.setSelected(false);
					fastModeCheckBox.setDisable(true);
				}
				if (gameLogic.nbPlayers == 3) {
					if (greatDuelCheckBox.isSelected()) {
						greatDuelCheckBox.setSelected(false);
					}
					greatDuelCheckBox.setDisable(true);
					apocalypseCheckBox.setDisable(false);
				} else if (gameLogic.nbPlayers == 4) {
					if (apocalypseCheckBox.isSelected()) {
						apocalypseCheckBox.setSelected(false);
					}
					apocalypseCheckBox.setDisable(true);
					coopCheckBox.setDisable(false);
				}
			}
		});

		Button minusButton = new Button("-");
		minusButton.setStyle("-fx-font: normal 20px 'Script MT Bold'");
		minusButton.setPrefHeight(45);
		minusButton.setPrefWidth(45);
		minusButton.setCursor(Cursor.HAND);
		minusButton.setOnAction(e -> {
			if (gameLogic.nbPlayers > 2) {
				gameLogic.nbPlayers--;
				nbPlayersText2.setText("" + gameLogic.nbPlayers);
				if (gameLogic.nbAI > gameLogic.nbPlayers) {
					gameLogic.nbAI--;
					nbAIText2.setText("" + gameLogic.nbAI);
				} else if (gameLogic.nbAI == gameLogic.nbPlayers) {
					fastModeCheckBox.setDisable(false);
				}
				if (gameLogic.nbPlayers == 3) {
					if (coopCheckBox.isSelected()) {
						coopCheckBox.setSelected(false);
					}
					coopCheckBox.setDisable(true);
					coopTeam1TextField.setDisable(true);
					coopTeam2TextField.setDisable(true);
					teamCompositionChoiceBox.setDisable(true);
					apocalypseCheckBox.setDisable(false);
				} else if (gameLogic.nbPlayers == 2) {
					if (apocalypseCheckBox.isSelected()) {
						apocalypseCheckBox.setSelected(false);
					}
					apocalypseCheckBox.setDisable(true);
					greatDuelCheckBox.setDisable(false);
				}
			}
		});

		Button plusAIButton = new Button("+");
		plusAIButton.setStyle("-fx-font: normal 20px 'Script MT Bold'");
		plusAIButton.setPrefHeight(45);
		plusAIButton.setPrefWidth(45);
		plusAIButton.setCursor(Cursor.HAND);
		plusAIButton.setOnAction(e -> {
			if (gameLogic.nbAI < gameLogic.nbPlayers) {
				gameLogic.nbAI++;
				nbAIText2.setText("" + gameLogic.nbAI);
				if (gameLogic.nbAI == gameLogic.nbPlayers) {
					fastModeCheckBox.setDisable(false);
				}
			}
		});

		Button minusAIButton = new Button("-");
		minusAIButton.setStyle("-fx-font: normal 20px 'Script MT Bold'");
		minusAIButton.setPrefHeight(45);
		minusAIButton.setPrefWidth(45);
		minusAIButton.setCursor(Cursor.HAND);
		minusAIButton.setOnAction(e -> {
			if (gameLogic.nbAI > 0) {
				gameLogic.nbAI--;
				nbAIText2.setText("" + gameLogic.nbAI);
				fastModeCheckBox.setSelected(false);
				fastModeCheckBox.setDisable(true);
			}
		});

		//add buttons on boxes
		VBox plusMinusButtonBox = new VBox(10);
		plusMinusButtonBox.getChildren().addAll(plusButton, minusButton);

		HBox nbPlayerBox = new HBox(10);
		nbPlayerBox.setAlignment(Pos.CENTER_LEFT);
		nbPlayerBox.relocate(50, 50);
		nbPlayerBox.getChildren().addAll(nbPlayersText1, nbPlayersText2, plusMinusButtonBox);

		VBox plusMinusAIButtonBox = new VBox(10);
		plusMinusAIButtonBox.getChildren().addAll(plusAIButton, minusAIButton);

		HBox nbAIBox = new HBox(10);
		nbAIBox.setAlignment(Pos.CENTER_LEFT);
		nbAIBox.relocate(50, 180);
		nbAIBox.getChildren().addAll(nbAIText1, nbAIText2, plusMinusAIButtonBox, fastModeCheckBox);

		HBox harmonyBox = new HBox(10);
		harmonyBox.setAlignment(Pos.CENTER_LEFT);
		harmonyBox.getChildren().addAll(harmonyCheckBox, harmonyBonusTextField);

		HBox middleEmpireBox = new HBox(10);
		middleEmpireBox.setAlignment(Pos.CENTER_LEFT);
		middleEmpireBox.getChildren().addAll(middleEmpireCheckBox, middleEmpireBonusTextField);

		VBox optionsBox = new VBox(20);
		optionsBox.setAlignment(Pos.CENTER_LEFT);
		optionsBox.relocate(50, 300);
		optionsBox.getChildren().addAll(dynastyCheckBox, harmonyBox, middleEmpireBox, greatDuelCheckBox,
				apocalypseCheckBox, coopBox);

		Pane beginGameFieldPane = new Pane(); // field to initialize the game scene

		//button to launch game with the selected options
		Button playButton2 = new Button("Play");
		playButton2.relocate(windowWidth * 0.66, windowHeight * 0.5);

		playButton2.setOnAction(e -> {

			System.out.println("Game launched !");

			if (dynastyCheckBox.isSelected()) {
				gameLogic.dynasty = true;
				System.out.println("Dynasty selected");
			} else {
				gameLogic.dynasty = false;
				System.out.println("Dynasty not selected");
			}

			if (harmonyCheckBox.isSelected()) {
				gameLogic.harmony = true;
				gameLogic.harmonyBonus = Integer.parseInt(harmonyBonusTextField.getText());
				System.out.println("Harmony selected (" + gameLogic.harmonyBonus + " bonus points)");
			} else {
				gameLogic.harmony = false;
				System.out.println("Harmony not selected");
			}

			if (middleEmpireCheckBox.isSelected()) {
				gameLogic.middleEmpire = true;
				gameLogic.middleEmpireBonus = Integer.parseInt(middleEmpireBonusTextField.getText());
				System.out.println("Middle Empire selected (" + gameLogic.middleEmpireBonus + " bonus points)");
			} else {
				gameLogic.middleEmpire = false;
				System.out.println("Middle Empire not selected");
			}

			if (greatDuelCheckBox.isSelected()) {
				gameLogic.greatDuel = true;
				gameLogic.gameFieldSize = 13;
				squareSize = Math.round(windowWidth / 35);
				System.out.println("Great Duel selected");

			} else {
				gameLogic.greatDuel = false;
				squareSize = Math.round(windowWidth / 24);
			}
			if (apocalypseCheckBox.isSelected()) {
				gameLogic.apocalypse = true;
				System.out.println("Apocalypse selected");
			} else {
				gameLogic.apocalypse = false;
			}
			if (coopCheckBox.isSelected()) {
				gameLogic.coop = true;
				gameLogic.team1Name = coopTeam1TextField.getText();
				gameLogic.team2Name = coopTeam2TextField.getText();
				gameLogic.teamComposition = teamCompositionChoiceBox.getValue();
				System.out.println("Cooperation selected: " + gameLogic.teamComposition + " (Teams " + gameLogic.team1Name + " and "
						+ gameLogic.team2Name + ")");
			} else {
				gameLogic.coop = false;
			}

			System.out.println("gameFieldSize = " + gameLogic.gameFieldSize);
			System.out.println("squareSize = " + squareSize);
			System.out.println("topLeftCorner = (" + topLeftCornerX + ", " + topLeftCornerY + ")");

			
			gameLogic.gameInitialisation(gameFieldBox, thisTurnPane, rotateButton, impossibleButton,
					squareSize, topLeftCornerX, topLeftCornerY, scores, winners, endScreenBox); //initialisation of the necessary elements to play Domi'Nations
			
			gameLogic.beginningState(nextTurnPane, secondNextTurnPane, num, secondNum);
			//creation of the random list of dominos, the first turn list, the random deque of players
			
			if ((gameLogic.nbKings == 3) && (gameLogic.apocalypse == false)) {		//keeps this fourth button disabled during the whole game if there are only 3 players
				secondNum[3].setDisable(true);
				isSndNumDisabled[3] = true;
			}

			gameLoop = new AnimationTimer() {			//game loop which refreshes the window (AnimationTimer: 60 FPS, non-modifiable)
				public void handle(long now) {
					
					if (gameLogic.isFirstTurn) {				//first turn is different from the next ones

						if (gameLogic.playersDeque.size() > 0) {					//print the name of the next player to play
							Player nextPlayer = gameLogic.playersDeque.peek();
							gameStateText.setText(nextPlayer.getKing() + " King, choose a domino");
							nextPlayer.setGameFieldForWindow(gameFieldBox, windowWidth, windowHeight);
							if (nextPlayer.isHuman == false) {
								gameLogic.playFirstTurn(gameFieldBox, windowWidth, windowHeight, secondNum);
							}
						}

						secondNum[0].setOnAction(e -> {
							Player player = gameLogic.playersDeque.poll();
							player.setGameFieldForWindow(gameFieldBox, windowWidth, windowHeight);	//set the game field of the player who plays
							secondNum[0].setStyle("-fx-background-color: " + player.getKing());		//set the player color
							secondNum[0].setDisable(true);												//make it disabled to prevent the others from selecting it
							isSndNumDisabled[0] = true;												//boolean to be sure it is disabled
							int order = gameLogic.temp.get(0);												//to remove the domino's number from listNum
							int tberased = gameLogic.listNum.indexOf(order);
							gameLogic.listNum.remove(tberased); 								//(.remove takes for argument the index)
							gameLogic.playerTab[0] = player;
						});
						
						secondNum[1].setOnAction(e -> {
							Player player = gameLogic.playersDeque.poll();
							player.setGameFieldForWindow(gameFieldBox, windowWidth, windowHeight);	//set the game field of the player who plays
							secondNum[1].setStyle("-fx-background-color: " + player.getKing());		//set the player color
							secondNum[1].setDisable(true);												//make it disabled to prevent the others from selecting it
							isSndNumDisabled[1] = true;												//boolean to be sure it is disabled
							int order = gameLogic.temp.get(1);												//to remove the domino's number from listNum
							int tberased = gameLogic.listNum.indexOf(order);
							gameLogic.listNum.remove(tberased); 								//(.remove takes for argument the index)
							gameLogic.playerTab[1] = player;
						});
						
						secondNum[2].setOnAction(e -> {
							Player player = gameLogic.playersDeque.poll();
							player.setGameFieldForWindow(gameFieldBox, windowWidth, windowHeight);	//set the game field of the player who plays
							secondNum[2].setStyle("-fx-background-color: " + player.getKing());		//set the player color
							secondNum[2].setDisable(true);												//make it disabled to prevent the others from selecting it
							isSndNumDisabled[2] = true;												//boolean to be sure it is disabled
							int order = gameLogic.temp.get(2);												//to remove the domino's number from listNum
							int tberased = gameLogic.listNum.indexOf(order);
							gameLogic.listNum.remove(tberased); 								//(.remove takes for argument the index)
							gameLogic.playerTab[2] = player;
						});
						
						secondNum[3].setOnAction(e -> {
							Player player = gameLogic.playersDeque.poll();
							player.setGameFieldForWindow(gameFieldBox, windowWidth, windowHeight);	//set the game field of the player who plays
							secondNum[3].setStyle("-fx-background-color: " + player.getKing());		//set the player color
							secondNum[3].setDisable(true);												//make it disabled to prevent the others from selecting it
							isSndNumDisabled[3] = true;												//boolean to be sure it is disabled
							int order = gameLogic.temp.get(3);												//to remove the domino's number from listNum
							int tberased = gameLogic.listNum.indexOf(order);
							gameLogic.listNum.remove(tberased); 								//(.remove takes for argument the index)
							gameLogic.playerTab[3] = player;
						});
						
						if (gameLogic.playersDeque.size() == 0) {			//end of the first turn
							gameLogic.isFirstTurn = false;
							gameLogic.countTurn ++;
							gameLogic.nextTurnList(gameLogic.nbKings);				//change the list of dominos for this turn
							gameLogic.dispDominos(nextTurnPane, secondNextTurnPane, num, secondNum);						//print dominos on screen
							for (int i = 0; i < gameLogic.playerTab.length; i++) {		//change the deque of players. Depends of the players' choice
								if (gameLogic.playerTab[i] != null) {
									gameLogic.playersDeque.add(gameLogic.playerTab[i]);
									secondNum[i].setDisable(false);
									isSndNumDisabled[i] = false;
									secondNum[i].setStyle("-fx-background-color: #FFFFFF");
								}
								gameLogic.playerTab[i] = null;		//all elements become null. This is to avoid duplication of a player in apocalypse mode
							}
						}
					} else {
						if ((gameLogic.playersDeque.size() > 0) && (gameLogic.isPlaying == false) && (countAITurn == 0)) {
							//set game field and action for the next player
							Player nextPlayer = gameLogic.playersDeque.peek();
							gameStateText.setText(nextPlayer.getKing() + " King, choose a domino");
							nextPlayer.setGameFieldForWindow(gameFieldBox, windowWidth, windowHeight);
							
							/*turn for AI. isPlaying boolean allows players to play.
							 *AI plays before a player can put the domino on the game field without it.
							 */
							if ((nextPlayer.isHuman == false)) {
								playNextTurn();
							}
						} else if ((gameLogic.playersDeque.size() >= 0) && (countAITurn != 0)) {
							playNextTurn();
						}
						
						secondNum[0].setOnAction(e -> {
							gameLogic.isPlaying = true;
							Player player = gameLogic.playersDeque.poll();
							gameStateText.setText(player.getKing() + " King, put your domino on your field");
							player.setGameFieldForWindow(gameFieldBox, windowWidth, windowHeight);	//set the game field of the player who plays
							secondNum[0].setStyle("-fx-background-color: " + player.getKing());		//set the player color
							secondNum[0].setDisable(true);												//make it disabled to prevent the others from selecting it
							isSndNumDisabled[0] = true;												//boolean to be sure it is disabled
							int order = gameLogic.temp.get(0);												//to remove the domino's number from listNum
							int tberased = gameLogic.listNum.indexOf(order);
							gameLogic.listNum.remove(tberased); 								//(.remove takes for argument the index)
							gameLogic.playerTab[0] = player;
							gameLogic.d = gameLogic.lastTurnDeque.poll();
							playDomino(player, gameLogic.d);					//put the domino on the game field
						});
						
						secondNum[1].setOnAction(e -> {
							gameLogic.isPlaying = true;
							Player player = gameLogic.playersDeque.poll();
							gameStateText.setText(player.getKing() + " King, put your domino on your field");
							player.setGameFieldForWindow(gameFieldBox, windowWidth, windowHeight);	//set the game field of the player who plays
							secondNum[1].setStyle("-fx-background-color: " + player.getKing());		//set the player color
							secondNum[1].setDisable(true);												//make it disabled to prevent the others from selecting it
							isSndNumDisabled[1] = true;												//boolean to be sure it is disabled
							int order = gameLogic.temp.get(1);												//to remove the domino's number from listNum
							int tberased = gameLogic.listNum.indexOf(order);
							gameLogic.listNum.remove(tberased); 								//(.remove takes for argument the index)
							gameLogic.playerTab[1] = player;
							gameLogic.d = gameLogic.lastTurnDeque.poll();
							playDomino(player, gameLogic.d);						//put the domino on the game field
						});
						
						secondNum[2].setOnAction(e -> {
							gameLogic.isPlaying = true;
							Player player = gameLogic.playersDeque.poll();
							gameStateText.setText(player.getKing() + " King, put your domino on your field");
							player.setGameFieldForWindow(gameFieldBox, windowWidth, windowHeight);	//set the game field of the player who plays
							secondNum[2].setStyle("-fx-background-color: " + player.getKing());		//set the player color
							secondNum[2].setDisable(true);												//make it disabled to prevent the others from selecting it
							isSndNumDisabled[2] = true;												//boolean to be sure it is disabled
							int order = gameLogic.temp.get(2);												//to remove the domino's number from listNum
							int tberased = gameLogic.listNum.indexOf(order);
							gameLogic.listNum.remove(tberased); 								//(.remove takes for argument the index)
							gameLogic.playerTab[2] = player;
							gameLogic.d = gameLogic.lastTurnDeque.poll();
							playDomino(player, gameLogic.d);					//put the domino on the game field		
						});
						
						secondNum[3].setOnAction(e -> {
							gameLogic.isPlaying = true;
							Player player = gameLogic.playersDeque.poll();
							gameStateText.setText(player.getKing() + " King, put your domino on your field");
							player.setGameFieldForWindow(gameFieldBox, windowWidth, windowHeight);	//set the game field of the player who plays
							secondNum[3].setStyle("-fx-background-color: " + player.getKing());		//set the player color
							secondNum[3].setDisable(true);												//make it disabled to prevent the others from selecting it
							isSndNumDisabled[3] = true;												//boolean to be sure it is disabled
							int order = gameLogic.temp.get(3);												//to remove the domino's number from listNum
							int tberased = gameLogic.listNum.indexOf(order);
							gameLogic.listNum.remove(tberased); 								//(.remove takes for argument the index)
							gameLogic.playerTab[3] = player;
							gameLogic.d = gameLogic.lastTurnDeque.poll();
							playDomino(player, gameLogic.d);				//put the domino on the game field
						});
					}
				}
			};
			gameLoop.start();					//begin the game loop

			window.setScene(gameScene);			//set game scene on screen

		});

		//button to go to the game mode explanation scene
		Button iButton = new Button("i");
		iButton.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22), linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%), linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));"
				+ "-fx-background-radius: 30;"
				+ "-fx-background-insets: 0,1,2,3,0;"
				+ "-fx-text-fill: #654b00;"
				+ "-fx-font-weight: bold;"
				+ "-fx-font-size: 35px;"
				+ "-fx-padding: 10 20 10 20;");
		iButton.setCursor(Cursor.HAND);
	  	iButton.relocate(windowWidth - 100, 100);
		iButton.setOnAction(e -> window.setScene(explanationScene));
		
		//button to return to the main menu
		Button backButton1 = new Button("Back");
		backButton1.relocate(100, windowHeight - 100);
		backButton1.setOnAction(e -> window.setScene(mainMenuScene));

		//place the menu on screen
		Pane gameModeRoot = new Pane();
		gameModeRoot.setStyle(style);
		gameModeRoot.getChildren().addAll(backgroundView4, nbPlayerBox, nbAIBox, optionsBox, playButton2, iButton, backButton1);
		
		//creation of the game mode scene
		gameModeScene = new Scene(gameModeRoot, windowWidth, windowHeight);

		/*
		 * ======================================================== Explanation layout
		 */

		ImageView backgroundView6 = new ImageView(background2);
		backgroundView6.setOpacity(.5);
		backgroundView6.setFitWidth(windowWidth);
		backgroundView6.setFitHeight(windowHeight);
		
		backButton2.setOnAction(e -> window.setScene(gameModeScene));
		backButton2.relocate(100, windowHeight - 100);
		
		explanationBox.getChildren().addAll(fastModeExpl, dynastyExpl, harmonyExpl, middleEmpireExpl, greatDuelExpl, apocalypseExpl, cooperationExpl);
		explanationBox.setAlignment(Pos.CENTER);
		explanationBox.relocate(windowWidth / 2 - (windowWidth / 2.3), windowHeight / 2 - (windowHeight / 3.9));
		
		explanationRoot.setStyle(style);
		explanationRoot.getChildren().addAll(backgroundView6, explanationBox, backButton2);
		
		explanationScene = new Scene(explanationRoot, windowWidth, windowHeight);
		
		/*
		 * ======================================================= Player Game layout
		 */

		//background
		ImageView backgroundView5 = new ImageView(background);
		backgroundView5.setOpacity(.4);
		backgroundView5.setFitWidth(windowWidth);
		backgroundView5.setFitHeight(windowHeight);
		backgroundView5.setX(0);
		backgroundView5.setY(0);

		// gameFieldPane defined in the play button event of the previous scene

		//creation of the two stack of dominos -> those played this turn and those played next turn and chosen by the players
		
		//dominos' number
		num[0].setText("" + 0);
		num[1].setText("" + 0);
		num[2].setText("" + 0);
		num[3].setText("" + 0);

		num[0].setStyle("-fx-font: normal 35px 'Script MT Bold'");
		num[1].setStyle("-fx-font: normal 35px 'Script MT Bold'");
		num[2].setStyle("-fx-font: normal 35px 'Script MT Bold'");
		num[3].setStyle("-fx-font: normal 35px 'Script MT Bold'");

		num[0].setLayoutX(60);
		num[1].setLayoutX(60);
		num[2].setLayoutX(60);
		num[3].setLayoutX(60);

		num[0].setLayoutY(70);
		num[1].setLayoutY(170);
		num[2].setLayoutY(270);
		num[3].setLayoutY(370);
		
		num[0].setDisable(true);
		num[1].setDisable(true);
		num[2].setDisable(true);
		num[3].setDisable(true);

		//place for dominos
		Rectangle rect1 = new Rectangle(130, 2, 200, 100);
		Rectangle rect2 = new Rectangle(130, 102, 200, 100);
		Rectangle rect3 = new Rectangle(130, 202, 200, 100);
		Rectangle rect4 = new Rectangle(130, 302, 200, 100);

		rect1.setFill(null);
		rect2.setFill(null);
		rect3.setFill(null);
		rect4.setFill(null);

		rect1.setStroke(Color.BLACK);
		rect2.setStroke(Color.BLACK);
		rect3.setStroke(Color.BLACK);
		rect4.setStroke(Color.BLACK);

		rect1.setStrokeWidth(4);
		rect2.setStrokeWidth(4);
		rect3.setStrokeWidth(4);
		rect4.setStrokeWidth(4);
		
		rect1.setStrokeLineJoin(StrokeLineJoin.ROUND);
		rect2.setStrokeLineJoin(StrokeLineJoin.ROUND);
		rect3.setStrokeLineJoin(StrokeLineJoin.ROUND);
		rect4.setStrokeLineJoin(StrokeLineJoin.ROUND);
		
		Line line1 = new Line(340, 202, 420, 202);
		Line line2 = new Line(340, 202, 370, 232);
		Line line3 = new Line(340, 202, 370, 172);
		
		line1.setStroke(Color.BLACK);
		line2.setStroke(Color.BLACK);
		line3.setStroke(Color.BLACK);

		line1.setStrokeWidth(8);
		line2.setStrokeWidth(8);
		line3.setStrokeWidth(8);
		
		line1.setStrokeLineCap(StrokeLineCap.ROUND);
		line2.setStrokeLineCap(StrokeLineCap.ROUND);
		line3.setStrokeLineCap(StrokeLineCap.ROUND);
		
		//pane to put dominos played this turn
		nextTurnPane.setPrefSize(Math.round(windowWidth / 4), Math.round(windowHeight / 2) - 25);
		nextTurnPane.getChildren().addAll(num[0], num[1], num[2], num[3], rect1, rect2, rect3, rect4, line1, line2, line3);

		//number of the dominos chosen this turn
		secondNum[0].setText("" + 0);
		secondNum[1].setText("" + 0);
		secondNum[2].setText("" + 0);
		secondNum[3].setText("" + 0);

		secondNum[0].setStyle("-fx-font: normal 35px 'Script MT Bold'");
		secondNum[1].setStyle("-fx-font: normal 35px 'Script MT Bold'");
		secondNum[2].setStyle("-fx-font: normal 35px 'Script MT Bold'");
		secondNum[3].setStyle("-fx-font: normal 35px 'Script MT Bold'");

		secondNum[0].setLayoutX(30);
		secondNum[1].setLayoutX(30);
		secondNum[2].setLayoutX(30);
		secondNum[3].setLayoutX(30);

		secondNum[0].setLayoutY(20);
		secondNum[1].setLayoutY(120);
		secondNum[2].setLayoutY(220);
		secondNum[3].setLayoutY(320);

		//place for dominos
		Rectangle secondRect1 = new Rectangle(130, 2, 200, 100);
		Rectangle secondRect2 = new Rectangle(130, 102, 200, 100);
		Rectangle secondRect3 = new Rectangle(130, 202, 200, 100);
		Rectangle secondRect4 = new Rectangle(130, 302, 200, 100);

		secondRect1.setFill(null);
		secondRect2.setFill(null);
		secondRect3.setFill(null);
		secondRect4.setFill(null);

		secondRect1.setStroke(Color.BLACK);
		secondRect2.setStroke(Color.BLACK);
		secondRect3.setStroke(Color.BLACK);
		secondRect4.setStroke(Color.BLACK);

		secondRect1.setStrokeWidth(3);
		secondRect2.setStrokeWidth(3);
		secondRect3.setStrokeWidth(3);
		secondRect4.setStrokeWidth(3);
		
		secondRect1.setStrokeLineJoin(StrokeLineJoin.ROUND);
		secondRect2.setStrokeLineJoin(StrokeLineJoin.ROUND);
		secondRect3.setStrokeLineJoin(StrokeLineJoin.ROUND);
		secondRect4.setStrokeLineJoin(StrokeLineJoin.ROUND);

		//pane to stock dominos
		secondNextTurnPane.setPrefSize(Math.round(windowWidth / 4), Math.round(windowHeight / 2) - 25);
		secondNextTurnPane.getChildren().addAll(secondNum[0], secondNum[1], secondNum[2], secondNum[3], secondRect1, secondRect2, secondRect3,
				secondRect4);

		//deck with the two stack
		nextTurnsDeckBox.setPrefSize(Math.round(windowWidth / 2), Math.round(windowHeight / 2) - 25);
		nextTurnsDeckBox.getChildren().addAll(nextTurnPane, secondNextTurnPane);

		//pane with the visualisation of the chosen domino
		thisTurnPane.setPrefSize(Math.round(windowWidth / 2), Math.round(windowHeight / 2) - 25);
		rotateButton.setLayoutX(0);
		rotateButton.setLayoutY(0);
		rotateButton.setDisable(true);
		impossibleButton.setLayoutX(Math.round(windowWidth / 4));
		impossibleButton.setLayoutY(0);
		impossibleButton.setDisable(true);
		thisTurnPane.getChildren().addAll(rotateButton, impossibleButton);

		//box to choose domino and the orientation (and verify if it is possible to play it)
		dominoBox.getChildren().addAll(nextTurnsDeckBox, thisTurnPane);

		//text with player instruction
		gameStateText.setStyle("-fx-font: bold 50px 'Script MT Bold'");
		exitGame.setOnAction(e -> {
			e.consume();
			closeAGame();
		});
		//box at the top of the screen
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().addAll(exitGame, gameStateText, continueTurn);
		
		//box with the game field
		gameFieldBox.setAlignment(Pos.CENTER);
		gameFieldBox.setPrefSize(Math.round(windowWidth / 2), windowHeight - 50);
		gameFieldBox.getChildren().addAll(kingText, pointsText, beginGameFieldPane);
		
		//BorderPane with all elements for a game
		continueTurn.setDisable(true);
		gameBorderPane.setTop(topBox);
		gameBorderPane.setLeft(gameFieldBox);
		gameBorderPane.setRight(dominoBox);

		//set background and the previous BorderPane
		gameRoot.setStyle(style);
		gameRoot.getChildren().addAll(backgroundView5, gameBorderPane);

		//creation of the scene
		gameScene = new Scene(gameRoot, windowWidth, windowHeight);
		
		/*
		 * ======================================================== End screen layout
		 */

		ImageView backgroundView10 = new ImageView(background2);
		backgroundView10.setOpacity(.5);
		backgroundView10.setFitWidth(windowWidth);
		backgroundView10.setFitHeight(windowHeight);
		backgroundView10.setX(0);
		backgroundView10.setY(0);
		
		backButton3.setOnAction(e -> window.setScene(mainMenuScene));
		backButton3.relocate(100, windowHeight - 100);

		endScreenBox.setAlignment(Pos.CENTER);
		endScreenBox.getChildren().addAll(text1, text3, backButton3);
		
		endScreenRoot.setStyle(style);
		endScreenRoot.getChildren().addAll(backgroundView10, endScreenBox);
		
		endScreenScene = new Scene(endScreenRoot, windowWidth, windowHeight);

		/*
		 * =======================================================
		 */

		//begin with the appearance of title screen
		window.setScene(titleScreenScene);
		//shows the window
		window.show();
	}
	
	public void closeGame() {
		/*
		 * function to close the window and end the program
		 */
		Boolean answer = ConfirmationWindow.display("Quitting DomiNations", "Exiting game...");
		if (answer) {
			window.close();
		}
	}
	
	public void closeAGame() {
		/*
		 * function to close the window and end the program
		 */
		Boolean answer = ConfirmationWindow.display("Quitting game", "Finish this game ?");
		if (answer) {
			gameLoop.stop();
			window.setScene(gameModeScene);
		}
	}

	public void playNextTurn() {
		/*
		 * lets the AI play during a turn which is not the first one
		 */
		if (countAITurn == 0) {
			
			gameLogic.isPlaying = true;
			Player player = gameLogic.playersDeque.poll();
			String king = player.getKing();

			AI ai = (AI) player;
			int numDom = ai.researchDomino(gameLogic.dominoDeque, gameLogic.playerList, gameLogic.listNum, 
					gameLogic.countTurn, gameLogic.middleEmpire, gameLogic.middleEmpireBonus, gameLogic.coop);

			int order = gameLogic.temp.indexOf(numDom);
			secondNum[order].setStyle("-fx-background-color: " + king);
			secondNum[order].setDisable(true);
			isSndNumDisabled[order] = true;
			int tberased = gameLogic.listNum.indexOf(numDom);
			gameLogic.listNum.remove(tberased); 		// (.remove takes for argument the index)
			gameLogic.playerTab[order] = player; 		// this player will play in [order]st position next turn

			gameLogic.lastTurnDeque.poll();
			ai.playDominoAI(gameLogic.greatDuel, gameLogic.gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize); //AI plays
			countAITurn ++;
			
		} else if ((countAITurn == 60) || (fastModeCheckBox.isSelected())) {
			gameLogic.isPlaying = false;
			if (gameLogic.playersDeque.size() == 0) {
				gameLogic.countTurn ++;
				countAITurn = 0;
				if (gameLogic.dominoList.size() == 0) {		//if it is the last, clears dominoDeque, counts points for each players
					gameLogic.isLastTurn = true;
					gameLogic.nextTurnList(gameLogic.nbKings);
					gameLogic.endGame();
					if ((gameLogic.dynasty) && (gameLogic.countDynasty < 2)) {	//if it is in dynasty mode, continues while countDynasty < 3
						gameLogic.countDynasty ++;
						gameLogic.dynastyReset(gameFieldBox, gameLogic.gameFieldSize, squareSize, topLeftCornerX, topLeftCornerY);
						gameLogic.beginningState(nextTurnPane, secondNextTurnPane, num, secondNum);
					} else {			//finds the winner(s), go to the winner scene stop the game loop
						gameLogic.winner(scores, winners);
						completeWinnerScene();
						window.setScene(endScreenScene);
						gameLoop.stop();
					}
				} else {						//else prepare for the next turn
					gameLogic.nextTurnList(gameLogic.nbKings);
					gameLogic.dispDominos(nextTurnPane, secondNextTurnPane, num, secondNum);
					for (int i = 0; i < gameLogic.playerTab.length; i++) {
						if (gameLogic.playerTab[i] != null) {
							gameLogic.playersDeque.add(gameLogic.playerTab[i]);
							secondNum[i].setDisable(false);
							isSndNumDisabled[i] = false;
							secondNum[i].setStyle("-fx-background-color: #FFFFFF");
						}
						gameLogic.playerTab[i] = null;
					}
				}
			} else {
				countAITurn = 0;
			}
		} else {
			countAITurn ++;
		}
	}


	public void playDomino(Player player, Domino d) {
		/*
		 * Allows the player to place a domino.
		 */
		
		thisTurnPane.getChildren().clear();												//erase possible message if the last player use impossible button
		orientations = new ArrayDeque<Character>(Arrays.asList('D', 'L', 'U', 'R'));	//begin with the same orientation for all the players (R)
		currentOrient = 'R';
		
		ImageView tileView1 = gameLogic.tileView(d.getSq1().getNumber(), 100);				//create view of each tile of the domino
		ImageView tileView2 = gameLogic.tileView(d.getSq2().getNumber(), 100);
		
		Rectangle referenceTile = new Rectangle(300, 200, 100, 100);
		referenceTile.setStroke(Color.GREENYELLOW);
		referenceTile.setFill(null);
		referenceTile.setStrokeLineCap(StrokeLineCap.ROUND);
		referenceTile.setStrokeType(StrokeType.OUTSIDE);
		referenceTile.setStrokeWidth(8);
		
		//set positions
		
		tileView1.setX(300);
		tileView1.setY(200);
		
		tileView2.setX(400);
		tileView2.setY(200);
		
		thisTurnPane.getChildren().addAll(rotateButton, impossibleButton, tileView1, tileView2, referenceTile);	//buttons and domino on screen
		
		rotateButton.setDisable(false);				//buttons can be used
		impossibleButton.setDisable(false);
		
		final int count;						//number of buttons to choose dominos
		if (gameLogic.apocalypse) {
			count = gameLogic.nbKings + 1;
		} else {
			count = gameLogic.nbKings;
		}
		for (int i = 0; i < count; i++) {		//disable all to avoid the players to choose another domino before they play theirs
			secondNum[i].setDisable(true);
		}
		Tile[][] gameField = player.getGameField();
		
		rotateButton.setOnAction(e2 -> {
			currentOrient = orientations.poll();
			orientations.add(currentOrient);
			switch (currentOrient) {
			case 'R':
				thisTurnPane.getChildren().clear();
				tileView1.setX(300);
				tileView1.setY(200);
				
				tileView2.setX(400);
				tileView2.setY(200);
				
				referenceTile.setX(300);
				referenceTile.setY(200);
				
				thisTurnPane.getChildren().addAll(tileView1, tileView2, referenceTile, rotateButton, impossibleButton);
				break;
			case 'L':
				thisTurnPane.getChildren().clear();
				tileView1.setX(300);
				tileView1.setY(200);
				
				tileView2.setX(200);
				tileView2.setY(200);
				
				referenceTile.setX(300);
				referenceTile.setY(200);
				
				thisTurnPane.getChildren().addAll(tileView1, tileView2, referenceTile, rotateButton, impossibleButton);
				break;
			case 'D':
				thisTurnPane.getChildren().clear();
				tileView1.setX(300);
				tileView1.setY(200);
				
				tileView2.setX(300);
				tileView2.setY(300);
				
				referenceTile.setX(300);
				referenceTile.setY(200);
				
				thisTurnPane.getChildren().addAll(tileView1, tileView2, referenceTile, rotateButton, impossibleButton);
				break;
			case 'U':
				thisTurnPane.getChildren().clear();
				tileView1.setX(300);
				tileView1.setY(200);
				
				tileView2.setX(300);
				tileView2.setY(100);
				
				referenceTile.setX(300);
				referenceTile.setY(200);
				
				thisTurnPane.getChildren().addAll(tileView1, tileView2, referenceTile, rotateButton, impossibleButton);
				break;
			}
		});

		impossibleButton.setOnAction(e2 -> {
			if (d.isImpossibleToPlay(player)) {
				Text impossibleText = new Text("This domino cannot be played, domino discarded");
				impossibleText.setY(windowHeight / 8);
				player.getGameFieldPane().setOnMouseClicked(new EventHandler<MouseEvent>() {		//remove the possibility to put another domino on field
					public void handle(MouseEvent mouseEvent) {}									//this makes nothing when clicked.
					});
				thisTurnPane.getChildren().clear();
				impossibleButton.setDisable(true);
				rotateButton.setDisable(true);
				thisTurnPane.getChildren().addAll(rotateButton, impossibleButton, impossibleText);
				continueTurn.setDisable(false);
				
				continueTurn.setOnAction(e -> {
					gameLogic.isPlaying = false;
					continueTurn.setDisable(true);
					for (int i = 0; i < count; i++) {
						if (isSndNumDisabled[i] == false) {
							secondNum[i].setDisable(false);
						}
					}
					if (gameLogic.playersDeque.size() == 0) {
						gameLogic.countTurn ++;
						if (gameLogic.dominoList.size() == 0) {		//if it is the last, clears dominoDeque, counts points for each players
							gameLogic.isLastTurn = true;
							gameLogic.nextTurnList(gameLogic.nbKings);
							gameLogic.endGame();
							if ((gameLogic.dynasty) && (gameLogic.countDynasty < 3)) {	//if it is in dynasty mode, continues while countDynasty < 3
								gameLogic.countDynasty ++;
								gameLogic.dynastyReset(gameFieldBox, gameLogic.gameFieldSize, squareSize, topLeftCornerX, topLeftCornerY);
								gameLogic.beginningState(nextTurnPane, secondNextTurnPane, num, secondNum);
							} else {			//finds the winner(s), go to the winner scene stop the game loop
								gameLogic.winner(scores, winners);
								completeWinnerScene();
								window.setScene(endScreenScene);
								gameLoop.stop();
							}
						} else {						//else prepare for the next turn
							gameLogic.nextTurnList(gameLogic.nbKings);
							gameLogic.dispDominos(nextTurnPane, secondNextTurnPane, num, secondNum);
							for (int i = 0; i < gameLogic.playerTab.length; i++) {
								if (gameLogic.playerTab[i] != null) {
									gameLogic.playersDeque.add(gameLogic.playerTab[i]);
									secondNum[i].setDisable(false);
									isSndNumDisabled[i] = false;
									secondNum[i].setStyle("-fx-background-color: #FFFFFF");
								}
								gameLogic.playerTab[i] = null;
							}
						}
					}
				});
			} else {
				Text possibleText = new Text("The domino is playable");
				possibleText.setY(windowHeight / 8);
				thisTurnPane.getChildren().add(possibleText);
			}
		});

		player.getGameFieldPane().setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				// coordinates within the grid
				int xClick = (int) Math.round(mouseEvent.getX() - topLeftCornerX);
				int yClick = (int) Math.round(mouseEvent.getY() - topLeftCornerY);
				if ((0 < xClick) && (xClick < squareSize * gameLogic.gameFieldSize) && (0 < yClick)
						&& (yClick < squareSize * gameLogic.gameFieldSize)) {
					System.out.print("(" + xClick + ", " + yClick + ")");
					xLoop: for (int j = 0; j <= gameLogic.gameFieldSize; j++) {
						if ((squareSize * j < xClick) && (xClick <= squareSize * (j + 1))) {
							for (int k = 0; k <= gameLogic.gameFieldSize; k++) {
								if ((squareSize * k < yClick) && (yClick <= squareSize * (k + 1))) {
									x1 = k;
									y1 = j;
									break xLoop;
								}
							}
						}
					}
					switch (currentOrient) {
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
					
					if (d.isPlayable(player, currentOrient, x1, y1, x2, y2, false) == true) {
						gameField[x1][y1] = d.getSq1();
						player.putTile(gameLogic.gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, x1, y1,
								d.getSq1().getNumber());
						gameField[x2][y2] = d.getSq2();
						player.putTile(gameLogic.gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize, x2, y2,
								d.getSq2().getNumber());
						player.completeLine(gameLogic.gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize);
						player.completeColumn(gameLogic.gameFieldSize, topLeftCornerX, topLeftCornerY, squareSize);
						rotateButton.setDisable(true);
						impossibleButton.setDisable(true);
						mouseEvent.consume();

						player.getGameFieldPane().setOnMouseClicked(new EventHandler<MouseEvent>() {		//remove the possibility to put another domino on field
							public void handle(MouseEvent mouseEvent) {}									//this makes nothing when clicked.
							});
						thisTurnPane.getChildren().clear();
						thisTurnPane.getChildren().addAll(rotateButton, impossibleButton);
						continueTurn.setDisable(false);
						continueTurn.setOnAction(e -> {
							for (int i = 0; i < count; i++) {
								if (isSndNumDisabled[i] == false) {
									secondNum[i].setDisable(false);
								}
							}
							continueTurn.setDisable(true);
							gameLogic.isPlaying = false;
							if (gameLogic.playersDeque.size() == 0) {
								gameLogic.countTurn ++;
								if (gameLogic.dominoList.size() == 0) {		//if it is the last, clears dominoDeque, counts points for each players
									gameLogic.isLastTurn = true;
									gameLogic.nextTurnList(gameLogic.nbKings);
									gameLogic.endGame();
									if ((gameLogic.dynasty) && (gameLogic.countDynasty < 2)) {	//if it is in dynasty mode, continues while countDynasty < 3
										gameLogic.countDynasty ++;
										gameLogic.dynastyReset(gameFieldBox, gameLogic.gameFieldSize, squareSize, topLeftCornerX, topLeftCornerY);
										gameLogic.beginningState(nextTurnPane, secondNextTurnPane, num, secondNum);
									} else {			//finds the winner(s), go to the winner scene stop the game loop
										gameLogic.winner(scores, winners);
										completeWinnerScene();
										window.setScene(endScreenScene);
										gameLoop.stop();
									}
								} else {						//else prepare for the next turn
									gameLogic.nextTurnList(gameLogic.nbKings);
									gameLogic.dispDominos(nextTurnPane, secondNextTurnPane, num, secondNum);
									for (int i = 0; i < gameLogic.playerTab.length; i++) {
										if (gameLogic.playerTab[i] != null) {
											gameLogic.playersDeque.add(gameLogic.playerTab[i]);
											secondNum[i].setDisable(false);
											isSndNumDisabled[i] = false;
											secondNum[i].setStyle("-fx-background-color: #FFFFFF");
										}
										gameLogic.playerTab[i] = null;
									}
								}
							}
						});

					}
				}
			}
		});
	}
	
	public void completeWinnerScene() {
		/*
		 * function which adds scores and winner(s) of the game
		 */
		endScreenBox.getChildren().clear();
		endScreenBox.getChildren().addAll(text1, text2);
		for (Text s : scores) {
			endScreenBox.getChildren().add(s);
		}
		endScreenBox.getChildren().add(text3);
		
		for (Text w : winners) {
			endScreenBox.getChildren().add(w);
		}
		endScreenBox.getChildren().add(backButton3);
	}

}
