/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
#include <jni.h>
#include "endianness.h"

#ifndef _Included_org_angproj_io_buf_Internals
#define _Included_org_angproj_io_buf_Internals
#ifdef __cplusplus
extern "C" {
#endif

static const char *JNIT_CLASS = "org/angproj/io/buf/Internals";

/*
 * Class:     org_angproj_io_buf_Internals
 * Method:    get_endian
 * Signature: ()I
 */
static jint get_endian(JNIEnv * env, jclass thisClass){
    return endian();
}

/*
 * Class:     org_angproj_io_buf_Internals
 * Method:    do_speedmemcpy
 * Signature: (JJI)V
 */
static void do_speedmemcpy(JNIEnv * env, jclass thisClass, jlong dest, jlong source, jint n){
    speedmemcpy((void *) dest, (void *) source, n);
}

/*
 * Class:     org_angproj_io_buf_Internals
 * Method:    do_speedbzero
 * Signature: (JI)V
 */
static void do_speedbzero(JNIEnv * env, jclass thisClass, jlong s, jint n){
    speedbzero((void *) s, n);
}

static JNINativeMethod funcs[] = {
	{ "endian", "()I", (void *)&get_endian },
	{ "speedmemcpy", "(JJI)V", (void *)&do_speedmemcpy },
	{ "speedbzero", "(JI)V", (void *)&do_speedbzero },
};

#define CURRENT_JNI JNI_VERSION_1_6

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
{
	JNIEnv *env;
	jclass  cls;
	jint    res;

	(void)reserved;

	if ((*vm)->GetEnv(vm, (void **)&env, CURRENT_JNI) != JNI_OK)
		return -1;

	cls = (*env)->FindClass(env, JNIT_CLASS);
	if (cls == NULL)
		return -1;

	res = (*env)->RegisterNatives(env, cls, funcs, sizeof(funcs)/sizeof(*funcs));
	if (res != 0)
		return -1;

	return CURRENT_JNI;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved)
{
	JNIEnv *env;
	jclass  cls;

	(void)reserved;

	if ((*vm)->GetEnv(vm, (void **)&env, CURRENT_JNI) != JNI_OK)
		return;

	cls = (*env)->FindClass(env, JNIT_CLASS);
	if (cls == NULL)
		return;

	(*env)->UnregisterNatives(env, cls);
}


#ifdef __cplusplus
}
#endif
#endif // _Included_org_angproj_io_buf_Internals