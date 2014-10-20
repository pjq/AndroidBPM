#ifndef SOUND_STRETCH_H
#define SOUND_STRETCH_HH

#include "RunParameters.h"
#include "WavFile.h"
#include "SoundTouch.h"
#include "BPMDetect.h"

using namespace soundtouch;
using namespace std;

class SoundStretch{

public:
int mainCall(const int nParams, const char * const paramStr[]);
void openFiles(WavInFile **inFile, WavOutFile **outFile, const RunParameters *params);
void setup(SoundTouch *pSoundTouch, const WavInFile *inFile, const RunParameters *params);
void process(SoundTouch *pSoundTouch, WavInFile *inFile, WavOutFile *outFile);
float detectBPM(WavInFile *inFile, RunParameters *params);
};

#endif