package simulation;

import core.LabeledSliderFloat;

public class LabeledSliderTheta extends LabeledSliderFloat
{
    public LabeledSliderTheta()
    {
        ConfigSim config = ConfigSim.getInstance();

        super(config.SLIDER_THETA_NAME, config.SLIDER_THETA_MAX, config.SLIDER_THETA_STEPSIZE, config.INIT_THETA);
    }

    @Override
    public String toString()
    {
        return "LabeledSlider: Theta" + " Value: " + super.slider.getFloatValue();
    }


}
