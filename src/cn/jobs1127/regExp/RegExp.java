package cn.jobs1127.regExp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 正则表达式简单认识 测试
 * 
 * @author jobs1127 "."表示一个字符、*代表0个或多个、+代表一个或多个、?代表0个或1个
 *         []表一个区间范围的一个字符、{}表示个数，多少位，多少个，{2,3}表示最少2个，最多3个 在[]里^是取反 \d 数字：[0-9]
 *         \D 非数字： [^0-9] \s 空白字符：[ \t\n\x0B\f\r] \S 非空白字符：[^\s] \w
 *         单词字符：[a-zA-Z_0-9] \W 非单词字符：[^\w]
 */
public class RegExp {
	public static void p(Object o) {
		System.out.println(o);
	}
	/**
	 * 判断是否是IP
	 * @param s
	 * @return
	 */
	public static boolean isIP(String s) {
		return s.matches("^(0?0?[1-9]|0?[1-9]\\d|1\\d\\d|2[01]\\d|22[0-3])(\\.([01]?\\d?\\d|2[0-4]\\d|25[0-5])){3}$");
	}
	public static void main(String[] args) {
		// 一个点代表一个字符
		p("abc".matches("...")); // true
		// \\d代表数字
		p("a8729ac99aa000kkk".replaceAll("\\d", "*"));
		p("a8729ac99aa000kkk".replaceAll("[a-z]", "*"));
		p("a8729ac99aa000kkkAA".replaceAll("[a-z]", "*"));

		// Pattern 是一个模式 编译一个正则表达式 [a-z]表示a到z任意一个字符 [a-z]{8}表示是a-z范围字符，刚好8个字符
		Pattern p1 = Pattern.compile("[a-z]{8}");
		// 通过这个模式去匹配一个字符串
		Matcher m1 = p1.matcher("jobs1127");
		p(m1.matches());// false

		// 以上三句话等同以下一句话，只是上面的三句话效率更高
		p("jobs1127".matches("[a-z]{8}"));// false

		p("jobsaaaa".matches("[a-z]{8}"));// true

		p("fgh".matches("[a-z]{3}"));// true
		p("fgh".matches("[a-z]{4}"));// false
		System.out.println("------------1-----------");
		// . * + ?
		p("a".matches("."));// true
		p("a".matches(".."));// false
		p("aa".matches("aa"));// true
		p("aaaa".matches("a*"));// true 0或N个a
		p("aaaa".matches("a+"));// true 1个或N个a
		p("".matches("a*"));// true 0或N个a
		p("aaaa".matches("a?"));// false 0或1个a
		System.out.println("------------1.1-----------");
		p("".matches("a?"));// true 0或1个a
		p("a".matches("a?"));// true 0或1个a
		p("214444".matches("\\d{3,100}"));// true 至少3个，最多100个数字
		p("214444a".matches("\\d{3,100}"));// false 至少3个，最多100个数字
		// \\d{1,3}1到3位的数字
		p("192.168.0.aaa".matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"));// false
		// []表示一个范围[0-2]表示0到2的一个数字
		p("192".matches("[0-2][0-9][0-9]"));// true
		System.out.println("------------1.2-----------");
		p("a".matches("[abc]")); // true
		p("a".matches("[^abc]"));// false
		p("A".matches("[a-zA-Z]"));// true
		p("A".matches("[a-z]|[A-Z]"));// true
		p("A".matches("[a-z[A-Z]]"));// true
		p("R".matches("[A-Z&&[RFG]]"));// true A-Z并且在[RFG]中的某1一个
		System.out.println("------------1.3-----------");
		// \s \w \d \
		p(" \n\r\t".matches("\\s{4}"));// true
		p(" ".matches("\\S"));// false
		p("a_6".matches("\\w{3}"));// true
		p("abc888&^%".matches("[a-z]{1,3}\\d+[&^#%]+"));// true
		p("\\".matches("\\\\"));// true
		System.out.println("------------1.4-----------");
		// POSIX Style
		p("a".matches("\\p{Lower}"));// true
		System.out.println("------------1.5-----------");
		// boundary 边界匹配
		p("hello sir".matches("^h*"));// false
		p("hhello sir".matches("^h*"));// false
		p("hello sir".matches("^h.*"));// true 以h开头
		p("hello sir".matches(".*ir$"));// true //以ir结束
		p("hello sir".matches("^h[a-z]{1,3}o\\b.*"));// true \\b单词边界
		p("hellosir".matches("^h[a-z]{1,3}o\\b.*"));// false o旁边是边界
		System.out.println("------------1.6-----------");
		// whilte lines 空白行 的正则表达式
		p(" \n".matches("^[\\s&&[^\\n]]*\\n$"));// true
		System.out.println("------------1.7-----------");
		p("aaa 8888c".matches(".*\\d{4}."));// true
		p("aaa 8888c".matches(".*\\b\\d{4}."));// true
		p("aaa8888c".matches(".*\\d{4}."));// true
		p("aaa8888c".matches(".*\\b\\d{4}."));// false
		System.out.println("------------1.8-----------");
		// 匹配email 的正则表达式
		p("asdfasdfsafsf@dsdfsdf.com".matches("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+"));// true
		System.out.println("------------1.9-----------");
		// matches匹配所有 find找子字符串 lookingAt从头开始找
		Pattern p = Pattern.compile("\\d{3,5}");
		String s = "123-34345-234-00";
		Matcher m = p.matcher(s);
		p(m.matches());// false m在匹配到第四个字符是发现 已经不匹配了
		m.reset(); // 如果没有reset m.find()是从第四个开始找
		p(m.find());// true 可以找到
		p(m.start() + "-" + m.end());// find 123-

		p(m.find());// true 可以找到
		p(m.start() + "-" + m.end());// find 34345-
		p(m.find());// true 可以找到234-
		p(m.start() + "-" + m.end());
		p(m.find());// false 00 不够3个，所有find false
		// p(m.start() + "-" + m.end());//报错，没有找到，但又打印，边界越界
		System.out.println("------------2.0-----------");
		p(m.lookingAt());
		p(m.lookingAt());
		p(m.lookingAt());
		p(m.lookingAt());
		System.out.println("------------2.1-----------");
		// replacement
		Pattern p2 = Pattern.compile("java", Pattern.CASE_INSENSITIVE);// 启用不区分大小写的匹配
		Matcher m2 = p2.matcher("java Java JAVa JaVa IloveJAVA you hateJavaafasdfasdf");
		StringBuffer buf = new StringBuffer();
		int i = 0;
		while (m2.find()) {// 找到符合要求的子串 后面不符合要求就不找了
		// p(m2.group());//把找到的分组显示
		// String str = m2.replaceAll("JAVA");
		// System.out.println(str);
			i++;
			if (i % 2 == 0) {// 偶数转换成小写 以追加的替换方式来替换并填充到buf里
				m2.appendReplacement(buf, "java");
			} else {// 奇数转换成大写
				m2.appendReplacement(buf, "JAVA");
			}
		}
		m2.appendTail(buf);
		p(buf);
		System.out.println("------------2.2-----------");
		// group 根据()来分组 (\\d{3,5})为第一组、([a-z]{2})为第二组，
		Pattern p3 = Pattern.compile("(\\d{3,5})([a-z]{2})");
		String s3 = "123aa-34345bb-234cc-00";
		Matcher m3 = p3.matcher(s3);
		while (m3.find()) {
			p(m3.group(1));// 分组显示 第一组
		}
		m3.reset();
		while (m3.find()) {
			p(m3.group(2));// 分组显示 第二组
		}
		m3.reset();
		while (m3.find()) {
			p(m3.group());// 分组显示全部
		}
		System.out.println("------------抓取email----------------------");
		getEmails();
		System.out.println("------------计算某个文件目录下所有java文件的空行、注释行、有效代码------------");
		CodeCounter();
		//System.out.println(StringUtils.trim(" /** a").startsWith("/*"));
		//System.out.println("/**".startsWith("/*"));
		System.out.println("------------2.3-----------");
		// qulifiers
		Pattern p4 = Pattern.compile("(.{3,10}?)[0-9]");
		String s4 = "aaaa5bb";
		Matcher m4 = p4.matcher(s4);
		if(m4.find()) {
			p(m4.start() + "-" + m4.end());
		} else {
			p("not match!");
		}
		System.out.println("------------2.4-----------");
		// non-capturing groups (?=a)不表示分组，表示3个字符后以a结束
		Pattern p5 = Pattern.compile(".{3}(?=a)"); 
		String s5 = "444a66b";
		Matcher m5 = p5.matcher(s5); 
		while(m5.find()) { 
			p(m5.group()); 
		}
	}

	/**
	 * 计算某个文件目录下所有java文件的空行、注释行、有效代码
	 */
	static Map<String, Long> counts = new HashMap<String, Long>();

	public static void CodeCounter() {
		String src = "D:\\myeclipse_workspace\\myLearing\\src";
		//String src = "D:\\DateTools.java";
		File file = new File(src);
		parseFile(file);
		for (String key : counts.keySet()) {
			System.out.println(key + ":" + counts.get(key));
		}
	}

	private static void parseFile(File f) {
		if (f.canRead()) {
			if (f.isDirectory()) {
				File[] files = f.listFiles();
				for (File ff : files) {
					parseFile(ff);
				}
			} else {
				if (f.getName().matches(".*\\.java$")) {
					// System.out.println(f.getName());
					parse(f);
				}
			}
		}
	}

	/**
	 * 读取某个文件里的email
	 */
	public static void getEmails() {
		try {
			BufferedReader bf = new BufferedReader(new FileReader("D:\\email.txt"));
			String ss = "";
			while ((ss = bf.readLine()) != null) {
				// System.out.println(ss);
				p(parse(ss));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析字符串
	 * 
	 * @param line
	 * @return
	 */
	public static String parse(String line) {
		StringBuilder sb = new StringBuilder();
		String regex = "[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+";
		Pattern pe = Pattern.compile(regex);
		Matcher me = pe.matcher(line);
		while (me.find()) {// 不停的去寻找，只要没有me.reset()，就会继续寻找
			sb.append(me.group() + "\n");
		}
		return sb.toString();
	}

	public static Map<String, Long> parse(File f) {
		long normalLines = 0;// 正常行
		long commentLines = 0;// 注释行
		long whiteLines = 0;// 空白行
		try {
			BufferedReader bf = new BufferedReader(new FileReader(f));
			String line = "";
			int i = 0;
			boolean isEnd = false;
			while ((line = bf.readLine()) != null) {
				i++;
				if (line.matches("^[\\s&&[^\\n]]*$")) {
					whiteLines++;
				} else if (StringUtils.trim(line).startsWith("//")) {
					commentLines++;
				} else if (StringUtils.trim(line).contains("/*")) {
					commentLines++;
					isEnd = true;
				} else if (isEnd) {
					commentLines++;
					if (line.contains("*/")) {
						isEnd = false;
					}
				} else {
					normalLines++;
				}
			}
			System.out.println(f.getName() + "文件共：" + i + "行");
			if (counts.containsKey("normalLines")) {
				counts.put("normalLines", counts.get("normalLines")
						+ normalLines);
			} else {
				counts.put("normalLines", normalLines);
			}
			if (counts.containsKey("commentLines")) {
				counts.put("commentLines", counts.get("commentLines")
						+ commentLines);
			} else {
				counts.put("commentLines", commentLines);
			}
			if (counts.containsKey("whiteLines")) {
				counts.put("whiteLines", counts.get("whiteLines") + whiteLines);
			} else {
				counts.put("whiteLines", whiteLines);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return counts;
	}
}
