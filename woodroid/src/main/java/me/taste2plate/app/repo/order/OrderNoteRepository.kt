package me.taste2plate.app.repo.order

import me.taste2plate.app.data.api.OrderNoteAPI
import me.taste2plate.app.models.Order
import me.taste2plate.app.models.OrderNote
import me.taste2plate.app.models.filters.OrderNoteFilter
import me.taste2plate.app.repo.WooRepository
import retrofit2.Call

class OrderNoteRepository(baseUrl: String, consumerKey: String, consumerSecret: String) :
    WooRepository(baseUrl, consumerKey, consumerSecret) {

    private val apiService: OrderNoteAPI = retrofit.create(OrderNoteAPI::class.java)

    fun create(order: Order, note: OrderNote): Call<OrderNote> {
        return apiService.create(order.id, note)
    }

    fun note(order: Order, id: Int): Call<OrderNote> {
        return apiService.view(order.id, id)
    }

    fun notes(order: Order): Call<List<OrderNote>> {
        return apiService.list(order.id)
    }

    fun notes(order: Order, orderNoteFilter: OrderNoteFilter): Call<List<OrderNote>> {
        return apiService.filter(order.id, orderNoteFilter.filters)
    }

    fun delete(order: Order, id: Int): Call<OrderNote> {
        return apiService.delete(order.id, id)
    }

    fun delete(order: Order, id: Int, force: Boolean): Call<OrderNote> {
        return apiService.delete(order.id, id, force)
    }


}
