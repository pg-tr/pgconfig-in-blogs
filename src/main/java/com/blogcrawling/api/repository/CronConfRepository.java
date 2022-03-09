package com.blogcrawling.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blogcrawling.api.domain.CronConfig;

@Repository
public interface CronConfRepository extends CrudRepository<CronConfig, Integer> {

}
