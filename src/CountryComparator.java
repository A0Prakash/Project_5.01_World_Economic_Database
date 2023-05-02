import java.util.Comparator;

public class CountryComparator implements Comparator<Country> {
    private boolean asc;
    private String primarySort, secondarySort;

    public CountryComparator(boolean a, String p, String s) {
        asc = a;
        primarySort = p;
        secondarySort = s;
    }

    public int compare(Country one, Country two) {
        int diff = 0;
        if(primarySort.equals("Country ID"))
            diff = one.getID()-two.getID();
        else if(primarySort.equals("Country Name"))
            diff = one.getCountryName().compareTo(two.getCountryName());
        else if(primarySort.equals("Region"))
            diff = one.getRegion().compareTo(two.getRegion());
        else if(primarySort.equals("Economic Freedom Index")) {
            Double dl = one.getEconomicFreedomIndex();
            Double d2 = two.getEconomicFreedomIndex();
            diff = dl.compareTo(d2);
        }
        else if(primarySort.equals("Specific Index")) {
            Double d1 = one.getIndexValue(secondarySort);
            Double d2 = two.getIndexValue(secondarySort);

            diff = d1.compareTo(d2);
        }
        return (asc) ? diff: -diff;
    }
}
