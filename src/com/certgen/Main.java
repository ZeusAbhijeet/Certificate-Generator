package com.certgen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ImageFrame {
    JFrame imageFrame;
    ImageFrame() {
        imageFrame = new JFrame("Image");

        imageFrame.add(new ImagePanel(), BorderLayout.NORTH);
        imageFrame.setSize(960, 540);
        imageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        imageFrame.setResizable(false);
        imageFrame.setVisible(true);
    }
}

class ImagePanel extends JPanel {
    int x, y, x2, y2;
    BufferedImage image;

    ImagePanel() {
        x = y = x2 = y2 = 0;

        try{
            image = ImageIO.read(new File("images/test/No text cypher neko arc.png"));
            Image tmp = image.getScaledInstance(image.getWidth() / 2, image.getHeight()/2, Image.SCALE_SMOOTH);
            BufferedImage dimg = new BufferedImage(image.getWidth() / 2, image.getHeight() / 2, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            image = dimg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NewMouseListener listener = new NewMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    @Override
    public Dimension getPreferredSize() {
        return image  == null ? new Dimension(200, 200) : new Dimension(image.getWidth(), image.getHeight());
    }

    class NewMouseListener extends MouseAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            //System.out.println(e.getX() + " " + e.getY());
            setEndPoint(e.getX(), e.getY());
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //System.out.println(e.getX() + " " + e.getY());
            setStartPoint(e.getX(), e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //System.out.println(e.getX() + " " + e.getY());
            setEndPoint(e.getX(), e.getY());
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        int x1 = (getWidth() - image.getWidth()) / 2;
        int y1 = (getHeight() - image.getHeight()) / 2;
        g2d.drawImage(image, x1, y1, this);

        g2d.setColor(Color.GREEN);
        drawPerfectRect(g2d, x, y, x2, y2);
        g2d.dispose();
    }

    public void setStartPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setEndPoint(int x, int y) {
        x2 = (x);
        y2 = (y);
    }

    public void drawPerfectRect(Graphics g, int x, int y, int x2, int y2) {
        int px = Math.min(x,x2);
        int py = Math.min(y,y2);
        int pw=Math.abs(x-x2);
        int ph=Math.abs(y-y2);
        g.drawRect(px, py, pw, ph);
        System.out.println(x + " " + y + " " + x2 + " " + y2 + " " + px + " " + py + " " + pw + " " + ph);
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        new ImageFrame();
    }
}
