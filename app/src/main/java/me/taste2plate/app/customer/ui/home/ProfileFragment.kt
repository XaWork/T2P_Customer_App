package me.taste2plate.app.customer.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.ui.customer.ProfileActivity
import me.taste2plate.app.customer.ui.onboarding.OnBoardActivity
import me.taste2plate.app.customer.ui.order.MyOrdersActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.UserViewModel


class ProfileFragment : androidx.fragment.app.Fragment() {


    lateinit var viewModel: UserViewModel
    val TAG = "ProfileFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HomeActivity).getViewModel(UserViewModel::class.java)
        /*llMyProfile.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(activity, ProfileActivity::class.java))
            } else {
                startActivity(Intent(activity, OnBoardActivity::class.java))
            }
        }*/

        llLogout.setOnClickListener {
            viewModel.logout()
            AppUtils(activity).logOut()
            startActivity(Intent(activity, OnBoardActivity::class.java))
        }

        llMyOrders.setOnClickListener {
            startActivity(Intent(activity, MyOrdersActivity::class.java))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

}
