import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable {
    private static List<Broker> brokers = new ArrayList<Broker>();


    public static List<Broker> getBrokers() {
        return brokers;
    }


    }

