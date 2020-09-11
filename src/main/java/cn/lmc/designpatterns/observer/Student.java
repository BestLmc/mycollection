package main.java.cn.lmc.designpatterns.observer;

/**
 * Student
 *
 * @author limingcheng
 * @Date 2020/9/8
 */
public class Student implements Observer {

    private String action;

    @Override
    public void update(Subject subject) {
        String teacherAction = ((Teacher) subject).getAction();

        if (teacherAction.equals("老师来了")) {
            action = "假装学习";
        } else if (teacherAction.equals("老师走了")) {
            action = "继续打牌";
        }
    }

    public String getAction() {
        return action;
    }
}
