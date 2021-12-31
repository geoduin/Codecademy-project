package domain;

import java.time.LocalDate;

public class Webcast extends ContentItem{

    private String title;
    private String speaker;
    private String organization;
    private int durationInMinutes;
    private String url;


    public Webcast(String title, String speaker, String organization, int durationInMinutes, String url, Status status, LocalDate date) {
        super(date, status);
        this.title = title;
        this.speaker = speaker;
        this.organization = organization;
        this.durationInMinutes = durationInMinutes;
        this.url = url;
    }
    
}
