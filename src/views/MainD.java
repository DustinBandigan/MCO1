package views;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class MainD extends Application implements EventHandler<ActionEvent> {

    Button button;
    TextArea Display = new TextArea();
    VBox layout = new VBox(10);  // 10 is the spacing between elements

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("=== VERDANT SUN FARMING SIMULATOR ===");
        button = new Button("Check views.Leaderboard");
        button.setOnAction(this); //Means that the code that handles this thing is in the class.
        
        layout.getChildren().addAll(button,Display);

        Scene scene = new Scene(layout,1280, 720); //Scene Size, and size of entire window
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == button) {
            try {
                Leaderboard lb = new Leaderboard();
                Display.setText(lb.display_score());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
