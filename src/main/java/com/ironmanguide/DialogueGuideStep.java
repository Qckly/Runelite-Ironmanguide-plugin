package com.ironmanguide;

public class DialogueGuideStep extends GuideStep
{
private final String[] options;

public DialogueGuideStep(
String title,
String description,
String... options)
{
super(GuideStepType.DIALOGUE, title, description);
this.options = options;
}

public String[] getOptions()
{
return options;
}
}