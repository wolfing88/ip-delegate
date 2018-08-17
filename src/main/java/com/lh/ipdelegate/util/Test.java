package com.lh.ipdelegate.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.lh.ipdelegate.model.IPInfo;
import com.lh.ipdelegate.queue.IpBlockQueue;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;

@Slf4j
public class Test {


    public static void main(String[] args) throws Exception {

        Document document = HttpUtils.getInstance().executeGetWithSSLAsDocument("https://pan.baidu.com/s/1GpENiFXkKHQTSWrQFN7rOg");
        System.out.println(document.html());


//        IPInfo ipInfo = IpBlockQueue.getInstance().takeIpFromQueue();
//        htmlUnitUrl("https://pan.baidu.com/s/1GpENiFXkKHQTSWrQFN7rOg",ipInfo);
    }



}
