import java.io.Serializable;
import java.math.BigInteger;

public class Message implements Serializable {
    String artist;
    Object entity;
    Boolean check=true;
    int port;
    String song;
    public Message(String song){
        this.song=song;
    }
    public Message(String artist,Object entity){
        this.artist=artist;
        this.entity=entity;
    }
    public Message(String artist,int port,boolean check){
        this.artist=artist;
        this.port=port;
        this.check=check;
    }
  public   BigInteger hash;
    public void sethash( BigInteger hash){

        this.hash=hash;
    }
    public BigInteger getHash(){
        return this.hash;
    }

}
