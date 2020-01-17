package com.stackbuilders.web.models.service;

import java.util.Map;

import com.stackbuilders.web.models.entity.Restriction;

/**
 * Interface service
 * 
 * @author William Simbana
 *
 */
public interface IRestrictionService {

	/**
	 * Find Restriction object by id method
	 * 
	 * @author William Simbana
	 * @param Long type object
	 * @return Restriction type object
	 */
	public Restriction findById(Long id);

	/**
	 * Validation restriction method
	 * 
	 * @author William Simbana
	 * @param String type object
	 * @param String type object
	 * @param String type object
	 * @return Validation messages Map
	 */
	public Map<String, String> validateRestriction(String plate, String date, String time);

}
