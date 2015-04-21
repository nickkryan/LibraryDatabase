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

public class TrackBook extends GridPane {
    private final Main app;

    private TextField isbn, floorNum, shelfNum, aisleNum, subject;

    public TrackBook(Main app, String user) {
        this.app = app;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label mainTitle = new Label("Track Book Location");
        HBox hbMainTitle = new HBox();
        hbMainTitle.setAlignment(Pos.CENTER);
        hbMainTitle.getChildren().add(mainTitle);
        add(hbMainTitle, 0, 0, 4, 1);

        Label isbnLabel = new Label("ISBN");
        isbn = new TextField();
        add(isbnLabel, 0, 1);
        add(isbn, 1, 1);

        Label floorNumLabel = new Label("Floor Number");
        floorNum = new TextField();
        floorNum.setEditable(false);
        add(floorNumLabel, 0, 2);
        add(floorNum, 1, 2);

        Label aisleNumLabel = new Label("Aisle Number");
        aisleNum = new TextField();
        aisleNum.setEditable(false);
        add(aisleNumLabel, 0, 3);
        add(aisleNum, 1, 3);

        Label shelfNumLabel = new Label("Shelf Number");
        shelfNum = new TextField();
        shelfNum.setEditable(false);
        add(shelfNumLabel, 2, 2);
        add(shelfNum, 3, 2);

        Label subjectLabel = new Label("Subject");
        subject = new TextField();
        subject.setEditable(false);
        add(subjectLabel, 2, 3);
        add(subject, 3, 3);

        final Text actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);
        add(actionTarget, 2, 4);


        Button locate = new Button("Locate");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(locate);
        hbBtn2.setMargin(locate, new Insets(0, 0, 0, 0));
        locate.setOnAction(e -> {
            
            if (!isbn.getText().equals("") && Integer.parseInt(isbn.getText()) > 0) {
                String[] info = Database.trackLocation(isbn.getText());
                shelfNum.setText(info[0]);
                subject.setText(info[1]);
                aisleNum.setText(info[2]);
                floorNum.setText(info[3]);
                actionTarget.setText("");
            } else if (isbn.getText().equals("")) {
                actionTarget.setText("Need book isbn!");
                shelfNum.setText("");
                subject.setText("");
                aisleNum.setText("");
                floorNum.setText("");
            } else {
                actionTarget.setText("Invalid isbn!");
                shelfNum.setText("");
                subject.setText("");
                aisleNum.setText("");
                floorNum.setText("");
            }

        });
        add(hbBtn2, 2, 1, 2, 1);

        Button back = new Button("Back");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.CENTER);
        hbBtnBack.getChildren().add(back);
        hbBtnBack.setMargin(back, new Insets(15, 0, 0, 0));
        back.setOnAction(e -> {
            app.changeScene(MainMenu.makeScene(app, user));
        });
        add(hbBtnBack, 0, 4, 2, 1);
        back.setOnAction(e -> app.changeScene(MainMenu.makeScene(app, user)));

    }

    public static Scene makeScene(Main app, String user) {
        return new Scene(new TrackBook(app, user));
    }
}

