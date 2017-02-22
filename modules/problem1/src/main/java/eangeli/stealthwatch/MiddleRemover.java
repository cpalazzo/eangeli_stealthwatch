package eangeli.stealthwatch;

import eangeli.stealthwatch.linkedlist.SinglyLinkedList;

/**
 * Assumptions:
 * 1. Accepts a Singly-linked list.
 * 2. The singly-linked list implements the interface class SinglyLinkedList
 * 3. Returns the head of the list, or null if the list was of length 1.
 * 4. The middle is determined to be the length divided by 2, and rounded up such that the mid point of 1 is 1, 2 is 1, 4 is 2, 5 is 3 and so on.
 * 5. The index of the nodes is NOT zero offset, the head node is considered to be at index 1.
 * 6. If the length of the list is less then or equal to 2, the head is changed to head.next().
 * 7. Only loops through the list one time in order to map the nodes.
 * 8. The nodes are indexed into a map corresponding to there index in the list.
 * 9. It assumes the list is not modified during processing, if the list is modified
 *    it makes no guarantees that the correct node is removed.
 * Created by eangeli on 2/20/2017.
 */
public class MiddleRemover extends ListMap{

    int midPoint = -1;

    /**
     * Finds the middle and then removes it by setting the previous nodes next to the middle nodes next.
     * If the head is null, nothing is done and the head is returned.
     * Please note, this does not use recursion to traverse the list. I decided it was easier
     * and a bit faster to only traverse the list once and not have to worry about the recursion returns,
     * especially since there were no memory constraints.
     * @param head The head of the linked list.
     * @return The new head (if it was changed, or the same head that was passed in) of the updated list.
     */
    public SinglyLinkedList remove(SinglyLinkedList head) {
        mapTheList(head);
        if (map.size() > 0) {
            midPoint = (int) Math.ceil(map.size() / 2.0d);
            if (map.containsKey(midPoint - 1)) {
                SinglyLinkedList current = map.get(midPoint - 1);
                if (current.next() != null) {
                    current.setNext(current.next().next());
                }
            }else if((midPoint-1)==0){
                head = head.next();
            }
        }
        return head;
    }

    /**
     * The index of the node at the midpoint.
     * This is used for testing purposes.
     * @return The index of the midpoint.
     */
    public int getMidPoint(){
        return midPoint;
    }
}
