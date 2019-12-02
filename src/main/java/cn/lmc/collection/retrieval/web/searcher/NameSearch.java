package main.java.cn.lmc.collection.retrieval.web.searcher;

import main.java.cn.lmc.collection.common.lucene.LuceneUtils;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.junit.Test;
import org.lionsoul.jcseg.analyzer.JcsegAnalyzer;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;

/**
 * NameSearch
 *
 * @author limingcheng
 * @Date 2019/11/25
 */
public class NameSearch {
    /**
     * lucene的写、读方法
     */
    private IndexWriter writer;
    private IndexReader reader;
    private IndexSearcher searcher;

    /**
     * 添加索引
     */
    @Test
    public void addIKIndex() {
        try {
            // 获取lucene的写入方法
            writer = LuceneUtils.getIndexWriter();

            Document doc;

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "1001", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "1002", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "1003", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "1004", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "1005", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 提交数据，第一次创建的时候需要提交，否则会报错
            long resultcode = writer.commit();

            if(resultcode > 0l) {
                System.out.println("成功建立索引");
            } else {
                System.out.println("建立索引失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加索引
     */
    @Test
    public void addJcsegIndex() {
        try {

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

            // 获取lucene的写入方法
            writer = LuceneUtils.getIndexWriter(analyzer);

            Document doc;

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "2001", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine 张楚岚 ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "2002", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "2003", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "危险化学品事故应急预案备案登记表 红色主题 441524199907224111 尚书部 佛山市南海尚铝五金制品有限公司 ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "2004", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "2005", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 提交数据，第一次创建的时候需要提交，否则会报错
            long resultcode = writer.commit();

            if(resultcode > 0l) {
                System.out.println("成功建立索引");
            } else {
                System.out.println("建立索引失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 全文检索
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void IkQueryParser() throws ParseException, IOException {
        try {
            // 获取一个indexReader对象
            reader = LuceneUtils.getIndexReader();
            // 创建一个indexsearcher对象
            searcher = new IndexSearcher(reader);

            QueryParser queryParser = new QueryParser("introduction", new IKAnalyzer());
            Query query = queryParser.parse("五月天");
            System.out.println("query:"+query);

            SortField sortField = new SortField("introduction",SortField.Type.SCORE,false);
            Sort sort = new Sort(sortField);

            TopDocs topDocs = searcher.search(query, 10, sort);
            System.out.println("数字查询");
            System.out.println("命中结果数为: "+ topDocs.totalHits);
            // 返回查询结果。遍历查询结果并输出。
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                int doc = scoreDoc.doc;
                Document document = searcher.doc(doc);
                // 打印content字段的值
                System.out.println("得分值: "+ scoreDoc.toString());
                System.out.println("companyid: "+document.get("companyid"));
                System.out.println("introduction: "+document.get("introduction"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 全文检索
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void JcsegQueryParser() throws ParseException, IOException {
        try {
            // 获取一个indexReader对象
            reader = LuceneUtils.getIndexReader();
            // 创建一个indexsearcher对象
            searcher = new IndexSearcher(reader);


            Analyzer analyzer = new JcsegAnalyzer(JcsegTaskConfig.NLP_MODE);
            //available constructor:
            //1, JcsegAnalyzer(int mode)
            //2, JcsegAnalyzer(int mode, String proFile)
            //3, JcsegAnalyzer(int mode, JcsegTaskConfig config)
            //4, JcsegAnalyzer(int mode, JcsegTaskConfig config, ADictionary dic)

            //非必须(用于修改默认配置): 获取分词任务配置实例
            JcsegAnalyzer jcseg = (JcsegAnalyzer) analyzer;

            JcsegTaskConfig  config = jcseg.getTaskConfig();
            //追加同义词, 需要在 jcseg.properties中配置jcseg.loadsyn=1
            config.setAppendCJKSyn(true);
            //追加拼音, 需要在jcseg.properties中配置jcseg.loadpinyin=1
            config.setAppendCJKPinyin(true);

            QueryParser queryParser = new QueryParser("introduction", analyzer);
            Query query = queryParser.parse("五月天");
            System.out.println("query:"+query);

            SortField sortField = new SortField("introduction",SortField.Type.SCORE,false);
            Sort sort = new Sort(sortField);

            TopDocs topDocs = searcher.search(query, 10, sort);
            System.out.println("数字查询");
            System.out.println("命中结果数为: "+ topDocs.totalHits);
            // 返回查询结果。遍历查询结果并输出。
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                int doc = scoreDoc.doc;
                Document document = searcher.doc(doc);
                // 打印content字段的值
                System.out.println("得分值: "+ scoreDoc.toString());
                System.out.println("companyid: "+document.get("companyid"));
                System.out.println("introduction: "+document.get("introduction"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
