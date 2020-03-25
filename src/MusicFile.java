import java.io.Serializable;

public class MusicFile implements Serializable {

    private String trackNAme;
    private String artist;
    private String albumInfo;
    private String genre;
    private byte[] data;

    public MusicFile(String trackNAme, String artist, String albumInfo, String genre, byte[] musicFile) {
        this.trackNAme = trackNAme;
        this.artist = artist;
        this.albumInfo = albumInfo;
        this.genre = genre;
        this.data = musicFile;
    }

    public MusicFile() {
    }

    public String getTrackNAme() {
        return trackNAme;
    }

    public void setTrackNAme(String trackNAme) {
        this.trackNAme = trackNAme;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbumInfo() {
        return albumInfo;
    }

    public void setAlbumInfo(String albumInfo) {
        this.albumInfo = albumInfo;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] musicFile) {
        this.data = musicFile;
    }
}
