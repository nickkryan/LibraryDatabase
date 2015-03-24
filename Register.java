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

public class Register extends GridPane {

    public static final int WIDTH = 400, HEIGHT = 275;

    private Main app;

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

        Label username = new Label("Username:");
        add(username, 0, 1);

        TextField userTextField = new TextField();
        add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        add(pwBox, 1, 2);

        Label pw2 = new Label("Confirm Password:");
        add(pw2, 0, 3);

        PasswordField pw2Box = new PasswordField();
        add(pw2Box, 1, 3);

        Button register = new Button("Register");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(register);
        add(hbBtn2, 0, 4, 2, 1);

        final Text actionTarget = new Text();
        add(actionTarget, 0, 5, 2, 1);

        register.setOnAction((ActionEvent e) -> {
            if (!pw.getText().equals(pw2.getText())) {
                actionTarget.setFill(Color.FIREBRICK);
                actionTarget.setText("Passwords do not match!");
            }
        });
    }
}
