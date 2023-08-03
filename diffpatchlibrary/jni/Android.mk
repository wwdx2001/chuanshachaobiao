LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE     := diffpatchlibrary
LOCAL_SRC_FILES  := com_sh3h_meterreading_util_PatchUtils.c

LOCAL_LDLIBS     := -lz -llog

include $(BUILD_SHARED_LIBRARY)