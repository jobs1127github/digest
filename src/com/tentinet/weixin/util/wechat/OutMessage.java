package com.tentinet.weixin.util.wechat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 * 回复消息实体类，回复消息给OpenID,发消息给OpenID
 * OpenID即用户在微信公众平台上的唯一标识
 * OutMessage的属性，参考微信的开发文档。
 * 
 * 微信服务器（FromUserName）响应给用户（ToUserName）的数据封装在该对象里。
 * @author jobs1127
 *
 */
public class OutMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 开发者微信号 ，响应消息给谁*/
	private String ToUserName;

	/** 消息从哪里来 */
	private String FromUserName;

	/** 消息创建时间 */
	private Long CreateTime;

	/** text,image,voice,video,music,news */
	private String MsgType = "text";

	/** 回复的消息内容长度不超过2048字节 */
	private String Content;

	/** 消息ID */
	private Long MsgId;
	
	/** 图片链接支持JPG、PNG格式较好的效果为大图640*320小图80*80 */
	private String PicUrl;

	/** 通过上传多媒体文件得到的ID */
	public String MediaId;
	
	public String Format;
	
	/** 缩略图的媒体ID通过上传多媒体文件得到的ID */
	public String ThumbMediaId;

	private int FuncFlag = 0;

	/** 音乐链接 */
	private String MusicURL;

	/** 高质量音乐链接 */
	private String HQMusicUrl;

	/** 图文消息个数限制为10条以内 */
	private Integer ArticleCount;

	/** 图文消息标题 */
	private String Title;

	/** 图文消息描述 */
	private String Description;

	/** 点击图文消息跳转链接 */
	private String Url;
	private String access_token;
	private String kf_account;
	private String kf_nick;
	private String kf_id;
	private String nickname;
	private String password;
	private String media;

	//多图文
	private List<Articles> Articles;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}


	public Long getMsgId() {
		return MsgId;
	}

	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public int getFuncFlag() {
		return FuncFlag;
	}

	public void setFuncFlag(int funcFlag) {
		FuncFlag = funcFlag;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

	public Integer getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(Integer articleCount) {
		ArticleCount = articleCount;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getMusicURL() {
		return MusicURL;
	}

	public void setMusicURL(String musicURL) {
		MusicURL = musicURL;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getKf_account() {
		return kf_account;
	}

	public void setKf_account(String kf_account) {
		this.kf_account = kf_account;
	}

	public String getKf_nick() {
		return kf_nick;
	}

	public void setKf_nick(String kf_nick) {
		this.kf_nick = kf_nick;
	}

	public String getKf_id() {
		return kf_id;
	}

	public void setKf_id(String kf_id) {
		this.kf_id = kf_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public List<Articles> getArticles() {
		return Articles;
	}

	public void setArticles(List<Articles> articles) {
		if (articles.size() > 10)
			articles = new ArrayList<Articles>(articles.subList(0, 10));
		this.Articles = articles;
	}

	public OutMessage() {
	}
}
