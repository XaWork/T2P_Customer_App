package me.taste2plate.app.repo

import me.taste2plate.app.data.api.OrderAPI
import me.taste2plate.app.models.LineItem
import me.taste2plate.app.models.Order
import me.taste2plate.app.models.OrderNote
import me.taste2plate.app.models.filters.OrderFilter
import me.taste2plate.app.models.order.MyOrderResponse
import me.taste2plate.app.repo.order.OrderNoteRepository
import me.taste2plate.app.repo.order.RefundRepository
import retrofit2.Call

class OrderRepository(baseUrl: String, consumerKey: String, consumerSecret: String) :
    WooRepository(baseUrl, consumerKey, consumerSecret) {

    private val apiService: OrderAPI

    internal var orderNoteRepository: OrderNoteRepository
    internal var refundRepository: RefundRepository

    init {
        apiService = retrofit.create(OrderAPI::class.java)

        orderNoteRepository = OrderNoteRepository(baseUrl, consumerKey, consumerSecret)
        refundRepository = RefundRepository(baseUrl, consumerKey, consumerSecret)
    }

    fun orders(userId:String): Call<MyOrderResponse> {
        return apiService.history(userId)
    }

    fun createNote(order: Order, note: OrderNote): Call<OrderNote> {
        return orderNoteRepository.create(order, note)
    }

    fun note(order: Order, id: Int): Call<OrderNote> {
        return orderNoteRepository.note(order, id)
    }

    fun notes(order: Order): Call<List<OrderNote>> {
        return orderNoteRepository.notes(order)
    }

    fun deleteNote(order: Order, id: Int): Call<OrderNote> {
        return orderNoteRepository.delete(order, id)
    }

    fun deleteNote(order: Order, id: Int, force: Boolean): Call<OrderNote> {
        return orderNoteRepository.delete(order, id, force)
    }


}
