package me.taste2plate.app.models.custom

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class DirectFromHome : Serializable {

    @SerializedName("from-complatename")
    lateinit var fromName: String

    @SerializedName("from-email")
    lateinit var fromEmail: String

    @SerializedName("from-mobile")
    lateinit var fromPhone: String

    @SerializedName("from_city")
    lateinit var fromCity: String

    @SerializedName("from-address")
    lateinit var fromAddress: String

    @SerializedName("whom-complatename")
    lateinit var whomName: String

    @SerializedName("whome-email")
    lateinit var whomEmail: String

    @SerializedName("whom-mobile")
    lateinit var whomPhone: String

    @SerializedName("to_city")
    lateinit var whomCity: String

    @SerializedName("whom-address")
    lateinit var whomAddress: String

    @SerializedName("WeightinKg")
    lateinit var productWeight: String

    @SerializedName("picuptime")
    lateinit var picuptime: String

    @SerializedName("deleverytime")
    lateinit var deleverytime: String


     fun getKeyValuePair(): HashMap<String, String> {
        val keyValuePairMap = HashMap<String,String>()
         keyValuePairMap["from-complatename"] = fromName;
         keyValuePairMap["from-email"] = fromEmail;
         keyValuePairMap["from-mobile"] = fromPhone;
         keyValuePairMap["from_city"] = fromCity;
         keyValuePairMap["from-address"] = fromAddress;

         keyValuePairMap["whom-complatename"] = whomName;
         keyValuePairMap["whome-email"] = whomEmail;
         keyValuePairMap["whom-mobile"] = whomPhone;
         keyValuePairMap["to_city"] = whomCity;
         keyValuePairMap["whom-address"] = whomAddress;

         keyValuePairMap["WeightinKg"] = productWeight;
         keyValuePairMap["picuptime"] = picuptime;
         keyValuePairMap["deleverytime"] = deleverytime;

         return keyValuePairMap;
    }

}

