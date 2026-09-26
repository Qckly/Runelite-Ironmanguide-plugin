package com.ironmanguide;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class IronmanGuidePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(IronmanGuidePlugin.class);
		RuneLite.main(args);
	}
}
