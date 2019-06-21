package com.kakao.sdk.user.entity

import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2019-06-21..
 */
enum class AgeRange {
    @SerializedName("15~19")
    TEEN,
    @SerializedName("20~29")
    TWENTIES,
    @SerializedName("30~39")
    THIRTIES,
    @SerializedName("40~49")
    FORTIES,
    @SerializedName("50~59")
    FIFTIES,
    @SerializedName("60~69")
    SIXTIES,
    @SerializedName("70~79")
    SEVENTIES,
    @SerializedName("80~89")
    EIGHTIES,
    @SerializedName("90~")
    NINTIES_AND_ABOVE,

}