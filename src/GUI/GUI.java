import javafx.application.Application;
import javafx.application.Platform;

import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.paint.Color;

import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GUI extends Application{
    public final static int NETWORKWIDTH = 600; // window width
    public final static int NETWORKHEIGHT = 600;  // window height

    public final static int PLAYWIDTH = 800; // window width
    public final static int PLAYHEIGHT = 600;  // window height

    private NetworkHost hostThread;
    private NetworkClient clientThread;

    private GUI thisGUI;

    private Stage window;

    public String name;//player's unique name
    public String[] namesArray;
    private Pane root;//all GUI objects are added to this parent
    private VBox menuRoot;//vertical container for menu objects

    //Added to interact with network
    private Text screenInfo;//labels current screen
    private Text networkInfo;//contains info from client thread
    private TextField nameField;//to input the player's name
    private TextField addressField;//to input the server address

    private Button hostServer;//on main menu, moves to setup the host server screen
    private Button startServer;//on hosting server screen, starts the server
    private Button startGame;//on hosting server screen, moves everyone to game

    private Button joinServer;//on main menu, moves to joining server screen
    private Button connectToServer;//on joining screen, attempts to connect to server

    private Button endTurn;

    private Button menu;//on joining and hosting screen, goes to main menu

    //reference to text/panes to be updated by key name
    HashMap<String,Text> nameToNumCards;
    HashMap<String,Pane> nameToCardPane;

    //Manipulated when playing game
    private CardView discardView;
    private Button play;
    private CardView cardRef;

    public void init(){
    }

    public void setupNetwork() {
        menuRoot = new VBox();
        screenInfo = new Text();
        networkInfo = new Text();
        nameField = new TextField();
        addressField = new TextField();

        hostServer = new Button("Host Game");
        hostServer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    setupHostMenu();
                }
            });

        startServer = new Button("Start Server");
        startServer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    if(nameField.getText().isEmpty()) {
                        networkInfo.setText("Please enter a name");
                        return;
                    }
                    name = nameField.getText();

                    networkInfo.setText("Starting server...");
                    hostThread = new NetworkHost();
                    String address = hostThread.startHost();
                    if(address == null) {//server failed to start
                        networkInfo.setText("Failed to start server");
                        hostThread = null;
                    }
                    else {
                        String[] addInfo = address.split(":");
                        if(addInfo.length != 2) {
                            networkInfo.setText("Invalid address");
                            return;
                        }
                        int port = Integer.parseInt(addInfo[1]);
                        clientThread = new NetworkClient();
                        clientThread.GUI = thisGUI;
                        String result = clientThread.connectToServer(addInfo[0], port, name);
                        if(result.equals("ERROR")) {
                            networkInfo.setText("Error starting server");
                            return;
                        }
                        hostingMenu(address);
                    }
                }
            });

        startGame = new Button("Start Game");
        startGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    startGame();
                }
            });

        joinServer = new Button("Join Game");
        joinServer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    joiningMenu();
                }
            });

        connectToServer = new Button("Connect to Server");
        connectToServer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    if(nameField.getText().isEmpty()) {
                        networkInfo.setText("Please enter a name");
                        return;
                    }
                    name = nameField.getText();

                    String[] addInfo = addressField.getText().split(":");
                    if(addInfo.length != 2) {
                        networkInfo.setText("Invalid address");
                        return;
                    }
                    int port = Integer.parseInt(addInfo[1]);
                    clientThread = new NetworkClient();
                    clientThread.GUI = thisGUI;
                    String result = clientThread.connectToServer(addInfo[0], port, name);
                    if(result.equals("Name taken")) {
                        networkInfo.setText("That name is taken, type another name");
                        return;
                    }
                    else if(result.equals("ERROR")) {
                        networkInfo.setText("Error connecting to server");
                        return;
                    }
                    //otherwise we are connected, move to lobby
                    lobbyMenu();
                }
            });

        endTurn = new Button("End Turn");
        endTurn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    endTurn();
                }
            });

        menu = new Button("Main Menu");
        menu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    mainMenu();
                }
            });
    }

    private void mainMenu() {

        if(hostThread != null) hostThread.shutdown();
        if(clientThread != null) clientThread.shutdown();

        root.getChildren().clear();
        menuRoot.getChildren().clear();

        screenInfo.setText("Main Menu - UNO");

        menuRoot.getChildren().addAll(screenInfo, joinServer, hostServer);
        root.getChildren().add(menuRoot);

    }

    private void setupHostMenu() {
        root.getChildren().clear();
        menuRoot.getChildren().clear();

        screenInfo.setText("Host a Game");
        networkInfo.setText("Enter your name");
        nameField.setText("");
        nameField.setPromptText("Name");
        menuRoot.getChildren().addAll(screenInfo, networkInfo, nameField, startServer, menu);
        root.getChildren().add(menuRoot);
    }

    private void hostingMenu(String address) {
        root.getChildren().clear();
        menuRoot.getChildren().clear();

        screenInfo.setText("Hosting a Game - Server Address "+address);
        networkInfo.setText("");

        menuRoot.getChildren().addAll(screenInfo, networkInfo, startGame, menu);
        root.getChildren().add(menuRoot);
    }

    private void joiningMenu() {
        root.getChildren().clear();
        menuRoot.getChildren().clear();

        screenInfo.setText("Join a Game");
        networkInfo.setText("Enter the server address and your name");
        addressField.setText("");
        addressField.setPromptText("Address");
        nameField.setText("");
        nameField.setPromptText("Name");
        menuRoot.getChildren().addAll(screenInfo, networkInfo, addressField, nameField, connectToServer, menu);
        root.getChildren().add(menuRoot);
    }

    private void lobbyMenu() {
        root.getChildren().clear();
        menuRoot.getChildren().clear();

        screenInfo.setText("Lobby - waiting for game to start");
        networkInfo.setText("");
        menuRoot.getChildren().addAll(screenInfo, networkInfo, menu);
        root.getChildren().add(menuRoot);
    }

    private void startGame() {
        hostThread.state = "Playing";
    }

    private void endTurn() {
        menuRoot.getChildren().remove(endTurn);
        clientThread.respondWithTurnInfo(name+" played");
    }

    public void updatePlayerList(String names) {
        networkInfo.setText(names);
        namesArray = names.split("\n");
    }

    public void goToGame() {
    	playGame();
//        root.getChildren().clear();
//        menuRoot.getChildren().clear();
//
//        screenInfo.setText("Uno");
//        networkInfo.setText("");
//        menuRoot.getChildren().addAll(screenInfo, networkInfo);
//        root.getChildren().addAll(menuRoot);
    }

    public void takeTurn() {
        menuRoot.getChildren().add(endTurn);
    }

    public void disconnected() {
        mainMenu();
        networkInfo.setText("Disconnected by server");
        menuRoot.getChildren().add(networkInfo);
    }
    
    //works for both lobby etc. and game
    public void setMessage(String info) {
        networkInfo.setText(info);
    }

    //methods below should be called after playGame is called
    public void playGame(){
        window.setTitle("UNO Game");

        BorderPane root = new BorderPane();

        //hashmaps for easy lookup when updating cards
        nameToNumCards = new HashMap<String,Text>();
        nameToCardPane = new HashMap<String,Pane>();

        //bottom, left, up, right

        //small containers for name and number of cards otherwise main containers left/right
        Text[] nameTexts = {new Text(), new Text(), new Text(), new Text()};
        Text[] cardTexts = {new Text(), new Text(), new Text(), new Text()};
        VBox[] verticalPanes = {new VBox(), new VBox(), new VBox(), new VBox()};
        verticalPanes[1].setAlignment(Pos.TOP_CENTER);
        verticalPanes[3].setAlignment(Pos.BOTTOM_CENTER);
        //Main top and bottom containers
        HBox botHBox = new HBox();
        botHBox.setAlignment(Pos.CENTER_LEFT);
        HBox topHBox = new HBox();
        topHBox.setAlignment(Pos.CENTER_RIGHT);

        //scroll panes which hold card containers
        ScrollPane[] scrollPanes = 
            {new ScrollPane(), new ScrollPane(), new ScrollPane(), new ScrollPane()};
        for(ScrollPane p : scrollPanes){
            p.setDisable(true);
            p.setVisible(false);
        }

        //hold cards
        Pane[] cardPanes = {new HBox(), new VBox(), new HBox(), new VBox()};
        ((HBox)cardPanes[0]).setAlignment(Pos.CENTER_LEFT);
        ((VBox)cardPanes[1]).setAlignment(Pos.TOP_CENTER);
        ((HBox)cardPanes[2]).setAlignment(Pos.CENTER_RIGHT);
        ((VBox)cardPanes[3]).setAlignment(Pos.BOTTOM_CENTER);

        Arrays.sort(namesArray);
        int nameIndex = Arrays.binarySearch(namesArray,name);
        int index = 0;
        for(int i = 0; i<4; i++){
            if(namesArray.length>i){
                String currentName = namesArray[index];
                if(i==0){
                    currentName = name;
                }
                else{
                    index++;
                }
                nameTexts[i].setText(currentName);
                cardTexts[i].setText("# of Cards: ");
                scrollPanes[i].setDisable(false);
                scrollPanes[i].setVisible(true);

                nameToNumCards.put(currentName, cardTexts[i]);
                nameToCardPane.put(currentName, cardPanes[i]);
            }
            scrollPanes[i].setContent(cardPanes[i]);
            if(i%2==0){
                scrollPanes[i].setHbarPolicy(ScrollBarPolicy.ALWAYS);
                scrollPanes[i].setVbarPolicy(ScrollBarPolicy.NEVER);

                verticalPanes[i].getChildren().addAll(nameTexts[i], cardTexts[i]);
                verticalPanes[i].setAlignment(Pos.TOP_CENTER);
            }
            else{
                scrollPanes[i].setHbarPolicy(ScrollBarPolicy.NEVER);
                scrollPanes[i].setVbarPolicy(ScrollBarPolicy.ALWAYS);
            }
            if(index == nameIndex){
                index++;
            }
        }

        botHBox.getChildren().addAll(verticalPanes[0], scrollPanes[0]);
        verticalPanes[1].getChildren().addAll(nameTexts[1], cardTexts[1], scrollPanes[1]);
        topHBox.getChildren().addAll(scrollPanes[2], verticalPanes[2]);
        verticalPanes[3].getChildren().addAll(scrollPanes[3], nameTexts[3], cardTexts[3]);

        botHBox.setPrefHeight(130);
        botHBox.setSpacing(10);
        botHBox.setPadding(new Insets(0, 20, 0, 10));
        verticalPanes[1].setPrefWidth(130);
        verticalPanes[1].setSpacing(5);
        verticalPanes[1].setPadding(new Insets(0, 0, 20, 0));
        topHBox.setPrefHeight(130);
        topHBox.setSpacing(10);
        topHBox.setPadding(new Insets(0, 10, 0, 20));
        verticalPanes[3].setPrefWidth(130);
        verticalPanes[3].setSpacing(5);
        verticalPanes[3].setPadding(new Insets(20, 0, 0, 0));

        root.setBottom(botHBox);
        root.setLeft(verticalPanes[1]);
        root.setTop(topHBox);
        root.setRight(verticalPanes[3]);

        VBox center = new VBox();
        center.setAlignment(Pos.CENTER);
        networkInfo = new Text("Your message here");
        Text discardText = new Text("Discard Pile");
        discardView = new CardView("wild","wild");
        play = new Button("Play Selected Card");
        Text playText = new Text("");
        Text wildCardText = new Text("Set Wildcard color");
        ObservableList<String> names = FXCollections.observableArrayList(
                "RED","YELLOW","GREEN","BLUE");
        ComboBox<String> colorList = new ComboBox<String>(names);

        center.getChildren().addAll(
            networkInfo, discardText, discardView, play, playText,wildCardText,colorList);

        center.setSpacing(15);

        root.setCenter(center);

        play.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent value){
                    if(cardRef != null){
                        UnoCard selected = cardRef.getSelectedCard();

                        if(selected != null){
                            UnoCard other = discardView.getCard();
                            if(other != null){
                                if(selected.getColor().equals("Wild")){
                                    String color = colorList.getValue();
                                    if(color != null){
                                        String text = "Wild" + " " + color;
                                        //clientThread.writeToServer(text);
                                        disableAction();
                                        playText.setText("");
                                    }
                                    else{
                                        playText.setText("Warning: Select Wildcard Color");
                                    }
                                }
                                else{
                                    if(other.matches(selected)){
                                        String text = selected.getType() + " " + selected.getColor();
                                        //clientThread.writeToServer(text);
                                        disableAction();
                                        playText.setText("");
                                    }
                                    else{
                                        //card does not match
                                        playText.setText("Warning: Card Does Not Match");
                                    }
                                }
                            }
                            else{
                                //discarded card is null
                                playText.setText("Warning: No Discarded Card Invalid");
                            }
                        }
                        else{
                            //not selected
                            playText.setText("Warning: No Card Selected");
                        }
                    }
                    else{
                        //hand is empty
                        playText.setText("Warning: Hand Is Empty");
                    }
                }
            });

        window.setScene(new Scene(root, PLAYWIDTH, PLAYHEIGHT));
        window.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent value){
                    stop();
                }
            });

        disableAction();
        window.show();
    }

    public void disableAction(){
        play.setDisable(true);
    }

    public void enableAction(){
        play.setDisable(false);
    }

    //listing of a pairs of cards ex. 3 blue 5 green...
    public void setHand(String hand)
    {
        String[] tokens = hand.split(" ");
        HBox handPane = (HBox)nameToCardPane.get(name);
        handPane.getChildren().clear();
        //add cards which can be selected
        ArrayList<CardView> list = new ArrayList<CardView>();
        for(int i = 0; i<tokens.length; i+=2){
            CardView newCard = new CardView(tokens[i+1],tokens[i],list);
            newCard.addEventFilter(MouseEvent.MOUSE_PRESSED,newCard);
            handPane.getChildren().addAll(newCard);
            cardRef = newCard;
        }
    }

    //listing of pairs of names and numbers ex. Bob 5 Sam 7
    public void setNumCardsInPlayerHands(String numCardsPerPlayer)
    {
        String[] tokens = numCardsPerPlayer.split(" ");
        for(int i = 0; i<tokens.length; i+=2){
            Text numText = nameToNumCards.get(tokens[i]);
            numText.setText("# of Cards: "+tokens[i+1]);
            //add static cards
            if(!tokens[i].equals(name)){
                Pane p = nameToCardPane.get(tokens[i]);
                int numCards = Integer.parseInt(tokens[i+1]);
                if(p instanceof HBox){
                    HBox h = (HBox)p;
                    h.getChildren().clear();
                    for(int j = 0; j<numCards; j++){
                        h.getChildren().addAll(new CardView("vertical"));
                    }
                }
                else if(p instanceof VBox){
                    VBox v = (VBox)p;
                    v.getChildren().clear();
                    for(int j = 0; j<numCards; j++){
                        v.getChildren().addAll(new CardView("horizontal"));
                    }
                }
            }
        }

    }

    //one card ex. 2 red
    public void setDiscardCard(String card)
    {
        String[] tokens = card.split(" ");
        CardView discard = new CardView(tokens[1],tokens[0]);
        discardView.setImage(discard.getImage());
        discardView.setCard(discard.getCard());
    }

    public void endGame(String winner){
        if(name.equals(winner)){
            networkInfo.setText("YOU LOSE!\n"+winner+" WON!");
        }
        else{
            networkInfo.setText("YOU WIN!");
        }
    }
    
    public void updateGameInfo(String info) {
    	
    	String[] data = info.split("\n");
		String topcard = data[0];
		String cards = "";
		String others = "";
		
		for(int i = 2;i<data.length;i+=2) {
			if(data[i].equals(name)) {
				cards = data[i+1].replaceAll(",", " ");
			}
			else {
				others += data[i]+" "+(data[i+1].split(",").length)+" ";
			}
		}
		
		setDiscardCard(topcard);
		setHand(cards);
		setNumCardsInPlayerHands(others);
		
		
    	//networkInfo.setText(topcard+"\n"+others);
    	//ObservableList<String> options = FXCollections.observableArrayList(cards.split(","));
    	//cardList.setItems(options);
    }

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        thisGUI = this;
        root = new StackPane();

        window.setScene(new Scene(root, NETWORKWIDTH, NETWORKHEIGHT));
        window.show();

        //network GUI works

        setupNetwork();
        mainMenu();

        //demo of GUI
//        namesArray = new String[]{"Bob","Jane","Zed","Dave"};
//        name = "Bob";
//        playGame();
//        setDiscardCard("3 blue");
//        setHand("4 red 3 yellow 5 yellow 5 yellow 5 yellow wild wild 5 yellow 5 yellow 5 yellow 5 yellow 5 yellow 5 yellow");
//        setNumCardsInPlayerHands("Bob 7 Jane 12 Zed 11 Dave 6");
//        enableAction();

    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
