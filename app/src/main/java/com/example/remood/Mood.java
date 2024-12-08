package com.example.remood;

public class Mood {
    private String name;
    private int imageResId;

    public Mood(int imageResId, String name) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String updatedMoodName) { this.name = updatedMoodName;}

    public void setImageResId(int imageResId) { this.imageResId = imageResId;}
}
