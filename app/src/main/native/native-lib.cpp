#include <jni.h>

extern "C" {

    JNIEXPORT jstring JNICALL
    Java_co_androidbaseappkotlinmvvm_App_getApiKey(JNIEnv *env, jobject instance) {

        return env-> NewStringUTF("ODkyN2IyMjcwYTFjNWZkMzVmZTAxMWY0YmFjODNhMzI=");
    }

}