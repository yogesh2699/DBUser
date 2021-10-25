package com.codercms.ImportExcelDemo.Repositories;


import com.codercms.ImportExcelDemo.Entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository to implement define overriding methods in CrudRepository
 */
public interface UserRepository extends CrudRepository<UserEntity, String> {
    @Override
    List<UserEntity> findAll();
}


