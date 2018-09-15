package com.tentinet.weixin.service;

import com.tentinet.weixin.util.wechat.InMessage;
import com.tentinet.weixin.util.wechat.OutMessage;

public interface PrecessService {
	public OutMessage doJob(InMessage msg);
}
