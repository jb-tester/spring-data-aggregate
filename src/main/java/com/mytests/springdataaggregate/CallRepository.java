package com.mytests.springdataaggregate;

import com.mytests.springdataaggregate.model.Call;
import org.springframework.data.repository.CrudRepository;


public interface CallRepository extends CrudRepository<Call, Long> {
}
