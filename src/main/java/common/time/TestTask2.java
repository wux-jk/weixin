package common.time;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2017/6/7.
 */
@Component
public class TestTask2 {

    @Scheduled(cron = "32 0 11 * * ?")
    public void sayHello() {
        Date date = new Date();
        SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Hello 皮卡丘 --> " + sdm.format(date));
    }

    @Scheduled(fixedRate = 1000 * 5)
    public void sayHi() {
        Date date = new Date();
        SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Hi 羊驼 --> " + sdm.format(date));
    }
}
