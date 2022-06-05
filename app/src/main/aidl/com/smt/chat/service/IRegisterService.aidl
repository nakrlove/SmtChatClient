// IRegisterService.aidl
package com.smt.chat.service;

// Declare any non-default types here with import statements
import com.smt.chat.service.IClientCallback;
//import java.rmi.RemoteException;
interface IRegisterService {
    void registerCallback(IClientCallback  callback);
    void unregisterCallback(IClientCallback callback);
    void send(String json,boolean isConnected);
}