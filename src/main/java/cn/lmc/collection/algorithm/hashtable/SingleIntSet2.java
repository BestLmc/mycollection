package main.java.cn.lmc.collection.algorithm.hashtable;

/**
 * SingleIntSet2
 *
 * @author limingcheng
 * @Date 2020/2/21
 */
public class SingleIntSet2
{
    private Object[] _values = new Object[10];

    private int H(int value)
    {
        return value - 10;
    }

    public void Add(int item)
    {
        _values[H(item)] = item;
    }
    public void Remove(int item)
    {
        _values[H(item)] = null;
    }
    public boolean Contains(int item)
    {
        if (_values[H(item)] == null) {
            return false;
        }else {
            return (int) _values[H(item)] == item;
        }
    }
}
