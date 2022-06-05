package theduc.dmt.appmusicdemo;

public class Song {
    private String name;
    private int file, image;

    public Song(String name, int file, int image) {
        this.name = name;
        this.file = file;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }
}