package core;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ApplicationCore
{
    public ApplicationCore(String name, int width, int height)
    {
        try
        {
            SwingUtilities.invokeAndWait(new Runnable()
            {
                public void run()
                {
                    frame = new JFrame(name);

                    frame.setSize(width, height);
                    frame.getContentPane().setBackground(Color.BLACK);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
            });
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        layers = new ArrayList<Layer>();
    }

    public void run()
    {
        while(true)
        {
            for(Layer layer : layers)
            {
                layer.onUpdate();
            }

            for(Layer layer : layers)
            {
                layer.onRender();
            }
        }

    }

    public void addLayer(Layer layer)
    {
        layers.add(layer);
    }

    public void removeLayer(Layer layer)
    {
        layers.remove(layer);
    }

    public void setMainPanel(JPanel mainPanel)
    {
        JLayeredPane layeredPane = frame.getLayeredPane();

        if(this.mainPanel == null)
        {
            this.mainPanel = mainPanel;
            layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER, 0);

            return;
        }

        layeredPane.remove(0);

        this.mainPanel = mainPanel;
        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER, 0);
    }

    public void addOverlayPanel(JPanel overlayPanel)
    {
        JLayeredPane layeredPane = frame.getLayeredPane();

        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);
    }

    public JFrame getFrame()
    {
        return frame;
    }

    JFrame frame;
    JPanel mainPanel;
    ArrayList<Layer> layers;
}
