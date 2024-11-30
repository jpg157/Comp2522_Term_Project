package WordGame;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class World {

    private final Map<String, Country> countries;
    private final List<String> countryNamesList;

    private static final String INPUT_FILES_DIRECTORY_NAME = "src/resources";

    private static final int EXPECTED_ELEMENT_NUMBER_IN_SPLIT_FILE_LINE = 2;
    private static final int FIRST_ELEMENT_IN_SPLIT_FILE_LINE           = 0;
    private static final int SECOND_ELEMENT_IN_SPLIT_FILE_LINE          = 1;

    private static final int NUMBER_OF_COUNTRY_FACTS    = 3;
    private static final int FACT_ONE_INDEX             = 0;
    private static final int FACT_TWO_INDEX             = 1;
    private static final int FACT_THREE_INDEX           = 2;

    public World() throws IOException {

        this.countries = populateCountriesMap();
        this.countryNamesList = new ArrayList<>(countries.keySet());
    }

    public List<String> getAllCountryNames()
    {
        final List<String> countryNamesListDeepCopy;
        countryNamesListDeepCopy = new ArrayList<>(this.countryNamesList);

        return countryNamesListDeepCopy;
    }

    /**
     * Returns the country with the corresponding countryName parameter value, or null if not found.
     *
     * @param countryName as a String.
     * @return a Country object based on the countryName, or {@code null} if no Countries were found with entered countryName.
     */
    public Country getCountry(final String countryName)
    {
        final Country country;
        final Country countryCopy;

        country = countries.get(countryName);

        if (country == null)
        {
            return null;
        }

        countryCopy = new Country(country.getName(), country.getCapitalCityName(), country.getFacts());

        return countryCopy;
    }

    private static Map<String, Country> populateCountriesMap()
            throws IOException
    {
        final Path folderPath = Paths.get(INPUT_FILES_DIRECTORY_NAME);
        final Map<String, Country> tempCountriesMap;

        if (Files.exists(folderPath)
            && Files.isDirectory(folderPath))
        {
            tempCountriesMap = new HashMap<>();
            readEachFileInDirectory(folderPath, tempCountriesMap);
        }
        else
        {
            throw new IOException("Directory was not found with the path: " + INPUT_FILES_DIRECTORY_NAME);
        }

        return tempCountriesMap;
    }


    private static void readEachFileInDirectory(final Path folderPath,
                                                final Map<String, Country> tempCountriesMap)
            throws IOException
    {
        // Get the list of all files in the "resources" folder and
        try (final Stream<Path> filePathList = Files.list(folderPath))
        {
            filePathList.forEach(
                    filePath ->
                    {
                        try
                        {
                            readFileAndLoadContents(filePath, tempCountriesMap);
                        } catch(final IOException exception)
                        {
                            //TODO find better solution
                            throw new RuntimeException(exception.getMessage());
                        }
                    }
            );
        }
        catch (final IOException exception)
        {
            throw new IOException(
                    "There was an error processing the country files:"
                    + exception.getMessage()
            );
        }
    }

    private static void readFileAndLoadContents(final Path filePath,
                                                final Map<String, Country> tempCountriesMap)
            throws IOException
    {
        if (!Files.exists(filePath))
        {
            throw new IOException("Input file with path " + filePath + " does not exist");
        }

        if (Files.isDirectory(filePath))
        {
            throw new IOException("Entered path " + filePath + " is a directory (expected a file)");
        }

        try (BufferedReader bufferedFileReader = Files.newBufferedReader(filePath))
        {
            String currentLine;

            // Read the first line of the file
            currentLine = bufferedFileReader.readLine();

            while (currentLine != null)
            {
                final String[] countryNameAndCapitalCity;

                final Country country;
                final String countryName;
                final String countryCapitalCity;
                final String[] countryFactArray;

                if (!currentLine.isBlank())
                {
                    // For the current Country:

                        // Extract the country name and the capital city from the first line

                        // Read the second line for fact 1

                        // Read the third line for fact 2

                        // Read the fourth line for fact 3

                        // Create new Country object and add to tempCountriesMap

                    // Extract the country name and the capital city from the first line
                    countryNameAndCapitalCity = currentLine.split(":");

                    if (countryNameAndCapitalCity.length != EXPECTED_ELEMENT_NUMBER_IN_SPLIT_FILE_LINE)
                    {
                        throw new IOException(
                                "There was a problem with loading the file with path - "
                                + filePath + ":"
                                + System.lineSeparator() +
                                "Invalid number of elements in current file line: " + currentLine
                                + System.lineSeparator() +
                                "Expected number of elements in current line was " + EXPECTED_ELEMENT_NUMBER_IN_SPLIT_FILE_LINE
                                + System.lineSeparator() +
                                "Actual Number of elements in the current line: " + currentLine.length()
                        );
                    }

                    countryName         = countryNameAndCapitalCity[FIRST_ELEMENT_IN_SPLIT_FILE_LINE];
                    countryCapitalCity  = countryNameAndCapitalCity[SECOND_ELEMENT_IN_SPLIT_FILE_LINE];
                    countryFactArray = new String[NUMBER_OF_COUNTRY_FACTS];

                    try
                    {
                        // Read the second line for fact 1
                        currentLine = bufferedFileReader.readLine();
                        validateCurrentFileLine(currentLine);
                        countryFactArray[FACT_ONE_INDEX] = currentLine;

                        // Read the third line for fact 2
                        currentLine = bufferedFileReader.readLine();
                        validateCurrentFileLine(currentLine);
                        countryFactArray[FACT_TWO_INDEX] = currentLine;

                        // Read the fourth line for fact 3
                        currentLine = bufferedFileReader.readLine();
                        validateCurrentFileLine(currentLine);
                        countryFactArray[FACT_THREE_INDEX] = currentLine;

                    }
                    catch(final IOException exception)
                    {
                        throw new IOException(
                                "There was a problem with the file structure for the remainder of the file with path: "
                                + filePath
                        );
                    }

                    // Create new Country object and add to tempCountriesMap

                    country = new Country(
                            countryName,
                            countryCapitalCity,
                            countryFactArray
                    );

                    tempCountriesMap.put(countryName, country);

                }
                else
                {
                    // Read the next line if the current line is blank
                }

                // Read the next line / first line for the next Country
                currentLine = bufferedFileReader.readLine();
            }
        }
        catch (final IOException exception)
        {
            throw new IOException("Current file with path " + filePath + " could not be opened");
        }
    }

    private static void validateCurrentFileLine(final String currentLine)
            throws IOException {
        if (currentLine == null)
        {
            throw new IOException("currentLine buffer value from file is null");
        }

        if (currentLine.isBlank())
        {
            throw new IOException("currentLine buffer value from file is blank");
        }
    }

}
