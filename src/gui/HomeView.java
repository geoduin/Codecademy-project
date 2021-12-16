import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class HomeView extends View {

    public HomeView(GUI baseUI) {
        super(baseUI);
    }

    @Override
    public void createView() {
        // Initial layout setup
        GridPane view = new GridPane();
        view.setAlignment(Pos.CENTER);

        // Buttons
        Button manageBtn = new Button("Manage");
        manageBtn.setOnMouseClicked(clicked -> new ManageView(this.gui).createView());
        Button statisticsBtn = new Button("Statistics");
        statisticsBtn
                .setOnMouseClicked(clicked -> {
                    view.add(new Label("Bestaat nog niet voor de vertical slide :)"), 1, 1);
                });

        // Futher layout setup
        view.add(manageBtn, 0, 0);
        view.add(statisticsBtn, 1, 0);
        view.setPadding(new Insets(40, 0, 0, 0));
        view.setHgap(40);

        activate(view);

    }

}
