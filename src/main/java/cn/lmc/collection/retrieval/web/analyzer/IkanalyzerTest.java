package main.java.cn.lmc.collection.retrieval.web.analyzer;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

public class IkanalyzerTest {
	
	private static void analysisString() {
		String text="佛山市南海尚铝五金制品有限公司";
		StringReader sr = new StringReader(text);
		IKSegmenter ik = new IKSegmenter(sr, true);
		Lexeme lex=null;
		try {
			while((lex=ik.next())!=null){
//				System.out.print(lex.getLexemeText()+"|");
				System.out.println(lex.getLexemeText());
			}
		} catch (IOException e) { 
			e.printStackTrace();
		} finally {
			// 关闭流资源
			if(sr != null) {
				sr.close();
			}
		}
	} 

	public static void main(String[] args) {
		analysisString();
	} 
}
