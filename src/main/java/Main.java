import com.mongodb.*;
import com.mongodb.util.JSON;
import org.codehaus.jettison.json.JSONObject;

public class Main {

    public static void main(String[] args) throws Exception{
        MyThread t1 = new MyThread(0,Integer.parseInt(args[0])+0, 5, Integer.parseInt(args[1]));
        MyThread t2 = new MyThread(1,Integer.parseInt(args[0])+1, 5, Integer.parseInt(args[1]));
        MyThread t3 = new MyThread(2,Integer.parseInt(args[0])+2, 5, Integer.parseInt(args[1]));
        MyThread t4 = new MyThread(3,Integer.parseInt(args[0])+3, 5, Integer.parseInt(args[1]));
        MyThread t5 = new MyThread(4,Integer.parseInt(args[0])+4, 5, Integer.parseInt(args[1]));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
