package com.ca.server.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ca.server.models.Dob;
import com.ca.server.models.Registration;
import com.ca.server.models.RegistratoinEM;

public class CAMapper {

	public RegistratoinEM mapRegistrationToSave(Registration registration) throws ParseException {

		RegistratoinEM registrationToSave = new RegistratoinEM();
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		

		//registrationToSave.setId(registration.getId());
		registrationToSave.setFirstName(registration.getFirstName());
		registrationToSave.setLastName(registration.getLastName());
		registrationToSave.setCountry(registration.getCountry());
		registrationToSave.setEmail(registration.getEmail());
		registrationToSave.setDob(df.parse(registration.getDob().getYear()+"-"+registration.getDob().getMonth()+"-"+registration.getDob().getDay()));
		registrationToSave.setIsDeleted(0);
		registrationToSave.setCreatedBy("ADMIN");
		registrationToSave.setCreatedDate(now);
		registrationToSave.setDeletedBy("");

		return registrationToSave;

	}

	@SuppressWarnings("deprecation")
	public List<Registration> mapRegistrationList(List<RegistratoinEM> regList) {
		List<Registration> registrations = new ArrayList<Registration>();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		for (RegistratoinEM registrationRecieved : regList) {

			Registration registration = new Registration();
			Dob dob  = new Dob();
			
			dob.setYear(registrationRecieved.getDob().getYear() + 1900);
			dob.setMonth(registrationRecieved.getDob().getMonth() + 1);
			dob.setDay(registrationRecieved.getDob().getDate());

			registration.setId(registrationRecieved.getId());
			registration.setFirstName(registrationRecieved.getFirstName());
			registration.setLastName(registrationRecieved.getLastName());
			registration.setCountry(registrationRecieved.getCountry());
			registration.setEmail(registrationRecieved.getEmail());
			registration.setDob(dob);;

			registrations.add(registration);

		}

		return registrations;
	}

	public RegistratoinEM mapRegistrationToDelete(Registration registration) throws ParseException {

		RegistratoinEM registrationToDelete = new RegistratoinEM();
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		registrationToDelete.setId(registration.getId());
		registrationToDelete.setFirstName(registration.getFirstName());
		registrationToDelete.setLastName(registration.getLastName());
		registrationToDelete.setCountry(registration.getCountry());
		registrationToDelete.setEmail(registration.getEmail());
		registrationToDelete.setDob(df.parse(registration.getDob().getYear()+"-"+registration.getDob().getMonth()+"-"+registration.getDob().getDay()));
		registrationToDelete.setIsDeleted(1);
		registrationToDelete.setDeletedBy("ADMIN");
		registrationToDelete.setDeletedDate(now);

		return registrationToDelete;

	}

}
