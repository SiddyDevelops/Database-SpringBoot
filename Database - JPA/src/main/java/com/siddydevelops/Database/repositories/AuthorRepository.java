package com.siddydevelops.Database.repositories;

import com.siddydevelops.Database.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    Iterable<AuthorEntity> ageLessThan(int age);

    Iterable<AuthorEntity> ageGreaterThan(int age);

//    @Query("SELECT a FROM Author a WHERE a.age > ?1")
//    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int age);
}
