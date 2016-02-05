package com.kchima.jdbc.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Component
public class ExampleDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExampleDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public void insert(Person person) {
        jdbcTemplate.update("insert into person (id, first_name, last_name) values(?, ?, ?)",
                person.getId(), person.getFirstName(), person.getLastName());
    }
}
