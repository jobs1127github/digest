package com.tentinet.app.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tentinet.app.bean.dto.DigestGoldDto;
import com.tentinet.app.excel.model.GoldDownLoadView;
import com.tentinet.app.excel.utils.ExcelUtils;
import com.tentinet.app.service.ReportFormsService;
import com.tentinet.app.util.Page;
import com.tentinet.weixin.util.BASE64Utils;

/**
 * 报表管理
 * 
 */
@Controller
public class ReportFormsAction extends BaseAction {
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ReportFormsAction.class);

	@Autowired
	private ReportFormsService reportFormsService;

	/**
	 * 查询金币发放列表信息
	 */
	@RequestMapping(value = "/Report/getGoldSendInfo.do")
	public void getGoldSendInfo(HttpServletRequest request,
			HttpServletResponse response, String send_type, Page page) {
		page.setAutoCount(true);// 直动设置分页
		HttpSession session = request.getSession();
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(send_type)
				&& !StringUtils.equals("0", send_type)) {
			params.put("send_type", send_type);
		}

		Integer Count = reportFormsService.queryGoldSendCount(params);
		page.setTotalCount(Count);
		List<DigestGoldDto> listUser = reportFormsService.queryGoldSendInfos(params);
		List<DigestGoldDto> newList = getPageList(listUser, page);
		if (newList != null) {
			for (int i = 0; i < newList.size(); i++) {
				DigestGoldDto dto = newList.get(i);
				String userName = getUserName(dto.getUsername());
				dto.setUsername(userName);
			}
		}
		JSONObject oj = new JSONObject();
		oj.put("data", newList);
		oj.put("total", Count);
		writeResponseByJson(request, response, oj);

	}
	/**
	 * 下载文件
	 */
	@RequestMapping(value = "/Report/downLoad.do")
	public void downLoad(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("send_type", 1);
		List<DigestGoldDto> listUser = reportFormsService.queryGoldSendInfos(params);
		List<Object> list = new ArrayList<Object>();
		for(DigestGoldDto d:listUser) {
			GoldDownLoadView g = new GoldDownLoadView( getUserName(d.getUsername()),getSendName(d.getSend_type()),d.getSend_time(),d.getGold_count()+"");
			list.add(g);
		}
		String filePath = "D:\\gold-report"+UUID.randomUUID().toString()+".xls";
		String[] title = new String[] { "领取人", "金币类型", "领取时间", "金币数量" };
		ExcelUtils.dataToExcel(filePath,"金币报表", title, list);
		/**
		 * ExcelUtils.dataToExcel调用后，就已经把数据，保存到了指定的路径了，但是该路径是服务器下的路径，
		 * 要实现下载到客户端，则需要跳转页面额外处理，这里通过请求文件下载的servlet来实现请求。
		 * 上传文件，下载文件，道理都是一样的，都是把目标文件读取到内存，然后通过输出管道写入到指定的路径。
		 */
		JSONObject oj = new JSONObject();
		oj.put("data", "/servlet/FileDownServlet?url="+filePath);
		writeResponseByJson(request, response, oj);
	}
	private String getUserName(String str) {
		return BASE64Utils.getFromBASE64(str);
	}
	// 金币发放类型
	private String getSendName(String id) {
		String Name = "系统发放";
		if (id.equals("3")) {
			Name = "";
		} else if (id.equals("2")) {
			Name = "分享奖励";
		} else if (id.equals("4")) {
			Name = "流量奖励";
		} else if (id.equals("1")) {
			Name = "关注奖励";
		}
		return Name;
	}
}
