package thread.test.com.lyq.jsoup.delayqueue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/1/22.
 */
public class ThreadPoolUtils {
    public static void main(String[] args){
        //存放任务处理的队列
        DelayQueue<PersonalTask> delayQueue = new DelayQueue();

        //初始化线程池
        ArrayBlockingQueue<Runnable> arrayWorkQueue = new ArrayBlockingQueue(10);
        ExecutorService threadPool = new ThreadPoolExecutor(5,
                10,
                60L,
                TimeUnit.SECONDS,
                arrayWorkQueue,
                new ThreadPoolExecutor.AbortPolicy());

        //模拟10条数据 这里可以是从数据库里面查询出满足条件的数据
        for (int i = 0;i<10;i++){
            delayQueue.put(getPersonalTask());
        }
        threadPool.execute(new PersonalCussumer(delayQueue));///
        threadPool.shutdown();
        System.out.println(Thread.currentThread().getName());
    }

    public static PersonalTask getPersonalTask(){
        Random random = new Random();
        int randomAddMilliSenconds = random.nextInt(20);

        long triggerTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(randomAddMilliSenconds, TimeUnit.SECONDS);

        int methodIndex = new Random().nextInt(2);
        MethodEnum methodEnum = MethodEnum.getMethodEnumByIndex(methodIndex);
        PersonalTask task = new PersonalTask(triggerTime,methodEnum);//

        //调适语句 可以忽略
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        System.out.println(sdf.format(new Date(task.getTriggerTime())));

        return task;
    }
}
