package me.taste2plate.app.customer.repo;


import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import javax.inject.Inject;

import me.taste2plate.app.Woocommerce;
import me.taste2plate.app.customer.WcApp;
import me.taste2plate.app.customer.common.WooLiveData;
import me.taste2plate.app.customer.utils.AppUtils;
import me.taste2plate.app.data.api.LogRequest;
import me.taste2plate.app.models.AddressListResponse;
import me.taste2plate.app.models.AppDataResponse;
import me.taste2plate.app.models.CityBrand;
import me.taste2plate.app.models.CityBrandFilter;
import me.taste2plate.app.models.CommonResponse;
import me.taste2plate.app.models.CustomAppData;
import me.taste2plate.app.models.DealsNComboResponse;
import me.taste2plate.app.models.DeliveryBoyResponse;
import me.taste2plate.app.models.DriverLocation;
import me.taste2plate.app.models.GetDeliveryResponse;
import me.taste2plate.app.models.ImageResponse;
import me.taste2plate.app.models.AllOffersResponse;
import me.taste2plate.app.models.LogCreatedResponse;
import me.taste2plate.app.models.Order;
import me.taste2plate.app.models.OrderData;
import me.taste2plate.app.models.ProductByCityBrand;
import me.taste2plate.app.models.ProductCityResponse;
import me.taste2plate.app.models.ShippingTaxResponse;
import me.taste2plate.app.models.StateCityZipCodeResponse;
import me.taste2plate.app.models.TrackerResponse;
import me.taste2plate.app.models.UserAddress;
import me.taste2plate.app.models.ValidateCouponRequest;
import me.taste2plate.app.models.Version;
import me.taste2plate.app.models.address.AddressResponse;
import me.taste2plate.app.models.address.check_zip.CheckAvailabilityResponse;
import me.taste2plate.app.models.address.cities.CityListResponse;
import me.taste2plate.app.models.address.cityzip.CityZipResponse;
import me.taste2plate.app.models.address.states.AllStateResponse;
import me.taste2plate.app.models.custom.BulkOrder;
import me.taste2plate.app.models.custom.DirectFromHome;
import me.taste2plate.app.models.cutoff.CutOffResponse;
import me.taste2plate.app.models.order.updates.OrderUpdateResponse;
import me.taste2plate.app.models.ordervalidation.OrderValidation;
import me.taste2plate.app.models.zip.ZipListResponse;

public class CustomRepository {

    private Context context;
    private String token = "";
    @Inject
    Woocommerce woocommerce;

    @Inject
    public CustomRepository() {
        context = WcApp.Companion.applicationContext();
        token = "Bearer " + new AppUtils(context).getToken();
        Log.e("custom reposiotory", new AppUtils(context).getToken());
    }

    public WooLiveData<CityBrand> brandList() {
        final WooLiveData<CityBrand> callBack = new WooLiveData();
        woocommerce.CustomRepository().getBrands().enqueue(callBack);
        return callBack;
    }


    public WooLiveData<CityBrand> cityList() {
        final WooLiveData<CityBrand> callBack = new WooLiveData();
        woocommerce.CustomRepository().getCity().enqueue(callBack);
        return callBack;
    }


    public WooLiveData<CommonResponse> createBulkOrder(BulkOrder bulkOrder) {
        final WooLiveData<CommonResponse> callBack = new WooLiveData();

        woocommerce.CustomRepository().createBulkOrder(bulkOrder).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<CheckAvailabilityResponse> checkAvailability(int pincode, String vendorId) {
        final WooLiveData<CheckAvailabilityResponse> callBack = new WooLiveData();
        woocommerce.CustomRepository().checkAvailability(pincode, vendorId).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<CutOffResponse> checkCutOfftime(String startCity, String endCity) {
        final WooLiveData<CutOffResponse> callBack = new WooLiveData();
        woocommerce.CustomRepository().checkCutOfftime(startCity, endCity).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<CommonResponse> deleteAddress(String userId, String addressId) {
        final WooLiveData<CommonResponse> callBack = new WooLiveData();
        woocommerce.CustomRepository().deleteAddress(userId, addressId).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<AddressResponse> getUserAddress(String userId) {
        final WooLiveData<AddressResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().getUserAddress(userId).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<CityZipResponse> getAllCityZip() {
        final WooLiveData<CityZipResponse> callBack = new WooLiveData();
        woocommerce.CustomRepository().getAllCityZip().enqueue(callBack);
        return callBack;
    }


    public WooLiveData<AllStateResponse> getAllStates() {
        final WooLiveData<AllStateResponse> callBack = new WooLiveData();
        woocommerce.CustomRepository().getAllStates().enqueue(callBack);
        return callBack;
    }

    public WooLiveData<CommonResponse> saveAddress(
            String userId, String name, String phone, String city, String state, String pincode, String postOffice, String addressLine, String secondary, Double lat, Double lng, String addressType
    ) {
        final WooLiveData<CommonResponse> callBack = new WooLiveData();
        woocommerce.CustomRepository().saveAddress(userId, name, phone, city, state, pincode, postOffice, addressLine, secondary, lat, lng, addressType).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<CommonResponse> editAddress(
            String addressId, String name, String phone, String city, String state, String pincode, String postOffice, String addressLine, String secondary, Double lat, Double lng, String addressType
    ) {
        final WooLiveData<CommonResponse> callBack = new WooLiveData();
        woocommerce.CustomRepository().editAddress(addressId, name, phone, city, state, pincode, postOffice, addressLine, secondary, lat, lng, addressType).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<CommonResponse> cancelOrder(String orderId) {
        final WooLiveData<CommonResponse> callBack = new WooLiveData();
        woocommerce.CustomRepository().cancelOrder(orderId).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<OrderUpdateResponse> getOrderUpdates(String orderId) {
        final WooLiveData<OrderUpdateResponse> callBack = new WooLiveData();
        woocommerce.CustomRepository().getOrderUpdates(orderId).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<AppDataResponse> fetchAppData() {
        final WooLiveData<AppDataResponse> callBack = new WooLiveData<>();
        woocommerce.CustomRepository().fetchAppData().enqueue(callBack);
        return callBack;
    }


    public WooLiveData<LogCreatedResponse> addLog(LogRequest request) {
        final WooLiveData<LogCreatedResponse> callBack = new WooLiveData<>();
        woocommerce.CustomRepository().addLog(request).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<TrackerResponse> install(
            String tracker_record, String clickId,
            String security_token,
            String gaid,
            String sub4) {
        final WooLiveData<TrackerResponse> callBack = new WooLiveData<>();
        woocommerce.CustomRepository().install(tracker_record, clickId, security_token, gaid, sub4).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<TrackerResponse> purchased(
            String
                    tracker_record,
            String clickId,
            String security_token,
            String gaid,
            String sub4,
            String goal_name,
            String sale_amount) {
        final WooLiveData<TrackerResponse> callBack = new WooLiveData<>();
        woocommerce.CustomRepository().purchased(tracker_record, clickId, security_token, gaid, sub4, goal_name, sale_amount).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<ZipListResponse> fetchZipList(String cityId) {
        final WooLiveData<ZipListResponse> callBack = new WooLiveData();
        woocommerce.CustomRepository().fetchZipList(cityId).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<CityListResponse> fetchCityByState(String stateId) {
        final WooLiveData<CityListResponse> callBack = new WooLiveData();
        woocommerce.CustomRepository().fetchCityByState(stateId).enqueue(callBack);
        return callBack;
    }


}
