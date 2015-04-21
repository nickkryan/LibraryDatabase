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

public class Checkout extends GridPane {
    private final Main app;

    private TextField issue_id, username, isbn, copyNum,
            checkoutDate, estReturnDate;

    public Checkout(Main app, String user) {
        this.app = app;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label mainTitle = new Label("Book Checkout");
        HBox hbMainTitle = new HBox();
        hbMainTitle.setAlignment(Pos.CENTER);
        hbMainTitle.getChildren().add(mainTitle);
        add(hbMainTitle, 0, 0, 4, 1);

        Label issue_idLabel = new Label("Issue Id");
        issue_id = new TextField();
        add(issue_idLabel, 0, 1);
        add(issue_id, 1, 1);

        Label isbnLabel = new Label("ISBN");
        isbn = new TextField();
        isbn.setEditable(false);
        add(isbnLabel, 0, 2);
        add(isbn, 1, 2);

        Label checkoutDateLabel = new Label("Checkout Date");
        checkoutDate = new TextField();
        checkoutDate.setEditable(false);
        add(checkoutDateLabel, 0, 3);
        add(checkoutDate, 1, 3);

        Label usernameLabel = new Label("Username");
        username = new TextField();
        username.setEditable(false);
        add(usernameLabel, 2, 1);
        add(username, 3, 1);

        Label copyNumLabel = new Label("Copy Number");
        copyNum = new TextField();
        copyNum.setEditable(false);
        add(copyNumLabel, 2, 2);
        add(copyNum, 3, 2);

        Label estReturnDateLabel = new Label("Estimated Return Date");
        estReturnDate = new TextField();
        estReturnDate.setEditable(false);
        add(estReturnDateLabel, 2, 3);
        add(estReturnDate, 3, 3);

        Button submit = new Button("Submit Isbn");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.CENTER);
        hbBtn3.getChildren().add(submit);
        hbBtn3.setMargin(submit, new Insets(15, 0, 0, 0));
        submit.setOnAction(e -> {
            String[] info = Database.checkoutBookInfo(issue_id.getText());
        });
        add(hbBtn3, 3, 4, 2, 1);


        Button locate = new Button("Confirm");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(locate);
        hbBtn2.setMargin(locate, new Insets(15, 0, 0, 0));
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
        return new Scene(new Checkout(app, user));
    }
}

