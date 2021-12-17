package domain;

public abstract class ContentItem {
    private String publicationDate;
    private Status status;

    public ContentItem(String publicationDate, Status status) {
        this.publicationDate = publicationDate;
        this.status = status;
    }

}
