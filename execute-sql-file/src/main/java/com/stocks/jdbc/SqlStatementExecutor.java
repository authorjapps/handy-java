package com.stocks.jdbc;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;

public class SqlStatementExecutor {

    private JdbcTemplate jdbcTemplate;

    private DataSource dataSource;

    public void executeSqlFile(String dotSqlFile) throws SQLException {
        dataSource = createDataSource();
        Resource resource = new ClassPathResource(dotSqlFile);
        ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
    }


    public int executeSqlStatement(String sqlStatement) {
        jdbcTemplate = new JdbcTemplate(createDataSource());

        int result = jdbcTemplate.queryForObject(sqlStatement, Integer.class);

        return result;
    }

    private DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/certgurus");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");

        return dataSource;
    }

}
