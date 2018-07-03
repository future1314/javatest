package thread.test.thread.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Task implements Runnable {

    private String taskCode;

    private AtomicInteger count;

    private CountDownLatch ready_latch;

    private CountDownLatch begin_latch;

    private CountDownLatch end_latch;

    public Task(String taskCode, CountDownLatch ready_latch, CountDownLatch begin_latch,
                CountDownLatch end_latch, AtomicInteger count) {
        super();
        this.taskCode = taskCode;
        this.begin_latch = begin_latch;
        this.end_latch = end_latch;
        this.ready_latch = ready_latch;
        this.count = count;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("子线程:  任务" + taskCode + "\t 准备就绪。。。1");
            ready_latch.countDown();//已准备的任务+1
            begin_latch.await();//等待开始信号
            System.out.println("子线程:  任务" + taskCode + "\t 开始执行。。。2");
            count.addAndGet(Integer.valueOf(taskCode));
            Thread.sleep(1000);
            System.out.println("子线程:  任务" + taskCode + "\t 执行完成。。。。3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            end_latch.countDown();//已完成的任务+1
        }
    }
}