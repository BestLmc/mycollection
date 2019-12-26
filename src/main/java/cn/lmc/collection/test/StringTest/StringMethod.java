package main.java.cn.lmc.collection.test.StringTest;

/**
 * stringMethod
 *
 * @author limingcheng
 * @Date 2019/12/24
 */
public class StringMethod {

    /**
     * 分割
     */
    private static void stringSplit(){
        String ips = "192.168.1.2,192.168.*.*,192.168.25.128";
        String[] iplist = ips.split("\\|");
        for(String ip:iplist){
            System.out.println("该ip为："+ip);
        }
    }

    /**
     * 包含
     */
    private static void stringContains(){

        String[] ips = {"192.168.1.2","192.168.*.*","*.*.*.*"};
        String loginIp = "192.168.1.2";

        for (String iplist:ips){
            if(iplist.contains("*")){
                iplist = iplist.substring(0, iplist.indexOf("*"));
            }

            if(loginIp.contains(iplist)){
                System.out.println("该ip可以放行:"+loginIp);
                System.out.println("匹配ip:"+iplist);
            }
        }

    }

    public static void main(String[] args) {
        // 包含
        stringContains();
        // 切割
//        stringSplit();
    }
}
