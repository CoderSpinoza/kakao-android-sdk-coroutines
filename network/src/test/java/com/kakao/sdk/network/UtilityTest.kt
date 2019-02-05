package com.kakao.sdk.network

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * @author kevin.kang. Created on 2018. 5. 2..
 */
class UtilityTest {
    @MethodSource("paramsProvider")
    @ParameterizedTest fun buildQuery(params: Map<String, String>) {
        val query = Utility.buildQuery(params)
        assertNotNull(query)
    }

    @Nested
    inner class ParseQuery {
        @Test fun withNull() {
            val params = Utility.parseQuery(null)
            assertEquals(0, params.size)
        }

        @Test fun withEmpty() {
            val params = Utility.parseQuery("")
        }

        @Test fun withOneParam() {
            val params = Utility.parseQuery("key1=value1")
            assertEquals(1, params.size)
        }

        @Test fun withTwoParams() {
            val params = Utility.parseQuery("key1=value1&key2=value2")
            assertEquals(2, params.size)
            assertEquals("value1", params["key1"])
            assertEquals("value2", params["key2"])
        }
    }

    companion object {
        @Suppress("unused")
        @JvmStatic fun paramsProvider(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(mapOf<String, String>()),
                    Arguments.of(mapOf(Pair("key1", "value1"))),
                    Arguments.of(mapOf(Pair("key1", "value1"), Pair("key2", "value2")))
            )
        }
    }
}