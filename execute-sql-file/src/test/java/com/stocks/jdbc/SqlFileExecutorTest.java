package com.stocks.jdbc;

import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SqlFileExecutorTest {

    @Test
    public void testExecuteSqlsIn_dotSqlFile() throws SQLException {
        SqlFileExecutor sqlFileExecutor = new SqlFileExecutor();
        sqlFileExecutor.executeSqlFile("create_customer_table.sql");
        sqlFileExecutor.executeSqlFile("insert_customers.sql");
    }

    @Test
    public void testExecute_sql() throws SQLException {
        SqlFileExecutor sqlFileExecutor = new SqlFileExecutor();
        int count = sqlFileExecutor.executeSqlStatement("SELECT count(*) FROM certgurus.question_set");
        assertThat(count, is(7));
    }

}