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

public class Login extends GridPane {

    private final Main app;

    public Login(Main app) {
        this.app = app;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label title = new Label("Login");
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

        Button login = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(login);
        add(hbBtn, 0, 3, 2, 1);

        HBox text = new HBox(10);
        text.setAlignment(Pos.CENTER);
        text.getChildren().add(new Label("OR"));
        add(text, 0, 4, 2, 1);

        Button register = new Button("Create Account");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(register);
        add(hbBtn2, 0, 5, 2, 1);

        final Text actionTarget = new Text();
        add(actionTarget, 0, 6, 2, 1);

        login.setOnAction((ActionEvent e) -> {
            if (Database.login(username.getText(), pw.getText())) {
                actionTarget.setFill(Color.GREEN);
                actionTarget.setText("Correct Login");
            } else {
                actionTarget.setFill(Color.FIREBRICK);
                actionTarget.setText("Incorrect Login");
            }
        });

        register.setOnAction((ActionEvent e) -> {
            app.changeScene(Register.makeScene(app));
        });
    }

    public static Scene makeScene(Main app) {
        return new Scene(new Login(app));
    }
}
