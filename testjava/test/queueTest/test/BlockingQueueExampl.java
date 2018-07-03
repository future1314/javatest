package thread.test.queueTest.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueExampl {

    public static void main(String[] args) throws Exception {

       // BlockingQueue queue = new ArrayBlockingQueue(4);
        BlockingQueue queue = new ArrayBlockingQueue(2);

        Producer1 producer = new Producer1(queue);
        Consumer1 consumer = new Consumer1(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        Thread.sleep(4000);
        System.out.println("size.."+queue.size());//如果指定大小2 只能放进2个元素，再放的话 就阻塞了。。。
    }
}

class Producer1 implements Runnable{

    protected BlockingQueue queue = null;

    public Producer1(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            queue.put("1");
            //Thread.sleep(2000);
            System.out.println("1阻塞了吗。。。。");
            queue.put("2");
            //Thread.sleep(2000);
            System.out.println("2阻塞了吗。。。。");
            queue.put("3");
            System.out.println("3阻塞。。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer1 implements Runnable{

    protected BlockingQueue queue = null;

    public Consumer1(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
//            System.out.println(queue.take());
////            System.out.println(queue.take());
////            System.out.println(queue.take());
            //System.out.println(queue.take());//阻塞//Process finished with exit code 0.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


