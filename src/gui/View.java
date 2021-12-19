package gui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

//  Class to standardize every view that will sit inside the center of the base
//  layout in the GUI class
public abstract class View {
    protected GUI gui;

    // associating the gui so that its methods can be used to set the center of the
    // base layout
    public View(GUI gui) {
        this.gui = gui;
    }

    // Standard view-layout initiation method, because the same properties are often
    // used.
    protected GridPane generateGrid() {
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);

        grid.setHgap(40);
        grid.setVgap(10);

        return grid;
    }

    // every view needs to create a parent type that can sit in the center of the
    // base layout
    public abstract void createView();

    // method to add any type of view to the center of the main base layout + giving
    // the window a new title
    public void activate(Parent view, String windowTitle) {
        this.gui.goToNext(view, windowTitle);
    }

}
