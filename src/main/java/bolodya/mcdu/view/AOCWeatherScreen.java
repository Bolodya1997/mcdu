package bolodya.mcdu.view;

import bolodya.mcdu.controller.Keyboard;
import bolodya.mcdu.controller.KeyboardListener;
import bolodya.mcdu.controller.Sender;
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

@Component("aoc-weather")
@NoArgsConstructor
public class AOCWeatherScreen extends AbstractScreen {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Font defaultFont;

    @Autowired
    private Keyboard keyboard;

    private final Map<Pair<Integer, Integer>, Runnable> screenButtonsListeners = new HashMap<>();

    private final JLabel actualSel = new JLabel(" <SEL>", JLabel.LEFT);

    private final JLabel forecastSel = new JLabel(" <SEL>", JLabel.LEFT);

    private final JLabel atisSel = new JLabel(" <SEL>", JLabel.LEFT);

    private final JLabel station = new JLabel("STUB", JLabel.RIGHT);

    private final JLabel send = new JLabel("SEND*", JLabel.RIGHT);

    private final JLabel vhf = new JLabel("VHF IN PROG", JLabel.RIGHT);

    private final JLabel weatherTime = new JLabel("22:14", JLabel.RIGHT);
    private final JLabel weather = new JLabel("WEATHER>", JLabel.RIGHT);

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

    private boolean sending = false;

    @PostConstruct
    private void init() {
        screenButtonsListeners.put(new Pair<>(1, 1), () -> {
            if (sending)
                return;

            actualSel.setForeground(Colors.GREEN);
            forecastSel.setForeground(Color.BLACK);
            atisSel.setForeground(Color.BLACK);

            setSendVisible();
        });

        screenButtonsListeners.put(new Pair<>(1, 2), () -> {
            if (sending)
                return;

            actualSel.setForeground(Color.BLACK);
            forecastSel.setForeground(Colors.GREEN);
            atisSel.setForeground(Color.BLACK);

            setSendVisible();
        });

        screenButtonsListeners.put(new Pair<>(1, 4), () -> {
            if (sending)
                return;

            actualSel.setForeground(Color.BLACK);
            forecastSel.setForeground(Color.BLACK);
            atisSel.setForeground(Colors.GREEN);

            setSendVisible();
        });

        screenButtonsListeners.put(new Pair<>(2, 1), () -> {
            if (sending)
                return;

            if (clear) {
                clear = false;

                station.setForeground(Color.BLACK);
            } else if (input.getText().length() == 4) {
                station.setText(input.getText());

                station.setForeground(Colors.WHITE);
            } else {
                errorInput();
                return;
            }
            input.setText("");

            setSendVisible();
        });

        screenButtonsListeners.put(new Pair<>(1, 6), () -> {
            if (sending && weather.getForeground().equals(Color.BLACK))
                return;

            actualSel.setForeground(Color.BLACK);
            forecastSel.setForeground(Color.BLACK);
            atisSel.setForeground(Color.BLACK);
            station.setForeground(Color.BLACK);

            input.setText("");
            clear = false;

            vhf.setForeground(Color.BLACK);
            weather.setForeground(Color.BLACK);
            weatherTime.setForeground(Color.BLACK);
            sending = false;

            val screen = context.getBean("aoc-requests", AbstractScreen.class);
            val mcduView = context.getBean(MCDUView.class);

            mcduView.setScreen(screen);
        });

        screenButtonsListeners.put(new Pair<>(2, 5), () -> {
            if (sending || !send.getForeground().equals(Colors.BLUE))
                return;
            sending = true;

            keyboard.removeListener(kbListener);
            vhf.setForeground(Colors.WHITE);

            context.getBean(Sender.class).send(this::receiveSignal);
        });

        screenButtonsListeners.put(new Pair<>(2, 6), () -> {
            if (weather.getForeground().equals(Color.BLACK))
                return;

            actualSel.setForeground(Color.BLACK);
            forecastSel.setForeground(Color.BLACK);
            atisSel.setForeground(Color.BLACK);
            station.setForeground(Color.BLACK);

            input.setText("");
            clear = false;

            vhf.setForeground(Color.BLACK);
            weather.setForeground(Color.BLACK);
            weatherTime.setForeground(Color.BLACK);
            sending = false;

            val screen = context.getBean("weather", AbstractDataScreen.class);
            val mcduView = context.getBean(MCDUView.class);

            screen.passData(station.getText());

            mcduView.setScreen(screen);
        });
    }

    private void setSendVisible() {
        if ((actualSel.getForeground().equals(Colors.GREEN) ||
                forecastSel.getForeground().equals(Colors.GREEN) ||
                atisSel.getForeground().equals(Colors.GREEN)) &&
                        station.getForeground().equals(Colors.WHITE)) {
            send.setForeground(Colors.BLUE);
        } else {
            send.setForeground(Color.BLACK);
        }
    }

    private void errorInput() {
        input.setForeground(Color.RED);

        val timer = new Timer(200, e -> input.setForeground(Colors.WHITE));
        timer.setRepeats(false);
        timer.start();
    }

    private void receiveSignal() {
        weather.setForeground(Colors.WHITE);
        weatherTime.setForeground(Colors.WHITE);

        input.setText("ACARS UPLINK");
    }

    @Override
    void construct(double sizeModifier) {
        val font = defaultFont.deriveFont((float) (20 * sizeModifier));
        val littleFont = defaultFont.deriveFont((float) (17 * sizeModifier));

//        ------   header   ------
        val header = new JLabel("AOC -WEATHER REQUEST", JLabel.CENTER);
        header.setFont(font);
        header.setForeground(Colors.WHITE);

        var constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 1.8;
        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.fill = GridBagConstraints.BOTH;

        add(header, constraints);

//        ------   1 1   ------
        val field11 = new JPanel(new GridBagLayout());
        field11.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("TYPE", JLabel.LEFT);
            label.setFont(littleFont);
            label.setForeground(Colors.WHITE);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field11.add(label, _constraints);

//            ------   actual   ------
            val actual = new JPanel(new GridBagLayout());
            actual.setOpaque(false);
            {
//                ------   button   ------
                val button = new JLabel("<ACTUAL", JLabel.LEFT);
                button.setFont(font);
                button.setForeground(Colors.WHITE);
                button.setPreferredSize(new Dimension(
                        button.getPreferredSize().width,
                        font.getSize()));

                var __constraints = new GridBagConstraints();
                __constraints.gridy = 0;
                __constraints.fill = GridBagConstraints.BOTH;

                actual.add(button, __constraints);

//                ------   actualSel   ------
                actualSel.setFont(font);
                actualSel.setForeground(Color.BLACK);
                actualSel.setPreferredSize(new Dimension(
                        actualSel.getPreferredSize().width,
                        font.getSize()));

                __constraints.weightx = 1;

                actual.add(actualSel, __constraints);
            }

            field11.add(actual, _constraints);
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
            val label = new JLabel("STUB", JLabel.LEFT);
            label.setFont(littleFont);
            label.setForeground(Color.BLACK);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field12.add(label, _constraints);

//            ------   forecast   ------
            val forecast = new JPanel(new GridBagLayout());
            forecast.setOpaque(false);
            {
//                ------   button   ------
                val button = new JLabel("<FORECAST", JLabel.LEFT);
                button.setFont(font);
                button.setForeground(Colors.WHITE);
                button.setPreferredSize(new Dimension(
                        button.getPreferredSize().width,
                        font.getSize()));

                var __constraints = new GridBagConstraints();
                __constraints.gridy = 0;
                __constraints.fill = GridBagConstraints.BOTH;

                forecast.add(button, __constraints);

//                ------   forecastSel   ------
                forecastSel.setFont(font);
                forecastSel.setForeground(Color.BLACK);
                forecastSel.setPreferredSize(new Dimension(
                        forecastSel.getPreferredSize().width,
                        font.getSize()));

                __constraints.weightx = 1;

                forecast.add(forecastSel, __constraints);
            }

            field12.add(forecast, _constraints);
        }

        add(field12, constraints);

//        ------   1 3   ------
        add(Box.createVerticalStrut(field12.getPreferredSize().height), constraints);

//        ------   1 4   ------
        val field14 = new JPanel(new GridBagLayout());
        field14.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("STUB", JLabel.LEFT);
            label.setFont(littleFont);
            label.setForeground(Color.BLACK);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field14.add(label, _constraints);

//            ------   atis   ------
            val atis = new JPanel(new GridBagLayout());
            atis.setOpaque(false);
            {
//                ------   button   ------
                val button = new JLabel("<ATIS", JLabel.LEFT);
                button.setFont(font);
                button.setForeground(Colors.WHITE);
                button.setPreferredSize(new Dimension(
                        button.getPreferredSize().width,
                        font.getSize()));

                var __constraints = new GridBagConstraints();
                __constraints.gridy = 0;
                __constraints.fill = GridBagConstraints.BOTH;

                atis.add(button, __constraints);

//                ------   atisSel   ------
                atisSel.setFont(font);
                atisSel.setForeground(Color.BLACK);
                atisSel.setPreferredSize(new Dimension(
                        atisSel.getPreferredSize().width,
                        font.getSize()));

                __constraints.weightx = 1;

                atis.add(atisSel, __constraints);
            }

            field14.add(atis, _constraints);
        }

        add(field14, constraints);

//        ------   1 6   ------
        val field16 = new JLabel("<RETURN", JLabel.LEFT);
        field16.setFont(font);
        field16.setForeground(Colors.WHITE);
        field16.setPreferredSize(new Dimension(
                field16.getPreferredSize().width,
                font.getSize()));

        constraints.gridy = 6;
        constraints.weighty = 2;
        constraints.fill = GridBagConstraints.BOTH;

        add(field16, constraints);

//        ------   vhf   ------
        vhf.setFont(littleFont);
        vhf.setForeground(Color.BLACK);
        vhf.setPreferredSize(new Dimension(
                vhf.getPreferredSize().width,
                littleFont.getSize()));

        constraints.weighty = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        add(vhf, constraints);

//        ------   2 1   ------
        val field21 = new JPanel(new GridBagLayout());
        field21.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("STATION 1", JLabel.RIGHT);
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

//            ------   station   ------
            station.setFont(font);
            station.setForeground(Color.BLACK);
            station.setPreferredSize(new Dimension(
                    station.getPreferredSize().width,
                    font.getSize()));

            field21.add(station, _constraints);
        }

        constraints.gridx = 2;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.BOTH;

        add(field21, constraints);

//        ------   2 2   ------
        val field22 = new JPanel(new GridBagLayout());
        field22.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("STATION 2", JLabel.RIGHT);
            label.setFont(littleFont);
            label.setForeground(Colors.WHITE);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field22.add(label, _constraints);

//            ------   station2   ------
            val station2 = new JLabel("----", JLabel.RIGHT);
            station2.setFont(font);
            station2.setForeground(Colors.WHITE);
            station2.setPreferredSize(new Dimension(
                    station2.getPreferredSize().width,
                    font.getSize()));

            field22.add(station2, _constraints);
        }

        add(field22, constraints);

//        ------   2 3   ------
        val field23 = new JPanel(new GridBagLayout());
        field23.setOpaque(false);
        {
//            ------   label   ------
            val label = new JLabel("STATION 3", JLabel.RIGHT);
            label.setFont(littleFont);
            label.setForeground(Colors.WHITE);
            label.setPreferredSize(new Dimension(
                    label.getPreferredSize().width,
                    littleFont.getSize()));

            var _constraints = new GridBagConstraints();
            _constraints.gridx = 0;
            _constraints.weightx = 1;
            _constraints.fill = GridBagConstraints.BOTH;

            field23.add(label, _constraints);

//            ------   station3   ------
            val station3 = new JLabel("----", JLabel.RIGHT);
            station3.setFont(font);
            station3.setForeground(Colors.WHITE);
            station3.setPreferredSize(new Dimension(
                    station3.getPreferredSize().width,
                    font.getSize()));

            field23.add(station3, _constraints);
        }

        add(field23, constraints);

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
//            send.setForeground(Colors.BLUE);
            setSendVisible();

            _constraints.weighty = 0;

            field25.add(send, _constraints);

//            ------   bottom spacer   ------
            _constraints.weighty = 0.1;

            field25.add(Box.createGlue(), _constraints);
        }

        constraints.gridy = 5;
        constraints.weighty = 2.5;

        add(field25, constraints);

//        ------   2 6   ------
        weather.setFont(font);
        weather.setForeground(Color.BLACK);

        constraints.gridy = 6;
        constraints.weighty = 0;

        add(weather, constraints);

//        ------   weatherTime   ------
        weatherTime.setFont(littleFont);
        weatherTime.setForeground(Color.BLACK);
        weatherTime.setPreferredSize(new Dimension(
                weatherTime.getPreferredSize().width,
                littleFont.getSize()));

        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        add(weatherTime, constraints);

//        ------   input   ------
        input.setFont(font);
        input.setForeground(Colors.WHITE);
        input.setPreferredSize(new Dimension(
                input.getPreferredSize().width,
                font.getSize()));

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.weighty = 1.2;
        constraints.weightx = 0;
        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        add(input, constraints);

//        ------   footer spacer   ------
        val stub = new JLabel("STUB");
        stub.setFont(font);
        stub.setForeground(Color.BLACK);
        stub.setPreferredSize(new Dimension(
                stub.getPreferredSize().width,
                font.getSize()));

        constraints.gridx = 2;
        constraints.gridwidth = 1;

        add(stub, constraints);

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

        setSendVisible();

        super.setVisible(visible);
    }
}
