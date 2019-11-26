package main.java.cn.lmc.collection.retrieval.web.searcher;

import main.java.cn.lmc.collection.common.lucene.LuceneUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;


public class FullTextRetrievalDemo2 {

	// lucene的写、读方法
	private IndexWriter writer;
    private IndexReader reader; 
	private IndexSearcher searcher;
    
    /**
     * 添加索引
     */
    @Test
    public void addIndex() { 
		try {
			// 获取lucene的写入方法
			writer = LuceneUtils.getIndexWriter();
			
			Document doc = new Document();
			
			// 书主键
			doc = new Document();
		    doc.add(new StringField("bookid", "92440604MA5112Q2XK", Field.Store.YES)); 
		    // 书名
		    doc.add(new StringField("bookname", "西游记", Field.Store.YES));  
		    // 书的类型
		    doc.add(new StringField("booktype", "小说", Field.Store.YES));  
		    // 书的价格
		    doc.add(new NumericDocValuesField("bookprice", 123));  
		    // 书的日期年份
		    Field intPoint = new NumericDocValuesField("bookdate", 100);
		    doc.add(intPoint);
		    intPoint = new StoredField("bookdate", 100);
		    doc.add(intPoint); 
		    // 书的内容
		    doc.add(new TextField("bookcontent", "949c35c7f4bb4fb98f34ac4c344c4fe3	代理机构营业执照副本	9					李燕媚		440602198504100923	2019/9/16 14:57:25	2019/9/17 10:08:39	1	7326c578f78a49cc8714043b371aecd1	089d6ce073d14c8fb5b62d5d4158ce3d	禅城区市场监督管理局	企业申请迁入调档	GS0069A	2	11473.00	"
		    		+ "5557.00	2019/9/17 11:40:06	2019/9/17 11:44:18	5557	92440604MA5112Q2XK ", Field.Store.YES));
		    // 书的内容
		    doc.add(new TextField("bookcontent1", "949c35c7f4bb4fb98f34ac4c344c4fe3	代理机构营业执照副本	9					李燕媚		440602198504100923	2019/9/16 14:57:25	2019/9/17 10:08:39	1	7326c578f78a49cc8714043b371aecd1	089d6ce073d14c8fb5b62d5d4158ce3d	禅城区市场监督管理局	企业申请迁入调档	GS0069A	2	11473.00	\"\r\n" + 
		    		"		    		+ \"5557.00	2019/9/17 11:40:06	2019/9/17 11:44:18	5557	92440604MA5112Q2XK", Field.Store.YES));
		    // 添加文档
            writer.addDocument(doc);
            
		    // 书主键
 			doc = new Document();
 		    doc.add(new StringField("bookid", "32088219960621581X", Field.Store.YES)); 
 		    // 书名
 		    doc.add(new StringField("bookname", "水浒传", Field.Store.YES));  
 		    // 书的类型
 		    doc.add(new StringField("booktype", "小说", Field.Store.YES));  
 		    // 书的价格
		    doc.add(new NumericDocValuesField("bookprice", 124));  
		    // 书的日期年份
		    Field intPoint1 = new NumericDocValuesField("bookdate", 200);
		    doc.add(intPoint1);
		    intPoint1 = new StoredField("bookdate", 200);
		    doc.add(intPoint1); 
 		    // 书的内容
 		    doc.add(new TextField("bookcontent", "88789b55df5340b992bbd38104bae0fb	申请人有效身份证明	5					13928590560		13928590560	2019/9/16 11:47:05	2019/9/17 10:09:19	1	c219beabed6b43649a8caebef58dfc92	5168e7cebe794be3a8f02bc462aa8b19	禅城区卫生健康局	医师执业证书（变更）	WJ009-2B	2	11812.00	5348.00	2019/9/17 11:20:01	2019/9/17 11:24:37	5348_0		32088219960621581X				江苏省淮安市淮安区淮城镇零址街村路东组8号\r\n" + 
 		    		"", Field.Store.YES)); 
 		    // 书的内容
 		    doc.add(new TextField("bookcontent2", "88789b55df5340b992bbd38104bae0fb	申请人有效身份证明	5					13928590560		13928590560	2019/9/16 11:47:05	2019/9/17 10:09:19	1	c219beabed6b43649a8caebef58dfc92	5168e7cebe794be3a8f02bc462aa8b19	禅城区卫生健康局	医师执业证书（变更）	WJ009-2B	2	11812.00	5348.00	2019/9/17 11:20:01	2019/9/17 11:24:37	5348_0		32088219960621581X				江苏省淮安市淮安区淮城镇零址街村路东组8号\r\n" + 
 		    		"", Field.Store.YES)); 
		    // 添加文档
            writer.addDocument(doc);
            
            // 书主键
 			doc = new Document();
 		    doc.add(new StringField("bookid", "3N21(129N9090PP", Field.Store.YES)); 
 		    // 书名
 		    doc.add(new StringField("bookname", "三国演义", Field.Store.YES));  
 		    // 书的类型
 		    doc.add(new StringField("booktype", "小说", Field.Store.YES));  
 		    // 书的价格
		    doc.add(new NumericDocValuesField("bookprice", 125));  
		    // 书的日期年份
		    Field intPoint2 = new NumericDocValuesField("bookdate", 300);
		    doc.add(intPoint2);
		    intPoint2 = new StoredField("bookdate", 300);
		    doc.add(intPoint2); 
 		    // 书的内容
 		    doc.add(new TextField("bookcontent", "76f7619b73fb4dafa7ceac399ad19c06	营业执照	9					罗银妮		441224200007292925	2019/9/16 9:37:09	2019/9/17 10:08:07	1	a4d1b9f8ef3442e8ab078a701ba55ab9	089d6ce073d14c8fb5b62d5d4158ce3d	禅城区市场监督管理局	企业申请迁入调档	GS0069A	2	11643.00	5383.00	2019/9/17 11:22:27	2019/9/17 11:24:55	5383	3N21(129N9090PP	", Field.Store.YES)); 
 		    // 书的内容
 		    doc.add(new TextField("bookcontent3", "76f7619b73fb4dafa7ceac399ad19c06	营业执照	9					罗银妮		441224200007292925	2019/9/16 9:37:09	2019/9/17 10:08:07	1	a4d1b9f8ef3442e8ab078a701ba55ab9	089d6ce073d14c8fb5b62d5d4158ce3d	禅城区市场监督管理局	企业申请迁入调档	GS0069A	2	11643.00	5383.00	2019/9/17 11:22:27	2019/9/17 11:24:55	5383	3N21(129N9090PP	", Field.Store.YES)); 
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
     * 查询解析生成器
     * QueryParser 查询解析生成器
     * Lucene QueryPaser包中提供了两类查询解析器：
     * A. 传统的解析器：QueryParser和MultiFieldQueryParser
     * B. 基于新的 flexible 框架的解析器：StandardQueryParser
     */
    @Test
    public void QueryParser() {
    	try {   
    		QueryParser parser = new QueryParser("bookcontent", new IKAnalyzer());
    		//parser.setPhraseSlop(2);
    		Query query = parser.parse("3N21\\(129N9090PP");
    		sortSearch(query); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * 排序查询
     */
    private void sortSearch(Query query) {
    	// 获取一个indexReader对象
		try {
			// 获取一个indexReader对象
			reader = LuceneUtils.getIndexReader();
			// 创建一个indexsearcher对象
			searcher = new IndexSearcher(reader);
			
			//true表示降序
			//SortField.Type.SCORE  根据相关度进行排序(默认)
			//SortField.Type.DOC    根据文档编号或者说是索引顺序
			//SortField.Type.FLOAT(Long等),根据fieldName的数值类型进行排序
			SortField sortField = new SortField("bookcontent",SortField.Type.SCORE,true);
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
				System.out.println("bookid: "+document.get("bookid"));
				System.out.println("bookname: "+document.get("bookname"));
				System.out.println("booktype: "+document.get("booktype"));
				System.out.println("bookcontent: "+document.get("bookcontent"));
				System.out.println("查询得分是: "+scoreDoc.score); 
				System.out.println("bookprice: "+document.get("bookprice"));
				System.out.println("bookdate: "+document.get("bookdate"));
				System.out.println("--------------我是分割线------------------");
			} 
		} catch (IOException e) { 
			e.printStackTrace();
		} 
    } 
    
    /**
     * 打印查询结果
     */
    private void printTopDocs(TopDocs topDocs) {
    	// 获取一个indexReader对象
		try {
			reader = LuceneUtils.getIndexReader();
			// 创建一个indexsearcher对象
			searcher = new IndexSearcher(reader);
	    	System.out.println("数字查询");  
	        System.out.println("命中结果数为: "+ topDocs.totalHits);
	        // 返回查询结果。遍历查询结果并输出。
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc scoreDoc : scoreDocs) {
				int doc = scoreDoc.doc;
				Document document = searcher.doc(doc);  
				// 打印content字段的值
				System.out.println("bookid: "+document.get("bookid"));
				System.out.println("bookname: "+document.get("bookname"));
				System.out.println("booktype: "+document.get("booktype"));
				System.out.println("bookcontent: "+document.get("bookcontent"));
			} 
		} catch (IOException e) { 
			e.printStackTrace();
		} 
    } 
}
