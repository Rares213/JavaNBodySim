package simulation;

import core.LabeledSliderFloat;

public class LabeledSliderSoft extends LabeledSliderFloat
{
    public LabeledSliderSoft()
    {
        ConfigSim config = ConfigSim.getInstance();

        super(config.SLIDER_SOFT_NAME,  config.SLIDER_SOFT_MAX, config.SLIDER_SOFT_STEPSIZE, config.INIT_SOFT);
    }

    @Override
    public String toString()
    {
        return "LabeledSlider: Soft" + " Value: " + super.slider.getFloatValue();
    }
}
