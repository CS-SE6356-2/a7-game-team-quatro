import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
    
    
    private Stage window; // main stage to display on the screen
    private ViewHandler vh; //  manages all renderables on the screen

    private Thread thread; // thread running
    private boolean running = false; // whether or not the application is still active

    private Canvas canvas; // draw canvas for the window
    
    
    private StackPane root;//all GUI objects are added to this parent
    private VBox menuRoot;//vertical container for menu objects
    
    //Added to interact with network
    private Text screenInfo;//labels current screen
    private Text newtworkInfo;//contains info from client thread
	private TextField nameField;//to input the player's name
	private TextField serverField;//to input the server address
	
	private Button hostServer;//on main menu, moves to setup the host server screen
	private Button startServer;//on hosting server screen, starts the server
	
	private Button joinServer;//on main menu, moves to joining server screen
	private Button connectToServer;//on joining screen, attempts to connect to server
	
	private Button menu;//on joining and hosting screen, goes to main menu
	
	
	
    /**
     * Initializes all objects required for the lifetime
     *  of the GUI because no constructor is called.
     */
    public void init() {
    	
    	clientThread = new NetworkClient();// can really be initialized whenever
    	
        // Initializes ViewHandler which serves to hold, render, and tick all Renderable objects
        vh = new ViewHandler();

        // Dummy renderable ViewCards
        for (int i = 0; i < 3; ++i) {
            vh.addRenderable(new ViewCard(Suit.Clubs, 4 * i + 2, WIDTH / 3 * i, 0));
        }
        // Dynamic object to test for functioning rerender and tick methods
        vh.addRenderable(new DynamicObject(0, 0));
        
        
        screenInfo = new Text();
        newtworkInfo = new Text();
        nameField = new TextField();
        serverField = new TextField();
        
        hostServer = new Button("Host Game");
        startServer = new Button("Start Server");
        joinServer = new Button("Join Game");
        connectToServer = new Button("Connect to Server");
        
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
    	
    	menuRoot.getChildren().addAll(screenInfo, joinServer, startServer);
    	root.getChildren().add(menuRoot);
    	
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
        vh.tick();
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

        vh.render(gc);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        primaryStage.setTitle("Generic Card Game Engine");

        root = new StackPane();
        canvas = new Canvas(WIDTH, HEIGHT);

        init();

        root.getChildren().addAll(canvas);

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();

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
