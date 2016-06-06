package com.liukun.teabaike.bean;

import java.io.Serializable;

public class Tea implements Serializable {
	private static final long serialVersionUID = 1L;
	// id
	private String id;
	// 标题
	private String title;
	// 出处
	private String source;
	// 内容描述
	private String description;
	// 图片
	private String wap_thumb;
	// 创建时间
	private String create_time;
	// 名称
	private String nickname;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWap_thumb() {
		return wap_thumb;
	}
	public void setWap_thumb(String wap_thumb) {
		this.wap_thumb = wap_thumb;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "Tea [id=" + id + ", title=" + title + ", source=" + source
				+ ", description=" + description + ", wap_thumb=" + wap_thumb
				+ ", create_time=" + create_time + ", nickname=" + nickname
				+ "]";
	}
	
}
