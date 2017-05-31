
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author EnriqueWood
 */
public class SearchEngine {

    private int maxResults;
    private final String[][] itemArray;
    private final String[] itemNameArray;

    public SearchEngine(String[][] itemArray, String[] itemNameArray) {
        this.itemArray = itemArray;
        this.itemNameArray = itemNameArray;
    }

    void setMaxResults(int results) {
        this.maxResults = results;
    }

    /**
     * Simple algorithm to search the items, it's based on two criteria:
     *  1.- It looks for the amount of coincidences in each match
     *  2.- If the two matches have the same amount of coincidences,
     *      it looks for the score given in each case.
     * 
     * @item Item to compare 
     * @return Sorted array of indexes with the solution 
    */
    public int[] search(String item) {
        int index = Arrays.asList(itemNameArray).indexOf(item);
        String[] selected = itemArray[index];
        ArrayList<Element> a = new ArrayList<>();
        for (int i = 0; i < itemArray.length; i++) {
            if (i != index) {
                Element e = new Element(i);
                for (int j = 0; j < selected.length; j++) {
                    if (selected[j].equals(itemArray[i][j])) {
                        e.score += selected.length - j;
                        e.coincidences += 1;
                    }
                }
                a.add(e);
            }
        }
        Collections.sort(a);
        int[] indexRes = new int[maxResults];
        for (int i = 0; i < maxResults; i++) {
            indexRes[i] = a.get(i).index;

            /**
             * ** NOTE: if you want to see the results more easily, you should
             * definitely uncomment this code and see the amount of coincidences
             * and score produced by each match
             */
//            System.out.println((i+1)+TestRedd.toJSONFormat(indexRes[i])
//                    +"Coincidences:"+a.get(i).coincidences+", Score: "+a.get(i).score);
             
        }

        return indexRes;
    }

    //Inner class, intentionally not using OOP encapsulation!
    private class Element implements Comparable<Element> {

        int index;
        int coincidences;
        int score;

        public Element(int index) {
            this.index = index;
        }

        @Override
        public int compareTo(Element o) {
            //If they have same number of coincidences, the score will define the match
            if (o.coincidences == this.coincidences) {
                return Integer.compare(o.score, this.score);
            }
            return Integer.compare(o.coincidences, this.coincidences);
        }
    }
}
