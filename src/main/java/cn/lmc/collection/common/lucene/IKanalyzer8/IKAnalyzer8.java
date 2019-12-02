package main.java.cn.lmc.collection.common.lucene.IKanalyzer8;

import org.apache.lucene.analysis.Analyzer;

/**
 * IKAnalyzer8
 *
 * 因为Analyzer的createComponents方法API改变了需要重新实现分析器
 * @author limingcheng
 * @Date 2019/11/28
 */
public class IKAnalyzer8 extends Analyzer {
    private boolean useSmart = false;

    public IKAnalyzer8() {
        this(false);
    }

    public IKAnalyzer8(boolean useSmart) {
        super();
        this.useSmart = useSmart;
    }

    public boolean isUseSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        IKTokenizer8 tk = new IKTokenizer8(this.useSmart);
        return new TokenStreamComponents(tk);
    }

}
