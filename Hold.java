import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Paint;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;

import java.util.ArrayList;

public class Hold extends GridPane {
    private final Main app;


    private RadioButton option1, option2, option3;

    private ObservableList<Book> availableBooks, reservedBooks;
    private Book selected;

    public Hold(Main app, String username, ArrayList<Book> books) {
        this.app = app;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label mainTitle = new Label("Hold Request for a Book");
        HBox hbMainTitle = new HBox();
        hbMainTitle.setAlignment(Pos.CENTER);
        hbMainTitle.getChildren().add(mainTitle);
        add(hbMainTitle, 0, 0, 4, 1);

        ArrayList<Book> reserved = new ArrayList<>();

        int longestString = 0;
        System.out.println(books.size());
        for (int i = 0; i < books.size(); i++) {
            int temp = books.get(i).toString().length();
            if (temp > longestString) {
                longestString = temp;
            }
            if (books.get(i).isReserved()) {
                System.out.println(books.get(i));
                reserved.add(books.get(i));
            }
        }
        books.removeAll(reserved);
        System.out.println(books.size());

        availableBooks = FXCollections.observableArrayList(books);
        ListView<Book> listView = new ListView<Book>(availableBooks);
        add(listView, 0, 1, 3, 1);
        listView.setPrefHeight(books.size()*30 + 2);
        listView.setPrefWidth(longestString*7);

        reservedBooks = FXCollections.observableArrayList(reserved);
        ListView<Book> reservedView = new ListView<>(reservedBooks);
        reservedView.setPrefHeight(reserved.size()*30 + 2);
        reservedView.setPrefWidth(longestString*7);
        add(reservedView, 0, 4, 3, 1);

        Label reservedDivide = new Label("Reserved Books\n");
        HBox divide = new HBox();
        divide.setAlignment(Pos.CENTER);
        divide.getChildren().add(reservedDivide);
        divide.setMargin(reservedDivide, new Insets(15, 0, 15, 0));
        add(divide, 0, 3, 3, 1);

        Button submit = new Button("Submit");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(submit);
        hbBtn2.setMargin(submit, new Insets(30, 0, 0, 18));
        add(hbBtn2, 1, 2);

        submit.setOnAction((ActionEvent e) -> {
            selected = listView.getSelectionModel().getSelectedItem();
            System.out.println(selected);
        });

        Button close = new Button("Close");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.CENTER);
        hbBtn3.getChildren().add(close);
        hbBtn3.setMargin(close, new Insets(30, 0, 0, 0));
        add(hbBtn3, 2, 2);
        close.setOnAction((ActionEvent e) -> {System.exit(0);});


        Button back = new Button("Back");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.CENTER);
        hbBtnBack.getChildren().add(back);
        hbBtnBack.setMargin(back, new Insets(30, 0, 0, 0));
        back.setOnAction((ActionEvent e) -> app.changeScene(SearchBooks.makeScene(app, username)));
        add(hbBtnBack, 0, 2);
    }

    public static Scene makeScene(Main app, String user, ArrayList<Book> books) {
        return new Scene(new Hold(app, user, books));
    }
}

