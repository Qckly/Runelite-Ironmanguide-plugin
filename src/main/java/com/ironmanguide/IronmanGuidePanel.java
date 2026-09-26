package com.ironmanguide;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.function.IntConsumer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.PluginPanel;

public class IronmanGuidePanel extends PluginPanel
{
private final GuideManager guideManager;
private final IntConsumer onStepChanged;

private final JLabel title = new JLabel();
private final JTextArea stepText = new JTextArea();

public IronmanGuidePanel(int savedStep, IntConsumer onStepChanged)
{
this.onStepChanged = onStepChanged;
this.guideManager = new GuideManager(GuideData.getSteps(), savedStep);

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

private void updateStep()
{
GuideStep step = guideManager.getCurrentStep();

title.setText(
"<html><b>" + step.getTitle() + "</b><br>Step "
+ (guideManager.getCurrentStepIndex() + 1)
+ " / " + guideManager.getTotalSteps()
+ "</html>"
);

stepText.setText(step.getDescription());
}
}