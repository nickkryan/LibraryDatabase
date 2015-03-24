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

public class Login extends GridPane {

    public static final int WIDTH = 300, HEIGHT = 275;

    private Main app;

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

        Label username = new Label("Username:");
        add(username, 0, 1);

        TextField userTextField = new TextField();
        add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        add(pwBox, 1, 2);

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
            if (Database.login(userTextField.getText(), pwBox.getText())) {
                actionTarget.setFill(Color.GREEN);
                actionTarget.setText("Correct Login");
            } else {
                actionTarget.setFill(Color.FIREBRICK);
                actionTarget.setText("Incorrect Login");
            }
        });

        register.setOnAction((ActionEvent e) -> {
            Register reg = new Register(app);
            app.changeScene(new Scene(reg, reg.WIDTH, reg.HEIGHT));
        });
    }
}
