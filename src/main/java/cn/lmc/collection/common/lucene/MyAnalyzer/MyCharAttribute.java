package main.java.cn.lmc.collection.common.lucene.MyAnalyzer;

import org.apache.lucene.util.Attribute;

/**
 * MyCharAttribute
 *
 * @author limingcheng
 * @Date 2019/11/28
 */
public interface MyCharAttribute extends Attribute {
    void setChars(char[] buffer, int length);

    char[] getChars();

    int getLength();

    String getString();
}
