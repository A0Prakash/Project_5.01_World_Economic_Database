import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * @TODO complete this header comment
 */
public class Country implements Comparable<Country> {
    private int ID;
    private String countryName, webName;
    private String region;
    private HashMap<String, Double> categoryIndices;
    private double economicFreedomIndex;
    private String secondaryCategory;

    /**
     * TODO complete this constructor comment
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
     * TODO: method comment
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
     * TODO: method comment
     * @param sTerm
     */
    public void setSecondaryCategory(String sTerm)  {
        secondaryCategory = sTerm;
    }

    /**
     * TODO: method comment
     * @return
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
     * TODO: method comment
     * @param other the object to be compared.
     * @return
     */
    public int compareTo(Country other) {
        Double myEFI = economicFreedomIndex;
        Double otherEFI = other.economicFreedomIndex;
        return myEFI.compareTo(otherEFI);
    }

    /**
     * TODO: method comment
     * @return
     */
    public double getEconomicFreedomIndex() {
        return economicFreedomIndex;
    }

    /**
     * TODO: method comment
     * @param indexName
     * @return
     */
    public double getIndexValue(String indexName)   {
        return categoryIndices.get(indexName);
    }

    /**
     * TODO: method comment
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     * TODO: method comment
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * TODO: method comment
     * @return
     */
    public String getRegion() {
        return region;
    }
}
