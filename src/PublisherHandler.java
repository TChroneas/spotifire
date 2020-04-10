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
    Socket requestSocket;
    ObjectOutputStream out ;
    ObjectInputStream in ;
    Message message;

    public PublisherHandler(Publisher publisher) {
        this.publisher = publisher;
        this.port = publisher.port;

    }

    public void run() {
        connectAndNotifyBrokers();
        openServer();

    }

    void openServer() {
        try {
            providerSocket = new ServerSocket(publisher.port, 10);

        } catch (IOException e) {
            e.printStackTrace();
        }
        int i=1;
        while (true) {
            try {
                Connection = providerSocket.accept();
                in=new ObjectInputStream(Connection.getInputStream());
                message=(Message) in.readObject();
                if(message!=null) {
                    System.out.println(message.toString());
                    System.out.println("Message rcv'd");

                }

            } catch (IOException e) {
                e.printStackTrace();
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }catch (NullPointerException e){
                System.err.println("Message Not Found" +i);
                i++;
            }
           PublisherServerHandler psh= new PublisherServerHandler(this);

            psh.push();


        }
    }

    public static void main(String[] args) {
        Publisher p = new Publisher("dataset2", 1234);
        System.out.println();
        new Thread(new PublisherHandler(p)).start();
    }


    void connectAndNotifyBrokers() {



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
//          try {
//               in.close();
//               out.close();
//                requestSocket.close();
//          }
//          catch (IOException e) {
//                e.printStackTrace();
//           }


    }

    public Socket getConnection() {
        return Connection;
    }

    public PublisherHandler(PublisherHandler p){
        this.publisher=p.publisher;
        this.port=p.port;
        this.message=p.message;
    }
}
