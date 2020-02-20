package main.java.cn.lmc.designpatterns.factory;

/**
 * MailSender
 *
 * @author limingcheng
 * @Date 2020/2/20
 */
public class MailSender implements Sender {
    @Override
    public void Send() {
        System.out.println("this is mailsender!");
    }
}
