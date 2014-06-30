package edu.purdue.bbc.util.task;

import java.awt.*;
import java.util.EventObject;

/**
 * Created with IntelliJ IDEA.
 * User: mcgrew
 * Date: 7/25/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskEvent<T> extends EventObject {

  public TaskEvent(Task<T> source) {
    super(source);
  }

  public Task<T> getTask() {
    return (Task<T>)source;
  }

}
