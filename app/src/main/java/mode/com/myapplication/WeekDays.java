package mode.com.myapplication;

/**
 * Created by 姬玉鹏 on 2018/2/22.
 */

public enum WeekDays {
    //枚举对象必须放在最前面，匿名内部类的创建可以带参数，必须实现父类的抽象方法
    Mon(1){
        @Override
        public WeekDays next() {
            return Sun;
        }
    },
    Sun(2){
        @Override
        public WeekDays next() {
            return Mon;
        }
    };
    private int num;

    //枚举的构造函数是默认为private的，可以带参数
    WeekDays(int num) {
        this.num = num;
    }

    public abstract WeekDays next();

}
