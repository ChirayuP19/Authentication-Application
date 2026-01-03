package com.techChirayu.Auth.Auth_Application.helpers;

import java.util.UUID;

public class UserHelper {

    public static UUID parseUUID(String uuid){
        return UUID.fromString(uuid);
    }
}
