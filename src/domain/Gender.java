package domain;

public enum Gender {
    M{@Override
    public String toString() {
        return "Male";
    }}, F{@Override
    public String toString() {
        return "Female";
    }}, O { @Override
    public String toString() {
        return "Other";
    }}
}
