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

public class FrequentUserReport extends GridPane {
    private final Main app;


    private RadioButton option1, option2, option3;

    private ObservableList<String> janViewList, febViewList;
    private Integer selected;

    public FrequentUserReport(Main app) {
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

        Label jan = new Label("January");
        Label feb = new Label("February");
        add(jan, 0, 2);
        add(feb, 0, 3);

        Label username = new Label("Username Number of Checkouts");
        // Label checkouts = new Label("Number of Checkouts");
        add(username, 1, 1);
        // add(checkouts, 2, 1);

        ArrayList<ArrayList<String>> qResult = Database.frequentUsersReport();
        ArrayList<String> janList = qResult.get(0);
        ArrayList<String> febList = qResult.get(1);



        janViewList = FXCollections.observableArrayList(janList);
        febViewList = FXCollections.observableArrayList(febList);
        ListView<String> janView = new ListView<String>(janViewList);
        ListView<String> febView = new ListView<String>(febViewList);
        janView.setPrefHeight(janList.size()*30 + 2);
        febView.setPrefHeight(febList.size()*30 + 2);
        add(janView, 1, 2);
        add(febView, 1, 3);

        Button back = new Button("Back");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.CENTER);
        hbBtnBack.getChildren().add(back);
        hbBtnBack.setMargin(back, new Insets(30, 0, 0, 0));
        add(hbBtnBack, 0, 4);
    }

    public static Scene makeScene(Main app) {
        return new Scene(new FrequentUserReport(app));
    }


}

