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

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Paint;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;

public class Return extends GridPane {
    private final Main app;

    private TextField issue_id, isbn, copyNum, username;
    private CheckBox damaged;

    public Return(Main app) {
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

        Button submitBtn = new Button("Submit");
        HBox hbSubmit = new HBox(10);
        hbSubmit.setAlignment(Pos.CENTER);
        hbSubmit.getChildren().add(submitBtn);
        hbSubmit.setMargin(submitBtn, new Insets(0, 0, 0, 0));
        add(hbSubmit, 2, 1, 2, 1);

        Button returnBtn = new Button("Return");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(returnBtn);
        hbBtn2.setMargin(returnBtn, new Insets(15, 0, 0, 0));
        add(hbBtn2, 2, 4, 2, 1);


        Button back = new Button("Back");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.CENTER);
        hbBtnBack.getChildren().add(back);
        hbBtnBack.setMargin(back, new Insets(15, 0, 0, 0));
        add(hbBtnBack, 0, 4, 2, 1);
    }

    public static Scene makeScene(Main app) {
        return new Scene(new Return(app));
    }
}

