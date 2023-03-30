package com.progetto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.dto.ClientBankDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;
import com.progetto.service.IutenteStandardService;
import com.progetto.utils.Constants;
import com.progetto.utils.DecryptorData;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/spring/client") 
@Validated
public class ClientBankController {

//	@Autowired
//	KafkaRender kafkaRender;
	
	@Autowired
	IutenteStandardService userService;


	
	@PostMapping(value = Constants.SAVE_CLIENT_BANK_MAPPING, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseFromApi save(@RequestBody @Valid ClientBankDto usdto, HttpServletRequest request) throws GenericError {
		ResponseFromApi response = userService.save(usdto, request);
		//kafkaRender.printMessage(response);
		return DecryptorData.decryptPanCodeIban(response); 
	}

	@GetMapping(value = Constants.GET_ALL_CLIENT_BANK_MAPPING, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ResponseFromApi> findAll() throws GenericError {
		return userService.findAll();
	}

	@DeleteMapping(value = Constants.DELETE_CLIENT_BANK_MAPPING)
	public ResponseFromApi delete(@PathVariable  @Pattern(regexp = Constants.PAN_CODD_REGEX, message = Constants.INVALID_PAN_CODE) String panCode) throws GenericError {
		return userService.delete(panCode);

	}

	@GetMapping(value = Constants.FIND_BY_EMAIL_CLIENT_BANK_MAPPING)
	public ResponseFromApi findByEmail(@PathVariable @Pattern(regexp = Constants.REGEX_EMAIL, message = Constants.FORMAT_EMAIL_ERROR)String email) throws GenericError{
		return userService.findByEmail(email);
	}
	
	@PutMapping(value = Constants.UPDATE_CLIENT_BANK_MAPPING)
	public ResponseFromApi update(@PathVariable @Pattern(regexp = Constants.PAN_CODD_REGEX, message = Constants.INVALID_PAN_CODE)String panCode, @RequestBody @Valid ClientBankDto userDTO) throws GenericError{
		return userService.update(userDTO, panCode);
	}
	

}
