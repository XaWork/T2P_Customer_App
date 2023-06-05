package me.taste2plate.app.customer.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import javax.inject.Inject;

import me.taste2plate.app.customer.common.CompletionGenericLiveData;
import me.taste2plate.app.customer.common.CompletionLiveData;
import me.taste2plate.app.customer.common.DocumentLiveData;
import me.taste2plate.app.customer.common.WooLiveData;
import me.taste2plate.app.data.api.RegistrationResponse;
import me.taste2plate.app.customer.models.User;
import me.taste2plate.app.customer.repo.CustomRepository;
import me.taste2plate.app.customer.repo.FirebaseUserRepository;
import me.taste2plate.app.models.AppDataResponse;
import me.taste2plate.app.models.CommonResponse;
import me.taste2plate.app.models.CustomAppData;
import me.taste2plate.app.models.Customer;
import me.taste2plate.app.models.LoginUser;
import me.taste2plate.app.models.UpdateMobileResponse;
import me.taste2plate.app.models.ValidateOtpResponse;
import me.taste2plate.app.models.FetchOtpResponse;
import me.taste2plate.app.models.Version;
import me.taste2plate.app.models.filters.CustomerFilter;
import me.taste2plate.app.models.report.LoginResponse;


public final class UserViewModel extends ViewModel {
    private final MutableLiveData<String> id = new MutableLiveData<>();
    private final FirebaseUserRepository firebaseUserRepository;
    private final me.taste2plate.app.customer.repo.CustomerRepository customerRepository;
    private final CustomRepository customRepository;

    @Inject
    UserViewModel(FirebaseUserRepository firebaseUserRepository, me.taste2plate.app.customer.repo.CustomerRepository customerRepository, CustomRepository customRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
        this.customerRepository = customerRepository;
        this.customRepository = customRepository;
    }

    public CompletionGenericLiveData<AuthResult> login(String username, String password) {
        return firebaseUserRepository.login(username, password);
    }

    public void logout() {
        firebaseUserRepository.logout();
    }

    public CompletionGenericLiveData<AuthResult> anonymousSignIn() {
        return firebaseUserRepository.anonymousSignIn();
    }
    public DocumentLiveData<User> user(String user_id) {
        return firebaseUserRepository.user(user_id);
    }

    public DocumentLiveData<User> user() {
        return firebaseUserRepository.user();
    }

    public WooLiveData<RegistrationResponse> signup(String email, String mobile, String token, String referredBy) {
        return customerRepository.signup(email, mobile, token, referredBy);
    }


    public WooLiveData<FetchOtpResponse> fetchOtp(String number, String deviceToken) {
        return customerRepository.fetchOtp(number, deviceToken);
    }

    public WooLiveData<ValidateOtpResponse> verifyOtp(String number, String otp, String deviceToken) {
        return customerRepository.verifyOtp(number, otp, deviceToken);
    }


    public WooLiveData<AppDataResponse> fetchAppData() {
        return customRepository.fetchAppData();
    }

}