package com.example.myapplication.network;

public class LoginHandlerNaiveImpl implements LoginHandler {

    @Override
    public boolean tryLogin(String username, String password) {
        try {
            // 模拟网络请求延时
            Thread.sleep(1000);
        } catch (Exception e) {
            // 模拟网络异常处理
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean tryRegister(String username, String password) {
        try {
            // 模拟网络请求延时
            Thread.sleep(1000);
        } catch (Exception e) {
            // 模拟网络异常处理
            e.printStackTrace();
            return false;
        }
        // 模拟失败
        return false;
    }
}
