package bolodya.mcdu.view;

import java.util.function.Consumer;

public interface MCDUView {

//    ------   keyboard   ------

    void setCharacterListener(Consumer<Character> listener);
    void setClearListener(Runnable listener);

//    ------   screen   ------

    void setScreen(Object screenObj);
}
