package com.tentinet.app.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 算术工具类
 * 
 * @author jobs1127
 *
 */
public class ArithUtil {
	//保留2位小数
	static DecimalFormat df = new DecimalFormat("0.00");
	static {
		df.setRoundingMode(RoundingMode.HALF_UP);
	}
	/***
	 * 保留2位，参数是String
	 * @param value
	 * @return
	 */
	public static String format2bit(String value) {
		String val = "0";
		if(value != null && !value.equals("")) {
			try {
				val = df.format(Double.parseDouble(value));
			} catch (NumberFormatException e) {
				val = "0";
			}
		}
		return val;
	}	
	/***
	 * 保留2位，参数是double
	 * @param value
	 * @return
	 */
	public static String format2bit(double value) {
		return df.format(value);
	}		
	/**
	 * 提供精确加法计算的add方法
	 * @param value1 被加数
	 * @param value2 加数
	 * @return double 两个参数的和
	 */
	public static double add(double value1,double value2){
		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.add(b2).doubleValue();
	}
    /**
     * 提供精确加法计算的add方法
     * @param value1 被加数
     * @param value2 加数
     * @return float 两个参数的和
     */
    public static float add(float value1,float value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.add(b2).floatValue();
    }
    
    /**
     * 提供精确减法运算的sub方法
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数double的差
     */
    public static double subtract(double value1,double value2){
    	BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
    	BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
    	return b1.subtract(b2).doubleValue();
    }
    /**
     * 提供精确减法运算的sub方法
     * @param value1 被减数
     * @param value2 减数
     * @return 两个float参数的差 
     */
    public static float subtract(float value1,float value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.subtract(b2).floatValue();
    }
    
    /**
     * 提供精确乘法运算的mul方法
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数double的积
     */
    public static double multiply(double value1,double value2){
    	BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
    	BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
    	return b1.multiply(b2).doubleValue();
    }
    /**
     * 提供精确乘法运算的mul方法
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数double的积
     */
    public static float multiply(float value1,float value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.multiply(b2).floatValue();
    }
    
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
     * 定精度，以后的数字四舍五入
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个double参数的商
     * @throws IllegalAccessException
     */
    public static double divide(double value1,double value2,int scale) throws IllegalAccessException{
    	//如果精确范围小于0，抛出异常信息
    	if(scale<0){         
    		throw new IllegalAccessException("精确度不能小于0");
    	}
    	BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
    	BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
    	return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();    
    }
    /**
     * 提供精确的除法运算方法div
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个double参数的商
     * @throws IllegalAccessException
     */
    public static float divide(float value1,float value2,int scale) throws IllegalAccessException{
        //如果精确范围小于0，抛出异常信息
        if(scale<0){         
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2, scale).floatValue();    
    }
    
    /** 
	* 提供精确的小数位四舍五入处理。 
	* @param v 需要四舍五入的数字 
	* @param scale 小数点后保留几位 
	* @return 四舍五入后的结果 
	*/  
	public static double round(double v, int scale) throws IllegalAccessException{  
	   if (scale < 0) {  
		   throw new IllegalAccessException("精确度不能小于0");
	   }  
	   BigDecimal b = new BigDecimal(Double.toString(v));  
	   BigDecimal ne = new BigDecimal("1");  
	   return b.divide(ne, scale, BigDecimal.ROUND_HALF_UP).doubleValue();  
	}  
	
	/***
	 * 返回金额 2位小数
	 * @param m 费用
	 * @param count 数量
 	 * @return 费用*数量
	 */
	public static String money(String m, String count) {
		String mymoney = "0.00";
		if(m == null || count == null) {
			return mymoney;
		}
		try {
			mymoney = df.format(Float.parseFloat(count.trim())*Float.parseFloat(m.trim()));
		} catch (Exception e) {
			mymoney = "0.00";
		}
		return mymoney;
	}
	/***
	 * 返回 m1/m2 % 
	 * @param m1
	 * @param m2
	 * @return
	 */
	public static String percent(String m1, String m2) {
		DecimalFormat df = new DecimalFormat("0");
		df.setRoundingMode(RoundingMode.HALF_UP);
		float result = 0;
		if(m1 == null || m2 == null) {
			result = 0;
			return df.format(result) + "%";
		}
		try {
			result = Float.parseFloat(m1.trim()) / Float.parseFloat(m2.trim()) * 100;
		} catch(Exception e) {
			result = 0;
		}
		return df.format(result) + "%";
	}
	/***
	 * 总费用 1+3+4 保留2位小数
	 * @param money1
	 * @param money3
	 * @param money4
	 * @return
	 */
	public static String total(String money1, String money3, String money4) {
		float total = 0;
		try {
			float tuiguang = Float.parseFloat(money1);
			float piaoju = Float.parseFloat(money3);
			float dabiao = Float.parseFloat(money4);
			total = tuiguang + piaoju + dabiao;
		} catch(Exception e) {
			total = 0;
		}
		return df.format(total);
	}
	public static void main(String[] args) {
		System.out.println(format2bit("21.7099990844727"));
	}
}
