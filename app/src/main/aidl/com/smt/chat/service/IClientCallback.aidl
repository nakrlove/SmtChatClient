// IClientCallback.aidl
package com.smt.chat.service;

// Declare any non-default types here with import statements

interface IClientCallback {
    void disconnect(String json);
    void receive(String json);
}