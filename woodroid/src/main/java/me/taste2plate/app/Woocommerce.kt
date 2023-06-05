package me.taste2plate.app

import android.content.Context
import me.taste2plate.app.data.ApiVersion
import me.taste2plate.app.repo.*
import me.taste2plate.app.repo.order.OrderNoteRepository
import me.taste2plate.app.repo.order.RefundRepository
import me.taste2plate.app.repo.product.*


class Woocommerce(
    siteUrl: String,
    apiVerion: ApiVersion,
    consumerKey: String,
    consumerSecret: String
) {
    companion object {
        val API_V1 = ApiVersion.API_VERSION1
        val API_V2 = ApiVersion.API_VERSION2
        val API_V3 = ApiVersion.API_VERSION3
    }

    private val orderNoteRepository: OrderNoteRepository
    private val refundRepository: RefundRepository
    private val attributeRepository: AttributeRepository
    private val attributeTermRepository: AttributeTermRepository
    private val categoryRepository: CategoryRepository
    private val shippingClassRepository: ShippingClassRepository
    private val tagRepository: TagRepository
    private val variationRepository: VariationRepository
    private val couponRepository: CouponRepository
    private val customerRepository: CustomerRepository
    private val orderRepository: OrderRepository
    private val productRepository: ProductRepository
    private val reviewRepository: ReviewRepository
    private val reportsRepository: ReportsRepository
    private val cartRepository: CartRepository
    private val paymentGatewayRepository: PaymentGatewayRepository
    private val settingsRepository: SettingsRepository
    private val shippingMethodRepository: ShippingMethodRepository
    private val customRepository: CustomRepository


    init {
//        val customerBaseUrl = "$siteUrl/wp-json/"
//        val cartBaseUrl = "$siteUrl/wp-json/wc/v2/"

        orderNoteRepository = OrderNoteRepository(Constants.baseUrl, consumerKey, consumerSecret)
        refundRepository = RefundRepository(Constants.baseUrl, consumerKey, consumerSecret)
        attributeRepository = AttributeRepository(Constants.baseUrl, consumerKey, consumerSecret)
        attributeTermRepository = AttributeTermRepository(Constants.baseUrl, consumerKey, consumerSecret)
        categoryRepository = CategoryRepository(Constants.baseUrl, consumerKey, consumerSecret)
        shippingClassRepository = ShippingClassRepository(Constants.baseUrl, consumerKey, consumerSecret)
        tagRepository = TagRepository(Constants.baseUrl, consumerKey, consumerSecret)
        variationRepository = VariationRepository(Constants.baseUrl, consumerKey, consumerSecret)
        couponRepository = CouponRepository(Constants.baseUrl, consumerKey, consumerSecret)
        customerRepository = CustomerRepository(Constants.baseUrl, consumerKey, consumerSecret)
        orderRepository = OrderRepository(Constants.baseUrl, consumerKey, consumerSecret)
        productRepository = ProductRepository(Constants.baseUrl, consumerKey, consumerSecret)
        reportsRepository = ReportsRepository(Constants.baseUrl, consumerKey, consumerSecret)
        cartRepository = CartRepository(Constants.baseUrl, consumerKey, consumerSecret)
        reviewRepository = ReviewRepository(Constants.baseUrl, consumerKey, consumerSecret)
        paymentGatewayRepository = PaymentGatewayRepository(Constants.baseUrl, consumerKey, consumerSecret)
        settingsRepository = SettingsRepository(Constants.baseUrl, consumerKey, consumerSecret)
        shippingMethodRepository = ShippingMethodRepository(Constants.baseUrl, consumerKey, consumerSecret)
        customRepository = CustomRepository(Constants.baseUrl, consumerKey, consumerSecret)
    }

    fun OrderNoteRepository(): OrderNoteRepository {
        return orderNoteRepository
    }

    fun RefundRepository(): RefundRepository {
        return refundRepository
    }

    fun AttributeRepository(): AttributeRepository {
        return attributeRepository
    }

    fun AttributeTermRepository(): AttributeTermRepository {
        return attributeTermRepository
    }

    fun CategoryRepository(): CategoryRepository {
        return categoryRepository
    }

    fun ShippingClassRepository(): ShippingClassRepository {
        return shippingClassRepository
    }

    fun TagRepository(): TagRepository {
        return tagRepository
    }

    fun VariationRepository(): VariationRepository {
        return variationRepository
    }

    fun CouponRepository(): CouponRepository {
        return couponRepository
    }

    fun CustomerRepository(): CustomerRepository {
        return customerRepository
    }

    fun OrderRepository(): OrderRepository {
        return orderRepository
    }

    fun ProductRepository(): ProductRepository {
        return productRepository
    }

    fun ReviewRepository(): ReviewRepository {
        return reviewRepository
    }

    fun ReportsRepository(): ReportsRepository {
        return reportsRepository
    }

    fun PaymentGatewayRepository(): PaymentGatewayRepository {
        return paymentGatewayRepository
    }

    fun SettingsRepository(): SettingsRepository {
        return settingsRepository
    }

    fun ShippingMethodRepository(): ShippingMethodRepository {
        return shippingMethodRepository
    }

    fun CartRepository(context: Context): CartRepository {
        // cartRepository.turnOnCookies(context)
        return cartRepository
    }

    fun CustomRepository(): CustomRepository {
        return customRepository;
    }


    class Builder {
        private lateinit var siteUrl: String
        private lateinit var apiVersion: ApiVersion
        private lateinit var consumerKey: String
        private lateinit var consumerSecret: String

        fun setSiteUrl(siteUrl: String): Builder {
            this.siteUrl = siteUrl
            return this
        }

        fun setApiVersion(apiVerion: ApiVersion): Builder {
            this.apiVersion = apiVerion
            return this
        }

        fun setConsumerKey(consumerKey: String): Builder {
            this.consumerKey = consumerKey
            return this
        }

        fun setConsumerSecret(consumerSecret: String): Builder {
            this.consumerSecret = consumerSecret
            return this
        }


        fun build(): Woocommerce {
            return Woocommerce(siteUrl, apiVersion, consumerKey, consumerSecret)
        }
    }


}

object Constants{
    const val baseUrl = "https://webapi.tastes2plate.com/"
}