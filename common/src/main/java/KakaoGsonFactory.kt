package com.kakao.sdk.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.FieldNamingPolicy
import com.google.gson.JsonSerializer
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive

import java.lang.reflect.Type

/**
 * @author kevin.kang. Created on 18/02/2019..
 */
object KakaoGsonFactory {

    private val kakaoExclusionStrategy = object : ExclusionStrategy {
        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
            return false
        }

        override fun shouldSkipField(f: FieldAttributes?): Boolean {
            val exclude = f!!.getAnnotation(Exclude::class.java)
            return exclude != null
        }
    }

    private val internalBuilder = GsonBuilder()
            .registerTypeHierarchyAdapter(IntEnum::class.java, object : JsonSerializer<IntEnum> {
                override fun serialize(src: IntEnum?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
                    return JsonPrimitive(src?.getValue())
                }
            })
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .addSerializationExclusionStrategy(kakaoExclusionStrategy)
            .addDeserializationExclusionStrategy(kakaoExclusionStrategy)

    val base: Gson = internalBuilder.create()

    val pretty: Gson = internalBuilder.setPrettyPrinting().create()
}