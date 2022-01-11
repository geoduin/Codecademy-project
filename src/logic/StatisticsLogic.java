package logic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.border.StrokeBorder;

import database.ModuleRepository;
import database.StatisticsRepository;
import domain.Gender;

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

    public String courseProgressStatisticFormatter(String courseName) { 
        HashMap<Integer, Integer> courseNames =  this.repo.retrieveAverageProgressionPerModule(courseName);
        Set<Integer> keySet = courseNames.keySet();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < keySet.size(); i++) { 
            //since the key in keySet is always an integer, using the valueOf method should not give any errors. 
            stringBuilder.append("Module: " + moduleRepo.retrieveModuleByID(Integer.valueOf(keySet.toArray()[i].toString())) + ", Average Progress: " + courseNames.get(keySet.toArray()[i]) + "%\n");
        }
        return stringBuilder.toString();

        
    }

    



    
}
