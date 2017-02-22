package eangeli.stealthwatch;

import eangeli.stealthwatch.linkedlist.SimpleTimeStampNode;
import eangeli.stealthwatch.linkedlist.SinglyLinkedList;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Tests for removing the middle node of a linked list.
 * It checks list varying length from 0(null),1,2,3,4,5 and then 15 lists of random size from 6 to 10,000,006;
 * It is capped at 10,000,006 due to memory constraints of maintaining a linked list of such size,
 * but given enough memory (and time), the size of the list does not matter.
 * Created by eangeli on 2/20/2017.
 */
public class MiddleRemoverTests {

    /**
     * Builds a simple list of given length.
     * @param length The length of the list to build, if 0, returns null.
     * @return The head of the list.
     */
    protected SinglyLinkedList buildList(int length){
        SinglyLinkedList head = null;
        SinglyLinkedList current;
        int total = length;
        int tenth = (int)Math.ceil(total/10.0d);
        int percent = 1;
        System.out.print("\tbuilding list\n\t\t...");
        if(length>0) {
            head = new SimpleTimeStampNode();
            current = head;
            length--;
            while (length > 0) {
                if((total-length)==tenth){
                    System.out.print(""+(10*percent)+"%...");//this is just an indicator that it is still working.
                    //do not assume it is that accurate.
                    percent++;
                    total = length;
                }
                current.setNext(new SimpleTimeStampNode());
                current = current.next();
                length--;
            }
        }
        System.out.println("\n\tfinished building list for the test.");
        return head;
    }

    /**
     * Checks if the given node is in the list with the given head.
     * @param current the head of the list.
     * @param node the node to check for
     * @return true if the node is in the list, otherwise false.
     */
    protected boolean contains(SinglyLinkedList current, SinglyLinkedList node){
        boolean found = false;
        while(current != null){
            if(current==node){
                found = true;
                break;
            }
            current = current.next();
        }
        return found;
    }

    protected void printList(SinglyLinkedList current){
        System.out.println("current list:"+(current==null?"\n\tnull":""));
        while(current!=null){
            System.out.println("\t"+((SimpleTimeStampNode)current).getTime());
            current = current.next();
        }
    }

    /**
     * Generate a 15 tests of random list size.
     * @return the data for test runs.
     */
    @DataProvider(name = "data")
    public Object[][] data(){
        Object[][] data = new Object[15][1];

        data[0][0] = 1;
        data[1][0] = 2;
        data[2][0] = 3;
        data[3][0] = 4;
        data[4][0] = 5;
        for(int i=5; i<data.length; i++){
            int d = (int)(Math.random()*10000000)+6;//Integer.MAX_VALUE/50);
            data[i][0] = d;
        }
        return data;
    }

    /**
     * Uses a data provider to generate 15 runs.
     * runs 1-5 test lengths of 1,2,3,4,5
     * runs 6-15 are a random length between 6 and 10,000,006
     * @param length
     */
    @Test(groups = {"unit"}, dataProvider = "data")
    public void test(int length){
        System.out.println("starting test: " + length);
        try {
            SinglyLinkedList head = buildList(length);
            System.out.println("\tscanning for middle for validation.");
            SinglyLinkedList middle = new MiddleFinder().middle(head);
            System.out.println("\t\tmiddle node: " + (middle!=null?((SimpleTimeStampNode)middle).getTime():"null"));
            assertTrue(contains(head, middle));
            System.out.println("\tremoving middle node now.");
            MiddleRemover mr = new MiddleRemover();
            head = mr.remove(head);
            System.out.println("\tchecking for removal of middle node.");
            assertFalse(contains(head, middle));
            int midPoint = (int) Math.ceil(((double) length) / 2.0d);
            System.out.println("\tremoved node at point: " + midPoint);
            assertEquals(midPoint, mr.getMidPoint());
        }catch(Throwable t){
            System.out.println("test failed: " + length);
            throw t;
        }
        System.out.println("test passed: " + length);
    }

    @Test(groups = {"unit"})
    public void NullList(){
        SinglyLinkedList head = buildList(0);
        assertNull(head);
        MiddleRemover mr = new MiddleRemover();
        head = mr.remove(head);
        assertNull(head);
        assertEquals(-1, mr.getMidPoint());
    }
}
