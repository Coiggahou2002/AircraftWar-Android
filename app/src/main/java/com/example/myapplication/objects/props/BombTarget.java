package com.example.myapplication.objects.props;

/**
 * 对炸弹爆炸产生响应的对象
 * <br>实现该接口的对象，应当在创建时将自己加入列表
 * @author Wu Zekai
 */
public interface BombTarget {
    /**
     * 炸弹爆炸时的行为
     */
    void onBombActive();

    /**
     * 是否仍可被炸弹摧毁的标志
     * @return 如果合法，返回true
     */
    boolean notValid();
}
