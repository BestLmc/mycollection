package main.java.cn.lmc.collection.common.lucene;

import main.java.cn.lmc.collection.common.utils.PropertyUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


/**
 * 全文检索工具类
 * @author limingcheng
 *
 */
public class LuceneUtils {
    // 打印日志
	private static final Logger LOGGER = LoggerFactory.getLogger(LuceneUtils.class);
	
    private static Directory directory;	// 索引文件存放目录对象
    private static IndexWriter indexWriter;	// 索引写对象,线程安全
    private static IndexReader indexReader;	// 索引读对象，线程安全
    private static IndexSearcher indexSearcher;	// 索引搜索对象，线程安全
    private static Analyzer analyzer;	// 分词器对象
    public static IndexWriterConfig indexWriterConfig;	// 索引配置
//    public static Version matchVersion;	// 索引版本(Lucene4.0之前需要用到，4.0之后被取消)
    
	static{
		try {
	    	//初始化索引文件存放目录对象
			directory = 
					FSDirectory.open(Paths.get((String) PropertyUtil.getParamFromConfig("lucene.index.directory")));
			// 虚拟机退出时关闭
			Runtime.getRuntime().addShutdownHook(new Thread(){
				@Override
				public void run() {
					LOGGER.info("--------Lucene释放关闭资源中....");
					try{
						//释放关闭资源
						if(null!=indexWriter){
							indexWriter.close();
						}
						if(null!=indexReader){
							indexReader.close();
						}
						if(null!=directory){
							directory.close();
						}
						if(null!=analyzer){
							analyzer.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					LOGGER.info("--------Lucene释放关闭资源成功....");
				}
			});
	       
		} catch (Exception e) {
	       e.printStackTrace();
	    }
	}
    
	/**
     * 
     * @return 返回用于操作索引的对象
     * @throws IOException
     */
    public static IndexWriter getIndexWriter() throws IOException{
    	if(null==indexWriter){
            // 初始化IK分词器
            Analyzer analyzer = getAnalyzer();
            // 初始化索引的写配置对象
            indexWriterConfig = new IndexWriterConfig(analyzer);
            // 初始化索引的写对象
            indexWriter=new IndexWriter(directory, indexWriterConfig);
         }
         return indexWriter;
    }

	/**
	 *
	 * @return 返回用于操作索引的对象
	 * @throws IOException
	 */
	public static IndexWriter getIndexWriter(Analyzer analyzer) throws IOException{
		if(null==indexWriter){
			// 初始化索引的写配置对象
			indexWriterConfig = new IndexWriterConfig(analyzer);
			// 初始化索引的写对象
			indexWriter=new IndexWriter(directory, indexWriterConfig);
		}
		return indexWriter;
	}
    
    /**
     * 
     * @return 返回用于操作索引的对象
     * @throws IOException
     */
    public static IndexReader getIndexReader() throws IOException{
    	indexReader = DirectoryReader.open(directory);
        return indexReader;
    }
    
    /**
     * 
     * @return 返回用于读取索引的对象
     * @throws IOException
     */
    public static IndexSearcher getIndexSearcher() throws IOException{
        indexReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(indexReader);
        return indexSearcher;
    }
    
    /**
     * 
     * @return 返回用于读取索引的对象
     * @throws IOException
     */
    public static IndexSearcher getIndexSearcher(Directory directory) throws IOException{
    	indexReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(indexReader);
        return indexSearcher;
    }

    /**
     * 
     * @return 返回版本信息
     */
//    public static Version getMatchVersion() {
//        return matchVersion;
//    }

    /**
     * 
     * @return 返回分词器
     */
    public static Analyzer getAnalyzer() {
    	// Lucene4以前的版本需要用到版本配置
    	// matchVersion = Version.LUCENE_44;
    	// 分词器
    	// analyzer = new StandardAnalyzer();	// 标准分词
    	if(analyzer == null) {
    		System.out.println("创建新的分析器");
    		analyzer = new IKAnalyzer();
    	} 
        return analyzer;
    }
    
    /**
     * 打印一个文档的所有字段的内容
     * @param
     */
    public static void printDocument(Document document){
    	//打印具体字段
    	List<IndexableField> fieldList = document.getFields();
    	//遍历列表
    	for (IndexableField field : fieldList){
    		//打印出所有的字段的名字和值（必须存储了的）
    		LOGGER.info(field.name()+":"+field.stringValue());
    	}
    	//文档详情
    	LOGGER.info(document.toString());
    }

    /**
     * 打印ScoreDoc
     * @param scoreDoc
     * @throws IOException
     */
    public static void printScoreDoc(ScoreDoc scoreDoc) throws IOException{
    	//获取文档的编号（类似索引主键）
    	int docId = scoreDoc.doc;
    	LOGGER.info("======文档编号："+docId);
    	// 取出文档得分
    	LOGGER.info("得分： " + scoreDoc.score);
    	//获取具体文档
    	Document document = indexSearcher.doc(docId);
    	//打印具体字段
    	printDocument(document);
    }

    /**
     * 打印命中的文档（带得分）的详情
     * @param topDocs
     */
    public static void printTopDocs(TopDocs topDocs) throws IOException {
    	// 1)打印总记录数（命中数）：类似于百度为您找到相关结果约100,000,000个
    	long totalHits = topDocs.totalHits.value;
    	LOGGER.info("查询（命中）总的文档条数："+totalHits);
//     	LOGGER.info("查询（命中）文档最大分数："+topDocs.getMaxScore());
    	//2)获取指定的最大条数的、命中的查询结果的文档对象集合
    	ScoreDoc[] scoreDocs = topDocs.scoreDocs;
    	//打印具体文档
    	for (ScoreDoc scoreDoc : scoreDocs) {
    		printScoreDoc(scoreDoc);
    	}
    }

    public static void printTopDocsByQueryForHighlighter(Query query, int n) throws Exception{

       //=========1.创建一个高亮工具对象
       // 格式化器：参数1：前置标签，参数2：后置标签
       Formatter formatter = new SimpleHTMLFormatter("<em>", "</em>");
       //打分对象，参数：query里面的条件，条件里面有搜索关键词
       Scorer fragmentScorer = new QueryScorer(query);
       //高亮工具
       //参数1.需要高亮什么颜色, 参数2.将哪些关键词进行高亮
       Highlighter highlighter = new Highlighter(formatter, fragmentScorer);
       //=======搜索相关
       IndexSearcher indexSearcher = getIndexSearcher();
       // 搜索数据,两个参数：查询条件对象要查询的最大结果条数
       // 返回的结果是 按照匹配度排名得分前N名的文档信息（包含查询到的总条数信息、所有符合条件的文档的编号信息）
       TopDocs topDocs = indexSearcher.search(query, n);
       // 打印命中的总条数
//     LOGGER.info("本次搜索共" + topDocs.totalHits + "条数据,最高分："+topDocs.getMaxScore());

       // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
       ScoreDoc[] scoreDocs = topDocs.scoreDocs;

       //循环
       for (ScoreDoc scoreDoc : scoreDocs) {
    	   // 取出文档编号
    	   int docID = scoreDoc.doc;
    	   System.out.println("=========文档的编号是："+docID);
    	   // 取出文档得分
    	   System.out.println("当前文档得分： " + scoreDoc.score);
    	   // 根据编号去找文档
    	   Document document = indexSearcher.doc(docID);
    	   //获取文档的所有字段对象
    	   List<IndexableField> fieldList= document.getFields();
    	   //遍历列表
    	   for (IndexableField field : fieldList) {
    		   String highlighterValue = highlighter.getBestFragment(getAnalyzer(), field.name(), field.stringValue());
    		   //如果没有得到高亮的值
    		   if (null==highlighterValue) {
    			   //则让高亮结果等不高亮的值
    			   highlighterValue = field.stringValue();
    		   }
    		   //打印出所有的字段的名字和值（必须存储了的）
    		   LOGGER.info(field.name()+":"+highlighterValue);
    	   }

       	}
    }
    
}