package main.java.cn.lmc.collection.retrieval.web.analyzer;

import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.*;
import org.ansj.util.MyStaticValue;


/**
 * AnsjAnalyzerTest
 *
 * @author limingcheng
 * @Date 2019/11/26
 */
public class AnsjAnalyzerTest {



    /**
     * 基本分词(BaseAnalysis)
     * 速度快
     */
    public static void BaseAnalysisTest(){
        String words = "让战士们过一个欢乐祥和的新春佳节。";
        System.out.println(BaseAnalysis.parse(words));
    }

    /**
     * 精准分词(ToAnalysis)
     * 精准分词方式兼顾精度与速度，比较均衡
     */
    public static void ToAnalysisTest(){
        String words = "让战士们过一个欢乐祥和的新春佳节。";
        System.out.println(ToAnalysis.parse(words));
    }

    /**
     * NLP分词(NlpAnalysis)
     * NLP分词方式可是未登录词，但速度较慢
     */
    public static void NlpAnalysisTest(){
        String words = "洁面仪配合洁面深层清洁毛孔 清洁鼻孔面膜碎觉使劲挤才能出一点点皱纹 " +
                "脸颊毛孔修复的看不见啦 草莓鼻历史遗留问题没辙 脸和脖子差不多颜色的皮肤才是健康的 " +
                "长期使用安全健康的比同龄人显小五到十岁 28岁的妹子看看你们的鱼尾纹。";
        System.out.println(NlpAnalysis.parse(words));
    }

    /**
     * 面向索引分词(IndexAnalysis)
     */
    public static void IndexAnalysisTest(){
        String words = "洁面仪配合洁面深层清洁毛孔 清洁鼻孔面膜碎觉使劲挤才能出一点点皱纹";
        System.out.println(IndexAnalysis.parse(words));
    }

    /**
     * 自定词典分词(DicLibrary)
     * 动态添加
     */
    public static void DicLibraryTest(){
        //添加自定义词语 【 英文，按照小写配置。（大写，不识别。拆词的结果，也转为小写了）】
        DicLibrary.insert(DicLibrary.DEFAULT, "基于java", "n", 1);

        String text = "基于Java开发的轻量级的中分分词工具包";

        System.out.println(DicAnalysis.parse(text));
    }

    /**
     * 自定词典分词(DicLibrary)
     * 路径获取
     */
    public static void DicLibraryPath(){
        // 关闭名字识别
        MyStaticValue.isNameRecognition = false;
        // 配置自定义词典的位置。注意是绝对路径
        MyStaticValue.ENV.put(DicLibrary.DEFAULT, "E:\\indexDir\\library\\default.dic");

        String text = "基于Java开发的轻量级的中分分词工具包";

        System.out.println(DicAnalysis.parse(text));
    }

    /**
     * 自定词典分词(DicLibrary)
     * 配置文件
     */
    public static void DicLibraryProperties(){
        String text = "基于Java开发的轻量级的中分分词工具包";

        System.out.println(DicAnalysis.parse(text));
    }

    public static void main(String[] args) {
        // 基本分词
//        BaseAnalysisTest();
//        // 精准分词
//        ToAnalysisTest();
//        // NLP分词
//        NlpAnalysisTest();
//        // 面向索引分词
//        IndexAnalysisTest();
        // 词典分词(动态添加)
//        DicLibraryTest();
        // 词典分词(路径)
//        DicLibraryPath();
        // 词典分词(配置文件)
        DicLibraryProperties();
    }
}
