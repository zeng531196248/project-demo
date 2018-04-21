package cn.tbnb1.jdbc;

import java.sql.Connection;

import org.junit.Test;

import junit.framework.TestCase;

public class JdbcDruidUtilsTest extends TestCase {
	   @Test
	    public void testAdd() {
		   JdbcDruidUtils jdbcDruidUtils = JdbcDruidUtils.getInstace();
		   Connection connection = jdbcDruidUtils.getConnection();
		   
	    }
}
