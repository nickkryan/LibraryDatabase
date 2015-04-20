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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.regex.Pattern;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Paint;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;

public class Profile extends GridPane {
    private final Main app;
    private final String user;

    private TextField firstname, lastname, dob, email;
    private TextArea address;
    private ComboBox<String> gender, department;
    private CheckBox faculty;
    private Label departmentLabel;

    private static final Pattern rfc2822 = Pattern.compile(
        "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
    );

    public Profile(Main app, String user) {
        this.app = app;
        this.user = user;

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

        firstname = new TextField();
        add(firstname, 1, 1);

        Label lastnameLabel = new Label("Last Name");
        add(lastnameLabel, 2, 1);

        lastname = new TextField();
        add(lastname, 3, 1);

        Label dobLabel = new Label("D.O.B");
        add(dobLabel, 0, 2);

        dob = new TextField();
        dob.setPromptText("yyyy-mm-dd");
        add(dob, 1, 2);

        Label genderLabel = new Label("Gender");
        add(genderLabel, 2, 2);

        gender = new ComboBox<String>();
        gender.getItems().addAll("M", "F", "T");
        add(gender, 3, 2);

        Label emailLabel = new Label("Email");
        add(emailLabel, 0, 3);

        email = new TextField();
        add(email, 1, 3);

        Label facultyLabel = new Label("Are you a faculty member?");
        add(facultyLabel, 2, 3);

        faculty = new CheckBox();
        add(faculty, 3, 3);

        Label addressLabel = new Label("Address");
        add(addressLabel, 0, 4);

        address = new TextArea();
        address.setPrefColumnCount(email.getPrefColumnCount());
        address.setPrefRowCount(3);
        add(address, 1, 4, 1, 2);

        departmentLabel = new Label("Associated Department");
        departmentLabel.setVisible(false);
        add(departmentLabel, 2, 4);

        department = new ComboBox<String>();
        department.getItems().addAll("MATH", "ENG", "PSYCH", "CS", "CM", "MECH",
            "BME", "AE", "BIO", "CHEM", "CHEME", "EE", "HPS", "HTS", "INTA");
        department.setVisible(false);
        add(department, 3, 4);

        Button submit = new Button("Submit");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(submit);
        add(hbBtn2, 2, 6, 2, 1);

        final Text actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);
        add(actionTarget, 2, 5, 2, 1);

        faculty.selectedProperty().addListener((obj, oldValue, newValue) -> {
            departmentLabel.setVisible(newValue);
            department.setVisible(newValue);
        });

        submit.setOnAction((ActionEvent e) -> {
            if (firstname.getText().equals("") || lastname.getText().equals("")) {
                actionTarget.setText("Please enter your first and last name");
            } else if (faculty.isSelected()
                 && department.getSelectionModel().getSelectedItem() == null) {
                actionTarget.setText("Please select a Department");
            } else if (!email.getText().equals("") && !rfc2822.matcher(email.getText()).matches()) {
                actionTarget.setText("Invalid email! Please match format: email@site.com");
            } else {
                if (Database.updateProfile(
                    firstname.getText() + " " + lastname.getText(),
                    dob.getText().equals("") ? null : dob.getText(),
                    gender.getSelectionModel().getSelectedItem(),
                    email.getText().equals("") ? null : email.getText(),
                    address.getText().equals("") ? null : address.getText(),
                    faculty.isSelected(),
                    faculty.isSelected() ? department.getSelectionModel().getSelectedItem() : null,
                    user)) {
                    app.changeScene(SearchBooks.makeScene(app, user));
                } else {
                    actionTarget.setText("Invalid date of birth! Please match format: yyyy-mm-dd");
                }
            }
        });
    }

    public static Scene makeScene(Main app, String user) {
        return new Scene(new Profile(app, user));
    }
}
