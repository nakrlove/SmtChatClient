// IClientCallback.aidl
package com.smt.chat.service;

// Declare any non-default types here with import statements

interface IClientCallback {
    void receive(String json);
}