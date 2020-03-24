import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PublisherHandler  implements  Runnable{
    Publisher publisher;
    ServerSocket socket;
    String ip="127.0.0.1";
    int port;
    ServerSocket providerSocket;
    Socket Connection;
    public PublisherHandler(Publisher publisher){
        this.publisher=publisher;
        this.port=publisher.port;

    }
    public void run(){
        connectAndNotifyBrokers();


    }
    void notifyBroker() {
        for (Broker broker : Node.getBrokers()){
              for (String artist:publisher.Artists){
                  int intBrokerKeyValue=broker.myKeys.intValue();
                  if(calculateKeys(artist)<=intBrokerKeyValue&&calculateKeys(artist)>=intBrokerKeyValue-11){
                      broker.GetPublishers().add(this.publisher);
                  }


              }

        }
    }
    int calculateKeys(String Artist){
        int hashKey;
        BigInteger ArtistKeys;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.reset();
        m.update(Artist.getBytes());
        byte[] digest = m.digest();
        ArtistKeys = new BigInteger(1,digest);
        BigInteger a=new BigInteger("25");
        ArtistKeys=ArtistKeys.mod(a);
        hashKey=ArtistKeys.intValue();
        if(hashKey>23){
            hashKey=hashKey%23;
        }
        return  hashKey;

    }
    void connectAndNotifyBrokers() {
        for (Broker broker:Node.getBrokers()){
            Socket requestSocket=null;
            ObjectOutputStream out = null;
            ObjectInputStream in = null;
            int port=broker.port;
            try {
                requestSocket=new Socket(broker.ip,port);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out = new ObjectOutputStream(requestSocket.getOutputStream());
                out.writeObject(publisher.request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
