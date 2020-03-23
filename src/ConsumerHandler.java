import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConsumerHandler extends Thread{


    Consumer consumer;
    public ConsumerHandler(Consumer consumer){
        this.consumer=consumer;
    }
    void connect(int port) {
        Socket requestSocket=null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {

            requestSocket = new Socket("127.0.0.1", consumer.port);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());
            System.out.println("Message created.");
            out.writeObject(consumer.request);
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    public void run(){
        connect(consumer.port);
    }
    public static void main(String args[]) {
        Consumer a=new Consumer("Kevin MacLeod");
        Consumer b=new Consumer("Alexander Narakada");
        Consumer c=new Consumer("Alexander Narakada");

        new ConsumerHandler(a).start();
        new ConsumerHandler(b).start();
        new ConsumerHandler(c).start();

    }
}
