package com.phonebridge.iam.rest.controller.v1.req;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BaseRequest {

	@NotEmpty
	@Size(message="accountName size should be between 3 and 20",min=3,max=50)
	@JsonProperty("requestId")
	protected String requestId;
	
	@JsonProperty("requestDate")
	protected Date requestDate = new Date();
}