package com.kakao.sdk.common

import com.google.gson.*
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

    val normal = Gson()

    val pretty = GsonBuilder().setPrettyPrinting().create()

    val inherited = GsonBuilder().addSerializationExclusionStrategy(kakaoExclusionStrategy)
            .registerTypeHierarchyAdapter(IntEnum::class.java, object : JsonSerializer<IntEnum> {
                override fun serialize(src: IntEnum?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
                    return JsonPrimitive(src?.getValue())
                }
            })
            .addDeserializationExclusionStrategy(kakaoExclusionStrategy).create()

    val prettyInherited = GsonBuilder().addSerializationExclusionStrategy(kakaoExclusionStrategy)
            .addDeserializationExclusionStrategy(kakaoExclusionStrategy)
            .setPrettyPrinting().create()
}