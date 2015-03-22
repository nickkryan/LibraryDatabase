import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));

        Label username = new Label("Username:");
        grid.add(username, 0, 0);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 0);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 1);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 1);

        Button login = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(login);
        grid.add(hbBtn, 0, 2, 2, 1);

        HBox text = new HBox(10);
        text.setAlignment(Pos.CENTER);
        text.getChildren().add(new Label("OR"));
        grid.add(text, 0, 3, 2, 1);

        Button register = new Button("Create Account");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(register);
        grid.add(hbBtn2, 0, 4, 2, 1);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 0, 5, 2, 1);

        login.setOnAction((ActionEvent e) -> {
            if (Database.login(userTextField.getText(), pwBox.getText())) {
                actionTarget.setFill(Color.GREEN);
                actionTarget.setText("Correct Login");
            } else {
                actionTarget.setFill(Color.FIREBRICK);
                actionTarget.setText("Incorrect Login");
            }
        });

        register.setOnAction((ActionEvent e) -> {
            actionTarget.setFill(Color.FIREBRICK);
            actionTarget.setText("Create Account button pressed");
        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
