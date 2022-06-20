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

    /**
     * Performed before a game in debug mode, this function makes sure that the
     * necessary files are present and correctly read: Furthermore, it verifies if
     * the card file has a list of cards large enough to execute every command in
     * the command file.
     * 
     * @param credits      The initial amount of credits of the player.
     * @param cmdFilePath  The path of the file with the commands to execute.
     * @param cardFilePath The path of the file with the ordered list of cards to
     *                     use.
     * @throws Exception
     */
    public GameDebug(int credits, String cmdFilePath, String cardFilePath) throws Exception {
        super(credits);

        this.cards = new ArrayList<Card>();
        this.commands = new ArrayList<Command>();

        this.cardFile = new File(cardFilePath);
        this.cmdFile = new File(cmdFilePath);

        this.readCardFile();
        this.readCmdFile();

        this.checkDeckSize();

        this.setVerbose(true);
    }

    /**
     * Runs the game on debug mode, with the commands and deck on the files.
     * 
     * @throws Exception
     * @see Exception
     */
    public void run() throws Exception {

        this.createDeck();

        for (Command c : this.commands) {

            System.out.println(c);

            switch (c.getCommand()) {
                case 'b':
                    try {
                        if (c.getValues().size() == 0)
                            this.placeBet();
                        else
                            this.placeBet(c.getValues().get(0));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case '$':
                    this.printCredits();
                    break;
                case 'd':
                    try {
                        this.dealHand();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        ArrayList<Integer> swap = holdToSwap(c.getValues());

                        this.swapCards(swap);

                        this.endRound();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'a':
                    this.getAdvice();

                    break;

                case 's':

                    this.printStatistics();
                    break;

            }

            System.out.println();
        }
    }

    /**
     * Gets the ArrayList of cards read from the card file.
     * 
     * @return The list of cards.
     */
    protected ArrayList<Card> getCardsList() {
        return this.cards;
    }

    /**
     * Reads the file with the list of cards and transforms the content into an
     * ArrayList of Cards
     * 
     * @throws Exception
     * @see Exception
     * @see Card
     */
    private void readCardFile() throws Exception {
        if (cardFile.exists() == false) {
            throw new cardFileDoesntExistException();
        }

        String text = "";

        Scanner cardReader = new Scanner(cardFile);

        while (cardReader.hasNextLine()) {
            text += cardReader.nextLine();
            text += " ";
        }

        cardReader.close();

        String[] cardList = text.split("\\s+");

        for (String s : cardList) {
            this.cards.add(new Card(s.charAt(0), s.charAt(1)));
        }

    }

    /**
     * Reads the file with the list of commands and transforms the content into an
     * ArrayList of Commands
     * 
     * @throws cmdFileDoesntExistException If the path of the file with the list of
     *                                     commands is incorrect.
     * @throws invalidCommandException     If any of the commands in the list is not
     *                                     a valid Command.
     * @throws IOException
     * @see IOException
     * @see Command
     */
    private void readCmdFile() throws cmdFileDoesntExistException, invalidCommandException, IOException {
        if (cmdFile.exists() == false) {
            throw new cmdFileDoesntExistException();
        }
        String text = "";

        Scanner cmdReader = new Scanner(cmdFile);

        while (cmdReader.hasNextLine()) {
            text += cmdReader.nextLine();
            text += " ";
        }

        cmdReader.close();

        String[] cmdList = text.split("\\s+");

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
                        values.add(Integer.parseInt(cmdList[i + lookahead]) - 1);
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

    /**
     * Checks if the deck of cards is large enough for the list of commands
     * presented in the file.
     * 
     * @throws invalidDeckSizeException If the Deck size is smaller than the
     *                                  required size to complete every command in
     *                                  the list.
     */
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

    // TODO: ver o que fazer com isto
    /**
     * Checks if a String is only composed of numbers.
     * 
     * @param input the String to analyze.
     * @return True if all the character in the string are numerical, false if any
     *         are not.
     */
    private static boolean isNumber(String input) {
        char[] c = input.toCharArray();
        for (char aux : c)
            if (!Character.isDigit(aux)) {
                return false;
            }
        return true;
    }
}