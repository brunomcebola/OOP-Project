package debug;

import java.io.*;
import java.util.*;
import exceptions.debugExceptions.*;

public class debugFiles {

    String card_file = "card-file.txt";
    String cmd_file = "cmd-file.txt";

    File cardFile = new File("txtFiles/"+card_file);
    File cmdFile = new File("txtFiles/"+cmd_file); 

    private String debugDeck = new String();

    private String debugCommandList = new String();



    public void readCardFile() throws cardFileDoesntExistException, IOException{
        if (cardFile.exists() == false) {
            throw new cardFileDoesntExistException();
        }

        Scanner cardReader = new Scanner(cardFile);

        
        while (cardReader.hasNextLine()) {
            debugDeck += cardReader.nextLine();
        }
        cardReader.close();
    }


    public void readCmdFile() throws cmdFileDoesntExistException, IOException{
        if (cardFile.exists() == false) {
            throw new cmdFileDoesntExistException();
        }

        Scanner cmdReader = new Scanner(cmdFile);
        
        while (cmdReader.hasNextLine()) {
            debugCommandList += cmdReader.nextLine();
        }
        cmdReader.close();
    }

    public String[] makeCardList(String deck){
        String[] finalCardList = deck.split(" ");

        return finalCardList;
    }

    public String[] makeCommandList(String commands){
        String[] finalCommandList = commands.split(" ");

        return finalCommandList;
    }

    public void checkDeckSize(String[] commandList, String[] deck) throws invalidDeckSizeException{
        int deckSize = deck.length;
        int requiredSize = 0, auxCounter = 1, holdBuffer = 0;

        for (int i = 0; i < commandList.length; i++) {
            //System.out.println("command: "+commandList[i]);
            switch (commandList[i]) {
                case "d":
                    requiredSize += 5;
                    //System.out.println("requiredSize: "+requiredSize);
                    break;
                case "h":
                    while ((i+auxCounter) < commandList.length && auxFunctions.aux.isNumber(commandList[i+auxCounter])) {
                        holdBuffer++;
                        auxCounter++;
                    }
                    requiredSize += (5-holdBuffer);//size of hand - cards that are not discarded
                    //System.out.println("requiredSize: "+requiredSize);
                    holdBuffer = 0;
                    auxCounter = 1;
                    break;
                default:
                    break;
            }
        }
        if (deckSize < requiredSize) {
            throw new invalidDeckSizeException();
        } else {
            System.out.println("Deck is valid!");
        }
    }

    


    public static void main(String[] args){

        debugFiles mystr = new debugFiles();
        try {
            mystr.readCardFile();
            mystr.readCmdFile();

            String[] finalCardList = mystr.makeCardList(mystr.debugDeck); //Lista separada de cartas
            String[] finalCommandList = mystr.makeCommandList(mystr.debugCommandList); //Lista separada de comandos

            System.out.println(mystr.debugDeck);
            for(String a : finalCardList){
                System.out.print(a);
                System.out.print(" ");
            }
            System.out.println("");
            System.out.println(mystr.debugCommandList);
            for(String b : finalCommandList){
                System.out.print(b);
                System.out.print(" ");
            }
            System.out.println("");
            mystr.checkDeckSize(finalCommandList, finalCardList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

