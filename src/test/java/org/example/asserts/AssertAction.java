package org.example.asserts;

import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AssertAction {

    //Response Verify
    public void verifyResponseBody(String actual, String expected, String description) {
        assertEquals(actual, expected, description);
    }
    public void verifyResponseBody(int actual, int expected, String description) {
        assertEquals(actual, expected, description);
    }


//Status code Verify
    public void verifyStatusCode(Response response, int expected) {
        assertEquals(response, expected);
    }


// String Key Verify - AssertJ
    public void verifyStringKey(String expectedKey, String actualKey) {
        assertThat(expectedKey).isNotNull();
        assertThat(expectedKey).isNotBlank();
        assertThat(expectedKey).isEqualTo(actualKey);
    }
    public void verifyStringKeyNotNull(String expectedKey) {
        assertThat(expectedKey).isNotNull();
    }
    public void verifyIntegerKeyNotnull(Integer expectedKey) {
        assertThat(expectedKey).isNotNull();
    }


// True Verify - TestNG
    public void verifyTrue(boolean expectedKey) {
        assertTrue(expectedKey);
    }
}
