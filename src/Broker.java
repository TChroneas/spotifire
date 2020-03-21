import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Broker extends Node implements Runnable{

    public int port;
    public Broker(int port){
        this.port=port;
    }

    ServerSocket providerSocket;
    Socket connection = null;
    String ip="127.0.0.1";


    public void run(){
        Node.getBrokers().add(this);

        openServer();




    }
    void openServer() {
        try {
            providerSocket = new ServerSocket(this.port, 10);
            while (true) {
                System.out.println(getBrokers().size());
                for(Broker broker: Node.getBrokers()){
                    System.out.println(broker.port);
                }
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

        new Thread(new Broker(4320)).start();
        new Thread(new Broker(4321)).start();





        }
    }

