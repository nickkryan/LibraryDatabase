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

import java.util.ArrayList;

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

        final Text actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);
        add(actionTarget, 0, 4, 2, 1);

        Button menu = new Button("Menu");
        menu.setOnAction(e -> app.changeScene(MainMenu.makeScene(app, user)));

        Button search = new Button("Search");
        search.setOnAction(e -> {
            ArrayList<Book> bookResults = new ArrayList<Book>();
            if (!isbn.getText().equals("")) {
                try {
                    Book bookByIsbn = null;
                    if (Integer.parseInt(isbn.getText()) > 0) {
                        bookByIsbn = Database.searchISBN(isbn.getText());
                        if (bookByIsbn == null) {
                            actionTarget.setText("Invalid ISBN");
                        } else {
                            bookResults.add(bookByIsbn);
                        }
                    } else {
                        actionTarget.setText("Invalid ISBN");
                    }
                } catch (Exception f) {
                    actionTarget.setText("Invalid ISBN");
                }
            } else if (!title.getText().equals("")) {
                try {
                    String titleQuery = title.getText();
                    if (!titleQuery.trim().equals("")) {
                        bookResults = Database.searchBookTitles(title.getText());
                        
                    } else {
                        actionTarget.setText("Invalid Book Title Search Query");
                    }
                } catch (Exception f) {
                    actionTarget.setText("Invalid Book Title Search Query");
                }

            } else if (!author.getText().equals("")) {
                try {
                    String authorQuery = author.getText();
                    if (!authorQuery.trim().equals("")) {
                        bookResults = Database.searchAuthors(author.getText());
                    } else {
                        actionTarget.setText("Invalid Author Search Query");
                    }
                } catch (Exception f) {
                    actionTarget.setText("Invalid Author Search Query");
                }
            }
            app.changeScene(Hold.makeScene(app, user, bookResults));
        });

        Button close = new Button("Close");
        close.setOnAction(e -> System.exit(0));

        HBox test = new HBox(15);
        test.getChildren().addAll(menu, search, close);
        test.setMargin(menu, new Insets(0, 0, 0, 20));
        add(test, 0, 5, 3, 1);
    }

    public static Scene makeScene(Main app, String user) {
        return new Scene(new SearchBooks(app, user));
    }
}

