package com.siddydevelops.Database.dao.impl;

import com.siddydevelops.Database.TestDataUtil;
import com.siddydevelops.Database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatBookGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBookA();
        underTest.create(book);
        verify(jdbcTemplate).update(eq("INSERT INTO books (isbn, title, author_id) VALUES (?,?,?)"),
                eq("8794-454L-8521"),eq("The Shadow in the Attic: Part 1"),eq(1L));
    }

    @Test
    public void testThatFindOneBookGeneratesCorrectSql() {
        underTest.findOne("8794-454L-8521");
        verify(jdbcTemplate).query(eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("8794-454L-8521")
        );
    }

    @Test
    public void testThatFindManyBooksGeneratesCorrectSql() {
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBookA();
        underTest.update("8794-454L-8521",book);
        verify(jdbcTemplate).update(
                "UPDATE books SET isbn=?, title=?, author_id=? WHERE isbn=?",
                "8794-454L-8521","The Shadow in the Attic: Part 1",1L,"8794-454L-8521"
        );
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        underTest.delete("8794-454L-8521");
        verify(jdbcTemplate).update(
                "DELETE FROM books WHERE isbn = ?",
                "8794-454L-8521"
        );
    }
}
