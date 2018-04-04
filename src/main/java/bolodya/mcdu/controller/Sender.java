package bolodya.mcdu.controller;

@FunctionalInterface
public interface Sender {

    void send(Runnable callback);
}
