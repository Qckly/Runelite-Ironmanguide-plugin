package com.ironmanguide;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.PluginPanel;

public class IronmanGuidePanel extends PluginPanel
{
private final GuideManager guideManager =
new GuideManager(GuideData.getSteps());

private final JLabel title = new JLabel();
private final JTextArea stepText = new JTextArea();

public IronmanGuidePanel()
{
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
updateStep();
});

next.addActionListener(e -> {
guideManager.next();
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