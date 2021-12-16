import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ModuleManagementView extends View {

    public ModuleManagementView(GUI baseUI) {
        super(baseUI);
    }

    @Override
    public void createView() {
        // This is a module form
        // Initial layout setup

        VBox view = new VBox();

        // text fields
        Label nameModule = new Label("Titel:");
        TextField moduleField = new TextField();

        Label version = new Label("Version:");
        TextField versionField = new TextField();

        Label trackingNumber = new Label("Trackingnumber:");
        TextField trckField = new TextField();

        Label description = new Label("Description:");
        TextField descriptionField = new TextField();

        Label contact = new Label("Contactnaam:");
        TextField contactField = new TextField();

        Label contactEmail = new Label("ContactEmail");
        TextField contactEmField = new TextField();
        // Buttons
        Button create = new Button("Create module");
        create.setOnMouseClicked((event) -> {

        });
        // Futher layout setup
        view.getChildren().addAll(nameModule, moduleField, version, versionField, trackingNumber, trckField,
                description,
                descriptionField,
                contact, contactField, contactEmail, contactEmField, create);
        activate(view);

    }

}
