package debug;

import java.io.*;
import java.util.*;
import exceptions.debugExceptions.*;

public class debugFiles {
    File cardFile = new File("debug/card-file.txt");
    File cmdFile = new File("debug/cmd-file.txt"); 

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

    public static void main(String[] args){

        debugFiles mystr = new debugFiles();
        try {
            mystr.readCardFile();
            mystr.readCmdFile();

            String[] finalCardList = mystr.makeCardList(mystr.debugDeck); //Lista separada de cartas
            String[] finalCommandList = mystr.makeCommandList(mystr.debugCommandList); //Lista separada de comandos

            System.out.println(mystr.debugDeck);
            for(String a : finalCardList){
                System.out.println(a);
            }
            
            System.out.println(mystr.debugCommandList);
            for(String b : finalCommandList){
                System.out.println(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

