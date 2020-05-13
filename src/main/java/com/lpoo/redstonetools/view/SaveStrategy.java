package com.lpoo.redstonetools.view;

public interface SaveStrategy {
    String getFileName();
    void notifySuccess(String filename);
    void notifyFailure(String filename);
}
