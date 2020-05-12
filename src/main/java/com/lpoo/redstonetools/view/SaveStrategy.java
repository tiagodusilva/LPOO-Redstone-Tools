package com.lpoo.redstonetools.view;

public interface SaveStrategy {
    String getFileName();
    void notifySuccess();
    void notifyFailure();
}
