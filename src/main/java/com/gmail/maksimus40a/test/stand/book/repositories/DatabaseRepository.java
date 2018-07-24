package com.gmail.maksimus40a.test.stand.book.repositories;

import com.gmail.maksimus40a.test.stand.book.Book;
import com.gmail.maksimus40a.test.stand.book.BookRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("db")
public class DatabaseRepository implements BookRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS books(" +
                "  id       INT(11)        NOT NULL AUTO_INCREMENT," +
                "  category VARCHAR(100)   NOT NULL," +
                "  author   VARCHAR(100)   NOT NULL," +
                "  title    VARCHAR(100)   NOT NULL," +
                "  price    DECIMAL(10, 2) NOT NULL," +
                "  PRIMARY KEY (id)" +
                ");";
        jdbcTemplate.execute(sql);
    }

    @Override
    public int countOfEntities() {
        String sql = "SELECT COUNT(*) FROM books";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    @Override
    public Optional<Book> getBookById(Integer id) {
        String sql = "SELECT * FROM books WHERE books.id = ?";
        Book book = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BookRowMapper());
        return Optional.of(book);
    }

    @Override
    public List<Book> getBooksByCriteria(String fieldName, String value, long limit) {
        String sql = "SELECT * FROM books WHERE " + fieldName + " = ? LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{value, limit}, new BookRowMapper());
    }

    @Override
    public Book addBook(final Book book) {
        String sql = "INSERT INTO books(category, author, title, price) VALUES (?,?,?,?)";
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getCategory());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getTitle());
            ps.setBigDecimal(4, book.getPrice());
            return ps;
        }, generatedKeyHolder);
        Integer newUserId = generatedKeyHolder.getKey().intValue();
        book.setId(newUserId);
        return book;
    }

    @Override
    public Optional<Book> editBook(Integer id, final Book editedBook) {
        String sql = "UPDATE books SET category = ?, author = ?, title = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                editedBook.getCategory(),
                editedBook.getAuthor(),
                editedBook.getTitle(),
                editedBook.getPrice(),
                id);
        return this.getBookById(id);
    }

    @Override
    public boolean deleteBookById(Integer id) {
        String sql = "DELETE FROM books WHERE id = ?";
        return jdbcTemplate.update(sql, id) != 0;
    }
}