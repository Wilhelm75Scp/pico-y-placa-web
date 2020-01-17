package com.stackbuilders.web.models.service;

import java.util.Map;

import com.stackbuilders.web.models.entity.Restriction;

public interface IRestrictionService {

	public Restriction findById(Long id);
	
	public Map<String, String> validateRestriction (String plate, String date, String time);

}
