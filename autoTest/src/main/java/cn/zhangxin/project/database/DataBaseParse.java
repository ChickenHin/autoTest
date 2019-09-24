package cn.zhangxin.project.database;

import cn.zhangxin.project.util.Log4j;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBaseParse {

    private static Logger log4jLogger;

    public static Object[][] getDataFromDB(String tableName, String testMethod, int paraCount) throws Exception {

        //Log4j to record logging

        log4jLogger = Log4j.logger(DataBaseParse.class.getName());

        List<HashMap<String, Object>> testDataList = new ArrayList<>();

        String sql = "select * from " + tableName + "where test_method = ?";

        DataBaseManage dataBaseManage = new DataBaseManage();

        try {


            testDataList = dataBaseManage.dbQuery(sql, testMethod);

            int rowNumber = testDataList.size();
            int columnNumber = paraCount;

            Object[][] testData = new Object[rowNumber][columnNumber];

            for (int i = 0; i < rowNumber; i++) {
                HashMap<String, Object> testDataMap = testDataList.get(i);
                int count = 1;
                for (int j = 0; j < columnNumber; j++) {
                    String mapKey = "test_para" + count;
                    Object data = testDataMap.get(mapKey);
                    if (data == null) {
                        testData[i][j] = null;
                        log4jLogger.info(mapKey + "is empty, treat it as null");
                    } else {
                        testData[i][j] = data;
                        log4jLogger.info(mapKey + ":" + data);
                    }
                    count++;
                }
            }
            return testData;
        } catch (Exception e) {

            e.printStackTrace();

            throw new Exception("Fail to get test data from database");

        } finally {

            dataBaseManage.closeConnection();

            log4jLogger.info("Close the database connection ");

        }

    }
}
