package exercises.io;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.IllegalFormatException;

public class ConsoleIOChannel implements IOChannel {

  private Console console = System.console();

  @Override
  public String read(String prompt) throws IOException {
    String line;
    if (console != null) {
      line = console.readLine(prompt);
    } else {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      write(prompt);
      line = bufferedReader.readLine();
    }
    return line.trim();
  }

  @Override
  public void write(String message) {
    write("%s", message);
  }

  @Override
  public void write(String formatString, Object... args) {
    try {
      if (console != null) {
        console.printf(formatString, args);
      } else {
        System.out.print(String.format(formatString, args));
      }
    } catch (IllegalFormatException e) {
      System.err.print("Error printing...\n");
    }

  }

}
