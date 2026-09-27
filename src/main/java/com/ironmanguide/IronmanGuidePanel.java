package com.ironmanguide;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.function.IntConsumer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.PluginPanel;

public class IronmanGuidePanel extends PluginPanel
{
private final GuideManager guideManager;
private final IronmanGuideItemChecker itemChecker;
private final IronmanGuideItemNameResolver itemNameResolver;
private final IntConsumer onStepChanged;

private final JLabel title = new JLabel();
private final JTextArea stepText = new JTextArea();

public IronmanGuidePanel(
GuideManager guideManager,
IronmanGuideItemChecker itemChecker,
IronmanGuideItemNameResolver itemNameResolver,
IntConsumer onStepChanged)
{
this.guideManager = guideManager;
this.itemChecker = itemChecker;
this.itemNameResolver = itemNameResolver;
this.onStepChanged = onStepChanged;

setLayout(new BorderLayout(0, 10));
setBorder(new EmptyBorder(10, 10, 10, 10));

add(title, BorderLayout.NORTH);

stepText.setEditable(false);
stepText.setLineWrap(true);
stepText.setWrapStyleWord(true);
stepText.setOpaque(false);
add(stepText, BorderLayout.CENTER);

JPanel buttons = new JPanel(new FlowLayout());

JButton previous = new JButton("Previous");
JButton next = new JButton("Next");

previous.addActionListener(e -> {
guideManager.previous();
onStepChanged.accept(guideManager.getCurrentStepIndex());
updateStep();
});

next.addActionListener(e -> {
guideManager.next();
onStepChanged.accept(guideManager.getCurrentStepIndex());
updateStep();
});

buttons.add(previous);
buttons.add(next);

add(buttons, BorderLayout.SOUTH);

updateStep();
}

public void refresh()
{
updateStep();
}

private void updateStep()
{
GuideStep step = guideManager.getCurrentStep();

title.setText(
"<html><b>" + step.getTitle() + "</b><br>Step "
+ (guideManager.getCurrentStepIndex() + 1)
+ " / " + guideManager.getTotalSteps()
+ "</html>"
);

String text = step.getDescription();

if (step instanceof ItemGuideStep)
{
ItemGuideStep itemStep = (ItemGuideStep) step;

int have = itemChecker.getInventoryQuantity(
itemStep.getItemId()
);

text += "\n\nRequired: "
+ itemNameResolver.getName(itemStep.getItemId())
+ " x"
+ itemStep.getQuantity()
+ "\nYou have: "
+ have
+ "\nStatus: "
+ (have >= itemStep.getQuantity()
? "READY"
: "MISSING");
}
else if (step instanceof QuestGuideStep)
{
QuestGuideStep questStep = (QuestGuideStep) step;

GuideItemRequirement[] requirements =
questStep.getItemRequirements();

if (requirements != null && requirements.length > 0)
{
text += "\n\nRequired items:";

for (GuideItemRequirement requirement : requirements)
{
int have = itemChecker.getInventoryQuantity(
requirement.getItemId()
);

boolean ready =
have >= requirement.getQuantity();

text += "\n"
+ itemNameResolver.getName(
requirement.getItemId()
)
+ ": "
+ have
+ " / "
+ requirement.getQuantity()
+ " "
+ (ready ? "[READY]" : "[MISSING]");
}
}
}

stepText.setText(text);
}
}