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



    public void createWebcast(String title, String speaker, String organization, int duration, String url, String status, String description) { 
        Webcast webcast = new Webcast(title, speaker, organization, duration, url, Status.valueOf(status), description);
        this.repo.insert(webcast);
    }

    public void deleteWebcast(Webcast webcast) { 
        this.repo.delete(webcast);
    }
    //url: String, title: String, description: String, status: Status
    public void editWebcast(String url, String title, String description, Status status) { 
        //creating helper webcast to use WebcastRepository method
        Webcast webcast = new Webcast(title, null, null, -1, url, status, null, description);
        this.repo.update(webcast);
    }

    public void editURL(String initialURL, String newURL) {
        this.repo.updateURL(initialURL, newURL);
    }
    //result set will be empty if the title of a module is given
    public Webcast retrieveByTitle(String title) { 
        return this.repo.retrieveByTitle(title);
    }


    //Ensures that the webcast title is unique as described in the assignment description. See report as to why this is done in Java and not SQL.
    public boolean titleAlreadyExists(String title) { 
        return retrieveWebcastNames().contains(title);
    }



    

    
}
