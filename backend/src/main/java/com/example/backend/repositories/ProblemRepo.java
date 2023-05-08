package com.example.backend.repositories;

import com.example.backend.entity.Problem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProblemRepo extends CrudRepository <Problem,Long> {
   @Override
   Iterable<Problem> findAll();

   @Override
   Optional<Problem> findById(Long aLong);
}
