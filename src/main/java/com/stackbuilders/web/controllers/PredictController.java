package com.stackbuilders.web.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stackbuilders.web.models.service.IRestrictionService;

/**
 * predict.html and info.html Spring controller
 * 
 * @author William Simbana
 * 
 */
@Controller
public class PredictController {

	@Value("${label.predictcontroller.predict}")
	private String labelTitlePredict;
	@Value("${label.predictcontroller.info}")
	private String labelTitleInfo;
	@Value("${label.predictcontroller.layout}")
	private String labelTitleLayout;
	@Value("${label.error.complete.data}")
	private String labelErrorCompleteData;
	@Value("${label.error.details.top}")
	private String labelErrorDetailsTop;
	@Value("${label.info.period.restriction}")
	private String labelInfoPeriodRestriction;
	@Value("${label.info.especific.restriccion}")
	private String labelInfoEspecificRestriction;

	@Autowired
	private IRestrictionService restrictionService;

	/**
	 * Default GET Request Mapping for predict.html
	 * 
	 * @author William Simbana
	 * @param objeto Model
	 * @return predict.html
	 */
	@RequestMapping(value = "/predict", method = RequestMethod.GET)
	public String predict(Model model) {
		model.addAttribute("title", labelTitlePredict);
		return "predict";
	}

	/**
	 * Default GET Request Mapping for info.html
	 * 
	 * @author William Simbana
	 * @param objeto Model
	 * @return info.html
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(Model model) {
		model.addAttribute("title", labelTitleInfo);
		model.addAttribute("periodrestrictioninfo", labelInfoPeriodRestriction);
		return "info";
	}

	/**
	 * POST Request Mapping for predict.html
	 * 
	 * @author William Simbana
	 * @param String plate
	 * @param String date
	 * @param String time
	 * @param objeto Model
	 * @return predict.html
	 */
	@RequestMapping(value = "/predict", method = RequestMethod.POST)
	public String predict(@RequestParam(value = "plate", required = false) String plate,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "time", required = false) String time, Model model) {

		// Null parameters validation
		if (plate.equals("") || date.equals("") || time.equals("")) {
			model.addAttribute("error", labelErrorCompleteData);
			model.addAttribute("message", labelErrorDetailsTop);
			return "predict";
		}
		// Restriction validation from parameters license plate, date and time
		Map<String, String> incidents = restrictionService.validateRestriction(plate, date, time);
		if (incidents.get("assert").equals("n/a")) {
			model.addAttribute("message", labelErrorDetailsTop);
		} else if (incidents.get("assert").equals("true")) {
			model.addAttribute("message",
					"Vehicle with license plate " + plate + " has traffic restriction on " + date + " at " + time + "!");
			model.addAttribute("inforestriction", labelInfoEspecificRestriction);
		} else if (incidents.get("assert").equals("false")) {
			model.addAttribute("message",
					"Vehicle with license plate " + plate + " is enabled to drive on " + date + " at " + time + "!");
		} else {
			model.addAttribute("message", incidents.get("assert"));
		}
		if (incidents.size() > 1) {
			incidents.forEach((key, value) -> model.addAttribute(key, value));
		}
		model.addAttribute("title", labelTitlePredict);
		return "predict";
	}

}
