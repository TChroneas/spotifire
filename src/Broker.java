import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

public class Broker extends Node implements Runnable, Serializable {
    private static List<Publisher> registeredpublishers = new ArrayList<Publisher>();
    private static List<Broker> registeredConsumer = new ArrayList<Broker>();

    public Integer port;
    public Broker(Integer port){
        this.port=port;
    }

    ServerSocket providerSocket;
    Socket connection = null;
    String ip="127.0.0.1";
    String g;
    BigInteger myKeys;
    ObjectInputStream in;
    ObjectOutputStream out;
    String f;
    BigInteger theirKeys;






    public void run(){
        calculateKeys();
        Node.getBrokers().add(this);
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
                BrokerHandler handler=new BrokerHandler(connection,this);









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

    void acceptConnection()throws NullPointerException {
        try {
            connection = providerSocket.accept();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("client connected.");
        {


        }
    }
    public static void main(String args[]) {

        new Thread(new Broker(12345)).start();
        new Thread(new Broker(54319)).start();






    }
    }

