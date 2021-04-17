package com.wty.zookeeper.wty.zookeeper.base;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ZookeeperBase {

    /** zookeeper地址 */
    private static final String CONNECT_ADDR = "192.168.112.140:2181,192.168.112.141:2181,192.168.112.142:2181";

    /** session超时时间 */
    private static final int SESSION_TIMEOUT = 5000;

    /** 信号量，阻塞程序执行，用于等待zookeeper连接成功，发送成功信号 */
    private static final CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {

        ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //获取事件的状态
                Event.KeeperState state = watchedEvent.getState();
                //事件的类型
                Event.EventType type = watchedEvent.getType();

                //如果时建立连接
                if(Event.KeeperState.SyncConnected == state){
                    connectedSemaphore.countDown();
                    System.out.println("zk 建立连接！");
                }
            }
        });

        //进行阻塞
        connectedSemaphore.await();
        //获取实例化对象
        System.out.println("zk实例化：" + zk.toString());

        /**
         * CreateMode.PERSISTENT 永久节点
         * CreateMode.EPHEMERAL 临时节点
         * */
//        String res = zk.create("/wty", "wangyianyi".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        String res = zk.create("/wty11", "wangyianyi".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //创建子节点
//        String res01 = zk.create("/wty/wty01", "wangyianyi01".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        String res02 = zk.create("/wty/wty02", "wangyianyi02".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.err.println(res);
//        System.err.println(res01);
//        System.err.println(res02);

        //创建父节点
//        zk.create("testRoot/child","i am a child".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,
//                new AsyncCallback.StringCallback(){
//
//                    @Override
//                    public void processResult(int resultCode, String parentPath, Object context, String name) {
//                        System.out.println("回调线程：" + Thread.currentThread().getName());
//                        System.out.println("resultCode："+resultCode);
//                        System.out.println("parentPath：" + parentPath);
//                        System.out.println("context："+context);
//                        System.out.println("name" + name);
//                    }
//
//                },"param");

        System.out.println("程序继续执行。。。");
        System.out.println("主线程：" + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(5);


        //获取节点
        /**
         * watch 和 new Watcher() 有什么关系
         * */
        byte[] data = zk.getData("/wty", false, null);
        String str = new String(data,"UTF-8");
        System.err.println(str);

        for(String path: zk.getChildren("/wty", false)){
            byte[] data1 = zk.getData("/wty/" + path, false, null);
            System.err.println(path);
            System.err.println("数据：" + new String(data1));
        }

        //修改节点 version -1 版本号，最好带上。有助于乐观锁的实现
        Stat stat = zk.setData("/wty", "{\"name\":\"wangtianyi\"}".getBytes(), -1);
        System.err.println(stat);

        byte[] data1 = zk.getData("/wty", false, null);
        System.err.println(new String(data1));

        //判断节点是否存在
        Stat exists = zk.exists("/wty", false);
        Stat exists1 = zk.exists("/wty01", false);
        System.err.println(exists);
        System.err.println(exists1);

        if(zk.exists("/wty/wty02",false) != null){
            zk.delete("/wty/wty02",-1);
        }

        zk.close();

    }

}
