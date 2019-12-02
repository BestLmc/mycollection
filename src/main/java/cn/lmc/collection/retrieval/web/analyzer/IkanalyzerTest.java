package main.java.cn.lmc.collection.retrieval.web.analyzer;

import main.java.cn.lmc.collection.common.lucene.IKanalyzer8.IKAnalyzer8;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

public class IkanalyzerTest {

    private static void doToken(TokenStream ts) throws IOException {
        ts.reset();
        CharTermAttribute cta = ts.getAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {
            System.out.print(cta.toString() + "|");
        }
        System.out.println();
        ts.end();
        ts.close();
    }
	
	private static void analysisString() {
		String text="五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine";
		StringReader sr = new StringReader(text);
		IKSegmenter ik = new IKSegmenter(sr, true);
		Lexeme lex=null;
		try {
			while((lex=ik.next())!=null){
				System.out.print(lex.getLexemeText()+"|");
			}
            System.out.println();
		} catch (IOException e) { 
			e.printStackTrace();
		} finally {
			// 关闭流资源
			if(sr != null) {
				sr.close();
			}
		}
	}

	/**
     * 使用分词器对字符串分词
     */
    private static void IKanalyzerStr() throws ParseException {
        String words = "张三说的确实在理";
        // 细粒度切分
        QueryParser queryParser = new QueryParser("word", new IKAnalyzer());
        Query query = queryParser.parse(words);
        System.out.println("细粒度切分分词结果:"+query);
        // 智能分词
        QueryParser queryParserId = new QueryParser("word", new IKAnalyzer(true));
        Query queryId = queryParser.parse(words);
        System.out.println("智能分词分词结果:"+queryId);
    }

    /**
     * 使用分词器对字符串分词
     */
    private static void IKanalyzer8() throws ParseException, IOException {
        String words = "五月天创建的人生有限公司举报了一场演唱会，陈信宏唱了一首do you ever shine,周杰伦";
        // 细粒度切分
        try (Analyzer ik = new IKAnalyzer8();) {
            TokenStream ts = ik.tokenStream("content", words);
            System.out.println("IKAnalyzer中文分词器 智能切分，英文分词效果：");
            doToken(ts);
            ts = ik.tokenStream("content", words);
            System.out.println("IKAnalyzer中文分词器 智能切分，中文分词效果：");
            doToken(ts);
        }

        // IKAnalyzer 智能切分
        try (Analyzer ik = new IKAnalyzer8(true);) {
            TokenStream ts = ik.tokenStream("content", words);
            System.out.println("IKAnalyzer中文分词器 智能切分，英文分词效果：");
            doToken(ts);
            ts = ik.tokenStream("content", words);
            System.out.println("IKAnalyzer中文分词器 智能切分，中文分词效果：");
            doToken(ts);
        }
    }
	public static void main(String[] args) throws ParseException, IOException {
//		analysisString();
//		IKanalyzerStr();
        IKanalyzer8();
	} 
}
