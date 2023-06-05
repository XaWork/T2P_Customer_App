package me.taste2plate.app.customer.repo


import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.JsonElement
import me.taste2plate.app.Woocommerce
import me.taste2plate.app.customer.WcApp
import me.taste2plate.app.customer.common.CompletionGenericLiveData
import me.taste2plate.app.customer.common.QueryLiveData
import me.taste2plate.app.customer.common.WooLiveData
import me.taste2plate.app.customer.models.CartLineItem
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.models.LineItem
import me.taste2plate.app.models.ShippingMethods
import javax.inject.Inject


open class CheckoutRepository @Inject
constructor() {

    @Inject
    lateinit var woocommerce: Woocommerce

    private var cart: CollectionReference = FirebaseFirestore.getInstance()
        .collection("users")
        .document(AppUtils(WcApp.applicationContext()).user.id.toString())
        .collection("cart")

    fun cart(): QueryLiveData<CartLineItem> {
        return QueryLiveData(cart, CartLineItem::class.java)
    }

    fun deleteItems(): CompletionGenericLiveData<Void> {
        val completion = CompletionGenericLiveData<Void>()
        deleteCartItems().addOnCompleteListener(completion)
        return completion
    }


    private fun deleteCartItems(): Task<Void> {
        return cart.firestore.runTransaction {
            cart.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        cart.document(document.id).delete()
                    }

                } else {
                }
            }

            null
        }
    }

    fun cart(context: Context): WooLiveData<Map<String, LineItem>> {
        val callBack = WooLiveData<Map<String, LineItem>>()
        woocommerce.CartRepository(context).cart().enqueue(callBack)

        return callBack
    }

    fun shippingCost(id: Int): WooLiveData<JsonElement> {
        val callBack = WooLiveData<JsonElement>()
        woocommerce.ShippingMethodRepository().getShippingMethod(id).enqueue(callBack)

        return callBack
    }
}
