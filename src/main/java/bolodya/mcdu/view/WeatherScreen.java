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

@Component("weather")
@NoArgsConstructor
public class WeatherScreen extends AbstractDataScreen {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Font defaultFont;

    private final Map<Pair<Integer, Integer>, Runnable> screenButtonsListeners = new HashMap<>();

    private final JLabel station = new JLabel("RJKT 281300Z 27005KT", JLabel.LEFT);

    @PostConstruct
    private void init() {
        screenButtonsListeners.put(new Pair<>(1, 6), () -> {
            val screen = context.getBean("aoc-menu", AbstractScreen.class);
            val mcduView = context.getBean(MCDUView.class);

            mcduView.setScreen(screen);
        });
    }

    @Override
    void passData(Object data) {
        station.setText(String.format("%s 281300Z 27005KT", data));
    }

    @Override
    void construct(double sizeModifier) {
        val font = defaultFont.deriveFont((float) (20 * sizeModifier));
        val littleFont = defaultFont.deriveFont((float) (17 * sizeModifier));

//        ------   first   ------
        val first = new JLabel("WEATHER UPLINK", JLabel.LEFT);
        first.setFont(font);
        first.setForeground(Colors.WHITE);

        var constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;

        add(first, constraints);

//        ------   second   ------
        val second = new JLabel("2018/05/04 13:00", JLabel.LEFT);
        second.setFont(font);
        second.setForeground(Colors.WHITE);

        add(second, constraints);

//        ------   station   ------
        station.setFont(font);
        station.setForeground(Colors.WHITE);

        add(station, constraints);

//        ------   fourth   ------
        val fourth = new JLabel("CAVOK 08/M01 Q1024 NOSIG", JLabel.LEFT);
        fourth.setFont(font);
        fourth.setForeground(Colors.WHITE);

        add(fourth, constraints);

//        ------   delim   ------
        val delim = new JLabel("--------------------------------", JLabel.LEFT);
        delim.setFont(font);
        delim.setForeground(Colors.WHITE);
        delim.setPreferredSize(new Dimension(
                delim.getPreferredSize().width,
                font.getSize()));

        constraints.weighty = 2;
        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        add(delim, constraints);

//        ------   print   ------
        val print = new JLabel("PRINT*", JLabel.RIGHT);
        print.setFont(font);
        print.setForeground(Colors.WHITE);
        print.setPreferredSize(new Dimension(
                print.getPreferredSize().width,
                font.getSize()));

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.weighty = 0;

        add(print, constraints);

//        ------   time   ------
        val time = new JLabel("22:14", JLabel.RIGHT);
        time.setFont(littleFont);
        time.setForeground(Colors.WHITE);
        time.setPreferredSize(new Dimension(
                time.getPreferredSize().width,
                littleFont.getSize()));

        add(time, constraints);

//        ------   weather   ------
        val weather = new JLabel("WEATHER>", JLabel.RIGHT);
        weather.setFont(font);
        weather.setForeground(Colors.WHITE);
        weather.setPreferredSize(new Dimension(
                weather.getPreferredSize().width,
                font.getSize()));

        add(weather, constraints);

//        ------   aoc   ------
        val aoc = new JLabel("<AOC MENU", JLabel.LEFT);
        aoc.setFont(font);
        aoc.setForeground(Colors.WHITE);
        aoc.setPreferredSize(new Dimension(
                aoc.getPreferredSize().width,
                font.getSize()));

        constraints.gridx = 0;
        constraints.gridy = 7;

        add(aoc, constraints);

//        ------   footer   ------
        val footer = new JLabel("ACARS UPLINK", JLabel.LEFT);
        footer.setFont(font);
        footer.setForeground(Colors.WHITE);
        footer.setPreferredSize(new Dimension(
                footer.getPreferredSize().width,
                font.getSize()));

        constraints.gridy = 8;
        constraints.gridwidth = 2;
        constraints.weighty = 0.5;

        add(footer, constraints);
    }

    @Override
    Map<Pair<Integer, Integer>, Runnable> getScreenButtonsListeners() {
        return screenButtonsListeners;
    }
}
