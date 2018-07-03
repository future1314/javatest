package thread.test.zk.lock;

//import com.google.common.base.Strings;
import org.apache.zookeeper.*;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

/**
 * Zookeepr实现分布式锁
 */
public class ZkLockTest {

    private String zkQurom = "localhost:2181";

    private String lockNameSpace = "/mylock";

    private String nodeString = lockNameSpace + "/test1";

    private Lock mainLock;

    private ZooKeeper zk;

    public ZkLockTest(){
        try {
            zk = new ZooKeeper(zkQurom, 6000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println(Thread.currentThread().getName()+" Receive event： "+watchedEvent);
                    if(Event.KeeperState.SyncConnected == watchedEvent.getState())
                        System.out.println(Thread.currentThread().getName()+" connection is established...");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void  ensureRootPath() throws InterruptedException {//yc add syc
        synchronized(ZkLockTest.class){//不加同步，第一次 创建跟节点 会报错。。。
            try {
                System.out.println(Thread.currentThread().getName() + " ensureRootPath.exits");
                if (zk.exists(lockNameSpace,true)==null){
                    System.out.println(Thread.currentThread().getName() + " ensureRootPath.create");
                    zk.create(lockNameSpace,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }
    }

    private void watchNode(String nodeString, final Thread thread) throws InterruptedException {
        try {
            zk.exists(nodeString, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println(Thread.currentThread().getName() + " ==" + watchedEvent.toString());
                    if(watchedEvent.getType() == Event.EventType.NodeDeleted){
                        System.out.println(Thread.currentThread().getName() + " Threre is a Thread released Lock==============."+thread.getName()+" thread will be interrupt");
                        thread.interrupt();
                    }
                    try {
                        zk.exists(nodeString,new Watcher() {
                            @Override
                            public void process(WatchedEvent watchedEvent) {
                                System.out.println(Thread.currentThread().getName() +  " ==2" + watchedEvent.toString());
                                if(watchedEvent.getType() == Event.EventType.NodeDeleted){
                                    System.out.println(Thread.currentThread().getName() + " Threre is a Thread released Lock2==============."+thread.getName()+" thread will be interrupt");
                                    thread.interrupt();
                                }
                                try {
                                    System.out.println(Thread.currentThread().getName() + " exists path ==3");
                                    zk.exists(nodeString,true);///
                                    /**
                                     *exists()方法仅仅监控对应节点的一次数据变化，无论是数据修改还是删除！
                                     * 若要每次对应节点发生变化都被监测到，那么每次都得先调用exists()方法获取一遍节点状态！
                                     */
                                } catch (KeeperException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        });
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取锁
     * @return
     * @throws InterruptedException
     */
    public boolean lock() throws InterruptedException {
        String path = null;
        ensureRootPath();
        watchNode(nodeString,Thread.currentThread());
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + "  will create a path >> path creating ");
                path = zk.create(nodeString, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            } catch (KeeperException e) {
                System.out.println(Thread.currentThread().getName() + "  getting Lock but can not get>> path create err");
                try {
                    System.out.println(Thread.currentThread().getName() + "  thread.sleep(5000)  >> path create err");
                    Thread.sleep(5000);
                }catch (InterruptedException ex){
                    System.out.println(Thread.currentThread().getName()+"  thread is notify>> path create err");
                }
            }
            //if (!Strings.nullToEmpty(path).trim().isEmpty()) {
            if (!StringUtils.isEmpty(path)) {
                System.out.println(Thread.currentThread().getName() + "  get Lock...");
                return true;
            }
        }
    }

    /**
     * 释放锁
     */
    public void unlock(){
        try {
            /**
             * java.lang.InterruptedException
             at java.lang.Object.wait(Native Method)
             at java.lang.Object.wait(Object.java:502)
             at org.apache.zookeeper.ClientCnxn.submitRequest(ClientCnxn.java:1278)
             at org.apache.zookeeper.ZooKeeper.delete(ZooKeeper.java:726)
             at zk.lock.ZkLockTest.unlock(ZkLockTest.java:132)
             at zk.lock.ZkLockTest.lambda$main$0(ZkLockTest.java:152)
             at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
             at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
             at java.lang.Thread.run(Thread.java:748)
             */
            System.out.println(Thread.currentThread().getName()+ " will  release Lock...");
            zk.delete(nodeString,-1);//任意版本都会删除
            System.out.println(Thread.currentThread().getName()+ "  released Lock...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0;i<6;i++){
            service.execute(()-> {
                ZkLockTest test = new ZkLockTest();
                try {
                    test.lock();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.unlock();
            });
        }
        service.shutdown();
    }
}
