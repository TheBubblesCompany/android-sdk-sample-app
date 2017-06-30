package com.mybubbles.sdksample.observers;

import java.util.Observable;

public class ServicesListObservable extends Observable {

  public void trigger() {
    // Notify
    setChanged();
    notifyObservers();
  }
}
