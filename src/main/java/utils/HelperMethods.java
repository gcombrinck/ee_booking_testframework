package utils;

public class HelperMethods {
    public int generateRandomNumber(int max, int min) {
        return (int) (Math.floor(Math.random() * (max - min + 1)) + min);
    }
}
