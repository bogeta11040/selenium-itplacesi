package tests;

import io.qameta.allure.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.CompanyPage;
import pages.SubmitSalaryPage;

import java.io.IOException;

public class ItplacesiTest extends BaseTest {

    @BeforeMethod
    @Parameters({"BROWSER","BROWSER_VERSION","WAIT","ENV"})
    public void init(String BROWSER, String BROWSER_VERSION, String WAIT, String ENV){
        startApplication(BROWSER,BROWSER_VERSION,Integer.parseInt(WAIT),ENV);
    }

    @AfterMethod
    public void tearDown() throws IOException {
        new BasePage(driver).reportScreenshot("OnFailure_"+System.currentTimeMillis(), "OnFailure");
        quitApplication();
    }

    @Test(enabled = true)
    @Parameters({"Test_name"})
    @Description("User should see proper design")
    @TmsLink("test-1")
    @Issue("Issue")
    @Link("TEST")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("EP001")
    @Feature("FE001")
    @Story("US001")
    public void testPageLayout(String Test_name) throws IOException {
        BasePage basePage = new BasePage(driver);
        SubmitSalaryPage submitSalaryPage = new SubmitSalaryPage(driver);
        Assert.assertEquals(basePage.verifyBaseElements(), true);
        Assert.assertEquals(submitSalaryPage.verifyBaseElements(), true);
    }

    @Test(enabled = true)
    @Parameters({"Test_name"})
    @Description("Navigation links should have correct labels")
    @TmsLink("test-1")
    @Issue("Issue")
    @Link("TEST")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("EP001")
    @Feature("FE001")
    @Story("US001")
    public void testNavLinks(String Test_name) throws IOException {
        BasePage basePage = new BasePage(driver);
        Assert.assertEquals(basePage.getNavItemText("1", "home link"), "ZAČETNA");
        Assert.assertEquals(basePage.getNavItemText("2", "salaries link"), "PLAČE");
        Assert.assertEquals(basePage.getNavItemText("3", "reviews link"), "MNENJA");
        Assert.assertEquals(basePage.getNavItemText("4", "companies link"), "PODJETJA");
    }

    @Test(enabled = true)
    @Parameters({"Test_name"})
    @Description("User should be able to authorize with GitHub")
    @TmsLink("test-1")
    @Issue("Issue")
    @Link("TEST")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("EP001")
    @Feature("FE001")
    @Story("US005")
    public void testAuthWithGithub(String Test_name) throws IOException {
        SubmitSalaryPage submitSalaryPage = new SubmitSalaryPage(driver);
        submitSalaryPage.clickAddSalaryBtn();
        submitSalaryPage.chooseGithubAuth();
        submitSalaryPage.authorizeWithGithub();
    }

    @Test(enabled = true)
    @Parameters({"Test_name"})
    @Description("User should be able to authorize with Linkedin")
    @TmsLink("test-1")
    @Issue("Issue")
    @Link("TEST")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("EP001")
    @Feature("FE001")
    @Story("US005")
    public void testAuthWithLinkedin(String Test_name) throws IOException {
        SubmitSalaryPage submitSalaryPage = new SubmitSalaryPage(driver);
        submitSalaryPage.clickAddSalaryBtn();
        submitSalaryPage.chooseLinkedinAuth();
        submitSalaryPage.authorizeWithLinkedin();
    }

    @Test(enabled = true)
    @Parameters({"Test_name"})
    @Description("User should be able to submit salary")
    @TmsLink("test-1")
    @Issue("Issue")
    @Link("TEST")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("EP001")
    @Feature("FE001")
    @Story("US005")
    public void testSubmitSalaryForm(String Test_name) throws IOException {
        SubmitSalaryPage submitSalaryPage = new SubmitSalaryPage(driver);
        submitSalaryPage.clickAddSalaryBtn();
        submitSalaryPage.chooseGithubAuth();
        submitSalaryPage.authorizeWithGithub();
        submitSalaryPage.submitSalary("Endava", "Kranj", "QA Engineer", "Medior", "1800", "2500");
        Assert.assertEquals(submitSalaryPage.formSubmitted(), true);
    }

    @Test(enabled = true)
    @Parameters({"Test_name"})
    @Description("User should not be able to submit if net amount bigger than gross and alert should be displayed")
    @TmsLink("test-1")
    @Issue("Issue")
    @Link("TEST")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("EP001")
    @Feature("FE001")
    @Story("US005")
    public void testWrongNetAmount(String Test_name) throws IOException {
        SubmitSalaryPage submitSalaryPage = new SubmitSalaryPage(driver);
        submitSalaryPage.clickAddSalaryBtn();
        submitSalaryPage.chooseGithubAuth();
        submitSalaryPage.authorizeWithGithub();
        submitSalaryPage.submitSalary("Sportradar", "Maribor", "Backend Developer", "Senior", "2800", "1500");
        Assert.assertEquals(submitSalaryPage.formSubmitted(), false);
        Assert.assertEquals(submitSalaryPage.isAlertPresent(), true);
        Assert.assertEquals(submitSalaryPage.alertText(), "Napačen vnos!");
    }

    @Test(enabled = true)
    @Parameters({"Test_name"})
    @Description("Correct minimum salary amount should be displayed on the company page")
    @TmsLink("test-1")
    @Issue("Issue")
    @Link("TEST")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("EP001")
    @Feature("FE001")
    @Story("US005")
    public void testCompanyMinSalaryAmount(String Test_name) throws IOException {
        CompanyPage companyPage = new CompanyPage(driver);
        companyPage.getCompany("Iskratel");
        Assert.assertEquals(companyPage.getMinimumSalary(), companyPage.calculateMinSalary());
    }

    @Test(enabled = true)
    @Parameters({"Test_name"})
    @Description("Correct maximum salary amount should be displayed on the company page")
    @TmsLink("test-1")
    @Issue("Issue")
    @Link("TEST")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("EP001")
    @Feature("FE001")
    @Story("US005")
    public void testCompanyMaxSalaryAmount(String Test_name) throws IOException {
        CompanyPage companyPage = new CompanyPage(driver);
        companyPage.getCompany("Iskratel");
        Assert.assertEquals(companyPage.getMaximumSalary(), companyPage.calculateMaxSalary());
    }

    @Test(enabled = true)
    @Parameters({"Test_name"})
    @Description("Correct average salary amount should be displayed on the company page")
    @TmsLink("test-1")
    @Issue("Issue")
    @Link("TEST")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("EP001")
    @Feature("FE001")
    @Story("US005")
    public void testCompanyAvgSalaryAmount(String Test_name) throws IOException {
        CompanyPage companyPage = new CompanyPage(driver);
        companyPage.getCompany("Iskratel");
        Assert.assertEquals(companyPage.getAverageSalary(), companyPage.calculateAvgSalary());
    }
}
