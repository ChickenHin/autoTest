package cn.zhangxin.project.database;

import cn.zhangxin.project.util.ProjectFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.ArrayList;

public class DataBaseManage {
    //数据库主机、用户名、密码
    private String url = null;
    private String username = null;
    private String password = null;

    private String driver = "com.mysql.cj.jdbc.Driver";

    private Connection connection = null;
    private PreparedStatement preStatement = null;
    private ResultSet resultSet = null;

    private Logger log4jLogger;

    public DataBaseManage() throws Exception {
        this.url = ProjectFile.read("src/profiles", "system.properties","dburl");
        this.username = ProjectFile.read("src/profiles", "system.properties","dbuser");
        this.password = ProjectFile.read("src/profiles", "system.properties","dbpwd");
    }

    //建立连接
    public Connection initConnection(){
        try {
            //加载驱动程序
            Class.forName(driver);
            //获得数据库连接
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
//            System.out.println("Fail to build connection!");
            log4jLogger.error("Fail to build connection!");
            e.printStackTrace();

        } catch (SQLException e) {
            log4jLogger.error("Fail to build connection!");
            e.printStackTrace();
        }
        return connection;
    }

    //多个数据查询
    public ArrayList<HashMap<String, Object>> dbQuery(String sql, String param) throws SQLException {
        //建立连接
        initConnection();
        preStatement = connection.prepareStatement(sql);

        //填充参数
        int index = 1;

        preStatement.setObject(index++, param);


        //查询结果
        resultSet = preStatement.executeQuery();

        ResultSetMetaData metaData = resultSet.getMetaData();
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        int columnCount = metaData.getColumnCount();
        while(resultSet.next()){
            HashMap<String, Object> map = new HashMap<String, Object>();
            for(int i = 0; i < columnCount; i++){
                String columnName = metaData.getColumnName(i+1);
                String columnVal = resultSet.getString(columnName);
                if(columnVal == null){
                    columnVal = "";
                }
                map.put(columnName, columnVal);
            }
            list.add(map);
        }
//        closeConnection();

        return list;
    }

    //单个数据查询
    public HashMap<String, Object> singleDbQuery(String sql, ArrayList<Object> params) throws SQLException {
        initConnection();
        preStatement = connection.prepareStatement(sql);

        HashMap<String, Object> map = new HashMap<String, Object>();

        int index  = 1;
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                preStatement.setObject(index++, params.get(i));
            }
        }

        resultSet = preStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();

        int columnCount = metaData.getColumnCount();
        while(resultSet.next()){
            for(int i = 0; i < columnCount; i++ ){
                String columnName = metaData.getColumnName(i+1);
                Object columnVal = resultSet.getObject(columnName);
                if(columnVal == null){
                    columnVal = "";
                }
                map.put(columnName, columnVal);
            }
        }
        closeConnection();

        return map;
    }

    //插入、删除、修改
    public boolean dbDeleteOrUpdateOrInsert(String sql, ArrayList<Object> params) throws SQLException {
        //建立连接
        initConnection();
        preStatement = connection.prepareStatement(sql);

        //填充参数
        int index = 1;
        if(params != null && !params.isEmpty()){
            for(int i = 0; i < params.size(); i++){
                preStatement.setObject(index++, params.get(i));
            }
        }

        //result:执行成功的条数
        int result = preStatement.executeUpdate();
        boolean flag = result > 0 ? true : false;
        closeConnection();

        return flag;
    }

    //关闭连接
    public void closeConnection() {
        try {
            if(resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
            if(preStatement != null) {
                preStatement.close();
                preStatement = null;
            }
            if(connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.out.println("Fail to close connection!");
            e.printStackTrace();
        }
    }
}
