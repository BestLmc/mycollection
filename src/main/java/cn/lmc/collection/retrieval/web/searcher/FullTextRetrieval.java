package main.java.cn.lmc.collection.retrieval.web.searcher;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.queryparser.simple.SimpleQueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;


public class FullTextRetrieval {
	
	private Directory directory = new RAMDirectory(); 
    private IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new IKAnalyzer());
    private IndexWriter indexWriter;

	 @Before
	    public void createIndex() {
	        try {
	            indexWriter = new IndexWriter(directory, indexWriterConfig); 
                Document doc = new Document();
                // 书主键
    			doc = new Document();
    		    doc.add(new StringField("bookid", "1345678", Field.Store.YES)); 
    		    // 书名
    		    doc.add(new StringField("bookname", "西游记", Field.Store.YES));  
    		    // 书的类型
    		    doc.add(new StringField("booktype", "小说", Field.Store.YES)); 
    		    // 书的价格
    		    doc.add(new NumericDocValuesField("bookprice", 123));  
    		    // 书的日期年份
    		    Field intPoint = new IntPoint("bookdate", 1066);
    		    doc.add(intPoint);
    		    intPoint = new StoredField("bookdate", 1066);
    		    doc.add(intPoint); 
//    		    doc.add(new NumericDocValuesField("bookdate", 123));  
    		    // 书的内容
    		    doc.add(new TextField("bookcontent", "《西游记》又称央视86版《西游记》，改编自明代小说家吴承恩同名文学古典名著。是由中央电视台、中国电视剧制作中心出品的一部25集古装神话剧。由杨洁执导，戴英禄，杨洁，邹忆青共同编剧，六小龄童、徐少华、迟重瑞、汪粤、马德华、闫怀礼等主演，李世宏、李扬、张云明、里坡等担任主要配音。 [1] \r\n" + 
    		    		"该剧讲述的是孙悟空、猪八戒、沙僧辅保大唐高僧玄奘去西天取经，师徒四人一路抢滩涉险，降妖伏怪，历经八十一难，取回真经，终修正果的故事。\r\n" + 
    		    		"《西游记》于1982年7月3日开机，同年10月1日首播试集《除妖乌鸡国》。1986年春节在央视首播前11集，1988年25集播出。\r\n" + 
    		    		"1986年春节一经播出，轰动全国，老少皆宜，获得了极高评价，造就了89.4%的收视率神话，至今仍是寒暑假被重播最多的电视剧，重播次数超过3000次，依然百看不厌，成为一部公认的无法超越的经典。", Field.Store.YES));
    		    // 添加文档
    		    indexWriter.addDocument(doc);
                
    		    // 书主键
     			doc = new Document();
     		    doc.add(new StringField("bookid", "12345678", Field.Store.YES)); 
     		    // 书名
     		    doc.add(new StringField("bookname", "水浒传", Field.Store.YES));  
     		    // 书的类型
     		    doc.add(new StringField("booktype", "小说", Field.Store.YES));  
     		    // 书的价格
    		    doc.add(new NumericDocValuesField("bookprice", 432));  
    		    // 书的日期年份
    		    Field intPoint1 = new IntPoint("bookdate", 1666);
    		    doc.add(intPoint1);
    		    intPoint = new StoredField("bookdate", 1666);
    		    doc.add(intPoint1); 
     		    // 书的内容
     		    doc.add(new TextField("bookcontent", "中国大陆，中央电视台无锡太湖影视城 43集\r\n" + 
     		    		"《水浒传》是由中央电视台与中国电视剧制作中心联合出品的43集电视连续剧，根据明代施耐庵的同名小说改编。 [1]  由张绍林执导，杨争光 、冉平改编，李雪健、周野芒、臧金生、丁海峰、赵小锐领衔主演。\r\n" + 
     		    		"该剧讲述的是宋朝徽宗时皇帝昏庸、奸臣当道、官府腐败、贪官污吏陷害忠良，弄得民不聊生，许多正直善良的人被官府逼得无路可走，被迫奋起反抗，最终108条好汉聚义梁山泊，但随后宋江对朝廷的投降使得一场轰轰烈烈的农民起义最后走向失败的故事。 [2] \r\n" + 
     		    		"《水浒传》于1998年1月8日在中央电视台一套首播。 [3] \r\n" + 
     		    		"2018年9月8日，9月15日，9月22日，央视四台《中国文艺》“向经典致敬”栏目播出《水浒传》20周年聚首专题节目", Field.Store.YES));
                indexWriter.addDocument(doc); 
	            indexWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	private IndexSearcher getIndexSearcher() throws IOException {
        return new IndexSearcher(DirectoryReader.open(directory));
    }
	
	 /**
     * 查询所有文档
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void testMatchAllDocsQuery() throws ParseException, IOException {  
        Query query = new MatchAllDocsQuery();
        IndexSearcher searcher = getIndexSearcher();
        TopDocs topDocs = searcher.search(query, 10); 
        
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
    }
    
    /**
     * 不匹配任何文档
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void testMatchNoDocsQuery() throws ParseException, IOException {  
        Query query = new MatchNoDocsQuery();
        IndexSearcher searcher = getIndexSearcher();
        TopDocs topDocs = searcher.search(query, 10); 
        
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
    }
	 
	 /**
     * 全文检索
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void testQueryParser() throws ParseException, IOException {
        //使用WhitespaceAnalyzer分析器不会忽略大小写，也就是说大小写敏感
        QueryParser queryParser = new QueryParser("bookcontent", new IKAnalyzer());
        Query query = queryParser.parse("2018");
        IndexSearcher searcher = getIndexSearcher();
        TopDocs topDocs = searcher.search(query, 10); 
        
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
    }
	
    /**
     *  词项查询
     *  最基本、最常用的查询。用来查询指定字段包含指定词项的文档。
     */
    @Test
    public void termQuery() {
    	try {
    		IndexSearcher searcher = getIndexSearcher();
			
			TermQuery tq = new TermQuery(new Term("bookname", "西游记")); 
	        TopDocs topDocs = searcher.search(tq, 10);
	        printTopDocs(topDocs);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * 布尔查询
     * 搜索的条件往往是多个的，如要查询名称包含“电脑” 或 “thinkpad”的商品，就需要两个词项查询做或合并。布尔查询就是用来组合多个子查询的。
     * 每个子查询称为布尔字句 BooleanClause，布尔字句自身也可以是组合的。 组合关系支持如下四种：
     * Occur.SHOULD 或
     * Occur.MUST 且
     * Occur.MUST_NOT 且非
     * Occur.FILTER 同 MUST，但该字句不参与评分
     * 布尔查询默认的最大字句数为1024，在将通配符查询这样的查询rewriter为布尔查询时，往往会产生很多的字句，可能抛出TooManyClauses 异常。
     * 可通过BooleanQuery.setMaxClauseCount(int)设置最大字句数。
     */
    @Test
    public void booleanQuery() {
    	try { 
			// 创建一个indexsearcher对象
			IndexSearcher searcher = getIndexSearcher();
			
			BooleanQuery.Builder builder = new BooleanQuery.Builder();
			// 书内容
			QueryParser queryParser = new QueryParser("bookcontent", new IKAnalyzer());
		    Query query1 = queryParser.parse("西游记");
		    builder.add(query1, Occur.MUST);
		    
		    // 书名称
		    Query qymc = new FuzzyQuery(new Term("bookname", "西游记"));
			builder.add(qymc, Occur.MUST);
		    
			// 书类型
			Query gmsfhm = new TermQuery(new Term("booktype", "小说"));
			builder.add(gmsfhm, Occur.MUST);
		    
			BooleanQuery booleanQuery = builder.build();
			 
	        TopDocs topDocs = searcher.search(booleanQuery, 10);
	        printTopDocs(topDocs);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
     
    /**
     * 短语查询
     * 最常用的查询，匹配特定序列的多个词项。PhraserQuery使用一个位置移动因子（slop）来决定任意两个词项的位置可最大移动多少个位置来进行匹配，默认为0。
     * 有两种方式来构建对象：
     * 注意：所有加入的词项都匹配才算匹配（即使是你在同一位置加入多个词项）。如果需要在同一位置匹配多个同义词中的一个，适合用MultiPhraseQuery
     */
    @Test
    public void phraseQuery() {
    	try {  
			PhraseQuery phraseQuery1 = new PhraseQuery("bookcontent", "根据", "明代");

			PhraseQuery phraseQuery2 = new PhraseQuery(0, "bookcontent", "根据", "明代");

			PhraseQuery phraseQuery3 = new PhraseQuery("bookcontent", "笔记本电脑", "联想");

			PhraseQuery phraseQuery4 = new PhraseQuery.Builder()
			    .add(new Term("bookcontent", "根据"), 4)
			    .add(new Term("bookcontent", "施耐"), 5).build();
			// 这两句等同
			PhraseQuery phraseQuery5 = new PhraseQuery.Builder()
			    .add(new Term("bookcontent", "笔记本电脑"), 0)
			    .add(new Term("bookcontent", "联想"), 1).build();
			
			doSearch(phraseQuery2); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    } 
    
    /**
     * 多重短语查询
     * 短语查询的一种更通用的用法，支持同位置多个词的OR匹配。通过里面的Builder来构建MultiPhraseQuery：
     */
    @Test
    public void multiPhraseQuery() {
    	try {  
    		// 4 MultiPhraseQuery 多重短语查询
    		Term[] terms = new Term[2];
    		terms[0] = new Term("bookcontent", "根据");
    		terms[1] = new Term("bookcontent", "根据明代");
    		Term t = new Term("bookcontent", "施耐");
    		MultiPhraseQuery multiPhraseQuery = new MultiPhraseQuery.Builder()
    		    .add(terms).add(t).build();

    		// 对比 PhraseQuery在同位置加入多个词 ，同位置的多个词都需匹配，所以查不出。
    		PhraseQuery pquery = new PhraseQuery.Builder().add(terms[0], 0)
    		    .add(terms[1], 0).add(t, 1).build();
			
			doSearch(multiPhraseQuery); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
     
    /**
     * 临近查询（跨度查询）
     * 用于更复杂的短语查询，可以指定词间位置的最大间隔跨度。通过组合一系列的SpanQuery 实例来进行查询，可以指定是否按顺序匹配、slop、gap
     */
    @Test
    public void spanNearQuery() {
    	try {  
    		// SpanNearQuery 临近查询
    		SpanTermQuery tq1 = new SpanTermQuery(new Term("bookcontent", "中央电视台"));
    		SpanTermQuery tq2 = new SpanTermQuery(new Term("bookcontent", "无锡"));
    		SpanNearQuery spanNearQuery = new SpanNearQuery(
    		    new SpanQuery[] { tq1, tq2 }, 0, true);

    		// SpanNearQuery 临近查询 gap slop 使用
    		SpanNearQuery.Builder spanNearQueryBuilder = SpanNearQuery
    		    .newOrderedNearQuery("bookcontent");
    		spanNearQueryBuilder.addClause(tq1).addGap(0).setSlop(1)
    		    .addClause(tq2);
    		SpanNearQuery spanNearQuery5 = spanNearQueryBuilder.build();
//    		IndexSearcher searcher = getIndexSearcher();
//			TopDocs topDocs = searcher.search(spanNearQueryBuilder, 10);
			doSearch(spanNearQuery); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
     
    /**
     * 词项范围查询
     * 用于查询包含某个范围内的词项的文档，如以字母开头a到c的词项。词项在反向索引中是排序的，只需指定的开始词项、结束词项，就可以查询该范围的词项。
     * 如果是做数值的范围查询则用 PointRangeQuery 
     * 参数说明:
     * 第1个参数：要查询的字段-field
     * 第2个参数:：下边界词-lowerTerm 
     * 第3个参数：上边界词-upperTerm 
     * 第4个参数：是否包含下边界-includeLower 
     * 第5个参数：是否包含上边界 includeUpper 
     */
    @Test
    public void termRangeQuery() {
    	try {   
    		// TermRangeQuery 词项范围查询
    		TermRangeQuery termRangeQuery = TermRangeQuery.newStringRange("bookcontent",
    		    "中央电视台", "同名小说改编", false, true);
			doSearch(termRangeQuery); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * 前缀查询
     * PrefixQuery：前缀查询，查询包含以xxx为前缀的词项的文档，是通配符查询，如 app，实际是 app*
     */
    @Test
    public void prefixQuery() {
    	try {   
    		// PrefixQuery 前缀查询
    		PrefixQuery prefixQuery = new PrefixQuery(new Term("bookcontent", "中国"));
			doSearch(prefixQuery); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * 通配符查询
     * WildcardQuery：通配符查询， *表示0个或多个字符，?表示1个字符，\是转义符。通配符查询可能会比较慢，不可以通配符开头（那样就是所有词项了）
     */
    @Test
    public void wildcardQuery() {
    	try {   
    		// WildcardQuery 通配符查询
    		WildcardQuery wildcardQuery = new WildcardQuery(
    		    new Term("bookcontent", "中国*"));
			doSearch(wildcardQuery); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * 正则表达式查询
     * RegexpQuery：正则表达式查询，词项符合某正则表达式
     */
    @Test
    public void regexpQuery() {
    	try {   
    		// RegexpQuery 正则表达式查询
    		RegexpQuery regexpQuery = new RegexpQuery(new Term("bookcontent", "厉害.{4}"));
			doSearch(regexpQuery); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
     
    /**
     * 模糊查询
     * 简单地与索引词项进行相近匹配，允许最大2个不同字符。常用于拼写错误的容错：如把 “thinkpad” 拼成 “thinkppd”或 “thinkd”，使用FuzzyQuery 仍可搜索到正确的结果。
     */
    @Test
    public void fuzzyQuery() {
    	try {   
    		// FuzzyQuery 模糊查询
    		FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("bookcontent", "猪八戒"));

    		FuzzyQuery fuzzyQuery2 = new FuzzyQuery(new Term("bookcontent", "thinkd"), 2);

    		FuzzyQuery fuzzyQuery3 = new FuzzyQuery(new Term("bookcontent", "thinkpaddd"));

    		FuzzyQuery fuzzyQuery4 = new FuzzyQuery(new Term("bookcontent", "thinkdaddd"));
			doSearch(fuzzyQuery); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    
    /**
     * 数值查询
     * 前提：查询的数值字段必须索引。通过 IntPoint, LongPoint, FloatPoint, or DoublePoint 中的方法构建对应的查询。以IntPoint为例：
     */
    @Test
    public void pointQuery() {
    	try {   
    		// 精确值查询
    		Query exactQuery = IntPoint.newExactQuery("bookprice", 123);

    		// 数值范围查询
    		Query pointRangeQuery = IntPoint.newRangeQuery("bookprice", 111,134);

    		// 集合查询
    		Query setQuery = IntPoint.newSetQuery("bookprice", 1999900, 1000000,2000000);
			doSearch(exactQuery); 
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
    		Query query = parser.parse("中国文艺央视");
//    		sortSearch(query); 
    		highLightSearch(query);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * 传统解析器-多默认字段 
     * B. 基于新的 flexible 框架的解析器：StandardQueryParser
     */
    @Test
    public void MultiFieldQueryParser() {
    	try {    
    		String[] multiDefaultFields = { "bookname", "booktype", "bookcontent" };
    		org.apache.lucene.queryparser.classic.MultiFieldQueryParser multiFieldQueryParser = new org.apache.lucene.queryparser.classic.MultiFieldQueryParser(
    		        multiDefaultFields, new IKAnalyzer());
    		// 设置默认的组合操作，默认是 OR
    		multiFieldQueryParser.setDefaultOperator(Operator.OR);
    		Query query = multiFieldQueryParser.parse("西游记》又称央视86版《西游记》，改编自明12345678901");
			doSearch(query); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * 新解析框架的标准解析器
     * B. 基于新的 flexible 框架的解析器：StandardQueryParser
     */
    @Test
    public void StandardQueryParser() {
    	try {    
    		SimpleQueryParser queryParserHelper = new SimpleQueryParser(new IKAnalyzer(),"bookcontent");
    		// 设置默认字段
    		// queryParserHelper.setMultiFields(CharSequence[] fields);
    		// queryParserHelper.setPhraseSlop(8);
    		// Query query = queryParserHelper.parse("a AND b", "defaultField");
    		Query query5 = queryParserHelper.parse("央视");
    		sortSearch(query5); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * 高亮排序查询
     * @throws InvalidTokenOffsetsException 
     */
    private void highLightSearch(Query query) throws InvalidTokenOffsetsException {
    	// 获取一个indexReader对象
		try {
			Analyzer ikanalyzer = new IKAnalyzer();
			IndexSearcher searcher = getIndexSearcher();
			//true表示降序
			//SortField.Type.SCORE  根据相关度进行排序(默认)
			//SortField.Type.DOC    根据文档编号或者说是索引顺序
			//SortField.Type.FLOAT(Long等),根据fieldName的数值类型进行排序
			SortField sortField = new SortField("bookprice",SortField.Type.INT,false);
			Sort sort = new Sort(sortField);
			
			TopDocs topDocs = searcher.search(query, 10, sort);
	    	System.out.println("数字查询");  
	        System.out.println("命中结果数为: "+ topDocs.totalHits); 
	        // 返回查询结果。遍历查询结果并输出。
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc scoreDoc : scoreDocs) {
				int doc = scoreDoc.doc;
				Document document = searcher.doc(doc);  
				String text = document.get("bookcontent");  
		        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");  
		        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));  
		        highlighter.setTextFragmenter(new SimpleFragmenter(text.length()));  
		        if (text != null) {  
		            TokenStream tokenStream = ikanalyzer.tokenStream("bookcontent", new StringReader(text));  
		            String highLightText = highlighter.getBestFragment(tokenStream,text);  
		            System.out.println("高亮显示第 " + (doc + 1) + " 条检索结果如下所示：");  
		            System.out.println("bookcontent: "+highLightText);  
		        }  
				
				// 打印content字段的值
				System.out.println("bookid: "+document.get("bookid"));
				System.out.println("bookname: "+document.get("bookname"));
				System.out.println("booktype: "+document.get("booktype"));
				System.out.println("bookprice: "+document.get("bookprice"));
				System.out.println("bookdate: "+document.get("bookdate"));
//				System.out.println("bookcontent: "+document.get("bookcontent")); 
				System.out.println("查询得分是: "+scoreDoc.score);
				System.out.println("--------------我是分割线------------------");
			} 
		} catch (IOException e) { 
			e.printStackTrace();
		} 
    } 
    
    /**
     * 排序查询
     */
    private void sortSearch(Query query) {
    	// 获取一个indexReader对象
		try {
			IndexSearcher searcher = getIndexSearcher();
			//true表示降序
			//SortField.Type.SCORE  根据相关度进行排序(默认)
			//SortField.Type.DOC    根据文档编号或者说是索引顺序
			//SortField.Type.FLOAT(Long等),根据fieldName的数值类型进行排序
			SortField sortField = new SortField("bookcontent",SortField.Type.SCORE,false);
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
				System.out.println("bookprice: "+document.get("bookprice"));
				System.out.println("bookcontent: "+document.get("bookcontent"));
				System.out.println("查询得分是: "+scoreDoc.score);
				System.out.println("--------------我是分割线------------------");
			} 
		} catch (IOException e) { 
			e.printStackTrace();
		} 
    } 
    
    /**
     * 查询打印结果
     */
    private void doSearch(Query query) {
    	// 获取一个indexReader对象
		try {
			IndexSearcher searcher = getIndexSearcher();
			TopDocs topDocs = searcher.search(query, 10);
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
			IndexSearcher searcher = getIndexSearcher();
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
