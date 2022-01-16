package logic;

import domain.Webcast;
import domain.Status;

import java.util.ArrayList;

import database.EnrollmentRepository;
import database.WebcastRepository;

public class WebcastLogic {

    private WebcastRepository repo;
    private InputValidation inputValidation;

    public WebcastLogic() {
        this.repo = new WebcastRepository();
        this.inputValidation = new InputValidation();
    }

    public ArrayList<String> retrieveWebcastNames() {
        return this.repo.getAllWebcastNames();
    }

    // Creates webcast and returns string which the GUI uses to show the user wether
    // the save was successful.
    public String createWebcast(String title, String speaker, String organization, int duration, String url,
            Status status, String description, int views) {
        if (titleAlreadyExists(title)) {
            return title + " already exists.";
        } else if (!inputValidation.isValidURL(url)) {
            return url + " is not a valid URL.";
        } else if (urlAlreadyExists(url)) {
            return url + " already exists.";
        }
        this.repo.insert(new Webcast(title, speaker, organization, duration, url, status, description, views, 0));
        if (saveSuccessful(title)) {
            return "Save successful";
        }
        return "Save failed";

    }

    public void deleteWebcast(Webcast webcast) {
        deleteWebcastProgress(webcast);
        this.repo.delete(webcast);
    }

    // edits the webcast and returns a String which the GUI
    // uses to show the user wether the update was successful.
    public String editWebcast(String initialURL, String newURL, String initialTitle, String newTitle,
            String description, Status status) {

        // Checking if the URL is invalid or already exists.

        if (!inputValidation.isValidURL(newURL)) {
            return newURL + " is not a valid URL.";
        } else if (!initialURL.equals(newURL) && urlAlreadyExists(newURL)) {
            return newURL + " already exists.";
        } else if (!initialTitle.equals(newTitle) && titleAlreadyExists(newTitle)) {
            return newTitle + " already exists.";
        } else {
            Webcast webcast = new Webcast(newTitle, null, null, -1, initialURL, status, null, description, -1, 0);
            this.repo.update(webcast);
            this.repo.updateURL(initialURL, newURL);
            if (updateSuccessful(newTitle, description, newURL, status.toString())) {
                return "Update successful.";
            } else {
                return "Update failed.";
            }
        }

    }

    public void editURL(String initialURL, String newURL) {
        this.repo.updateURL(initialURL, newURL);
    }

    public void editViewCount(String url, int newViewCount) {
        this.repo.updateViews(url, newViewCount);
    }

    // result set will be empty if the title of a module is given
    public Webcast retrieveByTitle(String title) {
        return this.repo.retrieveByTitle(title);
    }

    // Ensures that the webcast title is unique as described in the assignment
    // description. See report as to why this is done in Java and not SQL.
    public boolean titleAlreadyExists(String title) {
        if (retrieveByTitle(title) == null) {
            return false;
        }
        return true;
    }

    // Checks if the URL already exists, the repo method will return -1 if no ID is
    // found.
    private boolean urlAlreadyExists(String url) {
        if (this.repo.getIDFromURL(url) == -1) {
            return false;
        }
        return true;
    }

    // returns true if the webcast was saved to the database.
    private boolean saveSuccessful(String title) {
        Webcast saveTest = this.repo.retrieveByTitle(title);
        if (saveTest == null) {
            return false;
        }
        return true;
    }

    // Takes in all updateable values and checks if the update was successful.
    private boolean updateSuccessful(String title, String description, String url, String status) {
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

    public void deleteWebcastProgress(Webcast webcast) {
        new EnrollmentRepository().deleteProgressWithoutWebcast(webcast);
    }

}
