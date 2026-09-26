package com.ironmanguide;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.PluginPanel;

public class IronmanGuidePanel extends PluginPanel
{
private final String[] steps = {
"Welcome to the Ironman Guide.",
"Talk to the Gielinor Guide.",
"Continue through Tutorial Island."
};

private int currentStep = 0;

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
if (currentStep > 0)
{
currentStep--;
updateStep();
}
});

next.addActionListener(e -> {
if (currentStep < steps.length - 1)
{
currentStep++;
updateStep();
}
});

buttons.add(previous);
buttons.add(next);
add(buttons, BorderLayout.SOUTH);

updateStep();
}

private void updateStep()
{
title.setText("<html><b>Ironman Guide</b><br>Step " + (currentStep + 1) + " / " + steps.length + "</html>");
stepText.setText(steps[currentStep]);
}
}