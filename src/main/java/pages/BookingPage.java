package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.HelperMethods;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BookingPage extends HelperMethods {
    public WebDriver driver;
    private By firstname = By.id("firstname");
    private By lastname = By.id("lastname");
    private By totalprice = By.id("totalprice");
    private By depositpaid = By.id("depositpaid");
    private By checkin = By.id("checkin");
    private By checkout = By.id("checkout");
    private By bookingRows = By.xpath("//*[@id='bookings']/div");
    private By save = By.xpath("//*[@id=\"form\"]/div/div[7]/input");

    public BookingPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setFirstname(String name) {
        driver.findElement(firstname).sendKeys(name);
    }

    public void setLastname(String surname) {
        driver.findElement(lastname).sendKeys(surname);
    }

    public void setTotalprice(String price) {
        driver.findElement(totalprice).sendKeys(price);
    }

    public void setDepositpaid(String option) {
        findDropDownElement().selectByVisibleText(option);
    }

    public void setCheckin(String checkinDate) {
        driver.findElement(checkin).sendKeys(checkinDate);
    }

    public void setCheckout(String checkoutDate) {
        driver.findElement(checkout).sendKeys(checkoutDate);
    }

    public void saveBooking() {
        driver.findElement(save).click();
    }

    private Select findDropDownElement() {
        return new Select(driver.findElement(depositpaid));
    }

    public int getTableSize() {
        List<WebElement> rows = driver.findElements(bookingRows);
        System.out.println(rows.size() - 1);
        return rows.size();
    }

    public List<String> getTableHeader() {
        List<String> headerText = new ArrayList<>();
        List<WebElement> rows = driver.findElements(bookingRows);
        List<WebElement> columns = rows.get(0).findElements(By.cssSelector("div h3"));
        for (WebElement elem : columns) {
            headerText.add(elem.getText());
        }
        return headerText;
    }


    public List<String> getLastBookingEntry(int rows) throws InterruptedException {
        List<String> bookingDetails = new ArrayList<>();
        System.out.println("******ROWS:" + rows);
        WebDriverWait wait = new WebDriverWait(driver, 180);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#bookings > div:nth-child("+(rows)+") > div")));
        List<WebElement> columns = driver.findElements(By.cssSelector("#bookings > div:nth-child("+(rows)+") > div"));
        System.out.println("******Columns:" + columns.size());
        for (WebElement elem : columns) {
            bookingDetails.add(elem.getText());
        }
        System.out.println(bookingDetails);
        return bookingDetails;
    }

    public void deleteBooking(int numrow) {
        WebDriverWait wait = new WebDriverWait(driver, 180);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#bookings > div:nth-child("+(numrow)+") > div:nth-child(7) > input")));
        driver.findElement(By.cssSelector("#bookings > div:nth-child("+(numrow)+") > div:nth-child(7) > input")).click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void makeBooking(String name, String surname, String price, String deposit, String checkInDate, String checkOutDate) throws InterruptedException {
        setFirstname(name);
        setLastname(surname);
        setTotalprice(price);
        setDepositpaid(deposit);
        setCheckin(checkInDate);
        setCheckout(checkOutDate);

        saveBooking();
    }
}
