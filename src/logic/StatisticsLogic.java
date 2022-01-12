package logic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.border.StrokeBorder;

import database.ModuleRepository;
import database.StatisticsRepository;
import domain.Certificate;
import domain.Course;
import domain.Gender;
import domain.Webcast;

public class StatisticsLogic {

    private StatisticsRepository repo;
    private ModuleRepository moduleRepo = new ModuleRepository();

    public StatisticsLogic() { 
        this.repo = new StatisticsRepository();
    }


    //Retrieves gender statistics as a formatted string
    public String genderStatisticsFormatter(Gender gender) { 

        int percentage = this.repo.retrievePercentageAcquiredCertificates(gender);
        String formattedString = percentage + "%" + " of " + gender + " students who enrolled for a course obtained a certificate.";
        return formattedString;
    
    }
    //returns formatted string showing the average progress per module for a given course.
    public String courseProgressStatisticFormatter(String courseName) { 
        HashMap<Integer, Integer> courseNames =  this.repo.retrieveAverageProgressionPerModule(courseName);
        return moduleFormatter(courseNames);

        
    }
    //returns formatted string showing the progress of a student per module for a given course.
    public String progressOfStudentFormatter(String studentEmail, String courseName) { 
        HashMap<Integer, Integer> courseNames =  this.repo.retrieveProgressionPerModule(studentEmail, courseName);
        return moduleFormatter(courseNames);
    }


    //takes a hashmap of the format <ID, Percentage> and returns a formatted string containing the Module toString() and the progress percentage. 
    private String moduleFormatter(Map<Integer, Integer> map) { 
        Set<Integer> keySet = map.keySet();
        StringBuilder stringBuilder = new StringBuilder();
        //Integer.valueOf(keySet.toArray()[i].toString() is used to avoid having to sort the list again (as this is already done in SQL), since the keySet is always an integer in this method, it shouldn't lead to problems.
        for(int i = 0; i < keySet.size(); i++) { 
            stringBuilder.append("Module: " +this.moduleRepo.retrieveModuleByID(Integer.valueOf(keySet.toArray()[i].toString())) + ", Progress: " + map.get(keySet.toArray()[i]) + "%\n");
        }
        return stringBuilder.toString();
    }

    public String certificateFormatter(ArrayList<Certificate> certificates) { 
        
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < certificates.size(); i++) { 
            builder.append(certificates.get(i).toString() + "\n");
        }
        return builder.toString();
    }

    public ArrayList<Certificate> retrieveCertificates (String studentEmail) { 
        return repo.retrieveStudentCertificates(studentEmail);
    }


    public String top3WebcastFormatted() { 
        List<Webcast> topWebcasts = this.repo.retrieveTop3MostWatchedWebcasts();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < topWebcasts.size(); i++) { 
            stringBuilder.append("Title: " + topWebcasts.get(i).getTitle() + ", Views: " + topWebcasts.get(i).getView() + "\n");
        }
        return stringBuilder.toString();
    }

    public String top3CoursesFormatted() { 
        List<Course> topCourses = this.repo.retrieveTop3CoursesByNumberOfCertificates();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < topCourses.size(); i++) { 
            stringBuilder.append(topCourses.get(i).toString() + "\n");
        }

        return stringBuilder.toString();
    }

    



    
}
