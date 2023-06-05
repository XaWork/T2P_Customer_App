package me.taste2plate.app.customer.events;


public class DeleteFromWishlistEvent {

    int position;

    public DeleteFromWishlistEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}


