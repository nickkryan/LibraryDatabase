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
import javafx.scene.paint.Color;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Paint;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;

public class ReturnBook extends GridPane {
    private final Main app;

    private TextField issue_id, isbn, copyNum, username;
    private CheckBox damaged;

    public ReturnBook(Main app, String user) {
        this.app = app;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label mainTitle = new Label("Return Book");
        HBox hbMainTitle = new HBox();
        hbMainTitle.setAlignment(Pos.CENTER);
        hbMainTitle.getChildren().add(mainTitle);
        add(hbMainTitle, 0, 0, 4, 1);

        Label idLabel = new Label("Issue Id");
        issue_id = new TextField();
        add(idLabel, 0, 1);
        add(issue_id, 1, 1);

        Label isbnLabel = new Label("ISBN");
        isbn = new TextField();
        isbn.setEditable(false);
        add(isbnLabel, 0, 2);
        add(isbn, 1, 2);

        Label copyNumLabel = new Label("Copy Number");
        copyNum = new TextField();
        copyNum.setEditable(false);
        add(copyNumLabel, 2, 2);
        add(copyNum, 3, 2);

        Label usernameLabel = new Label("Username");
        username = new TextField();
        username.setEditable(false);
        add(usernameLabel, 2, 3);
        add(username, 3, 3);

        Label damagedLabel = new Label("Return in Damaged Condition");
        damaged = new CheckBox();
        HBox hbDamaged = new HBox(8);
        hbDamaged.getChildren().addAll(damagedLabel, damaged);
        add(hbDamaged, 0, 3, 2, 1);

        final Text actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);
        add(actionTarget, 3, 5);

        Button submitBtn = new Button("Submit");
        HBox hbSubmit = new HBox(10);
        hbSubmit.setAlignment(Pos.CENTER);
        hbSubmit.getChildren().add(submitBtn);
        hbSubmit.setMargin(submitBtn, new Insets(0, 0, 0, 0));
        submitBtn.setOnAction(e -> {
            if (!issue_id.getText().equals("") && Integer.parseInt(issue_id.getText()) > 0) {
                String[] info = Database.returnBookInfo(issue_id.getText());
                username.setText(info[0]);
                isbn.setText(info[1]);
                copyNum.setText(info[2]);
                if (info[3] != null) {
                    actionTarget.setText("Book has already been returned!");
                } else {
                    actionTarget.setText("");
                }
            } else if (issue_id.getText().equals("")) {
                actionTarget.setText("Need issue id!");
                username.setText("");
                copyNum.setText("");
                isbn.setText("");
                damaged.setSelected(false);
            } else {
                actionTarget.setText("Invalid issue id!");
                username.setText("");
                copyNum.setText("");
                isbn.setText("");
                damaged.setSelected(false);
            }
        });
        add(hbSubmit, 2, 1, 2, 1);

        Button returnBtn = new Button("Return Book");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(returnBtn);
        hbBtn2.setMargin(returnBtn, new Insets(15, 0, 0, 0));
        returnBtn.setOnAction(e -> {
            String isDamaged = (damaged.isSelected() ? "1" : "0");
            boolean result = Database.returnBookAndSetPenalties(isDamaged,
                username.getText(), isbn.getText(), copyNum.getText());
            if (result && damaged.isSelected()) {
                app.changeScene(LostDamaged.makeScene(app, user));
            } else if (!result) {
                actionTarget.setText("Error ocurred");
            } else {
                actionTarget.setText("Success");
            }
        });
        add(hbBtn2, 2, 4, 2, 1);


        Button back = new Button("Back");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.CENTER);
        hbBtnBack.getChildren().add(back);
        hbBtnBack.setMargin(back, new Insets(15, 0, 0, 0));
        add(hbBtnBack, 0, 4, 2, 1);
        back.setOnAction(e -> app.changeScene(MainMenu.makeScene(app, user)));

    }

    public static Scene makeScene(Main app, String user) {
        return new Scene(new ReturnBook(app, user));
    }
}

