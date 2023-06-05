package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AddUpdateAddress : Serializable {

    @SerializedName("billing_first_name")
    lateinit var firstName: String
    @SerializedName("billing_last_name")
    lateinit var lastName: String
    @SerializedName("billing_company")
    lateinit var company: String
    @SerializedName("billing_address_1")
    lateinit var address1: String
    @SerializedName("billing_address_2")
    lateinit var address2: String
    @SerializedName("billing_city")
    lateinit var city: String
    @SerializedName("billing_state")
    lateinit var state: String
    @SerializedName("billing_postcode")
    lateinit var postcode: String
    @SerializedName("billing_country")
    lateinit var country: String
    @SerializedName("billing_email")
    lateinit var email: String
    @SerializedName("billing_phone")
    lateinit var phone: String
    @SerializedName("billing_address_name")
    lateinit var billingAddressName: String
    @SerializedName("user_id")
    var userId: Int=0
    @SerializedName("address_id")
    lateinit var addressId: String

    override fun toString(): String {
        return (billingAddressName + "\n" + firstName + " " + lastName + "\n"
                + address1 + " " + address2 + "\n"
                + city + ", " + state + " " + postcode + "\n"
                + country + "\n"
                + phone)
    }

    fun getKeyValuePair(): HashMap<String, String> {
        val keyValuePairMap = HashMap<String, String>()
        keyValuePairMap["billing_address_name"] = billingAddressName;
        keyValuePairMap["billing_first_name"] = firstName;
        keyValuePairMap["billing_last_name"] = lastName;
        keyValuePairMap["billing_phone"] = phone;
        keyValuePairMap["billing_email"] = email;
        keyValuePairMap["billing_address_1"] = address1;
        keyValuePairMap["billing_address_2"] = address2;
        keyValuePairMap["billing_city"] = city;
        keyValuePairMap["billing_state"] = state;
        keyValuePairMap["billing_country"] = country;
        keyValuePairMap["billing_postcode"] = postcode;
        keyValuePairMap["billing_company"] = company;
        keyValuePairMap["user_id"] = userId.toString();

        if(addressId.isNotEmpty()){
            keyValuePairMap["address_id"] = addressId;
        }
        return keyValuePairMap;
    }
}
