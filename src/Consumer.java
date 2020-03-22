import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Random;

public class Consumer extends Node implements Serializable,Runnable  {

          String artist;
          Consumer(String artist){
              this.artist=artist;

          }

         Random r=new Random();
         int max=12345;
         int min=54319;
         int port=new Random().nextBoolean() ? max : min;

    Socket requestSocket=null;
    ObjectOutputStream out =null;
    ObjectInputStream in=null;

         public void run(){
             connect(this.port);

         }
         void connect(int port){
             try {

                 requestSocket = new Socket("127.0.0.1",this.port);
                 out = new ObjectOutputStream(requestSocket.getOutputStream());
                 in = new ObjectInputStream(requestSocket.getInputStream());
                 Message request= new Message(artist,this);
                 System.out.println("Message created.");
                 out.writeObject(request);
                 System.out.println("Message sent.");
                 try {
                     System.out.println("the hash is"+((Message)in.readObject()).getHash());
                 } catch (ClassNotFoundException e) {
                     e.printStackTrace();
                 }


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
            Thread a= new Thread (new Consumer("Kevin MacLeod"));
            Thread b= new Thread (new Consumer("Kevin MacLeod"));


            a.start();
            b.start();



    }





}
