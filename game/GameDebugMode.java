package game;

import java.io.*;
import java.util.*;
import exceptions.debugExceptions.*;

public class GameDebugMode {
    private String cardFilePath;
    private String cmdFilePath;

    private File cardFile;
    private File cmdFile;

    private String[] cardList;
    private String[] cmdList;

    public GameDebugMode(int credits, String cmdFilePath, String cardFilePath)
            throws cardFileDoesntExistException, cmdFileDoesntExistException, invalidDeckSizeException, IOException {

        // super(credits);

        this.cmdFilePath = cmdFilePath;
        this.cardFilePath = cardFilePath;

        cardFile = new File(this.cardFilePath);
        cmdFile = new File(this.cmdFilePath);

        this.readCmdFile();
        this.readCardFile();

        this.checkDeckSize();


        int[] v = {1,3};
        Command t = new Command('o', v);
        System.out.println(t.cmd);
    }

    // Private Instance Methods

    private void readCardFile() throws cardFileDoesntExistException, IOException {
        if (cardFile.exists() == false) {
            throw new cardFileDoesntExistException();
        }

        String text = "";

        Scanner cardReader = new Scanner(cardFile);

        while (cardReader.hasNextLine()) {
            text += cardReader.nextLine();
        }

        cardReader.close();

        cardList = text.split(" ");
    }

    private void readCmdFile() throws cmdFileDoesntExistException, IOException {
        // TODO: check if commands are valid

        if (cardFile.exists() == false) {
            throw new cmdFileDoesntExistException();
        }

        String text = "";

        Scanner cmdReader = new Scanner(cmdFile);

        while (cmdReader.hasNextLine()) {
            text += cmdReader.nextLine();
        }

        cmdReader.close();

        cmdList = text.split(" ");

    }

    private void checkDeckSize() throws invalidDeckSizeException {
        int holdCount = 0;
        int requiredSize = 0;
        int deckSize = cardList.length;

        for (int i = 0; i < cmdList.length; i++) {
            switch (cmdList[i]) {
                case "d":
                    requiredSize += 5;
                    break;
                case "h":
                    while (++i < cmdList.length
                            && auxFunctions.aux.isNumber(cmdList[i])) {
                        holdCount++;
                    }
                    --i;
                    requiredSize += (5 - holdCount); // size of hand - cards that are not discarded
                    holdCount = 0;
                    break;
                default:
                    break;
            }
        }
        if (deckSize < requiredSize) {
            throw new invalidDeckSizeException();
        }
    }

    // Private subclass

    private static class Command {
        public char cmd;
        public int[] values;

        public Command(char cmd, int[] values) {
            this.cmd = cmd;
            this.values = values;
        }
    }
}
