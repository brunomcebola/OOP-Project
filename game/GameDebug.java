package game;

import java.io.*;
import java.util.*;
import exceptions.debugExceptions.*;

public class GameDebug extends Game {
    private File cardFile;
    private File cmdFile;

    private String[] cardList;
    private String[] cmdList;

    private ArrayList<Command> commands;

    public GameDebug(int credits, String cmdFilePath, String cardFilePath) throws Exception {
        super(credits);

        this.commands = new ArrayList<Command>();

        this.cardFile = new File(cardFilePath);
        this.cmdFile = new File(cmdFilePath);

        this.readCardFile();
        this.readCmdFile();

        this.checkDeckSize();
    }

    public void generateDeck() throws Exception {
        this.deck.uploadCards(cardList);
    }

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
        ArrayList<Integer> values;

        for (int i = 0; i < cmdList.length; i++) { // transforms string into ArrayList of Commands
            values = new ArrayList<Integer>();

            switch (cmdList[i]) {
                case "b":
                    if ((i + 1) < cmdList.length && auxFunctions.aux.isNumber(cmdList[i + 1])) {
                        values.add(Integer.parseInt(cmdList[i + 1]));
                        i++;
                    }

                    this.commands.add(new Command('b', values));

                    break;

                case "h":
                    int lookahead = 1;

                    while ((i + lookahead) < cmdList.length && auxFunctions.aux.isNumber(cmdList[(i + lookahead)])) {
                        // the user can hold up to 5 cards
                        if (values.size() == 5) {
                            throw new invalidCommandException(cmdList[i + lookahead], i + lookahead);
                        }
                        values.add(Integer.parseInt(cmdList[i + lookahead]));
                        lookahead++;
                    }

                    this.commands.add(new Command('h', values));

                    i += (lookahead - 1);

                    break;

                case "$":
                    tempCommand = new Command('$', values);
                    this.commands.add(tempCommand);
                    break;

                case "d":
                    tempCommand = new Command('d', values);
                    this.commands.add(tempCommand);
                    break;

                case "a":
                    tempCommand = new Command('a', values);
                    this.commands.add(tempCommand);
                    break;

                case "s":
                    tempCommand = new Command('s', values);
                    this.commands.add(tempCommand);
                    break;

                default:
                    throw new invalidCommandException(cmdList[i], i);
            }
        }
    }

    private void checkDeckSize() throws invalidDeckSizeException {
        int requiredSize = 0;

        for (Command c : this.commands) {
            switch (c.getCommand()) {
                case 'd':
                    requiredSize += 5;
                    break;
                case 'h':
                    requiredSize += (5 - c.getValues().size()); // size of hand - cards that are not discarded
                    break;
            }
        }
        if (this.cardList.length < requiredSize) {
            throw new invalidDeckSizeException();
        }
    }
}