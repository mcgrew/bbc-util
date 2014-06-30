package edu.purdue.bbc.ui;

import edu.purdue.bbc.util.task.TaskEvent;
import edu.purdue.bbc.util.task.TaskListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: mcgrew
 * Date: 7/25/13
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProgressPanel extends JPanel implements TaskListener {
  JProgressBar progressBar;
  JLabel messageLabel;

  public ProgressPanel() {
    this(null);
  }

  public ProgressPanel(String message) {
    super(new BorderLayout());
    progressBar = new JProgressBar();
    add(progressBar, BorderLayout.CENTER);
    messageLabel = new JLabel();
    setMessage(message);
  }

  public void setMessage(String message) {
    if (message != null) {
      messageLabel.setText(message);
      add(messageLabel, BorderLayout.SOUTH);
    } else {
      remove(messageLabel);
    }
  }

  @Override
  public void taskUpdated(TaskEvent event) {
    int percentage = (int)(event.getTask().getPercentage() * 100);
    if (percentage >= 0) {
      progressBar.setIndeterminate(false);
      progressBar.setValue(percentage);
        progressBar.setString(String.format("%d%%", percentage));
        progressBar.setStringPainted(true);
    } else {
      progressBar.setIndeterminate(true);
      progressBar.setStringPainted(false);
    }
  }

}
