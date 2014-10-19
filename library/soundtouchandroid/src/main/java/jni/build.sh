#/bin/sh

echo ndk-build
ndk-build
echo cp ../libs/armeabi-v7a/libsoundtouch.so ../../../../../../app/src/main/jniLibs/armeabi-v7a/libsoundtouch.so 
cp ../libs/armeabi-v7a/libsoundtouch.so ../../../../../../app/src/main/jniLibs/armeabi-v7a/libsoundtouch.so 
