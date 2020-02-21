package main.java.cn.lmc.collection.algorithm.hashtable;

/**
 * 向操作系统申请9个存储单元。这里有个小问题，我们得到的存储单元的地址很可能并不是从1到9，而是从134456开始的。
 * 好在我们并不需要直接跟操作系统打交道，高级语言会为我们搞定这些琐事。当我们使用高级语言创建一个数组时，
 * 相当于申请了一块连续的存储空间，数组的下标是每个存储单元（抽象）的地址。这样我们第一个 O(1) 复杂度的容器 SingleIntSet 很容易就可以完成了，
 * 它只能存储 0～9 这10个数字：
 * SingleIntSet
 *
 * @author limingcheng
 * @Date 2020/2/21
 */
public class SingleIntSet
{
    private Object[] _values = new Object[10];

    public void Add(int item)
    {
        _values[item] = item;
    }
    public void Remove(int item)
    {
        _values[item] = null;
    }
    public boolean Contains(int item)
    {
        if (_values[item] == null){
            return false;
        } else {
            return (int) _values[item] == item;
        }
    }
}
