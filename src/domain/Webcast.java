package domain;

import java.time.LocalDate;

public class Webcast extends ContentItem {

    private String title;
    private String speaker;
    private String organization;
    private int durationInMinutes;
    private String url;
    private String description;
    private int views;

    public Webcast(String title, String speaker, String organization, int durationInMinutes, String url, Status status,
            LocalDate date, String description, int viewCount, int contentID) {
        super(date, status, contentID);
        this.title = title;
        this.speaker = speaker;
        this.organization = organization;
        this.durationInMinutes = durationInMinutes;
        this.url = url;
        this.description = description;
        this.views = viewCount;
    }

    public Webcast(String title, String speaker, String organization, int durationInMinutes, String url, Status status,
            String description, int viewCount, int contentID) {
        super(LocalDate.now(), status, contentID);
        this.title = title;
        this.speaker = speaker;
        this.organization = organization;
        this.durationInMinutes = durationInMinutes;
        this.url = url;
        this.description = description;
        this.views = viewCount;
    }

    // Getters and setters have been automatically generated
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getView() {
        return views;
    }

    public void setView(int view) {
        this.views = view;
    }

    @Override
    public String toString() {
        return "Tile: " + this.title + " Speaker: " + this.speaker + " Organization: " + this.organization
                + " Duration: " + this.durationInMinutes + "\n" + "URL: " + this.url + "\n" + "Status: "
                + super.getStatus().toString() + " Creationdate: " + " Description: " + this.description;
    }
}
