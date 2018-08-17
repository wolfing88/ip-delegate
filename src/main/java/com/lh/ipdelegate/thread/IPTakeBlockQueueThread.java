package com.lh.ipdelegate.thread;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.event.Event;
import com.gargoylesoftware.htmlunit.javascript.host.event.MouseEvent;
import com.lh.ipdelegate.model.IPInfo;
import com.lh.ipdelegate.queue.IpBlockQueue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class IPTakeBlockQueueThread implements Runnable {


    @Override
    public void run() {

        try {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                if (line.equals("take")) {
                    IPInfo ipInfo = IpBlockQueue.getInstance().takeIpFromQueue();
                    log.info("take info:" + ipInfo);
                    htmlUnitUrl("https://pan.baidu.com/s/1GpENiFXkKHQTSWrQFN7rOg", ipInfo.getIp(), ipInfo.getPort() + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void htmlUnitUrl(String url, String ip, String port)
            throws Exception {
        WebClient client = null;
        try {
            client = new WebClient(BrowserVersion.CHROME, ip, Integer.valueOf(port));
            client.getOptions().setUseInsecureSSL(true);
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(true);
            //获取某网站页面
            HtmlPage page = client.getPage(url);
            List<HtmlAnchor> anchorList = page.getAnchors();
            anchorList.forEach(tempA->{
                MouseEvent mouseEvent = null;
                String title = tempA.getAttribute("title") ;
                if (StringUtils.isNotEmpty(title) && title.equals("提取文件")){
                    log.info("找到提取文件按钮");
                    try {
                        tempA.click(mouseEvent,false);

                        if (mouseEvent.isMouseEvent("click")){
                            log.info("是点击方法 ");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            log.info(page.asXml());

        } catch (Exception e) {
            log.error("激活连接出错", e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }
}
