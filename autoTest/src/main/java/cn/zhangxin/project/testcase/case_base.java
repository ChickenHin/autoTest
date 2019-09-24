package cn.zhangxin.project.testcase;

import cn.zhangxin.project.testresult.ReportGenerate;
import cn.zhangxin.project.webdriver.DriverDefine;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Listeners;
import org.uncommons.reportng.HTMLReporter;
import org.uncommons.reportng.JUnitXMLReporter;
import cn.zhangxin.project.util.SeleniumScreenShot;

@Listeners({SeleniumScreenShot.class,HTMLReporter.class,JUnitXMLReporter.class, ReportGenerate.class})
public class case_base {

    @AfterSuite
    public void quit() {
        Reporter.log("用例执行完毕，关闭浏览器");
        DriverDefine.driver.quit();
    }
}
