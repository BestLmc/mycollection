package main.java.cn.lmc.collection.test.tools;

import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * uuidTest
 *
 * @author limingcheng
 * @Date 2020/9/9
 */
public class uuidTest {

    public static void main(String[] args) {
        String id1 = UUID.randomUUID().toString().replace("-","");
        String id2 = UUID.randomUUID().toString().replace("-","");
        System.out.println(id1);
        System.out.println(id2);

        System.out.println("VALID_END_TIME".equals("VALID_END_TIME"));
        System.out.println(StringUtils.isEmpty("经办人名称"));
    }
}
