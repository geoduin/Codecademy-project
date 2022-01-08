package database;

import java.util.HashMap;

import domain.Course;

public class Test {

    public static void main(String[] args) {
        
        StatisticsRepository repo = new StatisticsRepository();
        // HashMap<Course, Integer> map = repo.retrieveTop3CoursesByNumberOfCertificates();
        // for(Course course : map.keySet()) { 
        //     System.out.println(course.getName() + " Nr of certs: " + map.get(course));
        // }


        System.out.println(repo.retrieveTop3MostWachtedWebcasts());


    }
    
}
