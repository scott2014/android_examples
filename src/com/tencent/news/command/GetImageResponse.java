package com.tencent.news.command;

import android.graphics.Bitmap;

import com.tencent.news.model.pojo.ImageType;

public interface GetImageResponse {
	void onImageRecvOK(ImageType imageType,Object tag, Bitmap bm, String path);
	void onImageRecvError(ImageType imageType, Object tag, int retCode);
}
