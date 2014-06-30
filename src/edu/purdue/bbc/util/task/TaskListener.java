package edu.purdue.bbc.util.task;

/**
 * Created with IntelliJ IDEA.
 * User: mcgrew
 * Date: 7/25/13
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TaskListener<T> {

  public void taskUpdated(TaskEvent<T> event);

}
