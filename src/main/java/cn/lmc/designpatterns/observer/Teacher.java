package main.java.cn.lmc.designpatterns.observer;

/**
 * Teacher
 *
 * @author limingcheng
 * @Date 2020/9/8
 */
public class Teacher extends Subject {

    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
        // 被观察者状态发生变化，通知观察者
        this.notifyAllObservers();
    }

}
