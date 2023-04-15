package pages;

import com.google.gson.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import java.io.*;

public class SubmitSalaryPage extends BasePage {

    public SubmitSalaryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),'VNESI PLAČO')]")
    WebElement addSalaryBtn;

    @FindBy(xpath= "//a[contains(text(),'| Prijavite se s GitHub računom')]")
    WebElement githubBtn;

    @FindBy(xpath= "//a[contains(text(),'| Prijavite se s LinkedIn računom')]")
    WebElement linkedInBtn;

    private static String pageUrl = "https://it-place.si/vnos.php";
    private Boolean alertPresent = false;
    private String alertMessage;

    public Boolean verifyBaseElements(){
        driver.get(pageUrl);
        return super.verifyBaseElements();
    }

    public void clickAddSalaryBtn() {
        clickElement(addSalaryBtn);
    }

    public void chooseGithubAuth() {
        clickElement(githubBtn);
    }

    public void chooseLinkedinAuth() {
        clickElement(linkedInBtn);
    }

    public void authorizeWithGithub() throws FileNotFoundException {
        WebElement githubUserField = driver.findElement(By.cssSelector("#login_field"));
        WebElement githubPassField = driver.findElement(By.cssSelector("#password"));
        WebElement githubLoginBtn = driver.findElement(By.name("commit"));

        verifyCredentials("github");

        typeText(githubUserField, getGithubUsername(), "user field");
        typeText(githubPassField, getGithubPassword(), "password field");
        clickElement(githubLoginBtn);
        Assert.assertTrue(driver.getCurrentUrl().contains("vnos.php"));
        System.out.println("Authorized with GitHub.");
    }

    public void authorizeWithLinkedin() throws FileNotFoundException {
        WebElement linkedinEmailField = driver.findElement(By.id("username"));
        WebElement linkedinPassField = driver.findElement(By.id("password"));
        WebElement linkedinLoginBtn = driver.findElement(By.xpath("//button[@type='submit']"));

        verifyCredentials("linkedin");

        typeText(linkedinEmailField, getLinkedinUsername(), "email field");
        typeText(linkedinPassField, getLinkedinPassword(), "password field");
        clickElement(linkedinLoginBtn);
        Assert.assertTrue(driver.getCurrentUrl().contains("vnos.php"));
        System.out.println("Authorized with LinkedIn.");
    }

    public void submitSalary(String company, String location, String position, String seniority, String net, String gross) {
        WebElement companyField = driver.findElement(By.id("naziv"));
        WebElement locationField = driver.findElement(By.id("lokacija"));
        WebElement positionField = driver.findElement(By.id("rmesto"));
        WebElement seniorityField = driver.findElement(By.id("senioritet"));
        WebElement netField = driver.findElement(By.id("neto"));
        WebElement grossField = driver.findElement(By.id("bruto"));
        WebElement submitBtn = driver.findElement(By.name("submit"));

        selectByValue(companyField, company);
        selectByValue(locationField, location);
        selectByValue(positionField, position);
        scrollToWebElement(seniorityField);
        selectByValue(seniorityField, seniority);
        scrollToWebElement(netField);
        typeText(netField, net, "net pay field");
        typeText(grossField, gross, "gross pay field");
        clickElement(submitBtn, "the submit button");
        // handle alert
        handleAlerts();
        System.out.println("Submitting form...");
    }

    public Boolean formSubmitted() {
        if (driver.findElements(By.id("forma")).size() == 0) {
            System.out.print("Form is submitted!");
            return true;
        }
        System.out.println("Form not submitted!");
        return false;
    }

    public void handleAlerts()
    {
        try
        {
            Alert alert = driver.switchTo().alert();
            alertMessage = driver.switchTo().alert().getText();
            alertPresent = true;
            alert.accept();
        }
        catch (NoAlertPresentException Ex)
        {
        }
    }

    public Boolean isAlertPresent() {
        System.out.println("Verifying if alert is present...");
        return alertPresent;
    }

    public String alertText() {
        System.out.println("Getting alert text...");
        return alertMessage;
    }

    public void verifyCredentials(String linkedinOrGithub) throws FileNotFoundException {
        linkedinOrGithub.toLowerCase();
        if (linkedinOrGithub == "linkedin") {
            if (getLinkedinUsername() == "" || getLinkedinUsername() == "ENTER_HERE" || getLinkedinPassword() == "" || getLinkedinPassword() == "ENTER_HERE") {
                Assert.fail("Test aborted because credentials are not valid. Please edit the credentials.json file.");
            }
        } else {
            if (getGithubUsername() == "" || getGithubUsername() == "ENTER_HERE" || getGithubPassword() == "" || getGithubPassword() == "ENTER_HERE") {
                Assert.fail("Test aborted because credentials are not valid. Please edit the credentials.json file.");
            }
        }
    }

    public String getGithubUsername() throws FileNotFoundException {
        return getCredentials("github").getAsJsonArray().get(0).getAsJsonObject().get("username").getAsString();
    }

    public String getGithubPassword() throws FileNotFoundException {
        return getCredentials("github").getAsJsonArray().get(0).getAsJsonObject().get("password").getAsString();
    }

    public String getLinkedinUsername() throws FileNotFoundException {
        return getCredentials("linkedin").getAsJsonArray().get(0).getAsJsonObject().get("email").getAsString();
    }

    public String getLinkedinPassword() throws FileNotFoundException {
        return getCredentials("linkedin").getAsJsonArray().get(0).getAsJsonObject().get("password").getAsString();
    }

    private JsonElement getCredentials(String linkedinOrGithub) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(new FileReader("src/test/test_data/credentials.json"), JsonElement.class);
        JsonElement credentials;
        if (linkedinOrGithub.toLowerCase() == "linkedin") {
            credentials = element.getAsJsonObject().get("LinkedinAccounts");
        } else {
            credentials = element.getAsJsonObject().get("GithubAccounts");
        }
        return credentials;
    }
}
