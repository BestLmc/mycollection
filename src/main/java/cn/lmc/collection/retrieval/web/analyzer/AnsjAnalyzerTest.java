package main.java.cn.lmc.collection.retrieval.web.analyzer;

import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;


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

    public static void main(String[] args) {
        // 基本分词
        BaseAnalysisTest();
        // 精准分词
        ToAnalysisTest();
        // NLP分词
        NlpAnalysisTest();
        // 面向索引分词
        IndexAnalysisTest();
    }
}
