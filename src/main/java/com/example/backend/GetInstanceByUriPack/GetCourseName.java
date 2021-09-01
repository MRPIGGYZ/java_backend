package com.example.backend.GetInstanceByUriPack;

public interface GetCourseName {
    static String GetCourseNameInUri(String uri) {
        uri = uri.substring(0, uri.lastIndexOf("#"));
        return uri.substring(uri.lastIndexOf("/")+1);
    }
}
