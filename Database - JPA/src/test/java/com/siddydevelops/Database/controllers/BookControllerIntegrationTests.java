package com.siddydevelops.Database.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siddydevelops.Database.TestDataUtil;
import com.siddydevelops.Database.domain.dto.AuthorDto;
import com.siddydevelops.Database.domain.dto.BookDto;
import com.siddydevelops.Database.domain.entities.BookEntity;
import com.siddydevelops.Database.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc,BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJSON = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJSON)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedAuthor() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJSON = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatListBookSuccessfullyReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListBookSuccessfullyListOfBooks() throws Exception {
        BookEntity testBookA = TestDataUtil.createTestBookA(null);
        bookService.save(testBookA.getIsbn(),testBookA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBookA.getTitle())
        );
    }

    @Test
    public void testThatGetBookSuccessfullyReturnsHttp200WhenBookExists() throws Exception {
        BookEntity testBookA = TestDataUtil.createTestBookA(null);
        bookService.save(testBookA.getIsbn(),testBookA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetBookSuccessfullyReturnsHttp404WhenBookDoesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/123-456-789")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetBookWhenBookExists() throws Exception {
        BookEntity testBookA = TestDataUtil.createTestBookA(null);
        bookService.save(testBookA.getIsbn(),testBookA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".isbn").value(testBookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".title").value(testBookA.getTitle())
        );
    }

    @Test
    public void testThatFullUpdateBookSuccessfullyReturnsHttp200WhenBookExists() throws Exception {
        BookEntity testBook = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.save(testBook.getIsbn(),testBook);

        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(null);
        String bookDtoJSON = objectMapper.writeValueAsString(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetUpdatedBookWhenBookExists() throws Exception {
        BookEntity testBook = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.save(testBook.getIsbn(),testBook);

        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(null);
        String bookDtoJSON = objectMapper.writeValueAsString(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".isbn").value(testBookDtoA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".title").value(testBookDtoA.getTitle())
        );
    }

    @Test
    public void testThatPartialUpdateBookSuccessfullyReturnsHttp200WhenBookExists() throws Exception {
        BookEntity testBook = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.save(testBook.getIsbn(),testBook);

        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(null);
        testBookDtoA.setTitle("UPDATED");
        String bookDtoJSON = objectMapper.writeValueAsString(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdatedBookWhenBookExists() throws Exception {
        BookEntity testBook = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.save(testBook.getIsbn(),testBook);

        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(null);
        testBookDtoA.setTitle("UPDATED");
        String bookDtoJSON = objectMapper.writeValueAsString(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".isbn").value(testBookDtoA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".title").value("UPDATED")
        );
    }

    @Test
    public void testThatDeleteBookReturnHttpStatus204() throws Exception {
        BookEntity testBook = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.save(testBook.getIsbn(),testBook);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
