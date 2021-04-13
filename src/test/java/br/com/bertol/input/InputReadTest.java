package br.com.bertol.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class InputReadTest {

    private final static String INPUT_FILE_NAME = "src/test/resources/input.csv";

    private final static String INPUT_FILE_WITH_EMPTY_LINE = "src/test/resources/inputWithEmptyLine.csv";

    private final static String INPUT_FILE_WRONG_DATA = "src/test/resources/inputWithWrongData.csv";

    private final static String INPUT_FILE_WRONG_PATH = "src/test/resourcesSDSD/inputWithWrongData.csv";

    @Test
    public void shouldReadValidInputFile() {
        // given
        final var inputReader = new InputRead(INPUT_FILE_NAME);

        // when
        final var routes = inputReader.getRoutes();

        // then
        assertEquals(5, routes.getAirports().size());
    }

    @Test
    public void shouldReadInputFileWithEmptyLineAndFail() {
        // given
        final var inputReader = new InputRead(INPUT_FILE_WITH_EMPTY_LINE);

        // when
        final var exception = assertThrows(RuntimeException.class, inputReader::getRoutes);

        // then
        assertEquals("Line  contains wrong number of columns. Must be 3.", exception.getMessage());
    }

    @Test
    public void shouldReadInputFileWithWrongDataAndFail() {
        // given
        final var inputReader = new InputRead(INPUT_FILE_WRONG_DATA);

        // when
        final var exception = assertThrows(RuntimeException.class, inputReader::getRoutes);

        // then
        assertEquals("Line contains wrong value for distance. Must be integer. Current value is asas.", exception.getMessage());
    }


    @Test
    public void shouldReadInputFileWithWrongPathFail() {
        // given
        final var inputReader = new InputRead(INPUT_FILE_WRONG_PATH);

        // when
        final var exception = assertThrows(RuntimeException.class, inputReader::getRoutes);

        // then
        assertEquals("Error reading input file src/test/resourcesSDSD/inputWithWrongData.csv.", exception.getMessage());
    }
}
