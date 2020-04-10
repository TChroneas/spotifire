import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.util.ArrayList;
import java.util.List;

public class Broker extends Node implements Runnable {
    private static List<Publisher> registeredpublishers = new ArrayList<Publisher>();
    private static List<Consumer> registeredConsumers = new ArrayList<Consumer>();
    public static List<Consumer> GetConsumers(){
        return registeredConsumers;
    }
    public static List<Publisher> GetPublishers(){
        return registeredpublishers;
    }
    private boolean transfer=true;
    public String Name;
    public Integer port;
    public Broker(Integer port,String name){
        this.port=port;
        this.Name=name;
    }

    ServerSocket providerSocket;
    Socket connection = null;
    String ip="127.0.0.1";
    BigInteger myKeys;

    public void run(){
        calculateKeys();
        Node.getBrokers().add(this);
        System.out.println(Node.getBrokers().size());
            openServer();


    }
    void calculateKeys(){
        String g =ip+ (port != null ? port.toString() : null);
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.reset();
        m.update(g.getBytes());
        byte[] digest = m.digest();
        myKeys = new BigInteger(1,digest);
        BigInteger a=new BigInteger("25");
        myKeys=myKeys.mod(a);
        System.out.println(myKeys);

    }
    void openServer()throws NullPointerException {
        try {
            providerSocket = new ServerSocket(this.port, 10);
            while (true) {
                acceptConnection();
                new BrokerHandler(this).start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                providerSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

     void  acceptConnection()throws NullPointerException {

         try {
             connection = providerSocket.accept();


         } catch (IOException e) {
             e.printStackTrace();
         }
         System.out.println("client connected.");
         {


         }
     }
     Socket getConnection(){
     return this.connection;
     }


    public static void main(String args[]) {

        new Thread(new Broker(54319,"First")).start();
        new Thread(new Broker(12320,"Second")).start();


    }





































}