import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Consumer extends Thread{

          String artist;
          Consumer(String artist){
              this.artist=artist;
          }
         Random r=new Random();
         int max=4321;
         int min=4320;
         int port=new Random().nextBoolean() ? max : min;

         public void run(){
             System.out.println(port);
             Socket requestSocket=null;
             ObjectOutputStream out =null;
             ObjectInputStream in=null;
             try {

                 requestSocket = new Socket("127.0.0.1",port);
                 out = new ObjectOutputStream(requestSocket.getOutputStream());
                 in = new ObjectInputStream(requestSocket.getInputStream());

                 System.out.println("Message created.");

                 System.out.println("Message sent.");


             } catch (UnknownHostException unknownHost) {
                 System.err.println("You are trying to connect to an unknown host!");
             } catch (IOException ioException) {
                 ioException.printStackTrace();
             } finally {
                 try {
                     in.close(); out.close();
                     requestSocket.close();
                 } catch (IOException ioException) {
                     ioException.printStackTrace();
                 }
             }
         }
    public static void main(String args[]) {
            Consumer a= new Consumer ("yolo");
            a.start();



    }





}
