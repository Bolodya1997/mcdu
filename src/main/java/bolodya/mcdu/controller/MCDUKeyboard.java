package bolodya.mcdu.controller;

import bolodya.mcdu.view.MCDUView;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
@NoArgsConstructor
public class MCDUKeyboard implements Keyboard {

    @Autowired
    private ApplicationContext context;

    private final Set<KeyboardListener> listeners = new HashSet<>();

    @PostConstruct
    private void init() {
        val mcdu = context.getBean(MCDUView.class);

        mcdu.setCharacterListener(this::printCharacter);
        mcdu.setClearListener(this::clear);
    }

    @Override
    public void printCharacter(char character) {
        listeners.forEach(listener -> listener.printCharacter(character));
    }

    @Override
    public void clear() {
        listeners.forEach(KeyboardListener::clear);
    }

    @Override
    public void addListener(KeyboardListener listener) {
        listeners.add(listener);
    }

    @Override
    public boolean removeListener(KeyboardListener listener) {
        return listeners.remove(listener);
    }
}
