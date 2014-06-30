package edu.purdue.bbc.ui;

import edu.purdue.bbc.util.ProcessUtils;
import edu.purdue.bbc.util.task.AbstractTask;
import edu.purdue.bbc.util.task.Task;
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
public class ProgressDialog extends JDialog implements TaskListener {
  ProgressPanel progressPanel;

  public ProgressDialog(Frame owner, String title, String message) {
    super(owner, title);
    progressPanel = new ProgressPanel(message);
    setLayout(new BorderLayout());
    getContentPane().add(progressPanel, BorderLayout.CENTER);
  }

  @Override
  public void taskUpdated(TaskEvent event) {
    progressPanel.taskUpdated(event);
    Task task = event.getTask();
    progressPanel.setMessage(event.getTask().getDescription());
    Task.Status status = task.getStatus();
    if (status == Task.Status.COMPLETE || status == Task.Status.CANCELED) {
      dispose();
    }
    if (status == Task.Status.ERROR) {
      Exception e = event.getTask().getError();
      progressPanel.setMessage(e.getMessage());
      ProcessUtils.sleep(5000);
      dispose();
    }
  }

  /* testing code */
  public static void main(String [] args) {
    JFrame frame = new JFrame();
    AbstractTask task = new AbstractTask() {

      @Override
      public void run() {
        setPercentage(-1);
        ProcessUtils.sleep(3000);
        setStatus(Status.WORKING);
        for (double i = 0; i <= 1; i+= 0.01) {
          setPercentage(i);
          ProcessUtils.sleep(100);
        }
        setStatus(Status.COMPLETE);
      }

      @Override
      public String getDescription() {
        return "Working";
      }
    };
    ProgressDialog progressDialog = new ProgressDialog(frame, "Progress Test",
            "Conducting progress test");
    task.addTaskListener(progressDialog);
    progressDialog.setSize(new Dimension(400,70));
    progressDialog.setVisible(true);
    task.start();
  }
}
