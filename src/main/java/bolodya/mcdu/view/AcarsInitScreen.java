package bolodya.mcdu.view;

import bolodya.mcdu.controller.Keyboard;
import bolodya.mcdu.controller.KeyboardListener;
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

@Component("acars-init")
@NoArgsConstructor
public class AcarsInitScreen extends AbstractScreen {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Font defaultFont;

    @Autowired
    private Keyboard keyboard;

    private final Map<Pair<Integer, Integer>, Runnable> screenButtonsListeners = new HashMap<>();

    private final JPanel fltEmpty = new JPanel(new GridBagLayout());
    private final JLabel flt = new JLabel("0FLT", JLabel.LEFT);

    private final JLabel deptEmpty = new JLabel("□□□□");
    private final JLabel dept = new JLabel("0DEPT", JLabel.LEFT);

    private final JLabel fuelEmpty = new JLabel("[    ] LB", JLabel.LEFT);
    private final JLabel fuel = new JLabel("0FUEL", JLabel.LEFT);

    private final JLabel gwEmpty = new JLabel("[  .] LB/1000", JLabel.LEFT);
    private final JLabel gw = new JLabel("0GW", JLabel.LEFT);

    private final JLabel destEmpty = new JLabel("□□□□", JLabel.RIGHT);
    private final JLabel dest = new JLabel("0DEST", JLabel.RIGHT);

    private final JLabel eteEmpty = new JLabel("□□:□□", JLabel.RIGHT);
    private final JLabel ete = new JLabel("0ETE", JLabel.RIGHT);

    private final JLabel schEmpty = new JLabel("□□:□□", JLabel.RIGHT);
    private final JLabel sch = new JLabel("0SCH", JLabel.RIGHT);

    private final JLabel send = new JLabel("SEND*", JLabel.RIGHT);

    private final JLabel input = new JLabel("", JLabel.LEFT);

    private boolean clear = false;
    private KeyboardListener kbListener = new KeyboardListener() {
        @Override
        public void printCharacter(char character) {
            if (clear) {
                input.setText("" + character);
                clear = false;
            } else {
                input.setText(input.getText() + character);
            }
        }

        @Override
        public void clear() {
            if (clear || input.getText().isEmpty()) {
                input.setText("<CLR>");
                clear = true;
            } else {
                input.setText(input.getText().substring(0, input.getText().length() - 1));
            }
        }
    };

    @PostConstruct
    private void init() {
        screenButtonsListeners.put(new Pair<>(1, 6), () -> {
            val acarsMenuScreen = context.getBean("acars-menu", AbstractScreen.class);
            val mcduView = context.getBean(MCDUView.class);

            mcduView.setScreen(acarsMenuScreen);
        });
    }

    @Override
    void construct(double sizeModifier) {
        val font = defaultFont.deriveFont((float) (20 * sizeModifier));
        val littleFont = defaultFont.deriveFont((float) (17 * sizeModifier));

//        ------   header   ------
        val header = new JLabel("ACARS-INIT DATA", JLabel.CENTER);
        header.setFont(font);
        header.setForeground(Colors.WHITE);

        var constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 2.2;
        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.fill = GridBagConstraints.BOTH;

        add(header, constraints);

//        ------   1 1   ------
        val field11 = new JPanel(new GridBagLayout());
        field11.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("FLT NO", JLabel.LEFT);
            label.setFont(littleFont);
            label.setForeground(Colors.WHITE);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.gridy = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field11.add(label, _constraints);

//            ------   fltEmpty   ------
            fltEmpty.setOpaque(false);
            {
//                ------   boxes   ------
                val boxes = new JLabel("□□□□", JLabel.LEFT);
                boxes.setFont(font);
                boxes.setForeground(Colors.BROWN);

                var __constraints = new GridBagConstraints();
                __constraints.gridy = 0;
                __constraints.weightx = 0;
                __constraints.weighty = 1;
                __constraints.fill = GridBagConstraints.BOTH;

                fltEmpty.add(boxes, __constraints);

//                ------   slash   ------
                val slash = new JLabel("/", JLabel.LEFT);
                slash.setFont(font);
                slash.setForeground(Colors.WHITE);

                fltEmpty.add(slash, __constraints);

//                ------   numbers   ------
                val numbers = new JLabel("01", JLabel.LEFT);
                numbers.setFont(font);
                numbers.setForeground(Colors.BLUE);

                fltEmpty.add(numbers, __constraints);

//                ------   spacer   ------
                __constraints.weightx = 1;

                fltEmpty.add(Box.createGlue(), __constraints);
            }
            fltEmpty.setPreferredSize(new Dimension(
                    fltEmpty.getPreferredSize().width,
                    font.getSize()));

            _constraints.gridy = 1;

            field11.add(fltEmpty, _constraints);

//            ------   flt   ------
            flt.setFont(font);
            flt.setForeground(Colors.BLUE);
            flt.setPreferredSize(new Dimension(
                    flt.getPreferredSize().width,
                    font.getSize()));
            flt.setVisible(false);

            field11.add(flt, _constraints);
        }

        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 1;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.BOTH;

        add(field11, constraints);

//        ------   1 2   ------
        val field12 = new JPanel(new GridBagLayout());
        field12.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("DEPT", JLabel.LEFT);
            label.setFont(littleFont);
            label.setForeground(Colors.WHITE);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.gridy = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field12.add(label, _constraints);

//            ------   deptEmpty   ------
            deptEmpty.setFont(font);
            deptEmpty.setForeground(Colors.BROWN);
            deptEmpty.setPreferredSize(new Dimension(
                    deptEmpty.getPreferredSize().width,
                    font.getSize()));

            _constraints.gridy = 1;

            field12.add(deptEmpty, _constraints);

//            ------   dept   ------
            dept.setFont(font);
            dept.setForeground(Colors.BLUE);
            dept.setPreferredSize(new Dimension(
                    dept.getPreferredSize().width,
                    font.getSize()));
            dept.setVisible(false);

            field12.add(dept, _constraints);
        }

        add(field12, constraints);

//        ------   1 3   ------
        val field13 = new JPanel(new GridBagLayout());
        field13.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("FUEL QTY", JLabel.LEFT);
            label.setFont(littleFont);
            label.setForeground(Colors.WHITE);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.gridy = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field13.add(label, _constraints);

//            ------   fuelEmpty   ------
            fuelEmpty.setFont(font);
            fuelEmpty.setForeground(Colors.BLUE);
            fuelEmpty.setPreferredSize(new Dimension(
                    fuelEmpty.getPreferredSize().width,
                    font.getSize()));

            _constraints.gridy = 1;

            field13.add(fuelEmpty, _constraints);

//            ------   fuel   ------
            fuel.setFont(font);
            fuel.setForeground(Colors.BLUE);
            fuel.setPreferredSize(new Dimension(
                    fuel.getPreferredSize().width,
                    font.getSize()));
            fuel.setVisible(false);

            field13.add(fuel, _constraints);
        }

        add(field13, constraints);

//        ------   1 4   ------
        val field14 = new JPanel(new GridBagLayout());
        field14.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("GW", JLabel.LEFT);
            label.setFont(littleFont);
            label.setForeground(Colors.WHITE);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.gridy = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field14.add(label, _constraints);

//            ------   gwEmpty   ------
            gwEmpty.setFont(font);
            gwEmpty.setForeground(Colors.BLUE);
            gwEmpty.setPreferredSize(new Dimension(
                    gwEmpty.getPreferredSize().width,
                    font.getSize()));

            _constraints.gridy = 1;

            field14.add(gwEmpty, _constraints);

//            ------   gw   ------
            gw.setFont(font);
            gw.setForeground(Colors.BLUE);
            gw.setPreferredSize(new Dimension(
                    gw.getPreferredSize().width,
                    font.getSize()));
            gw.setVisible(false);

            field14.add(gw, _constraints);
        }

        add(field14, constraints);

//        ------   1 5   ------
        val field15 = new JPanel(new GridBagLayout());
        field15.setOpaque(false);
        {
//            ------   top spacer   ------
            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.weightx = 1;
            _constraints.weighty = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field15.add(Box.createGlue(), _constraints);

//            ------   label   ------
            var label = new JLabel("<CLR INIT", JLabel.LEFT);
            label.setFont(font);
            label.setForeground(Colors.WHITE);

            _constraints.weighty = 0;

            field15.add(label, _constraints);

//            ------   bottom spacer   ------
            _constraints.weighty = 0.5;

            field15.add(Box.createGlue(), _constraints);
        }

        constraints.weighty = 2.5;

        add(field15, constraints);

//        ------   1 6   ------
        val field16 = new JLabel("<RETURN", JLabel.LEFT);
        field16.setFont(font);
        field16.setForeground(Colors.WHITE);
        field16.setPreferredSize(new Dimension(
                field16.getPreferredSize().width,
                font.getSize()));

        constraints.weighty = 2;
        constraints.fill = GridBagConstraints.BOTH;

        add(field16, constraints);

//        ------   header right   ------
        val headerRight = new JLabel("1/2", JLabel.RIGHT);
        headerRight.setFont(littleFont);
        headerRight.setForeground(Colors.WHITE);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weighty = 0;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        add(headerRight, constraints);

//        ------   2 1   ------
        val field21 = new JPanel(new GridBagLayout());
        field21.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("Z DATE", JLabel.RIGHT);
            label.setFont(littleFont);
            label.setForeground(Colors.WHITE);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field21.add(label, _constraints);

//            ------   date   ------
            val date = new JLabel("01", JLabel.RIGHT);
            date.setFont(font);
            date.setForeground(Colors.GREEN);
            date.setPreferredSize(new Dimension(
                    date.getPreferredSize().width,
                    font.getSize()));

            field21.add(date, _constraints);
        }

        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.BOTH;

        add(field21, constraints);

//        ------   2 2   ------
        val field22 = new JPanel(new GridBagLayout());
        field22.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("DEST", JLabel.RIGHT);
            label.setFont(littleFont);
            label.setForeground(Colors.WHITE);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.gridy = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field22.add(label, _constraints);

//            ------   destEmpty   ------
            destEmpty.setFont(font);
            destEmpty.setForeground(Colors.BROWN);
            destEmpty.setPreferredSize(new Dimension(
                    destEmpty.getPreferredSize().width,
                    font.getSize()));

            _constraints.gridy = 1;

            field22.add(destEmpty, _constraints);

//            ------   dest   ------
            dest.setFont(font);
            dest.setForeground(Colors.BLUE);
            dest.setPreferredSize(new Dimension(
                    dest.getPreferredSize().width,
                    font.getSize()));
            dest.setVisible(false);

            field22.add(dest, _constraints);
        }

        add(field22, constraints);

//        ------   2 3   ------
        val field23 = new JPanel(new GridBagLayout());
        field23.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("ETE", JLabel.RIGHT);
            label.setFont(littleFont);
            label.setForeground(Colors.WHITE);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.gridy = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field23.add(label, _constraints);

//            ------   eteEmpty   ------
            eteEmpty.setFont(font);
            eteEmpty.setForeground(Colors.BROWN);
            eteEmpty.setPreferredSize(new Dimension(
                    eteEmpty.getPreferredSize().width,
                    font.getSize()));

            _constraints.gridy = 1;

            field23.add(eteEmpty, _constraints);

//            ------   ete   ------
            ete.setFont(font);
            ete.setForeground(Colors.BLUE);
            ete.setPreferredSize(new Dimension(
                    ete.getPreferredSize().width,
                    font.getSize()));
            ete.setVisible(false);

            field23.add(ete, _constraints);
        }

        add(field23, constraints);

//        ------   2 4   ------
        val field24 = new JPanel(new GridBagLayout());
        field24.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("SCH BLOCK", JLabel.RIGHT);
            label.setFont(littleFont);
            label.setForeground(Colors.WHITE);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.gridy = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field24.add(label, _constraints);

//            ------   schEmpty   ------
            schEmpty.setFont(font);
            schEmpty.setForeground(Colors.BROWN);
            schEmpty.setPreferredSize(new Dimension(
                    schEmpty.getPreferredSize().width,
                    font.getSize()));

            _constraints.gridy = 1;

            field24.add(schEmpty, _constraints);

//            ------   sch   ------
            sch.setFont(font);
            sch.setForeground(Colors.BLUE);
            sch.setPreferredSize(new Dimension(
                    sch.getPreferredSize().width,
                    font.getSize()));
            sch.setVisible(false);

            field24.add(sch, _constraints);
        }

        add(field24, constraints);

//        ------   2 5   ------
        val field25 = new JPanel(new GridBagLayout());
        field25.setOpaque(false);
        {
//            ------   top spacer   ------
            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.weightx = 1;
            _constraints.weighty = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field25.add(Box.createGlue(), _constraints);

//            ------   send   ------
            send.setFont(font);
            send.setForeground(Colors.BLUE);

            _constraints.weighty = 0;

            field25.add(send, _constraints);

//            ------   bottom spacer   ------
            _constraints.weighty = 0.5;

            field25.add(Box.createGlue(), _constraints);
        }

        add(field25, constraints);

//        ------   2 6   ------
        val field26 = new JPanel();
        field26.setOpaque(false);
        field26.setLayout(new GridBagLayout());
        {
//            ------   spacer   ------
            var _constraints = new GridBagConstraints();
            _constraints.gridy = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field26.add(Box.createGlue(), _constraints);

//            ------   power   ------
            val power = new JLabel("UTC");
            power.setFont(font);
            power.setForeground(Colors.BROWN);
            power.setPreferredSize(new Dimension(
                    power.getPreferredSize().width,
                    font.getSize()));

            _constraints.weightx = 0;

            field26.add(power, _constraints);

//            ------   asterisk   ------
            val asterisk = new JLabel("*");
            asterisk.setFont(font);
            asterisk.setForeground(Colors.BLUE);
            asterisk.setPreferredSize(new Dimension(
                    asterisk.getPreferredSize().width,
                    font.getSize()));

            field26.add(asterisk, _constraints);
        }

        constraints.fill = GridBagConstraints.BOTH;

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
            var timePanel = new TimePanel();
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

//        ------   input   ------
        input.setFont(font);
        input.setForeground(Colors.WHITE);

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.weighty = 1;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        add(input, constraints);

//        ------   footer spacer   ------
        val stub = new JLabel("0123456789A");
        stub.setFont(font);

        constraints.gridx = 2;
        constraints.gridwidth = 1;

        add(Box.createRigidArea(stub.getPreferredSize()), constraints);

//        ---
//        for (var c : getComponents()) {
//            ((JComponent) c).setBorder(BorderFactory.createLineBorder(Color.WHITE));
//        }
    }

    @Override
    Map<Pair<Integer, Integer>, Runnable> getScreenButtonsListeners() {
        return screenButtonsListeners;
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible)
            keyboard.addListener(kbListener);
        else
            keyboard.removeListener(kbListener);

        super.setVisible(visible);
    }
}
