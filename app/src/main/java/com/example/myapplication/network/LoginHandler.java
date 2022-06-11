package com.example.myapplication.network;

/**
 * 用于注册页面的网络逻辑<br><br>
 * 在实现该类后，需要将application.LoginActivity第38行初始化替换为实现类
 */
public interface LoginHandler {

    /**
     * 尝试一次登录<br><br>
     * 该方法必须阻塞线程，直到服务器返回<br>
     * 该方法应当对密码进行加密<br>
     * 为了方便，网络异常时，直接打印异常并返回false<br>
     * @param username 用户名字符串
     * @param password 密码字符串，未被加密！
     * @return true: 登陆成功
     */
    boolean tryLogin(String username, String password);

    /**
     * 尝试一次注册<br><br>
     * 该方法必须阻塞线程，直到服务器返回<br>
     * 该方法应当对密码进行加密<br>
     * 为了方便，网络异常时，直接打印异常并返回false<br>
     * @param username 用户名字符串
     * @param password 密码字符串，未被加密！
     * @return true: 注册成功
     */
    boolean tryRegister(String username, String password);
}
