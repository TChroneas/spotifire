import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PublisherServerHandler extends Thread {
    Socket connection;
    ObjectInputStream in=null;
    ObjectOutputStream out=null;
     public PublisherServerHandler(PublisherHandler publisherHandler){
         connection=publisherHandler.getConnection();
         try {
             in = new ObjectInputStream(connection.getInputStream());
             out =new ObjectOutputStream(connection.getOutputStream());
             out.flush();
         } catch (IOException e) {
             e.printStackTrace();
         }


     }
}
