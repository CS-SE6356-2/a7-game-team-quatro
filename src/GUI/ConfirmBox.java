import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox {
    static boolean answer;

    public static boolean display(String title, String msg) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);


        HBox layout = new HBox();
        Button yes = new Button("Confirm");
        yes.setOnAction(e -> {
            answer = true;
            window.close();
        });

        Button no = new Button("Deny");
        no.setOnAction(e -> {
            answer = false;
            window.close();
        });

        layout.getChildren().addAll(yes, no);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

}
