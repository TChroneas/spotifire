import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Publisher {
    public static void main(String args[]){


    }
    ServerSocket providerSocket;
    Socket connection = null;

    void openServer() {
        try {
            providerSocket= new ServerSocket(4319,10);
            while(true){
                connection = providerSocket.accept();
                System.out.println("broker connected.");


            }









        } catch (IOException e) {
            e.printStackTrace();
        }
    }












}
