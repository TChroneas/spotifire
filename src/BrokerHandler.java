import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BrokerHandler extends Thread implements Serializable {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String f;
    private BigInteger theirKeys;
    private Broker broker;
    private Object e;
    private Message request;
    private Socket Stopcon=null;


    public BrokerHandler(Broker broker) throws NullPointerException{
        Stopcon=broker.getConnection();
        try {

            in = new ObjectInputStream(Stopcon.getInputStream());
            out =new ObjectOutputStream(Stopcon.getOutputStream());
            out.flush();
            try {
                this.request=(Message)in.readObject();
                this.f=request.artist;
                this.e =request.entity;
                this.broker=broker;

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        if(this.e instanceof Consumer){
            calculateMessageKeys(this.request);
            checkBroker(this.broker,(Consumer) e);
        }
        else if (this.e instanceof Publisher){
            System.out.println("This is a publisher");
        }

    }
    public  void disconnect(Socket connection){
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public  void checkBroker(Broker broker,Consumer consumer) {


        int intMyKeys = broker.myKeys.intValue();
        int intTheirKeys = theirKeys.intValue();
        if (intTheirKeys > 23) {
            intTheirKeys = intTheirKeys % 23;
        }
        if (intTheirKeys <= intMyKeys && intTheirKeys >= intMyKeys - 11) {
            consumer.Register(broker, f);
            System.out.println(broker.Name + "Client Connected and Registered");
            Message answer=(new Message("what song would u like to listen to?"));
            try {
                out.writeObject(answer);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            int thePort = 0;
            System.out.println(broker.Name + "Client changing server");
            for (Broker broker1 : Node.getBrokers()) {
                int KEYS = broker1.myKeys.intValue();
                if (intTheirKeys <= KEYS && intTheirKeys >= KEYS - 11) {
                    thePort = broker1.port;
                    System.out.println(thePort);
                }
            }
            Message answer=new Message(this.f,thePort,false);
            try {
                out.writeObject(answer);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            disconnect(Stopcon);


        }
    }
    public  void calculateMessageKeys(Message request)  {
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
