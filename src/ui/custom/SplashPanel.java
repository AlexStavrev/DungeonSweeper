package ui.custom;

import ui.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class SplashPanel extends JPanel implements EventIF {
    /**
     * Standard Constructor for the class
     */
    public SplashPanel() {
        initComponents();
    }

    /**
     * A method to initialize the gui components and other elements
     */
    private void initComponents() {
        removeAll();
        GridBagLayout gbl_splashPanel = new GridBagLayout();
        gbl_splashPanel.columnWidths = new int[]{0, 0};
        gbl_splashPanel.rowHeights = new int[]{0, 0};
        gbl_splashPanel.columnWeights = new double[]{0.8, 1.0};
        gbl_splashPanel.rowWeights = new double[]{1.0, 0.8};
        setLayout(gbl_splashPanel);
        setBackground(Color.BLACK);
        //---- titleButton ----
        JButton titleButton = new JButton();
        formatButton(titleButton);
        add(titleButton, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        //---- logoButton ----
        JButton logoButton = new JButton();
        formatButton(logoButton);
        add(logoButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 0, 0), 0, 0));

        //---- label ----
        JLabel label = new JLabel("WrexBG Studios");
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 50));
        add(label, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 0, 0), 0, 0));

        //======== this ========
        Thread splash = new Thread(() -> {
            try {
                for (int amp = 0; amp <= 81; amp++) {
                    try {
                        Thread.sleep(1);
                        try {
                            BufferedImage bim = ImageIO.read(getClass().getResource("images/LogoIcon.png"));
                            Image icon = new ImageIcon(fadingImage(amp, bim)).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                            logoButton.setIcon(new ImageIcon(icon));

                            BufferedImage bimTitle = ImageIO.read(getClass().getResource("images/Title.png"));
                            Image iconTitle = new ImageIcon(fadingImage(amp, bimTitle)).getImage().getScaledInstance(800, 113, Image.SCALE_SMOOTH);
                            titleButton.setIcon(new ImageIcon(iconTitle));

                            setBackground(new Color(amp/3, amp/3, amp/3+7));
                            label.setForeground(new Color(amp*3+10, amp*3+10, amp*3+10));

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                Thread.sleep(1000);
                done();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        splash.setDaemon(true);
        splash.start();
    }

    private void formatButton(JButton button) {
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    private BufferedImage fadingImage(float amp, BufferedImage bim) {
        BufferedImage nbim = new BufferedImage(bim.getWidth(), bim.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D createGraphics = nbim.createGraphics();
        createGraphics.drawImage(bim, null, 0, 0);
        RescaleOp r = new RescaleOp(new float[]{1f, 1f, 1f, amp / 81}, new float[]{0, 0, 0, 0}, null);
        return r.filter(nbim, null);
    }

    @Override
    public void done() {}
}
