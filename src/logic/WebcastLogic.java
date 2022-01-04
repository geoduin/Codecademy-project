package logic;

import domain.Webcast;
import domain.Status;
import java.util.ArrayList;
import java.util.HashMap;

import database.WebcastRepository;

public class WebcastLogic {

    private WebcastRepository repo;

    public WebcastLogic() {
        this.repo = new WebcastRepository();
    }

    public ArrayList<String> retrieveWebcastNames() {
        return this.repo.getAllWebcastNames();
    }

    public void createWebcast(String title, String speaker, String organization, int duration, String url,
            String status, String description) {
        Webcast webcast = new Webcast(title, speaker, organization, duration, url, Status.valueOf(status), description);
        this.repo.insert(webcast);
    }

    public void deleteWebcast(Webcast webcast) {
        this.repo.delete(webcast);
    }

    // url: String, title: String, description: String, status: Status
    public void editWebcast(String url, String title, String description, Status status) {
        // creating helper webcast to use WebcastRepository method
        Webcast webcast = new Webcast(title, null, null, -1, url, status, null, description);
        this.repo.update(webcast);
    }

    public void editURL(String initialURL, String newURL) {
        this.repo.updateURL(initialURL, newURL);
    }

    // result set will be empty if the title of a module is given
    public Webcast retrieveByTitle(String title) {
        return this.repo.retrieveByTitle(title);
    }

    // Ensures that the webcast title is unique as described in the assignment
    // description. See report as to why this is done in Java and not SQL.
    public boolean titleAlreadyExists(String title) {
        return retrieveWebcastNames().contains(title);
    }

    //returns true if the webcast was saved to the database.
    public boolean saveSuccessful(String title) {
        Webcast saveTest = this.repo.retrieveByTitle(title);
        if (saveTest == null) {
            return false;
        }
        return true;
    }

    // Takes in all updateable values and checks if the update was successful.
    public boolean updateSuccessful(String title, String description, String url, String status) {
        Webcast updateTest = this.repo.retrieveByTitle(title);
        if (updateTest == null) {
            return false;
            // returns true only if all values from the database match the values in the app
        } else if (updateTest.getTitle().equals(title) && updateTest.getDescription().equals(description)
                && updateTest.getUrl().equals(url) && updateTest.getStatus().toString().equals(status)) {
            return true;
        }
        return false;
    }

}
