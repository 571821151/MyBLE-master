package com.clj.blexy.comm;


import com.clj.fastble.data.BleDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模型，用于Ble设备断开
 */
public class ObserverManager implements Observable {

    public static ObserverManager getInstance() {
        return ObserverManagerHolder.sObserverManager;
    }

    private static class ObserverManagerHolder {
        private static final ObserverManager sObserverManager = new ObserverManager();
    }

    private List<Observer> observers = new ArrayList<>(2);

    @Override
    public void addObserver(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void deleteObserver(Observer obj) {
        int i = observers.indexOf(obj);
        if (i >= 0) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObserver(BleDevice bleDevice) {
        for (int i = 0; i < observers.size(); i++) {
            Observer o = observers.get(i);
            o.disConnected(bleDevice);
        }
    }

}
