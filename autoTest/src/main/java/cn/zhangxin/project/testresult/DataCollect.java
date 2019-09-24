package cn.zhangxin.project.testresult;

import org.testng.*;

import java.util.*;
import org.apache.log4j.Logger;

public class DataCollect {
    private Logger log4jLogger;
    // 测试结果Set<ITestResult>转为list，再按执行时间排序 ，返回list
    public List<ITestResult> sortByTime(Set<ITestResult> str) {
        List<ITestResult> list = new ArrayList<ITestResult>();
        for (ITestResult r : str) {
            list.add(r);
        }
        Collections.sort(list);
        return list;
    }

    public DataBean wholeTestResult(ITestContext context) {
        // 测试结果汇总数据
        DataBean data = new DataBean();
        DataHandle dataHandle = new DataHandle();
        IResultMap passedTests = context.getPassedTests();
        IResultMap failedTests= context.getFailedTests();
        IResultMap skipedTests = context.getSkippedTests();
        //全部测试周期方法，包括beforetest,beforeclass,beforemethod,aftertest,afterclass,aftermethod
        //IResultMap passedConfigurations =context.getPassedConfigurations();
        //IResultMap failedConfigurations =context.getFailedConfigurations();
        //IResultMap skipedConfigurations =context.getSkippedConfigurations();
//        Collection<ITestNGMethod> excludeTests = context.getExcludedMethods();

        int passedTestsCount = passedTests.size();
        int failedTestsCount = failedTests.size();
        int skipedTestsCount = skipedTests.size();
//        int excludeTestsCount = excludeTests.size();
        //所有测试结果的数量＝测试pass+fail+skip的和，因为数据驱动一个测试方法有多次执行的可能，导致方法总数并不等于测试总数
        int allTestsCount= passedTestsCount + failedTestsCount + skipedTestsCount;
        data.setAllTestsCount(allTestsCount);
        data.setPassedTestsCount(passedTestsCount);
        data.setFailedTestsCount(failedTestsCount);
        data.setSkippedTestsCount(skipedTestsCount);
        data.setTestsTime(dataHandle.calTestDuration(context));
        data.setPassPercent(dataHandle.calPercentage(passedTestsCount, allTestsCount));

        ITestNGMethod[] methods = context.getAllTestMethods();
        String[] method = new String[methods.length];
        for(int i = 0 ; i < methods.length ; i++){
            method[i] = methods[i].getMethodName();
        }
        data.setAllTestsMethod(method);
        return data;


    }


    public List<DataBean> singleTestResults(IResultMap map, int status) {
        // 测试结果详细数据
        List<DataBean> list = new ArrayList<DataBean>();
        DataHandle dataHandle = new DataHandle();
//        map.getAllResults().size();
        for (ITestResult result : sortByTime(map.getAllResults())) {
            DataBean data = new DataBean();
            data.setTestMethodName((result.getMethod().getMethodName()));
//            log4jLogger.debug("data"+data.getTestMethodName());
            String classname = result.getTestClass().getName();
            int i=classname.lastIndexOf(".");
            data.setTestClassName(classname.substring(i+1));

            data.setDuration(dataHandle.formatDuration(result.getEndMillis()
                    - result.getStartMillis()));
            data.setParams(dataHandle.getParams(result));
            data.setDescription(result.getMethod().getDescription());
            data.setOutput(Reporter.getOutput(result));
            data.setThrowable(result.getThrowable());
            list.add(data);
        }
        return list;
    }
}
