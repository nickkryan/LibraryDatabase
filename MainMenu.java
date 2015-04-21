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

public class MainMenu extends GridPane {
    private final Main app;
    private final String user;

    private TextField isbn, title, author;

    public MainMenu(Main app, String user) {
        this.app = app;
        this.user = user;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label mainTitle = new Label(user + "'s Main Menu");
        HBox hbMainTitle = new HBox();
        hbMainTitle.setAlignment(Pos.CENTER);
        hbMainTitle.getChildren().add(mainTitle);
        add(hbMainTitle, 0, 0, 2, 1);


        Button search = new Button("Search");
        search.setOnAction(e -> app.changeScene(SearchBooks.makeScene(app, user)));
        Button extension = new Button("Request Extension");
        extension.setOnAction(e -> app.changeScene(Extension.makeScene(app, user)));
        Button futureHold = new Button("Future Hold Request");
        futureHold.setOnAction(e -> app.changeScene(FutureHold.makeScene(app, user)));
        Button track = new Button("Track Location");
        track.setOnAction(e -> app.changeScene(TrackBook.makeScene(app, user)));
        Button checkout = new Button("Checkout");
        checkout.setOnAction(e -> app.changeScene(Checkout.makeScene(app, user)));
        Button returnBook = new Button("Return Book");
        returnBook.setOnAction(e -> app.changeScene(ReturnBook.makeScene(app, user)));

        Button lostDamaged = new Button("Lost/Damaged");
        lostDamaged.setOnAction(e -> app.changeScene(LostDamaged.makeScene(app, user)));
        Button damReport = new Button("Damaged Report");
        damReport.setOnAction(e -> app.changeScene(DamagedReport.makeScene(app, user)));
        Button popReport = new Button("Popular Book Report");
        popReport.setOnAction(e -> app.changeScene(PopularBookReport.makeScene(app, user)));
        Button userReport = new Button("Frequent User Report");
        userReport.setOnAction(e -> app.changeScene(FrequentUserReport.makeScene(app, user)));
        Button subReport = new Button("Popular Subject Report");
        subReport.setOnAction(e -> app.changeScene(PopularSubjectReport.makeScene(app, user)));

        add(search, 0, 1);
        add(extension, 0, 2);
        add(futureHold, 0, 3);
        add(track, 0, 4);
        add(checkout, 0, 5);
        add(returnBook, 0, 6);

        add(lostDamaged, 1, 1);
        add(damReport, 1, 2);
        add(popReport, 1, 3);
        add(userReport, 1, 4);
        add(subReport, 1, 5);


    }

    public static Scene makeScene(Main app, String user) {
        return new Scene(new MainMenu(app, user));
    }
}

