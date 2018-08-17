package com.lh.ipdelegate.thread;

import com.lh.ipdelegate.model.IPInfo;
import com.lh.ipdelegate.queue.IpBlockQueue;
import com.lh.ipdelegate.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class IPPutBlockQueueThread implements Runnable {



    private HashMap<String,ArrayList<IPInfo>> ipInfoMap = new HashMap<>();

    @Override
    public void run() {
        log.info("准备获取代理ip");
        while (true){
            try {
                Document document = HttpUtils.getInstance().executeGetAsDocument("http://www.xicidaili.com/nn/");
                Element element = document.getElementById("ip_list");
                Elements elements = element.getElementsByTag("tr");
                Iterator<Element> iterator = elements.iterator();
                log.info("爬取成功");
                List<Element> elementList = elements.subList(1,elements.size()-1);
                elementList.forEach(ele -> {
                    String[] arr = ele.text().split(" ");
                    IPInfo ipInfo = new IPInfo();
                    ipInfo.setGetTime(new Date());
                    ipInfo.setIp(arr[0]);
                    ipInfo.setPort(Integer.parseInt(arr[1]));
                    ipInfo.setType(arr[4]);
                    //判断ip是否重复
                    if (!isExitIp(ipInfo)){

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                        ArrayList todayIpList = ipInfoMap.get(simpleDateFormat.format(new Date()));
                        if (todayIpList == null){
                            todayIpList = new ArrayList();
                            ipInfoMap.put(simpleDateFormat.format(new Date()),todayIpList);
                        }
                        todayIpList.add(ipInfo);
                        IpBlockQueue.getInstance().putIpToQueue(ipInfo);
                        log.info("非重复ip，存放到内存队列");
                    }else {
                        log.info("重复ip忽略");
                    }
                    //1秒钟获取一次ip
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                log.info("error:"+e.getLocalizedMessage());
            }
        }
    }

    private boolean isExitIp(IPInfo ipInfo){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmmdd");
        ArrayList todayIpList = ipInfoMap.get(simpleDateFormat.format(new Date()));
        if (todayIpList==null){
            return false;
        }else {
            return todayIpList.contains(ipInfo);
        }
    }


}
