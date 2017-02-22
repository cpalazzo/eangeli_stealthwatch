package eangeli.stealthwatch;

import eangeli.stealthwatch.linkedlist.SinglyLinkedList;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple class that maps a linked list into a map based on the nodes index in the list.
 * Created by eangeli on 2/20/2017.
 */
public class ListMap {
    Map<Integer, SinglyLinkedList> map = new HashMap<>();
    /**
     * Map the singly-linked list into a map.
     * @param current
     */
    public void mapTheList(SinglyLinkedList current){
        int count = 0;
        while(current!=null){
            count++;
            map.put(count, current);
            current = current.next();
        }
    }
}
