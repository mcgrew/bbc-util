package edu.purdue.bbc.util.task;

import edu.purdue.bbc.util.task.TaskListener;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mcgrew
 * Date: 7/25/13
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Task<T> extends Runnable {
  public enum Status {
    WAITING, WORKING, COMPLETE, CANCELED, ERROR
  }

  public String getDescription();

  public double getPercentage();

  public Status getStatus();

  public Exception getError();

  public List<T> getResults();

  public void addTaskListener(TaskListener t);

  public boolean removeTaskListener(TaskListener t);

}
