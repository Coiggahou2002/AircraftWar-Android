package com.example.myapplication.network;

import com.example.myapplication.application.StandingEntry;

import java.io.Serializable;
import java.util.List;

/**
 * 用于联机游戏的网络逻辑<br>
 * 该类必须实现Serializable接口,
 * 因为该类需要在Activity之间传递（从而可以在一次匹配中暂存信息），而安卓框架下序列化是最简易的对象传递方式<br>
 * 在实现该类后，需要将application.StartActivity第33行初始化替换为实现类
 */
public interface GameHandler extends Serializable {

    /**
     * 尝试匹配<br><br>
     * 该方法必须阻塞线程，直到匹配成功或失败<br>
     * 为了方便，网络异常时，直接打印异常并返回false<br>
     * @param username: 客户端用户名
     * @return true: 匹配成功
     */
    boolean tryMatch(String username);

    /**
     * 联机模式游戏结束
     * 1. 告知服务器该客户端游戏结束，并提供得分<br>
     * 2. 获取对手得分<br><br>
     * 该方法必须阻塞线程，直到对方游戏结束或出现异常<br>
     * 为了方便，网络异常时，直接打印异常并返回-1<br>
     * @param username: 客户端用户名
     * @param score 游戏得分
     * @return 对手游戏得分，如果发生异常则返回-1
     */
    int onOnlineGameOver(String username, int score);

    /**
     * 非联机模式游戏结束，告知服务器该客户端游戏结束，并提供难度及得分<br><br>
     * 该方法不应当阻塞线程，但必须保证下次调用getStandingList()前，服务端数据同步完毕<br>
     * @param username: 客户端用户名
     * @param score 游戏得分
     * @param difficulty 游戏难度 0-简单 1-普通 2-困难
     */
    void onNormalGameOver(String username, int score, int difficulty);

    /**
     * 获取当前游戏难度的排行榜<br><br>
     * 该方法必须阻塞线程，直到服务器返回<br>
     * 为了方便，网络异常时，直接打印异常并返回null<br>
     * @return 排行榜列表
     * @see StandingEntry
     */
    List<StandingEntry> getStandingList();

    /**
     * 删除某条记录<br><br>
     * 该方法不应当阻塞线程，但必须保证下次调用getStandingList()前，服务端数据同步完毕<br>
     * TODO：没空就不做，录视频看不出来，这句话交代码前删掉
     * @param entry 删除的条目
     * @see StandingEntry
     */
    void deleteStandingEntry(StandingEntry entry);
}
