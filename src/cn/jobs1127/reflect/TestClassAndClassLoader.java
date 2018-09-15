/**
 * 
 */
package cn.jobs1127.reflect;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * @author jobs1127
 * reflect反射
 * 
 * jvm里有多个类加载器，每个类加载器可以负责加载特定位置的类，
 * 例如，我们平时用的jdk中的类都位于rt.jar中，bootstrap（引导程序）类加载器负责加载jre/lib/rt.jar中的类。
 * extclassloader负责加载jar/lib/ext/*.jar中的类，
 * appclassloader负责classpath指定的目录或jar中的类。
 * 除了bootstrap之外，其他的类加载器本身也都是java类，它们的父类是ClassLoader。
 */
public class TestClassAndClassLoader {
	private static Properties properties = null;
	static {
		//src/resorces 里面的文件都是编译到classes类路径下
		String configFileRelativePath = "/properties/classAndClassLoader.properties";
		InputStream in = TestClassAndClassLoader.class.getResourceAsStream(configFileRelativePath);
		properties = new Properties();
		try {
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * run Configurations Arguments VM-argumengts参数填写： -verbose:class 显示 详细加载过程
	 * @param args
	 * 
	 * ClassLoader类加载器，运行时会把相关的.class字节码对象加载load到内存，动态加载.class字节码对象
	 * bootstrap class loader 是c等底层语言写的负责加载核心的类加载器如：extesion calss loader,
	 * application calss loader,other calss loader
	 */
	public static void main(String[] args) {
		new A();
		System.out.println("---------动态加载------------");
		new B();
		System.out.println("---------new俩次C对象看看C输出多少次  1次---------");
		new C();
		new C();
		System.out.println("---------new俩次D对象看看D输出多少次 2次---------");
		new D();
		new D();
		System.out.println("------------1-------------");
		System.out.println(String.class.getClassLoader());
		System.out.println(TestClassAndClassLoader.class.getClassLoader().getClass().getName());
		System.out.println(TestClassAndClassLoader.class.getClassLoader().getClass().getName());
		System.out.println("-----------2-------------");
		System.out.println(TestClassAndClassLoader.class.getName());
		System.out.println(TestClassAndClassLoader.class.getAnnotations());
		System.out.println("-----------3-------------");
		/**
		 * 每个classLoader对象里都有一个parent对象的引用，classLoader加载字节码对象时，
		 * 先问parent对象是否已经加载了，如果加载了，他就不加载了，确保安全性
		 */
		ClassLoader classLoader  = TestClassAndClassLoader.class.getClassLoader();
		while(classLoader != null) {// 为null则是bootstrap class loader 引导类加载器
			System.out.println("I:"+classLoader.getClass().getName());
			classLoader = classLoader.getParent();
		}
		System.out.println("-----------4-------------");
		String clazzName = properties.get("class").toString();
		System.out.println("从配置文件中获得类名:"+clazzName);
		try {
			Package p = null;
			/**
			 * 字节码对象，可以获取他的包、类、方法、属性等对象
			 */
			p = TestClassAndClassLoader.class.getPackage();
			System.out.println("包名："+p.getName());
			Class<?> c = Class.forName(p.getName()+"."+clazzName);
			Object obj = c.newInstance();
			Method[] methods = c.getMethods();
			for(Method m:methods) {
				System.out.println("方法名："+m.getName());
				if(m.getName().equals("mymehtod")){
					m.invoke(obj);
				}
			}
			System.out.println("-------5-------------");
			Field[] fields = c.getFields();
			for(Field f:fields) {
				System.out.println("属性名:"+f);
			}
			Method mm = c.getMethod("mymehtod");
			mm.invoke(obj);
			System.out.println("-------6-------------");
			Class<?> clazz = Class.forName(p.getName()+"."+"F");
			Class<?> t = clazz.asSubclass(c);
			//子类new一个实体对象，会优先调用父类的构造方法
			Object subobj = t.newInstance();
			Method subm = clazz.getMethod("mymehtod");
			subm.invoke(subobj);
			System.out.println("-------7-------------");
			Class<?> e = Class.forName(p.getName()+"."+clazzName);
			Object eobj = e.newInstance();
			System.out.println("e.name="+e.getName());
			Method paramm = e.getMethod("myParameterTypes",int.class,int.class);
			Class<?> reytp = paramm.getReturnType();
			System.out.println("返回的类型："+reytp.getName());
			paramm.invoke(eobj,1,3);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
}
class A {
	public A() {System.out.println("A");}
}
class B {
	public B() {System.out.println("B");}
}
class C {
	static {//静态代码块只在加载时执行一次
		System.out.println("C");
	}
}
class D {
	public D() {
		System.out.println("D构造方法执行");
	}
	{//动态代码块，是在构造方法前面执行，相当于是追加在构造方法前面的代码
		System.out.println("D");
	}
}
class E {
	public String s;
	public int i;
	public E() {
		System.out.println("new 了一个E对象");
	}
	public int mymehtod() {
		++i;
		System.out.println("mymehtod is invoked "+i);
		return i;
	} 
	public String myParameterTypes(int a,int b) {
		this.i = a+b;
		System.out.println("a+b="+this.i);
		return this.i+"";
	}
}
class F extends E {
	@Override
	public int mymehtod() {
		++i;
		System.out.println("@Override mymehtod is invoked "+i);
		return i;
	}
}