package com.example.myapplication;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * @author Wu Zekai
 */
public class Utils {

    /**
     * 随机数发生器
     */
    private static final Random RANDOM = new Random();

    /**
     * 按给定频率返回随机下标，支持概率和为任意值。
     * @param probability 随机到每个下标的概率
     * @return 随机下标
     */
    public static int getByRandom(List<Double> probability) {
        double total = 0.0;
        for(Double p : probability) {
            total += p;
        }
        double criterion = RANDOM.nextDouble() * total;
        double current = 0.0;
        for(int i = 0; i < probability.size(); i++) {
            current += probability.get(i);
            if(current >= criterion) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return 返回随机运动方向
     */
    public static int getDirectionByRandom() {
        return RANDOM.nextDouble() > 0.5 ? 1 : -1;
    }

//    /**
//     * 时间格式化器
//     */
//    private static final DateTimeFormatter DATE_TIME_FORMATTER
//            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
//            .withLocale(Locale.CHINA )
//            .withZone(ZoneId.systemDefault());

//    /**
//     * @return 返回当前日期格式化后的字符串
//     */
//    public static String getDate() {
//        return DATE_TIME_FORMATTER.format(Instant.now());
//    }

}

