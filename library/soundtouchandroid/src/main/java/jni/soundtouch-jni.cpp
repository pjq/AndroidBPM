#include <jni.h>
#include <android/log.h>
#include <queue>
#include <string>
#include <stdlib.h>
#include <stdio.h>
#include <vector>

#include <stdio.h>
//#include <dlfcn.h>

#include "soundtouch/include/SoundTouch.h"
#include "soundtouch/include/BPMDetect.h"
#include "soundtouch/include/STTypes.h"
#include "SoundStretch/RunParameters.h"
#include "SoundStretch/WavFile.h"
#include "SoundStretch/SoundStretch.h"
//#include "TimeShiftEffect.h"


// Processing chunk size (size chosen to be divisible by 2, 4, 6, 8, 10, 12, 14, 16 channels ...)

#if _WIN32
    #include <io.h>
    #include <fcntl.h>

    // Macro for Win32 standard input/output stream support: Sets a file stream into binary mode
    #define SET_STREAM_TO_BIN_MODE(f) (_setmode(_fileno(f), _O_BINARY))
#else
    // Not needed for GNU environment...
    #define SET_STREAM_TO_BIN_MODE(f) {}
#endif

#define LOGV(...)   __android_log_print((int)ANDROID_LOG_INFO, "SOUNDTOUCH", __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,"SOUNDTOUCH" ,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,"SOUNDTOUCH" ,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,"SOUNDTOUCH" ,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,"SOUNDTOUCH" ,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,"SOUNDTOUCH" ,__VA_ARGS__)
//#define //LOGV(...)

#define DLL_PUBLIC __attribute__ ((visibility ("default")))

using namespace soundtouch;
using namespace std;

static void log(char* message){
    LOGI("%s", message);
}

class SoundTouchStream : public SoundTouch
{

private:
	queue<jbyte>* byteBufferOut;
	int sampleRate;
	int bytesPerSample;

public:

	queue<jbyte>* getStream()
	{
		return byteBufferOut;
	}

	int getSampleRate()
	{
		return sampleRate;
	}

	int getBytesPerSample()
	{
		return bytesPerSample;
	}

	void setSampleRate(int sampleRate)
	{
		SoundTouch::setSampleRate(sampleRate);
		this->sampleRate = sampleRate;
	}

	void setBytesPerSample(int bytesPerSample)
	{
		this->bytesPerSample = bytesPerSample;
	}

	uint getChannels()
	{
		return channels;
	}

	SoundTouchStream()
	{
		byteBufferOut = new queue<jbyte>();
		sampleRate = bytesPerSample = 0;
	}

	SoundTouchStream(const SoundTouchStream& other)
	{
		byteBufferOut = new queue<jbyte>();
		sampleRate = bytesPerSample = 0;
	}
};

const int MAX_TRACKS = 16;

vector<SoundTouchStream> stStreams(MAX_TRACKS);

static void* getConvBuffer(int);
static int write(const float*, queue<jbyte>*, int, int);
static void setup(SoundTouchStream&, int, int, int, float, float);
static void convertInput(jbyte*, float*, int, int);
static inline int saturate(float, float, float);
static void* getConvBuffer(int);
static int process(SoundTouchStream&, SAMPLETYPE*, queue<jbyte>*, int, bool);
static void setPitchSemi(SoundTouchStream&, float);
static void setTempo(SoundTouchStream&, float);
static void setTempoChange(SoundTouchStream&, float);
static int copyBytes(jbyte*, queue<jbyte>*, int);

#ifdef __cplusplus

extern "C" DLL_PUBLIC void Java_com_smp_soundtouchandroid_SoundTouch_clearBytes(
		JNIEnv *env, jobject thiz, jint track)
{
	SoundTouchStream& soundTouch = stStreams.at(track);

	const int BUFF_SIZE = 8192;

	queue<jbyte>* byteBufferOut = soundTouch.getStream();

	SAMPLETYPE* fBufferIn = new SAMPLETYPE[BUFF_SIZE];
	soundTouch.clear();

	delete[] fBufferIn;
	fBufferIn = NULL;

	while (!byteBufferOut->empty())
	{
		byteBufferOut->pop();
	}
}

extern "C" DLL_PUBLIC void Java_com_smp_soundtouchandroid_SoundTouch_setup(
		JNIEnv *env, jobject thiz, jint track, jint channels, jint samplingRate,
		jint bytesPerSample, jfloat tempo, jfloat pitchSemi)
{
	SoundTouchStream& soundTouch = stStreams.at(track);
	setup(soundTouch, channels, samplingRate, bytesPerSample, tempo, pitchSemi);
}

extern "C" DLL_PUBLIC void Java_com_smp_soundtouchandroid_SoundTouch_finish(
		JNIEnv *env, jobject thiz, jint track, int length)
{
	SoundTouchStream& soundTouch = stStreams.at(track);

	const int bytesPerSample = soundTouch.getBytesPerSample();
	const int BUFF_SIZE = length / bytesPerSample;

	queue<jbyte>* byteBufferOut = soundTouch.getStream();

	SAMPLETYPE* fBufferIn = new SAMPLETYPE[BUFF_SIZE];
	process(soundTouch, fBufferIn, byteBufferOut, BUFF_SIZE, true); //audio is finishing

	delete[] fBufferIn;
	fBufferIn = NULL;
}

extern "C" DLL_PUBLIC void Java_com_smp_soundtouchandroid_SoundTouch_putBytes(
		JNIEnv *env, jobject thiz, jint track, jbyteArray input, jint length)
{
	SoundTouchStream& soundTouch = stStreams.at(track);

	const int bytesPerSample = soundTouch.getBytesPerSample();
	const int BUFF_SIZE = length / bytesPerSample;

	queue<jbyte>* byteBufferOut = soundTouch.getStream();

	jboolean isCopy;
	jbyte* ar = env->GetByteArrayElements(input, &isCopy);

	SAMPLETYPE* fBufferIn = new SAMPLETYPE[BUFF_SIZE];

	convertInput(ar, fBufferIn, BUFF_SIZE, bytesPerSample);

	process(soundTouch, fBufferIn, byteBufferOut, BUFF_SIZE, false); //audio is ongoing.

	env->ReleaseByteArrayElements(input, ar, JNI_ABORT);

	delete[] fBufferIn;
	fBufferIn = NULL;
}

extern "C" DLL_PUBLIC jint Java_com_smp_soundtouchandroid_SoundTouch_getBytes(
		JNIEnv *env, jobject thiz, jint track, jbyteArray get, jint toGet)
{
	queue<jbyte>* byteBufferOut = stStreams.at(track).getStream();

	jbyte* res = new jbyte[toGet];

	jint bytesWritten;

	jboolean isCopy;
	jbyte* ar = (jbyte*) env->GetPrimitiveArrayCritical(get, &isCopy);

	bytesWritten = copyBytes(ar, byteBufferOut, toGet);

	env->ReleasePrimitiveArrayCritical(get, ar, JNI_ABORT);

	delete[] res;
	res = NULL;

	return bytesWritten;
}

static void openFiles(WavInFile **inFile, WavOutFile **outFile, const RunParameters *params)
{
    log("openFiles");
    int bits, samplerate, channels;

   LOGI("InFile(%s), OutFile(%s)", params->inFileName, params->outFileName);

    FILE* fptr = fopen(params->inFileName, "rb");

       if (fptr == NULL)
       {
           // didn't succeed
           string msg = "Error : Unable to open file \"";
//           msg += params->inFileName;
//           msg += "\" for reading.";
           LOGI("Error, unable to open file %s", params->inFileName);
       }else {
            log("can open the file");
       }

    if (strcmp(params->inFileName, "stdin") == 0)
    {
        // used 'stdin' as input file
        SET_STREAM_TO_BIN_MODE(stdin);
        *inFile = new WavInFile(stdin);
    }
    else
    {

    LOGI("new WavInFile(%s)", params->inFileName);
        // open input file...
        *inFile = new WavInFile(params->inFileName);
    }

    // ... open output file with same sound parameters
    bits = (int)(*inFile)->getNumBits();
    LOGI("openFiles, bits = %d", bits);
    samplerate = (int)(*inFile)->getSampleRate();
    channels = (int)(*inFile)->getNumChannels();
    LOGI("openFiles, channels = %d", channels);

    if (params->outFileName)
    {
        if (strcmp(params->outFileName, "stdout") == 0)
        {
            SET_STREAM_TO_BIN_MODE(stdout);
            *outFile = new WavOutFile(stdout, samplerate, bits, channels);
        }
        else
        {
          LOGI(" new WavOutFile(%s)", params->outFileName);
            *outFile = new WavOutFile(params->outFileName, samplerate, bits, channels);
        }
    }
    else
    {
        *outFile = NULL;
    }
}


// Detect BPM rate of inFile and adjust tempo setting accordingly if necessary
static float detectBPM(WavInFile *inFile, RunParameters *params)
{
	log("detectBPM in");
    float bpmValue;
    int nChannels;
    const int BUFF_SIZE = 8192;
//    BPMDetect bpm(inFile->getNumChannels(), inFile->getSampleRate());
    BPMDetect bpm(2, 44100);
    SAMPLETYPE sampleBuffer[BUFF_SIZE];

	log("Detecting BPM rate...");
    // detect bpm rate
    fprintf(stderr, "Detecting BPM rate...");
    fflush(stderr);

    nChannels = (int)inFile->getNumChannels();
    nChannels = 2;


    assert(BUFF_SIZE % nChannels == 0);

	LOGI("Detecting BPM rate...nChannels = %d", nChannels);

    // Process the 'inFile' in small blocks, repeat until whole file has
    // been processed
    if (false){


        while (inFile->eof() == 0)
        {
            int num, samples;

            // Read sample data from input file
            num = inFile->read(sampleBuffer, BUFF_SIZE);
            LOGI("read size %d", num);

            // Enter the new samples to the bpm analyzer class
            samples = num / nChannels;
            bpm.inputSamples(sampleBuffer, samples);
        }
    } else {


        SAMPLETYPE buffer[BUFF_SIZE];
        FILE *file = fopen(params->inFileName, "r+");
        while (!feof(file)) {
            int num, samples;
            num = fread(buffer, sizeof(SAMPLETYPE), BUFF_SIZE, file);
            samples = num / nChannels;

            bpm.inputSamples(buffer, samples);
        }
    }

    // Now the whole song data has been analyzed. Read the resulting bpm.
    bpmValue = bpm.getBpm();
    log("Detecting BPM rate DONE");
    fprintf(stderr, "Done!\n");

    // rewind the file after bpm detection
    inFile->rewind();

	LOGI("detectBPM, bpm=%d", bpmValue);
    if (bpmValue > 0)
    {
        fprintf(stderr, "Detected BPM rate %.1f\n\n", bpmValue);
    }
    else
    {
        fprintf(stderr, "Couldn't detect BPM rate.\n\n");
        return bpmValue;
    }

    if (params->goalBPM > 0)
    {
        // adjust tempo to given bpm
        params->tempoDelta = (params->goalBPM / bpmValue - 1.0f) * 100.0f;
        fprintf(stderr, "The file will be converted to %.1f BPM\n\n", params->goalBPM);
    }

    return bpmValue;
}

static const char _helloText[] =
    "\n"
    "   SoundStretch v%s -  Written by Olli Parviainen 2001 - 2014\n"
    "==================================================================\n"
    "author e-mail: <oparviai"
    "@"
    "iki.fi> - WWW: http://www.surina.net/soundtouch\n"
    "\n"
    "This program is subject to (L)GPL license. Run \"soundstretch -license\" for\n"
    "more information.\n"
    "\n";
static float mainEntry()
{
    LOGI("mainEntry");
    WavInFile *inFile;
    WavOutFile *outFile;
    RunParameters *params;
    SoundTouch soundTouch;
	float bpm = 0;

//    char * paramStr[] = {"4" ,"/storage/emulated/0/androidsoundtouch/aliza.mp3", "/sdcard/androidsoundtouch/aliza.mp3.out", "-b"};
    char * paramStr[] = {"4" ,"/storage/emulated/0/androidsoundtouch/sample_orig.mp3", "/sdcard/androidsoundtouch/aliza.mp3.out", "-bpm"};
    int nParams = 4;

    fprintf(stderr, _helloText, SoundTouch::getVersionString());
    log(SoundTouch::getVersionString());

//    try
    {
        // Parse command line parameters
        params = new RunParameters(nParams, paramStr);

        // Open input & output files
        log("openFiles");
        openFiles(&inFile, &outFile, params);
        log("detectBPM");
        bpm = detectBPM(inFile, params);
        if (params->detectBPM == TRUE)
        {
            // detect sound BPM (and adjust processing parameters
            //  accordingly if necessary)
//            detectBPM(inFile, params);
        }

        // Setup the 'SoundTouch' object for processing the sound
//        setup(&soundTouch, inFile, params);

        // clock_t cs = clock();    // for benchmarking processing duration
        // Process the sound
//        process(&soundTouch, inFile, outFile);
        // clock_t ce = clock();    // for benchmarking processing duration
        // printf("duration: %lf\n", (double)(ce-cs)/CLOCKS_PER_SEC);

        // Close WAV file handles & dispose of the objects
        delete inFile;
        delete outFile;
        delete params;

        fprintf(stderr, "Done!\n");

        return bpm;
    }
//    catch (const runtime_error &e)
//    {
//        // An exception occurred during processing, display an error message
//        fprintf(stderr, "%s\n", e.what());
//        return -1;
//    }

    return 0;
}

extern "C" DLL_PUBLIC jfloat Java_com_smp_soundtouchandroid_SoundTouch_getBPM2(
		JNIEnv *env, jobject thiz, jint track, jint channels)
{
//	SoundTouchStream& soundTouch = stStreams.at(track);
	LOGI("Java_com_smp_soundtouchandroid_SoundTouch_getBPM");

    float bpm = 0;// (float)main.mainEntry();

	return bpm;
}


extern "C" DLL_PUBLIC jfloat Java_com_smp_soundtouchandroid_SoundTouch_soundstretch(
		JNIEnv *env, jobject thiz)
{
	LOGI("Java_com_smp_soundtouchandroid_SoundTouch_soundstretch");
    char * paramStr[] = {"4" ,"/storage/emulated/0/androidsoundtouch/sample_orig.mp3", "/sdcard/androidsoundtouch/aliza.mp3.out", "-bpm"};
    int nParams = 4;
    SoundStretch *main = new SoundStretch();
    float bpm = main->mainCall(nParams, paramStr);

	return bpm;
}


static int copyBytes(jbyte* arrayOut, queue<jbyte>* byteBufferOut, int toGet)
{
	int bytesWritten = 0;

	for (int i = 0; i < toGet; i++)
		{
			if (byteBufferOut->size() > 0)
			{
				arrayOut[i] = byteBufferOut->front();
				byteBufferOut->pop();
				++bytesWritten;
			}
			else
			{
				break;
			}
		}

	return bytesWritten;
}
extern "C" DLL_PUBLIC void Java_com_smp_soundtouchandroid_SoundTouch_setPitchSemi(
		JNIEnv *env, jobject thiz, jint track, jfloat pitchSemi)
{
	SoundTouchStream& soundTouch = stStreams.at(track);
	setPitchSemi(soundTouch, pitchSemi);
}
extern "C" DLL_PUBLIC void Java_com_smp_soundtouchandroid_SoundTouch_setTempo(
		JNIEnv *env, jobject thiz, jint track, jfloat tempo)
{
	SoundTouchStream& soundTouch = stStreams.at(track);
	setTempo(soundTouch, tempo);
}
extern "C" DLL_PUBLIC jlong Java_com_smp_soundtouchandroid_SoundTouch_getOutputBufferSize(
		JNIEnv *env, jobject thiz, jint track)
{
	SoundTouchStream& soundTouch = stStreams.at(track);
	queue<jbyte>* byteBufferOut = soundTouch.getStream();
	return byteBufferOut->size();
}
extern "C" DLL_PUBLIC void Java_com_smp_soundtouchandroid_SoundTouch_setTempoChange(
		JNIEnv *env, jobject thiz, jint track, jfloat tempoChange)
{
	SoundTouchStream& soundTouch = stStreams.at(track);
	setTempoChange(soundTouch, tempoChange);
}

static int process(SoundTouchStream& soundTouch, SAMPLETYPE* fBufferIn,
		queue<jbyte>* byteBufferOut, const int BUFF_SIZE, bool finishing)
{
	const uint channels = soundTouch.getChannels();
	const int buffSizeSamples = BUFF_SIZE / channels;
	const int bytesPerSample = soundTouch.getBytesPerSample();

	int nSamples = BUFF_SIZE / channels;

	int processed = 0;

	if (finishing)
	{
		soundTouch.flush();
	}
	else
	{
		soundTouch.putSamples(fBufferIn, nSamples);
	}

	do
	{
		nSamples = soundTouch.receiveSamples(fBufferIn, buffSizeSamples);
		processed += write(fBufferIn, byteBufferOut, nSamples * channels, bytesPerSample);
	} while (nSamples != 0);

	return processed;
}

static void* getConvBuffer(int sizeBytes)
{
	int convBuffSize = (sizeBytes + 15) & -8;
	// round up to following 8-byte bounday
	char *convBuff = new char[convBuffSize];
	return convBuff;
}

static int write(const float *bufferIn, queue<jbyte>* bufferOut,
		int numElems, int bytesPerSample)
{
	int numBytes;

	int oldSize = bufferOut->size();

	if (numElems == 0)
		return 0;

	numBytes = numElems * bytesPerSample;
	short *temp = (short*) getConvBuffer(numBytes);

	switch (bytesPerSample)
	{
	case 1:
	{
		unsigned char *temp2 = (unsigned char *) temp;
		for (int i = 0; i < numElems; i++)
		{
			temp2[i] = (unsigned char) saturate(bufferIn[i] * 128.0f + 128.0f,
					0.0f, 255.0f);
		}
		break;
	}

	case 2:
	{
		short *temp2 = (short *) temp;
		for (int i = 0; i < numElems; i++)
		{
			short value = (short) saturate(bufferIn[i] * 32768.0f, -32768.0f,
					32767.0f);
			temp2[i] = value;
		}
		break;
	}

	case 3:
	{
		char *temp2 = (char *) temp;
		for (int i = 0; i < numElems; i++)
		{
			int value = saturate(bufferIn[i] * 8388608.0f, -8388608.0f,
					8388607.0f);
			*((int*) temp2) = value;
			temp2 += 3;
		}
		break;
	}

	case 4:
	{
		int *temp2 = (int *) temp;
		for (int i = 0; i < numElems; i++)
		{
			int value = saturate(bufferIn[i] * 2147483648.0f, -2147483648.0f,
					2147483647.0f);
			temp2[i] = value;
		}
		break;
	}
	default:
		//should throw
		break;
	}
	for (int i = 0; i < numBytes / 2; ++i)
	{
		bufferOut->push(temp[i] & 0xff);
		bufferOut->push((temp[i] >> 8) & 0xff);
	}
	delete[] temp;
	temp = NULL;
	return bufferOut->size() - oldSize;
}

static void setPitchSemi(SoundTouchStream& soundTouch, float pitchSemi)
{
	soundTouch.setPitchSemiTones(pitchSemi);
}

static void setTempo(SoundTouchStream& soundTouch, float tempo)
{
	soundTouch.setTempo(tempo);
}
static void setTempoChange(SoundTouchStream& soundTouch, float tempoChange)
{
	soundTouch.setTempoChange(tempoChange);
}
static void setup(SoundTouchStream& soundTouch, int channels, int sampleRate,
		int bytesPerSample, float tempoChange, float pitchSemi)
{
	soundTouch.setBytesPerSample(bytesPerSample);

	soundTouch.setSampleRate(sampleRate);
	soundTouch.setChannels(channels);

	soundTouch.setTempo(tempoChange);
	soundTouch.setPitchSemiTones(pitchSemi);
	soundTouch.setRateChange(0);

	soundTouch.setSetting(SETTING_USE_QUICKSEEK, false);
	soundTouch.setSetting(SETTING_USE_AA_FILTER, true);

	//todo if speech
	if (false)
	{
		// use settings for speech processing
		soundTouch.setSetting(SETTING_SEQUENCE_MS, 40);
		soundTouch.setSetting(SETTING_SEEKWINDOW_MS, 15);
		soundTouch.setSetting(SETTING_OVERLAP_MS, 8);
		//fprintf(stderr, "Tune processing parameters for speech processing.\n");
	}
}

static void convertInput(jbyte* input, float* output, const int BUFF_SIZE,
		int bytesPerSample)
{
	switch (bytesPerSample)
	{
	case 1:
	{
		unsigned char *temp2 = (unsigned char*) input;
		double conv = 1.0 / 128.0;
		for (int i = 0; i < BUFF_SIZE; i++)
		{
			output[i] = (float) (temp2[i] * conv - 1.0);
		}
		break;
	}
	case 2:
	{
		short *temp2 = (short*) input;
		double conv = 1.0 / 32768.0;
		for (int i = 0; i < BUFF_SIZE; i++)
		{
			short value = temp2[i];
			output[i] = (float) (value * conv);
		}
		break;
	}
	case 3:
	{
		char *temp2 = (char *) input;
		double conv = 1.0 / 8388608.0;
		for (int i = 0; i < BUFF_SIZE; i++)
		{
			int value = *((int*) temp2);
			value = value & 0x00ffffff;             // take 24 bits
			value |= (value & 0x00800000) ? 0xff000000 : 0; // extend minus sign bits
			output[i] = (float) (value * conv);
			temp2 += 3;
		}
		break;
	}
	case 4:
	{
		int *temp2 = (int *) input;
		double conv = 1.0 / 2147483648.0;
		assert(sizeof(int) == 4);
		for (int i = 0; i < BUFF_SIZE; i++)
		{
			int value = temp2[i];
			output[i] = (float) (value * conv);
		}
		break;
	}
	}
}
static inline int saturate(float fvalue, float minval, float maxval)
{
	if (fvalue > maxval)
	{
		fvalue = maxval;
	}
	else if (fvalue < minval)
	{
		fvalue = minval;
	}
	return (int) fvalue;
}
#endif
