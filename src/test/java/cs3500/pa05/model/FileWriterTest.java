package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.controller.FileWriter;
import cs3500.pa05.model.json.TaskJson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * testing class for the FileWriter class
 */
class FileWriterTest {

  /**
   * testing the writing JsonNode content to a .bujo file
   */
  @Test
  public void testWriteBujo() {
    TaskJson testTask = new TaskJson("Name", "Description", Weekday.SUNDAY, false);

    JsonNode node = new ObjectMapper().convertValue(testTask, JsonNode.class);
    FileWriter fw = new FileWriter();
    fw.writeBujo(Path.of("src/test/resources/test"), node);

    String actualBujo = "";
    try {
      List<String> lines = Files.readAllLines(Path.of("src/test/resources/test.bujo"));
      actualBujo = lines.get(0);
    } catch (IOException ex) {
      fail();
    }

    //read the outputted file and determine that it is correct
    assertEquals(
        "{\"name\":\"Name\",\"description\":\"Description\",\"weekday\":\""
           + "SUNDAY\",\"complete\":false}",
        actualBujo);
  }

  /**
   * testing writing direct strings to a bujo file
   */
  @Test
  public void testStringBujoWrite() {
    String expectedString = "this is a test";

    FileWriter fw = new FileWriter();
    Path source = Path.of("src/test/resources/outputFile.txt");
    fw.writeBujoString(source, expectedString);

    String actualResult = "";
    try {
      List<String> lines = Files.readAllLines(source);
      actualResult = lines.get(0);
    } catch (IOException ex) {
      fail();
    }

    //read the outputted file and determine that it is correct
    assertEquals(expectedString, actualResult);
  }

  /**
   * testing if adding .bujo works properly to a filepath
   */
  @Test
  public void testAddBujo() {
    Path p = FileWriter.addBujo(Path.of("testExtension"));

    assertEquals("testExtension.bujo", p.toString());
  }
}