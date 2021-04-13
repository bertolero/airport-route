package br.com.bertol.io;

import br.com.bertol.model.Routes;
import br.com.bertol.service.AirportInclusion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ReadRoutesFromFileTest {

    private final static String INPUT_FILE_NAME = "src/test/resources/input.csv";

    private final static String INPUT_FILE_WITH_EMPTY_LINE = "src/test/resources/inputWithEmptyLine.csv";

    private final static String INPUT_FILE_WRONG_DATA = "src/test/resources/inputWithWrongData.csv";

    private final static String INPUT_FILE_WRONG_PATH = "src/test/resourcesSDSD/inputWithWrongData.csv";

    @Test
    public void shouldReadValidInputFile() {
        // given
        final var routes = new Routes();
        final var airportInclusion = new AirportInclusion(routes);
        final var inputReader = new ReadRoutesFromFile(INPUT_FILE_NAME, airportInclusion);

        // when
        inputReader.loadRoutesFromFile();

        // then
        assertEquals(5, routes.getAirports().size());
    }

    @Test
    public void shouldReadInputFileWithEmptyLineAndFail() {
        // given
        final var routes = new Routes();
        final var airportInclusion = new AirportInclusion(routes);
        final var inputReader = new ReadRoutesFromFile(INPUT_FILE_WITH_EMPTY_LINE, airportInclusion);

        // when
        final var exception = assertThrows(RuntimeException.class, inputReader::loadRoutesFromFile);

        // then
        assertEquals("Line  contains wrong number of columns. Must be 3.", exception.getMessage());
    }

    @Test
    public void shouldReadInputFileWithWrongDataAndFail() {
        // given
        final var routes = new Routes();
        final var airportInclusion = new AirportInclusion(routes);
        final var inputReader = new ReadRoutesFromFile(INPUT_FILE_WRONG_DATA, airportInclusion);

        // when
        final var exception = assertThrows(RuntimeException.class, inputReader::loadRoutesFromFile);

        // then
        assertEquals("Line contains wrong value for distance. Must be integer. Current value is asas.", exception.getMessage());
    }


    @Test
    public void shouldReadInputFileWithWrongPathFail() {
        // given
        final var routes = new Routes();
        final var airportInclusion = new AirportInclusion(routes);
        final var inputReader = new ReadRoutesFromFile(INPUT_FILE_WRONG_PATH, airportInclusion);

        // when
        final var exception = assertThrows(RuntimeException.class, inputReader::loadRoutesFromFile);

        // then
        assertEquals("Error reading input file src/test/resourcesSDSD/inputWithWrongData.csv.", exception.getMessage());
    }
}
