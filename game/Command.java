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
}
