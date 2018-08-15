package com.gmail.maksimus40a.test.stand;

import com.gmail.maksimus40a.test.stand.features.book.domain.Book;
import com.gmail.maksimus40a.test.stand.features.employee.domain.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class SearchConfiguration {

    @Bean
    @Qualifier("book-search")
    public Function<String, Predicate<Book>> searchFunctionOfBook() {
        return criteria -> book -> {
            double price;
            try {
                price = Double.parseDouble(criteria);
            } catch (NumberFormatException e) {
                return book.getAuthor().equals(criteria) ||
                        book.getCategory().equals(criteria) ||
                        book.getTitle().equals(criteria);
            }
            return book.getPrice().compareTo(BigDecimal.valueOf(price)) == 0;
        };
    }

    @Bean
    @Qualifier("employee-search")
    public Function<String, Predicate<Employee>> searchFunctionOfEmployee() {
        return criteria -> employee -> employee.getFirstName().equals(criteria) ||
                employee.getLastName().equals(criteria) ||
                employee.getEmail().equals(criteria) ||
                employee.getCareer().equals(criteria) ||
                employee.getSkills().stream()
                        .anyMatch(skill -> skill.equals(criteria));
    }

    @Bean
    @Qualifier("employee-fields")
    public List<String> employeeFields() {
        return getEntityFieldsNames(Employee.class);
    }

    @Bean
    @Qualifier("book-fields")
    public List<String> bookFields() {
        return getEntityFieldsNames(Book.class);
    }

    private List<String> getEntityFieldsNames(Class cl) {
        return Stream.of(cl.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .map(Field::getName)
                .collect(Collectors.toList());
    }
}