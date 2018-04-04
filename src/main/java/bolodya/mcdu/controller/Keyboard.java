package bolodya.mcdu.controller;

public interface Keyboard {

    void printCharacter(char character);
    void clear();

    void addListener(KeyboardListener listener);
    boolean removeListener(KeyboardListener listener);
}
