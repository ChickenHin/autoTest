package cn.zhangxin.project.util;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import cn.zhangxin.project.po.Po_login;

public class SeleniumScreenShot extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult tr) {
        WebDriver driver = Po_login.getDriver();

        // 没用到webdrvier不需要重写
        if ((driver == null) || (driver.toString().contains("null"))) {
            super.onTestFailure(tr);
        } else {
            // 截图写到测试报告
            String screenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            String dataUri = "classpath:image/png;base64," + screenshotBase64;
            Reporter.setEscapeHtml(false);
            Reporter.log("<img alt=\"Captured Screenshot\" src=\"" + dataUri + "\" />");

            // 截图保存到本地
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, new File("/Users/zhangxin/Documents/autoTest/" + tr.getMethod() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        WebDriver driver = Po_login.getDriver();

        // 没用到webdrvier不需要重写
        if ((driver == null) || (driver.toString().contains("null"))) {
            super.onTestFailure(tr);
        } else {

            // 截图写到测试报告
            String screenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            String dataUri = "classpath:image/png;base64," + screenshotBase64;
            Reporter.setEscapeHtml(false);
            Reporter.log("<img alt=\"Captured Screenshot\" src=\"" + dataUri + "\" />");

            // 截图保存到本地
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, new File("/Users/zhangxin/Documents/autoTest/" + tr.getMethod() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
