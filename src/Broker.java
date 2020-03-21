import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

public class Broker extends Node implements Runnable{

    public Integer port;
    public Broker(Integer port){
        this.port=port;
    }

    ServerSocket providerSocket;
    Socket connection = null;
    String ip="127.0.0.1";
    String g;




    public void run(){
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
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        BigInteger a=new BigInteger("25");
        bigInt=bigInt.mod(a);
        System.out.println(bigInt);
        Node.getBrokers().add(this);
        System.out.println(g);


        openServer();




    }
    void openServer() {
        try {
            providerSocket = new ServerSocket(this.port, 10);
            while (true) {

                connection = providerSocket.accept();
                System.out.println("client connected.");


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
    public static void main(String args[]) {

        new Thread(new Broker(12345)).start();
        new Thread(new Broker(54319)).start();






    }
    }

