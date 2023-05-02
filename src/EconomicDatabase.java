import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @version 05.01.2023
 * @author 26prakash
 * @author jcochran
 */

public class EconomicDatabase {
    private ArrayList<Country> database;
    private String[] categories;
    private double[] indices;
    private CountryComparator myComparator;
    private String primarySort, secondarySort;
    private boolean asc;
    private String[] primaryTerms = {"ID: Country ID", "NA: Country Name", "RE: Region", "EF: Economic Freedom Index", "IN: Specific Index"};
    private String[] secondaryTerms = {"JE: Judicial Effectiveness","GI: Government Integrity","TB: Tax Burden","GS: Government Spending",
    "FH: Fiscal Health","BF: Business Freedom","LF: Labor Freedom","MF: Monetary Freedom","TF: Trade Freedom","IF: Investment Freedom","FF: Financial Freedom"};

    /**
     * Simple Constructor for Economic Database class.
     */
    public EconomicDatabase()   {
        database = new ArrayList<>();
        categories = new String[16];
        indices = new double[12];
        primarySort = "";
        secondarySort = "";
    }

    /**
     * populateDatabase method of the EconomicDatabase class
     * Adds all of the Countries to the database arraylist
     */
    public void populateDatabase() {
        String filename = "IEF_2023_data.txt";
        try {
            boolean firstLine = true;
            Scanner in = new Scanner(new File(filename));
            String[] line;
            while(in.hasNext()) {
                if (firstLine) {
                    categories = in.nextLine().split("\t");
                    firstLine = false;
                }
                else {
                    line = in.nextLine().split("\t");
                    database.add(new Country(Integer.parseInt(line[0]), line[1], line[2], line[3]));
                    indices[0] = Double.parseDouble(line[4]);
                    indices[1] = Double.parseDouble(line[5]);
                    indices[2] = Double.parseDouble(line[6]);
                    indices[3] = Double.parseDouble(line[7]);
                    indices[4] = Double.parseDouble(line[8]);
                    indices[5] = Double.parseDouble(line[9]);
                    indices[6] = Double.parseDouble(line[10]);
                    indices[7] = Double.parseDouble(line[11]);
                    indices[8] = Double.parseDouble(line[12]);
                    indices[9] = Double.parseDouble(line[13]);
                    indices[10] = Double.parseDouble(line[14]);
                    indices[11] = Double.parseDouble(line[15]);
                    database.get(database.size()-1).populateIndices(indices);
                }
                //System.out.println(database.size());
            }
            in.close();
        }
        catch(Exception e)  {
            e.printStackTrace();
        }
    }

    /**
     * getSearchCriteria method of the EconomicDatabase class
     * @return true or false, depending on whether the program should be quit or not.
     */
    public boolean getSearchCriteria()  {
        String userIn;
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("+ Menu of search terms to use for sorting countries +");
            for(String term : primaryTerms)
                System.out.println("\t" + term);
            System.out.print("Enter your preferred sort by term or 'Q' to quit: ");
            userIn = in.nextLine();
            if(userIn.equalsIgnoreCase("q")) {
                in.close();
                return false;
            }
            primarySort = userIn.toUpperCase();
            if(!primarySort.equals("IN")) {
                // prompt for asc/dsc
                System.out.print("Enter 'A' to sort in ascending order or 'D' to sort in descending order: ");
                userIn = in.nextLine();
                asc = (userIn.equalsIgnoreCase("a")) ? true : false;
                for(String term : primaryTerms) {
                    if(term.indexOf(primarySort) != -1) {
                        primarySort = term.substring(4);    // for map retrieval
                        return true;
                    }
                }
                System.out.println("Invalid search term entered, try again.");
            }
            else {
                System.out.println("Search term selected specific index, please select the index value to display:");
                for(String term : secondaryTerms)
                    System.out.println("\t"+term);
                System.out.print("Enter your preferred sort by index or 'Q' to quit: ");
                userIn = in.nextLine();
                if(userIn.equalsIgnoreCase("q")) {
                    return false;
                }
                secondarySort = userIn.toUpperCase();
                System.out.print("Enter 'A' to sort in ascending order or 'D' to sort in descending order: ");
                userIn = in.nextLine();
                asc = (userIn.equalsIgnoreCase("a")) ? true : false;
                for(String term : secondaryTerms) {
                    if(term.indexOf(secondarySort) != -1) {
                        primarySort = "Specific Index";
                        secondarySort = term.substring(4); // for map retrieval
                        return true;
                    }
                }
                System.out.println("Invalid search term entered, try again.");
            }
        }
    }


    private void merge(ArrayList<Country> database, int p, int q, int r, CountryComparator comp) {
        int n1 = q - p + 1;
        int n2 = r - q;

        ArrayList<Country> L = new ArrayList<>();
        ArrayList<Country> M = new ArrayList<>();

        // fill the left and right array
        for (int i = 0; i < n1; i++) {
            L.add(i, database.get(p + i));
        }
        for (int j = 0; j < n2; j++) {
            M.add(j, database.get(q + 1 + j));
        }



        // Maintain current index of sub-arrays and main array
        int i, j, k;
        i = 0;
        j = 0;
        k = p;

        // Until we reach either end of either L or M, pick larger among
        // elements L and M and place them in the correct position at A[p..r]
        // for sorting in descending
        // use if(L[i] >= <[j])
        while (i < n1 && j < n2) {
            if (comp.compare(L.get(i), M.get(j)) < 0) {
                database.set(k, L.get(i));
                i++;
            } else {
                database.set(k, M.get(j));
                j++;
            }
            k++;
        }

        // When we run out of elements in either L or M,
        // pick up the remaining elements and put in A[p..r]
        while (i < n1) {
            database.set(k, L.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            database.set(k, M.get(j));
            j++;
            k++;
        }
    }

    private void mergeSort(ArrayList<Country> database, int left, int right, CountryComparator comp) {
        if (left < right) {

            // m is the point where the array is divided into two sub arrays
            int mid = (left + right) / 2;

            // recursive call to each sub arrays
            mergeSort(database, left, mid, comp);
            mergeSort(database, mid + 1, right, comp);

            // Merge the sorted sub arrays
            merge(database, left, mid, right, comp);

        }
    }
    /**
     * sortDB method of the EconomicDatabase class
     * Sorts the database arraylist
     * SORT: UTILIZES MERGESORT IN THE mergeSort AND merge private methods
     * The mergesort is recursive.
     * Lines 131-198
     * Resources used to write sort: https://www.programiz.com/java-programming/examples/merge-sort
     */
    public void sortDB()    {
        printDatabase();
        CountryComparator comp = new CountryComparator(asc, primarySort, secondarySort);
        mergeSort(database, 0, database.size()-1, comp);
    }

    /**
     * printDatabase method of the EconomicDatabase class
     * prints out the database arraylist
     */
    public void printDatabase() {
        for(int i = 0; i < database.size(); i++) {
            database.get(i).setSecondaryCategory(secondarySort);
            System.out.println(database.get(i));
        }
    }

    /**
     * main method of the EconomicDatabase class
     * @param args command line arguments, if needed
     */
    public static void main(String[] args) {
        EconomicDatabase database = new EconomicDatabase();
        database.populateDatabase();
        System.out.println("*** Welcome to the World Economic Freedom Database ***");
        while (database.getSearchCriteria()) {
            database.sortDB();
            database.printDatabase();
        }
        System.out.println("Exiting the program");
    }
}
