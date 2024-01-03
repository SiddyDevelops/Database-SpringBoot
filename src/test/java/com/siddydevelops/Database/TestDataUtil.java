package com.siddydevelops.Database;

import com.siddydevelops.Database.domain.Author;
import com.siddydevelops.Database.domain.Book;

public class TestDataUtil {
    private TestDataUtil() {

    }
    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("John Doe")
                .age(72)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Siddhartha Singh")
                .age(21)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Rose Marie")
                .age(32)
                .build();
    }

    public static Book createTestBookA() {
        return Book.builder()
                .isbn("8794-454L-8521")
                .title("The Shadow in the Attic: Part 1")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookB() {
        return Book.builder()
                .isbn("8794-454L-8522")
                .title("The Shadow in the Attic: Part 2")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookC() {
        return Book.builder()
                .isbn("8794-454L-8523")
                .title("The Shadow in the Attic: Part 3")
                .authorId(1L)
                .build();
    }
}
