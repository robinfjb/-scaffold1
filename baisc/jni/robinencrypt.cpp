//
// Created by Administrator on 2017/11/22.
//
#include"robin_scaffold_basic_security_jni_JniManager.h"
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
//这是RELEASE_SIGN的MD5
//"e2167d50ba38cbac22ab540ebb774bb6";
const char* RELEASE_SIGN="xxxxxxxxxxxxx";
const char* PUBLIC_KEY = "xxxxxxxxxxxxxxxx";
const char* SECRET = "123456";
const char* DES_KEY = "xxx";
const char* APK_SIGN_ERROR = "签名不一致";

JNIEXPORT jstring JNICALL  Java_robin_scaffold_baisc_security_jni_JniManager_getPublicKey(JNIEnv *env, jobject obj, jobject contextObject) {
    const char* signStrng =  getSignString(env,contextObject);
    if(strcasecmp(signStrng,RELEASE_SIGN)==0)//签名一致  返回合法的 api key，否则返回错误
    {
        return (env)->NewStringUTF(PUBLIC_KEY);
    }else
    {
     LOGE(APK_SIGN_ERROR,"");
           return NULL;
    }
}

JNIEXPORT jstring JNICALL  Java_robin_scaffold_baisc_security_jni_JniManager_getSecret(JNIEnv *env, jobject obj, jobject contextObject) {
    const char* signStrng =  getSignString(env,contextObject);
    if(strcasecmp(signStrng,RELEASE_SIGN)==0)//签名一致  返回合法的 api key，否则返回错误
    {
        return (env)->NewStringUTF(SECRET);
    }else
    {
     LOGE(APK_SIGN_ERROR,"");
           return NULL;
    }
}

JNIEXPORT jstring JNICALL  Java_robin_scaffold_baisc_security_jni_JniManager_getDesKey(JNIEnv *env, jobject obj, jobject contextObject) {
    const char* signStrng =  getSignString(env,contextObject);
    if(strcasecmp(signStrng,RELEASE_SIGN)==0)//签名一致  返回合法的 api key，否则返回错误
    {
        return (env)->NewStringUTF(DES_KEY);
    }else
    {
     LOGE(APK_SIGN_ERROR,"");
           return NULL;
    }
}



const char* getSignString(JNIEnv *env,jobject context_object) {
        jclass context_class = env->GetObjectClass(context_object);

        //context.getPackageManager()
        jmethodID methodId = env->GetMethodID(context_class, "getPackageManager", "()Landroid/content/pm/PackageManager;");
        jobject package_manager_object = env->CallObjectMethod(context_object, methodId);
        if (package_manager_object == NULL) {
            __android_log_print(ANDROID_LOG_INFO, "JNITag","getPackageManager() Failed!");
            return NULL;
        }

        //context.getPackageName()
        methodId = env->GetMethodID(context_class, "getPackageName", "()Ljava/lang/String;");
        jstring package_name_string = (jstring)env->CallObjectMethod(context_object, methodId);
        if (package_name_string == NULL) {
            __android_log_print(ANDROID_LOG_INFO, "JNITag","getPackageName() Failed!");
            return NULL;
        }

        env->DeleteLocalRef(context_class);

        //PackageManager.getPackageInfo(Sting, int)
        jclass pack_manager_class = env->GetObjectClass(package_manager_object);
        methodId = env->GetMethodID(pack_manager_class, "getPackageInfo", "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
        env->DeleteLocalRef(pack_manager_class);
        jobject package_info_object = env->CallObjectMethod(package_manager_object, methodId, package_name_string, 64);
        if (package_info_object == NULL) {
            __android_log_print(ANDROID_LOG_INFO, "JNITag","getPackageInfo() Failed!");
            return NULL;
        }

        env->DeleteLocalRef(package_manager_object);

        //PackageInfo.signatures[0]
        jclass package_info_class = env->GetObjectClass(package_info_object);
        jfieldID fieldId = env->GetFieldID(package_info_class, "signatures", "[Landroid/content/pm/Signature;");
        env->DeleteLocalRef(package_info_class);
        jobjectArray signature_object_array = (jobjectArray)env->GetObjectField(package_info_object, fieldId);
        if (signature_object_array == NULL) {
            __android_log_print(ANDROID_LOG_INFO, "JNITag","PackageInfo.signatures[] is null");
            return NULL;
        }
        jobject signature_object = env->GetObjectArrayElement(signature_object_array, 0);

        env->DeleteLocalRef(package_info_object);

        //Signature.toCharsString()
        jclass signature_class = env->GetObjectClass(signature_object);
        methodId = env->GetMethodID(signature_class, "toCharsString", "()Ljava/lang/String;");
        env->DeleteLocalRef(signature_class);
        jstring signature_string = (jstring) env->CallObjectMethod(signature_object, methodId);

        return (env)->GetStringUTFChars(signature_string, 0);
}



void ByteToHexStr(char* source, char* dest, int sourceLen)
{
    short i;
    unsigned char highByte, lowByte;

    for (i = 0; i < sourceLen; i++)
    {
        highByte = source[i] >> 4;
        lowByte = source[i] & 0x0f;

        highByte += 0x30;

        if (highByte > 0x39)
                dest[i * 2] = highByte + 0x07;
        else
                dest[i * 2] = highByte;

        lowByte += 0x30;
        if (lowByte > 0x39)
            dest[i * 2 + 1] = lowByte + 0x07;
        else
            dest[i * 2 + 1] = lowByte;
    }
    return;
}

/**
    利用OnLoad钩子,初始化需要用到的Class类.
*/
JNIEXPORT jint JNICALL JNI_OnLoad (JavaVM* vm,void* reserved){

     JNIEnv* env = NULL;
     jint result=-1;
     if(vm->GetEnv((void**)&env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("JNI not OK", "");
        return result;
     }

     contextClass = (jclass)env->NewGlobalRef((env)->FindClass("android/content/Context"));
     signatureClass = (jclass)env->NewGlobalRef((env)->FindClass("android/content/pm/Signature"));
     packageNameClass = (jclass)env->NewGlobalRef((env)->FindClass("android/content/pm/PackageManager"));
     packageInfoClass = (jclass)env->NewGlobalRef((env)->FindClass("android/content/pm/PackageInfo"));

     return JNI_VERSION_1_4;
 }