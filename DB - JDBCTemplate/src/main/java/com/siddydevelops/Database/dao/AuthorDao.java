package com.siddydevelops.Database.dao;

import com.siddydevelops.Database.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);

    Optional<Author> findOne(long l);

    List<Author> find();

    void update(long id,Author author);

    void delete(long id);
}
