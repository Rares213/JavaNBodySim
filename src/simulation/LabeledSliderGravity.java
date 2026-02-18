package simulation;

import core.LabeledSliderFloat;


public class LabeledSliderGravity extends LabeledSliderFloat
{
    public LabeledSliderGravity()
    {
        ConfigSim config = ConfigSim.getInstance();

        super(config.SLIDER_GRAVITY_NAME,  config.SLIDER_G_MAX, config.SLIDER_G_STEPSIZE, config.INIT_G);
    }

    @Override
    public String toString()
    {
        return "LabeledSlider: Gravity" + " Value: " + super.slider.getFloatValue();
    }

}
