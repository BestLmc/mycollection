package main.java.cn.lmc.collection.retrieval.web.analyzer;



import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.lionsoul.jcseg.analyzer.JcsegAnalyzer;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;

/**
 * JcsegAnalyzerTest
 * 1.JcsegTaskConfig.COMPLEX_MODE 为复杂模式：
 *  特点：四种过滤算法。
 *  2.JcsegTaskConfig.SIMPLE_MODE 为简易模式 特点：只使用了最大化过滤算法，其他的同复杂模式。
 * @author limingcheng
 * @Date 2019/11/26
 */
public class JcsegAnalyzerTest {

    // 检索内容
    static String text = "jcseg是使用 Java 开发的一款开源的中文分词器, 基于流行的mmseg算法实现，分词准确率高达98.4%, 支持中文人名识别, 同义词匹配, 停止词过滤等。并且提供了最新版本的lucene,solr,elasticsearch分词接口。";

    public static void main(String[] args) throws Exception {
        Analyzer analyzer = new JcsegAnalyzer(JcsegTaskConfig.NLP_MODE);
        //available constructor:
        //1, JcsegAnalyzer(int mode)
        //2, JcsegAnalyzer(int mode, String proFile)
        //3, JcsegAnalyzer(int mode, JcsegTaskConfig config)
        //4, JcsegAnalyzer(int mode, JcsegTaskConfig config, ADictionary dic)

        // 非必须(用于修改默认配置): 获取分词任务配置实例
        JcsegAnalyzer jcseg = (JcsegAnalyzer) analyzer;
        JcsegTaskConfig config = jcseg.getTaskConfig();
        // 追加同义词到分词结果中, 需要在 jcseg.properties 中配置 jcseg.loadsyn=1
        config.setAppendCJKSyn(true);
        // 追加拼音到分词结果中, 需要在 jcseg.properties 中配置 jcseg.loadpinyin=1
        config.setAppendCJKPinyin(true);
        // 更多配置, 请查看 com.webssky.jcseg.core.JcsegTaskConfig 类
        config.setICnName(true);

        String keyword = "孙悟空，杨超越";

        // 使用QueryParser查询分析器构造Query对象
        QueryParser qp = new QueryParser("text", analyzer);
        qp.setDefaultOperator(QueryParser.AND_OPERATOR);
        Query query = qp.parse(keyword);
        System.out.println("Query = " + query);
    }



}
