#include <jni.h>
#include <android/bitmap.h>


// create global variables
static jobject bitmap;
static void *pixels;
static AndroidBitmapInfo info;

static jobject bitmap2;
static void *pixels2;
static AndroidBitmapInfo info2;


// create global functions
void pixel(JNIEnv *env, jobject bmp, jint x, jint y, jint r, jint g, jint b) {

    void* pxs;
    AndroidBitmapInfo inf;
    AndroidBitmap_getInfo(env, bmp, &inf);
    AndroidBitmap_lockPixels(env, bmp, &pxs);

    auto* pixelData = (uint32_t*) pxs;
    uint32_t pixel = (0xFF << 24) | (b << 16) | (g << 8) | r;
    pixelData[y * inf.stride / 4 + x] = pixel;
    AndroidBitmap_unlockPixels(env, bmp);
}


void lockBitmap(JNIEnv *env, jobject bmp) {
    bitmap = env->NewGlobalRef(bmp);
    AndroidBitmap_getInfo(env, bitmap, &info);
    AndroidBitmap_lockPixels(env, bitmap, &pixels);
}

void unlockBitmap(JNIEnv *env) {
    AndroidBitmap_unlockPixels(env, bitmap);
    env->DeleteGlobalRef(bitmap);
}

void pixel(__attribute__((unused)) JNIEnv *env, jint x, jint y, jint r, jint g, jint b) {
    auto* pixelData = (uint32_t*) pixels;
    uint32_t pixel = 255 << 24 | b << 16 | g << 8 | r;
    pixelData[y * info.stride / 4 + x] = pixel;
}


void gradient(__attribute__((unused)) JNIEnv *env) {
    auto* pixelData = (uint32_t*) pixels;
    for (int y = 0; y < info.height; y++) {
        for (int x = 0; x < info.width; x++) {
            uint32_t pixel = (0xFF << 24) | (255 << 16) | (y * 255 / info.height << 8) | x * 255 / info.width;
            pixelData[y * info.stride / 4 + x] = pixel;
        }
    }
}


void lockBitmap2(JNIEnv *env, jobject bmp) {
    bitmap2 = env->NewGlobalRef(bmp);
    AndroidBitmap_getInfo(env, bitmap2, &info2);
    AndroidBitmap_lockPixels(env, bitmap2, &pixels2);
}

void unlockBitmap2(JNIEnv *env) {
    AndroidBitmap_unlockPixels(env, bitmap2);
    env->DeleteGlobalRef(bitmap2);
}

void pixel2(__attribute__((unused)) JNIEnv *env, jint x, jint y, jint r, jint g, jint b) {
    auto* pixelData = (uint32_t*) pixels2;
    uint32_t pixel = 255 << 24 | b << 16 | g << 8 | r;
    pixelData[y * info.stride / 4 + x] = pixel;
}


void gradient2(__attribute__((unused)) JNIEnv *env) {
    auto* pixelData = (uint32_t*) pixels2;
    for (int y = 0; y < info.height; y++) {
        for (int x = 0; x < info.width; x++) {
            uint32_t pixel = (0xFF << 24) | (255 << 16) | (y * 255 / info.height << 8) | x * 255 / info.width;
            pixelData[y * info.stride / 4 + x] = pixel;
        }
    }
}


// create JNI functions
extern "C" JNIEXPORT void JNICALL
Java_uz_alien_1dev_graphtestforandroid_manipulate_ManipulatePixel1_pixel(JNIEnv *env, jobject /* this */, jobject bmp, jint x, jint y, jint r, jint g, jint b) {
    pixel(env, bmp, x, y, r, g, b);
}


extern "C" JNIEXPORT void JNICALL
Java_uz_alien_1dev_graphtestforandroid_manipulate_ManipulatePixel2_lockBitmap(JNIEnv *env, jobject /* this */, jobject bmp) {
    lockBitmap(env, bmp);
}
extern "C" JNIEXPORT void JNICALL
Java_uz_alien_1dev_graphtestforandroid_manipulate_ManipulatePixel2_unlockBitmap(JNIEnv *env, jobject /* this */) {
    unlockBitmap(env);
}
extern "C" JNIEXPORT void JNICALL
Java_uz_alien_1dev_graphtestforandroid_manipulate_ManipulatePixel2_pixel(JNIEnv *env, jobject /* this */, jint x, jint y, jint r, jint g, jint b) {
    pixel(env, x, y, r, g, b);
}


extern "C" JNIEXPORT void JNICALL
Java_uz_alien_1dev_graphtestforandroid_manipulate_ManipulateCpp_lockBitmap(JNIEnv *env, jobject /* this */, jobject bmp) {
    lockBitmap(env, bmp);
}
extern "C" JNIEXPORT void JNICALL
Java_uz_alien_1dev_graphtestforandroid_manipulate_ManipulateCpp_unlockBitmap(JNIEnv *env, jobject /* this */) {
    unlockBitmap(env);
}
extern "C" JNIEXPORT void JNICALL
Java_uz_alien_1dev_graphtestforandroid_manipulate_ManipulateCpp_pixel(JNIEnv *env, jobject /* this */, jint x, jint y, jint r, jint g, jint b) {
    pixel(env, x, y, r, g, b);
}
extern "C" JNIEXPORT void JNICALL
Java_uz_alien_1dev_graphtestforandroid_manipulate_ManipulateCpp_gradient(JNIEnv *env, jobject /* this */) {
    gradient(env);
}


extern "C" JNIEXPORT void JNICALL
Java_uz_alien_1dev_graphtestforandroid_manipulate_CppSurfaceView_lockBitmap(JNIEnv *env, jobject /* this */, jobject bmp) {
    lockBitmap2(env, bmp);
}
extern "C" JNIEXPORT void JNICALL
Java_uz_alien_1dev_graphtestforandroid_manipulate_CppSurfaceView_unlockBitmap(JNIEnv *env, jobject /* this */) {
    unlockBitmap2(env);
}
extern "C" JNIEXPORT void JNICALL
Java_uz_alien_1dev_graphtestforandroid_manipulate_CppSurfaceView_pixel(JNIEnv *env, jobject /* this */, jint x, jint y, jint r, jint g, jint b) {
    pixel2(env, x, y, r, g, b);
}
extern "C" JNIEXPORT void JNICALL
Java_uz_alien_1dev_graphtestforandroid_manipulate_CppSurfaceView_gradient(JNIEnv *env, jobject /* this */) {
    gradient2(env);
}
