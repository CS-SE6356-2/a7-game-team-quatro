import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application implements Runnable {
    public final static int WIDTH = 900; // window width
    public final static int HEIGHT = 600;  // window height

    //Player's name, will need to be accessed by client thread
    public String name;
    
    //References to the network threads
    private NetworkHost hostThread;
    private NetworkClient clientThread;
    
    private Main thisMain;
    
    private Stage window; // main stage to display on the screen
//    private ViewHandler vh; //  manages all renderables on the screen

    private Thread thread; // thread running
    private boolean running = false; // whether or not the application is still active

    private Canvas canvas; // draw canvas for the window
    
    
    private StackPane root;//all GUI objects are added to this parent
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
	
	private ComboBox<String> cardList;
	private Button endTurn;
	
	private Button menu;//on joining and hosting screen, goes to main menu
	private Button showCanvas;
	
	
    /**
     * Initializes all objects required for the lifetime
     *  of the GUI because no constructor is called.
     */
    public void init() {
    	
    	thisMain = this;
    	
        // Initializes ViewHandler which serves to hold, render, and tick all Renderable objects
//        vh = new ViewHandler();

        // Dummy renderable ViewCards
//        for (int i = 0; i < 3; ++i) {
//            vh.addRenderable(new ViewCard(Suit.Clubs, 4 * i + 2, WIDTH / 3 * i, 0));
//        }
//        // Dynamic object to test for functioning rerender and tick methods
//        vh.addRenderable(new DynamicObject(0, 0));
        
        
        menuRoot = new VBox();
        
        screenInfo = new Text();
        networkInfo = new Text();
        nameField = new TextField();
        addressField = new TextField();
        
        cardList = new ComboBox<String>();
        
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
					clientThread.GUI = thisMain;
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
				clientThread.GUI = thisMain;
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
        
        showCanvas = new Button("Show Canvas");
        showCanvas.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showCanvas();
			}
		});
    }

    
    private void mainMenu() {
    	
    	if(hostThread != null) hostThread.shutdown();
    	if(clientThread != null) clientThread.shutdown();
    	
    	root.getChildren().clear();
    	menuRoot.getChildren().clear();
    	
    	screenInfo.setText("Main Menu - UNO");
    	
    	menuRoot.getChildren().addAll(screenInfo, showCanvas, joinServer, hostServer);
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
    
    private void showCanvas() {
    	root.getChildren().clear();
    	root.getChildren().addAll(canvas);
    }
    
    private void endTurn() {
    	menuRoot.getChildren().remove(endTurn);
    	clientThread.respondWithTurnInfo(cardList.getValue());
    }
    
    public void updatePlayerList(String names) {
    	networkInfo.setText(names);
    }
    
    public void goToGame() {
    	root.getChildren().clear();
    	menuRoot.getChildren().clear();
    	
    	screenInfo.setText("Uno");
    	networkInfo.setText("");
    	menuRoot.getChildren().addAll(screenInfo, networkInfo, cardList);
    	root.getChildren().addAll(menuRoot);
    }
    
    public void updateGameInfo(String info) {
    	
    	String[] data = info.split("\n");
		String topcard = "Current pile: "+data[0];
		String cards = "";
		String others = "";
		
		for(int i = 2;i<data.length;i+=2) {
			if(data[i].equals(name)) {
				cards = data[i+1];
			}
			else {
				others += data[i]+" has "+data[i+1].split(",").length+" cards\n";
			}
		}
		
    	networkInfo.setText(topcard+"\n"+others);
    	ObservableList<String> options = FXCollections.observableArrayList(cards.split(","));
    	cardList.setItems(options);
    }
    
    public void takeTurn() {
    	menuRoot.getChildren().add(endTurn);
    }
    
    public void endGame(String winner) {
    	root.getChildren().clear();
    	menuRoot.getChildren().clear();
    	
    	screenInfo.setText("Game Over");
    	networkInfo.setText(winner+ " won!");
    	menuRoot.getChildren().addAll(screenInfo, networkInfo, menu);
    	root.getChildren().addAll(menuRoot);
    }
    
    
    public void disconnected() {
    	
    	mainMenu();
    	networkInfo.setText("Disconnected by server");
    	menuRoot.getChildren().add(networkInfo);
    }
    
    public void run() {
        /*
         * Defines the activity that occurs
         *  every time the thread updates the UI.
         */
        final Runnable updater = () -> {
            render(); // Renders the Renderables at their new position
            tick(); // Updates all Renderables with whatever tick method
        };

        /*
         * Attempts to get the thread to update tickRate times a second.
         *  Not 100% accurate, so tick count frequently varies between
         *  tickRate-1 & tickRate+1 times a second even if system is
         *  sufficiently fast.
         * Tick rate will be significantly slower if the system is unable
         *  to render and tick at the set tickrate. 60.0 is very standard.
         */
        long lastUpdate = System.nanoTime();
        final double tickRate = 60.0;
        final double tickGap = Math.pow(10, 9) / tickRate;
        /*
         * Loops while thread hasn't yet joined with main thread
         *  While loop runs very fast, so tickGap is updated very
         *  frequently to check for next update.
         */
        while (running) {
            // Checks if tickGap time has passed since last update and calls update if so
            if (System.nanoTime() >= lastUpdate + tickGap) {
                // UI update is run on the Application thread to prevent thread crashes & desync
                Platform.runLater(updater);
                lastUpdate += tickGap;
            }
        }
    }

    /**
     * Tick method handles all state changes during
     *  the lifetime of the main thread.
     * Calls ViewHandler.tick() to update state on all
     *  Renderable objects.
     */
    private void tick() {
//        vh.tick();
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
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

//        vh.render(gc);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        primaryStage.setTitle("Uno Game - Team 4");

        root = new StackPane();
        canvas = new Canvas(WIDTH, HEIGHT);

        init();

        root.getChildren().addAll(canvas);
        
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();

        mainMenu();
        
        thread = new Thread(this);

        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);

        running = true; // sets running to begin start of GUI thread
        thread.start(); // starts GUI update thread
    }

    /**
     * Defines the stop method for the JavaFX Application.
     *  1) Sets running to false, which kills the GUI thread.
     *  2) Joins GUI thread with Main thread, preventing it from dangling.
     *  3) Prints stacktrace and error if thread.join() fails.
     */
    @Override
    public void stop() {
        running = false;
        try {
            if (thread != null)
                thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unable to join display thread with Main thread.");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
