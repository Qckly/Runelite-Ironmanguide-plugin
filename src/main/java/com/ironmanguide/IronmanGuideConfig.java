package com.ironmanguide;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup("ironmanguide")
public interface IronmanGuideConfig extends Config
{
@ConfigSection(
name = "Highlight style",
description = "Configure how guide targets are highlighted",
position = 0
)
String highlightStyleSection = "highlightStyleSection";

@ConfigSection(
name = "Navigation",
description = "Configure minimap and world map guidance",
position = 1
)
String navigationSection = "navigationSection";

@ConfigSection(
name = "Integrations",
description = "Configure integrations with other plugins",
position = 2,
closedByDefault = true
)
String integrationsSection = "integrationsSection";

@ConfigItem(
keyName = "highlightColor",
name = "Highlight color",
description = "Color used to highlight guide targets",
position = 0,
section = highlightStyleSection
)
default Color highlightColor()
{
return Color.CYAN;
}

@Range(
min = 1,
max = 8
)
@ConfigItem(
keyName = "highlightOutlineWidth",
name = "Outline thickness",
description = "Thickness of target and area outlines",
position = 1,
section = highlightStyleSection
)
default int highlightOutlineWidth()
{
return 3;
}

@Range(
min = 0,
max = 100
)
@ConfigItem(
keyName = "highlightFillOpacity",
name = "Fill opacity",
description = "Opacity of filled highlight areas in percent",
position = 2,
section = highlightStyleSection
)
default int highlightFillOpacity()
{
return 20;
}

@Range(
min = 0,
max = 4
)
@ConfigItem(
keyName = "highlightFeather",
name = "Outline feather",
description = "Softness of NPC and object outlines",
position = 3,
section = highlightStyleSection
)
default int highlightFeather()
{
return 1;
}

@ConfigItem(
keyName = "showMinimapArrow",
name = "Minimap guidance",
description = "Show direction guidance on the minimap",
position = 0,
section = navigationSection
)
default boolean showMinimapArrow()
{
return true;
}

@ConfigItem(
keyName = "showWorldMapGuidance",
name = "World map guidance",
description = "Show guide target on the world map",
position = 1,
section = navigationSection
)
default boolean showWorldMapGuidance()
{
return true;
}

@ConfigItem(
keyName = "questHelperIntegration",
name = "Quest Helper integration",
description = "Allow Ironman Guide to integrate with Quest Helper",
position = 0,
section = integrationsSection
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