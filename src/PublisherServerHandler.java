import org.apache.tika.metadata.Metadata;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class PublisherServerHandler extends PublisherHandler{

    Socket connection;
    ObjectInputStream in;
    ObjectOutputStream out;
    private static final int CHUNK_SIZE=512000;


    public void run() {
        System.out.println("Run");
        push();
    }

    public PublisherServerHandler(PublisherHandler publisherHandler){
        super(publisherHandler);
        connection=publisherHandler.getConnection();
        try {
           // in = new ObjectInputStream(connection.getInputStream());
             out =new ObjectOutputStream(connection.getOutputStream());
             out.flush();
         } catch (Exception e) {
             e.printStackTrace();
         }


     }

     public  MusicFile importMusicFile(String songname,String dir) {
         MusicFile song=null;
        try {
            File songFile = new File(dir + "\\" + songname);
            GlobalFunctions gf = new GlobalFunctions();
            Metadata metadata = gf.getMp3Metadata(songFile);
           song = new MusicFile(
                    metadata.get("title"), metadata.get("xmpDM:artist"), metadata.get("xmpDM:album"), metadata.get("xmpDM:genre"), inputStreamToByteArray(new FileInputStream(songFile)));
        } catch (FileNotFoundException e){
            try {
                out.writeObject("Not Found");
                e.printStackTrace();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
         return song;
     }

     public synchronized void push(){
         System.out.println("\n\nPushing song *insert_name_here*");
         //Todo push byte array on intervals
         //Todo 512kb size chunks-DONE
         //InputStream checks chunk size if chunk.size<512 end;
        MusicFile song=null;

        song = importMusicFile(message.song, this.publisher.getDir());


         Message tempmsg=null;

        try {
            for (int i = 0; i <= song.getData().length/CHUNK_SIZE;i++ ) {
                // TODO Transfer Correctly the last chunk
                byte[] chunk=extractByteChunk(i,song.getData());

                tempmsg = new Message(chunk);
              out.writeObject(tempmsg);
              Thread.sleep(1000);
                System.out.println("CHUNK :"+tempmsg.toString()+" Sent");

            }
            tempmsg=new Message("END");
            tempmsg.setTransfer(false);
            out.writeObject(tempmsg);
//            out.flush();
 //           out.close();
        }catch(Exception e){
            e.printStackTrace();
            tempmsg = new Message("");
            try {
                out.writeObject(tempmsg);
            }catch (IOException ex){
                e.printStackTrace();
            }
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

    public byte[] extractByteChunk(int i,byte[] song){
        byte [] chunk;
        if (i<song.length/CHUNK_SIZE){
            chunk=new byte[CHUNK_SIZE];
            for (int j=0;j<CHUNK_SIZE;j++){
                chunk[j]=song[(i*CHUNK_SIZE)+j];
            }
        }
        else{
            chunk=new byte[song.length-((i)*CHUNK_SIZE)];
            for (int j=0;j<song.length-((i)*CHUNK_SIZE);j++){
                chunk[j]=song[(i*CHUNK_SIZE)+j];
            }
        }

        return chunk;

    }





}
