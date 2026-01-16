
import core.*;
import simulation.*;

import javax.swing.*;

public class NBodySimMain
{
    public static void main(String[] args)
    {
        ApplicationCore core = new ApplicationCore("NBodySim", 1280, 720);

        SimulationState simState = new SimulationState();
        SimulationPanel simPanel = new SimulationPanel();
        Simulation sim = new Simulation(simState, simPanel);
        SimulationMenu simMenu = new SimulationMenu();

        simMenu.addChangeListener(sim);
        simMenu.addActionListener(sim);

        core.addLayer(sim);
        core.addLayer(simPanel);

        core.setMainPanel(simPanel);
        core.addOverlayPanel(simMenu);

        JFrame coreFrame = core.getFrame();

        coreFrame.addComponentListener(simPanel);

        core.run();
    }
}
