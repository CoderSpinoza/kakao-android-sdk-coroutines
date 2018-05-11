package com.kakao.sdk.network

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
        System.out.println(query)
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