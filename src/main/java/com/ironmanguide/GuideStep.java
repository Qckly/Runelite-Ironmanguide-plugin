package com.ironmanguide;

public class GuideStep
{
private final GuideStepType type;
private final String title;
private final String description;

public GuideStep(GuideStepType type, String title, String description)
{
this.type = type;
this.title = title;
this.description = description;
}

public GuideStepType getType()
{
return type;
}

public String getTitle()
{
return title;
}

public String getDescription()
{
return description;
}
}