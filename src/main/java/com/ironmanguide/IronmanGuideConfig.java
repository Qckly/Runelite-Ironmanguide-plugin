package com.ironmanguide;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ironmanguide")
public interface IronmanGuideConfig extends Config
{
@ConfigItem(
keyName = "highlightColor",
name = "Highlight color",
description = "Color used to highlight guide targets",
position = 0
)
default Color highlightColor()
{
return Color.YELLOW;
}

@ConfigItem(
keyName = "currentStep",
name = "",
description = "",
hidden = true
)
default int currentStep()
{
return 0;
}
}