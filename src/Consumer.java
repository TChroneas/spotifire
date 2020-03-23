import java.io.*;
import java.util.Random;
public class Consumer extends Node implements Serializable  {

          String artist;
    Random r=new Random();
    int max=12320;
    int min=54319;
    int port;
    Message request;


    public Consumer(String artist){
              this.artist=artist;
              this.port=new Random().nextBoolean() ? max : min;
              request= new Message(artist,this.getConsumer());
          }
     public Consumer(String artist,int port){
        this.artist=artist;
        this.port=port;
         request= new Message(artist,this.getConsumer());
     }
          public Consumer getConsumer(){
        return this;
    }

         public void Register(Broker broker,String ArtistName){
             broker.GetConsumers().add(this);
             System.out.println(this.artist);

         }

}
