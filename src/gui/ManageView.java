package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ManageView extends View {

    public ManageView(GUI baseUI) {
        super(baseUI);
    }

    @Override
    public void createView() {
        GridPane view = new GridPane();
        view.setAlignment(Pos.CENTER);

        // Buttons
        Button courseManageBtn = new Button("Course");
        Button moduleManageBtn = new Button("Module");

        moduleManageBtn.setOnMouseClicked(clicked -> new ModuleManagementView(this.gui).createView());
        courseManageBtn.setOnMouseClicked(clicked -> new CourseManageView(this.gui).createView());
        // Futher layout setup
        view.add(courseManageBtn, 0, 0);
        view.add(moduleManageBtn, 1, 0);
        view.setPadding(new Insets(40, 0, 0, 0));
        view.setHgap(40);

        activate(view, "Manage");
    }

}
