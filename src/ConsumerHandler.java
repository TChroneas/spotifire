import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConsumerHandler extends Thread {
    Message answer;
    Boolean check;


    Consumer consumer;

    public ConsumerHandler(Consumer consumer) {
        this.consumer = consumer;
    }

    void connect(int port) {
        Socket requestSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {

            requestSocket = new Socket("192.168.1.9", consumer.port);
            out = new ObjectOutputStream(requestSocket.getOutputStream());

            System.out.println("Message created.");
            out.writeObject(consumer.request);
            in = new ObjectInputStream(requestSocket.getInputStream());
            try {
                answer = (Message) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            check = answer.check;

            if (check == false) {
                System.out.println("I change server");

                this.consumer.setPort(answer.port);
                new ConsumerHandler(this.consumer).start();
                System.out.println("Server Changed");
                this.interrupt();


            } else {
                System.out.println(answer.song);
                MusicFile tempMusicFile=new MusicFile();
                int i=0;
                while (true){
                    try {
                        Message message = (Message) in.readObject();
                        if(message.getTransfer()==true){
                            tempMusicFile.setData(message.getByteChunk());
                            tempMusicFile.setTrackNAme(this.consumer.song+" "+i);
                            tempMusicFile.saveFileLocally();
                            i++;
                        }
                        System.out.println(message.toString());
                        if(message.getTransfer()==false){
                            if(message.song.equals("File not found")){
                                System.err.println("Song does not exist");
                                break;
                            }
                            else if(message.song.equals("artist not found")){
                                System.out.println("Artist not found");
                                break;

                            }
                            else {
                                System.out.println("Song Received");

                                break;
                            }

                        }

                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }catch (EOFException e){
                        e.printStackTrace();
                    }
                }




            }
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



    public void run() {
        connect(consumer.port);
    }

    public static void main(String args[]) {
        Consumer a = new Consumer("Kevin MacLeod", "City Run.mp3");
        Consumer b = new Consumer("Alexander Narakada", "123");
        Consumer c = new Consumer("Alexander Narakada", "dunno");

        new ConsumerHandler(a).start();


    }
}
