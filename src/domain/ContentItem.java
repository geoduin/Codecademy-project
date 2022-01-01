package domain;

import java.time.LocalDate;

//CLass responsible for defining the overarching attributes of Webcasts and Modules
public abstract class ContentItem {
    private LocalDate publicationDate;
    private Status status;

    public ContentItem(LocalDate date, Status status) {
        this.publicationDate = date;
        this.status = status;
    }

    public LocalDate getDate() {
        return this.publicationDate;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
