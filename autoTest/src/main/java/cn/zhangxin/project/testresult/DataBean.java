package cn.zhangxin.project.testresult;

import java.util.List;

public class DataBean {
    private int passedTestsCount; //测试通过的数量
    private int failedTestsCount; //测试失败的数量
    private int skippedTestsCount; //测试跳过的数量
    private int allTestsCount; //全部执行的测试的数量
    private String[] allTestsMethod; //全部执行的测试方法
    private String testsTime; //测试耗时
    private String passPercent; //测试通过率
    private String testMethodName; //测试方法名
    private String testClassName; //测试类名
    private String duration; //单个测试周期
    private String params; //测试用参数
    private String description; //测试描述
    private List<String> output; //Reporter Output
    private Throwable throwable; //测试异常原因

    public int getPassedTestsCount() { return passedTestsCount; }

    public void setPassedTestsCount(int passedTestsCount) { this.passedTestsCount = passedTestsCount; }

    public int getFailedTestsCount() { return failedTestsCount; }

    public void setFailedTestsCount(int failedTestsCount) { this.failedTestsCount = failedTestsCount; }

    public int getSkippedTestsCount() {
        return skippedTestsCount;
    }

    public void setSkippedTestsCount(int skippedTestsCount) {
        this.skippedTestsCount = skippedTestsCount;
    }

    public int getAllTestsCount() {
        return allTestsCount;
    }

    public void setAllTestsCount(int allTestsCount) {
        this.allTestsCount = allTestsCount;
    }

    public String getPassPercent() {
        return passPercent;
    }

    public void setPassPercent(String passPercent) {
        this.passPercent = passPercent;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable2) {
        this.throwable = throwable2;
    }

    public void setTestsTime(String testsTime) {
        this.testsTime = testsTime;
    }

    public String getTestsTime() {
        return testsTime;
    }

    public void setAllTestsMethod(String[] allTestsMethod) {
        this.allTestsMethod = allTestsMethod;
    }

    public String[] getAllTestsMethod() {
        return allTestsMethod;
    }
}
