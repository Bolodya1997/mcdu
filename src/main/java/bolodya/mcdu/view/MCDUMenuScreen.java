package bolodya.mcdu.view;

import bolodya.mcdu.util.Pair;
import lombok.NoArgsConstructor;
import lombok.experimental.var;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Component("mcdu-menu")
@NoArgsConstructor
public class MCDUMenuScreen extends AbstractScreen {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Font defaultFont;

    private final Map<Pair<Integer, Integer>, Runnable> screenButtonsListeners = new HashMap<>();

    @PostConstruct
    private void init() {
        screenButtonsListeners.put(new Pair<>(1, 2), () -> {
            val acarsMenuScreen = context.getBean("acars-menu", AbstractScreen.class);
            val mcduView = context.getBean(MCDUView.class);

            mcduView.setScreen(acarsMenuScreen);
        });
    }

    @Override
    public void construct(double sizeModifier) {
        val font = defaultFont.deriveFont((float) (20 * sizeModifier));

//        ------   header   ------
        val header = new JLabel("MCDU MENU", JLabel.CENTER);
        header.setFont(font);
        header.setForeground(Colors.WHITE);

        var constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 1.6;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;

        add(header, constraints);

//        ------   1 1   ------
        val field11 = new JLabel("<FMGC", JLabel.LEFT);
        field11.setFont(font);
        field11.setForeground(Colors.GREEN);

        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 1;
        constraints.weighty = 1;

        add(field11, constraints);

//        ------   1 2   ------
        val field12 = new JLabel("<ACARS", JLabel.LEFT);
        field12.setFont(font);
        field12.setForeground(Colors.WHITE);

        add(field12, constraints);

//        ------   1 3   ------
        add(Box.createVerticalStrut(field12.getPreferredSize().height), constraints);

//        ------   1 4   ------
        val field14 = new JLabel("<CFDS", JLabel.LEFT);
        field14.setFont(font);
        field14.setForeground(Colors.WHITE);

        add(field14, constraints);

//        ------   1 5   ------
        add(Box.createVerticalStrut(field14.getPreferredSize().height), constraints);

//        ------   2 4   ------
        val field24 = new JLabel("MCDU MAINT>", JLabel.RIGHT);
        field24.setFont(font);
        field24.setForeground(Colors.WHITE);

        constraints.gridx = 1;
        constraints.gridy = 4;

        add(field24, constraints);

//        ------   2 6   ------
        val field26 = new JLabel("RETURN>", JLabel.RIGHT);
        field26.setFont(font);
        field26.setForeground(Colors.WHITE);

        constraints.gridy = 6;

        add(field26, constraints);

//        ------   footer   ------
        val footer = new JLabel("SELECT DESIRED SYSTEM", JLabel.LEFT);
        footer.setVerticalAlignment(JLabel.BOTTOM);
        footer.setFont(font);
        footer.setForeground(Colors.WHITE);

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.weighty = 0.5;

        add(footer, constraints);
    }

    @Override
    public Map<Pair<Integer, Integer>, Runnable> getScreenButtonsListeners() {
        return screenButtonsListeners;
    }
}
