package thread.test.queueTest.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class QueueTest {
    public static void main(String[] args) throws Exception{
        BlockingQueue<String> queue   = new PriorityBlockingQueue<String>();//?

        //String implements java.lang.Comparable
        //queue.add("b1");
//        queue.add("c");
//        queue.add("d");
        queue.add("b");
        queue.add("a");
        queue.add("c");
        queue.add("d");
        //queue.add("b2");
        //queue.put("b2");

       // String value = queue.take();

        for (int i=0;i<queue.size();i++) {
            System.out.println(queue.take());///？？？？
        }

        SynchronousQueue<Object> queue2   = new SynchronousQueue<Object>();
//         queue2.add(1);//java.lang.IllegalStateException: Queue full
       //queue2.add(2);
//        System.out.println(queue2.size());
//        System.out.println(queue2.take());
    }
}
