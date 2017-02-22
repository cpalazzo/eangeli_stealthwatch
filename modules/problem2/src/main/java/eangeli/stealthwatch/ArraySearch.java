package eangeli.stealthwatch;

import java.util.List;

/**
 * Looks for a given int value in a list of arrays.
 * Assumptions:
 * 1. The target is a valid int, so it cannot be null.
 * 2. The List is an arbitrary length
 * 3. The arrays in the list are expected to be of length 4, but may not be.
 * 4. The elements in the arrays may not be initialized or may be null.
 * 5. The elements in the arrays may not be integers.
 * 6. The array containing the target will be outputted to System.out, or null will be printed.
 * 7. Only the first array containing the target will be printed and returned.
 * 8. The target may exists in an array that has empty, null or uninitialized elements.
 * 9. The elements may any type, but will only match the target if they parse to an int.
 * 10. Even if the array does not have 4 elements, if it contains the target it will be printed and returned.
 * Created by eangeli on 2/21/2017.
 */
public class ArraySearch {

    /**
     * Looks for the given target in the given list of arrays.
     * @param list The list of arrays.
     * @param target The target to find.
     * @return The first array to contain the target, or null if not found.
     */
    public Object[] search(List<Object[]> list, int target){
        Object[] ta = null;
        if(list!=null&&list.size()>0) {
            for (Object[] a : list) {
                if(checkArray(a, target)){
                    ta = a;
                    break;
                }
            }
        }
        print(ta, target);
        return ta;
    }

    protected boolean checkArray(Object[] a, int target){
        boolean result = false;
        if(a!=null&&a.length>0) {
            for (Object o : a) {
                if (o != null) {
                    try {
                            int i = Integer.parseInt("" + o);
                            if (i == target) {
                                result = true;
                                break;
                            }
                    } catch (NumberFormatException e) {
                        //do nothing.
                    }
                }
            }
        }
        return result;
    }

    protected void print(Object[] a, int target){
        String found = "null";
        if(a!=null) {
            found = "[";
            if(a.length>0) {
                for (Object o : a) {
                    found += "" + o + ", ";
                }
                found = found.substring(0, found.lastIndexOf(","));
            }
            found += "]";
        }
        System.out.println("The array with the target ("+target+"):\n\t"+found);
    }
}
