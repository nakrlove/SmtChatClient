// IConnectionService.aidl
package com.smt.chat.service;

// Declare any non-default types here with import statements
//import com.smt.chat.service.IChatMessinger;
//https://codechacha.com/ko/android-remote-service-with-aidl/ 참조
interface IConnectionService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

//     boolean registerCallback(IChatMessinger callback);
//     boolean unregisterCallback(IChatMessinger callback);


//    int getStatus();
    void connect();
    void disconnect();
    String send(String sendData);
    String receive();


}