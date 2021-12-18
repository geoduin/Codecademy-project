package domain;

import java.time.LocalDate;

public abstract class ContentItem {
    private LocalDate publicationDate;
    private Status status;

    public ContentItem(Status status) {
        this.publicationDate = java.time.LocalDate.now();
        this.status = status;
    }

}