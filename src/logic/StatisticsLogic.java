package logic;
import database.StatisticsRepository;
import domain.Gender;

public class StatisticsLogic {

    private StatisticsRepository repo;

    public StatisticsLogic() { 
        this.repo = new StatisticsRepository();
    }


    //Retrieves gender statistics as a formatted string
    public String genderStatisticsFormatter(Gender gender) { 

        int percentage = this.repo.retrievePercentageAcquiredCertificates(gender);
        String formattedString = percentage + "%" + " of " + gender + " students who enrolled for a course obtained a certificate.";
        return formattedString;

    }

    
}
