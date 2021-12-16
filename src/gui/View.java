import javafx.scene.Parent;

/**
 * Class to standardize every view that will sit inside the center of the base
 * layout in the GUI class
 */
public abstract class View {
    protected GUI gui;

    // associating the gui so that its methods can be used to set the center of the
    // base layout
    public View(GUI gui) {
        this.gui = gui;
    }

    // every view needs to create a parent type that can sit in the center of the
    // base layout
    public abstract void createView();

    // setting the created view in the base layout center
    public void activate(Parent view) {
        this.gui.goToNext(view);
    }

}
