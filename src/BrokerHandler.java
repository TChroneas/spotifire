import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BrokerHandler extends Thread  {
    ObjectInputStream in;
    ObjectOutputStream out;
    String f;
    BigInteger theirKeys;
    public BrokerHandler(Socket connection,Broker broker) throws NullPointerException{
        try {
            in = new ObjectInputStream(connection.getInputStream());
            out =new ObjectOutputStream(connection.getOutputStream());
            calculateMessageKeys();
            if(theirKeys=broker.myKeys;)

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public  void calculateMessageKeys() throws NullPointerException {
        try {
            Message request=(Message)in.readObject();
            f=request.artist;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.reset();
        m.update(f.getBytes());
        byte[] digest = m.digest();
        theirKeys = new BigInteger(1,digest);
        BigInteger mod=new BigInteger("25");
        theirKeys=theirKeys.mod(mod);
        System.out.println(theirKeys);


    }


}



