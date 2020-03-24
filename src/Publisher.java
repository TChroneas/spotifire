import com.mpatric.mp3agic.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Publisher {
    private HashMap<String,ArrayList<MusicFile>> songsByArtist;
    private List<Mp3File> mp3Files;


    public Publisher(String filepath){
        songsByArtist=initializeMusicFiles(filepath);
        System.out.println("Initialization successful!");

    }



    public void push(){}

    public static void main(String args[]) {
        Scanner keyboard=new Scanner(System.in);
        System.out.println("Choose Artist:");

        Publisher p=new Publisher("dataset");
        System.out.println(p.toString());
    }





    public static Metadata getMetadata(File file){
        Metadata meta=null;
        try {

            InputStream input = new FileInputStream(file);
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();
            meta=metadata;

//            // List all metadata
//            String[] metadataNames = metadata.names();
//
//            for(String name : metadataNames){
//                System.out.println(name + ": " + metadata.get(name));
//            }
//
//            // Retrieve the necessary info from metadata
//            // Names - title, xmpDM:artist etc. - mentioned below may differ based
//            System.out.println("----------------------------------------------");
//            System.out.println("Title: " + metadata.get("title"));
//            System.out.println("Artists: " + metadata.get("xmpDM:artist"));
//
//            System.out.println("----------------------------------------------");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }

        return meta;
    }


    public HashMap<String,ArrayList<MusicFile>> initializeMusicFiles(String filepath){
        File subdir=new File(filepath);
        HashMap<String,ArrayList<MusicFile>> tempMap=new HashMap<>();
        int i=0;
        System.out.println("Loading Files!");

        try{
            for (File tempfile: subdir.listFiles()){
                i++;
                printProgressBar(i,subdir.listFiles().length);
//                Mp3File t;
//                if(!f.getName().startsWith(".")) {
//                  t =new Mp3File(f.getAbsoluteFile());
//                  printMP3(f.getPath());
//                  temp.add(t);
//                }
                Metadata metadata=getMetadata(tempfile);
                if(metadata.get("xmpDM:artist")!=null){
                    if(tempMap.containsKey(metadata.get("xmpDM:artist"))){
                        tempMap.get(metadata.get("xmpDM:artist")).add(new MusicFile(
                                metadata.get("title"),metadata.get("xmpDM:artist"),metadata.get("xmpDM:album"),metadata.get("xmpDM:genre"),inputStreamToByteArray(new FileInputStream(tempfile))
                                //TODO commit changes

                        ));
                    }
                    else{
                        ArrayList<MusicFile> tempList=new ArrayList<>();
                        tempList.add(new MusicFile(
                                metadata.get("title"),metadata.get("xmpDM:artist"),metadata.get("xmpDM:album"),metadata.get("xmpDM:genre"),inputStreamToByteArray(new FileInputStream(tempfile))
                        ));
                        tempMap.put(metadata.get("xmpDM:artist"),tempList);
                    }
                }



            }
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("\nAll Tracks from dataset have been loaded!");
        return tempMap;

    }

    public byte[] inputStreamToByteArray(InputStream inStream) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        try {
            while ((bytesRead = inStream.read(buffer)) > 0) {
                baos.write(buffer, 0, bytesRead);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public void printProgressBar(int i,int size){
        if(i%5==0)System.out.print("#");
    }



    public List<Mp3File> getMp3Files() {
        return mp3Files;
    }
}
