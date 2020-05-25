package com.lpoo.redstonetools.view;

public interface SaveCircuitListener {
    String getFileName();
    void notifySuccess();
    void notifyFailure();
}
