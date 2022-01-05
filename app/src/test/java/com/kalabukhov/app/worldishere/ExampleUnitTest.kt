package com.kalabukhov.app.worldishere

import com.kalabukhov.app.worldishere.ui.EmailValidator
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.com"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email"))
    }

    @Test
    fun emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email..com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("@email.com"))
    }

    @Test
    fun emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(""))
    }

    @Test
    fun emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(null))
    }

    @Test
    fun emailValidator_Equals() {
        assertEquals(EmailValidator.isValidEmail("123"), "123")
    }

    @Test
    fun emailValidator_NotEquals() {
        assertNotEquals(EmailValidator.isValidEmail(""), "123")
    }

    @Test
    fun emailValidator_ArrayEquals() {
        val arr: Array<EmailValidator> = arrayOf()
        assertArrayEquals(arr, arr)
    }

    @Test
    fun emailValidator_Null() {
        val emailValidator: EmailValidator? = null
        assertNull(emailValidator)
    }

    @Test
    fun emailValidator_Not_Null() {
        assertNotNull(EmailValidator.isValidEmail(""))
    }

    @Test
    fun emailValidator_Same() {
        assertSame("same", EmailValidator.isValidEmail("same").toString(), "same")
    }
}