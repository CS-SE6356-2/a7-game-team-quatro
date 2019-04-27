import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.util.ArrayList;

public class GUI extends Application{
    public final static int CONNECTGUIWIDTH = 600; // window width
    public final static int CONNECTGUIHEIGHT = 600;  // window height

    public final static int PLAYGUIWIDTH = 1000; // window width
    public final static int PLAYGUIHEIGHT = 800;  // window height

    private Stage window; // main stage to display on the screen

    private Label instructionLabel;
    private ImageView discardView;
    private Button draw;
    private Button play;
    private ScrollPane handPane;

    private ViewHandler vh; // manages all renderables on the screen

    private Canvas canvas; // draw canvas for the window

    /**
     * Initializes all objects required for the lifetime
     *  of the GUI because no constructor is called.
     */
    public void init() {
        // Initializes ViewHandler which serves to hold, render, and tick all Renderable objects
        vh = new ViewHandler();

        // Dummy renderable ViewCards
        //vh.addRenderable(new ViewCard(2,2));
        // Dynamic object to test for functioning rerender and tick methods
        //vh.addRenderable(new DynamicObject(0, 0));
    }

    /**
     * Render method handles screen refresh and redraw.
     *  Serves as hub for all renders to the screen.
     *  Currently only calls ViewHandler.render() in order
     *      to draw all assigned Renderables.
     *  Clears background of screen with WHITE box before
     *      starting any other render process.
     */
    private void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (gc == null) {
            System.err.println("MAIN: Cannot render to Canvas when GraphicsContext is null.");
            return;
        }

        // painting the whole screen with white
        //gc.setFill(Color.WHITE);
        //gc.fillRect(0, 0, WIDTH, HEIGHT);

        //vh.render(gc);
    }

    private void createConnectGUI(){
        window.setTitle("Connect to or Create UNO game");

        GridPane root = new GridPane();
        //canvas = new Canvas(CONNECTGUIWIDTH, CONNECTGUIHEIGHT);
        Label nameLabel = new Label("Enter Player Name");
        TextField nameField = new TextField();
        Label hostLabel = new Label("Host a game");
        Button host = new Button("Host");
        Label connectLabel = new Label("Connect to an existing game by IP address");
        Button connect = new Button("Connect");
        TextField connectField = new TextField();
        Label connectingLabel = new Label("Connecting...");

        connectingLabel.setVisible(false);

        host.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent value){
                    host();
                }
            });
        connect.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent value){
                    connect(nameLabel.getText(), connectField.getText());
                }
            });

        root.add(nameLabel, 0, 0);
        root.add(nameField, 1, 0);
        root.add(hostLabel, 0, 1);
        root.add(host, 1, 1);
        root.add(connectLabel, 0, 2);
        root.add(connect, 0, 3);
        root.add(connectField, 1, 3);
        root.add(connectingLabel, 0, 4);

        window.setScene(new Scene(root, CONNECTGUIWIDTH, CONNECTGUIHEIGHT));
        window.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent value){
                    stop();
                }
            });
        window.show();

    }

    private void host(){
        //call controller to host
    }

    private void connect(String name, String address){
        if(name!=null && address!=null){
            //call controller to connect
        }
    }

    public void enterRoom(ArrayList<String> names){
        //get names from controller
        window.setTitle("UNO Lobby");

        GridPane root = new GridPane();
        //canvas = new Canvas(CONNECTGUIWIDTH, CONNECTGUIHEIGHT);
        Label instructionLabel = new Label("Click 'Ready' when ready when to start UNO game");
        Button ready = new Button("Ready");

        ready.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent value){
                    //send message to controller that client is ready
                }
            });

        root.add(instructionLabel, 0, 0);
        root.add(ready, 0, 1);
        for(int i = 0; i<names.size(); i++){
            root.add(new Label(names.get(i)), 0, i+2);
        }

        window.setScene(new Scene(root, CONNECTGUIWIDTH, CONNECTGUIHEIGHT));
        window.setOnCloseRequest(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent value){
                    stop();
                }
            });
        window.show();
    }

    public void play(){
        window.setTitle("UNO Game");

        BorderPane root = new BorderPane();
        //canvas = new Canvas(CONNECTGUIWIDTH, CONNECTGUIHEIGHT);
        Label instructionLabel = new Label("Your Turn");
        ImageView discardView = new ImageView();
        Button draw = new Button("Draw Card");
        Button play = new Button("Play Selected");
        ScrollPane handPane = new ScrollPane();

        root.setTop(instructionLabel);
        root.setCenter(discardView);
        root.setLeft(draw);
        root.setRight(play);
        root.setBottom(handPane);

        this.instructionLabel = instructionLabel;
        this.discardView = discardView;
        this.draw = draw;
        this.play = play;
        this.handPane = handPane;

        //how many actions per turn
        draw.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent value){
                    //controller command draw to add to hand
                }
            });
        play.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent value){
                    //computeMessage
                    //sendMessageToServer
                }
            });

        window.setScene(new Scene(root, PLAYGUIWIDTH, PLAYGUIHEIGHT));
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
        draw.setDisable(true);
    }

    public void enableAction(){
        play.setDisable(false);
        draw.setDisable(false);
    }

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        createConnectGUI();
    }
    
    public void setHand(String hand)//string is a listing of pairs of cards
    //ex blue 8 green wild
    {
    }
    
    public void setNumCardsInPlayerHand(String numCardsPerPlayer)
    {
    }
    
    public void setMessage(String message)
    {
    }
    
    public void setDiscardCard(String card)
    {
    }
    
    /**
     * Defines the stop method for the JavaFX Application.
     */
    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
