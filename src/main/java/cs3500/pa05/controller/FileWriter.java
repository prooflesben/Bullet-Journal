package cs3500.pa05.controller;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * class to write the inputted content into a .bujo file
 */
public final class FileWriter {

  /**
   * appends the .bujo extension to the given filepath
   *
   * @param path Path to add the .bujo extension
   * @return A Path representing the new bujo file.
   */
  public static Path addBujo(Path path) {
    String pathString = path.toString() + ".bujo";

    return Path.of(pathString);
  }

  /**
   * given a path and content, write that content to the corresponding .bujo file
   *
   * @param path    Path to be written towards. Will overwrite any file mentioned
   * @param content Content to write to the file
   */
  public void writeBujo(Path path, JsonNode content) {
    byte[] data = content.toString().getBytes();

    path = addBujo(path);

    try {
      Files.write(path, data);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * writes directly string content into a file path
   *
   * @param path    Path to be written towards. Will overwrite any file mentioned
   * @param content String content to write to file
   */
  public void writeBujoString(Path path, String content) {
    try {
      Files.write(path, content.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
