package main.java.cn.lmc.collection.retrieval.web.searcher;


import main.java.cn.lmc.collection.common.lucene.LuceneUtils;
import main.java.cn.lmc.collection.utils.GroupingUtil;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.*;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.util.BytesRef;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;


public class FullTextRetrievalDemo {

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
		    doc.add(new StringField("bookid", "1345678", Field.Store.YES)); 
		    // 书名
		    doc.add(new StringField("bookname", "西游记", Field.Store.YES));  
		    // 书的类型
		    doc.add(new StringField("booktype", "小说", Field.Store.YES));  
		    // 需要使用特定的field存储分组，需要排序及分组的话，要加上下面语句，注意默认SortedDocValuesField也是不存储的
		    doc.add(new SortedDocValuesField("booktype", new BytesRef("小说")));
		    // 书的价格
		    doc.add(new NumericDocValuesField("bookprice", 123));  
		    // 书的日期年份
		    Field intPoint = new NumericDocValuesField("bookdate", 1066);
		    doc.add(intPoint);
		    intPoint = new StoredField("bookdate", 1066);
		    doc.add(intPoint); 
		    // 书的内容
		    doc.add(new TextField("bookcontent", "《西游记》又称央视86版《西游记》，改编自明123456789012345678 12333代小说家吴承恩同名文学古典名著。是由中央电视台、中国电视剧制作中心出品的一部25集古装神话剧。由杨洁执导，戴英禄，杨洁，邹忆青共同编剧，六小龄童、徐少华、迟重瑞、汪粤、马德华、闫怀礼等主演，李世宏、李扬、张云明、里坡等担任主要配音。 [1] \r\n" + 
		    		"该剧讲述的是孙悟空、猪八戒、沙僧辅保大唐高僧玄奘去西天取经，师徒四人一路抢滩涉险，降妖伏怪，历经八十一难，取回真经，终修正果的故事。\r\n" + 
		    		"《西游记》于1982年7月3日开机，同年10月1日首播试集《除妖乌鸡国》。1986年春节在央视首播前11集，1988年25集播出。\r\n" + 
		    		"1986年春节一经播出，轰动全国，老少皆宜，获得了极高评价，造就了89.4%的收视率神话，至今仍是寒暑假被重播最多的电视剧，重播次数超过3000次，依然百看不厌，成为一部公认的无法超越的经典。", Field.Store.YES));
		    // 添加文档
            writer.addDocument(doc);
            
            // 书主键
			doc = new Document();
		    doc.add(new StringField("bookid", "1533278", Field.Store.YES)); 
		    // 书名
		    doc.add(new StringField("bookname", "自然", Field.Store.YES));  
		    // 书的类型
		    doc.add(new StringField("booktype", "杂志", Field.Store.YES));  
		    // 需要使用特定的field存储分组，需要排序及分组的话，要加上下面语句，注意默认SortedDocValuesField也是不存储的
		    doc.add(new SortedDocValuesField("booktype", new BytesRef("杂志")));
		    // 书的价格
		    doc.add(new NumericDocValuesField("bookprice", 123));  
		    // 书的日期年份
		    Field intPoint2 = new NumericDocValuesField("bookdate", 1066);
		    doc.add(intPoint2);
		    intPoint = new StoredField("bookdate", 1066);
		    doc.add(intPoint); 
		    // 书的内容
		    doc.add(new TextField("bookcontent", "宣扬科学，发表论文！", Field.Store.YES));
		    // 添加文档
	         writer.addDocument(doc);
            
		    // 书主键
 			doc = new Document();
 		    doc.add(new StringField("bookid", "12345678", Field.Store.YES)); 
 		    // 书名
 		    doc.add(new StringField("bookname", "水浒传", Field.Store.YES));   
 		    // 书的类型
 		    doc.add(new StringField("booktype", "小说", Field.Store.YES));  
 		    // 需要使用特定的field存储分组，需要排序及分组的话，要加上下面语句，注意默认SortedDocValuesField也是不存储的
		    doc.add(new SortedDocValuesField("booktype", new BytesRef("小说")));
 		    // 书的价格
		    doc.add(new NumericDocValuesField("bookprice", 123));  
		    // 书的日期年份
		    Field intPoint1 = new NumericDocValuesField("bookdate", 1666);
		    doc.add(intPoint1);
		    intPoint1 = new StoredField("bookdate", 1666);
		    doc.add(intPoint1); 
 		    // 书的内容
 		    doc.add(new TextField("bookcontent", "中国大陆，中央电视台无锡太湖影视城 43集\r\n" + 
 		    		"《水浒传》是由中央电视台与中国电视剧制作中心联合出品的43集电视连续剧，根据明代施耐123456789012345678庵的同名小说改编。 [1]  由张绍林执导，杨争光 、冉平改编，李雪健、周野芒、臧金生、丁海峰、赵小锐领衔主演。\r\n" + 
 		    		"该剧讲述的是宋朝徽宗时皇帝昏庸、奸臣当道、官府腐败、贪官污吏陷害忠良，弄得民不聊生，许多正直善良的人被官府逼得无路可走，被迫奋起反抗，最终108条好汉聚义梁山泊，但随后宋江对朝廷的投降使得一场轰轰烈烈的农民起义最后走向失败的故事。 [2] \r\n" + 
 		    		"《水浒传》于1998年1月8日在中央电视台一套首播。 [3] \r\n" + 
 		    		"2018年9月8日，9月15日，9月22日，央视四台《中国文艺》“向经典致敬”栏目播出《水浒传》20周年聚首专题节目", Field.Store.YES));
		     
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
			
			QueryParser queryParser = new QueryParser("bookcontent", new IKAnalyzer());
		    Query query = queryParser.parse("孙悟空");
		    
		    SortField sortField = new SortField("bookcontent",SortField.Type.SCORE,true);
		    SortField sortField1 = new SortField("bookprice",SortField.Type.INT,true);
			Sort sort = new Sort(sortField,sortField1);
			 
	        TopDocs topDocs = searcher.search(query, 10, sort);
	        System.out.println("数字查询");  
	        System.out.println("命中结果数为: "+ topDocs.totalHits);
	        // 返回查询结果。遍历查询结果并输出。
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc scoreDoc : scoreDocs) {
				int doc = scoreDoc.doc;
				Document document = searcher.doc(doc);  
				// 打印content字段的值
				System.out.println("得分值: "+scoreDoc.score);
				System.out.println("bookid: "+document.get("bookid"));
				System.out.println("bookname: "+document.get("bookname"));
				System.out.println("booktype: "+document.get("booktype"));
				System.out.println("bookprice: "+document.get("bookprice"));
				System.out.println("bookcontent: "+document.get("bookcontent"));
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * 多条件查询
     */
    @Test
    public void fullBooleanQuery() {
    	try {
    		// 获取一个indexReader对象
			reader = LuceneUtils.getIndexReader();
			// 创建一个indexsearcher对象
			searcher = new IndexSearcher(reader);
			
			BooleanQuery.Builder builder = new BooleanQuery.Builder();
			// 书内容
			QueryParser queryParser = new QueryParser("bookcontent", new IKAnalyzer());
		    Query query1 = queryParser.parse("123456789012345678 dd");
		    builder.add(query1, Occur.MUST);
		    
		    // 书名称
		    Query qymc = new FuzzyQuery(new Term("bookname", "西游记"));
			builder.add(qymc, Occur.MUST);
		    
			// 书类型
			Query gmsfhm = new TermQuery(new Term("booktype", "小说"));
			builder.add(gmsfhm, Occur.MUST);
		    
			BooleanQuery booleanQuery = builder.build();
			 
	        TopDocs topDocs = searcher.search(booleanQuery, 10);
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
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    
    /**
     *  词项查询
     *  最基本、最常用的查询。用来查询指定字段包含指定词项的文档。
     */
    @Test
    public void TermQuery() {
    	try {
    		// 获取一个indexReader对象
			reader = LuceneUtils.getIndexReader();
			// 创建一个indexsearcher对象
			searcher = new IndexSearcher(reader);
			TermQuery tq = new TermQuery(new Term("bookname", "西游记")); 
	        TopDocs topDocs = searcher.search(tq, 10);
	        printTopDocs(topDocs);
		} catch (Exception e) {
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
    		Query query = parser.parse("除妖乌鸡国央视");
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
			SortField sortField = new SortField("bookdate",SortField.Type.INT,true);
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
	 * 分组查询
	 * @param param
	 * @param rule
	 * @return
	 * @throws IOException 
	 */
    @Test
	public void GroupingStatistics() throws IOException{ 
		try {
			// 获取一个indexReader对象
			reader = LuceneUtils.getIndexReader();
			// 创建一个indexsearcher对象
			searcher = new IndexSearcher(reader);
			Query query = new MatchAllDocsQuery();
			GroupingUtil groupingUtil = new GroupingUtil();
			 
			int curPage = 1;
			int pageSize = 10;
			// 控制每次返回几组
			int groupLimit = curPage*pageSize;
			// 控制每一页的组内文档数
			int groupDocsLimit = curPage*pageSize;
			// 控制组的偏移
			int groupOffset = 0;
			
			// 为了排除干扰因素，全部使用默认的排序方式，当然你还可以使用自己喜欢的排序方式
	        // 初始值为命中的所有文档数，即最坏情况下，一个文档分成一组，那么文档数就是分组的总数
	        int totalGroupCount = searcher.count(query);
	        TopGroups<BytesRef> topGroups;
	        System.out.println("#### 组的分页大小为：" + groupLimit);
	        System.out.println("#### 组内分页大小为：" + groupDocsLimit);
			while (groupOffset < totalGroupCount) {//说明还有不同的分组
	            // 控制组内偏移，每次开始遍历一个新的分组时候，需要将其归零
	            int groupDocsOffset = 0;
	            System.out.println("#### 开始组的分页");
	            topGroups = groupingUtil.group(searcher, query, "booktype", 
	            		groupDocsOffset, groupDocsLimit, groupOffset, groupLimit);
	            // 具体搜了一次之后，就知道到底有多少组了，更新totalGroupCount为正确的值
	            totalGroupCount = topGroups.totalGroupCount;
	            GroupDocs<BytesRef>[] groups = topGroups.groups;
	            // 开始对组进行遍历
	            for (int i = 0; i < groups.length; i++) {
	                long totalHits = groupingUtil.iterGroupDocs(searcher, groups[i]);// 获得这个组内一共多少doc
	                // 获取数据
                    TotalHits totalH = groups[i].totalHits; 
                    totalHits = totalH.value;
                    System.out.println("\t#### 开始组内分页");
                    System.out.println("\t分组名称：" + groups[i].groupValue.utf8ToString());

                    ScoreDoc[] scoreDocs = groups[i].scoreDocs;
                    
                    if(scoreDocs!=null && scoreDocs.length>0) {
                    	for (int j=0; j<1; j++) { 
            				Document document = searcher.doc(scoreDocs[j].doc);
            				// 打印content字段的值
            				System.out.println("得分值: "+scoreDocs.length);
            				System.out.println("bookid: "+document.get("bookid"));
            				System.out.println("bookname: "+document.get("bookname"));
            				System.out.println("booktype: "+document.get("booktype"));
            				System.out.println("bookprice: "+document.get("bookprice"));
            				System.out.println("bookcontent: "+document.get("bookcontent"));
            			}
                    }
	                groupDocsOffset = 0;
	            }
	            groupOffset += groupLimit;
	            System.out.println("#### 结束组的分页");
	        }
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭indexReader对象
			reader.close();
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
