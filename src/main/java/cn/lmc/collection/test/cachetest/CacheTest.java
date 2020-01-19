package main.java.cn.lmc.collection.test.cachetest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * CacheTest
 *
 * @author limingcheng
 * @Date 2020/1/7
 */
public class CacheTest {

//
//    @RunWith(SpringJUnit4ClassRunner.class)
//    @ContextConfiguration(classes = {RootConfig.class, CacheConfig.class})
//    public class TestSpringBean {
//
//        @Autowired
//        private CacheDemoService cacheDemoService;
//        @Autowired
//        private CacheManager cacheManager;
//
//        @Test
//        public void test1() {
//            cacheDemoService.getFromDB(1);
//            cacheDemoService.getFromDB(1);
//
//            System.out.println("----------验证缓存是否生效----------");
//            Cache cache = cacheManager.getCache("demoCache");
//            System.out.println(cache);
//            System.out.println(cache.get(1, String.class));
//        }
//
//    }
//
//    public static void main(String[] args) {
////        Cache<String, String> cache = Caffeine.newBuilder()
////                .expireAfterWrite(1, TimeUnit.SECONDS)
////                .expireAfterAccess(1,TimeUnit.SECONDS)
////                .maximumSize(10)
////                .build();
////        cache.put("hello","hello");
//    }

}
