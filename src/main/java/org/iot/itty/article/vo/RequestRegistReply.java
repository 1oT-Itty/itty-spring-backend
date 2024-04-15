package org.iot.itty.article.vo;

import lombok.Data;

@Data
public class RequestRegistReply {
	private String replyContent;
	private int userCodeFk;
	private int articleCodeFk;
}
