import java.io.Serializable;
import java.math.BigInteger;

public class Message implements Serializable {
    String artist;
    Object entity;
    public Message(String artist,Object entity){
        this.artist=artist;
        this.entity=entity;
    }
  public   BigInteger hash;
    public void sethash( BigInteger hash){

        this.hash=hash;
    }
    public BigInteger getHash(){
        return this.hash;
    }

}
