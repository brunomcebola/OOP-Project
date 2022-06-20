package game;

import java.util.ArrayList;

public class Command {
  private char cmd;
  private ArrayList<Integer> values;

  /**
   * The constructor of the class.
   * 
   * @param cmd    command to control the program
   * @param values list of values associted with the command
   */
  public Command(char cmd, ArrayList<Integer> values) {
    this.cmd = cmd;
    this.values = values;
  }

  /**
   * This function returns the command character of the current object.
   * 
   * @return The command character.
   */
  public char getCommand() {
    return this.cmd;
  }

  /**
   * This function returns the values of the current object.
   * 
   * @return The command character.
   */
  public ArrayList<Integer> getValues() {
    return this.values;
  }

  @Override
  /**
   * Overrides the toString function to print the object in the right way «.
   * 
   * @return Object in string notation.
   */
  public String toString() {
    StringBuilder str = new StringBuilder();

    str.append("-cmd " + this.cmd + " ");

    for (int v : this.values) {
      if (this.cmd == 'h')
        str.append((v + 1) + " ");
      else
        str.append(v + " ");
    }

    return str.toString();
  }
}
