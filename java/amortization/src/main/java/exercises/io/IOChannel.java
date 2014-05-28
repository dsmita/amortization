package exercises.io;

import java.io.IOException;

public interface IOChannel {

  public String read(String prompt) throws IOException;

  public void write(String message);

  public void write(String formatString, Object... args);

}
