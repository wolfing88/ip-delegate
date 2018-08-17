package com.lh.ipdelegate.queue;

import com.lh.ipdelegate.model.IPInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
public class IpBlockQueue {

    //内存队列 满载10
    private ArrayBlockingQueue<IPInfo> queues = new ArrayBlockingQueue<IPInfo>(10);


    //存放一个ip
    public void putIpToQueue(IPInfo ipInfo){
        try {
            queues.put(ipInfo);
            log.info("添加ip到队列,总量:"+queues.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("error:",e.getMessage());
        }
    }

    //拿出一个ip
    public IPInfo takeIpFromQueue(){
        try {
            IPInfo ipInfo = queues.take();
            log.info("从队列拿出ip，剩余总量:"+queues.size());
            return ipInfo;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 静态内部类
     *
     * jvm 内部类的初始化一定只会发生一次
     */
    private  static class Singleton{
        private  static IpBlockQueue instance;
        static {
            instance = new IpBlockQueue();
        }

        public static IpBlockQueue getInstance(){
            return instance;
        }
    }

    public static IpBlockQueue getInstance(){
        return IpBlockQueue.Singleton.getInstance();
    }

}
