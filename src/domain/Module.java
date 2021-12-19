package domain;

import java.time.LocalDate;

public class Module extends ContentItem {
    private String title;
    private int version;
    private int positionWithinCourse;
    private String description;
    private String contactName;
    private String emailAddress;

    public Module(LocalDate date, Status status, String title, int version, int positionWithinCourse,
            String description, String contactName, String emailAddress) {
        super(date, status);
        this.title = title;
        this.version = version;
        this.positionWithinCourse = positionWithinCourse;
        this.description = description;
        this.contactName = contactName;
        this.emailAddress = emailAddress;
    }

    public Module(Status status, String title, int version, int positionWithinCourse,
            String description, String contactName, String emailAddress) {
        this(java.time.LocalDate.now(), status, title, version, positionWithinCourse, description, contactName,
                emailAddress);
    }

    // Getters and setters bulk
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getTrackingNumber() {
        return positionWithinCourse;
    }

    public void setTrackingNumber(int positionWithinCourse) {
        this.positionWithinCourse = positionWithinCourse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // Title and version makes a module instance unique
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + version;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Module other = (Module) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (version != other.version)
            return false;
        return true;
    }

}
