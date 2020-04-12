import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Node {
    public static final List<Broker> brokers = new CopyOnWriteArrayList<Broker>();
    public static List<Broker> getBrokers() {

        return brokers;
    }
    public synchronized static void sort(){
        Collections.sort(brokers, (Broker a1, Broker a2) -> a1.myKeys.intValue()-a2.myKeys.intValue());
    }
    static BigInteger MAX=new BigInteger("0");
    static BigInteger MIN=new BigInteger("35");
    public static synchronized void setMinMax(){
        for (Broker broker:brokers){
            if(broker.myKeys.intValue()>MAX.intValue()){
                MAX=broker.myKeys;
            }
            if(broker.myKeys.intValue()<MIN.intValue()){
                MIN=broker.myKeys;
            }
        }
    }



}


