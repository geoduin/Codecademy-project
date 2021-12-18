package domain;

import java.time.LocalDate;

public abstract class ContentItem {
    private LocalDate publicationDate;
    private Status status;

    public ContentItem(LocalDate date, Status status) {
        this.publicationDate = date;
        this.status = status;
        this.publicationDate = date;
    }
}
