package com.tentinet.app.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 字符串工具类
 * 
 * @author jobs1127
 *
 */
public class StringUtil {
    /***
     * 判断传进来的字符串数组，是否全部为空字符串或null，只要有一个不空则返回false
     * @param ss
     * @return
     */
    public static boolean isBlankAll(String... ss) {
        for (String s : ss) {
            if (isNotBlank(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 至少有1个为空字符串或null
     * @param ss
     * @return 只要有1个字符串为空字符串或null就返回true
     */
    public static boolean isBlankAtLeastOne(String... ss) {
        for (String s : ss) {
            if (StringUtils.isBlank(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否 不为空字符串或非null
     * @param s
     * @return
     */
    public static boolean isNotBlank(String s) {
        return StringUtils.isNotBlank(s);
    }

    /***
     * 判断字符串是否 为空字符串或null
     * @param s
     * @return
     */
    public static boolean isBlank(String s) {
        return StringUtils.isBlank(s);
    }
    /**
     * 判断字符串是否 不为空字符串
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return s != null && s.trim().length() > 0;
    }

    /***
     * 判断字符串是否为ip
     * @param s
     * @return
     */
    public static boolean isIP(String s) {
        return s.matches("^(0?0?[1-9]|0?[1-9]\\d|1\\d\\d|2[01]\\d|22[0-3])(\\.([01]?\\d?\\d|2[0-4]\\d|25[0-5])){3}$");
    }

    /**
     * 替换空白字符,包括前后空白，换行空白，回车空白等 
     * @param str
     * @return
     */
    public static String trimAll(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    
    /***
     * 替换成<br>换行
     * @param str
     * @return
     */
    public static String replaceBr(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\t|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("<br>");
        }
        return dest;
    }

    /**
     * 替换成 \n换行
     * @param str
     * @return
     */
    public static String replaceHh(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\t|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("\n");
        }
        return dest;
    }

    /**
     * 过滤特殊字符  清除掉所有特殊字符
     * @param str
     * @return
     */
    public static String strFilter(String str) {
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return trimAll(m.replaceAll(""));
    }
    /**
     * 过滤特殊字符  清除掉所有特殊字符，并清掉所有空格
     * @param str
     * @return
     */
    public static String strFilterAndTrim(String str) {
    	// 清除掉所有特殊字符
    	String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    	Pattern p = Pattern.compile(regEx);
    	Matcher m = p.matcher(str);
    	return m.replaceAll("").trim();
    }

    /**
     * 空格隔开的id转换成逗号隔开的id，用于sql语句
     * @param strId
     * @return
     */
    public static String spaceId2DouHaoId(String strId) {
        StringBuilder str = new StringBuilder();
        if (strId != null) {
            String[] ids = strId.trim().split(" ");
            for (String s : ids) {
                if (StringUtils.isNotBlank(s)) {
                    str.append(s + ",");
                }
            }
            if(str.toString().contains(",")){
                str.deleteCharAt(str.length() - 1);
            }
        }
        return str.toString();
    }
    /***
     * 空格隔开的id字符串，转换成字符串数组
     * @param strId
     * @return
     */
    public static String[] strId2StrArray(String strId) {
    	String[] ids = null;
    	if (strId != null && strId.trim().length()>=1) {
    		ids = strId.trim().split(" ");
    	}
    	return ids;
    }
    /***
     * 逗号隔开的id字符串，转换成字符串数组
     * @param strId
     * @return
     */
    public static String[] strDouHaoId2StrArray(String strId,String split) {
    	String[] ids = null;
    	if (strId != null && strId.trim().length()>=1) {
    		ids = strId.trim().split(split);
    	}
    	return ids;
    }
    /***
     * 空格隔开的id字符串，转换成list<String> split:隔开id的字符串
     * @param strId
     * @return
     */
    public static List<String> strId2List(String strId,String split) {
    	 List<String> list= new ArrayList<String>(1024);
    	if (strId != null && strId.trim().length()>=1) {
    		String[] ids = strId.trim().split(split);
    		for (String s : ids) {
				if(StringUtil.isNotBlank(trimAll(s))) {
					list.add(s);
				}
			}
    	}
    	return list;
    }
    /***
     * 逗号隔开的id字符串，转换成list<String>
     * @param strId
     * @return
     */
    public static List<String> strDaoHaoId2List(String strId) {
    	List<String> list= new ArrayList<String>(1024);
    	if (strId != null && strId.trim().length()>=1) {
    		String[] ids = strId.trim().split(",");
    		list.addAll(Arrays.asList(ids));
    	}
    	return list;
    }
    /**
     * list里的id转换成逗号隔开的id，用于sql语句
     * @param list
     * @return
     */
    public static String ListId2DouHaoId(List<Object> list) {
        StringBuilder str = new StringBuilder();
        if (list != null && !list.isEmpty()) {
            for (Object s : list) {
                if (s != null && StringUtils.isNotBlank(s.toString())) {
                    str.append(s + ",");
                }
            }
            if(str.toString().contains(",")){
                str.deleteCharAt(str.length() - 1);
            }
        }
        return str.toString();
    }

    /**
     * 判断2个字符串是否equals，区分大小写
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1,String str2){
        if(str1 == null || str2 == null){
            return false;
        }
        return StringUtils.equals(str1,str2);
    }
    /**
     * 判断2个字符串是否equals，忽略大小写
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equalsIgnoreCase(String str1,String str2){
        if(str1 == null || str2 == null){
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }
    /***
     * 把字符串转换成Integer，转换失败返回null
     */
    public static Integer parseInt(String str) {
    	Integer target = null;
    	if(str == null) {
    		target=null;
    	}
    	str = trimAll(str);
    	try {
    		target = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			target=null;
		}
    	return target;
    }
    /***
     * 把字符串转换成Float，转换失败返回null
     */
    public static Float parseFloat(String str) {
    	Float target = null;
    	if(str == null) {
    		target=null;
    	}
    	str = trimAll(str);
    	try {
    		target = Float.parseFloat(str);
    	} catch (NumberFormatException e) {
    		target=null;
    	}
    	return target;
    }
    /***
	 * 格式化字符串，获取数字
	 * @param str
	 * @return
	 */
	public static String formatStringGetNumber(String str) {
		if(str == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		//分割!
        String[] s=str.split("\\D+");
        for (String string : s) {
			sb.append(string);
		}
		return trimAll(sb.toString());
	}
    
    public static String[] listToArray(List<String> strArray) {
    	if(strArray == null) {
    		return null;
    	}
    	String[] array = new String[strArray.size()];
    	for(int i = 0 ;i<strArray.size();i++) {
    		array[i]=strArray.get(i);
    	}
    	return array;
    }
    /***
     * 通过 - 连接字符串
     * @param args
     * @return
     */
    public static String concat(String ...args) {
    	StringBuffer sb = new StringBuffer();
    	if(args == null) {
    		return null;
    	} else {
    		for (String s : args) {
				sb.append(s+"-");
			}
    		if(sb.length()>=1) {
    			sb.deleteCharAt(sb.length()-1);
    		}
    	}
    	return sb.toString();
    }

    /***
     * 逗号隔开的id字符串，转换成字符串数组
     * @param strId
     * @return
     */
    public static String[] strToArray(String strId,String split) {
    	String[] ids = null;
    	if (strId != null && strId.trim().length()>=1) {
    		ids = strId.trim().split(split);
	    	for (int i = 0; i < ids.length; i++) {
				ids[i] = trimAll(ids[i]);
			}
    	}
    	return ids;
    }
    /***
     * list 转换 String数组
     * @param l
     * @return
     */
    public static String[] listToStrArray(List<String> l) {
    	if(l == null) {
    		return null;
    	}
    	ArrayList<String> list=(ArrayList<String>) l;
		String[] strings = new String[list.size()];
		String[] array = list.toArray(strings);
		return array;
    }
    /***
     * 为map里元素除去空格并返回map
     * @param map
     * @return
     */
    public static Map<String, Object> trim4Map(Map<String, Object> map){
    	for(Entry<String, Object> entry:map.entrySet()) {
    		map.put(entry.getKey(), entry.getValue().toString().trim());
    	}
    	return map;
    }
    public static void main(String[] args) {//TODO
    	System.out.println(trimAll("p  p          张富贵   "));
	}
    
}
