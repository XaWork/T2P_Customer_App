package me.taste2plate.app.customer.viewmodels;

import androidx.lifecycle.ViewModel;

import me.taste2plate.app.customer.common.WooLiveData;
import me.taste2plate.app.customer.repo.CustomRepository;
import me.taste2plate.app.customer.repo.CustomerRepository;
import me.taste2plate.app.models.AddressListResponse;
import me.taste2plate.app.models.CommonResponse;
import me.taste2plate.app.models.Customer;
import me.taste2plate.app.models.DashboardItemResponse;
import me.taste2plate.app.models.DealsNComboResponse;
import me.taste2plate.app.models.DeliveryBoyResponse;
import me.taste2plate.app.models.DriverLocation;
import me.taste2plate.app.models.MakakhanaDetailsResponse;
import me.taste2plate.app.models.AllOffersResponse;
import me.taste2plate.app.models.StateCityZipCodeResponse;
import me.taste2plate.app.models.UserAddress;
import me.taste2plate.app.models.address.AddressResponse;
import me.taste2plate.app.models.address.cities.CityListResponse;
import me.taste2plate.app.models.address.cityzip.CityZipResponse;
import me.taste2plate.app.models.address.states.AllStateResponse;
import me.taste2plate.app.models.filters.CustomerFilter;
import me.taste2plate.app.models.ordervalidation.OrderValidation;
import me.taste2plate.app.models.zip.ZipListResponse;

import javax.inject.Inject;
import java.util.List;


public final class CustomerViewModel extends ViewModel {
    private final CustomerRepository customerRepository;
    private final CustomRepository customRepository;

    @Inject
    CustomerViewModel(CustomerRepository customerRepository,   CustomRepository customRepository) {
        this.customerRepository = customerRepository;
        this.customRepository=customRepository;

    }


    public WooLiveData<CommonResponse> update(String id, String fullName, String mobile) {
        return customerRepository.update(id, fullName, mobile);
    }


    public WooLiveData<CommonResponse> deleteAddress(String userId,String addressId) {
        return customRepository.deleteAddress(userId,addressId);
    }

    public WooLiveData<AddressResponse> getAddressList(String userId) {
        return customRepository.getUserAddress(userId);
    }

    public WooLiveData<CityZipResponse> getAllCityZip() {
        return customRepository.getAllCityZip();
    }


    public WooLiveData<AllStateResponse> getAllStates() {
        return customRepository.getAllStates();
    }

    public WooLiveData<CommonResponse> saveAddress(String userId, String name, String phone, String city,String state, String pincode, String postOffice, String addressLine, String secondary,  Double lat, Double lng, String addressType) {
        return customRepository.saveAddress(userId, name, phone, city, state, pincode, postOffice, addressLine, secondary, lat, lng, addressType);
    }

    public WooLiveData<CommonResponse> editAddress(String addressId, String name, String phone, String city,String state, String pincode, String postOffice, String addressLine, String secondary,  Double lat, Double lng, String addressType) {
        return customRepository.editAddress(addressId, name, phone, city, state, pincode, postOffice, addressLine, secondary, lat, lng, addressType);
    }

    public WooLiveData<CommonResponse> updateAddress(String userId, String name, String phone, String city,String state, String pincode, String postOffice, String addressLine, String secondary,  Double lat, Double lng, String addressType) {
        return customRepository.saveAddress(userId, name, phone, city, state, pincode, postOffice, addressLine, secondary, lat, lng, addressType);
    }

    public WooLiveData<ZipListResponse> fetchZipList(String cityId) {
        return customRepository.fetchZipList(cityId);
    }

    public WooLiveData<CityListResponse> fetchCityByState(String stateId) {
        return customRepository.fetchCityByState(stateId);
    }
}