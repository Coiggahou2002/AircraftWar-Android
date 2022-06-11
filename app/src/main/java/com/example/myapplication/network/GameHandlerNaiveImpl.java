package com.example.myapplication.network;

import com.example.myapplication.application.StandingEntry;

import java.util.ArrayList;
import java.util.List;

public class GameHandlerNaiveImpl implements GameHandler {

    @Override
    public boolean tryMatch(String username) {
        try {
            // 模拟匹配等待延时
            Thread.sleep(1000);
        } catch (Exception e) {
            // 模拟匹配异常处理
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public int onOnlineGameOver(String username, int score) {
        try {
            // 模拟等待对手过程
            Thread.sleep(1000);
        } catch (Exception e) {
            // 模拟匹配异常处理
            e.printStackTrace();
            return -1;
        }
        return 233;
    }

    @Override
    public void onNormalGameOver(String username, int score, int difficulty) {
        // 不阻塞线程，用空实现模拟
    }

    @Override
    public List<StandingEntry> getStandingList() {
        try {
            // 模拟等待
            Thread.sleep(1000);
        } catch (Exception e) {
            // 模拟异常处理
            e.printStackTrace();
            return null;
        }
        // 模拟数据
        List<StandingEntry> standingData = new ArrayList<>();
        standingData.add(new StandingEntry("TestName", "123", "TestTime"));
        standingData.add(new StandingEntry("TestNameNNNNN", "234", "TestTimeTTTTT"));
        return standingData;
    }

    @Override
    public void deleteStandingEntry(StandingEntry entry) {
        // 不阻塞线程，用空实现模拟
    }
}
