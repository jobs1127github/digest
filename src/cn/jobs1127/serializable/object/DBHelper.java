package cn.jobs1127.serializable.object;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
	private static Connection conn;  //连接
	private PreparedStatement pres; //PreparedStatement对象
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver"); //加载驱动
			System.out.println("数据库加载成功!!!");
			String url="jdbc:mysql://localhost:3306/digest_wx";
			String user="root";
			String password="root";
			conn=DriverManager.getConnection(url,user,password); //建立连接
			System.out.println("数据库连接成功!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 向数据库中的表：testobj 中插入多个Person对象
	 * params:
	 * persons:Person对象list
	 * 
	 * BLOB (binary large object)，二进制大对象，是一个可以存储二进制文件的容器。
	 * MySQL中，BLOB是个类型系列，包括：TinyBlob、Blob、MediumBlob、LongBlob，
	 * 这几个类型之间的唯一区别是在存储文件的最大大小上不同。  
	 * MySQL的四种BLOB类型  
	 * 类型 大小(单位：字节)  
	 * TinyBlob 最大 255  
	 * Blob 最大 65K  
	 * MediumBlob 最大 16M  
	 * LongBlob 最大 4G  
	 * 
	 * obj是objtest表中的字段。类型为MediumBlob
	 */
	public void savePerson(List<Person> persons){
		String sql=" insert into objtest(obj) values(?)";
		try {
			pres=conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
			for(int i=0;i<persons.size();i++){
				pres.setObject(1, persons.get(i));
				pres.addBatch(); //实现批量插入
			}
			pres.executeBatch(); //批量插入到数据库中
			if(pres!=null) {
				pres.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从数据库中读出存入的对象
	 * return:
	 * list:Person对象列表
	 */
	public List<Person> getPerson(){
		List<Person> list=new ArrayList<Person>();
		String sql=" select obj from objtest";
		try {
			pres=conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			ResultSet res = pres.executeQuery();
			while(res.next()){
				Blob inBlob=res.getBlob(1); //获取blob对象
				InputStream is=inBlob.getBinaryStream(); //获取二进制流对象
				BufferedInputStream bis=new BufferedInputStream(is);//带缓冲区的流对象
				
				//该缓冲流里每次只保存一个对象长度的byte
				byte[] buff=new byte[(int) inBlob.length()];
				while(-1!=(bis.read(buff, 0, buff.length))){ //一次性全部读到buff中
					ObjectInputStream in=new ObjectInputStream(new ByteArrayInputStream(buff));
					Person p=(Person)in.readObject();//读出对象
					list.add(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}