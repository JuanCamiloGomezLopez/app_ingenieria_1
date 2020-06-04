package model;

public class Inbox {

    public int id;
    public String image = null;
    public String from;
    public String email;
    public String message;
    public String area;

    public int color = -1;

    public Inbox(int id, String image, String from, String email, String message, String area, int color) {
        this.id = id;
        this.image = image;
        this.from = from;
        this.email = email;
        this.message = message;
        this.area = area;
        this.color = color;
    }
}
