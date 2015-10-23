import com.mongodb.*;
import com.mongodb.util.JSON;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by jhan on 10/22/15.
 */
public class MyThread extends Thread {

    private int startNumber, nThreads, maxNumber;
    private int threadId;
    public MyThread(int threadId, int s, int n, int m) {
        this.startNumber=s;
        this.nThreads=n;
        this.maxNumber=m;
        this.threadId=threadId;
    }

    @Override
    public void run() {
        //DB dBlocal=(new MongoClient(new MongoClientURI("mongodb://172.31.31.40:27017"))).getDB("test");

        DB dB=(new MongoClient(new MongoClientURI("mongodb://172.31.26.123:27017, 172.31.13.18:27017, 172.31.3.93:27017, 172.31.10.34:27017, 172.31.25.76:27017"))).getDB("test");
        DBCollection dBCollection = dB.getCollection("test");

        DB dBlocal=(new MongoClient("localhost",27017)).getDB("test");
        DBCollection dBCollectionlocal = dBlocal.getCollection("test");

        int count=0;
        for (int i= startNumber; i<maxNumber; i+=nThreads){

            count++;
            JSONObject obj = new JSONObject();
            JSONObject obj2 = new JSONObject();
            try {
                Thread.sleep(1000);
                obj.put("_id", i);
                obj.put("number", i);
                obj.put("acknowledged", false);

                obj2.put("_id",i);
                obj2.put("number",i);
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            // WriteResult result =new WriteResult(0,false,null);
            //System.out.println("writeResult : " + result.wasAcknowledged()+ result.getN()+"  "+ result.toString()) ;
            System.out.println(count);

            //write to local mongodb for logging
            dBCollectionlocal.insert(((DBObject) JSON.parse(obj.toString())));
            try {
                //write to mongodb replica
                WriteResult result = dBCollection.insert(((DBObject) JSON.parse(obj2.toString())),WriteConcern.MAJORITY);

                // System.out.println("[ThreadID " + this.threadId+ "] " + i+" count: "+count+"  writeResult : " + result.wasAcknowledged()+ result.getN()+"  "+ result.toString());


                //write acknowledged result to local mongodb for logging
                DBObject query = new BasicDBObject("number", i);
                DBObject history = new BasicDBObject().append("_id", i)
                        .append("number", i)
                        .append("acknowledged", result.wasAcknowledged());
                DBObject update = new BasicDBObject("$set", history);

                dBCollectionlocal.updateMulti(query, update);



            }
            catch(Exception e) {
                continue;
            }


            //DBObject doc = dBCollection.findOne(); //get first document
            //BasicDBObject query = new BasicDBObject();
            //query.put("number", new BasicDBObject("$gt", 9));
            //WriteResult result = dBCollection.remove(query);

            //System.out.println("total : " +  i);
            //System.out.println("[ThreadID " + this.threadId+ "] " + i+" count: "+count+"  writeResult : " + result.wasAcknowledged()+ result.getN()+"  "+ result.toString()) ;
            //Thread.sleep(100);
        }
    }
}
