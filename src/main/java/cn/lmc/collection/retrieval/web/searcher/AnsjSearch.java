package main.java.cn.lmc.collection.retrieval.web.searcher;

import main.java.cn.lmc.collection.common.lucene.LuceneUtils;
import main.java.cn.lmc.collection.common.lucene.ansj.AnsjAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
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
public class AnsjSearch {
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
    public void addIndex() {
        try {
            // 获取Ansj的分词器
            Analyzer analyzer = new AnsjAnalyzer(AnsjAnalyzer.TYPE.nlp_ansj);
            // 获取lucene的写入方法
            writer = LuceneUtils.getIndexWriter(analyzer);
            // 定义文档
            Document doc;

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "001", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "《佛山市城镇职工基本医疗保险参保人本市跨区参加居民门诊申报（单位）》电子版 尚书部 公安局 张楚岚 ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "002", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "佛山市“电梯使用权者”安全承诺书 张家企业 441524199907224111 尚书部 佛山市南海区信浩鑫五金经营部 ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "003", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "危险化学品事故应急预案备案登记表 楚楚可怜 441524199907224111 尚书部 佛山市南海尚铝五金制品有限公司 ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "004", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "第二类医疗器械经营备案凭证租赁合同 岚侠 441524199907224111 铝材 佛山市南海顺迈五金制品有限公司", Field.Store.YES));

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
    public void testQueryParser() throws ParseException, IOException {
        try {
            // 获取一个indexReader对象
            reader = LuceneUtils.getIndexReader();
            // 创建一个indexsearcher对象
            searcher = new IndexSearcher(reader);
            // 获取Ansj的分词器
            Analyzer analyzer = new AnsjAnalyzer(AnsjAnalyzer.TYPE.nlp_ansj);

            QueryParser queryParser = new QueryParser("introduction", analyzer);
            Query query = queryParser.parse("张楚岚");
            System.out.println("query分词结果:"+query);

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
