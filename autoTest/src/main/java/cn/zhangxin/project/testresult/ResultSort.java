package cn.zhangxin.project.testresult;

import org.testng.ITestResult;

public class ResultSort implements Comparable<ITestResult> {
    private Long order;
    @Override
    public int compareTo(ITestResult arg0) {
        return this.order.compareTo( arg0.getStartMillis());//按test开始时间排序
    }
}
