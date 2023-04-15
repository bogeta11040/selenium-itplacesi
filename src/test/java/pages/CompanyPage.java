package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CompanyPage extends BasePage {
    public CompanyPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    private static String pageUrl = "https://it-place.si/place.php";
    private static String query = "?podjetje=";

    public void getCompany(String company) {
        System.out.println("Open " +company+ " company page");
        driver.get(pageUrl + query + company);
    }

    public Integer getMinimumSalary() {
        WebElement minSalaryField = driver.findElement(By.cssSelector("h1:nth-child(5) > .badge"));
        return getTextAsInt(minSalaryField, "minimum salary field");
    }

    public Integer getMaximumSalary() {
        WebElement maxSalaryField = driver.findElement(By.cssSelector("h1:nth-child(8) > .badge"));
        return getTextAsInt(maxSalaryField, "maximum salary field");
    }

    public Integer getAverageSalary() {
        WebElement avgSalaryField = driver.findElement(By.cssSelector(".bg-warning"));
        return getTextAsInt(avgSalaryField, "average salary field");
    }

    public Integer calculateMinSalary() {
        return findMin(getAllSalaries());
    }

    public Integer calculateMaxSalary() {
        return findMax(getAllSalaries());
    }

    public Integer calculateAvgSalary() {
        return findAvg(getAllSalaries());
    }

    public List<Integer> getAllSalaries() {
        List<WebElement> salaryFields = driver.findElements(By.xpath("//tr/td[3]"));
        Integer salary;
        List<Integer> salaries = new ArrayList<Integer>();
        for (WebElement salaryField : salaryFields) {
            salary = getTextAsInt(salaryField, "salary field");
            salaries.add(salary);
        }
        return salaries;
    }
}
