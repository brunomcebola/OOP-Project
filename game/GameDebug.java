package game;

import java.io.*;
import java.util.*;

import cards.*;
import exceptions.debugExceptions.*;

public class GameDebug extends Game {
    private File cardFile;
    private File cmdFile;

    private ArrayList<Card> cards;
    private ArrayList<Command> commands;

    public GameDebug(int credits, String cmdFilePath, String cardFilePath) throws Exception {
        super(credits);

        this.cards = new ArrayList<Card>();
        this.commands = new ArrayList<Command>();

        this.cardFile = new File(cardFilePath);
        this.cmdFile = new File(cmdFilePath);

        this.readCardFile();
        this.readCmdFile();

        this.checkDeckSize();
    }

    protected ArrayList<Card> getCardsList() {
        return this.cards;
    }

    private void readCardFile() throws Exception {
        if (cardFile.exists() == false) {
            throw new cardFileDoesntExistException();
        }

        String text = "";

        Scanner cardReader = new Scanner(cardFile);

        while (cardReader.hasNextLine()) {
            text += cardReader.nextLine();
        }

        cardReader.close();

        String[] cardList = text.split(" ");

        for (String s : cardList) {
            this.cards.add(new Card(s.charAt(0), s.charAt(1)));
        }

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

        String[] cmdList = text.split(" ");

        Command tempCommand;
        ArrayList<Integer> values;

        for (int i = 0; i < cmdList.length; i++) { // transforms string into ArrayList of Commands
            values = new ArrayList<Integer>();

            switch (cmdList[i]) {
                case "b":
                    if ((i + 1) < cmdList.length && isNumber(cmdList[i + 1])) {
                        values.add(Integer.parseInt(cmdList[i + 1]));
                        i++;
                    }

                    this.commands.add(new Command('b', values));

                    break;

                case "h":
                    int lookahead = 1;

                    while ((i + lookahead) < cmdList.length && isNumber(cmdList[(i + lookahead)])) {
                        // the user can hold up to 5 cards
                        if (values.size() == 5) {
                            throw new invalidCommandException(cmdList[i + lookahead], i + lookahead);
                        } // if the card to hold is not a valid Hand card (i.e. not from card 1 to card 5)
                        if (Integer.parseInt(cmdList[(i + lookahead)]) < 1
                                || Integer.parseInt(cmdList[(i + lookahead)]) > 5) {
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

        if (this.cards.size() < requiredSize) {
            throw new invalidDeckSizeException();
        }
    }

    private static boolean isNumber(String input) {
        char[] c = input.toCharArray();
        for (char aux : c)
            if (!Character.isDigit(aux)) {
                return false;
            }
        return true;
    }

    // TODO: delete below - test porpuses only

    public void printCmds() {
        int i = 1;
        System.out.println("Commands:");
        for (Command c : this.commands) {
            System.out.println(i++ + ": " + c);
        }
        System.out.println();
    }
}