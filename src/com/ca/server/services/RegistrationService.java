package com.ca.server.services;

import java.util.List;

import com.ca.server.entity.RegistrationFactory;
import com.ca.server.models.RegistratoinEM;

public class RegistrationService {

	public void saveRegistration(RegistratoinEM registration) {

		RegistrationFactory registrationFactory = new RegistrationFactory();
		registrationFactory.saveRegistration(registration);

	}

	public List<RegistratoinEM> getRegistrations() throws Exception {

		RegistrationFactory registrationFactory = new RegistrationFactory();
		List<RegistratoinEM> registrations = registrationFactory.getAllRegistrations();

		return registrations;

	}

}
