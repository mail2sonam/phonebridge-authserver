package com.phonebridge.iam.rest.controller.v1.resp;

import lombok.Data;

@Data
public class GenericResp {
	String requestId;
	String entityId;
	ErrorResponse errorResp;
	
	public GenericResp(String requestId,String entityId) {
		this.requestId = requestId;
		this.entityId=entityId;
	}
	
	public GenericResp(String requestId,ErrorResponse er){
		this.requestId = requestId;
		this.errorResp=er;
	}
}
