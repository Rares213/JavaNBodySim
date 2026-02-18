/**
 * Class that contains the minimum requirements to run,
 * but doesn't do much by itself
 */

package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.KeyListener;

public class ApplicationCore implements KeyListener
{
    private JFrame frame;
    private JPanel mainPanel;
    private ArrayList<Layer> layers;
    private boolean shouldClose = false;

    /**
     * Constructor for core
     * @param name name of the app
     * @param width width of the window
     * @param height height of the window
     */
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
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
            });
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        layers = new ArrayList<Layer>();
        frame.addKeyListener(this);
    }

    /**
     * The main loop
     */
    public void run()
    {
        while(!shouldClose)
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

        frame.dispose();
    }

    /**
     * Add a layer
     * @param layer
     */
    public void addLayer(Layer layer)
    {
        layers.add(layer);
    }

    /**
     * Remove a layer
     * @param layer
     */
    public void removeLayer(Layer layer)
    {
        layers.remove(layer);
    }

    /**
     * Sets the main panel that will be as big as the window
     * @param mainPanel
     */
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

    public void listenFrameComponent(ComponentListener componentListener)
    {
        frame.addComponentListener(componentListener);
    }

    /**
     * Panel that will be displayed over the main panel
     * @param overlayPanel
     */
    public void addOverlayPanel(JPanel overlayPanel)
    {
        JLayeredPane layeredPane = frame.getLayeredPane();

        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);
    }

    public JFrame getFrame()
    {
        return frame;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            shouldClose = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
