package com.siddydevelops.Database;

import com.siddydevelops.Database.domain.dto.AuthorDto;
import com.siddydevelops.Database.domain.dto.BookDto;
import com.siddydevelops.Database.domain.entities.AuthorEntity;
import com.siddydevelops.Database.domain.entities.BookEntity;

public class TestDataUtil {
    private TestDataUtil() {

    }
    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                .id(1L)
                .name("John Doe")
                .age(72)
                .build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .id(2L)
                .name("Siddhartha Singh")
                .age(21)
                .build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .id(3L)
                .name("Rose Marie")
                .age(32)
                .build();
    }

    public static AuthorDto createTestAuthorDtoA() {
        return AuthorDto.builder()
                .id(1L)
                .name("John Doe")
                .age(72)
                .build();
    }

    public static BookEntity createTestBookA(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("8794-454L-8521")
                .title("The Shadow in the Attic: Part 1")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookB(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("8794-454L-8522")
                .title("The Shadow in the Attic: Part 2")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("8794-454L-8523")
                .title("The Shadow in the Attic: Part 3")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto authorEntity) {
        return BookDto.builder()
                .isbn("8794-454L-8521")
                .title("The Shadow in the Attic: Part 1")
                .author(authorEntity)
                .build();
    }
}
