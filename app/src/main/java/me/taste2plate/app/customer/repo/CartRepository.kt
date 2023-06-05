package me.taste2plate.app.customer.repo


import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import me.taste2plate.app.Woocommerce
import me.taste2plate.app.customer.WcApp
import me.taste2plate.app.customer.common.CompletionGenericLiveData
import me.taste2plate.app.customer.common.QueryLiveData
import me.taste2plate.app.customer.common.WooLiveData
import me.taste2plate.app.customer.models.CartLineItem
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.models.LineItem
import javax.inject.Inject


open class CartRepository @Inject constructor() {


    @Inject
    lateinit var woocommerce: Woocommerce


    private val cart: CollectionReference = FirebaseFirestore.getInstance()
        .collection("users")
        .document(
            AppUtils(WcApp.applicationContext()).user.id
        )
        .collection("cart")

    fun cart(): QueryLiveData<CartLineItem> {
        return QueryLiveData(cart, CartLineItem::class.java)
    }

    fun deleteItem(cartLineItem: CartLineItem): CompletionGenericLiveData<Void> {
        val completion = CompletionGenericLiveData<Void>()
        cart.document(cartLineItem.getId()).delete().addOnCompleteListener(completion)

        return completion
    }

    fun setQuantity(cartLineItem: CartLineItem, quantity: Int): CompletionGenericLiveData<Void> {
        val completion = CompletionGenericLiveData<Void>()
        cartLineItem.setQuantity(quantity)

        cart.document(cartLineItem.getId()).set(cartLineItem).addOnCompleteListener(completion)

        return completion
    }

    fun addToCart(cartLineItem: CartLineItem): CompletionGenericLiveData<DocumentReference> {
        val completion = CompletionGenericLiveData<DocumentReference>()

        cart.add(cartLineItem).addOnCompleteListener(completion)

        return completion

    }

    fun updateCart(cartLineItem: CartLineItem): CompletionGenericLiveData<Void> {
        val completion = CompletionGenericLiveData<Void>()

        val query = cart.whereEqualTo("id", "" + cartLineItem.id)
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var hasData = false
                var dataDoc: QueryDocumentSnapshot? = null
                for (document in task.result!!) {
                    if (document.data["id"].toString().contentEquals("" + cartLineItem.id)) {
                        dataDoc = document
                        Log.d(TAG, document.id + " => " + document.data.get("id"))
                        hasData = true;
                        break
                    }
                }

                if (hasData && dataDoc != null) {
                    cart.document(dataDoc.id).update("quantity", cartLineItem.quantity)
                        .addOnCompleteListener(completion)
                }
            }
        }
        return completion
    }

    fun cart(context: Context): WooLiveData<Map<String, LineItem>> {
        val callBack = WooLiveData<Map<String, LineItem>>()
        woocommerce.CartRepository(context).cart().enqueue(callBack)

        return callBack
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
}
