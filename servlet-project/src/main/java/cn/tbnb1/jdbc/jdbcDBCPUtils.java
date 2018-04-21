package cn.tbnb1.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

/**
 * DBCP 连接池
 * @author zengrong
 *
 */
public class jdbcDBCPUtils {
	private static final String dbconfig = "dbcp.properties";
	private static Connection connection; 
    private static ResultSet resultSet;  
    private static PreparedStatement pstmt;  
    private static Properties properties = new Properties();
    private static DataSource dataSource;
    //加载DBCP配置文件
    static{
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(dbconfig);
            properties.load(is);
        }catch(IOException e){
            e.printStackTrace();
        }
        
        try{
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //从连接池中获取一个连接
    public static Connection getConnection(){
        try{
            connection = dataSource.getConnection();
        }catch(SQLException e){
            e.printStackTrace();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    
    /**
     * 更新数据
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static boolean updateByPreparedStatement(String sql, List<?> params)  
            throws SQLException {  
    	  boolean flag = false;  
    	try {
  	        int result = -1;// 表示当用户执行添加删除和修改的时候所影响数据库的行数  
  	        pstmt = connection.prepareStatement(sql);  
  	        int index = 1;  
  	        // 填充sql语句中的占位符  
  	        if (params != null && !params.isEmpty()) {  
  	            for (int i = 0; i < params.size(); i++) {  
  	                pstmt.setObject(index++, params.get(i));  
  	            }  
  	        }  
  	        result = pstmt.executeUpdate();  
  	        flag = result > 0 ? true : false;  
		} catch (Exception e) {
			   throw e;
		}finally {
			releaseConn();
		}
      
        return flag;  
    }  
    
    /**
     * 查询结果
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> findResult(String sql, List<?> params)  
            throws SQLException {  
    	
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        int index = 1;  
    	try {
    		  pstmt = connection.prepareStatement(sql);  
  	        if (params != null && !params.isEmpty()) {  
  	            for (int i = 0; i < params.size(); i++) {  
  	                pstmt.setObject(index++, params.get(i));  
  	            }  
  	        }  
  	      resultSet = pstmt.executeQuery();  
	        ResultSetMetaData metaData = resultSet.getMetaData();  
	        int cols_len = metaData.getColumnCount();  
	        while (resultSet.next()) {  
	            Map<String, Object> map = new HashMap<String, Object>();  
	            for (int i = 0; i < cols_len; i++) {  
	                String cols_name = metaData.getColumnName(i + 1);  
	                Object cols_value = resultSet.getObject(cols_name);  
	                if (cols_value == null) {  
	                    cols_value = "";  
	                }  
	                map.put(cols_name, cols_value);  
	            }  
	            list.add(map);  
	        } 
		} catch (Exception e) {
			
		}finally {
			releaseConn();
		}
        return list;  
    }  
    
    
    /** 
     * 释放资源 
     */  
    public static void releaseConn() {  
        if (resultSet != null) {  
            try {  
                resultSet.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        if (pstmt != null) {  
            try {  
                pstmt.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        if (connection != null) {  
            try {  
                connection.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
//    t_servlet_test
    public static void main(String[] args) {
    	jdbcDBCPUtils.getConnection();
    	try {
			List<Map<String,Object>> findResult = jdbcDBCPUtils.findResult("select * from t_servlet_test", null);
			for (Map<String, Object> map : findResult) {
				Set<String> keySet = map.keySet();
				for (String keyCom : keySet) {
					System.out.println(keyCom+"=="+map.get(keyCom));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    
}
