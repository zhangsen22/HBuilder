//package ccash.android.com.util;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.text.DecimalFormat;
//
///**
// * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
// */
//public class Arith {
//
//    //默认除法运算精度
//    public static int DEF_SCALE = 2;
//    public static RoundingMode DEF_ROUNDMODE = RoundingMode.DOWN;
//
//    //这个类不能实例化
//    private Arith() {
//    }
//
//
//    /**
//     * 提供精确的加法运算。
//     *
//     * @param v1 被加数
//     * @param v2 加数
//     * @return 两个参数的和
//     */
//    public static double add(double v1, double v2) {
//        return add(v1, v2, DEF_SCALE, DEF_ROUNDMODE);
//    }
//
//    /**
//     * 提供精确的加法运算。
//     *
//     * @param v1 被加数
//     * @param v2 加数
//     * @param scale
//     * @return 两个参数的和
//     */
//    public static double add(double v1, double v2, int scale) {
//        return add(v1, v2, scale, DEF_ROUNDMODE);
//    }
//
//    /**
//     * 提供精确的加法运算。
//     *
//     * @param v1 被加数
//     * @param v2 加数
//     * @param scale
//     * @param mode
//     * @return 两个参数的和
//     */
//    public static double add(double v1, double v2, int scale, RoundingMode mode) {
//        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(scale, mode);
//        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(scale, mode);
//        return b1.add(b2).setScale(scale, mode).doubleValue();
//    }
//
//    /**
//     * 提供精确的减法运算。
//     *
//     * @param v1 被减数
//     * @param v2 减数
//     * @return 两个参数的差
//     */
//    public static double sub(double v1, double v2) {
//        return sub(v1, v2, DEF_SCALE, DEF_ROUNDMODE);
//    }
//
//    /**
//     * 提供精确的减法运算。
//     *
//     * @param v1 被减数
//     * @param v2 减数
//     * @param scale
//     * @return 两个参数的差
//     */
//    public static double sub(double v1, double v2, int scale) {
//        return sub(v1, v2, scale, DEF_ROUNDMODE);
//    }
//
//    /**
//     * 提供精确的减法运算。
//     *
//     * @param v1 被减数
//     * @param v2 减数
//     * @param scale
//     * @param mode
//     * @return 两个参数的差
//     */
//    public static double sub(double v1, double v2, int scale, RoundingMode mode) {
//        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(scale, mode);
//        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(scale, mode);
//        return b1.subtract(b2).setScale(scale, mode).doubleValue();
//    }
//
//    /**
//     * 提供精确的减法运算。
//     *
//     * @param v1 被减数
//     * @param v2 减数
//     * @param scale
//     * @param mode
//     * @return 两个参数的差
//     */
//    public static BigDecimal subBigDicimal(double v1, double v2, int scale, RoundingMode mode) {
//        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(scale, mode);
//        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(scale, mode);
//        return b1.subtract(b2).setScale(scale, mode);
//    }
//
//    /**
//     * 提供精确的乘法运算。
//     *
//     * @param v1 被乘数
//     * @param v2 乘数
//     * @return 两个参数的积
//     */
//    public static double mul(double v1, double v2) {
//        return mul(v1, v2, DEF_SCALE, DEF_ROUNDMODE);
//    }
//
//    /**
//     * 提供精确的乘法运算。
//     *
//     * @param v1 被乘数
//     * @param v2 乘数
//     * @param scale
//     * @return 两个参数的积
//     */
//    public static double mul(double v1, double v2, int scale) {
//        return mul(v1, v2, scale, DEF_ROUNDMODE);
//    }
//
//    /**
//     * 提供精确的乘法运算。
//     *
//     * @param v1 被乘数
//     * @param v2 乘数
//     * @param scale
//     * @param mode
//     * @return 两个参数的积
//     */
//    public static double mul(double v1, double v2, int scale, RoundingMode mode) {
//        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(scale, mode);
//        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(scale, mode);
//        return b1.multiply(b2).setScale(scale, mode).doubleValue();
//    }
//
//    /**
//     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后N位，以后的数字四舍五入。
//     *
//     * @param v1 被除数
//     * @param v2 除数
//     * @return 两个参数的商
//     */
//    public static double div(double v1, double v2) {
//        return div(v1, v2, DEF_SCALE, DEF_ROUNDMODE);
//    }
//
//    /**
//     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后N位，以后的数字四舍五入。
//     *
//     * @param v1 被除数
//     * @param v2 除数
//     * @param scale
//     * @return 两个参数的商
//     */
//    public static double div(double v1, double v2, int scale) {
//        return div(v1, v2, scale, DEF_ROUNDMODE);
//    }
//
//    /**
//     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
//     *
//     * @param v1 被除数
//     * @param v2 除数
//     * @param scale 表示表示需要精确到小数点以后几位。
//     * @param mode
//     * @return 两个参数的商
//     */
//    public static double div(double v1, double v2, int scale, RoundingMode mode) {
//        if (scale < 0) {
//            throw new IllegalArgumentException(
//                    "The scale must be a positive integer or zero");
//        }
//        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(scale, mode);
//        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(scale, mode);
//        return b1.divide(b2, scale, mode).doubleValue();
//    }
//
//    /**
//     * 提供精确的小数位舍入处理。默认保留2位
//     *
//     * @param v 需要四舍五入的数字
//     * @param scale 小数点后保留几位
//     * @param mode
//     * @return 四舍五入后的结果
//     */
//    public static double round2(double v, RoundingMode mode) {
//        return round(Double.toString(v), 2, mode);
//    }
//
//    /**
//     * 提供精确的小数位舍入处理。默认保留2位，floor
//     *
//     * @param v 需要四舍五入的数字
//     * @param scale 小数点后保留几位
//     * @param mode
//     * @return 四舍五入后的结果
//     */
//    public static double round2(double v) {
//        return round(Double.toString(v), 2, RoundingMode.DOWN);
//    }
//
//    /**
//     * 提供精确的小数位舍入处理。
//     *
//     * @param v 需要舍入的数字
//     * @param scale 小数点后保留几位
//     * @param mode
//     * @return 四舍五入后的结果
//     */
//    public static double round(double v, int scale) {
//        return round(Double.toString(v), scale, DEF_ROUNDMODE);
//    }
//
//    /**
//     * 提供精确的小数位舍入处理。
//     *
//     * @param v 需要舍入的数字
//     * @param scale 小数点后保留几位
//     * @param mode
//     * @return 四舍五入后的结果
//     */
//    public static double round(double v, int scale, RoundingMode mode) {
//        return round(Double.toString(v), scale, mode);
//    }
//
//    /**
//     * 提供精确的小数位舍入处理。
//     *
//     * @param v 需要舍入的数字
//     * @param scale 小数点后保留几位
//     * @param mode
//     * @return 四舍五入后的结果
//     */
//    public static double round(String v, int scale, RoundingMode mode) {
//
//        if (scale < 0) {
//            throw new IllegalArgumentException(
//                    "The scale must be a positive integer or zero");
//        }
//        BigDecimal b = new BigDecimal(v);
//        BigDecimal one = new BigDecimal("1");
//        return b.divide(one, scale, mode).doubleValue();
//    }
//
//    public static void main(String[] args) {
////        double s = 123.1234567;
////        double dd = 32.342342342342;
////        System.out.println(add(s, dd));
////        System.out.println(mul(s, dd));
////        double buyPrice = 6.78;
////        double maxBuyPrice = Arith.round2(buyPrice * (1000 + Double.parseDouble("4")) / 1000, RoundingMode.CEILING);
////        double minBuyPrice = Arith.round2(buyPrice - buyPrice * Double.parseDouble("1") / 1000, RoundingMode.CEILING);
////        System.out.println(maxBuyPrice);
////        System.out.println(minBuyPrice);
////
////        maxBuyPrice = Math.ceil(buyPrice * (1000 + Double.parseDouble("4")) / 10) / 100;
////        minBuyPrice = buyPrice - Math.ceil(buyPrice * Double.parseDouble("1") / 10) / 100;
////
////        System.out.println(maxBuyPrice);
////        System.out.println(minBuyPrice);
//
//        int money = 222222;
//
//        System.out.println(new DecimalFormat("0.00").format(1089.0 * 1.05));
//    }
//};
