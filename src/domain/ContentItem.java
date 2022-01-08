package domain;

import java.time.LocalDate;

//CLass responsible for defining the overarching attributes of Webcasts and Modules
public abstract class ContentItem {
    private LocalDate publicationDate;
    private Status status;
    private int contentID;

    public ContentItem(LocalDate date, Status status, Integer contentID) {
        this.publicationDate = date;
        this.status = status;
        this.contentID = contentID;
    }

    public ContentItem(LocalDate date, Status status) {
        this(date, status, null);
    }

    public LocalDate getDate() {
        return this.publicationDate;
    }

    public Status getStatus() {
        return this.status;
    }

    public Integer getID() {
        return this.contentID;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
