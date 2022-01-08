package gui;

import database.DBConnection;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * This class works as the base UI of the application. It contains the layout,
 * of which the center will be changed to other views, while containing a menu
 * at the top.
 */
public class GUI extends Application {
    private BorderPane layout;
    protected Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        // layout starting setup
        this.layout = new BorderPane();

        // menu
        Button homeBtn = new Button("Home");
        homeBtn.setOnMouseClicked(clicked -> new HomeView(this).createView());
        Button exitBtn = new Button("Exit");
        exitBtn.setOnMouseClicked(clicked -> System.exit(1));
        HBox menu = new HBox();
        menu.setPadding(new Insets(0, 0, 150, 0));
        menu.setSpacing(15);
        menu.setAlignment(Pos.CENTER);
        menu.getChildren().add(homeBtn);
        menu.getChildren().add(exitBtn);

        // Stage setup
        this.layout.setBottom(menu);
        new HomeView(this).createView();
        Scene scene = new Scene(this.layout);
        scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
        new DBConnection();
    }

    /**
     * Can receive any pane to act as a new view on screen. Center is only set to
     * the view, so that the menu remains visible at the top on all pages
     */
    public void goToNext(Parent view, String windowTitle) {
        this.layout.setCenter(view);
        this.stage.setTitle(windowTitle);
    }
}
