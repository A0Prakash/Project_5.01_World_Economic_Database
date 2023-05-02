import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * @version 05.01.23
 * @author jcochran
 * @author 26prakash
 */
public class Country implements Comparable<Country> {
    private int ID;
    private String countryName, webName;
    private String region;
    private HashMap<String, Double> categoryIndices;
    private double economicFreedomIndex;
    private String secondaryCategory;

    /**
     * Constructor for the Country object
     * Takes name, webName, and region in local variables
     * Creates a hashmap with all double values
     * @param id
     * @param name
     * @param wName
     * @param region
     */
    public Country(int id, String name, String wName, String region)    {
        this.ID = id;
        this.countryName = name;
        this.webName = wName;
        this.region = region;
        this.secondaryCategory = "";    // populate this with a sub-category for display
        categoryIndices = new HashMap<>();
        categoryIndices.put("Property Rights", 0.0);
        categoryIndices.put("Judicial Effectiveness", 0.0);
        categoryIndices.put("Government Integrity", 0.0);
        categoryIndices.put("Tax Burden", 0.0);
        categoryIndices.put("Government Spending", 0.0);
        categoryIndices.put("Fiscal Health", 0.0);
        categoryIndices.put("Business Freedom", 0.0);
        categoryIndices.put("Labor Freedom", 0.0);
        categoryIndices.put("Monetary Freedom", 0.0);
        categoryIndices.put("Trade Freedom", 0.0);
        categoryIndices.put("Investment Freedom", 0.0);
        categoryIndices.put("Financial Freedom", 0.0);
    }

    /**
     * populateIndices method of the Country class.
     * populates the hashmap with the actual values.
     * @param values
     */
    public void populateIndices(double[] values)    {
        int i = 0;
        double sum = 0.;
        for(String key : categoryIndices.keySet())  {
            sum += values[i];
            categoryIndices.put(key, values[i]);
            i++;
        }
        // calculate economic freedom index
        economicFreedomIndex = sum/categoryIndices.size();
    }

    /**
     * setSecondaryCategory method of the Country class
     * sets the secondary category.
     * @param sTerm
     */
    public void setSecondaryCategory(String sTerm)  {
        secondaryCategory = sTerm;
    }

    /**
     * toString method of the Country class
     * Makes a country object a string
     * @return The string in a readable format
     */
    public String toString()    {
        DecimalFormat df = new DecimalFormat("0.00");
        String asString = ID + ": " + countryName + " | Region: " + region +
                " | Economic Freedom Index: " + df.format(economicFreedomIndex);
        if(!secondaryCategory.equals(""))
            asString += " | " + secondaryCategory + ": " + categoryIndices.get(secondaryCategory);
        return asString;
    }

    /**
     * compareTo method of the Country class
     * @param other the object to be compared.
     * @return the comparison made by the two countries
     */
    public int compareTo(Country other) {
        Double myEFI = economicFreedomIndex;
        Double otherEFI = other.economicFreedomIndex;
        return myEFI.compareTo(otherEFI);
    }

    /**
     * getEconomicFreedomIndex method of the Country class
     * @return the economicFreedomIndex
     */
    public double getEconomicFreedomIndex() {
        return economicFreedomIndex;
    }

    /**
     * getIndexValue method of the Country class
     * @param indexName the name of the variable that needs to be retrieved(the key)
     * @return the value set with that indice
     */
    public double getIndexValue(String indexName)   {
        return categoryIndices.get(indexName);
    }

    /**
     * getID method of the Country class
     * @return the ID of the Country
     */
    public int getID() {
        return ID;
    }

    /**
     * getCountryName method of the Country class
     * @return the name of the country
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * getRegion method of the Country class
     * @return the region that the country is in
     */
    public String getRegion() {
        return region;
    }
}
