package com.kakao.sdk.network

import android.net.Uri
import com.google.gson.Gson
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author kevin.kang. Created on 2018. 3. 21..
 */
class KakaoConverterFactory : Converter.Factory() {
    override fun stringConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, String>? {
        if (type is ParameterizedType) {
            if (type.rawType == Map::class.java) {
                return Converter { value: Map<String, String> ->
                    Gson().toJson(value)
                }
            }
        }
        if (type == Uri::class.java) {
            return Converter { uri: Uri -> uri.query }
        }
        return null
    }
}