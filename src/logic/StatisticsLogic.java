package logic;
import java.util.List;
import database.ModuleRepository;
import database.StatisticsRepository;
import domain.Certificate;
import domain.Course;
import domain.Gender;
import domain.Webcast;

public class StatisticsLogic {

    private StatisticsRepository repo;
    private ModuleRepository moduleRepo; 

    public StatisticsLogic() { 
        this.repo = new StatisticsRepository();
        this.moduleRepo = new ModuleRepository();
    }


    //Used to display gender statistics on the gui.
    public String genderStatisticsFormatter(Gender gender) { 
        int percentage = this.repo.retrievePercentageAcquiredCertificates(gender);
        if(percentage == -1) { 
            return "No " + gender.toString().toLowerCase() + " student has enrolled for a course.";
            
        }
        String formattedString = percentage + "%" + " of " + gender.toString().toLowerCase() + " students who enrolled for a course obtained a certificate.";
        return formattedString;
    }
    //Returns formatted string showing the average progress per module for a given course.
    //The int[] is in the format [ID, Percentage]
    public String courseProgressStatisticFormatter(String courseName) { 
        List<int[]> courseAndPercentage = this.repo.retrieveAverageProgressionPerModule(courseName);
        return moduleFormatter(courseAndPercentage);

        
    }
    //Returns formatted string showing the progress of a student per module for a given course.
    //The int[] is in the format [ID, Percentage]
    public String progressOfStudentFormatter(String studentEmail, String courseName) { 
        List<int[]> courseNames =  this.repo.retrieveProgressionPerModule(studentEmail, courseName);
        return moduleFormatter(courseNames);
    }


    //Takes a List of the type int[id][percentage] and returns a formatted string of the module 
    //A StringBuilder is used since a string is non-editable. 
    private String moduleFormatter(List<int[]> idPercentage) { 
        StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < idPercentage.size(); i++) { 
                stringBuilder.append(moduleRepo.retrieveModuleByID(idPercentage.get(i)[0]) + ", progress: " + idPercentage.get(i)[1] + "%\n");
            }
        return stringBuilder.toString();
    }
    //Formats the certificates as a single string.
    //A StringBuilder is used since a string is non-editable
    public String certificateFormatter(List<Certificate> certificates) { 
        if(certificates.size() == 0) { 
            return "This student has received no certificates.";
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < certificates.size(); i++) { 
            builder.append(certificates.get(i).getCourseName() + ", " + certificates.get(i).toString().toLowerCase() + "\n");
        }
        return builder.toString();
    }
    //Retrieves all certificates of a student. 
    public List<Certificate> retrieveCertificates (String studentEmail) { 
        return repo.retrieveStudentCertificates(studentEmail);
    }

    //Returns the 3 most viewed webcasts as a single String, a StringBuilder is used as a String is re-created every time you edit it.
    public String top3WebcastFormatted() { 
        List<Webcast> topWebcasts = this.repo.retrieveTop3MostWatchedWebcasts();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < topWebcasts.size(); i++) { 
            stringBuilder.append("Title: " + topWebcasts.get(i).getTitle() + ", views: " + topWebcasts.get(i).getView() + "\n");
        }
        return stringBuilder.toString();
    }
    //Returns the 3 courses with the most certificates and formats them as a String.
    //A StringBuilder is used as a String is re-created every time you edit it.
    public String top3CoursesFormatted() { 
        List<Course> topCourses = this.repo.retrieveTop3CoursesByNumberOfCertificates();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < topCourses.size(); i++) { 
            stringBuilder.append(topCourses.get(i).toString() + "\n");
        }

        return stringBuilder.toString();
    }

    //Retrieves all recommended courses and returns them as a single String.
    //S StringBuilder is used as a String is re-created every time you edit it.
    public String recommendedCourseFormatter(String course) { 
        List<Course> courses = this.repo.retrieveRecommendedCourses(course);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < courses.size(); i++) { 
            stringBuilder.append(courses.get(i).toString() + "\n");
        }
        return stringBuilder.toString();
    }


    //Returns information about the number of students who have finished a course to be used on in the GUI.
    public String numberOfCertificatesFormatter(String course) { 
        return "Number of students who finished this course:  " + this.repo.retrieveNumberOfStudentsWhoCompletedCourse(course);
    }

    



    
}
