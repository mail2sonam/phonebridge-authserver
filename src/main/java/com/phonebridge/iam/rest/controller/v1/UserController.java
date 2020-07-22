package com.phonebridge.iam.rest.controller.v1;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phonebridge.iam.rest.controller.v1.req.NewUserReq;
import com.phonebridge.iam.rest.controller.v1.req.UpdateUserReq;
import com.phonebridge.iam.rest.controller.v1.resp.GenericResp;
import com.phonebridge.iam.rest.controller.v1.resp.UserResp;
import com.phonebridge.iam.row.UserRow;
import com.phonebridge.iam.service.IUserService;

@RestController
@EnableResourceServer
@RequestMapping("/v1/user")
public class UserController {

	@Autowired
	private IUserService userService; // User service reference
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * To create the user
	 * 
	 * @param newUserRq
	 * @return created user
	 */
	@PostMapping
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<GenericResp> create(@RequestBody @Valid NewUserReq newUserRq) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserRow userRow = userService.createUser(newUserRq.toUserRow(),authentication.getName());
		GenericResp resp = new GenericResp(newUserRq.getRequestId(),userRow.getUsername());
		ResponseEntity<GenericResp> result=new ResponseEntity<>(resp, HttpStatus.CREATED);
		logger.info("Result:"+result.getStatusCode()+","+result.getBody());
		return result;
	}

	/**
	 * To get all the users
	 * 
	 * @return list of users
	 */
	@GetMapping("/all")
	public ResponseEntity<List<UserResp>> findAll() {
		List<UserRow> userRowLst = userService.findAll();
		List<UserResp> result= Collections.emptyList();//userMapper.userRowListToUserRespList(userRowLst);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/**
	 * To get all the users in an account
	 * 
	 * @param accountId
	 * @return list of users
	 */
	@GetMapping("/{accountId}/all")
	public ResponseEntity<List<UserResp>> findAllByAccountId(@PathVariable("accountId") String accountId) {
		List<UserRow> userRowLst = userService.findAllByAccountId(accountId);
		List<UserResp> result= Collections.emptyList();//userMapper.userRowListToUserRespList(userRowLst);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/**
	 * To update the user details by id
	 * 
	 * @param accountId
	 * @param userId
	 * @param todoEntry
	 * @return no content status, on success
	 */
	@PutMapping
	public ResponseEntity<UserResp> updateUser(@RequestBody @Valid UpdateUserReq updateUserReq) {
		//UserRow givenUserRow=userMapper.updateUserReqtoUserRow(updateUserReq);
		//UserRow userRow = userService.updateUser(givenUserRow);
		UserResp result=new UserResp();//userMapper.userRowtoUserResp(userRow);
		return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
	}

}
