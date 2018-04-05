package bolodya.mcdu.view;

import bolodya.mcdu.util.Pair;
import bolodya.mcdu.util.SafeRunnable;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class MainFrame extends JFrame implements MCDUView {

    class MainPanel extends JPanel {
        private final Image BACKGROUND;

        MainPanel() {
            try {
                @Cleanup
                val inputStream = MainFrame.class
                        .getClassLoader()
                        .getResourceAsStream("img/mcdu.png");

                BACKGROUND = ImageIO.read(inputStream)
                        .getScaledInstance(MainFrame.WIDTH, MainFrame.HEIGHT,
                                Image.SCALE_SMOOTH);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(BACKGROUND, 0, 0, null);
        }
    }

    private static final double SIZE_MODIFIER = 3.0 / 4.0;

    private static final int WIDTH = (int) (560 * SIZE_MODIFIER);
    private static final int HEIGHT = (int) (879 * SIZE_MODIFIER);

    private final MainPanel mainPanel = new MainPanel();

    private Consumer<Character> characterListener;
    private Runnable clearListener;

    private Map<Pair<Integer, Integer>, Runnable> screenButtonsListeners = new HashMap<>();

    private final Set<JComponent> screens = new HashSet<>();

    public MainFrame(@Autowired ApplicationContext context) {
        super("MCDU");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//        ------   size   ------
        setSize(WIDTH * 2, HEIGHT);
        setResizable(false);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                val size = getContentPane().getSize();

                setSize((WIDTH * 2) + ((WIDTH * 2) - size.width),
                        HEIGHT + (HEIGHT - size.height));
            }
        });

//        ------   panels   ------
        setLayout(new GridLayout(1, 2));
        add(mainPanel);
        add(context.getBean(PlainPanel.class));

//        ------   keyboard   ------
        final Supplier<Boolean> hasCharacterListener = () -> characterListener != null;
        final Supplier<Boolean> hasClearListener = () -> clearListener != null;

        val buttonA = createButton(
                "img/buttons/A.png",
                "img/selected/A.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('A')));
        buttonA.setLocation(                        //  1 1
                (int) (229 * SIZE_MODIFIER),
                (int) (543 * SIZE_MODIFIER));
        mainPanel.add(buttonA);

        val buttonB = createButton(
                "img/buttons/B.png",
                "img/selected/B.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('B')));
        buttonB.setLocation(                        //  2 1
                (int) (285 * SIZE_MODIFIER),
                (int) (543 * SIZE_MODIFIER));
        mainPanel.add(buttonB);

        val buttonC = createButton(
                "img/buttons/C.png",
                "img/selected/C.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('C')));
        buttonC.setLocation(                        //  3 1
                (int) (340 * SIZE_MODIFIER),
                (int) (543 * SIZE_MODIFIER));
        mainPanel.add(buttonC);

        val buttonD = createButton(
                "img/buttons/D.png",
                "img/selected/D.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('D')));
        buttonD.setLocation(                        //  4 1
                (int) (394 * SIZE_MODIFIER),
                (int) (543 * SIZE_MODIFIER));
        mainPanel.add(buttonD);

        val buttonE = createButton(
                "img/buttons/E.png",
                "img/selected/E.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('E')));
        buttonE.setLocation(                        //  5 1
                (int) (450 * SIZE_MODIFIER),
                (int) (543 * SIZE_MODIFIER));
        mainPanel.add(buttonE);

        val buttonF = createButton(
                "img/buttons/F.png",
                "img/selected/F.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('F')));
        buttonF.setLocation(                        //  1 2
                (int) (229 * SIZE_MODIFIER),
                (int) (595 * SIZE_MODIFIER));
        mainPanel.add(buttonF);

        val buttonG = createButton(
                "img/buttons/G.png",
                "img/selected/G.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('G')));
        buttonG.setLocation(                        //  2 2
                (int) (285 * SIZE_MODIFIER),
                (int) (595 * SIZE_MODIFIER));
        mainPanel.add(buttonG);

        val buttonH = createButton(
                "img/buttons/H.png",
                "img/selected/H.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('H')));
        buttonH.setLocation(                        //  3 2
                (int) (340 * SIZE_MODIFIER),
                (int) (595 * SIZE_MODIFIER));
        mainPanel.add(buttonH);

        val buttonI = createButton(
                "img/buttons/I.png",
                "img/selected/I.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('I')));
        buttonI.setLocation(                        //  4 2
                (int) (394 * SIZE_MODIFIER),
                (int) (595 * SIZE_MODIFIER));
        mainPanel.add(buttonI);

        val buttonJ = createButton(
                "img/buttons/J.png",
                "img/selected/J.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('J')));
        buttonJ.setLocation(                        //  5 2
                (int) (450 * SIZE_MODIFIER),
                (int) (595 * SIZE_MODIFIER));
        mainPanel.add(buttonJ);

        val buttonK = createButton(
                "img/buttons/K.png",
                "img/selected/K.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('K')));
        buttonK.setLocation(                        //  1 3
                (int) (229 * SIZE_MODIFIER),
                (int) (647 * SIZE_MODIFIER));
        mainPanel.add(buttonK);

        val buttonL = createButton(
                "img/buttons/L.png",
                "img/selected/L.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('L')));
        buttonL.setLocation(                        //  2 3
                (int) (285 * SIZE_MODIFIER),
                (int) (647 * SIZE_MODIFIER));
        mainPanel.add(buttonL);

        val buttonM = createButton(
                "img/buttons/M.png",
                "img/selected/M.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('M')));
        buttonM.setLocation(                        //  3 3
                (int) (340 * SIZE_MODIFIER),
                (int) (647 * SIZE_MODIFIER));
        mainPanel.add(buttonM);

        val buttonN = createButton(
                "img/buttons/N.png",
                "img/selected/N.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('N')));
        buttonN.setLocation(                        //  4 3
                (int) (394 * SIZE_MODIFIER),
                (int) (647 * SIZE_MODIFIER));
        mainPanel.add(buttonN);

        val buttonO = createButton(
                "img/buttons/O.png",
                "img/selected/O.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('O')));
        buttonO.setLocation(                        //  5 3
                (int) (450 * SIZE_MODIFIER),
                (int) (647 * SIZE_MODIFIER));
        mainPanel.add(buttonO);

        val buttonP = createButton(
                "img/buttons/P.png",
                "img/selected/P.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('P')));
        buttonP.setLocation(                        //  1 4
                (int) (229 * SIZE_MODIFIER),
                (int) (698 * SIZE_MODIFIER));
        mainPanel.add(buttonP);

        val buttonQ = createButton(
                "img/buttons/Q.png",
                "img/selected/Q.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('Q')));
        buttonQ.setLocation(                        //  2 4
                (int) (285 * SIZE_MODIFIER),
                (int) (698 * SIZE_MODIFIER));
        mainPanel.add(buttonQ);

        val buttonR = createButton(
                "img/buttons/R.png",
                "img/selected/R.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('R')));
        buttonR.setLocation(                        //  3 4
                (int) (340 * SIZE_MODIFIER),
                (int) (698 * SIZE_MODIFIER));
        mainPanel.add(buttonR);

        val buttonS = createButton(
                "img/buttons/S.png",
                "img/selected/S.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('S')));
        buttonS.setLocation(                        //  4 4
                (int) (394 * SIZE_MODIFIER),
                (int) (698 * SIZE_MODIFIER));
        mainPanel.add(buttonS);

        val buttonT = createButton(
                "img/buttons/T.png",
                "img/selected/T.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('T')));
        buttonT.setLocation(                        //  5 4
                (int) (450 * SIZE_MODIFIER),
                (int) (698 * SIZE_MODIFIER));
        mainPanel.add(buttonT);

        val buttonU = createButton(
                "img/buttons/U.png",
                "img/selected/U.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('U')));
        buttonU.setLocation(                        //  1 5
                (int) (229 * SIZE_MODIFIER),
                (int) (752 * SIZE_MODIFIER));
        mainPanel.add(buttonU);

        val buttonV = createButton(
                "img/buttons/V.png",
                "img/selected/V.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('Q')));
        buttonV.setLocation(                        //  2 5
                (int) (285 * SIZE_MODIFIER),
                (int) (752 * SIZE_MODIFIER));
        mainPanel.add(buttonV);

        val buttonW = createButton(
                "img/buttons/W.png",
                "img/selected/W.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('W')));
        buttonW.setLocation(                        //  3 5
                (int) (340 * SIZE_MODIFIER),
                (int) (752 * SIZE_MODIFIER));
        mainPanel.add(buttonW);

        val buttonX = createButton(
                "img/buttons/X.png",
                "img/selected/X.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('X')));
        buttonX.setLocation(                        //  4 5
                (int) (394 * SIZE_MODIFIER),
                (int) (752 * SIZE_MODIFIER));
        mainPanel.add(buttonX);

        val buttonY = createButton(
                "img/buttons/Y.png",
                "img/selected/Y.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('Y')));
        buttonY.setLocation(                        //  5 5
                (int) (450 * SIZE_MODIFIER),
                (int) (752 * SIZE_MODIFIER));
        mainPanel.add(buttonY);

        val buttonZ = createButton(
                "img/buttons/Z.png",
                "img/selected/Z.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('Z')));
        buttonZ.setLocation(                        //  1 6
                (int) (229 * SIZE_MODIFIER),
                (int) (802 * SIZE_MODIFIER));
        mainPanel.add(buttonZ);

        val buttonCLR = createButton(
                "img/buttons/CLR.png",
                "img/selected/CLR.png",
                SafeRunnable.build(hasClearListener, () -> clearListener.run()));
        buttonCLR.setLocation(                      //  5 6
                (int) (450 * SIZE_MODIFIER),
                (int) (802 * SIZE_MODIFIER));
        mainPanel.add(buttonCLR);

        val button1 = createButton(
                "img/buttons/1.png",
                "img/selected/1.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('1')));
        button1.setLocation(
                (int) (64 * SIZE_MODIFIER),
                (int) (670 * SIZE_MODIFIER));
        mainPanel.add(button1);

        val button2 = createButton(
                "img/buttons/2.png",
                "img/selected/2.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('2')));
        button2.setLocation(
                (int) (119 * SIZE_MODIFIER),
                (int) (670 * SIZE_MODIFIER));
        mainPanel.add(button2);

        val button3 = createButton(
                "img/buttons/3.png",
                "img/selected/3.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('3')));
        button3.setLocation(
                (int) (175 * SIZE_MODIFIER),
                (int) (670 * SIZE_MODIFIER));
        mainPanel.add(button3);

        val button4 = createButton(
                "img/buttons/4.png",
                "img/selected/4.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('4')));
        button4.setLocation(
                (int) (64 * SIZE_MODIFIER),
                (int) (715 * SIZE_MODIFIER));
        mainPanel.add(button4);

        val button5 = createButton(
                "img/buttons/5.png",
                "img/selected/5.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('5')));
        button5.setLocation(
                (int) (119 * SIZE_MODIFIER),
                (int) (715 * SIZE_MODIFIER));
        mainPanel.add(button5);

        val button6 = createButton(
                "img/buttons/6.png",
                "img/selected/6.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('6')));
        button6.setLocation(
                (int) (175 * SIZE_MODIFIER),
                (int) (715 * SIZE_MODIFIER));
        mainPanel.add(button6);

        val button7 = createButton(
                "img/buttons/7.png",
                "img/selected/7.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('7')));
        button7.setLocation(
                (int) (64 * SIZE_MODIFIER),
                (int) (760 * SIZE_MODIFIER));
        mainPanel.add(button7);

        val button8 = createButton(
                "img/buttons/8.png",
                "img/selected/8.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('8')));
        button8.setLocation(
                (int) (119 * SIZE_MODIFIER),
                (int) (760 * SIZE_MODIFIER));
        mainPanel.add(button8);

        val button9 = createButton(
                "img/buttons/9.png",
                "img/selected/9.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('9')));
        button9.setLocation(
                (int) (175 * SIZE_MODIFIER),
                (int) (760 * SIZE_MODIFIER));
        mainPanel.add(button9);

        val button0 = createButton(
                "img/buttons/0.png",
                "img/selected/0.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('0')));
        button0.setLocation(
                (int) (64 * SIZE_MODIFIER),
                (int) (806 * SIZE_MODIFIER));
        mainPanel.add(button0);

        val buttonDot = createButton(
                "img/buttons/dot.png",
                "img/selected/dot.png",
                SafeRunnable.build(hasCharacterListener, () -> characterListener.accept('.')));
        buttonDot.setLocation(
                (int) (119 * SIZE_MODIFIER),
                (int) (806 * SIZE_MODIFIER));
        mainPanel.add(buttonDot);

//        ------   screen buttons   ------
        val pair11 = new Pair<Integer, Integer>(1, 1);
        val screenButton11 = createButton(
                "img/buttons/button.png",
                "img/selected/button.png",
                SafeRunnable.build(() -> screenButtonsListeners.containsKey(pair11),
                        () -> screenButtonsListeners.get(pair11).run()));
        screenButton11.setLocation(
                (int) (16 * SIZE_MODIFIER),
                (int) (124 * SIZE_MODIFIER));
        mainPanel.add(screenButton11);

        val pair21 = new Pair<Integer, Integer>(2, 1);
        val screenButton21 = createButton(
                "img/buttons/button.png",
                "img/selected/button.png",
                SafeRunnable.build(() -> screenButtonsListeners.containsKey(pair21),
                        () -> screenButtonsListeners.get(pair21).run()));
        screenButton21.setLocation(
                (int) (508 * SIZE_MODIFIER),
                (int) (124 * SIZE_MODIFIER));
        mainPanel.add(screenButton21);

        val pair12 = new Pair<Integer, Integer>(1, 2);
        val screenButton12 = createButton(
                "img/buttons/button.png",
                "img/selected/button.png",
                SafeRunnable.build(() -> screenButtonsListeners.containsKey(pair12),
                        () -> screenButtonsListeners.get(pair12).run()));
        screenButton12.setLocation(
                (int) (16 * SIZE_MODIFIER),
                (int) (169 * SIZE_MODIFIER));
        mainPanel.add(screenButton12);

        val pair22 = new Pair<Integer, Integer>(2, 2);
        val screenButton22 = createButton(
                "img/buttons/button.png",
                "img/selected/button.png",
                SafeRunnable.build(() -> screenButtonsListeners.containsKey(pair22),
                        () -> screenButtonsListeners.get(pair22).run()));
        screenButton22.setLocation(
                (int) (508 * SIZE_MODIFIER),
                (int) (169 * SIZE_MODIFIER));
        mainPanel.add(screenButton22);

        val pair13 = new Pair<Integer, Integer>(1, 3);
        val screenButton13 = createButton(
                "img/buttons/button.png",
                "img/selected/button.png",
                SafeRunnable.build(() -> screenButtonsListeners.containsKey(pair13),
                        () -> screenButtonsListeners.get(pair13).run()));
        screenButton13.setLocation(
                (int) (16 * SIZE_MODIFIER),
                (int) (214 * SIZE_MODIFIER));
        mainPanel.add(screenButton13);

        val pair23 = new Pair<Integer, Integer>(2, 3);
        val screenButton23 = createButton(
                "img/buttons/button.png",
                "img/selected/button.png",
                SafeRunnable.build(() -> screenButtonsListeners.containsKey(pair23),
                        () -> screenButtonsListeners.get(pair23).run()));
        screenButton23.setLocation(
                (int) (508 * SIZE_MODIFIER),
                (int) (214 * SIZE_MODIFIER));
        mainPanel.add(screenButton23);

        val pair14 = new Pair<Integer, Integer>(1, 4);
        val screenButton14 = createButton(
                "img/buttons/button.png",
                "img/selected/button.png",
                SafeRunnable.build(() -> screenButtonsListeners.containsKey(pair14),
                        () -> screenButtonsListeners.get(pair14).run()));
        screenButton14.setLocation(
                (int) (16 * SIZE_MODIFIER),
                (int) (258 * SIZE_MODIFIER));
        mainPanel.add(screenButton14);

        val pair24 = new Pair<Integer, Integer>(2, 4);
        val screenButton24 = createButton(
                "img/buttons/button.png",
                "img/selected/button.png",
                SafeRunnable.build(() -> screenButtonsListeners.containsKey(pair24),
                        () -> screenButtonsListeners.get(pair24).run()));
        screenButton24.setLocation(
                (int) (508 * SIZE_MODIFIER),
                (int) (258 * SIZE_MODIFIER));
        mainPanel.add(screenButton24);

        val pair15 = new Pair<Integer, Integer>(1, 5);
        val screenButton15 = createButton(
                "img/buttons/button.png",
                "img/selected/button.png",
                SafeRunnable.build(() -> screenButtonsListeners.containsKey(pair15),
                        () -> screenButtonsListeners.get(pair15).run()));
        screenButton15.setLocation(
                (int) (16 * SIZE_MODIFIER),
                (int) (302 * SIZE_MODIFIER));
        mainPanel.add(screenButton15);

        val pair25 = new Pair<Integer, Integer>(2, 5);
        val screenButton25 = createButton(
                "img/buttons/button.png",
                "img/selected/button.png",
                SafeRunnable.build(() -> screenButtonsListeners.containsKey(pair25),
                        () -> screenButtonsListeners.get(pair25).run()));
        screenButton25.setLocation(
                (int) (508 * SIZE_MODIFIER),
                (int) (302 * SIZE_MODIFIER));
        mainPanel.add(screenButton25);

        val pair16 = new Pair<Integer, Integer>(1, 6);
        val screenButton16 = createButton(
                "img/buttons/button.png",
                "img/selected/button.png",
                SafeRunnable.build(() -> screenButtonsListeners.containsKey(pair16),
                        () -> screenButtonsListeners.get(pair16).run()));
        screenButton16.setLocation(
                (int) (16 * SIZE_MODIFIER),
                (int) (346 * SIZE_MODIFIER));
        mainPanel.add(screenButton16);

        val pair26 = new Pair<Integer, Integer>(2, 6);
        val screenButton26 = createButton(
                "img/buttons/button.png",
                "img/selected/button.png",
                SafeRunnable.build(() -> screenButtonsListeners.containsKey(pair26),
                        () -> screenButtonsListeners.get(pair26).run()));
        screenButton26.setLocation(
                (int) (508 * SIZE_MODIFIER),
                (int) (346 * SIZE_MODIFIER));
        mainPanel.add(screenButton26);

//        ------   screens   ------
        val screen = context.getBean("mcdu-menu", AbstractScreen.class);
        setScreen(screen);

//        ------   end of init   ------
        setVisible(true);
    }

    private JButton createButton(final String iconResource, final String selectedResource,
                                 final Runnable action) {
        final Icon icon, selected;
        try {
            @Cleanup
            val iconInput = MainFrame.class
                    .getClassLoader()
                    .getResourceAsStream(iconResource);
            val iconImage = ImageIO.read(iconInput);
            icon = new ImageIcon(iconImage.getScaledInstance(
                    (int) (iconImage.getWidth() * SIZE_MODIFIER),
                    (int) (iconImage.getHeight() * SIZE_MODIFIER),
                    Image.SCALE_SMOOTH));

            @Cleanup
            val selectedInput = MainFrame.class
                    .getClassLoader()
                    .getResourceAsStream(selectedResource);
            val selectedImage = ImageIO.read(selectedInput);
            selected = new ImageIcon(selectedImage.getScaledInstance(
                    (int) (selectedImage.getWidth() * SIZE_MODIFIER),
                    (int) (selectedImage.getHeight() * SIZE_MODIFIER),
                    Image.SCALE_SMOOTH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        val button = new JButton();
        button.setFocusable(false);
        button.setBorderPainted(false);

        button.setSize(icon.getIconWidth() - 1, icon.getIconHeight() - 1);
        button.setIcon(icon);
        button.addActionListener(e -> action.run());

        button.removeMouseListener(button.getMouseListeners()[0]);
        button.addMouseListener(new BasicButtonListener(button) {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(selected);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(icon);
            }
        });

        return button;
    }

    @Override
    public void setCharacterListener(Consumer<Character> listener) {
        characterListener = listener;
    }

    @Override
    public void setClearListener(Runnable listener) {
        clearListener = listener;
    }

    @Override
    public void setScreen(@NonNull Object screenObj) {
        val screen = (AbstractScreen) screenObj;

        for (JComponent s : screens) {
            s.setVisible(false);
        }

        if (!screens.contains(screen)) {
            screen.setSize(
                    (int) (387 * SIZE_MODIFIER),
                    (int) (360 * SIZE_MODIFIER));
            screen.setLocation(
                    (int) (85 * SIZE_MODIFIER),
                    (int) (60 * SIZE_MODIFIER));
            screen.construct(SIZE_MODIFIER);

            mainPanel.add(screen);
            screens.add(screen);
        }

        screenButtonsListeners = screen.getScreenButtonsListeners();

        screen.setVisible(true);

        repaint();
    }
}
