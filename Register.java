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

public class Register extends GridPane {

    public static final int WIDTH = 400, HEIGHT = 275;

    private final Main app;

    public Register(Main app) {
        this.app = app;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label title = new Label("New User Registration");
        HBox hbTitle = new HBox();
        hbTitle.setAlignment(Pos.CENTER);
        hbTitle.getChildren().add(title);
        add(hbTitle, 0, 0, 2, 1);

        Label usernameLabel = new Label("Username:");
        add(usernameLabel, 0, 1);

        TextField username = new TextField();
        add(username, 1, 1);

        Label pwLabel = new Label("Password:");
        add(pwLabel, 0, 2);

        PasswordField pw = new PasswordField();
        add(pw, 1, 2);

        Label pw2Label = new Label("Confirm Password:");
        add(pw2Label, 0, 3);

        PasswordField pw2 = new PasswordField();
        add(pw2, 1, 3);

        Button register = new Button("Register");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(register);
        add(hbBtn2, 0, 4, 2, 1);

        final Text actionTarget = new Text();
        add(actionTarget, 0, 5, 2, 1);

        register.setOnAction((ActionEvent e) -> {
            if (username.getText().isEmpty() || pw.getText().isEmpty() || pw2.getText().isEmpty()) {
                actionTarget.setFill(Color.FIREBRICK);
                actionTarget.setText("All fields are required and must not be empty!");
            } else if (!pw.getText().equals(pw2.getText())) {
                actionTarget.setFill(Color.FIREBRICK);
                actionTarget.setText("Passwords do not match!");
            } else {
                if (Database.register(username.getText(), pw.getText())) {
                    actionTarget.setFill(Color.GREEN);
                    actionTarget.setText("User successfully registered!");
                    app.changeScene(Profile.makeScene(app));
                } else {
                    actionTarget.setFill(Color.FIREBRICK);
                    actionTarget.setText("Username already registered!");
                }
            }
        });
    }

    public static Scene makeScene(Main app) {
        return new Scene(new Register(app), WIDTH, HEIGHT);
    }
}
