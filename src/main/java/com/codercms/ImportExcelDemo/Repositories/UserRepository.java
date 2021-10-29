package com.codercms.ImportExcelDemo.Repositories;


import com.codercms.ImportExcelDemo.Entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository to implement define overriding methods in CrudRepository
 */
public interface UserRepository extends CrudRepository<UserEntity, String> {
    @Override
    List<UserEntity> findAll();

    @Query(value="SELECT password FROM users WHERE username=?1",nativeQuery=true)
     String findByUsername(String username);

}


