package main.java.cn.lmc.collection.test.enumtest;

/**
 * 枚举测试类（包括方法）
 * @author limingcheng
 *
 */
public enum EnumDemo2 {
	MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6) {
		@Override
	    public boolean isRest() {
	        return true;
	    }
    }, SUN(0) {
        @Override
        public boolean isRest() {
            return true;
        }
    };
 
    private int value;
 
    private EnumDemo2(int value) {
        this.value = value;
    }
 
    public int getValue() {
        return value;
    }
 
    public boolean isRest() {
        return false;
    }
}
