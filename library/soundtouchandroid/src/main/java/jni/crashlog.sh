#!/bin/bash
if test $# -lt 2 ; then
    echo "Extract readable stack trace from Android logcat crash"
    echo "Usage $0 lib.so crash.log"
    exit 1
fi
of=$(mktemp -t android_native_crash)
echo "Disassemble $1"
#~/tools/android-ndk-r5/toolchains/arm-linux-androideabi-4.4.3/prebuilt/linux-x86/bin/arm-linux-androideabi-objdump -S $1 > $of
/Users/pengjianqing/Documents/Android/android-ndk-r10c/toolchains/arm-linux-androideabi-4.9/prebuilt/darwin-x86_64/bin/arm-linux-androideabi-objdump -S $1 > $of
echo "Parse stack trace in $2"
python parse_stack.py $of $2
