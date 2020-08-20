package main.java.cn.lmc.collection.javabasic.datetime;

import java.util.Calendar;
import java.util.Date;

/**
 * DatetimeTest
 *
 * @author limingcheng
 * @Date 2020/2/26
 */
public class DatetimeTest {


    /**
     * 获取当前季节的第一天或最后一天
     * @return
     */
    private String getSessionFE(Date today, String type) {
        // 回参
        String rtday = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int month = cal.get(cal.MONTH) + 1;
        int year = cal.get(cal.YEAR);
        int quarter = 0;
        // 判断季度
        if (month >= 1 && month <= 3) {
            quarter = 1;
        } else if (month >= 4 && month <= 6) {
            quarter = 2;
        } else if (month >= 7 && month <= 9) {
            quarter = 3;
        } else {
            quarter = 4;
        }

        String startDay = "";
        String endDay = "";
        switch (quarter){
            case 1://第一季度
                startDay = year + "-01-01";
                endDay = year + "-03-31";
                break;
            case 2://第二季度
                startDay = year + "-04-01";
                endDay = year + "-06-30";
                break;
            case 3://第三季度
                startDay = year + "-07-01";
                endDay = year + "-09-30";
                break;
            case 4://第四季度
                startDay = year + "-10-01";
                endDay = year + "-12-31";
                break;
            default:
                break;
        }

        // 若type==F,返回第一天; 若type==E,返回最后一天
        if("F".equals(type)) {
            rtday = startDay;
        } else if("E".equals(type)) {
            rtday = endDay;
        }

        return rtday;
    }

    public static void main(String[] args) {

        DatetimeTest dt = new DatetimeTest();
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        // 获取当前季节的第一天和最后一天
        String startday = dt.getSessionFE(cal.getTime(), "F");
        String endday = dt.getSessionFE(cal.getTime(), "E");
        System.out.println("startday:"+startday);
        System.out.println("endday:"+endday);
    }


}
