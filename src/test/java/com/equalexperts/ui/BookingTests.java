package com.equalexperts.ui;

import com.equalexperts.BaseTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class BookingTests extends BaseTests {
    private int numRows;
    @DataProvider
    public Object[][] getData() {
        return new Object[][]{
                {"GarthUI" +generateRandomNumber(5000, 1000), "Combrinck", "1000", "true", "2020-10-01", "2020-10-30"}};
    }

    @Test(dataProvider = "getData")
    public void createValidBooking(String name, String surname, String price, String deposit, String checkin, String checkout) throws InterruptedException {
        numRows = bookingPage.getTableSize();
        assertEquals(bookingPage.getTableHeader(), Arrays.asList("Firstname","Surname", "Price", "Deposit", "Check-in","Check-out"), "Incorrect heading displayed");
        bookingPage.makeBooking(name, surname, price, deposit, checkin, checkout);
        List<String> bookingDetails =  bookingPage.getLastBookingEntry(numRows + 1);
        assertEquals( bookingDetails , Arrays.asList(name, surname, price, deposit, checkin, checkout, ""), "Expected entry not found.");
    }

    @Test(dependsOnMethods = "createValidBooking")
    public void deleteBooking() {
        bookingPage.deleteBooking(numRows + 1);
        assertEquals(numRows, bookingPage.getTableSize() -1, "");
    }
}
