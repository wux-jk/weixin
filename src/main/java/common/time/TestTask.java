package common.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2017/6/7.
 */
public class TestTask {

    public void task1() {
        Date date = new Date();
        SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("这是我的第一个定时任务 --> " + sdm.format(date));
    }

    public void task2() {
        Date date = new Date();
        SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("革命尚未成功，同志仍需努力 --> " + sdm.format(date));
    }
}
