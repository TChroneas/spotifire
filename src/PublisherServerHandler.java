import org.apache.tika.metadata.Metadata;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class PublisherServerHandler extends Thread {

    Socket connection;
    ObjectInputStream in;
    ObjectOutputStream out;
    private static final int CHUNK_SIZE=512000;


     public PublisherServerHandler(PublisherHandler publisherHandler){
//         connection=publisherHandler.getConnection();
//         try {
//             in = new ObjectInputStream(connection.getInputStream());
//             out =new ObjectOutputStream(connection.getOutputStream());
//             out.flush();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }


     }

     public static MusicFile importMusicFile(String songname,String dir) throws FileNotFoundException{

         File songFile= new File(dir+"\\"+songname);
         GlobalFunctions gf=new GlobalFunctions();
         Metadata metadata=gf.getMp3Metadata(songFile);
         MusicFile song=new MusicFile(
                 metadata.get("title"),metadata.get("xmpDM:artist"),metadata.get("xmpDM:album"),metadata.get("xmpDM:genre"),inputStreamToByteArray(new FileInputStream(songFile)));

         return song;
     }

     public static void push(){
         System.out.println("\n\nPushing song *insert_name_here*");
         //Todo push byte array on intervals
         //Todo 512kb size chunks-DONE
         //InputStream checks chunk size if chunk.size<512 end;
        MusicFile song=null;
        try{
            song = importMusicFile("After The End.mp3", "dataset");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

         Socket requestSocket = null;
         ObjectOutputStream out = null;
         ObjectInputStream in = null;


        try {
            for (int i = 0; i < song.getData().length; i++) {
                byte[] chunk=null;
                for(int j=0;j<CHUNK_SIZE;j++){
                    if(i+j<song.getData().length)break;
                    chunk[j]=song.getData()[j+i];
                }
                Message msg = new Message(chunk);
 //              out.writeObject(msg);
                System.out.println("CHUNK :"+msg.toString()+" Sent");
                i+=CHUNK_SIZE;
            }
//            out.flush();
 //           out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
         System.out.println("\n\nMessage Sent");

     }




     public static byte[] inputStreamToByteArray(InputStream inStream) {

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

    public static void main(String []args){
         PublisherServerHandler psh=new PublisherServerHandler(new PublisherHandler(new Publisher("dataset")));
         psh.push();
    }



}
