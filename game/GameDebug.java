package game;

import java.io.*;
import java.util.*;
import exceptions.debugExceptions.*;

import cards.*;

public class GameDebug extends Game {
    private String cardFilePath;
    private String cmdFilePath;

    private File cardFile;
    private File cmdFile;

    private String[] cardList;
    private String[] cmdList;

    private ArrayList<Command> commands;

    public GameDebug(int credits, String cmdFilePath, String cardFilePath) throws Exception {
        super(credits);
        
        this.cmdFilePath = cmdFilePath;
        this.cardFilePath = cardFilePath;
        this.commands = new ArrayList<Command>();

        cardFile = new File(this.cardFilePath);
        cmdFile = new File(this.cmdFilePath);

        

        this.readCmdFile();
        this.readCardFile();

        this.checkDeckSize();
    }

    protected Deck generateDeck() throws Exception {
        Deck deck = new Deck();

        deck.uploadCards(cardList);

        return deck;
    }

    public void playRound() throws Exception {
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

    private void readCmdFile() throws cmdFileDoesntExistException, invalidCommandException, IOException {
        // TODO: check if commands are valid

        if (cmdFile.exists() == false) {
            throw new cmdFileDoesntExistException();
        }
        String text = "";

        Scanner cmdReader = new Scanner(cmdFile);

        while (cmdReader.hasNextLine()) {
            text += cmdReader.nextLine();
        }

        cmdReader.close();

        cmdList = text.split(" ");

        Command tempCommand;
        int commandValueSize = 0; // size of array of values in a command
        int auxCounter = 1; // used to move through the cmdList without changing the value of i
        int[] tempValues;

        for (int i = 0; i < cmdList.length; i++) { // transforms string into ArrayList of Commands
            switch (cmdList[i]) {
                case "b":
                    while ((i + auxCounter) < cmdList.length && auxFunctions.aux.isNumber(cmdList[(i + auxCounter)])) {
                        commandValueSize++; // determines size of values array in the new command
                        if (commandValueSize > 1) { // bet can only have 1 number after
                            throw new invalidCommandException(cmdList[i + commandValueSize], i + commandValueSize);
                        }
                        auxCounter++;
                    }

                    tempValues = new int[commandValueSize];
                    for (int j = 1; j <= commandValueSize; j++) {
                        tempValues[j - 1] = Integer.parseInt(cmdList[i + j]); // all the numbers before the next symbol
                                                                              // in the cmdList are added to the values
                                                                              // array
                    }
                    tempCommand = new Command('b', tempValues);
                    this.commands.add(tempCommand);
                    i = i + commandValueSize;
                    commandValueSize = 0;
                    auxCounter = 1;
                    break;

                case "h":
                    while ((i + auxCounter) < cmdList.length && auxFunctions.aux.isNumber(cmdList[(i + auxCounter)])) {
                        commandValueSize++;
                        if (commandValueSize > 5) { // the user can hold up to 5 cards
                            throw new invalidCommandException(cmdList[i + commandValueSize], i + commandValueSize);
                        }
                        auxCounter++;
                    }

                    tempValues = new int[commandValueSize];
                    for (int j = 1; j <= commandValueSize; j++) {
                        tempValues[j - 1] = Integer.parseInt(cmdList[i + j]);
                    }
                    tempCommand = new Command('h', tempValues);
                    this.commands.add(tempCommand);
                    i = i + commandValueSize;
                    commandValueSize = 0;
                    auxCounter = 1;
                    break;

                case "$":
                    tempValues = new int[0];
                    tempCommand = new Command('$', tempValues);
                    this.commands.add(tempCommand);
                    break;

                case "d":
                    tempValues = new int[0];
                    tempCommand = new Command('d', tempValues);
                    this.commands.add(tempCommand);
                    break;

                case "a":
                    tempValues = new int[0];
                    tempCommand = new Command('a', tempValues);
                    this.commands.add(tempCommand);
                    break;

                case "s":
                    tempValues = new int[0];
                    tempCommand = new Command('s', tempValues);
                    this.commands.add(tempCommand);
                    break;

                default:
                    throw new invalidCommandException(cmdList[i], i);
            }
        }

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