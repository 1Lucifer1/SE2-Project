package com.example.hotel.blImpl.order;


import com.example.hotel.bl.order.OrderService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Configuration
@EnableScheduling
public class ErrorTimerTask{
    private static Logger log = Logger.getLogger(ErrorTimerTask.class);

    @Autowired
    OrderService orderService;

    /**
     * 安排指定的任务在指定的时间开始进行重复的固定延迟执行。
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void run() {
        try{
            // 定时执行内容
            orderService.timingErrorOrder();
            System.out.println("执行成功");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            log.info("-------------解析信息发生异常--------------");
        }
    }
}
