package am.pam.remoodedit;

public class Remood {
    public int id;
    public String mood;
    public String title;
    public String description;
    public String date;

    public Remood() {
        // Konstruktor kosong diperlukan untuk Firebase
    }

    public Remood(String mood, String title, String description, String date) {
        this.mood = mood;
        this.title = title;
        this.description = description;
        this.date = date;
    }
}
