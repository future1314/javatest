package thread.test;

import java.util.concurrent.Exchanger;

class ExchangerRunnable implements Runnable{

    Exchanger exchanger = null;
    Object    object    = null;

    public ExchangerRunnable(Exchanger exchanger, Object object) {
        this.exchanger = exchanger;
        this.object = object;
    }

    public void run() {
        try {
            Object previous = this.object;

            this.object = this.exchanger.exchange(this.object);

            System.out.println(
                    Thread.currentThread().getName() +
                            " exchanged " + previous + " for " + this.object
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ExchangerTest{
    public static void main(String[] args) {
        Exchanger exchanger = new Exchanger();

        ExchangerRunnable exchangerRunnable1 =
                new ExchangerRunnable(exchanger, "A");

        ExchangerRunnable exchangerRunnable2 =
                new ExchangerRunnable(exchanger, "B");
        ExchangerRunnable exchangerRunnable3 =
                new ExchangerRunnable(exchanger, "C");

        new Thread(exchangerRunnable3,"z 线程 ").start(); //不能多个线程 交换？？

        // new Thread(exchangerRunnable1,"x 线程 ").start();
        new Thread(exchangerRunnable2,"y 线程 ").start();

        
    }

}
