package com.ironmanguide;

public enum IronmanGuideNavigationMode
{
ARROW("Arrow"),
PATHFINDER("Pathfinder (Shortest Path)");

private final String displayName;

IronmanGuideNavigationMode(
String displayName)
{
this.displayName = displayName;
}

@Override
public String toString()
{
return displayName;
}
}
