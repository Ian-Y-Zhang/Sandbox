package com.Ian.message.resp;

import java.util.List;

/**
 * 
 * 图文消息类
 * 
 * @author Ian
 * @date 2016-05-12
 *
 */
public class NewsMessage extends BaseMessage {

	private int ArticleCount;

	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		this.ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		this.Articles = articles;
	}

}
