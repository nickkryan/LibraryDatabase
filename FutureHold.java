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

public class FutureHold extends GridPane {
    private final Main app;

    private TextField isbn, copyNumber, expAvailableDate;

    public FutureHold(Main app, String user) {
        this.app = app;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label mainTitle = new Label("Future Hold Request for a Book");
        HBox hbMainTitle = new HBox();
        hbMainTitle.setAlignment(Pos.CENTER);
        hbMainTitle.getChildren().add(mainTitle);
        add(hbMainTitle, 0, 0, 3, 1);

        Label isbnLabel = new Label("ISBN");
        isbn = new TextField();
        add(isbnLabel, 0, 1);
        add(isbn, 1, 1);

        Label copyNumberLabel = new Label("Copy Number");
        copyNumber = new TextField();
        copyNumber.setEditable(false);
        add(copyNumberLabel, 0, 2);
        add(copyNumber, 1, 2);

        Label expAvailableDateLabel = new Label("Expected Available Date");
        expAvailableDate = new TextField();
        expAvailableDate.setEditable(false);
        add(expAvailableDateLabel, 0, 3);
        add(expAvailableDate, 1, 3);

        final Text actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);
        add(actionTarget, 1, 4, 2, 1);

        Button request = new Button("Request");
        request.setOnAction(e -> {
            String isbnNum = isbn.getText();
            try {
                if (!isbn.equals("") && Integer.parseInt(isbnNum) > 0) {
                    String[] ans = Database.getFutureHoldRequest(isbnNum);
                    if (ans != null) {
                        copyNumber.setText(ans[0]);
                        expAvailableDate.setText(ans[1].split(" ")[0]);
                        if (Database.setFutureHoldRequest(user, isbnNum, ans[0])) {
                            actionTarget.setText("Future Hold request successful!");
                        } else {
                            actionTarget.setText("Unable to place future hold request");
                        }
                    } else {
                        copyNumber.setText("");
                        expAvailableDate.setText("");
                        actionTarget.setText("No book found");
                    }
                } else {
                    copyNumber.setText("");
                    expAvailableDate.setText("");
                    actionTarget.setText("Invalid ISBN");
                }
            } catch (Exception f) {
                copyNumber.setText("");
                expAvailableDate.setText("");
                actionTarget.setText("Invalid ISBN");
            }
        });
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(request);
        // hbBtn2.setMargin(request, new Insets(0, 0, 20, 0));
        add(hbBtn2, 2, 1);

        Button back = new Button("Back");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.CENTER);
        hbBtnBack.getChildren().add(back);
        hbBtnBack.setMargin(back, new Insets(30, 0, 0, 0));
        add(hbBtnBack, 0, 4);
        back.setOnAction(e -> app.changeScene(MainMenu.makeScene(app, user)));

    }

    public static Scene makeScene(Main app, String user) {
        return new Scene(new FutureHold(app, user));
    }
}

