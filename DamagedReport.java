import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
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

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Paint;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;

public class DamagedReport extends GridPane {
    private final Main app;

    private Label subject1, subject2, subject3,
            damagedNum1, damagedNum2, damagedNum3;

    private ComboBox<String> monthBox, subject1Box, subject2Box, subject3Box;

    public DamagedReport(Main app) {
        this.app = app;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label mainTitle = new Label("Damaged Books Report");
        HBox hbMainTitle = new HBox();
        hbMainTitle.setAlignment(Pos.CENTER);
        hbMainTitle.getChildren().add(mainTitle);
        add(hbMainTitle, 0, 0, 2, 1);

        HBox hbMonth = new HBox(5);
        Label monthBoxLabel = new Label("Month");
        monthBox = new ComboBox<String>();
        monthBox.getItems().addAll("January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October",
            "November", "December");
        hbMonth.getChildren().addAll(monthBoxLabel, monthBox);
        add(hbMonth, 0, 1);

        HBox hbSubject1 = new HBox(5);
        Label subject1Label = new Label("Subject");
        subject1Box = new ComboBox<String>();
        subject1Box.getItems().addAll("Science", "Math", "English", "History", "CS");
        hbSubject1.getChildren().addAll(subject1Label, subject1Box);
        add(hbSubject1, 1, 1);

        HBox hbSubject2 = new HBox(5);
        Label subject2Label = new Label("Subject");
        subject2Box = new ComboBox<String>();
        subject2Box.getItems().addAll("Science", "Math", "English", "History", "CS");
        hbSubject2.getChildren().addAll(subject2Label, subject2Box);
        add(hbSubject2, 1, 2);

        HBox hbSubject3 = new HBox(5);
        Label subject3Label = new Label("Subject");
        subject3Box = new ComboBox<String>();
        subject3Box.getItems().addAll("Science", "Math", "English", "History", "CS");
        hbSubject3.getChildren().addAll(subject3Label, subject3Box);
        add(hbSubject3, 1, 3);

        Label subject = new Label("Subject");
        Label damagedNum = new Label("# Damaged Books");
        setHalignment(subject, HPos.CENTER);
        setHalignment(damagedNum, HPos.CENTER);
        add(subject, 0, 6);
        add(damagedNum, 1, 6);

        subject1 = new Label("");
        damagedNum1 = new Label("");
        setHalignment(subject1, HPos.CENTER);
        setHalignment(damagedNum1, HPos.CENTER);
        add(subject1, 0, 7);
        add(damagedNum1, 1, 7);

        subject2 = new Label("");
        damagedNum2 = new Label("");
        setHalignment(subject2, HPos.CENTER);
        setHalignment(damagedNum2, HPos.CENTER);
        add(subject2, 0, 8);
        add(damagedNum2, 1, 8);

        subject3 = new Label("");
        damagedNum3 = new Label("");
        setHalignment(subject3, HPos.CENTER);
        setHalignment(damagedNum3, HPos.CENTER);
        add(subject3, 0, 9);
        add(damagedNum3, 1, 9);



        Button showReport = new Button("Show Report");
        HBox reportBtn = new HBox(10);
        reportBtn.setAlignment(Pos.CENTER);
        reportBtn.getChildren().add(showReport);
        reportBtn.setMargin(showReport, new Insets(15, 0, 0, 0));
        add(reportBtn, 0, 4, 2, 1);

        Button back = new Button("Back");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.CENTER);
        hbBtnBack.getChildren().add(back);
        hbBtnBack.setMargin(back, new Insets(15, 0, 0, 0));
        add(hbBtnBack, 0, 10, 2, 1);
    }

    public static Scene makeScene(Main app) {
        return new Scene(new DamagedReport(app));
    }
}

