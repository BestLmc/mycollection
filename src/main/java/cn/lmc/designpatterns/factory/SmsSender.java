package main.java.cn.lmc.designpatterns.factory;

/**
 * SmsSender
 *
 * @author limingcheng
 * @Date 2020/2/20
 */
public class SmsSender implements Sender {

    @Override
    public void Send() {
        System.out.println("this is sms sender!");
    }
}