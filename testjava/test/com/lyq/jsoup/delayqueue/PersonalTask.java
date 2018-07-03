package thread.test.com.lyq.jsoup.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class PersonalTask implements Delayed {
    private static int counter = 0;//
    private int id = counter++;
    private long triggerTime;
    private MethodEnum methodEnum;

    public PersonalTask(long triggerTime, MethodEnum methodEnum) {
        this.triggerTime = triggerTime;
        this.methodEnum = methodEnum;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(triggerTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        PersonalTask that = (PersonalTask) o;
        if (triggerTime < that.triggerTime) return -1;
        if (triggerTime > that.triggerTime) return 1;
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(long triggerTime) {
        this.triggerTime = triggerTime;
    }

    public MethodEnum getMethodEnum() {
        return methodEnum;
    }

    public void setMethodEnum(MethodEnum methodEnum) {
        this.methodEnum = methodEnum;
    }
}