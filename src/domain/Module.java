package domain;

import java.time.LocalDate;

public class Module extends ContentItem {
    private String title;
    private int version;
    private int positionWithinCourse;
    private String description;
    private String contactName;
    private String emailAddress;
    private Course relatedCourse;
    private int contentID;

    public Module(LocalDate date, Status status, String title, int version, int positionWithinCourse,
            String description, String contactName, String emailAddress, Course relatedCourse, Integer contentID) {
        super(date, status, contentID);
        this.title = title;
        this.version = version;
        this.positionWithinCourse = positionWithinCourse;
        this.description = description;
        this.contactName = contactName;
        this.emailAddress = emailAddress;
        this.relatedCourse = relatedCourse;
    }

    public Module(LocalDate date, Status status, String title, int version, int positionWithinCourse,
            String description, String contactName, String emailAddress) {
        this(date, status, title, version, positionWithinCourse, description, contactName, emailAddress, null, null);
    }

    // An overloaded method exists to automatically give newly created modules the
    // date of
    // creation. However, retrieved modules from the database need to get their
    // actual historical date, therefore an overloaded constructor is necessary
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

    public int getPositionWithinCourse() {
        return positionWithinCourse;
    }

    public void setPositionWithinCourse(int positionWithinCourse) {
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

    public String getRelatedCourseName() {
        return this.relatedCourse.getName();
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Module other = (Module) obj;

        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title)) {
            return false;
        } else if (version != other.version) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.positionWithinCourse + ": " + this.title + " (version " + this.version + ")";
    }

}
