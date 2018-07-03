package thread.test.thread.test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTxst {

    public static void main(String[] args) {
        Runnable barrier1Action = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() +" BarrierAction 1 executed ");
            }
        };
        Runnable barrier2Action = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() +" BarrierAction 2 executed ");
            }
        };
        Runnable barrier3Action = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() +" BarrierAction 3 executed ");
            }
        };

        CyclicBarrier barrier1 = new CyclicBarrier(3, barrier1Action);
        CyclicBarrier barrier2 = new CyclicBarrier(3, barrier2Action);
        CyclicBarrier barrier3 = new CyclicBarrier(3, barrier3Action);

//        CyclicBarrierRunnabl barrierRunnable1 =
//                new CyclicBarrierRunnabl(barrier1, barrier2);
//
//        CyclicBarrierRunnabl barrierRunnable2 =
//                new CyclicBarrierRunnabl(barrier1, barrier2);
//
//        CyclicBarrierRunnabl barrierRunnable3 =
//                new CyclicBarrierRunnabl(barrier1, barrier2);

        CyclicBarrierRunnabl barrierRunnable1 =
                new CyclicBarrierRunnabl(barrier1, barrier2, barrier3);
        CyclicBarrierRunnabl barrierRunnable2 =
                new CyclicBarrierRunnabl(barrier1, barrier2, barrier3);
        CyclicBarrierRunnabl barrierRunnable3 =
                new CyclicBarrierRunnabl(barrier1, barrier2, barrier3);

        new Thread(barrierRunnable1).start();
        new Thread(barrierRunnable2).start();
        new Thread(barrierRunnable3).start();//3个线程
    }
}

class CyclicBarrierRunnabl implements Runnable{

    CyclicBarrier barrier1 = null;
    CyclicBarrier barrier2 = null;
    CyclicBarrier barrier3 = null;

    public CyclicBarrierRunnabl(
            CyclicBarrier barrier1,
            CyclicBarrier barrier2) {

        this.barrier1 = barrier1;
        this.barrier2 = barrier2;
    }

    public CyclicBarrierRunnabl(
            CyclicBarrier barrier1,
            CyclicBarrier barrier2,
            CyclicBarrier barrier3) {

        this.barrier1 = barrier1;
        this.barrier2 = barrier2;
        this.barrier3 = barrier3;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() +
                    " waiting at barrier 1");
            this.barrier1.await();

            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() +
                    " waiting at barrier 2");
            this.barrier2.await();

            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() +
                    " waiting at barrier 3");
            this.barrier3.await();

            System.out.println(Thread.currentThread().getName() +
                    " done!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
