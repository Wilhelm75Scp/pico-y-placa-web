package com.stackbuilders.web;

import static org.mockito.ArgumentMatchers.any;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.stackbuilders.web.models.dao.IRestrictionDao;
import com.stackbuilders.web.models.entity.Restriction;
import com.stackbuilders.web.models.service.RestrictionServiceImpl;

@SpringBootTest
public class RestrictionServiceImplTest {

	@InjectMocks
	private RestrictionServiceImpl restrictionServiceImpl;
	@Mock
	private IRestrictionDao iRestrictionDao;
	@Mock
	private Restriction restriction = new Restriction();

	private static final String PLATE = "ABC5251";
	private static final String DATE = "17/01/2020";
	private static final String TIME = "08:00";
	private static final String WRONG_TIME = "56:56";

	@BeforeEach
	public void inicializar() {
		restriction = new Restriction();
		restriction.setId(2L);
		restriction.setDay(2);
		restriction.setDigitRestriction("1,2");
		restriction.setTimeFrame("0,0,0,0,0,0,0,60,60,30,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
		restriction.setCreateAt(new Date());
	}

	@Test
	public void mustValidateRestrictionRightParameters() {
		Mockito.when(iRestrictionDao.findById(any(Long.class))).thenReturn(Optional.of(restriction));
		restrictionServiceImpl.validateRestriction(PLATE, DATE, TIME);
	}
	
	@Test
	public void mustValidateRestrictionWrongParameters() {
		Mockito.when(iRestrictionDao.findById(any(Long.class))).thenReturn(Optional.of(restriction));
		restrictionServiceImpl.validateRestriction(PLATE, DATE, WRONG_TIME);
	}

}
