package WebAutomationBase.step;

import WebAutomationBase.base.BaseTest;
import WebAutomationBase.helper.ElementHelper;
import WebAutomationBase.helper.StoreHelper;
import WebAutomationBase.model.ElementInfo;
import com.thoughtworks.gauge.Step;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.NoSuchElementException;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.PropertyConfigurator;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;


import javax.swing.text.Document;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Fail.fail;
import static org.junit.Assert.assertTrue;

public class BaseSteps extends BaseTest {

    public static int DEFAULT_MAX_ITERATION_COUNT = 150;
    public static int DEFAULT_MILLISECOND_WAIT_AMOUNT = 100;

    public static Log4jLoggerAdapter logger = (Log4jLoggerAdapter) LoggerFactory
            .getLogger(BaseSteps.class);

    private static String SAVED_ATTRIBUTE;

    private Actions actions = new Actions(driver);
    private String compareText;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");


    public BaseSteps() {

        PropertyConfigurator
                .configure(BaseSteps.class.getClassLoader().getResource("log4j.properties"));
    }







    private void clickElement(WebElement element) {
        clickElement(element);
    }

    private void clickElementBy(String key) {
        findElement(key).click();
    }



    private void hoverElementBy(String key) {
        WebElement webElement = findElement(key);
        actions.moveToElement(webElement).build().perform();
    }

    private void sendKeyESC(String key) {
        findElement(key).sendKeys(Keys.ESCAPE);

    }

    public boolean isDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    private boolean isDisplayedBy(By by) {
        return driver.findElement(by).isDisplayed();
    }

    private String getPageSource() {
        return driver.switchTo().alert().getText();
    }

    public static String getSavedAttribute() {
        return SAVED_ATTRIBUTE;
    }

    public String randomString(int stringLength) {

        Random random = new Random();
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUWVXYZabcdefghijklmnopqrstuwvxyz0123456789".toCharArray();
        String stringRandom = "";
        for (int i = 0; i < stringLength; i++) {

            stringRandom = stringRandom + chars[random.nextInt(chars.length)];
        }

        return stringRandom;
    }



    public String getElementText(String key) {
        return findElement(key).getText();
    }

    public String getElementAttributeValue(String key, String attribute) {
        return findElement(key).getAttribute(attribute);
    }

    @Step("Print page source")
    public void printPageSource() {
        System.out.println(getPageSource());
    }

    public void javaScriptClicker(WebDriver driver, WebElement element) {

        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("var evt = document.createEvent('MouseEvents');"
                + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
                + "arguments[0].dispatchEvent(evt);", element);
    }

    public void javascriptclicker(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }



    public WebElement findElementWithKey(String key) {
        return findElement(key);
    }

    private List<WebElement> findElements(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        return driver.findElements(infoParam);
    }

    private WebElement findElement(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 60);
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    public void hoverElement(WebElement element) {
        actions.moveToElement(element).build().perform();
    }

    @Step({"<text> textini <key> elemente yaz ve enter yap"})
    public void ssendKeysAndEnter(String text, String key) {
        if (!key.equals("")) {
            findElement(key).sendKeys(text + "\n");
            logger.info(key + " elementine " + text + " texti yazıldı ve enter yapıldı.");
        }
    }

    @Step({"<int> saniye bekle"})
    public void waitBySeconds(int seconds) {
        try {
            logger.info(seconds + " saniye bekleniyor.");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step({"<long> milisaniye bekle"})
    public void waitByMilliSeconds(long milliseconds) {
        try {
            logger.info(milliseconds + " milisaniye bekleniyor.");
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step({"<key> listesinden rastgele tıkla"})
    public void clickRandomElement(String key) {
        List<WebElement> elementList = findElements(key);
        Random rnd = new Random();
        int randomIndex = rnd.nextInt(elementList.size());
        WebElement element = elementList.get(randomIndex);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                element);
        element.click();
        logger.info(key + " elementine tıklandı.");
    }

    @Step("Yeni sekmeye odaklan")
    public void focusNewTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
    }

    @Step({"Elementine tıkla <key>"})
    public void clickElement(String key) {
        if (!key.equals("")) {
            WebElement element = findElement(key);
            hoverElement(element);
            waitByMilliSeconds(500);
            element.click();
            logger.info(key + " elementine tıklandı.");
        }
    }


    public WebElement fastFindElementExplicitWait(String key) {

        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        WebDriverWait wait = new WebDriverWait(driver, 0, 500);

        WebElement element = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(infoParam));

        return element;
    }


    @Step({"<key> elementini bulana kadar aşağı kaydır, bulamazsa hata verme"})
    public void swipeDownUntilFindNoAssertion(String key) throws InterruptedException {

        WebElement element = null;

        try {

            element = fastFindElementExplicitWait(key);

            if (element != null) {
                hoverElement(element);
                logger.info(key + " elementi sayfada bulundu.");
            }

        } catch (Exception e) {
            scrollBottomSlowly();

        }
    }

    @Step({"Element var mı kontrol et <key>"})
    public WebElement checkElementHere(String key) throws InterruptedException {
        WebElement element;
        try {
            element = findElement(key);
            hoverElement(element);
            logger.info(key + " key'li element bulundu.");
        } catch (Exception ex) {
            Assert.fail("Element: '" + key + "' doesn't exist.");
            return null;
        }
        return element;
    }

    @Step({"biraz aşağı kaydır"})
    public void scrollBottomSlowly() {
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("window.scrollBy(0,100)");
    }

}









