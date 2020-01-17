package com.stackbuilders.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stackbuilders.web.models.service.IRestrictionService;

@Controller
public class PredictController {

	@Autowired
	private IRestrictionService restrictionService;

	@RequestMapping(value = "/predict", method = RequestMethod.GET)
	public String predict(Model model) {
		model.addAttribute("title", "xxx");
		return "predict";
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(Model model) {
		model.addAttribute("title", "xxx");
		return "info";
	}

	@RequestMapping(value = "/predict", method = RequestMethod.POST)
	public String predict(@RequestParam(value = "plate", required = false) String plate,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "time", required = false) String time, Model model) {

		if (plate.equals("") || date.equals("") || time.equals("")) {
			model.addAttribute("error", "Complete los datos!");
			model.addAttribute("message", "Data errors, check details at the top!");
			return "predict";
		}
		Map<String, String> incidents = restrictionService.validateRestriction(plate, date, time);
		if (incidents.get("assert").equals("n/a")) {
			model.addAttribute("message", "Data errors, check details at the top!");
		} else if (incidents.get("assert").equals("true")) {
			model.addAttribute("message", "Vehicle with plate " + plate + " has traffic restriction!");
			model.addAttribute("inforestriction",
					"Los dias de restriccion para placas terminadas con digito 1 es Lunes de ... hasta ...");
		} else if (incidents.get("assert").equals("false")) {
			model.addAttribute("message", "Vehicle with plate " + plate + " is enabled to drive!");
		}
		if (incidents.size() > 1) {
			incidents.forEach((key, value) -> model.addAttribute(key, value));
		}
		model.addAttribute("title", "xxx");
		return "predict";
	}

}
