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

@Component("acars-send")
@NoArgsConstructor
public class AcarsSendScreen extends AbstractScreen {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Font defaultFont;

    private final Map<Pair<Integer, Integer>, Runnable> screenButtonsListeners = new HashMap<>();

    private TimePanel timePanel = new TimePanel("IN PROG");

    private boolean activated = false;

    @PostConstruct
    private void init() {
        screenButtonsListeners.put(new Pair<>(1, 6), () -> {
            if (!activated)
                return;

            val screen = context.getBean("acars-init", AbstractScreen.class);
            val mcduView = context.getBean(MCDUView.class);

            mcduView.setScreen(screen);
        });

        screenButtonsListeners.put(new Pair<>(2, 1), () -> {
            if (!activated)
                return;

            val screen = context.getBean("aoc-menu", AbstractScreen.class);
            val mcduView = context.getBean(MCDUView.class);

            mcduView.setScreen(screen);
        });
    }

    @Override
    public void construct(double sizeModifier) {
        val font = defaultFont.deriveFont((float) (20 * sizeModifier));

//        ------   header   ------
        val header = new JLabel("ACARS-APPLICATION MENU", JLabel.CENTER);
        header.setFont(font);
        header.setForeground(Colors.WHITE);

        var constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 1.6;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;

        add(header, constraints);

//        ------   1 1   ------
        val field11 = new JLabel("<TECHNICAL", JLabel.LEFT);
        field11.setFont(font);
        field11.setForeground(Colors.WHITE);

        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 1;
        constraints.weighty = 1;

        add(field11, constraints);

//        ------   1 2   ------
        add(Box.createVerticalStrut(field11.getPreferredSize().height), constraints);

//        ------   1 3   ------
        add(Box.createVerticalStrut(field11.getPreferredSize().height), constraints);

//        ------   1 4   ------
        add(Box.createVerticalStrut(field11.getPreferredSize().height), constraints);

//        ------   1 5   ------
        add(Box.createVerticalStrut(field11.getPreferredSize().height), constraints);

//        ------   1 6   ------
        val field16 = new JLabel("<RETURN", JLabel.LEFT);
        field16.setFont(font);
        field16.setForeground(Colors.WHITE);

        add(field16, constraints);

//        ------   2 1   ------
        val field21 = new JLabel("AOC ACARS>", JLabel.RIGHT);
        field21.setFont(font);
        field21.setForeground(Colors.WHITE);

        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.weightx = 0;

        add(field21, constraints);

//        ------   2 6   ------
        val field26 = new JPanel(new GridBagLayout());
        field26.setOpaque(false);
        {
//            ------   spacer   ------
            var _constraints = new GridBagConstraints();
            _constraints.gridy = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field26.add(Box.createGlue(), _constraints);

//            ------   utc   ------
            val utc = new JLabel("UTC");
            utc.setFont(font);
            utc.setForeground(Colors.BROWN);

            _constraints.weightx = 0;

            field26.add(utc, _constraints);

//            ------   asterisk   ------
            val asterisk = new JLabel("*");
            asterisk.setFont(font);
            asterisk.setForeground(Colors.BLUE);

            field26.add(asterisk, _constraints);
        }

        constraints.gridy = 6;

        add(field26, constraints);

//        ------   time   ------
        val time = new JPanel(new GridBagLayout());
        time.setOpaque(false);
        {
//            ------   top spacer   ------
            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.weighty = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            time.add(Box.createGlue(), _constraints);

//            ------   timePanel   ------
            timePanel.setFont(font);
            timePanel.setForeground(Colors.GREEN);

            _constraints.weighty = 0;

            time.add(timePanel, _constraints);

//            ------   bottom spacer   ------
            _constraints.weighty = 0.1;

            time.add(Box.createGlue(), _constraints);
        }

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.gridheight = 8;

        add(time, constraints);

//        ------   footer   ------
        val stub = new JLabel("0123456789A");
        stub.setFont(font);

        constraints.gridx = 2;
        constraints.gridy = 7;
        constraints.gridheight = 1;
        constraints.weighty = 0.5;

        add(Box.createRigidArea(stub.getPreferredSize()), constraints);

//        ---
//        for (var c : getComponents()) {
//            ((JComponent) c).setBorder(BorderFactory.createLineBorder(Color.WHITE));
//        }
    }

    void activate(final boolean activated) {
        if (activated)
            timePanel.setText("SATCOM");
        else
            timePanel.setText("IN PROG");

        this.activated = activated;
    }

    @Override
    Map<Pair<Integer, Integer>, Runnable> getScreenButtonsListeners() {
        return screenButtonsListeners;
    }
}
