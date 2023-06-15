package com.fleetstudio.county.Repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.fleetstudio.county.models.County;

public interface CountyRepository extends CrudRepository<County, Long> {

    @Query(value = "SELECT c FROM County c WHERE lower(c.state) LIKE ?1 OR lower(c.name) LIKE ?1")
    List<County> getCountySuggestion(String query,Pageable pageable);

    @Query(value = "SELECT c FROM County c WHERE lower(c.state) LIKE ?1 AND lower(c.name) LIKE ?2")
    List<County> getCountySuggestionByStateAndName(String state,String name,Pageable pageable);

    List<County> findByStateContainingIgnoreCase(String title,Pageable pageable);

    List<County> findByNameContainingIgnoreCase(String title,Pageable pageable);

}
