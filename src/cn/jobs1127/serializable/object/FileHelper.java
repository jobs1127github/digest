package cn.jobs1127.serializable.object;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/***
 * 保存对象到文件并恢复
 * 
 * @author Jobs1127
 */
public class FileHelper {
	private String fileName;

	public FileHelper() {

	}

	public FileHelper(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * 将person对象保存到文件中 params: p:person类对象
	 */
	public void saveObjToFile(Person p) {
		try {
			// 写对象流的对象
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
			oos.writeObject(p); // 将Person对象p写入到oos中
			oos.close(); // 关闭文件流
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从文件中读出对象，并且返回Person对象
	 */
	public Person getObjFromFile() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
			Person person = (Person) ois.readObject(); // 读出对象
			return person; // 返回对象
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
