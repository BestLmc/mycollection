package main.java.cn.lmc.collection.retrieval.web.analyzer;

import org.ansj.domain.Result;
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
        String words = "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine";
        System.out.println(BaseAnalysis.parse(words));
    }

    /**
     * 精准分词(ToAnalysis)
     * 精准分词方式兼顾精度与速度，比较均衡
     */
    public static void ToAnalysisTest(){
        String words = "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine。";
        System.out.println(ToAnalysis.parse(words));
    }

    /**
     * NLP分词(NlpAnalysis)
     * NLP分词方式可是未登录词，但速度较慢
     */
    public static void NlpAnalysisTest(){
        String words = "佛山市南海尚铝五金制品有限公司 91440101304471127J";
        System.out.println(NlpAnalysis.parse(words));
        Result result = NlpAnalysis.parse(words);
        System.out.println(result.toString());
        System.out.println(result.getTerms().toString());
    }

    /**
     * 面向索引分词(IndexAnalysis)
     */
    public static void IndexAnalysisTest(){
        String words = "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine";
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
        IndexAnalysisTest();
        // 词典分词(动态添加)
//        DicLibraryTest();
        // 词典分词(路径)
//        DicLibraryPath();
        // 词典分词(配置文件)
//        DicLibraryProperties();
    }
}
