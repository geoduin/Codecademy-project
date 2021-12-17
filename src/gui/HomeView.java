package gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class HomeView extends View {

    public HomeView(GUI baseUI) {
        super(baseUI);
    }

    @Override
    public void createView() {
        // Initial layout setup
        GridPane view = new GridPane();
        view.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome.");
        welcomeLabel.setFont(Font.font("Comic Sans MS", 30));

        // Buttons
        Button manageBtn = new Button("Manage");
        manageBtn.setOnMouseClicked(clicked -> new ManageView(this.gui).createView());
        Button statisticsBtn = new Button("Statistics");
        statisticsBtn
                .setOnMouseClicked(clicked -> {
                    view.add(new Label("Bestaat nog niet voor deze vertical slice :)"), 2, 2);
                });
        Button exitBtn = new Button("Exit");
        exitBtn.setOnMouseClicked(clicked -> System.exit(1));

        // Futher layout setup
        view.add(welcomeLabel, 1, 0);
        view.add(manageBtn, 0, 1);
        view.add(statisticsBtn, 2, 1);
        view.add(exitBtn, 1, 2);
        view.setHalignment(exitBtn, HPos.CENTER);
        view.setPadding(new Insets(40, 0, 0, 0));
        view.setHgap(40);
        view.setVgap(20);

        activate(view,
                "Jascha van der Ark (2182194), Jef Koldenhof (2187834), Mohammed Bogatyrev (2182116), Xin Wang (2154458)");

    }

}
