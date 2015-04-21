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

public class LostDamaged extends GridPane {
    private final Main app;

    private TextField isbn, copyNum, currentTime, lastUser, chargeAmount;

    public LostDamaged(Main app, String user) {
        this.app = app;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label mainTitle = new Label("Lost/Damaged Book");
        HBox hbMainTitle = new HBox();
        hbMainTitle.setAlignment(Pos.CENTER);
        hbMainTitle.getChildren().add(mainTitle);
        add(hbMainTitle, 0, 0, 4, 1);

        Label isbnLabel = new Label("ISBN");
        isbn = new TextField();
        setHalignment(isbnLabel, HPos.RIGHT);
        add(isbnLabel, 0, 1);
        add(isbn, 1, 1);

        Label copyNumLabel = new Label("Copy Number");
        copyNum = new TextField();
        setHalignment(copyNumLabel, HPos.RIGHT);
        add(copyNumLabel, 2, 1);
        add(copyNum, 3, 1);

        Label currentTimeLabel = new Label("Current Time");
        currentTime = new TextField();
        currentTime.setEditable(false);
        add(currentTimeLabel, 0, 2);
        add(currentTime, 1, 2);

        Label lastUserLabel = new Label("Last User of the Book");
        lastUser = new TextField();
        lastUser.setEditable(false);
        add(lastUserLabel, 1, 4);
        add(lastUser, 2, 4);

        Label chargeAmountLabel = new Label("Amount to be Charged");
        chargeAmount = new TextField();
        chargeAmount.setEditable(false);
        add(chargeAmountLabel, 1, 5);
        add(chargeAmount, 2, 5);


        Button lastUserBtn = new Button("Look for Last User");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(lastUserBtn);
        hbBtn2.setMargin(lastUserBtn, new Insets(0, 0, 20, 0));
        add(hbBtn2, 0, 3, 4, 1);

        Button submit2 = new Button("Submit");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.CENTER);
        hbBtn3.getChildren().add(submit2);
        hbBtn3.setMargin(submit2, new Insets(15, 0, 0, 0));
        add(hbBtn3, 3, 6, 2, 1);

        Button back = new Button("Back");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.CENTER);
        hbBtnBack.getChildren().add(back);
        hbBtnBack.setMargin(back, new Insets(15, 0, 0, 0));
        add(hbBtnBack, 0, 6, 2, 1);
    }

    public static Scene makeScene(Main app, String user) {
        return new Scene(new LostDamaged(app, user));
    }
}

