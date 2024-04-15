package org.iot.itty.article.vo;

import lombok.Data;

@Data
public class RequestAddArticleLike {
	private int articleCode;
	private int userCode;
}
