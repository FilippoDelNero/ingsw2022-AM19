package it.polimi.ingsw.am19.Persistence;

import it.polimi.ingsw.am19.Model.Match.MatchDecorator;

import java.io.*;

/**
 * This class store and restore a SavedData from a txt file
 */
public class StorageMatch implements Serializable {

    public void store(MatchDecorator model, boolean isExpert){
        SavedData savedData = new SavedData(isExpert,model);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("savedMatch.txt");
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(savedData);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public SavedData restore(){
        SavedData savedData;
        try {
            FileInputStream fileInputStream = new FileInputStream("savedMatch.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            savedData = (SavedData) objectInputStream.readObject();
            savedData.getModel().getIslandManager().setIterator();
            return savedData;
        } catch (IOException | ClassNotFoundException ignored) {
        }

        return null;
    }

}