package WordGame;

/**
 * The {@code Country} class represents a country in the World.
 * <p>
 *      It has attributes:
 * </p>
 * <ul>
 *      <li>Name</li>
 *      <li>Capital City Name</li>
 *      <li>Facts about the country (number of facts - {@value NUMBER_OF_COUNTRY_FACTS})</li>
 * </ul>
 * @author Jason Lau
 * @version 1.0
 */
public class Country {

    private final String name;
    private final String capitalCityName;
    private final String[] facts;

    public static final int NUMBER_OF_COUNTRY_FACTS = 3;

    /**
     * Constructs a Country class setting name, capitalCityName, and facts to the parameters.
     * @param name as a String.
     * @param capitalCityName as a String.
     * @param facts as an array of Strings.
     * @throws IllegalArgumentException if name is null or empty.
     * @throws IllegalArgumentException if capitalCityName is null or empty.
     * @throws IllegalArgumentException if facts is null or does not contain the required length {@value NUMBER_OF_COUNTRY_FACTS}.
     */
    public Country(final String name, final String capitalCityName, final String[] facts)
    {
        validateCountryName(name);
        validateCountryCapitalCityName(capitalCityName);
        validateCountryFacts(facts);

        this.name = name;
        this.capitalCityName = capitalCityName;
        this.facts = facts;
    }

    /**
     * Returns the name of this Country.
     * @return name as a String.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the capital city name of this Country.
     * @return capitalCityName as a String.
     */
    public String getCapitalCityName()
    {
        return capitalCityName;
    }

    /**
     * Returns the list of facts for this Country.
     * @return capitalCityName as an array of Strings.
     */
    public String[] getFacts()
    {
        return facts.clone();
    }



    /*
     * Validates the name instance variable of this Country.
     *
     * @param name as a String.
     * @throws IllegalArgumentException if name is null or empty.
     */
    private static void validateCountryName(final String name)
    {
        if (name == null ||
            name.isBlank())
        {
            throw new IllegalArgumentException("Country name cannot be null or only contain whitespace.");
        }
    }

    /*
     * Validates the name instance variable of this Country.
     *
     * @param capitalCityName as a String.
     * @throws IllegalArgumentException if capitalCityName is null or empty.
     */
    private static void validateCountryCapitalCityName(final String capitalCityName)
    {
        if (capitalCityName == null ||
            capitalCityName.isBlank())
        {
            throw new IllegalArgumentException("Country capital city name cannot be null or only contain whitespace.");
        }

    }

    /*
     * Validates the facts instance variable of this Country.
     *
     * @param facts as an array of Strings.
     * @throws IllegalArgumentException if facts is null.
     * @throws IllegalArgumentException if facts array does not have the length of REQUIRED_FACTS_ARRAY_SIZE,
     * @throws IllegalArgumentException if facts array has any String elements that are null or empty.
     */
    private static void validateCountryFacts(final String[] facts)
    {
        if (facts == null)
        {
            throw new IllegalArgumentException("Country facts array cannot be null");
        }

        if (facts.length != NUMBER_OF_COUNTRY_FACTS)
        {
            throw new IllegalArgumentException("Country facts array has an invalid length.");
        }

        for (final String fact : facts)
        {
            if (fact == null ||
                fact.isBlank())
            {
                throw new IllegalArgumentException("Country fact in facts array cannot be null or empty.");
            }
        }
    }
}
