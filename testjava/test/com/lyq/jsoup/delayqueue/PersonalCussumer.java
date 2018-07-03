package thread.test.com.lyq.jsoup.delayqueue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.DelayQueue;

/**
 * Created by Administrator on 2017/1/22.
 */
public class PersonalCussumer implements Runnable {

    private DelayQueue<PersonalTask> tasks;

    public PersonalCussumer(DelayQueue<PersonalTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {//
            try {
                PersonalTask task = tasks.take();
                if (null == task) {
                    continue;
                }
                handler(task);//
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finished PersonalCussumer");
        }
        System.out.println("Finished PersonalCussumer .... over ... ");//没执行。。。
    }

    private void handler(PersonalTask task) {
        try {
            Method method = this.getClass().getMethod(task.getMethodEnum().getMethodName(), PersonalTask.class);//
//            Method method = this.getClass().getMethod(task.getMethodEnum().getMethodName(), null);//
            method.invoke(this, task);//this 指什么？
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void handlerSendMessage(PersonalTask task) {
        System.out.println("id:" + task.getId() + "  start send message");
    }

    public void handlerAutoBuy(PersonalTask task) {
        System.out.println("id:" + task.getId() + "  start auto buy");
    }
}
