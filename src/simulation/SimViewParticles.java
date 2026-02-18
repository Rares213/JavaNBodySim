package simulation;

import core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;


public class SimViewParticles extends JPanel
{
    private ArrayList<ArrayList<Float>> position;
    private float radius;

    public SimViewParticles()
    {
        super();
        setSize(1280, 720);
        this.setBackground(new Color(25, 25, 25));
        setVisible(true);
    }

    public void draw()
    {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.setPaint(Color.white);

        ArrayList<Float> x = position.get(0);
        ArrayList<Float> y = position.get(1);

        for(int i = 0; i < x.size(); ++i)
        {
            Ellipse2D.Float ellipseBody = new Ellipse2D.Float(x.get(i), y.get(i), radius, radius);
            g2d.fill(ellipseBody);
            g2d.draw(ellipseBody);
        }
    }

    public ArrayList<ArrayList<Float>> getPosition()
    {
        return position;
    }

    public void setPosition(ArrayList<ArrayList<Float>> position)
    {
        this.position = position;
    }

    public float getRadius()
    {
        return radius;
    }

    public void setRadius(float radius)
    {
        this.radius = radius;
    }
}
