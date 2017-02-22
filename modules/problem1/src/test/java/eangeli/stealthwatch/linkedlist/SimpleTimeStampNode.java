package eangeli.stealthwatch.linkedlist;

/**
 * Simple implementation of a singly linked list.
 * Just stores the System.nanoTime()
 * Created by eangeli on 2/20/2017.
 */
public class SimpleTimeStampNode implements SinglyLinkedList {
    long time = System.nanoTime();
    SinglyLinkedList next = null;

    public long getTime(){
        return time;
    }

    @Override
    public SinglyLinkedList next() {
        return next;
    }

    @Override
    public void setNext(SinglyLinkedList next) {
        this.next = next;
    }
}
