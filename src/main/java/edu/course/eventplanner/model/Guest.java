package edu.course.eventplanner.model;

public class Guest {
    private final String name;
    private final String groupTag;
    public Guest(String name, String groupTag) {
        this.name = name;
        this.groupTag = groupTag;
    }
    public  String getName() { return name; }
    public String getGroupTag() { return groupTag; }

    @Override
    public String toString() {
        return "Guest: first name='" + name + "', groupTag='" + groupTag ;
    }
}
