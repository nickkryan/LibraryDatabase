import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Paint;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;

import java.util.ArrayList;

public class PopularBookReport extends GridPane {
    private final Main app;

    private Label title1, title2, title3,
            checkout1, checkout2, checkout3,
            title4, title5, title6,
            checkout4, checkout5, checkout6;


    public PopularBookReport(Main app, String user) {
        this.app = app;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(15, 15, 15, 15));

        Label mainTitle = new Label("Popular Books Report");
        HBox hbMainTitle = new HBox();
        hbMainTitle.setAlignment(Pos.CENTER);
        hbMainTitle.getChildren().add(mainTitle);
        add(hbMainTitle, 0, 0, 3, 1);

        Label month = new Label("Month");
        Label title = new Label("Title");
        Label checkout = new Label("Checkouts");
        Label jan = new Label("January");
        Label feb = new Label("February");

        Label filler1 = new Label("");
        Label filler2 = new Label("");
        Label filler3 = new Label("");
        Label filler4 = new Label("");

        VBox vbMonth = new VBox();
        vbMonth.setAlignment(Pos.CENTER);
        vbMonth.getChildren().addAll(month, jan, filler1, filler2,
                                     feb, filler3, filler4);
        add(vbMonth, 0, 1);
        title1 = new Label("");
        title2 = new Label("");
        title3 = new Label("");
        title4 = new Label("");
        title5 = new Label("");
        title6 = new Label("");
        ArrayList<Label> titles = new ArrayList<>();
        titles.add(title1);
        titles.add(title2);
        titles.add(title3);
        titles.add(title4);
        titles.add(title5);
        titles.add(title6);


        VBox vbTitle = new VBox();
        vbTitle.setAlignment(Pos.CENTER);
        vbTitle.getChildren().addAll(title, title1, title2, title3,
                                     title4, title5, title6);

        checkout1 = new Label("");
        checkout2 = new Label("");
        checkout3 = new Label("");
        checkout4 = new Label("");
        checkout5 = new Label("");
        checkout6 = new Label("");
        ArrayList<Label> checkouts = new ArrayList<>();
        checkouts.add(checkout1);
        checkouts.add(checkout2);
        checkouts.add(checkout3);
        checkouts.add(checkout4);
        checkouts.add(checkout5);
        checkouts.add(checkout6);

        VBox vbCheckout = new VBox();
        vbCheckout.setAlignment(Pos.CENTER);
        vbCheckout.getChildren().addAll(checkout, checkout1, checkout2, checkout3,
                                     checkout4, checkout5, checkout6);

        add(vbTitle, 2, 1);
        add(vbCheckout, 3, 1);

        Button back = new Button("Back");
        HBox hbBtnBack = new HBox(10);
        hbBtnBack.setAlignment(Pos.CENTER);
        hbBtnBack.getChildren().add(back);
        hbBtnBack.setMargin(back, new Insets(15, 0, 0, 0));
        add(hbBtnBack, 0, 3, 2, 1);
        back.setOnAction(e -> app.changeScene(MainMenu.makeScene(app, user)));

        ArrayList<ArrayList<ArrayList<String>>> qResult = Database.popularBookReport();
        ArrayList<ArrayList<String>> janList = qResult.get(0);
        for (int i = 0; i < Math.min(janList.size(), 3); i++) {
            titles.get(i).setText(janList.get(i).get(0));
            checkouts.get(i).setText(janList.get(i).get(1));
        }
        ArrayList<ArrayList<String>> febList = qResult.get(1);
        for (int i = 0; i < Math.min(febList.size(), 3); i++) {
            titles.get(i + 3).setText(febList.get(i).get(0));
            checkouts.get(i + 3).setText(febList.get(i).get(1));
        }
    }

    public static Scene makeScene(Main app, String user) {
        return new Scene(new PopularBookReport(app, user));
    }
}

