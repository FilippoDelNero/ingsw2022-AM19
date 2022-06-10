package it.polimi.ingsw.am19.Persistence;

import it.polimi.ingsw.am19.Model.Match.MatchDecorator;

import java.io.*;

/**
 * This class store and restore a SavedData from a txt file
 */
public class StorageMatch implements Serializable {
    /**
     * This method store a match
     * @param model the model to store
     * @param roundNUmber the number of the last round played in all
     */
    public void store(MatchDecorator model, int roundNUmber){
        SavedData savedData = new SavedData(model,roundNUmber);

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

    /**
     * This method restore a SavedMatch
     * @return a SavedData class
     */
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

    /**
     * This method delete the previous match saved
     * It's called when a match is ended
     */
    public void delete(){
        boolean isEliminated;
        File file =new File("savedMatch.txt");
        do{
            isEliminated=file.delete();
        } while(!isEliminated);
    }

    /**
     * Returns true if a previous match's storage data exists, false otherwise
     * @return true if a previous match's storage data exists, false otherwise
     */
    public boolean checkOldMatches(){
        File file = new File("savedMatch.txt");
        return file.exists();
    }
}
