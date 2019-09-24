package cn.zhangxin.project.testresult;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class ReportGenerate implements IReporter {
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outputDirectory) {
        // TODO Auto-generated method stub
        try {
            // 初始化并取得Velocity引擎
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();

            Template t = ve.getTemplate("UIReport.vm");
            VelocityContext context = new VelocityContext();

            for (ISuite suite : suites) {
                Map<String, ISuiteResult> suiteResults = suite.getResults();
                for (ISuiteResult suiteResult : suiteResults.values()) {
                    DataCollect data = new DataCollect();
                    ITestContext testContext = suiteResult.getTestContext();
                    // 把数据填入上下文
                    context.put("overView", data.wholeTestResult(testContext));//测试结果汇总信息

                    IResultMap passedTests = testContext.getPassedTests();//测试通过的测试方法
                    IResultMap failedTests = testContext.getFailedTests();//测试失败的测试方法
                    IResultMap skippedTests = testContext.getSkippedTests();//测试跳过的测试方法

                    context.put("pass", data.singleTestResults(passedTests, ITestResult.SUCCESS));
                    context.put("fail", data.singleTestResults(failedTests, ITestResult.FAILURE));
                    context.put("skip", data.singleTestResults(skippedTests, ITestResult.FAILURE));
                }
            }
            // 输出流
            String filePath=System.getProperty("user.dir") + "/report.html";
            Writer writer = new BufferedWriter(new FileWriter(filePath));
            // 转换输出
            t.merge(context, writer);
            //System.out.println(writer.toString());
            writer.flush();

            //send email
            EmailSend email=new EmailSend();
            email.setAddress("zhangxin49@meituan.com", "zhangxin49@meituan.com", "UI自动化执行结果");
            email.setText(filePath);
            email.send();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
