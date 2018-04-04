package mode.com.myapplication;

/**
 * Created by 姬玉鹏 on 2018/2/22.
 */

public abstract class WeekDay {

    private int num;
    //私有构造方法
    private WeekDay() {
    }
    //在生成匿名内部类的时候，可以传参给父类的构造函数
    private WeekDay(int num) {
        this.num = num;
    }

    //对外提供的抽象方法，由子类去实现
    public abstract WeekDay next();

    public   static WeekDay  MON=new WeekDay(1) {
        @Override
        public WeekDay next() {
            return SUN;
        }

        @Override
        public String toString() {
            return "SUN";
        }
    };


    public   static WeekDay  SUN=new WeekDay(1) {
        @Override
        public WeekDay next() {
            return MON;
        }

        @Override
        public String toString() {
            return "MON";
        }
    };

}
