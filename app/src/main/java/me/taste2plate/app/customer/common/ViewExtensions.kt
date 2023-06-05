package me.taste2plate.app.customer.common

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.fueled.reclaim.ItemsViewAdapter
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.databinding.ListAlertDialogBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun Context.getLayoutInflater() =
    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

val View.layoutInflater
    get() = context.getLayoutInflater()

inline fun <T> View.setupDialog(
    dialogTitle: String,
    list: Array<T>,
    crossinline itemFormatter: (T) -> String,
    crossinline onItemSelected: (T) -> Unit,
    crossinline onShow: () -> Unit,
    crossinline onApplyCoupon: (String) -> Unit,
) {

    val itemAdapter = ItemsViewAdapter()
    val builder = AlertDialog.Builder(context, R.style.AlertDialogStyle)

    val binding = ListAlertDialogBinding.inflate(layoutInflater)
    binding.run {
        options.adapter = itemAdapter
        title = dialogTitle
    }
    builder.setView(binding.root)
    val dialog: AlertDialog = builder.create()

    binding.applyCoupon.setOnClickListener {
        val couponCode = binding.edtCouponCode.text.toString()

        if (couponCode.isEmpty())
            Toast.makeText(context, "Enter coupon code", Toast.LENGTH_SHORT).show()
        else {
            onApplyCoupon(couponCode)
            binding.edtCouponCode.setText("")
        dialog.dismiss()
        }

    }

    val items = list.map { choice ->
        GeneralChoiceAdapterItem(itemFormatter(choice)) {
            onItemSelected(list[it])
            dialog.dismiss()
        }
    }

    itemAdapter.replaceItems(items, useDiffCheck = true)

    setOnClickListener {
        onShow.invoke()
        dialog.show()
    }
}

class BindListItem<in R : RecyclerView.ViewHolder, out T : ViewDataBinding>(
    private val view: View
) : ReadOnlyProperty<R, T> {

    private var value: T? = null

    override operator fun getValue(thisRef: R, property: KProperty<*>): T {
        if (value == null) {
            value = DataBindingUtil.bind<T>(view)
        }
        return value!!
    }
}
