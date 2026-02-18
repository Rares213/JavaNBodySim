package simulation;

import core.LabeledSliderFloat;

public class LabeledSliderStepsize extends LabeledSliderFloat
{
    public LabeledSliderStepsize()
    {
        ConfigSim config = ConfigSim.getInstance();

        super(config.SLIDER_STEPSIZE_NAME,  config.SLIDER_STEPSIZE_MAX, config.SLIDER_STEPSIZE_STEPSIZE, config.INIT_STEPSIZE);
    }

    @Override
    public String toString()
    {
        return "LabeledSlider: Stepsize" + " Value: " + super.slider.getFloatValue();
    }
}
