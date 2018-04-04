package bolodya.mcdu.view;

import bolodya.mcdu.controller.Sender;
import lombok.Cleanup;
import lombok.val;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;

@Component
public class PlainPanel extends JPanel implements Sender {

    private static final Image IMAGES[];
    static {
        try {
            val builder = Stream.<Image>builder();

            for (int i = 0; i <= 15; i++) {
                @Cleanup
                val imageInput = PlainPanel.class
                        .getClassLoader()
                        .getResourceAsStream("img/plain/plain" + i + ".png");
                builder.accept(ImageIO.read(imageInput));
            }

            IMAGES = builder.build().toArray(Image[]::new);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Image currentImage = IMAGES[0];

    private final Object lock = new Object();
    private boolean sending = false;

    private Runnable callback;

    @Override
    protected void paintComponent(final Graphics g) {
        g.drawImage(currentImage, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public void send(final Runnable callback) {
        synchronized (lock) {
            if (sending)
                return;

            sending = true;
            this.callback = callback;
        }

        val timer = new Timer(0, null);
        timer.setInitialDelay(0);
        timer.setDelay(1000);

        final int[] i = { 0 };
        timer.addActionListener(e -> {
            if (++i[0] != IMAGES.length) {
                currentImage = IMAGES[i[0]];
            } else {
                currentImage = IMAGES[0];
                timer.stop();

                synchronized (lock) {
                    sending = false;
                    SwingUtilities.invokeLater(this.callback);
                    this.callback = null;
                }
            }

            repaint();
        });

        timer.start();
    }
}
