package thread.test.queueTest.test;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class TestQ1 {
    public static void main(String[] args) throws Exception{
        BlockingDeque<String> deque = new LinkedBlockingDeque<String>();

        deque.addFirst("1");
        deque.addLast("2");
        deque.addLast("3");

        String two3 = deque.takeLast();
        String two = deque.takeLast();
        String one ="oneEmpty取没了再取 阻塞";
        one = deque.takeFirst();
        System.out.println("two3:"+two3+ "   two:"+two+ "   one:"+one);

        String oneEmpty = deque.takeFirst();//取空元素则 阻塞。
        System.out.println("oneEmpty:"+oneEmpty);

        //deque.add(null);//不能向BlockingQueue中插入null,否则会抛出NullPointerException异常。

        BlockingQueue<String> unbounded = new LinkedBlockingQueue<String>();// this(Integer.MAX_VALUE);
        BlockingQueue<Object> bounded   = new LinkedBlockingQueue<Object>(1024);

        bounded.put("Value");

        //Object value = bounded.take();
        //System.out.println("value "+ value);
        System.out.println("unbounded.size "+ unbounded.size());
        System.out.println("bounded.size "+ bounded.size());//元素全部取出就 size 0
//        for (int i = -1; i < Integer.MAX_VALUE; i++) {
//            unbounded.put("");///最多能放多少个？？？
//        }
        System.out.println("unbounded.size "+ unbounded.size());//元素全部取出就 size 0


    }
}
