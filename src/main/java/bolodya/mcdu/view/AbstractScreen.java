package bolodya.mcdu.view;

import bolodya.mcdu.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

abstract class AbstractScreen extends JPanel {

    AbstractScreen() {
        setOpaque(false);
        setLayout(new GridBagLayout());
    }

    abstract void construct(double sizeModifier);

    abstract Map<Pair<Integer, Integer>, Runnable> getScreenButtonsListeners();
}
