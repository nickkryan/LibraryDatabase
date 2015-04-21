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

public class PopularReport extends GridPane {
    private final Main app;

    private Label title1, title2, title3,
            checkout1, checkout2, checkout3,
            title4, title5, title6,
            checkout4, checkout5, checkout6;


    public PopularReport(Main app) {
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
        checkout1 = new Label("");
        checkout2 = new Label("");
        checkout3 = new Label("");
        checkout4 = new Label("");
        checkout5 = new Label("");
        checkout6 = new Label("");
        VBox vbTitle = new VBox();
        vbTitle.setAlignment(Pos.CENTER);
        vbTitle.getChildren().addAll(title, title1, title2, title3,
                                     title4, title5, title6);

        VBox vbCheckout = new VBox();
        vbCheckout.setAlignment(Pos.CENTER);
        vbCheckout.getChildren().addAll(checkout, checkout1, checkout2, checkout3,
                                     checkout4, checkout5, checkout6);

        // add(title1, 2, 2);
        // add(title2, 2, 3);
        // add(title3, 2, 4);
        // add(title4, 2, 5);
        // add(title5, 2, 6);
        // add(title6, 2, 7);
        add(vbTitle, 1, 1);
        add(vbCheckout, 2, 1);
        // add(checkout1, 3, 2);
        // add(checkout2, 3, 3);
        // add(checkout3, 3, 4);
        // add(checkout4, 3, 5);
        // add(checkout5, 3, 6);
        // add(checkout6, 3, 7);


    }

    public static Scene makeScene(Main app) {
        return new Scene(new PopularReport(app));
    }
}

