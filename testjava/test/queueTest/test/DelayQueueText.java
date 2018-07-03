package thread.test.queueTest.test;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueText {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<Message> delayQueue =  new DelayQueue<>();//？？？
        for (int i=1;i<11;i++){
            Message m = new Message(i+""+new Random().nextInt(i),System.currentTimeMillis()+i*100);
//            Message m = new Message(i+"",System.currentTimeMillis()+new Random().nextInt(i)*100);
            delayQueue.add(m);
        }
        System.out.println("delayQueue put done");
        while(!delayQueue.isEmpty()){
            Message message = delayQueue.take();//此处会阻塞
            //执行广告上下架操作
            System.out.println("msg :"+ message.getId() +"   "+message.getInsertTime());
        }

    }
}

class Message  implements Delayed {
    private String id;
    private long insertTime ;//开始时间，广告上下架时间。

    public Message(String id,long insertTime){
        this.id = id;
        this.insertTime =  insertTime;
    }

    //获取失效时间
    @Override
    public long getDelay(TimeUnit unit) {
        //获取失效时间
        long delay=this.insertTime+10000-System.currentTimeMillis();
        //System.out.println("delay .. "+delay);
        return delay;
    }


    @Override
    public int compareTo(Delayed o) {
        //比较 1是放入队尾  -1是放入队头
        Message that = (Message)o;
        int flag=this.insertTime>that.insertTime?1:this.insertTime==that.insertTime?0:-1;
        System.out.println("this.insertTime .. "+this.insertTime+",that.insertTime .. "+that.insertTime+"...."+flag);
        if(this.insertTime>that.insertTime){
            return 1;
        }
        else  if(this.insertTime==that.insertTime){
            return 0;
        }else {
            return -1;
        }
    }

    public String getId() {
        return id;
    }

    public long getInsertTime() {
        return insertTime;
    }
}