package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

//Class responsible to give the user the choice to go into course or module management
public class ManageView extends View {

    public ManageView(GUI baseUI) {
        super(baseUI);
    }

    // Only one view necessary to show the users the available management options.
    // Method. Makes a pathway to both.
    @Override
    public void createView() {
        // setting the layout of the view
        GridPane view = generateGrid();

        // Buttons including the action events
        Button courseManageBtn = new Button("Course");
        courseManageBtn
                .setOnMouseClicked(clicked -> {
                    view.add(new Label("Not available yet!"), 0, 1);
                });
        Button moduleManageBtn = new Button("Module");
        moduleManageBtn.setOnMouseClicked(clicked -> new ModuleManagementView(this.gui).createView());

        // Futher layout setup
        view.add(courseManageBtn, 0, 0);
        view.add(moduleManageBtn, 1, 0);
        view.setPadding(new Insets(40, 0, 0, 0));
        view.setHgap(40);

        activate(view, "Manage");
    }

}
