package org.example.base;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    //this is common to all test cases

    @BeforeTest
    public void setup() {
        System.out.println("Start the test");
    }

    @AfterTest
    public void teardown() {
        System.out.println("Finished the test");
    }

    public String getToken() {
        return null;
    }
}
