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

@Component("aoc-requests")
@NoArgsConstructor
public class AOCRequestsScreen extends AbstractScreen {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Font defaultFont;

    private final Map<Pair<Integer, Integer>, Runnable> screenButtonsListeners = new HashMap<>();

    @PostConstruct
    private void init() {
        screenButtonsListeners.put(new Pair<>(1, 6), () -> {
            val screen = context.getBean("aoc-menu", AbstractScreen.class);
            val mcduView = context.getBean(MCDUView.class);

            mcduView.setScreen(screen);
        });

        screenButtonsListeners.put(new Pair<>(1, 3), () -> {
            val screen = context.getBean("aoc-weather", AbstractScreen.class);
            val mcduView = context.getBean(MCDUView.class);

            mcduView.setScreen(screen);
        });
    }

    @Override
    public void construct(double sizeModifier) {
        val font = defaultFont.deriveFont((float) (20 * sizeModifier));

//        ------   header   ------
        val header = new JLabel("AOC -REQUESTS", JLabel.CENTER);
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
        val field11 = new JLabel("<INITIALIZATION", JLabel.LEFT);
        field11.setFont(font);
        field11.setForeground(Colors.WHITE);

        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 1;
        constraints.weighty = 1;

        add(field11, constraints);

//        ------   1 2   ------
        val field12 = new JLabel("<WT/BALANCE", JLabel.LEFT);
        field12.setFont(font);
        field12.setForeground(Colors.WHITE);

        add(field12, constraints);

//        ------   1 3   ------
        val field13 = new JLabel("<WX REQ", JLabel.LEFT);
        field13.setFont(font);
        field13.setForeground(Colors.WHITE);

        add(field13, constraints);
//        ------   1 4   ------
        val field14 = new JLabel("<TO DLY/RTN", JLabel.LEFT);
        field14.setFont(font);
        field14.setForeground(Colors.WHITE);

        add(field14, constraints);

//        ------   1 5   ------
        val field15 = new JLabel("<MESSAGES", JLabel.LEFT);
        field15.setFont(font);
        field15.setForeground(Colors.WHITE);

        add(field15, constraints);

//        ------   1 6   ------
        val field16 = new JLabel("<RETURN", JLabel.LEFT);
        field16.setFont(font);
        field16.setForeground(Colors.WHITE);

        add(field16, constraints);

//        ------   2 1   ------
        val field21 = new JLabel("UTC>", JLabel.RIGHT);
        field21.setFont(font);
        field21.setForeground(Colors.WHITE);

        constraints.gridx = 1;

        add(field21, constraints);

//        ------   2 2   ------
        val field22 = new JLabel("ATC>", JLabel.RIGHT);
        field22.setFont(font);
        field22.setForeground(Colors.WHITE);

        add(field22, constraints);

//        ------   2 3   ------
        val field23 = new JLabel("CALC FUEL>", JLabel.RIGHT);
        field23.setFont(font);
        field23.setForeground(Colors.WHITE);

        add(field23, constraints);

//        ------   2 4   ------
        val field24 = new JLabel("REQUESTS>", JLabel.RIGHT);
        field24.setFont(font);
        field24.setForeground(Colors.WHITE);

        add(field24, constraints);

//        ------   2 5   ------
        val field25 = new JLabel("LINK TEST>", JLabel.RIGHT);
        field25.setFont(font);
        field25.setForeground(Colors.WHITE);

        add(field25, constraints);

//        ------   footer   ------
        val stub = new JLabel("STUB", JLabel.LEFT);
        stub.setFont(font);

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.weighty = 0.5;

        add(Box.createVerticalStrut(stub.getPreferredSize().height), constraints);
    }

    @Override
    public Map<Pair<Integer, Integer>, Runnable> getScreenButtonsListeners() {
        return screenButtonsListeners;
    }
}
