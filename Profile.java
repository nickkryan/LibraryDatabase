import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Profile extends GridPane {

    public static final int WIDTH = 750, HEIGHT = 300;

    private final Main app;

    public Profile(Main app) {
        this.app = app;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label title = new Label("Create Profile");
        HBox hbTitle = new HBox();
        hbTitle.setAlignment(Pos.CENTER);
        hbTitle.getChildren().add(title);
        add(hbTitle, 0, 0, 4, 1);

        Label firstnameLabel = new Label("First Name");
        add(firstnameLabel, 0, 1);

        TextField firstname = new TextField();
        add(firstname, 1, 1);

        Label lastnameLabel = new Label("Last Name");
        add(lastnameLabel, 2, 1);

        TextField lastname = new TextField();
        add(lastname, 3, 1);

        Label dobLabel = new Label("D.O.B");
        add(dobLabel, 0, 2);

        TextField dob = new TextField();
        add(dob, 1, 2);

        Label genderLabel = new Label("Gender");
        add(genderLabel, 2, 2);

        ComboBox<String> gender = new ComboBox<>();
        gender.getItems().addAll("M", "F", "T");
        add(gender, 3, 2);

        Label emailLabel = new Label("Email");
        add(emailLabel, 0, 3);

        TextField email = new TextField();
        add(email, 1, 3);

        Label facultyLabel = new Label("Are you a faculty member?");
        add(facultyLabel, 2, 3);

        CheckBox faculty = new CheckBox();
        add(faculty, 3, 3);

        Label addressLabel = new Label("Address");
        add(addressLabel, 0, 4);

        TextArea address = new TextArea();
        address.setPrefColumnCount(email.getPrefColumnCount());
        add(address, 1, 4, 1, 2);
        
        final Label departmentLabel = new Label("Associated Department");
        departmentLabel.setVisible(false);
        add(departmentLabel, 2, 4);
        
        final ComboBox<String> department = new ComboBox<>();
        department.getItems().addAll("PUT DEPARTMENTS HERE");
        department.setVisible(false);
        add(department, 3, 4);

        Button submit = new Button("Submit");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(submit);
        add(hbBtn2, 3, 6);

        final Text actionTarget = new Text();
        add(actionTarget, 0, 6, 2, 1);

        faculty.selectedProperty().addListener((obj, oldValue, newValue) -> {
            departmentLabel.setVisible(newValue);
            department.setVisible(newValue);
        });
        
        submit.setOnAction((ActionEvent e) -> {
            // DO SOMETHING
        });
    }

    public static Scene makeScene(Main app) {
        return new Scene(new Profile(app), WIDTH, HEIGHT);
    }
}
