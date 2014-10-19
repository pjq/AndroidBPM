cmake_minimum_required(VERSION 2.8.4)
project(soundtouchandroid)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES
    src/main/java/jni/soundtouch/include/BPMDetect.h
    src/main/java/jni/soundtouch/include/FIFOSampleBuffer.h
    src/main/java/jni/soundtouch/include/FIFOSamplePipe.h
    src/main/java/jni/soundtouch/include/SoundTouch.h
    src/main/java/jni/soundtouch/include/STTypes.h
    src/main/java/jni/soundtouch/source/Android-lib/jni/soundtouch-jni.cpp
    src/main/java/jni/soundtouch/source/SoundStretch/main.cpp
    src/main/java/jni/soundtouch/source/SoundStretch/RunParameters.cpp
    src/main/java/jni/soundtouch/source/SoundStretch/RunParameters.h
    src/main/java/jni/soundtouch/source/SoundStretch/WavFile.cpp
    src/main/java/jni/soundtouch/source/SoundStretch/WavFile.h
    src/main/java/jni/soundtouch/source/SoundTouch/AAFilter.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/AAFilter.h
    src/main/java/jni/soundtouch/source/SoundTouch/BPMDetect.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/cpu_detect.h
    src/main/java/jni/soundtouch/source/SoundTouch/cpu_detect_x86.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/FIFOSampleBuffer.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/FIRFilter.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/FIRFilter.h
    src/main/java/jni/soundtouch/source/SoundTouch/InterpolateCubic.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/InterpolateCubic.h
    src/main/java/jni/soundtouch/source/SoundTouch/InterpolateLinear.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/InterpolateLinear.h
    src/main/java/jni/soundtouch/source/SoundTouch/InterpolateShannon.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/InterpolateShannon.h
    src/main/java/jni/soundtouch/source/SoundTouch/mmx_optimized.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/PeakFinder.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/PeakFinder.h
    src/main/java/jni/soundtouch/source/SoundTouch/RateTransposer.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/RateTransposer.h
    src/main/java/jni/soundtouch/source/SoundTouch/SoundTouch.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/sse_optimized.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/TDStretch.cpp
    src/main/java/jni/soundtouch/source/SoundTouch/TDStretch.h
    src/main/java/jni/soundtouch/source/SoundTouchDLL/resource.h
    src/main/java/jni/soundtouch/source/SoundTouchDLL/SoundTouchDLL.cpp
    src/main/java/jni/soundtouch/source/SoundTouchDLL/SoundTouchDLL.h
    src/main/java/jni/soundtouch-jni.cpp)

add_executable(soundtouchandroid ${SOURCE_FILES})