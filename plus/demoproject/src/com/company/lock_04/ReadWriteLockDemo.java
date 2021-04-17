package com.company.lock_04;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁验证
 * */
@SuppressWarnings("Convert2Lambda")
public class ReadWriteLockDemo {

    public static void main(String[] args) {

        Cache cache = new Cache();

        //写入
        for(int i = 0 ; i < 5 ; i++){
            int temp = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cache.set(temp+"",temp+"");
                }
            },i+" write").start();
        }
        //读取
        for(int i = 0 ; i < 5 ; i++){
            int temp = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cache.get(temp+"");
                }
            },i+" read").start();
        }

    }

}

/**
 * 资源类 - 模拟分布式缓存
 * */
class Cache{

    /** 缓存 k,v 键值对 */
    private volatile Map<String,Object> map = new ConcurrentHashMap<>();

    //这玩意能满足数据一致，但是满足不了我们的读共享（并发性）需求
    //private Lock lock = new ReentrantLock();

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    //缓存三大基本方法，读、写、清空

    public void set(String key,Object value){
        rwLock.writeLock().lock();
        try{
            if(key != null && key !=""){
                //模拟网络拥堵
                System.out.println(Thread.currentThread().getName() + " " +key + " 正在写入 " + value);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(key,value);
                System.out.println(Thread.currentThread().getName() + " " +key + " 写入 " + value + " 完成，写操作独占不可中断");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            rwLock.writeLock().unlock();
        }
    }

    public Object get(String key){
        rwLock.readLock().lock();
        try{
            //模拟网络拥堵
            System.out.println(Thread.currentThread().getName() + " " +key + " 正在读取 ");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + " " +key + " 读取 " + o + " 完成，读可共享~");

            return o;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally {
            rwLock.readLock().unlock();
        }

    }

    public void remove(String key){
        if(key == null || key == ""){
            return ;
        }
        map.remove(key);
    }

    public void clear(){
        map.clear();
    }

}
