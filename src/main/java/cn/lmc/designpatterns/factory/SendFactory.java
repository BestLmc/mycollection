package main.java.cn.lmc.designpatterns.factory;

/**
 * SendFactory
 *
 * @author limingcheng
 * @Date 2020/2/20
 */
public class SendFactory {

    public Sender produce(String type) {
        if ("mail".equals(type)) {
            return new MailSender();
        } else if ("sms".equals(type)) {
            return new SmsSender();
        } else {
            System.out.println("请输入正确的类型!");
            return null;
        }
    }
}