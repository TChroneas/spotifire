import java.io.Serializable;
import java.math.BigInteger;

public class Message implements Serializable {
    String artist;
    Object entity;
    Boolean check=true;
    int port;
    String song;
    MusicFile extract;
    String songPart;
    public Message(String songPart,MusicFile extract){
        this.songPart=songPart;
        this.extract=extract;
    }
    public Message(Object entity){
        this.entity=entity;
    }
    public Message(String artist,String song){
        this.artist=artist;
        this.song=song;
    }
    public Message(String song){
        this.song=song;
    }

    public Message(String artist,Object entity,String song){
        this.artist=artist;
        this.entity=entity;
        this.song=song;
    }
    public Message(String artist,int port,boolean check){
        this.artist=artist;
        this.port=port;
        this.check=check;
    }


}
