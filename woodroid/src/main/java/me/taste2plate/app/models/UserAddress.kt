package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserAddress : Serializable {

    var address_id: Int = 0
    @SerializedName("address_details")
    var userAddress: AddressDetails? = null

    class AddressDetails :Serializable{
        @SerializedName("first_name")
        var firstName: String?=""
        @SerializedName("last_name")
         var lastName: String?=""
        @SerializedName("company")
         var company: String?=""
        @SerializedName("address_1")
         var address1: String?=""
        @SerializedName("address_2")
         var address2: String?=""
        @SerializedName("city")
         var city: String?=""
        @SerializedName("state")
         var state: String?=""
        @SerializedName("postcode")
         var postcode: String?=""
        @SerializedName("country")
         var country: String?=""
        @SerializedName("email")
         var email: String?=""
        @SerializedName("phone")
         var phone: String?=""
        @SerializedName("address_name")
         var billingAddressName: String?=""
        @SerializedName("user_id")
        var userId: String? = null
        @SerializedName("address_id")
        var addressId: String?=""

        override fun toString(): String {
            return (firstName + " " + lastName + "\n\n"
                    + address1 + " " + address2 + "\n"
                    + city + ", " + state + " " + postcode + "\n"
                    + country + "\n"
                    + phone)
        }

        fun getKeyValuePair(): HashMap<String, String> {
            val keyValuePairMap = HashMap<String, String>()
            keyValuePairMap["address_name"] = billingAddressName!!
            keyValuePairMap["first_name"] = firstName!!
            keyValuePairMap["last_name"] = lastName!!
            keyValuePairMap["phone"] = phone!!
            keyValuePairMap["email"] = email!!
            keyValuePairMap["address_1"] = address1!!
            keyValuePairMap["address_2"] = address2!!
            keyValuePairMap["city"] = city!!
            keyValuePairMap["state"] = state!!
            keyValuePairMap["country"] = country!!
            keyValuePairMap["postcode"] = postcode!!
            keyValuePairMap["company"] = company!!
            keyValuePairMap["user_id"] = userId.toString()

            if (addressId?.isNotEmpty()!!) {
                keyValuePairMap["address_id"] = addressId!!;
            }
            return keyValuePairMap;
        }

        fun getKeyValuePairAddUpdate(): HashMap<String, String> {
            val keyValuePairMap = HashMap<String, String>()
            keyValuePairMap["address_name"] = billingAddressName!!
            keyValuePairMap["first_name"] = firstName!!
            keyValuePairMap["last_name"] = lastName!!
            keyValuePairMap["phone"] = phone!!
            keyValuePairMap["email"] = email!!
            keyValuePairMap["address_1"] = address1!!
            keyValuePairMap["address_2"] = address2!!
            keyValuePairMap["city"] = city!!
            keyValuePairMap["state"] = state!!
            keyValuePairMap["country"] = country!!
            keyValuePairMap["postcode"] = postcode!!
            keyValuePairMap["company"] = company!!
            keyValuePairMap["user_id"] = userId.toString();

            if(addressId?.isNotEmpty()!!){
                keyValuePairMap["address_id"] = addressId!!;
            }
            return keyValuePairMap;
        }
    }

}
