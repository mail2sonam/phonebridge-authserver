package com.phonebridge.iam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig{
	
	 	@Bean
	    public AbstractRequestLoggingFilter logFilter() {
	 		AbstractRequestLoggingFilter filter = new CustomizedRequestLoggingFilter();
	        filter.setIncludeQueryString(true);
	        filter.setIncludePayload(true);
	        filter.setMaxPayloadLength(100000);
	        filter.setHeaderPredicate((headerName -> !"authorization".equals(headerName)));
	        filter.setIncludeHeaders(true);
	        filter.setAfterMessagePrefix("REQUEST DATA : ");
	        return filter;
	    }
}
