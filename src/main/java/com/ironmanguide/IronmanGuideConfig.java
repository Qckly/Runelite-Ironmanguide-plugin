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
keyName = "showMinimapArrow",
name = "Minimap guidance",
description = "Show direction guidance on the minimap",
position = 1
)
default boolean showMinimapArrow()
{
return true;
}

@ConfigItem(
keyName = "showWorldMapGuidance",
name = "World map guidance",
description = "Show guide target on the world map",
position = 2
)
default boolean showWorldMapGuidance()
{
return true;
}

@ConfigItem(
keyName = "questHelperIntegration",
name = "Quest Helper integration",
description = "Allow Ironman Guide to integrate with Quest Helper",
position = 3
)
default boolean questHelperIntegration()
{
return true;
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