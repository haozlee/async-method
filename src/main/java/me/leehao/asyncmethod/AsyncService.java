package me.leehao.asyncmethod;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Async("taskExecutor")
    public CompletableFuture<String> queryIp() throws InterruptedException {
        String url = "http://httpbin.org/ip";
        String results = restTemplate.getForObject(url, String.class);
        logger.info("查询 ip 返回：{}", results);
        // 等待 10 秒，模拟调用第三方接口较长时间返回结果
        Thread.sleep(10000L);
        return CompletableFuture.completedFuture(results);
    }

}
