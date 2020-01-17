package com.stackbuilders.web.models.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackbuilders.web.models.dao.IRestrictionDao;
import com.stackbuilders.web.models.entity.Restriction;

@Service
public class RestrictionServiceImpl implements IRestrictionService {

	static final String COMPLETE_HOUR = "60";
	static final String DATE_FORMAT = "dd/MM/yyyy";
	static final String HOUR_TOKEN = ":";
	static final String GENERAL_TOKEN = ",";

	@Autowired
	private IRestrictionDao restrictionDao;

	@Override
	public Restriction findById(Long id) {
		return restrictionDao.findById(id).orElse(null);
	}

	@Override
	public Map<String, String> validateRestriction(String plate, String date, String time) {
		Map<String, String> incidents = new HashMap<String, String>();
		String plateDigit = retrievePlateDigit(plate);
		Integer day = retrieveDayNumber(date, incidents);
		List<String> hourMinutes = this.retrieveHourMinutes(time, incidents);
		Integer hour = Integer.parseInt(hourMinutes.get(0));
		if (hour > 60)
			incidents.put("error", "El campo hora no puede ser mayor a 24.");
		Integer minutes = 0;
		if (hourMinutes.size() > 1)
			minutes = Integer.parseInt(hourMinutes.get(1));
		if (minutes > 60)
			incidents.put("error", "El campo minutos no puede ser mayor a 60.");

		Restriction restriction = this.findById(Long.parseLong(day.toString()));
		List<String> digitRestriction = this.retrieveDigitTestriction(restriction);
		List<String> timeFrame = this.retrieveTimeFrame(restriction);
		if (validateTimeFrameRestriction(hour, minutes, timeFrame) == null) {
			incidents.put("assert", "n/a");
		} else if (digitRestriction.contains(plateDigit) && validateTimeFrameRestriction(hour, minutes, timeFrame)) {
			incidents.put("assert", "true");
		} else {
			incidents.put("assert", "false");
		}
		return incidents;
	}

	private List<String> retrieveHourMinutes(String time, Map<String, String> incidents) {
		List<String> hourMinutes = new ArrayList<String>();
		StringTokenizer timeTokenizer = new StringTokenizer(time, HOUR_TOKEN);
		while (timeTokenizer.hasMoreTokens())
			hourMinutes.add(timeTokenizer.nextToken());
		if (Integer.valueOf(hourMinutes.get(0)) > 24)
			incidents.put("error", "El valor hora no puede ser mayor a 24.");
		if (hourMinutes.size() > 1 && Integer.valueOf(hourMinutes.get(1)) > 60)
			incidents.put("error", "El valor minutos no puede ser mayor a 60.");
		return hourMinutes;
	}

	private List<String> retrieveTimeFrame(Restriction restriction) {
		List<String> timeFrame = new ArrayList<String>();
		StringTokenizer timeFrameTokenizer = new StringTokenizer(restriction.getTimeFrame(), GENERAL_TOKEN);
		while (timeFrameTokenizer.hasMoreTokens())
			timeFrame.add(timeFrameTokenizer.nextToken());
		return timeFrame;
	}

	private List<String> retrieveDigitTestriction(Restriction restriction) {
		List<String> digitRestriction = new ArrayList<String>();
		StringTokenizer digitRestrictionTokenizer = new StringTokenizer(restriction.getDigitRestriction(),
				GENERAL_TOKEN);
		while (digitRestrictionTokenizer.hasMoreTokens())
			digitRestriction.add(digitRestrictionTokenizer.nextToken());
		return digitRestriction;
	}

	private Integer retrieveDayNumber(String date, Map<String, String> incidents) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			Date dateObject = formatter.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateObject);
			if (calendar.get(Calendar.DAY_OF_WEEK) > 31) {
				incidents.put("error", "El valor de día no puede ser mayor a 31.");
				return 0;
			}
			return calendar.get(Calendar.DAY_OF_WEEK);
		} catch (ParseException e) {
			return 0;
		}
	}

	private String retrievePlateDigit(String plate) {
		return plate.substring(plate.length() - 1, plate.length());
	}

	private Boolean validateTimeFrameRestriction(Integer hour, Integer minutes, List<String> timeFrame) {
		Boolean validation = false;
		if (hour > 24) {
			return null;
		} else if (timeFrame.get(hour).equals(COMPLETE_HOUR)) {
			validation = true;
		} else if (Integer.parseInt(timeFrame.get(hour)) > 0 && Integer.parseInt(timeFrame.get(hour)) > minutes) {
			validation = true;
		}
		return validation;
	}

}
