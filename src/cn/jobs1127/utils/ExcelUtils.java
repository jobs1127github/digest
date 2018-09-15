package cn.jobs1127.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tentinet.app.excel.model.Person;

/**
 * excel操作工具类
 * @author jobs1127
 *
 */
public class ExcelUtils {
	private static final Logger logger = Logger.getLogger(ExcelUtils.class);
	public static final int excelSheetSize = 65535;
	protected HSSFWorkbook wb;
	private HSSFCellStyle cellStyleTitle;
	private HSSFCellStyle cellStyleNormal;
	/**
	 * 初始化，设置首行的字体样式和表格字体样式 
	 */
	public ExcelUtils() { 
		//首行样式
		wb = new HSSFWorkbook();
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("Verdana");
		font.setFontHeightInPoints((short) 9);
		cellStyleTitle = wb.createCellStyle();
		cellStyleTitle.setFont(font);
		//表格里字体样式
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontName("SimSun");
		font.setFontHeightInPoints((short) 9);
		cellStyleNormal = wb.createCellStyle();
		cellStyleNormal.setFont(font);
	}
	/**
	 * 把数据内容写入并生成Excel .xls
	 * @param list
	 * @param firstRowValue
	 * @return
	 */
	public String dataToExcel(List<Object[]> list, String[] firstRowValue) {
		int listSize = list.size();
		//Excel2003版最大行数是65536行 表单数
		int sheetCount = (listSize - 1) / ExcelUtils.excelSheetSize + 1;
		int startRow = 0, endRow = 0;
		boolean aok = false;
		boolean aI = false;
		for (int i = 1; i <= sheetCount; i++) {
			HSSFSheet sheet = wb.createSheet("Sheet" + i);
			HSSFRow row = sheet.createRow(0);
			for (int j = 0; j < firstRowValue.length; j++) {
				HSSFCell cell = row.createCell(j);
				String a = firstRowValue[j];
				try {
					Float.parseFloat(a);
				} catch (NumberFormatException e) {
					aok = true;
				}
				if (aok) {
					cell.setCellValue(new HSSFRichTextString(a));
					aok = false;
					cell.setCellStyle(cellStyleTitle);
				} else {
					try {
						Integer.parseInt(a);
					} catch (NumberFormatException e) {
						aI = true;
					}
					if (aI) {
						Float numv = Float.parseFloat(a);
						cell.setCellValue(numv);
						HSSFCellStyle cellStyle = wb.createCellStyle();
						cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
						cellStyle.setLocked(false);
						cell.setCellStyle(cellStyle);
						aI = false;
					} else {
						Float numv = Float.parseFloat(a);
						cell.setCellValue(numv);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					}
				}
			}
			startRow = (i - 1) * ExcelUtils.excelSheetSize;
			endRow = Math.min(startRow + ExcelUtils.excelSheetSize - 1,listSize - 1);
			for (int j = startRow; j <= endRow; j++) {
				Object[] obj = list.get(j);
				row = sheet.createRow(j % ExcelUtils.excelSheetSize + 1);
				for (int k = 0; k < obj.length; k++) {
					HSSFCell cell = row.createCell(k);
					String a = obj[k].toString();
					try {
						Float.parseFloat(a);
					} catch (NumberFormatException e) {
						aok = true;
					}
					if (aok) {
						cell.setCellValue(new HSSFRichTextString(a));
						aok = false;
						cell.setCellStyle(cellStyleNormal);
					} else if (Float.parseFloat(a) > 2147483647) {
						cell.setCellValue(new HSSFRichTextString(a));
						aok = false;
						cell.setCellStyle(cellStyleNormal);
					} else {
						try {
							Integer.parseInt(a);
						} catch (NumberFormatException e) {
							aI = true;
						}
						if (aI) {
							Float numv = Float.parseFloat(a);
							cell.setCellValue(numv);
							HSSFCellStyle cellStyle = wb.createCellStyle();
							cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
							cellStyle.setLocked(false);
							cell.setCellStyle(cellStyle);
							aI = false;
						} else {
							Float numv = Float.parseFloat(a);
							cell.setCellValue(numv);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}
					}
				}
			}
		}
		String realname = createFile();
		try {
			FileOutputStream fout = new FileOutputStream(realname);
			//文件输出流，参数如果是：String，指定对应的路径即可
			wb.write(fout);
			fout.flush();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return realname;
	}
	/**
	 * 创建文件，把生成的Excle写入到某个路径
	 * @return
	 */
	public String createFile() {
		// path表示你所创建文件的路径
		String path = "d:/SnowServer/down_xls/";
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		// fileName表示你创建的文件名；为txt类型；
		String fileName = System.currentTimeMillis() + ".xls";
		// System.out.println(fileName);
		File file = new File(f, fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path+fileName;
	}
	
	/**
	 * 数据生成excel .xls
	 * @param filePath 保存路径
	 * @param sheetName sheet标题
	 * @param title 列标题
	 * @param dataSet 数据
	 */
	@SuppressWarnings("deprecation")
	public static void dataToExcel(String filePath, String sheetName,String[] title, List<Object> dataSet) {
		//logger.info("---------------数据生成excel文件开始-------------");
		// 创建一个webbook
		HSSFWorkbook wb = new HSSFWorkbook();
		// 添加一个sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		// sheet中添加表头第0行
		HSSFRow row = sheet.createRow((int) 0);
		// 创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = null;
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}

		// 填充数据
		Field[] fields = null;
		int i = 1;
		try {
			for (Object obj : dataSet) {
				fields = obj.getClass().getDeclaredFields();
				int j = 0;
				row = sheet.createRow((int) i);
				for (Field filed : fields) {
					filed.setAccessible(true);
					Object val = filed.get(obj);
					val = (null == val ? "" : val);
					row.createCell((short) j).setCellValue(val.toString());
					j++;
				}
				i++;
			}
			// 保存excel文件到指定路径
			FileOutputStream fout = new FileOutputStream(filePath);
			wb.write(fout);
			fout.close();
		} catch (IllegalArgumentException ex) {
			logger.error("生成excel文件错误", ex);
		} catch (IllegalAccessException ex) {
			logger.error("生成excel文件错误", ex);
		} catch (Exception ex) {
			logger.error("生成excel文件错误", ex);
		}
		//logger.info("---------------数据生成excel文件结束--------------");
	}
	/**
	 * 从服务器下载到本地 
	 * 通过servlet处理的下载downLoad @RequestMapping(value = "/Report/downLoad.do")
	 * @param realname
	 * @throws IOException
	 */
	public void downloadExcel(String realname) throws IOException {
//		File f = new File(realname);
//		String fileName = f.getName(); 
//		FacesContext ctx = FacesContext.getCurrentInstance();
//		String contentType = "application/x-download";
//		HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
//		response.setContentType(contentType);
//		response.setHeader("Content-Disposition", "attachment;filename="
//				+ new String(fileName.getBytes("gb2312"), "ISO8859-1"));
//		//HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		ServletOutputStream out = response.getOutputStream();
//		byte[] bytes = new byte[0xffff];
//		InputStream is = new FileInputStream(new File(realname));
//		int b = 0;
//		while ((b = is.read(bytes, 0, 0xffff)) > 0) {
//			out.write(bytes, 0, b);
//		}
//		is.close();
//		out.flush();
//		ctx.responseComplete();
//		f.delete();
	}
	
	/**
	 * 导出cvs的格式的Excel .csv
	 * @param list
	 * @param firstRowValue
	 * @return
	 */
	public String exportCvs(List<Object[]> list, String[] firstRowValue){
		List exportData = new ArrayList<Map>();  
        Map row = new LinkedHashMap<String, String>();
		for(int i=0;i<list.size();i++){
			Object[] obj = list.get(i);
			for(int j=0;j<obj.length;j++){
				row.put(j+1, obj[j]);
			}
			 exportData.add(row);
			 row = new LinkedHashMap<String, String>(); 
		}
		List propertyNames = new ArrayList();  
	    LinkedHashMap map = new LinkedHashMap();
	    for(int k=0;k<firstRowValue.length;k++){
	    	map.put(k+1,firstRowValue[k]);
	    }
	    String realname = "d:/SnowServer/down_xls/";
	    String titlename = System.currentTimeMillis()+"";
	    createCSVFile(exportData, map, realname, titlename);
		return realname+titlename+".csv";
	}
	
    public static File createCSVFile(List exportData,LinkedHashMap rowMapper,String outPutPath, String filename) {  
        File csvFile = null;  
        BufferedWriter csvFileOutputStream = null;  
        try {  
            csvFile = new File(outPutPath + filename + ".csv");  
            File parent = csvFile.getParentFile();  
            if (parent != null && !parent.exists()) {  
                parent.mkdirs();  
            }  
            csvFile.createNewFile();   
            // GB2312使正确读取分隔符","  
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(  
                    new FileOutputStream(csvFile), "GBK"), 1024);  
            // 写入文件头部  
            for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator.hasNext();) {  
                java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();  
                csvFileOutputStream.write("\""+ propertyEntry.getValue().toString() + "\"");  
                if (propertyIterator.hasNext()) {  
                    csvFileOutputStream.write(",");  
                }  
            }  
            csvFileOutputStream.newLine();  
            // 写入文件内容  
            for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {    
               // Object row = (Object) iterator.next();    
                LinkedHashMap row = (LinkedHashMap) iterator.next();  
                for (Iterator propertyIterator = row.entrySet().iterator(); propertyIterator.hasNext();) {    
                    java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();    
                    csvFileOutputStream.write("\""    
                            +  propertyEntry.getValue().toString() + "\"");    
                   if (propertyIterator.hasNext()) {    
                       csvFileOutputStream.write(",");    
                    }    
               }    
	           if (iterator.hasNext()) {    
	              csvFileOutputStream.newLine();    
	           }    
           }    
           csvFileOutputStream.flush();    
        } catch (Exception e) {    
           e.printStackTrace();    
        } finally {    
           try {    
               csvFileOutputStream.close();    
            } catch (IOException e) {    
               e.printStackTrace();  
           }    
       }    
       return csvFile;  
    }
	    
    
    
    
    
    
	public static void main(String[] args) {
		Person person = new Person(1, "jack", '1', 1);
		Person person1 = new Person(2, "jack2", '2', 12);
		Person person2 = new Person(3, "jack3", '1', 123);
		Person person3 = new Person(4, "jack4", '2', 1234);
		String[] title = new String[] { "用户", "名字", "性别", "手机号码" };
		List<Object> list = new ArrayList<Object>();
		list.add(person);
		list.add(person1);
		list.add(person2);
		list.add(person3);
		List<Object[]> listExport = new ArrayList<Object[]>();
		for(Object o:list) {
			Object[] obj = new Object[title.length];
			obj[0] = ((Person)o).getName();
			obj[1] = ((Person)o).getName();
			obj[2] = ((Person)o).getSex();
			obj[3] = ((Person)o).getPhone();
			listExport.add(obj);
		}
		//ExcelUtils.dataToExcel("D:\\test.xls", "person表", title, list);
		ExcelUtils eu = new ExcelUtils();
		String realpath = eu.exportCvs(listExport, title);
	}
}