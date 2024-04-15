package org.iot.itty.article.vo;

import lombok.Data;

@Data
public class RequestRegistFreeBoardArticle {
	private String articleTitle;
	private String articleContent;
	private int userCodeFk;
}
