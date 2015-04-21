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

import java.time.LocalDate;

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

        final Text actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);
        add(actionTarget, 3, 5);

        Button submit = new Button("Isbn Submit");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.CENTER);
        hbBtn3.getChildren().add(submit);
        hbBtn3.setMargin(submit, new Insets(15, 0, 0, 0));
        add(hbBtn3, 1, 5);
        submit.setOnAction(e -> {
            String[] info = Database.checkoutBookInfo(issue_id.getText());
            String[] date = info[4].split(" ");
            String[] dateComponents = date[0].split("-");
            LocalDate issueDate = LocalDate.of(Integer.parseInt(dateComponents[0]), 
                Integer.parseInt(dateComponents[1]), 
                Integer.parseInt(dateComponents[2]));
            int yearDiff = LocalDate.now().getYear() - issueDate.getYear();
            int monthDiff = LocalDate.now().getMonthValue() - issueDate.getMonthValue();
            int dayDiff = LocalDate.now().getDayOfMonth() - issueDate.getDayOfMonth();

            if (info[3] != null) {
                actionTarget.setText("Invalid issue: Already checked out/returned.");
                username.setText("");
                isbn.setText("");
                copyNum.setText("");
                checkoutDate.setText("");
                estReturnDate.setText("");
            } else if (yearDiff > 0 || monthDiff > 0 || dayDiff > 3) {
                actionTarget.setText("Issue date greater than 3 days, hold request expired\n Please submit a new hold or a future hold request.");
                username.setText("");
                isbn.setText("");
                copyNum.setText("");
                checkoutDate.setText("");
                estReturnDate.setText("");
            } else {
                actionTarget.setText("");
                username.setText(info[0]);
                isbn.setText(info[1]);
                copyNum.setText(info[2]);
                checkoutDate.setText(LocalDate.now().toString());
                estReturnDate.setText(LocalDate.now().plusDays(14).toString());
            }
        });

        Button locate = new Button("Confirm");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(locate);
        hbBtn2.setMargin(locate, new Insets(15, 0, 0, 0));
        locate.setOnAction(e -> {
            boolean result = Database.checkoutBookAndUpdateDb(checkoutDate.getText(),
                isbn.getText(), copyNum.getText(), username.getText());
            if (result) {
                actionTarget.setText("Successfully checked out!");
            } else {
                actionTarget.setText("Error ocurred");
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
        return new Scene(new Checkout(app, user));
    }
}

