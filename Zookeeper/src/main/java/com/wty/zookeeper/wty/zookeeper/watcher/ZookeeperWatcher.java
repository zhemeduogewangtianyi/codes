package com.wty.zookeeper.wty.zookeeper.watcher;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ZookeeperWatcher implements Watcher {

    /** 定义原子变量 */
    AtomicInteger seq = new AtomicInteger();

    /** 定义session失效时间 */
    private static final int SESSION_TIMEOUT = 100000;

    /** zookeeper服务地址 */
    private static final String CONNECT_ADDR = "192.168.112.140:2181,192.168.112.141:2181,192.168.112.142:2181";

    /** zk父路径设置 */
    private static final String PARENT_PATH = "/p";

    /** zk子路径设置 */
    private static final String CHILDREN_PATH = "/p/c";

    /** 进入标识 */
    private static final String LOG_PERFIX_OF_MAIN = "【Main】 ";

    /** zk变量 */
    private ZooKeeper zk = null;

    /** 用于等待zookeeper连接建立之后，通知阻塞程序继续向下执行 */
    private CountDownLatch connectedSemaphore = new CountDownLatch(1);

    /**
     * 创建zk连接
     */
    public void createConnection(String connectedAddr,int sessionTimeout) throws InterruptedException {
        this.close();
        try{
            this.zk = new ZooKeeper(connectedAddr,sessionTimeout,this);
            System.err.println(LOG_PERFIX_OF_MAIN + "开始连接zk服务器");
            connectedSemaphore.await();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /** zk关闭连接 */
    public void close(){
        try {
            if(this.zk != null){
                System.err.println("连接关闭！");
                this.zk.close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** 创建节点 */
    public boolean createPath(String path,String data,boolean needWatch){
        boolean isOk = false;
        try {
            //因为zk的监控是一次性的，每次必须设置监控 watch
            if(!exists(path,needWatch)){
                String s = this.zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                if(!StringUtils.isEmpty(s)){
                    System.err.println(s);
                    isOk = true;
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isOk;
    }


    /** 读取指定节点数据内容 */
    public String readData(String path,boolean needWatch){
        String res = "";
        try {
            byte[] data = this.zk.getData(path, needWatch, null);
            res = new String(data);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    /** 更新指定节点数据内容 */
    public boolean writeData(String path,String data,boolean needWatch){
        boolean isOk = false;
        try {
            if(exists(path,needWatch)){
                Stat stat = this.zk.setData(path, data.getBytes(), -1);
                if(stat != null){
                    isOk = true;
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isOk;
    }

    /**
     * 删除指定节点
     * */
    public void deleteNode(String path){
        try {
            if(exists(path,false)){
                this.zk.delete(path,-1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除所有节点
     * */
    public void deleteAllTestPath(String rootPath,boolean needWatch){
        boolean exists = exists(rootPath, needWatch);
        String parentPath = rootPath + "/";
        if(exists){
            List<String> children = this.getChildrens(rootPath, needWatch);
            if(!CollectionUtils.isEmpty(children)){
                for(String path : children){
                    if(!CollectionUtils.isEmpty(getChildrens(parentPath + path,needWatch))){
                        deleteAllTestPath(parentPath + path,needWatch);
                    }else{
                        if(exists(parentPath + path,needWatch)){
                            this.deleteNode(parentPath  + path);
                        }
                    }
                }
                deleteAllTestPath(rootPath,needWatch);
            }else{
                deleteNode(rootPath);
            }
        }
    }

    /**
     * 获取子节点
     * */
    public List<String> getChildrens(String path,boolean needWatch){
        List<String> res = null;
        try {
            if(exists(path,needWatch)){
                res = this.zk.getChildren(path,needWatch);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 判断节点是否存在
     * */
    public boolean exists(String path,boolean needWatch){
        boolean isOk = false;
        try {
            Stat exists = this.zk.exists(path, needWatch);
            if(exists != null){
                isOk = true;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isOk;
    }

    /**
     * 收到来自Server的Watcher通知后的处理
     * */
    @Override
    public void process(WatchedEvent watchedEvent) {

        if(watchedEvent == null){
            return;
        }

        //连接状态
        Event.KeeperState state = watchedEvent.getState();
        //事件类型
        Event.EventType type = watchedEvent.getType();
        //受影响的path
        String path = watchedEvent.getPath();
        //原子对象seq 记录进入process的次数
        int i = this.seq.incrementAndGet();
        System.err
                .println("进入次数 -> " + i);

        //状态判断
        if(Event.KeeperState.SyncConnected == state){

            if(Event.EventType.None == type){
                System.err.println("成功连接上zk服务器");
                this.connectedSemaphore.countDown();
            } else if(Event.EventType.NodeCreated == type){
                System.err.println(i + " 创建节点 " + path);
            }else if(Event.EventType.NodeDataChanged == type){
                System.err.println(i + " 节点数据更新 " + path);
            }else if (Event.EventType.NodeChildrenChanged == type){
                System.err.println(i + " 更新子节点 " + path);
            }else if (Event.EventType.NodeDeleted == type){
                System.err.println(i + " 节点 " + path + " 被删除");
            }else;

        }else if (Event.KeeperState.Disconnected == state){
            System.err.println("与ZK服务器断开连接");
        }else if (Event.KeeperState.AuthFailed == state){
            System.err.println("权限检查失败");
        }else if(Event.KeeperState.Expired == state){
            System.err.println("会话超时");
        }else;

        System.err.println("------------------------------------------");

    }


    public static void main(String[] args) throws InterruptedException {

        //建立watcher , 当前客户端可以成为一个 watcher 观察者角色
        ZookeeperWatcher zookeeperWatcher = new ZookeeperWatcher();

        //创建连接
        zookeeperWatcher.createConnection(CONNECT_ADDR,SESSION_TIMEOUT);

        //清理节点
        zookeeperWatcher.deleteAllTestPath(PARENT_PATH,true);

        /*
            第一步创建父节点，当前boolean watch操作是让我们上下文已经存在的 Watcher 监听 watch 这个节点
            watch = true 代表当前上下文的 watcher 对象 是否需要对 /p 这个节点进行watch
            是否客户端要对服务器的 /p 节点进行监听？
        */
        boolean parentNode = zookeeperWatcher.createPath(PARENT_PATH, "parentNode value", true);
        if(parentNode){

            //第二步 读取节点 /p 和 读取 节点 读取 /p 节点下的子节点（getChildren）的区别
            //读取数据 /p
            String s = zookeeperWatcher.readData(PARENT_PATH,true);
            System.err.println("查询出来的root节点内容" + s);

            //更新数据
            zookeeperWatcher.writeData(PARENT_PATH,"update parentNode data",true);

            //创建子节点
            boolean childrenNode = zookeeperWatcher.createPath(CHILDREN_PATH, "i am a children", true);

            //建立子节点的触发
            zookeeperWatcher.createPath(PARENT_PATH + "/c1",System.currentTimeMillis()+"",true);
            zookeeperWatcher.createPath(PARENT_PATH + "/c1/c2",System.currentTimeMillis()+"",true);

        }

        //清理节点
//        zookeeperWatcher.deleteAllTestPath(true);

        //关闭连接
        zookeeperWatcher.close();
    }
}
