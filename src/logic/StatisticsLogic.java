package logic;
import java.util.ArrayList;
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


    //Retrieves gender statistics as a formatted string
    public String genderStatisticsFormatter(Gender gender) { 
        int percentage = this.repo.retrievePercentageAcquiredCertificates(gender);
        if(percentage == -1) { 
            return "No " + gender.toString().toLowerCase() + " student has enrolled for a course.";
            
        }
        String formattedString = percentage + "%" + " of " + gender.toString().toLowerCase() + " students who enrolled for a course obtained a certificate.";
        return formattedString;
    }
    //returns formatted string showing the average progress per module for a given course.
    public String courseProgressStatisticFormatter(String courseName) { 
        List<int[]> courseAndPercentage = this.repo.retrieveAverageProgressionPerModule(courseName);
        return moduleFormatter(courseAndPercentage);

        
    }
    //returns formatted string showing the progress of a student per module for a given course.
    public String progressOfStudentFormatter(String studentEmail, String courseName) { 
        List<int[]> courseNames =  this.repo.retrieveProgressionPerModule(studentEmail, courseName);
        return moduleFormatter(courseNames);
    }


    //Takes a List of the type int[id][percentage] and returns a formatted string of the module 
    private String moduleFormatter(List<int[]> idPercentage) { 

        
        StringBuilder stringBuilder = new StringBuilder();

            for(int i = 0; i < idPercentage.size(); i++) { 
                stringBuilder.append(moduleRepo.retrieveModuleByID(idPercentage.get(i)[0]) + ", progress: " + idPercentage.get(i)[1] + "%\n");
            }
        
        return stringBuilder.toString();
    }

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

    public String recommendedCourseFormatter(String course) { 
        ArrayList<Course> courses = this.repo.retrieveRecommendedCourses(course);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < courses.size(); i++) { 
            stringBuilder.append(courses.get(i).toString() + "\n");
        }
        return stringBuilder.toString();
    }


    
    public String numberOfCertificatesFormatter(String course) { 
        return "Number of students who finished this course:  " + this.repo.retrieveNumberOfStudentsWhoCompletedCourse(course);
    }

    



    
}
