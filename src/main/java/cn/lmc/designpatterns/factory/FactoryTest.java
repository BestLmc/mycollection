package main.java.cn.lmc.designpatterns.factory;

/**
 * FactoryTest
 *
 * @author limingcheng
 * @Date 2020/2/20
 */
public class FactoryTest {

    public static void main(String[] args) {
        SendFactory factory = new SendFactory();
        Sender sender = factory.produce("sms");
        sender.Send();
    }
}
