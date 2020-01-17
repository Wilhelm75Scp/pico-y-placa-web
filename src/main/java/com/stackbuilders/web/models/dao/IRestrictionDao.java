package com.stackbuilders.web.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.stackbuilders.web.models.entity.Restriction;

public interface IRestrictionDao extends CrudRepository<Restriction, Long> {

}
