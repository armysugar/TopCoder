package dynamic_programming;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Jun on 11/28/2014.
 *
 * O(n2)
 *
 * Given a sequence of N numbers - A[1] , A[2] , ..., A[N] . Find the length of the longest non-decreasing sequence
 *
 * http://www.topcoder.com/tc?d1=tutorials&d2=dynProg&module=Static
 */
public class LongestNonDecreasingSequence<T extends Comparable> {


    public static void main(String[] args) {
        ArrayList<Integer> ints = new ArrayList<Integer>();
        ints.add(2);
        ints.add(3);
        ints.add(1);
        ints.add(5);
        ints.add(4);
        ints.add(7);
        ArrayList<String> strs = new ArrayList<String>();
        strs.add("ab");
        strs.add("cde");
        strs.add("test");
        strs.add("dc");
        strs.add("check");
        LongestNonDecreasingSequence lnds = new LongestNonDecreasingSequence();
        System.out.println(lnds.longestNonDecreasingSequence(ints));
        System.out.println(lnds.longestNonDecreasingSequence(strs));
    }

    public ArrayList<T> longestNonDecreasingSequence(ArrayList<T> seq){
        if(seq == null){
            return null;
        }
        if(seq.size() < 2){
            return seq;
        }
        ArrayList<Integer> bases = new ArrayList<Integer>();
        bases.add(1); //for the first element/number, the longest sequence ending with it is 1;
        int maxLength = 1;
        int indexMax = 0;
        for(int i = 1; i < seq.size(); i ++){
            bases.add(1);
            for(int j = i -1; j >= 0; j --){ //checking backwards, likely to get less true in the following if -- see some operations
                if( bases.get(j) >= bases.get(i) && seq.get(j).compareTo(seq.get(i)) <= 0){
                    bases.set(i, bases.get(j) + 1);
                    if(bases.get(i) > maxLength){
                        maxLength = bases.get(i);
                        indexMax = i;
                    }
                }
                System.out.println(" in for");
            }
        }
        Stack<T> sequence = new Stack<T>();
        sequence.push(seq.get(indexMax--));
        maxLength--;
        while (maxLength > 0 && indexMax >=0){
            System.out.println("in while");
//            if(seq.get(indexMax).compareTo(sequence.peek()) <= 0 ){
            if(bases.get(indexMax) == maxLength && seq.get(indexMax).compareTo(sequence.peek()) <= 0){
                sequence.push(seq.get(indexMax));
                maxLength--;
            }
            indexMax --;
        }
        ArrayList<T> result = new ArrayList<T>(sequence);
        System.out.println(bases);
        return result;
    }
}
