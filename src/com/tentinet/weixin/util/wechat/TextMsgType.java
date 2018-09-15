package com.tentinet.weixin.util.wechat;

/**
 * 事件消息类型
 */
public class TextMsgType 
{

    /** 关注公众帐号后提示的正则表达式. */
    public static final String RE_CMD_WELCOME = "^hello2bizuser$";
    
    /** Hi命令的正则表达式. */
    public static final String RE_CMD_HI = "^(\\s*)(hi|hello|你好|您好)(\\s*)$";
}
