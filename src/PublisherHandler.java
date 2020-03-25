import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PublisherHandler extends Node implements Runnable{
    Publisher publisher;
    ServerSocket socket;
    String ip = "127.0.0.1";
    int port;
    ServerSocket providerSocket;
    Socket Connection;

    public PublisherHandler(Publisher publisher) {
        this.publisher = publisher;
        this.port = publisher.port;

    }

    public void run() {
        connectAndNotifyBrokers();
        //openServer();

    }

    void openServer() {
        try {
            providerSocket = new ServerSocket(publisher.port, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Connection = providerSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new PublisherServerHandler(this).start();


        }
    }

    public static void main(String[] args) {
        Publisher p = new Publisher("dataset2", 1234);
        System.out.println();
        new Thread(new PublisherHandler(p)).start();
    }

    public Socket getConnection() {
        return this.Connection;
    }
    //void notifyBroker() {
    //  for (Broker broker : Node.getBrokers()){
    //      for (String artist:publisher.Artists){
    //        int intBrokerKeyValue=broker.myKeys.intValue();
    //      if(calculateKeys(artist)<=intBrokerKeyValue&&calculateKeys(artist)>=intBrokerKeyValue-11){
    //        broker.GetPublishers().add(this.publisher);
    //  }


    //}

    //}
    //}
    //  int calculateKeys(String Artist){
    //    int hashKey;
    //  BigInteger ArtistKeys;
    //MessageDigest m = null;
    //try {
    //  m = MessageDigest.getInstance("MD5");
    // } catch (NoSuchAlgorithmException e) {
    //   e.printStackTrace();
    //}
    //m.reset();
    //m.update(Artist.getBytes());
    //byte[] digest = m.digest();
    //ArtistKeys = new BigInteger(1,digest);
    //BigInteger a=new BigInteger("25");
    //ArtistKeys=ArtistKeys.mod(a);
    //hashKey=ArtistKeys.intValue();
    //if(hashKey>23){
    //  hashKey=hashKey%23;
    //}
    //return  hashKey;

    //}
    void connectAndNotifyBrokers() {


            Socket requestSocket = null;
            ObjectOutputStream out = null;
            ObjectInputStream in = null;
            int port = 12320;
            try {
                requestSocket = new Socket("127.0.0.1", port);
                out = new ObjectOutputStream(requestSocket.getOutputStream());
                System.out.println("Connection Established!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                out.writeObject(publisher.getMsg());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
              //  in.close();
                out.close();
                requestSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


    }
}
