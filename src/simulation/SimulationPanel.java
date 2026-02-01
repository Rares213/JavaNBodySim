package simulation;

import core.*;
import core.LabeledSliderFloat;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;


public class SimulationPanel extends JPanel
    implements Layer, ComponentListener, ChangeListener
{
    ArrayList<ArrayList<Float>> position = null;
    float radius;

    public SimulationPanel()
    {
        super();
        setSize(1280, 720);
        this.setBackground(new Color(25, 25, 25));
        setVisible(true);

        ConfigSim conf = ConfigSim.getInstance();
        radius = conf.INIT_RADIUS;
    }

    public void setPosition(ArrayList<ArrayList<Float>> position)
    {
        this.position = position;
    }

    @Override
    public void onUpdate()
    {

    }

    @Override
    public void onRender()
    {
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        long startTime = System.currentTimeMillis();

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.setPaint(Color.white);

        ArrayList<Float> x = position.get(0);
        ArrayList<Float> y = position.get(1);
        int size = x.size();

        for(int i = 0; i < size; i++)
        {
            Ellipse2D.Float ellipseBody = new Ellipse2D.Float(x.get(i), y.get(i), radius, radius);
            g2d.fill(ellipseBody);
            g2d.draw(ellipseBody);
        }
    }

    @Override
    public void componentResized(ComponentEvent e)
    {
        System.out.println(e.toString());
        Component comp = e.getComponent();
        setSize(comp.getSize());

    }

    @Override
    public void componentMoved(ComponentEvent e)
    {

    }

    @Override
    public void componentShown(ComponentEvent e)
    {

    }

    @Override
    public void componentHidden(ComponentEvent e)
    {

    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        ConfigSim conf = ConfigSim.getInstance();

        if(e.getSource() instanceof SliderFloat sliderFloat)
        {
            if (sliderFloat.getName().equals(conf.SLIDER_RADIUS_NAME))
                radius = sliderFloat.getFloatValue();

        }
    }
}
