package me.taste2plate.app.customer.ui

import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.common.BaseActivity
import me.taste2plate.app.customer.ui.state.ProgressDialogFragment

abstract class WooDroidActivity<T : ViewModel> : BaseActivity() {


    var hideCart: Boolean = false
    var hideWishlist: Boolean = false
    abstract var viewModel: T
    private lateinit var progressDialog: ProgressDialogFragment


    fun showLoading() {
        showLoading(getString(R.string.please_wait), getString(R.string.loading))
    }

    fun showLoading(title: String, message: String) {
        val manager = supportFragmentManager
        progressDialog = ProgressDialogFragment.newInstance(title, message)
        progressDialog.isCancelable = false
        progressDialog.show(manager, "progress")
    }

    fun stopShowingLoading() {
        if(this::progressDialog.isInitialized) {
            progressDialog.dismiss()
        }
    }

    fun toast(text: String) {
        Toast.makeText(baseContext, text, Toast.LENGTH_LONG).show()
    }


    var tvCartCounter: TextView? = null
    var tvWishlistCounter: TextView? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.product, menu)

        val item = menu.findItem(R.id.menu_cart)
        item.isVisible = false

        val itemWishlist = menu.findItem(R.id.menu_wishlist)
        itemWishlist.isVisible = false

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cart -> {

                true
            }

            R.id.menu_wishlist -> {

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
