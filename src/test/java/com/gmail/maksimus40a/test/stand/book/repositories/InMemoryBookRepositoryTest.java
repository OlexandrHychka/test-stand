package com.gmail.maksimus40a.test.stand.book.repositories;

import com.gmail.maksimus40a.test.stand.book.domain.Book;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class InMemoryBookRepositoryTest {

    private BookRepository testObject;

    @BeforeEach
    void setUp() {
        testObject = new InMemoryBookRepository();
        Arrays.asList(
                new Book("category1", "author1", "title1", BigDecimal.ONE),
                new Book("repetitionCategory", "author2", "title2", BigDecimal.ONE),
                new Book("repetitionCategory", "author3", "title3", BigDecimal.ONE),
                new Book("category3", "author4", "title4", BigDecimal.ONE),
                new Book("category4", "author5", "title5", BigDecimal.ONE),
                new Book("category5", "repetitiveAuthor", "title6", BigDecimal.ONE),
                new Book("category6", "repetitiveAuthor", "title7", BigDecimal.ONE),
                new Book("category7", "repetitiveAuthor", "title8", BigDecimal.ONE)
        ).forEach(book -> testObject.addBook(book));
    }

    @ParameterizedTest
    @MethodSource("booksSupplier")
    void testGetAllBooks(Book book) {
        List<Book> actualBooks = testObject.getAllBooks();
        assertAll(
                () -> assertThat(actualBooks.size(), is(testObject.countOfEntities())),
                () -> assertThat(actualBooks.get(1), isA(Book.class)),
                () -> assertThat(actualBooks, hasItem(book))
        );
    }

    static private List<Book> booksSupplier() {
        return Arrays.asList(
                new Book(1, "category1", "author1", "title1", BigDecimal.ONE),
                new Book(2, "repetitionCategory", "author2", "title2", BigDecimal.ONE),
                new Book(3, "repetitionCategory", "author3", "title3", BigDecimal.ONE),
                new Book(4, "category3", "author4", "title4", BigDecimal.ONE),
                new Book(5, "category4", "author5", "title5", BigDecimal.ONE),
                new Book(6, "category5", "repetitiveAuthor", "title6", BigDecimal.ONE),
                new Book(7, "category6", "repetitiveAuthor", "title7", BigDecimal.ONE),
                new Book(8, "category7", "repetitiveAuthor", "title8", BigDecimal.ONE)
        );
    }

    @Nested
    @DisplayName("Test method getBookById().")
    class GetBookByIdTest {

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
        @DisplayName("Check normal case to work getBookById() method.")
        void testGetBookByIdNormalCase(Integer id) {
            List<Book> expectedBooks = Arrays.asList(
                    new Book(1, "category1", "author1", "title1", BigDecimal.ONE),
                    new Book(2, "repetitionCategory", "author2", "title2", BigDecimal.ONE),
                    new Book(3, "repetitionCategory", "author3", "title3", BigDecimal.ONE),
                    new Book(4, "category3", "author4", "title4", BigDecimal.ONE),
                    new Book(5, "category4", "author5", "title5", BigDecimal.ONE),
                    new Book(6, "category5", "repetitiveAuthor", "title6", BigDecimal.ONE),
                    new Book(7, "category6", "repetitiveAuthor", "title7", BigDecimal.ONE),
                    new Book(8, "category7", "repetitiveAuthor", "title8", BigDecimal.ONE)
            );
            Optional<Book> actualBookOptional = testObject.getBookById(id);
            Book expectedBook = expectedBooks.get(id - 1);
            assertAll(
                    () -> assertTrue(actualBookOptional.isPresent()),
                    () -> assertThat(actualBookOptional.get(), isA(Book.class)),
                    () -> assertEquals(expectedBook, actualBookOptional.get())
            );
        }

        @Test
        @DisplayName("Check behaviour when send to getBookById() the id less than 0.")
        void testGetBookByIdLessZeroId() {
            Integer idLessZero = -1;
            String expectedMessage = "Id must be greater than 0. Your id = -1";
            Exception exception = assertThrows(IllegalArgumentException.class, () -> testObject.getBookById(idLessZero));
            assertEquals(expectedMessage, exception.getMessage());
        }

        @Test
        @DisplayName("Check behaviour when send to getBookById() id whose not present in repository.")
        void testGetBookByIdWhenSendIdWhoseNotPresent() {
            Integer idGreaterThanPresent = testObject.countOfEntities() + 1;
            Optional<Book> actualBook = testObject.getBookById(idGreaterThanPresent);
            assertFalse(actualBook.isPresent());
        }
    }

    @Nested
    @DisplayName("Test method getBooksByCriteria().")
    class GetBookByCriteriaTest {

        @Test
        @DisplayName("Check that getBooksByCriteria() method return valid concrete authors books list.")
        void testGetBooksByCriteriaAuthor() {
            List<Book> expectedList = Arrays.asList(
                    new Book(6, "category5", "repetitiveAuthor", "title6", BigDecimal.ONE),
                    new Book(7, "category6", "repetitiveAuthor", "title7", BigDecimal.ONE),
                    new Book(8, "category7", "repetitiveAuthor", "title8", BigDecimal.ONE)
            );
            List<Book> actualBooks = testObject.getBooksByCriteria("repetitiveAuthor", Integer.MAX_VALUE);

            assertAll(
                    () -> assertThat(actualBooks.size(), is(3)),
                    () -> assertIterableEquals(expectedList, actualBooks)
            );
        }

        @Test
        @DisplayName("Check that getBooksByCriteria() method return valid concrete category books list.")
        void testGetBooksByCriteriaCategory() {
            List<Book> expectedList = Arrays.asList(
                    new Book(2, "repetitionCategory", "author2", "title2", BigDecimal.ONE),
                    new Book(3, "repetitionCategory", "author3", "title3", BigDecimal.ONE)
            );
            List<Book> actualBooks = testObject.getBooksByCriteria("repetitionCategory", Integer.MAX_VALUE);

            assertAll(
                    () -> assertThat(actualBooks.size(), is(2)),
                    () -> assertIterableEquals(expectedList, actualBooks)
            );
        }

        @Test
        @DisplayName("Check that getBooksByCriteria() method return valid concrete author limited books list .")
        void testGetBooksByCriteriaAuthorWithLimit() {
            List<Book> expectedList = Arrays.asList(
                    new Book(6, "category5", "repetitiveAuthor", "title6", BigDecimal.ONE),
                    new Book(7, "category6", "repetitiveAuthor", "title7", BigDecimal.ONE)
            );
            long limit = 2;
            List<Book> actualBooks = testObject.getBooksByCriteria("repetitiveAuthor", limit);
            assertAll(
                    () -> assertThat(actualBooks.size(), is(2)),
                    () -> assertIterableEquals(expectedList, actualBooks)
            );
        }
    }

    @Test
    @DisplayName("Test addBook() method when Book is new and has not an id.")
    void testAddBookMethodWhenBookHasNoId() {
        assumeTrue(testObject.countOfEntities() == 8);
        Book newBook = new Book("newCategory", "newAuthor", "newTitle", BigDecimal.ONE);
        Book addedBook = testObject.addBook(newBook);
        assertAll(
                () -> assertNotNull(addedBook),
                () -> assertThat(addedBook.getId(), is(testObject.countOfEntities())),
                () -> assertThat(testObject.countOfEntities(), is(9))
        );
    }

    @Nested
    @DisplayName("Test method editBook().")
    class EditBookTest {

        private Book editedBook;

        @BeforeEach
        void setUp() {
            editedBook = new Book("editedCategory", "editedAuthor", "editedTitle", BigDecimal.TEN);
        }

        @Test
        @DisplayName("Test normal behaviour of editBook() method.")
        void testEditBookMethod() {
            int testId = getRandomId();
            assumeTrue(testObject.countOfEntities() == 8);
            Book actualBook = testObject.editBook(testId, editedBook).get();
            Book expectedBook = new Book(testId, "editedCategory", "editedAuthor", "editedTitle", BigDecimal.TEN);
            assertAll(
                    () -> assertNotNull(actualBook),
                    () -> assertEquals(expectedBook, actualBook),
                    () -> assertThat(actualBook.getId(), is(testId)),
                    () -> assertThat(testObject.countOfEntities(), is(8))
            );
        }

        @Test
        @DisplayName("Check behaviour when send to editBook() the id less than 0.")
        void testEditBookMethodGetLessZeroId() {
            Integer idLessZero = -1;
            String expectedMessage = "Id must be greater than 0. Your id = -1";
            Exception exception = assertThrows(IllegalArgumentException.class, () -> testObject.editBook(idLessZero, editedBook));
            assertEquals(expectedMessage, exception.getMessage());
        }

        @Test
        @DisplayName("Check behaviour when send to editBook() id whose not present in repository.")
        void testEditBookMethodWhenSendIdWhoseNotPresent() {
            Integer idGreaterThanPresent = testObject.countOfEntities() + 1;
            Optional<Book> editedBookOptional = testObject.editBook(idGreaterThanPresent, editedBook);
            assertFalse(editedBookOptional.isPresent());
        }

        @AfterEach
        void tearDown() {
            editedBook = null;
        }
    }

    @Nested
    @DisplayName("Test method deleteBookById().")
    class DeleteBookByIdTest {

        @Test
        @DisplayName("Test deleteById() when id is within the available range.")
        void testDeleteBookByIdNormalBehaviour() {
            assumeTrue(testObject.countOfEntities() == 8);
            int deletedBookId = getRandomId();
            assertAll(
                    () -> assertTrue(testObject.deleteBookById(deletedBookId)),
                    () -> assertThat(testObject.countOfEntities(), is(7))
            );
        }

        @Test
        @DisplayName("Test deleteById() when id is out of bounds of the available range")
        void testDeleteBookByIdWhenIdOutOfBoundsNormalRange() {
            assumeTrue(testObject.countOfEntities() == 8);
            int lessId = -1;
            String expectedExceptionMessage = "Id must be greater than 0. Your id = -1";
            Exception actualException = assertThrows(IllegalArgumentException.class, () -> testObject.deleteBookById(lessId));
            assertAll(
                    () -> assertEquals(expectedExceptionMessage, actualException.getMessage()),
                    () -> assertThat(testObject.countOfEntities(), is(8))
            );
            int largerId = testObject.countOfEntities() + 1;
            assertAll(
                    () -> assertFalse(testObject.deleteBookById(largerId)),
                    () -> assertThat(testObject.countOfEntities(), is(8))
            );
        }
    }

    private int getRandomId() {
        return new Random().nextInt(testObject.countOfEntities());
    }

    @AfterEach
    void tearDown() {
        testObject = null;
    }
}