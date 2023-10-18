package me.taste2plate.app.customer.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.taste2plate.app.customer.common.CompletionGenericLiveData;
import me.taste2plate.app.customer.common.QueryLiveData;
import me.taste2plate.app.customer.common.WooLiveData;
import me.taste2plate.app.customer.models.CartLineItem;
import me.taste2plate.app.customer.repo.CartRepository;
import me.taste2plate.app.customer.repo.CategoryRepository;
import me.taste2plate.app.customer.repo.CustomRepository;
import me.taste2plate.app.customer.repo.OrderRepository;
import me.taste2plate.app.customer.repo.ProductRepository;
import me.taste2plate.app.models.Category;
import me.taste2plate.app.models.LineItem;
import me.taste2plate.app.models.ProductReview;
import me.taste2plate.app.models.CityBrand;
import me.taste2plate.app.models.CommonResponse;
import me.taste2plate.app.models.SubCategories;
import me.taste2plate.app.models.address.AddressResponse;
import me.taste2plate.app.models.address.check_zip.CheckAvailabilityResponse;
import me.taste2plate.app.models.cart.CartItemResponse;
import me.taste2plate.app.models.custom.BulkOrder;
import me.taste2plate.app.models.filters.ProductCategoryFilter;
import me.taste2plate.app.models.home.HomePageModel;
import me.taste2plate.app.models.home.HomePageResponse;
import me.taste2plate.app.models.home.SliderProductsResponse;
import me.taste2plate.app.models.newproducts.NewProductResponse;
import me.taste2plate.app.models.wishlist.DeleteWishlistItemResponse;
import me.taste2plate.app.models.wishlist.WishlistItemResponse;


public final class ProductViewModel extends ViewModel {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CategoryRepository categoryRepository;
    private final CustomRepository customRepository;

    private final MutableLiveData<Integer> selectedProductId = new MutableLiveData();

    @Inject
    ProductViewModel(ProductRepository productRepository,
                     OrderRepository orderRepository,
                     CartRepository cartRepository,
                     CategoryRepository categoryRepository,
                     CustomRepository customRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.categoryRepository = categoryRepository;
        this.customRepository = customRepository;

    }

    public WooLiveData<AddressResponse> fetchUserAddress(String userId){
        return productRepository.getUserAddress(userId);
    }

    public MutableLiveData<Integer> getSelectedProduct() {
        return selectedProductId;
    }

    public void selectProduct(int productId) {
        selectedProductId.setValue(productId);
    }

    public WooLiveData<NewProductResponse> productsBySubcategory(String id, String taste) {
        return productRepository.productsBySubcategory(id, taste);
    }


    public WooLiveData<NewProductResponse> productByFilter(Map<String, String> filters, String taste) {
        return productRepository.productByFilter(filters, taste);
    }

    public WooLiveData<NewProductResponse> productsByCity(String id, String taste) {
        return productRepository.productsByCity(id, taste);
    }

    public WooLiveData<NewProductResponse> productsByBrand(String id, String taste) {
        return productRepository.productsByBrand(id, taste);
    }

    public WooLiveData<SliderProductsResponse> productsBySlider(String name, String taste) {
        return productRepository.productsBySlider(name, taste);
    }


    public WooLiveData<NewProductResponse> productsByCuisine(String id, String taste) {
        return productRepository.productsByCuisine(id, taste);
    }


    public WooLiveData<NewProductResponse> productByQuery(String query) {
        return productRepository.productByQuery(query);
    }

    public WooLiveData<WishlistItemResponse> getWishlist(String userId) {
        return productRepository.getWishlist(userId);
    }


    public WooLiveData<DeleteWishlistItemResponse> deleteFromWishlist(String userId, String productId) {
        return productRepository.deleteFromWishlist(userId, productId);
    }

    public WooLiveData<CommonResponse> addToWishlist(String userId, String productId) {
        return productRepository.addToWishlist(userId, productId);
    }

    public WooLiveData<CartItemResponse> getCart(String userId, String cityId, String zipCode) {
        return productRepository.cartProducts(userId, cityId, zipCode);
    }

    public WooLiveData<CommonResponse> addToCart(int quantity, String pId, String userId) {
        return productRepository.addToCart(userId, pId, quantity);
    }

    public WooLiveData<CommonResponse> updateCart(int quantity, String pId, String userId) {
        return productRepository.updateCart(userId, pId, quantity);
    }

    public QueryLiveData<CartLineItem> cart() {
        return cartRepository.cart();
    }

    public CompletionGenericLiveData<Void> updateCart(CartLineItem cartLineItem) {
        return cartRepository.updateCart(cartLineItem);
    }


    public WooLiveData<CommonResponse> deleteItem(String productId, String userId) {
        return productRepository.deleteItem(productId, userId);
    }

    public CompletionGenericLiveData<Void> deleteAllCartItems() {
        return cartRepository.deleteItems();
    }

    public CompletionGenericLiveData<Void> setQuantity(CartLineItem cartLineItem, int quantity) {
        return cartRepository.setQuantity(cartLineItem, quantity);
    }

    public WooLiveData<Map<String, LineItem>> cart(Context context) {
        return cartRepository.cart(context);
    }


    public WooLiveData<NewProductResponse> productById(String productId) {
        return productRepository.productById(productId);
    }


    public WooLiveData<CheckAvailabilityResponse> checkAvailability(int pinCode, String vendorId) {
        return customRepository.checkAvailability(pinCode, vendorId);
    }


    public WooLiveData<List<ProductReview>> reviews(int productId) {
        return productRepository.reviews(productId);
    }


    public WooLiveData<Category> categories(ProductCategoryFilter productCategoryFilter, String taste) {
        return categoryRepository.categories(productCategoryFilter, taste);
    }

    public WooLiveData<HomePageResponse> homePageData(String cityId, String taste) {
        return categoryRepository.homePageData(cityId, taste);
    }

    public WooLiveData<HomePageModel> homePageData() {
        return categoryRepository.getHomePage();
    }

    public WooLiveData<SubCategories> subCategories(String parentId) {
        return categoryRepository.subCategories(parentId);
    }

    public WooLiveData<CityBrand> getCity() {
        return customRepository.cityList();
    }

    public WooLiveData<CityBrand> getBrandList() {
        return customRepository.brandList();
    }


    public WooLiveData<CommonResponse> createBulkOrder(BulkOrder bulkOrder) {
        return customRepository.createBulkOrder(bulkOrder);
    }
}