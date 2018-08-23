package com.ca.server.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ca.server.models.Registration;
import com.ca.server.models.RegistratoinEM;
import com.ca.server.models.Version;
import com.ca.server.services.RegistrationService;
import com.ca.server.util.CAException;
import com.ca.server.util.CAExceptionCodes;
import com.ca.server.util.CAMapper;
import com.ca.server.util.ErrorResponse;

/**
 * @author hasithakaushan
 * 
 * This controller is related in controlling functions of Registration of a person such
 * as creating, deleting, updating, retrieving etc.
 *
 */
@RestController
@CrossOrigin
public class RegistrationController {
	
	@RequestMapping(value="/test")
	public Version getVersion(){
		
		Version version = new Version();
		version.setVersion("1.0");
		version.setTitle("Initial Version");
		
		return version;
	}
	
	/**
	 * This controller method is related in saving or updating a registration in database.
	 * Required url ==> http://localhost:8080/caServer/registration/save
	 * 
	 * @param registration: the registration object which is going to be saved
	 * @throws CAException: a customized exception object
	 */
	@RequestMapping(value="/registration/save", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus>  saveRegistration(@RequestBody Registration registration) throws Exception{
		
		//This is a new instance from RegistrationService where calls the entity
		RegistrationService registrationService = new RegistrationService();
		
		//This is a new instance from CAMapper where maps the received object into the entity object
		CAMapper mapper = new CAMapper();
		
		//This is a new object from Registration Entity Model which is going to be saved at the entity
		RegistratoinEM registrationToSave = new RegistratoinEM();
		
		HttpStatus httpStatus = null;
		
		System.out.println(registration.getFirstName());
		
		try {
			
			/*
			 * The received Registration object will be mapped into the Registration Entity Model using the CAMapper instance
			 * and assigns it to the Registration Entity Model which is going to be saved at the entity 
			 */
			registrationToSave = mapper.mapRegistrationToSave(registration);
			
			//This will send the mapped Registration Entity object to Registration Service.
			registrationService.saveRegistration(registrationToSave);
			
			httpStatus = HttpStatus.CREATED;
			
		} catch (Exception e) {
			
			//This will fire a customized CAException once an error is occurred while saving
			throw new CAException(e.toString(), CAExceptionCodes.REGISTRATION_SAVE_ERROR);
		}
		
		return new ResponseEntity<HttpStatus>(httpStatus);
		
	}
	
	/**
	 * This controller method is related in retrieving a list of registration from database.
	 * Required url ==> http://localhost:8080/caServer/registration/get
	 * 
	 * @return registrationsToSend: a list of registrations
	 * @throws CAException: a customized exception object
	 */
	@RequestMapping(value="/registration/get", method = RequestMethod.GET)
	public List<Registration> getAllRegistrations() throws Exception{
		
		//This is a new instance from RegistrationService where calls the entity
		RegistrationService registrationService = new RegistrationService();
		
		//This is a list of Registration Entity objects which are received from database
		List<RegistratoinEM> registrations = null;
		
		//This is a list of Registration objects which are going to be sent to the client
		List<Registration> registrationsToSend = null;
		
		//This is a new instance from CAMapper where maps the received object into the entity object
		CAMapper mapper = new CAMapper();
		
		try {
			
			//A list of Registration Entity Objects will be received and assigned into  'registrations' list
			registrations= registrationService.getRegistrations();
			
			//Received Registration Entity Objects list from entity will be mapped into Registration objects list to be sent to the client
			registrationsToSend = mapper.mapRegistrationList(registrations);
			
		} catch (Exception e) {
			
			//This will fire a customized CAException once an error is occurred while retrieving the list of registrations
			throw new CAException("No Registration Found", CAExceptionCodes.NO_REGISTRATION_FOUND);
		}
		
		return registrationsToSend;
	}
	
	/**
	 * This controller method is related in deleting a particular registration from database.
	 * Actually the registration object will not be deleted completely from the database.
	 * The 'isDeleted' status will be '1' at the end of the process.
	 * Required url ==> http://localhost:8080/caServer/registration/delete
	 * 
	 * @param registration: required registration that needs to be deleted
	 * @throws CAException: a customized exception object
	 */
	@RequestMapping(value="/registration/delete", method = RequestMethod.POST)
	public void deleteRegistration(@RequestBody Registration registration) throws Exception{
		
		//This is a new instance from RegistrationService where calls the entity
		RegistrationService registrationService = new RegistrationService();
		
		//This is a new instance from CAMapper where maps the received object into the entity object
		CAMapper mapper = new CAMapper();
		
		//This is a new object from Registration Entity Model which is going to be deleted at the entity
		RegistratoinEM registrationToDelete = new RegistratoinEM();
		
		try {
			
			/*
			 * The received Registration object will be mapped into the Registration Entity Model using the CAMapper instance
			 * and assigns it to the Registration Entity Model which is going to be deleted at the entity 
			 */
			registrationToDelete = mapper.mapRegistrationToDelete(registration);
			
			//This will send the mapped Registration Entity object to Registration Service.
			registrationService.saveRegistration(registrationToDelete);
			
		} catch (Exception e) {
			
			//This will fire a customized CAException once an error is occurred while retrieving the list of registrations
			throw new CAException("Registration Not Saved", CAExceptionCodes.REGISTRATION_SAVE_ERROR);
			
		}
		
	}
	
	/**
	 * This controller method is related in handling exception within this controller.
	 * 
	 * @param exception: a customized exception thrown in above methods.
	 * @return ResponseEntity<ErrorResponse>: an 'ErrorResponse' ResponseEntity which contains the error JSON object
	 */
	@ExceptionHandler(CAException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(CAException exception) {
		
		//This is a new object from ErrorResponse which is goint to be sent to the client within ResponseEntity 
		ErrorResponse error = new ErrorResponse();
		
		//Sets the errorCode of the ErrorResponse as the customized error code which is within 'exception'
		error.setErrorCode(exception.getErrorCode());
		
		//Sets the errorMessage of the ErrorResponse as the customized error message which is within 'exception'
		error.setMessage(exception.getMessage());
		
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	
}
