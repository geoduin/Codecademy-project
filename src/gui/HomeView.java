package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

//Class is responsible for creating the first view of the application (home)
class HomeView extends View {

    HomeView(GUI baseUI) {
        super(baseUI);
    }

    @Override
    protected void createView() {
        // Initial layout setup
        GridPane view = generateGrid();

        // Welcoming the user
        Label welcomeLabel = new Label("Welcome.");
        welcomeLabel.setId("title");
        welcomeLabel.setFont(Font.font("Comic Sans MS", 30));

        // Option buttons for the user to go to functionality of the application
        Button manageBtn = new Button("Manage");
        manageBtn.setId("ManageButton");
        Button statisticsBtn = new Button("Statistics");
        statisticsBtn.setId("StatBtn");
        statisticsBtn
                .setOnMouseClicked(clicked -> {
                    new StatisticsViews(this.gui).createView();
                });
        manageBtn.setOnMouseClicked(clicked -> new ManageView(this.gui).createView());

        // Futher layout setup
        view.add(welcomeLabel, 1, 0);
        view.add(manageBtn, 0, 1);
        view.add(statisticsBtn, 2, 1);

        view.setPadding(new Insets(40, 0, 0, 0));

        activate(view,
                "Jascha van der Ark (2182194), Jef Koldenhof (2187834), Mohammed Bogatyrev (2182116), Xin Wang (2154458)");

    }

}
