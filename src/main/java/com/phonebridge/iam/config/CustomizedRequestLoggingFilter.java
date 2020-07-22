package com.phonebridge.iam.config;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

public class CustomizedRequestLoggingFilter extends AbstractRequestLoggingFilter {

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		this.logger.info(message);
	}

	@Override
	protected String getMessagePayload(HttpServletRequest request) {
		String result=super.getMessagePayload(request);
		return maskSensitiveData(result, Collections.singletonList("password"));
	} 
	
	private static String maskSensitiveData(String payLoad,List<String> sensitiveKeys) {
		if(payLoad==null || payLoad.length()<1 || sensitiveKeys.isEmpty())
			return payLoad;
		for(String eachKey:sensitiveKeys) { 
			int startIndex = payLoad.indexOf("\""+eachKey+"\":");
			int finishIndex = payLoad.substring(startIndex).indexOf("\",") + startIndex;
			if(startIndex>-1 && finishIndex>startIndex) {
				String toReplace = payLoad.substring(startIndex,finishIndex);
				toReplace = toReplace.substring(toReplace.indexOf("\":\"")+3);
				payLoad = payLoad.replace(":\""+toReplace+"\",",":\"masked\",");
			}
		}
		return payLoad;
	}
}
