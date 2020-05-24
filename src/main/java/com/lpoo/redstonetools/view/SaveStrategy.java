package com.lpoo.redstonetools.view;

public interface SaveStrategy {
    String getFileName(String oldName);
    void notifySuccess(String filename);
    void notifyFailure(String filename);
}
