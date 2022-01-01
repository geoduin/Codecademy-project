package logic;
import domain.Webcast;
import domain.Status;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import database.WebcastRepository;

public class WebcastLogic {

    private WebcastRepository repo;

    public WebcastLogic() {
        this.repo = new WebcastRepository();
    }

    public HashMap<String, String> retrieveWebcasts() {
        return this.repo.getAllWebcastNamesAndURL();
    }

    public ArrayList<String> retrieveWebcastNames() {
        HashMap<String, String> webcastMap = this.repo.getAllWebcastNamesAndURL();
        ArrayList<String> webcastNames = new ArrayList<>();
        for(String key : webcastMap.keySet()) { 
            webcastNames.add(webcastMap.get(key));
        }
        return webcastNames;
        
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
    //result set will be empty if the title of a module is given
    public Webcast retrieveByTitle(String title) { 
        return this.repo.retrieveByTitle(title);
    }

    
}
