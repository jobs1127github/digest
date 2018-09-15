package com.tentinet.weixin.service;

import com.tentinet.weixin.util.wechat.InMessage;
import com.tentinet.weixin.util.wechat.OutMessage;

/**
 * 事件处理接口
 */
public interface EventService {
	/**
	 * 微信事件处理接口
	 * @param msg
	 * @return
	 * msg是用户的请求，用户的请求是以xml格式的文件去请求微信服务器的，我们通过获取该请求的流文件，
	 * 把流文件转换成字符串，发现该字符串的格式是以xml格式组成的，为了在java程序中方面处理，把该xml字符串，
	 * 通过XStream对象序列化获取该xml格式对应的java类对象。msg就是这个用户请求对象。
	 * 
	 * OutMessage对象是，通过处理了用户的请求，微信服务器对用户进行回复的（response响应）对象，
	 * OutMessage只是在java程序为了方便处理抽象的类对象，最终响应时，还得通过XStream对象把其转换成xml格式文件，
	 * 并写入到response里。
	 */
   public OutMessage doPrecess(InMessage msg);
}
