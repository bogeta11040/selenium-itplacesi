package pages;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class BasePage {
    WebDriver driver;

    int wait = Integer.parseInt(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("WAIT"));

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getNavItemText(String pos, String log){
        String cssSelector = ".navbar-nav > :nth-child(" +pos+ ") a";
        WebElement element = driver.findElement(By.cssSelector(cssSelector));
        return getText(element, log);
    }

    public Boolean verifyBaseElements(){
        WebElement navbar = driver.findElement(By.cssSelector(".navbar"));
        WebElement footer = driver.findElement(By.tagName("footer"));
        if(verifyElementPresent(navbar, "navigation") && verifyElementPresent(footer, "footer")) {
            return true;
        }
        return false;
    }
    public Boolean verifyElementPresent(WebElement element, String log){
        System.out.println("Checking for presence of '" +log+ "' element...");
        if(element.isDisplayed()){
            return true;
        }
        return false;
    }

    public void clickElement(WebElement element, String log){
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(wait));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));

        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();

        System.out.println("Clicked "+log+" element.");
    }

    public void clickElement(WebElement element){
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(wait));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));

        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

    public void typeText(WebElement element,String text, String log){
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(wait));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));

        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();

        element.clear();
        element.sendKeys(text);

        System.out.println("Typed: '"+text+"' in "+log+" element.");
    }

    public String getText(WebElement element, String log){
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(wait));
        webDriverWait.until(ExpectedConditions.visibilityOf(element));

        System.out.println("Getting text from "+log+" element.");

        return element.getText().trim();
    }

    public Integer getTextAsInt(WebElement element, String log){
        String text = getText(element, log).replaceAll("[^\\d.]", "");
        return Integer.parseInt(text);
    }

    public static Integer findMin(List<Integer> list)
    {
        // check if list is empty
        if (list == null || list.size() == 0) {
            return Integer.MAX_VALUE;
        }

        return Collections.min(list);
    }

    public static Integer findMax(List<Integer> list)
    {

        // check if list is empty
        if (list == null || list.size() == 0) {
            return Integer.MIN_VALUE;
        }
        return Collections.max(list);
    }

    public static Integer findAvg(List<Integer> list)
    {
        Integer sum = 0;
        // check if list is empty
        if (list == null || list.size() == 0) {
            return Integer.MIN_VALUE;
        }
        for (Integer el : list) {
            sum += el;
        }
        return sum / list.size();
    }

    public void selectByValue(WebElement element, String value){
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(wait));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));

        Select select = new Select(element);
        select.selectByValue(value);

        System.out.println("Selecting '"+value+"' from dropdown.");
    }

    public void takeScreenshot(String name) throws IOException {
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/screenshots/"+name+".png"));
    }

    public void reportScreenshot(String name, String allureName) throws IOException {
        takeScreenshot(name);
        Path content = Paths.get("src/screenshots/"+name+".png");
        try(InputStream is = Files.newInputStream(content)){
            Allure.addAttachment(allureName, is);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void scrollToWebElement(WebElement element){
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(wait));
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);
    }
}
