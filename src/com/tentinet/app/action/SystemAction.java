package com.tentinet.app.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tentinet.app.bean.DataDictionaryVo;
import com.tentinet.app.bean.MarkVo;
import com.tentinet.app.bean.QianDao;
import com.tentinet.app.service.SystemService;
import com.tentinet.app.util.Page;

@Controller
public class SystemAction extends BaseAction {

	@Autowired
	private SystemService systemService;

	@RequestMapping(value = "/system/loadinitDatas.do")
	public void loadinitDatas(HttpServletRequest request,
			HttpServletResponse response, String param) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataType", param);
		List<DataDictionaryVo> listDatas = systemService.loadinitDatas(params);
		JSONObject oj = new JSONObject();
		oj.put("data", listDatas);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 查询所有的数据字典中的值
	 * 
	 * @param request
	 * @param response
	 * @param datakey
	 * @param dataItem 真实值
	 * @param dataValue 显示值
	 */
	@RequestMapping(value = "/system/queryDataDictionaryInfos.do")
	public void queryDataDictionaryInfos(HttpServletRequest request,
			HttpServletResponse response, String datakey, String dataItem,
			String dataValue, String status, Page page) {
		page.setAutoCount(true);
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(datakey)) {
			params.put("datakey", "%" + datakey + "%");
		}

		if (StringUtils.isNotEmpty(dataItem)) {
			params.put("dataItem", "%" + dataItem + "%");
		}

		if (StringUtils.isNotEmpty(dataValue)) {
			params.put("dataValue", "%" + dataValue + "%");
		}

		if (StringUtils.isNotEmpty(status) && !StringUtils.equals("0", status)) {
			params.put("status", status);
		}

		Integer count = systemService.queryDataDictionaryCount(params);
		List<DataDictionaryVo> list = systemService
				.queryDataDictionaryInfos(params);
		List<DataDictionaryVo> newlist = getPageList(list, page);
		page.setTotalCount(count);
		JSONObject oj = new JSONObject();
		oj.put("data", newlist);
		oj.put("total", count);
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/system/queryBiaoQianInfos.do")
	public void queryBiaoQianInfos(HttpServletRequest request,
			HttpServletResponse response, String mark_name, Page page) {
		page.setAutoCount(true);
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(mark_name)) {
			params.put("mark", "%" + mark_name + "%");
		}
		Integer count = systemService.queryBiaoQianCount(params);
		List<DataDictionaryVo> list = systemService.loadinitMark(params);
		List<DataDictionaryVo> newlist = getPageList(list, page);
		page.setTotalCount(count);
		JSONObject oj = new JSONObject();
		oj.put("data", newlist);
		oj.put("total", count);
		writeResponseByJson(request, response, oj);
		/**
		 * 如果没配置oscache全局缓存，则下面的语句，每次请求时都会打印，如果配置了全局缓存，
		 * 请求该路径时，会从缓存里获取，不会调用该queryBiaoQianInfos()方法。
		 */
		System.out.println("进入system路径，获取标签……");
	}

	@RequestMapping(value = "/system/qiandao_edit.do")
	public void qiandao_edit(HttpServletRequest request,
			HttpServletResponse response, String open_id, Page page) {
		if (open_id != null && !"".equals(open_id.trim())) {
			QianDao qd = systemService.getQianDaoByOpenId(open_id);
			if (qd != null) {
				systemService.updateQiandao(qd);
			}
		}
		JSONObject oj = new JSONObject();
		oj.put("data", "sucess");
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/system/qiandaolist.do")
	public void qiandaolist(HttpServletRequest request,
			HttpServletResponse response, Page page, String qiandao14,
			String openid) {
		page.setAutoCount(true);
		if (qiandao14 != null && !"".equals(qiandao14.trim())) {
			Integer count = systemService.getCountQiandao(qiandao14);
			List<QianDao> list = systemService.getQiandaolist(qiandao14);
			List<QianDao> newlist = getPageList(list, page);
			page.setTotalCount(count);
			JSONObject oj = new JSONObject();
			oj.put("data", newlist);
			oj.put("total", count);
			writeResponseByJson(request, response, oj);
		} else {
			if (openid != null && !"".equals(openid.trim())) {
				Integer count = systemService
						.getCountQiandao(" and 1=1 and t.openid like '%"
								+ openid + "%'");
				List<QianDao> list = systemService
						.getQiandaolist(" and 1=1 and t.openid like '%"
								+ openid + "%'");
				List<QianDao> newlist = getPageList(list, page);
				page.setTotalCount(count);
				JSONObject oj = new JSONObject();
				oj.put("data", newlist);
				oj.put("total", count);
				writeResponseByJson(request, response, oj);
			} else {
				Integer count = systemService.getCountQiandao(" and 1=1 ");
				List<QianDao> list = systemService.getQiandaolist(" and 1=1 ");
				List<QianDao> newlist = getPageList(list, page);
				page.setTotalCount(count);
				JSONObject oj = new JSONObject();
				oj.put("data", newlist);
				oj.put("total", count);
				writeResponseByJson(request, response, oj);
			}
		}
	}

	/**
	 * 查询单个字典的内容
	 * 
	 * @param request
	 * @param response
	 * @param datakey
	 */
	@RequestMapping(value = "/system/showDataDictionaryInfo.do")
	public void showDataDictionaryInfo(HttpServletRequest request,
			HttpServletResponse response, String datakey) {
		JSONObject oj = new JSONObject();
		DataDictionaryVo data = systemService
				.getDataDictionaryVoInfosById(datakey);
		oj.put("data", data);
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/system/showBiaoQianInfo.do")
	public void showBiaoQianInfo(HttpServletRequest request,
			HttpServletResponse response, String datakey) {
		JSONObject oj = new JSONObject();
		MarkVo data = systemService.getMarkVoInfosById(datakey);
		oj.put("data", data);
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/system/updateDataDictionaryInfo.do")
	public void updateDataDictionaryInfo(HttpServletRequest request,
			HttpServletResponse response, DataDictionaryVo data) {
		boolean isResult = systemService.updateDataDictionary(data);
		JSONObject oj = new JSONObject();
		if (isResult) {
			oj.put("data", 1);
		} else {
			oj.put("data", 2);
		}
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/system/updateMarkInfo.do")
	public void updateMarkInfo(HttpServletRequest request,
			HttpServletResponse response, String mark_code, String mark_name) {
		boolean isResult = systemService.updateMarkVo(mark_code, mark_name);
		JSONObject oj = new JSONObject();
		if (isResult) {
			oj.put("data", 1);
		} else {
			oj.put("data", 2);
		}
		writeResponseByJson(request, response, oj);
	}
	
	@RequestMapping(value = "/system/dataCount.do")
	public void dataCount(HttpServletRequest request,
			HttpServletResponse response,String mark_code) {
		MarkVo m = systemService.getMarkVoInfosById(mark_code);
		int c = -1;
		try {
			c = Integer.parseInt(m.getCreated_by());
		} catch (NumberFormatException e) {
			m.setCreated_by("0");
			c = 0;
		}
		if(c == 0) {
			m.setCreated_by("1");
		} else if(c > 0) {
			m.setCreated_by((c+1)+"");
		}
		systemService.updateMark(m);
		JSONObject oj = new JSONObject();
		writeResponseByJson(request, response, oj);
	}
	/**
	 * 保存标签时，应用了Velocity页面静态化，即保存一个标签后，会立刻生成一个标签的详情页（静态页面）
	 * @param request
	 * @param response
	 * @param mark_name
	 */
	@RequestMapping(value = "/system/saveMarkVoInfo.do")
	public void saveMarkVoInfo(HttpServletRequest request,
			HttpServletResponse response, String mark_name) {
		MarkVo m = new MarkVo();
		m.setCreated_by(new Date() + "");
		m.setCreated_time(new Date() + "");
		m.setMark_code(UUID.randomUUID().toString());
		m.setMark_name(mark_name);
		m.setUpdated_by(new Date() + "");
		m.setUpdated_time(new Date() + "");
		boolean isResult = systemService.savaMarkVo(m);
		JSONObject oj = new JSONObject();
		if (isResult) {
			oj.put("data", 1);
		} else {
			oj.put("data", 2);
		}
		writeResponseByJson(request, response, oj);
		/***
		 * 页面静态化的相关使用,这个地方只是为了方便阅读，故把初始化velocity的代码写在这里，其实velocity初始化，在这个应用中只
		 * 需要初始化一次即可，可以放在filter的init()方法中。
		 */
		try {
			/**
			 * 编程的方式初始化Velocity，这里的路径是绝对路径，其实是不方便项目移植的，比如在Linux系统下就不行了，
			 * 在实际开发中，最好是使用相对路径:
			 * 1、HttpServletRequest对象request.getSession().getServletContext().getRealPath("/");
			 * 2、在filter中调用，filterConfig.getServletContext().getRealPath("/");
			 */
			Properties pop = new Properties();
			String logPath = request.getSession().getServletContext().getRealPath("log/velocity.log");
			pop.put("runtime.log", logPath);
			String loaderPath = request.getSession().getServletContext().getRealPath("vm");
			pop.put("file.resource.loader.path", loaderPath);
			pop.put("input.encoding", "UTF-8");
			pop.put("output.encoding", "UTF-8");
			pop.put("directive.foreach.counter.initial.value", "0");
			pop.put("directive.foreach.counter.name", "index");
			System.out.println("logPath="+logPath);
			System.out.println("loaderPath="+loaderPath);
			Velocity.init(pop);
			/**
			 * 通过velocityContext可以给模板文件传递参数，可以在模板文件中使用。
			 * 这个对象，其实也可以把他优化成一个单例对象来使用。
			 */
			VelocityContext context = new VelocityContext();
			/**
			 * 把产品对象存放到VelocityContext上下文中
			 */
			context.put("mark", m);
			/**
			 * 获取模板文件，后缀名可以任意取名。模板文件存放到vm文件下。
			 */
			Template template = Velocity.getTemplate("mark_detail_template.html");
			
			String file = request.getSession().getServletContext().getRealPath("/html/mark_detail/"+m.getMark_code()+".html");
			File saveFile = new File(file);
			if(!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(saveFile);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos,"UTF-8");
			//频繁操作io,提供缓冲
			BufferedWriter writer = new BufferedWriter(outputStreamWriter);
			//StringWriter writer = new StringWriter();
			template.merge(context, writer);
			//System.out.println("writer="+writer.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/system/saveDataDictionaryInfo.do")
	public void saveDataDictionaryInfo(HttpServletRequest request,
			HttpServletResponse response, DataDictionaryVo data) {
		data.setDatakey(UUID.randomUUID().toString());
		boolean isResult = systemService.saveDataDictionary(data);
		JSONObject oj = new JSONObject();
		if (isResult) {
			oj.put("data", 1);
		} else {
			oj.put("data", 2);
		}
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/system/delMarkVoInfo.do")
	public void delMarkVoInfo(HttpServletRequest request,
			HttpServletResponse response, String datakey) {
		MarkVo data = systemService.getMarkVoInfosById(datakey);
		boolean isResult = systemService.deleteMarkVo(data);
		JSONObject oj = new JSONObject();
		if (isResult) {
			oj.put("data", 1);
		} else {
			oj.put("data", 2);
		}
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/system/delDataDictionaryInfo.do")
	public void delDataDictionaryInfo(HttpServletRequest request,
			HttpServletResponse response, String datakey) {
		DataDictionaryVo data = systemService
				.getDataDictionaryVoInfosById(datakey);
		data.setStatus("N");
		boolean isResult = systemService.updateDataDictionary(data);
		JSONObject oj = new JSONObject();
		if (isResult) {
			oj.put("data", 1);
		} else {
			oj.put("data", 2);
		}
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/system/loadinitMark.do")
	public void loadinitMark(HttpServletRequest request,
			HttpServletResponse response, String mark) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mark", mark);
		List<DataDictionaryVo> listDatas = systemService.loadinitMark(params);
		JSONObject oj = new JSONObject();
		oj.put("data", listDatas);
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/system/loadinitExpert.do")
	public void loadinitExpert(HttpServletRequest request,
			HttpServletResponse response, String openid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("openid", openid);
		List<DataDictionaryVo> listDatas = systemService.loadinitExpert(params);
		JSONObject oj = new JSONObject();
		oj.put("data", listDatas);
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/system/loadinitGroup.do")
	public void loadinitGroup(HttpServletRequest request,
			HttpServletResponse response, String group_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(group_id)) {
			params.put("group_id", group_id);
		}

		List<DataDictionaryVo> listDatas = systemService.loadinitGroup(params);
		JSONObject oj = new JSONObject();
		oj.put("data", listDatas);
		writeResponseByJson(request, response, oj);
	}
}
