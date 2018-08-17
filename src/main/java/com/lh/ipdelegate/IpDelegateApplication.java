package com.lh.ipdelegate;

import com.lh.ipdelegate.thread.IPPutBlockQueueThread;
import com.lh.ipdelegate.thread.IPTakeBlockQueueThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
public class IpDelegateApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(IpDelegateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("开始创建线程池");
		//线程池
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		log.info("提交获取ip线程到线程池");
		//提交到线程
		executorService.submit(new IPPutBlockQueueThread());
		executorService.submit(new IPTakeBlockQueueThread());
		log.info("启动完成");
		//控制台输入take 则从队列中获取一个ip
//		executorService.shutdown();
	}





}
