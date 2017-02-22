package eangeli.stealthwatch;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Tests for finding the target int in a list of arrays of elements.
 * It checks list varying length from null, 0,1,4,5 and then 14 lists of random size from 6 to 10,000,006;
 * It is capped at 10,000,006 due to memory constraints of maintaining a list of such size,
 * Created by eangeli on 2/20/2017.
 */
public class ArraySearchTests {

    /**
     * Builds a simple list of given length.
     *
     * @param length The length of the list to build, if 0, returns null.
     * @return The head of the list.
     */
    protected Object[] buildList(int length, int target, boolean targetFindable, boolean invalidElements) {
        Object[] data = new Object[4];
        List<Object[]> list = length < 0 ? null : new ArrayList<>();
        int total = length;
        int tenth = (int) Math.ceil(total / 10.0d);
        int percent = 1;
        System.out.print("\tbuilding data\n\t\t...");
        if (length > 0) {
            int findableIndex = -1;
            if (targetFindable) {
                findableIndex = (int) (Math.random() * length);
                if(findableIndex==length){
                    findableIndex--;
                }
            }
            while (length > 0) {
                if ((total - length) == tenth) {
                    if ((total - length) == tenth) {
                        System.out.print("" + (10 * percent) + "%...");//this is just an indicator that it is still working.
                        //do not assume it is accurate.
                        percent++;
                        total = length;
                    }
                }
                int al = invalidElements ? ((int) (Math.random() * 8)) : 4;
                Object[] elements = new Object[al];
                for (int i = 0; i < elements.length; i++) {
                    int random = (int) (Math.random() * Integer.MAX_VALUE);
                    while (random == target) {
                        random = (int) (Math.random() * Integer.MAX_VALUE);
                    }
                    elements[i] = invalidElements ? (Math.random() * 20 < 4) ? randomElement() : random : random;
                }
                length--;
                if (length == findableIndex) {
                    int ei = (int)(Math.random()*elements.length);
                    if(elements.length==0){
                        findableIndex--;
                    }else {
                        if(ei==elements.length){
                            ei--;
                        }
                        elements[ei] = target;
                        data[3] = elements;
                    }
                }
                list.add(elements);
            }
        }
        data[0] = list;
        data[1] = target;
        data[2] = data[3]!=null;
        System.out.println("\n\tfinished building data for the test.");
        return data;
    }


    protected Object randomElement(){
        return Math.random()*50<5?null:"cheese";
    }

    protected void printArray(Object[] oa){
        String array = "\t\t[";
        if(oa!=null&&oa.length>0) {
            for (Object o : oa) {
                array += ""+o+", ";
            }
            array = array.substring(0, array.lastIndexOf(","));
        }
        array += "]";
        System.out.println(array);
    }

    protected void printData(List<Object[]> list, int target, Object[] expected){
        System.out.println("test data:\n\tlist:");
        for(Object[] oa:list){
            printArray(oa);
        }
        System.out.println("\ttarget: " + target);
        printArray(expected);
    }
    /**
     * Generate a 15 tests of random list size.
     *
     * @return the data for test runs.
     */
    @DataProvider(name = "data")
    public Object[][] data() {
        Object[][] data = new Object[20][];

        data[0] = buildList(-1, (int) (Math.random() * Integer.MAX_VALUE), false, false);
        data[1] = buildList(0, (int) (Math.random() * Integer.MAX_VALUE), false, false);
        data[2] = buildList(1, (int) (Math.random() * Integer.MAX_VALUE), true, false);
        data[3] = buildList(4, (int) (Math.random() * Integer.MAX_VALUE), false, false);
        data[4] = buildList(4, (int) (Math.random() * Integer.MAX_VALUE), true, false);
        data[5] = buildList(5, (int) (Math.random() * Integer.MAX_VALUE), true, true);
        for (int i = 6; i < data.length; i++) {
            data[i] = buildList((int) (Math.random() * 1000000+6), (int) (Math.random() * Integer.MAX_VALUE), Math.random() * 10 < 7, true);
        }
        return data;
    }

    @Test(groups = {"unit", "p2"}, dataProvider = "data")
    public void test(List<Object[]> list, int target, boolean doesExist, Object[] expected) {
        System.out.println("starting test: " + target + " - exists=" + doesExist);
        try {
            Object[] actual = new ArraySearch().search(list, target);
            if(doesExist){
                if(doesExist){
                    assertNotNull(actual);
                    assertEquals(actual, expected);
                }else{
                    assertNull(actual);
                }
            }
        } catch (Throwable t) {
            printData(list, target, expected);
            System.out.println("test failed: " + target + " - exists=" + doesExist);
            throw t;
        }
        System.out.println("test passed: " + target + " - exists=" + doesExist);
    }
}
