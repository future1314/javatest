package thread.test.thread.test;

import java.util.concurrent.CountDownLatch;

public class Decrementer implements Runnable {

    CountDownLatch latch = null;

    public Decrementer(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        System.out.println("Decrementer .latch.getCount():"+ latch.getCount()+"  ...start...");
        try {
            Thread.sleep(1000);
            this.latch.countDown();
            System.out.println("Decrementer .latch.getCount():" + (this.latch.getCount()));
            Thread.sleep(1000);
            this.latch.countDown();
            System.out.println("Decrementer .latch.getCount():" + (this.latch.getCount()));
            Thread.sleep(1000);
            //System.out.println("Decrementer .latch.getCount():" + (this.latch.getCount()-1)+"  ...over...");
            this.latch.countDown();//
            //System.out.println("Decrementer .latch.getCount():" + (this.latch.getCount())+"  ...over...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        CountDownLatch latch    = new CountDownLatch(3);//

        Waiter waiter      = new Waiter(latch);
        Decrementer decrementer = new Decrementer(latch);

        new Thread(waiter).start();
        new Thread(decrementer).start();
        //Thread.sleep(4000);
        System.out.println("main over ... ");
    }
}

class Waiter implements Runnable{

    CountDownLatch latch = null;

    public Waiter(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        System.out.println("Waiter start ......" );
        try {
            latch.await();//
            System.out.println("Decrementer .latch.getCount():" + this.latch.getCount()+"  ...【over】...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Waiter Released");
    }
}

