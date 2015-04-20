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

public class SearchBooks extends GridPane {
    private final Main app;
    private final String user;

    private TextField isbn, title, author;

    public SearchBooks(Main app, String user) {
        this.app = app;
        this.user = user;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label mainTitle = new Label("Search Books");
        HBox hbMainTitle = new HBox();
        hbMainTitle.setAlignment(Pos.CENTER);
        hbMainTitle.getChildren().add(mainTitle);
        add(hbMainTitle, 0, 0, 3, 1);

        Label isbnLabel = new Label("ISBN");
        isbn = new TextField();
        add(isbnLabel, 0, 1);
        add(isbn, 1, 1);

        Label titleLabel = new Label("Title");
        title = new TextField();
        add(titleLabel, 0, 2);
        add(title, 1, 2);

        Label authorLabel = new Label("Author");
        author = new TextField();
        add(authorLabel, 0, 3);
        add(author, 1, 3);


        Button back = new Button("Back");

        Button search = new Button("Search");

        Button close = new Button("Close");

        HBox test = new HBox(15);
        test.getChildren().addAll(back, search, close);
        test.setMargin(back, new Insets(0, 0, 0, 20));
        add(test, 0, 4, 3, 1);
    }

    public static Scene makeScene(Main app, String user) {
        return new Scene(new SearchBooks(app, user));
    }
}

