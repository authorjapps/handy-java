package com.stocks.jdbc;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class SqlFileExecutor {

    private final DataSource dataSource;

    public SqlFileExecutor() {
        this.dataSource = createDataSource();
    }

    public SqlFileExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeSqlFile(String dotSqlFile) throws SQLException {
        Resource resource = new ClassPathResource(dotSqlFile);
        ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
    }

    public int executeSqlStatement(String sqlStatement) {
        JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForObject(sqlStatement, Integer.class);
    }

    private DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties dbProperties = loadProperties("db_server.properties");

        dataSource.setDriverClassName(dbProperties.getProperty("db.datasource.driver.class.name"));
        dataSource.setUrl(dbProperties.getProperty("db.datasource.url"));
        dataSource.setUsername(dbProperties.getProperty("db.datasource.username"));
        dataSource.setPassword(dbProperties.getProperty("db.datasource.password"));

        return dataSource;
    }

    private Properties loadProperties(String propFile){
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(propFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }
}
