package com.ironmanguide;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ironmanguide")
public interface IronmanGuideConfig extends Config
{
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