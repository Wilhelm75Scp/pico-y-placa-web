package com.stackbuilders.web.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.stackbuilders.web.models.entity.Restriction;

/**
 * Dao class to Restriction entity
 * 
 * @author William Simbana
 *
 */
public interface IRestrictionDao extends CrudRepository<Restriction, Long> {

}
