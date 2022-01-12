package logic;
import database.EnrollRepository;
import database.StatisticsRepository;


public class Test {

    public static void main(String[] args) {
        StatisticsLogic logic = new StatisticsLogic();
        System.out.println(logic.progressOfStudentFormatter("henk@henk.nl", "TestCourse"));
        // System.out.println(logic.courseProgressStatisticFormatter("TestCourse"));
       
    }
    
}
