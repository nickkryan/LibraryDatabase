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

public class Extension extends GridPane {
    private final Main app;

    private TextField issue_id, checkoutDate, currExtension, newExtension,
        currReturn, estReturn;
    String id_value, newDate = null;

    public Extension(Main app, String user) {
        this.app = app;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label mainTitle = new Label("Request Extension on a Book");
        HBox hbMainTitle = new HBox();
        hbMainTitle.setAlignment(Pos.CENTER);
        hbMainTitle.getChildren().add(mainTitle);
        add(hbMainTitle, 0, 0, 4, 1);

        Label idLabel = new Label("Enter your issue_id");
        issue_id = new TextField();

        add(idLabel, 0, 1);
        add(issue_id, 1, 1);

        Label checkoutDateLabel = new Label("Original Checkout Date");
        checkoutDate = new TextField();
        checkoutDate.setEditable(false);
        add(checkoutDateLabel, 0, 2);
        add(checkoutDate, 1, 2);

        Label currExtensionLabel = new Label("Current Extension Date");
        currExtension = new TextField();
        currExtension.setEditable(false);
        add(currExtensionLabel, 0, 3);
        add(currExtension, 1, 3);

        Label newExtensionLabel = new Label("New Extension Date");
        newExtension = new TextField();
        newExtension.setEditable(false);
        add(newExtensionLabel, 0, 4);
        add(newExtension, 1, 4);

        Label currReturnLabel = new Label("Current Return Date");
        currReturn = new TextField();
        currReturn.setEditable(false);
        add(currReturnLabel, 2, 3);
        add(currReturn, 3, 3);

        Label estReturnLabel = new Label("New Estimated Return Date");
        estReturn = new TextField();
        estReturn.setEditable(false);
        add(estReturnLabel, 2, 4);
        add(estReturn, 3, 4);

        final Text actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);
        add(actionTarget, 3, 2);


        Button submit = new Button("Submit");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(submit);
        hbBtn2.setMargin(submit, new Insets(0, 0, 20, 0));
        add(hbBtn2, 3, 1, 2, 1);

        submit.setOnAction(e -> {
            String[] info = Database.requestExtension(issue_id.getText());
            if (info == null) {
                actionTarget.setText("Invalid Issue_ID.\nBook may not be checked out");
            } else {
                checkoutDate.setText(info[0]);
                currExtension.setText(info[1]);
                currReturn.setText(info[2]);
                newExtension.setText(info[3]);
                estReturn.setText(info[4]);
                newDate = info[4];
                id_value = issue_id.getText();
            }
        });

        Button submit2 = new Button("Submit");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.CENTER);
        hbBtn3.getChildren().add(submit2);
        hbBtn3.setMargin(submit2, new Insets(30, 0, 0, 0));
        add(hbBtn3, 3, 5, 2, 1);

        submit2.setOnAction(e -> {
            if (newDate != null) {
                Database.updateExtensionDate(id_value, newDate);
            }
        });

        Button back = new Button("Back");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.CENTER);
        hbBtnBack.getChildren().add(back);
        hbBtnBack.setMargin(back, new Insets(30, 0, 0, 0));
        add(hbBtnBack, 0, 5, 2, 1);
        back.setOnAction(e -> app.changeScene(MainMenu.makeScene(app, user)));
    }

    public static Scene makeScene(Main app, String user) {
        return new Scene(new Extension(app, user));
    }
}

