package cn.tbnb1.jdbc;

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

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class JdbcDruidUtils {
	 private static Properties properties = null;
	    private static DataSource dataSource = null;
	    private volatile static JdbcDruidUtils instatce = null;
		private static Connection connection; 
	    private static ResultSet resultSet;  
	    private static PreparedStatement pstmt;  

	    //私有构造函数,防止实例化对象
	    private JdbcDruidUtils() {

	    }

	    static {
	        try {
	            properties = new Properties();
	            // 1.加载properties文件
	            InputStream is = JdbcDruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");

	            // 2.加载输入流
	            properties.load(is);

	            // 3.获取数据源
	            dataSource = getDatasource();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * 用简单单例模式确保只返回一个链接对象
	     * 
	     * @return
	     */
	    public static  JdbcDruidUtils getInstace() {
	        if(instatce == null) {
	            synchronized (JdbcDruidUtils.class) {
	                if(instatce == null) {
	                    instatce = new JdbcDruidUtils();
	                }
	            }
	        }
	        return instatce;
	    }

	    // 返回一个数据源
	    public DataSource getDataSource() {
	        return dataSource;
	    }

	    // 返回一个链接
	    public Connection getConnection() {
	        try {
	            connection = dataSource.getConnection();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return connection;
	    }

	    // 加载数据源
	    private static DataSource getDatasource() {
	        DataSource source = null;
	        try {
	            source = DruidDataSourceFactory.createDataSource(properties);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return source;
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
//	    t_servlet_test
	    public static void main(String[] args) throws Exception {
	    	JdbcDruidUtils.getInstace().getConnection();
	    	try {
				List<Map<String,Object>> findResult = JdbcDruidUtils.findResult("select * from t_servlet_test", null);
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
