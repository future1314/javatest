package thread.test.thread.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskCountDownLatch {
        public static void main(String[] args) throws InterruptedException {
            AtomicInteger count = new AtomicInteger(0);
            int taskNum = 10;
            CountDownLatch ready_latch = new CountDownLatch(taskNum);
            CountDownLatch begin_latch = new CountDownLatch(1);
            CountDownLatch end_latch = new CountDownLatch(taskNum);
            Executor executor = Executors.newCachedThreadPool();
            System.out.println("主线程--> 开始分发任务。。。");
            for(int i=1; i<=taskNum; i++){
                Task task = new Task(String.valueOf(i), ready_latch, begin_latch, end_latch, count);
                executor.execute(task);
            }
            System.out.println("主线程--> 等待所有子任务准备就绪。。。");
            ready_latch.await();
            System.out.println("主线程--> 所有子任务已准备就绪，通知子任务执行。。。");
            begin_latch.countDown();
            end_latch.await();
            System.out.println("主线程--> 所有子任务执行完毕，获得结果："+count.get());
        }
}
