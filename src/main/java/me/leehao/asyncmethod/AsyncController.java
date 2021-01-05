package me.leehao.asyncmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class AsyncController {
    private static final Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Resource
    private AsyncService asyncService;

    @RequestMapping(value = "/async", method = RequestMethod.GET)
    public String async() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        CompletableFuture<String> ip1 = asyncService.queryIp();
        CompletableFuture<String> ip2 = asyncService.queryIp();
        CompletableFuture<String> ip3 = asyncService.queryIp();

        CompletableFuture.allOf(ip1, ip2, ip3).join();

        float exc = (float)(System.currentTimeMillis() - start)/1000;
        logger.info("完成所有查询所用时间：{}", exc);

        return String.format("ip1: %s, ip2: %s, ip3: %s",
                ip1.get(), ip2.get(), ip3.get());
    }
}
