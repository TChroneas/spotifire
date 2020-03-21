import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class ConsumerHandler extends Thread{
    ObjectInputStream in;
    ObjectOutputStream out;
    public ConsumerHandler(Socket connection){

        try {
            out =new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void run(){
        try {
            Message a=(Message)in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
