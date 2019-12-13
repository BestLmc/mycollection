package main.java.cn.lmc.collection.retrieval.web.searcher;

import main.java.cn.lmc.collection.common.lucene.LuceneUtils;
import main.java.cn.lmc.collection.common.lucene.ansj.AnsjAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
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
            Analyzer analyzer = new AnsjAnalyzer(AnsjAnalyzer.TYPE.index_ansj);
            // 获取lucene的写入方法
            writer = LuceneUtils.getIndexWriter(analyzer);
            // 定义文档
            Document doc;

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "001", Field.Store.YES));
            // 介绍
            FieldType type = new FieldType();
            // 设置是否存储该字段
            type.setStored(true); // 请试试不存储的结果
            // 设置是否对该字段分词
            type.setTokenized(true); // 请试试不分词的结果
            // 设置该字段的索引选项
            type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS); // 请尝试不同的选项的效果
            type.freeze(); // 使不可更改
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            TextField testfield = new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine ", Field.Store.YES);
//            testfield.set

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "002", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "003", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "004", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine, 周杰伦 ", Field.Store.YES));
            // 添加文档
            writer.addDocument(doc);

            // 公司id
            doc = new Document();
            doc.add(new StringField("companyid", "005", Field.Store.YES));
            // 介绍
            doc.add(new TextField("introduction", "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine", Field.Store.YES));
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
            Query qwjs = queryParser.parse(QueryParser.escape("五月天"));
            System.out.println("qwjs分词结果:"+qwjs);


            SortField sortField = new SortField("introduction",SortField.Type.SCORE,false);
            Sort sort = new Sort(sortField);

            TopDocs topDocs = searcher.search(qwjs, 10, sort);
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
