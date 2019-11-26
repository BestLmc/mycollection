package main.java.cn.lmc.collection.retrieval.web.analyzer;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

public class IkanalyzerTest {
	
	private static void analysisString() {
		String text="杨超越，1998年7月31日出生于江苏省盐城市，中国内地流行乐女歌手、影视演员，" +
				"女子演唱组合CH2、火箭少女101成员。2017年，加入女子演唱组合CH2，从而正式出道。" +
				"2018年，参加腾讯视频女团青春成长节目《创造101》，最终获得第3名，" +
				"并加入女子演唱组合火箭少女101；同年，相继推出个人单曲《跟着我一起》、《冲鸭冲鸭》、" +
				"《招财进宝》；12月15日，获得“影响中国”年度演艺人物奖";
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
