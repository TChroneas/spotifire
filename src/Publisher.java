import com.mpatric.mp3agic.*;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Publisher {
    String Name;
    int count=0;
    public List<String> Artists=new ArrayList<String>();
    ServerSocket providerSocket;
    Socket connection = null;
    public List<Mp3File> files3=new ArrayList<Mp3File>();
    public Publisher(String name){
        this.Name=name;
        File directory = new File("C:\\Users\\jum\\Desktop\\spotifire\\src\\data"+this.Name);
            String dir="C:\\Users\\jum\\Desktop\\spotifire\\src\\data"+this.Name;
        File[] files=directory.listFiles();
        for(File f:files){
            String filename=f.getName();
          //  System.out.println(filename);
            try {
                Mp3File MP3=new Mp3File(dir+"\\"+filename);
                files3.add(MP3);

               // if (MP3.hasId3v1Tag()) {
                 //   ID3v1 id3v1Tag = MP3.getId3v1Tag();
                   // System.out.println(id3v1Tag.getTitle());
                    //count++;
                //}
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedTagException e) {
                e.printStackTrace();
            } catch (InvalidDataException e) {
                e.printStackTrace();
            }




        }
        System.out.println(count);
        for (Mp3File FILE:files3){
            count++;
            if (FILE.hasId3v1Tag()){
                ID3v1 id3v1Tag = FILE.getId3v1Tag();
                System.out.println(id3v1Tag.getTitle());



            }
        }
        System.out.println(count);



    }

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

    public static void main(String args[]) {
        Publisher a=new Publisher("1");
    }










}
