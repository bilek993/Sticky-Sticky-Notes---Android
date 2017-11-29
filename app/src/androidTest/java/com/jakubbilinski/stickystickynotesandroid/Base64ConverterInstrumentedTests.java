package com.jakubbilinski.stickystickynotesandroid;

import android.support.test.runner.AndroidJUnit4;

import com.jakubbilinski.stickystickynotesandroid.helpers.Base64Converter;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * Created by jbili on 29.11.2017.
 */

@RunWith(AndroidJUnit4.class)
public class Base64ConverterInstrumentedTests {
    // Encoded strings for tests using this website:
    // https://www.base64encode.org/

    @Test
    public void encodeNumbers() throws UnsupportedEncodingException {
        String value = Base64Converter.encode("0123456789");
        assertEquals(value, "MDEyMzQ1Njc4OQ==");
    }

    @Test
    public void encodeLetters() throws UnsupportedEncodingException {
        String value = Base64Converter.encode("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertEquals(value, "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXpBQkNERUZHSElKS0xNTk9QUVJTVFVWV1hZWg==");
    }

    @Test
    public void encodeMixedWithSpecialSymbols() throws UnsupportedEncodingException {
        String value = Base64Converter.encode("!#$%^&*() a-Z{}:\"|,./");
        assertEquals(value, "ISMkJV4mKigpIGEtWnt9OiJ8LC4v");
    }
}
