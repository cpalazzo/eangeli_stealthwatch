package eangeli.stealthwatch;

import eangeli.stealthwatch.linkedlist.SinglyLinkedList;

/**
 * Finds the middle node of a list.
 * While not needed for the problem, it is used
 * for testing to make sure the expected instance is removed.
 * Created by eangeli on 2/20/2017.
 */
public class MiddleFinder extends ListMap{

    public SinglyLinkedList middle(SinglyLinkedList head){
        int midPoint = -1;
        SinglyLinkedList middle = null;
        mapTheList(head);
        if(map.size()>0){
            midPoint = (int)Math.ceil(map.size()/2.0d);
            if(map.containsKey(midPoint)){
                 middle = map.get(midPoint);
            }
        }
        return middle;
    }
}
