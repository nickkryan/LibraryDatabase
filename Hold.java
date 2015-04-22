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
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Paint;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import java.util.ArrayList;

public class Hold extends GridPane {
    private final Main app;


    private RadioButton option1, option2, option3;
    private TextField issue_id, holdReqDate, estRetDate;
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

        for (int i = 0; i < books.size(); i++) {
            int temp = books.get(i).toString().length();
            if (temp > longestString) {
                longestString = temp;
            }
            if (books.get(i).isReserved()) {
                reserved.add(books.get(i));
            }
        }
        books.removeAll(reserved);

        availableBooks = FXCollections.observableArrayList(books);
        ListView<Book> listView = new ListView<Book>(availableBooks);
        add(listView, 0, 1, 4, 1);
        listView.setPrefHeight(books.size()*30 + 2);
        listView.setPrefWidth(longestString*7);

        Label holdRequestDate = new Label("Hold Request Date");
        add(holdRequestDate, 0, 2);

        holdReqDate = new TextField();
        holdReqDate.setEditable(false);
        add(holdReqDate, 1, 2);

        Label estReturnDate = new Label("Estimated Return Date");
        add(estReturnDate, 2, 2);

        estRetDate = new TextField();
        estRetDate.setEditable(false);
        add(estRetDate, 3, 2);

        Button back = new Button("Back");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.CENTER);
        hbBtnBack.getChildren().add(back);
        hbBtnBack.setMargin(back, new Insets(30, 0, 0, 0));
        back.setOnAction((ActionEvent e) -> app.changeScene(SearchBooks.makeScene(app, username)));
        add(hbBtnBack, 0, 3);

        Button submit = new Button("Submit");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(submit);
        hbBtn2.setMargin(submit, new Insets(30, 0, 0, 18));
        add(hbBtn2, 1, 3);

        Button close = new Button("Close");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.CENTER);
        hbBtn3.getChildren().add(close);
        hbBtn3.setMargin(close, new Insets(30, 0, 0, 0));
        add(hbBtn3, 2, 3);
        close.setOnAction((ActionEvent e) -> {System.exit(0);});

        Label issueIdLabel = new Label("Issue ID");
        issue_id = new TextField();
        issue_id.setEditable(false);
        HBox hbIssue_ID = new HBox();
        hbIssue_ID.setAlignment(Pos.CENTER);
        hbIssue_ID.getChildren().addAll(issueIdLabel, issue_id);
        hbIssue_ID.setMargin(issue_id, new Insets(0,0,0,18));
        add(hbIssue_ID, 0, 4, 4, 1);

        Label reservedDivide = new Label("Reserved Books\n");
        HBox divide = new HBox();
        divide.setAlignment(Pos.CENTER);
        divide.getChildren().add(reservedDivide);
        divide.setMargin(reservedDivide, new Insets(15, 0, 15, 0));
        add(divide, 0, 5, 4, 1);

        reservedBooks = FXCollections.observableArrayList(reserved);
        ListView<Book> reservedView = new ListView<>(reservedBooks);
        reservedView.setPrefHeight(reserved.size()*30 + 2);
        reservedView.setPrefWidth(longestString*7);
        add(reservedView, 0, 6, 4, 1);

        final Text actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);
        add(actionTarget, 1, 7, 2, 1);

        submit.setOnAction((ActionEvent e) -> {
            selected = listView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                actionTarget.setText("Please select a book");
            } else {
                String copyNum = Database.copyNumOfHoldRequest(String.valueOf(selected.getIsbn()));
                int id = -1;
                if (copyNum != null) {
                    id = Database.requestHoldUpdateDb(String.valueOf(selected.getIsbn()), copyNum, username);
                }
                if (id > -1) {
                    actionTarget.setText("");
                    actionTarget.setText("Copy number " + copyNum + " hold request submitted.");
                    holdReqDate.setText(LocalDateTime.now().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")));
                    estRetDate.setText(LocalDateTime.now().plusDays(17).format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")));
                    issue_id.setText("" + id);
                } else if (id == -2) {
                    actionTarget.setText("Debarred user!");
                } else {
                    actionTarget.setText("Hold request failed.");
                }
                selected.setNumAvailableCopies(String.valueOf(Integer.parseInt(selected.getNumAvailableCopies()) - 1));
            }
        });
    }

    public static Scene makeScene(Main app, String user, ArrayList<Book> books) {
        return new Scene(new Hold(app, user, books));
    }

}

