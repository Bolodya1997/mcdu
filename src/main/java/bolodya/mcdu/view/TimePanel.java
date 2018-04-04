package bolodya.mcdu.view;

import lombok.experimental.var;
import lombok.val;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

class TimePanel extends JPanel {

    private JLabel textLabel = new JLabel("SATCOM");
    private JLabel timeLabel = new JLabel();

    TimePanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());

//        ------   spacer   ------
        var constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        add(Box.createGlue(), constraints);

//        ------   textLabel   ------
        constraints.weighty = 0;

        add(textLabel, constraints);

//        ------   timeLabel   ------
        add(timeLabel, constraints);

        resetText();
        new Timer(200, e -> resetText()).start();
    }

    private void resetText() {
        val time = Calendar.getInstance();

        timeLabel.setText(String.format("%02d:%02d",
                time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE)));
    }

    @Override
    public void setFont(Font font) {
        if (textLabel == null)
            return;

        textLabel.setFont(font);
        timeLabel.setFont(font);
    }

    @Override
    public void setForeground(Color fg) {
        if (textLabel == null)
            return;

        textLabel.setForeground(fg);
        timeLabel.setForeground(fg);
    }
}
