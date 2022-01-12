package logic;
import database.EnrollRepository;
import database.StatisticsRepository;


public class Test {

    public static void main(String[] args) {
        StatisticsLogic logic = new StatisticsLogic();
        StatisticsRepository repo = new StatisticsRepository();
        System.out.println(logic.top3CoursesFormatted());
       
    }
    
}
