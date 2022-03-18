package WebAutomationBase.step;

import WebAutomationBase.base.BaseTest;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ProductDetailPage extends BaseSteps {


    @Step({"Üründe yorum varsa ilk yorum beğenilir ve teşekkür mesajı kontrol edilir"})
    public void clickElementNoAssertionForLike() {
        WebElement element = null;

        try {
            element = findElementWithKey("ProductDetail_ReviewLikeButton");
        } catch (Exception ex) {
        }

        if (element != null) {
            hoverElement(element);
            waitByMilliSeconds(500);
            element.click();
            logger.info("ProductDetail_ReviewLikeButton elementine tıklandı.");


            WebDriverWait webDriverWait = new WebDriverWait(driver, 60);
            WebElement webElement = webDriverWait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Teşekkür Ederiz.')]")));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                    webElement);

            Assert.assertTrue("Textinde Teşekkür Ederiz. geçen bir element ekranda görülmedi.", isDisplayed(webElement));

            logger.info("Expected Contains: Teşekkür Ederiz.");
            logger.info("Screen : " + webElement.getText());



        }else{
            logger.info("Hiçbir değerlendirme bulunamadı.");
        }



    }
}
