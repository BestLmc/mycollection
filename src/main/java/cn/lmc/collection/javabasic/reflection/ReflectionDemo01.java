package main.java.cn.lmc.collection.javabasic.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * ReflectionDemo01
 * 反射测试类
 * @author limingcheng
 * @Date 2019/12/10
 */
public class ReflectionDemo01 {

    private String name;
    public int age;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 正常操作类
     */
    public static void normalOperation(){
        //正常操作类
        ReflectionDemo01 one=new ReflectionDemo01();
        one.setName("我");
        //我好帅
        System.out.println(one.getName()+"好帅");
    }

    /**
     * 反射操作类
     */
    public static void reflectOperation() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //  1、获取类的 Class 对象实例
        Class demo = Class.forName("main.java.cn.lmc.collection.javabasic.reflection.ReflectionDemo01");
        //  2、通过类的实例的getmethod方法得到Method对象
        Method setNameMethod = demo.getMethod("setName", String.class);
        //  3、根据class对象实例的getconstructor方法获取Constructor对象
        Constructor demooneConstructor = demo.getConstructor();
        //  4、使用constructor对象的newinstance方法获取反射类对象实例
        Object newInstance = demooneConstructor.newInstance();
        //  5、利用invoke方法调用方法
        setNameMethod.invoke(newInstance, "我");
        //  6、获取方法的method对象
        Method getName =demo.getMethod("getName");
        //  我
        System.out.println(getName.invoke(newInstance)+"超级帅");
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // 正常操作类
        normalOperation();
        // 反射操作类
        reflectOperation();
    }

}
