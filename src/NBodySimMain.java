
import core.*;
import simulation.*;

import javax.swing.*;

public class NBodySimMain
{
    public static void main(String[] args)
    {
        try
        {
            ApplicationCore core = new ApplicationCore("NBodySim", 1280, 720);

            SimViewParticles simViewParticles = new SimViewParticles();
            SimViewMenu simViewMenu = new SimViewMenu();
            SimModel simModel = new SimModel();
            SimController simController = new SimController(simViewParticles, simViewMenu, simModel);

            core.addLayer(simController);

            core.setMainPanel(simController.getSimViewParticles());
            core.addOverlayPanel(simController.getSimViewMenu());
            core.listenFrameComponent(simController);

            core.run();

            System.exit(0);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
