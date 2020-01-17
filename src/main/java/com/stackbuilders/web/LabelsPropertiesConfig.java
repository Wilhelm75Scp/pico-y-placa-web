package com.stackbuilders.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Configuration class to define property files
 * 
 * @author William Simbana
 *
 */
@Configuration
@PropertySources({ @PropertySource("classpath:labels.properties") })
public class LabelsPropertiesConfig {

}
