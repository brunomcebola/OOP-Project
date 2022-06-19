package game;

import java.util.ArrayList;

public class Command {
  private char cmd;
  private ArrayList<Integer> values;

  public Command(char cmd, ArrayList<Integer> values) {
    this.cmd = cmd;
    this.values = values;
  }

  public char getCommand() {
    return this.cmd;
  }

  public ArrayList<Integer> getValues() {
    return this.values;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();

    str.append("Cmd: " + this.cmd + " -> ");

    for (int v : this.values) {
      str.append(v + " ");
    }

    return str.toString();
  }
}
