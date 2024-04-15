package org.iot.itty.article.vo;

import lombok.Data;

@Data
public class RequestDeleteArticleLike {
	private int articleCode;
	private int userCode;
}
