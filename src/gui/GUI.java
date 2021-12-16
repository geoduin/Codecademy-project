import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/* 
This class works as the base UI of the application. It contains the layout, of which the center 
will be changed to other views, while containing a menu in the top. 
*/
public class GUI extends Application {
    private BorderPane layout;

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();

    }

    /*
     * 
     * Can receive any pane to act as a new view on screen. Center is only set to
     * the view, so that the menu remains
     */
    public void goToNext(Parent view) {
        this.layout.setCenter(view);
    }
}
