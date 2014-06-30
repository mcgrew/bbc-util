package edu.purdue.bbc.util.task;

import edu.purdue.bbc.util.task.Task;
import edu.purdue.bbc.util.task.TaskListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mcgrew
 * Date: 7/25/13
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTask<T> extends Thread implements Task<T> {
  private List<TaskListener> listeners;
  private List<T> results;
  private Exception error = null;
  private Status status = Status.WAITING;
  protected boolean cancelRequested;
  private double percentComplete;

  protected AbstractTask() {
    listeners = new LinkedList();
  }

  @Override
  public Status getStatus() {
    return status;
  }

  protected void setStatus(Status status) {
    this.status = status;
    fireTaskEvent(new TaskEvent(this));
  }

  @Override
  public Exception getError() {
    return error;
  }

  /**
   * Sets an error indicating what problem occurred. This should be called
   * BEFORE calling setStatus(Status.ERROR)
   * @param e
   */
  protected void setError(Exception e) {
    error = e;
  }

  @Override
  public double getPercentage() {
    return percentComplete;
  }

  protected void setPercentage(double newPercent) {
    this.percentComplete = newPercent;
    fireTaskEvent(new TaskEvent<T>(this));
  }

  @Override
  public List<T> getResults() {
    return results;
  }

  @Override
  public void addTaskListener(TaskListener t) {
    listeners.add(t);
  }

  @Override
  public boolean removeTaskListener(TaskListener t) {
    return listeners.remove(t);
  }

  private void fireTaskEvent(TaskEvent<T> event) {
    for (TaskListener<T> l : listeners) {
      l.taskUpdated(event);
    }
  }

  public void cancel() {
    cancelRequested = true;
  }

  public boolean isComplete() {
    return status == Status.COMPLETE;
  }

  public boolean hadError() {
    return status == Status.ERROR;
  }

  public boolean isCanceled() {
    return status == Status.CANCELED;
  }


  @Override
  public abstract void run();

//  @Override
//  public List<TaskListener> getTaskListeners() {
//    return listeners;
//  }
}
