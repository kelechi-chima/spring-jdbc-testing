package com.kchima.jdbc.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/db.xml")
public class ExampleDaoTest {

    private final Logger logger = LoggerFactory.getLogger(ExampleDaoTest.class);

    @Autowired private ExampleDao exampleDao;

    @Autowired private DataSource dataSource;

    private final RowMapper<Person> personRowMapper = new RowMapper<Person>() {

        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person p = new Person();
            p.setId(rs.getInt("id"));
            p.setFirstName(rs.getString("first_name"));
            p.setLastName(rs.getString("last_name"));
            return p;
        }
    };

    @Test
    public void testInsert() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Person> rows = jdbcTemplate.query("select id, first_name, last_name from person", personRowMapper);

        logger.debug("initial rows: {}", rows.size());

        Person person = new Person();
        person.setId(2);
        person.setFirstName("Nancy");
        person.setLastName("Drew");
        exampleDao.insert(person);

        rows = jdbcTemplate.query("select id, first_name, last_name from person", personRowMapper);
        assertEquals(2, rows.size());
    }

}
