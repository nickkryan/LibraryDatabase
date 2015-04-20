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

public class TrackBook extends GridPane {
    private final Main app;

    private TextField isbn, floorNum, shelfNum, aisleNum, subject;

    public TrackBook(Main app) {
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


        Button locate = new Button("Locate");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(locate);
        hbBtn2.setMargin(locate, new Insets(0, 0, 0, 0));
        add(hbBtn2, 2, 1, 2, 1);

        Button back = new Button("Back");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.CENTER);
        hbBtnBack.getChildren().add(back);
        hbBtnBack.setMargin(back, new Insets(15, 0, 0, 0));
        add(hbBtnBack, 0, 4, 2, 1);
    }

    public static Scene makeScene(Main app) {
        return new Scene(new TrackBook(app));
    }
}

