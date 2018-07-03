package thread.test.queueTest.test;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueTest {

    public static void main(String[] args) {
        DelayQueue queue = new DelayQueue();

        Delayed element1 = new DelayedElement();

        queue.put(element1);

        //Delayed element2 = queue.take();
    }
}

class DelayedElement implements Delayed{
    public int compareTo(Delayed o){
        return 1;
    }

    public long getDelay(TimeUnit unit){
        return 1;//TimeUnit.MILLISECONDS.;
    }

}
