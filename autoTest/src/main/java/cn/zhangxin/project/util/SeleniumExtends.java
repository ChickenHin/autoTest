package cn.zhangxin.project.util;

import java.util.Set;
import org.apache.log4j.Logger;

import org.apache.log4j.Priority;
import org.openqa.selenium.*;

import org.openqa.selenium.internal.WrapsDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumExtends {
    private Logger log4jLogger;

    public static void switchToFrame(WebDriver driver, By frameLocator) throws Exception{

        try {

            WebDriverWait driverWait = new WebDriverWait(driver, 15);

            driverWait.until(ExpectedConditions.presenceOfElementLocated(frameLocator));

            driver.switchTo().frame(driver.findElement(frameLocator));

        } catch (Exception e) {

            e.printStackTrace();

            throw new Exception("Fail to switch to frame: " + frameLocator.toString());

        }

    }

    public static void setAttribute(WebElement element, String attributeName, String attributeValue){

        WrapsDriver wrappedElement = (WrapsDriver) element;

        JavascriptExecutor javascriptDriver = (JavascriptExecutor) wrappedElement.getWrappedDriver();



        javascriptDriver.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, attributeName, attributeValue);

    }

    public static void clickElementByJavascript(WebElement element){

        WrapsDriver wrappedElement = (WrapsDriver) element;

        JavascriptExecutor javascriptDriver = (JavascriptExecutor) wrappedElement.getWrappedDriver();



        javascriptDriver.executeScript("arguments[0].click();", element);

    }

    public static void removeAttribute(WebElement element, String attributeName){

        WrapsDriver wrappedElement = (WrapsDriver) element;

        JavascriptExecutor javascriptDriver = (JavascriptExecutor) wrappedElement.getWrappedDriver();

        javascriptDriver.executeScript("arguments[0].removeAttribute(arguments[1])", element, attributeName);

    }

    public static boolean isElementExsit(WebDriver driver, By locator) {

        boolean flag = false;

        try {

            WebElement element=driver.findElement(locator);

            flag=null!=element;

        } catch (NoSuchElementException e) {

        }

        return flag;

    }



    public static boolean ButtonDisableStatus(WebDriver driver,By Locator) throws Exception{

        boolean flag=false;

        try {

            String ElementStatus=driver.findElement(Locator).findElement(By.cssSelector("span")).getAttribute("style");

            if (ElementStatus.equals("color: gray;"))

            {

                flag=true;

                return flag;

            }

            return flag;

        }

        catch (Exception e)

        {

            return flag;

        }

    }





    public static boolean CheckBoxDisableStatus(WebDriver driver,By Locator) throws Exception{

        boolean flag=false;

        try {

            String[] ElementString=driver.findElement(Locator).getAttribute("src").split("\\/");

            String ElementStatus=ElementString[ElementString.length-1];

            if ((ElementStatus.equals("disabled_checked.gif"))||((ElementStatus.equals("disabled_unchecked.gif"))))

            {

                flag=true;

                return flag;

            }

            return flag;

        }

        catch (Exception e)

        {

            return flag;

        }

    }



    public static boolean RadioButtonDisableStatus(WebDriver driver,By Locator) throws Exception{

        boolean flag=false;

        try {

            boolean ElementStatus=driver.findElement(Locator).isEnabled();

            flag=!ElementStatus;

            return flag;

        }

        catch (Exception e) {

            return flag;

        }

    }



    public static WebDriver switchToWindow(WebDriver driver){

        WebDriver newDriver =null;

        try {

            String currentHandle = driver.getWindowHandle();

            Set<String> handles = driver.getWindowHandles();

            for (String s : handles) {

                if (s.equals(currentHandle))

                    continue;

                else {

                    newDriver=driver.switchTo().window(s);

                }

            }

        } catch (NoSuchWindowException e) {

            e.printStackTrace();

        }

        return newDriver;

    }

    public void stableClick(WebDriver driver, By locator) {
        int time = 3, count = 0;
        while (time > 0) {
            try {
                driver.findElement(locator).click();
                log4jLogger.info("执行点击" + locator + "元素" + count + "次成功");
            }
            catch (Exception e) {
                if (e instanceof WebDriverException) {
                    time--;
                    count++;
                    log4jLogger.debug("第" + count + "次点击" + locator + "元素失败");
                } else {
                    e.printStackTrace();
                    log4jLogger.error("点击" + locator + "元素失败");
                    break;
                }
            }
        }
        if(time == 0) {
            log4jLogger.error("点击" + locator + "元素失败，点击次数：" + time + "次" );
        }
    }

    public void stableInput(WebDriver driver, By locator) {

    }

    public void refresh(WebDriver driver) {
        driver.navigate().refresh();
    }

}
