import com.mpatric.mp3agic.*;

import java.io.*;
import java.util.ArrayList;



public class Publisher implements Serializable{
    private ArrayList<String> artists;
    private String dir;
    private Message msg;
    public int port;



    public Publisher(String filepath,int port) {
        this.dir=filepath;
        artists=getListofArtist(filepath);
        System.out.println("\nArtists Loaded");
        this.port=port;
        this.msg=new Message(this);
        System.out.println(Node.getBrokers().size());

    }








    public ArrayList<String> getListofArtist(String filepath){
        ArrayList<String> tempList = new ArrayList<>();
        File dir=new File(filepath);
        System.out.println("Loading Artists");
        int i=0;
        for (File temp: dir.listFiles()){
            printProgressBar(++i);
            GlobalFunctions gf=new GlobalFunctions();
            String artist=gf.getMp3Metadata(temp).get("xmpDM:artist");
            if(!tempList.contains(artist) && artist!=null)tempList.add(artist);
        }

        return tempList;
    }

    public  String getDir() {
        return dir;
    }

    public void printProgressBar(int i) {
        if (i % 5 == 0) System.out.print("#");
    }

    public ArrayList<String> getArtists() {
        return artists;
    }

    public Message getMsg() {
        return msg;
    }
}
