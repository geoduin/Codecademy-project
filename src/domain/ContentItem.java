package domain;

import java.time.LocalDate;

/* 
*CLass responsible for defining the overarching attributes of Webcasts and Modules. The database *ContentID is *also an attribute, because in GUI editing functionality it is efficient to use to push *changes to the database (mainly because module has a multi part primary key) 
*/
public abstract class ContentItem {
    private LocalDate publicationDate;
    private Status status;
    private int contentID;

    // Self explanatory constructor
    ContentItem(LocalDate date, Status status, int contentID) {
        this.publicationDate = date;
        this.status = status;
        this.contentID = contentID;
    }

    // Getters and setters bulk
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
