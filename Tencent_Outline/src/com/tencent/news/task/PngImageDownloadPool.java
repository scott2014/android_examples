package com.tencent.news.task;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.tencent.news.command.GetImageRequest;
import com.tencent.news.command.GetImageResponse;
import com.tencent.news.http.HttpEngine;
import com.tencent.news.http.HttpEngine.HttpCode;
import com.tencent.news.http.HttpGetEngine;
import com.tencent.news.model.pojo.HttpResult;
import com.tencent.news.model.pojo.ImageInfo;
import com.tencent.news.model.pojo.ImageResult;
import com.tencent.news.model.pojo.ImageType;
import com.tencent.news.system.Application;
import com.tencent.news.system.ExternalStorageReceiver;
import com.tencent.news.system.NetStatusReceiver;
import com.tencent.news.utils.ImageUtil;
import com.tencent.news.utils.SLog;

public class PngImageDownloadPool {
	private static PngImageDownloadPool instance = null;
	private static final int POOL_SIZE = 2;
	private ArrayList<ImageInfo> mImagesPrepareQueue = new ArrayList<ImageInfo>();
	private ImageInfo[] mImageProcessingArray = new ImageInfo[POOL_SIZE];
	private ImageThread[] mImageThread = new ImageThread[POOL_SIZE];
	private boolean[] mProcessingState = { false, false };
	private static final int MAX_PREPARE_NUM = 20;

	public PngImageDownloadPool(){
		
	}

	public static synchronized PngImageDownloadPool getInstance() {
		if (instance == null) {
			instance = new PngImageDownloadPool();
		}
		return instance;
	}

	public void addTask(ImageType imageType, GetImageRequest request, GetImageResponse response) {
		synchronized (mImagesPrepareQueue) {
			ImageInfo imageinfo = new ImageInfo(imageType, request, response);

			if (mImagesPrepareQueue.contains(imageinfo) || (mImageProcessingArray[0] != null && mImageProcessingArray[0].equals(imageinfo))
					|| (mImageProcessingArray[1] != null && mImageProcessingArray[1].equals(imageinfo))) {
				return;
			}

			// 添加到任务队列中
			mImagesPrepareQueue.add(imageinfo);
			int length = mImagesPrepareQueue.size();
			if (length >= MAX_PREPARE_NUM) {
				ImageInfo removeinfo  = mImagesPrepareQueue.remove(0);
				onRecvError(removeinfo.getResponse(), removeinfo.getImageType(), removeinfo.getRequest().getTag(), ImageResult.ERROR_NET_ACCESS);
			}

			for (int i = 0; i < mProcessingState.length; i++) {
				if (mProcessingState[i] == false) {
					// 开始下载任务
					mProcessingState[i] = true;
					mImageThread[i] = new ImageThread(i);
					mImageThread[i].start();
					break;
				}
			}
		}
	}
	
	/**
	 * 退出系统时使用
	 */
	public void waitThreadExit() {
		for (int i = 0; i < POOL_SIZE; i++) {
			try {
				if ( mImageThread[i] != null )
					mImageThread[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void cancelAllThread() {
		if(mImagesPrepareQueue != null && mImagesPrepareQueue.size() > 0){
			synchronized (mImagesPrepareQueue) {
				mImagesPrepareQueue.clear();
			}
		}
		for (int i = 0; i < POOL_SIZE; i++) {
			if (mProcessingState[i] && mImageThread[i] != null) {
				mImageThread[i].cancel();
			}
			mProcessingState[i] = false;
		}
	}

	private ImageInfo pullOneTaskInHeadSync() {
		synchronized (mImagesPrepareQueue) {
			if (mImagesPrepareQueue.size() == 0) {
				return null;
			}

			ImageInfo headInfo = mImagesPrepareQueue.get(0);
			mImagesPrepareQueue.remove(0);
			return headInfo;
		}
	}

	private void onRecvError(final GetImageResponse response, final ImageType imageType, final Object tag, final int errorCode) {
		Application.getInstance().runOnUIThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				response.onImageRecvError(imageType, tag, errorCode);
			}
		});
	}

	private void onRecvOK(final GetImageResponse response, final ImageType imageType, final Object tag, final Bitmap bitmap, final String path) {
		Application.getInstance().runOnUIThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				response.onImageRecvOK(imageType, tag, bitmap, path);
			}
		});
	}


	private class ImageThread extends Thread {

		private int runId = 0;
		private boolean cancel = false;

		public ImageThread(int runId) {
			super();
			this.runId = runId;
		}

		public void cancel() {
			this.cancel = true;
		}

		public Bitmap getBitmapFromNet(GetImageRequest request, GetImageResponse response) {
			if (request.isCancelled()) {
				return null;
			}
			Bitmap retBitmap = null;
			HttpResult result = null;

			//** 从网络取数据 *//*
			HttpEngine httpEngine = new HttpGetEngine(request);
			result = httpEngine.execute();

			if (result.getResultCode() == HttpCode.STATUS_OK && result.getData() != null && result.getData().length > 0) {
				retBitmap = ImageUtil.FromByteToBitmap(result.getData());
			}
			return retBitmap;
		}

		@SuppressWarnings("unused")
		@Override
		public void run() {
			mProcessingState[runId] = true;
			while (!cancel) {
				mImageProcessingArray[runId] = pullOneTaskInHeadSync();

				if (mImageProcessingArray[runId] == null) {
					break;
				}

				GetImageRequest request = mImageProcessingArray[runId].getRequest();
				GetImageResponse response = mImageProcessingArray[runId].getResponse();
				ImageType type = mImageProcessingArray[runId].getImageType();
				Bitmap returnBitmap = null;

				/** 从网络取数据 */
				if (NetStatusReceiver.netStatus == NetStatusReceiver.NETSTATUS_INAVAILABLE) {
					onRecvError(response, type, request.getTag(),ImageResult.ERROR_URL_NULL);
				} else {
					try {
						SLog.v("download web image : " ,request.getUrl());
						returnBitmap = getBitmapFromNet(request, response);
						
						if (returnBitmap == null) {
							onRecvError(response, type, request.getTag(), ImageResult.ERROR_NET_ACCESS);
						} else {
							if (ExternalStorageReceiver.isSDCardMounted) {
								ImageUtil.saveBitmapPNG(returnBitmap, request.getFilePath(), ImageUtil.QUALITY_HIGH);
							}
							if (returnBitmap != null) {
								TaskManager.putImageInCache(type, request.getFilePath(), returnBitmap);
								SLog.i("image download", request.getFilePath());
								onRecvOK(response, type, request.getTag(), returnBitmap, request.getFilePath());
							} else {
								onRecvError(response, type, request.getTag(), ImageResult.ERROR_OOM);
							}
						}
					} catch (OutOfMemoryError e) {
						SLog.e(e.toString(), e);
						onRecvError(response, type, request.getTag(), ImageResult.ERROR_OOM);
					} catch (Exception e) {
						SLog.e(e.toString(), e);
						onRecvError(response, type, request.getTag(), ImageResult.ERROR_NET_ACCESS);
					} 
				}
			}
			mProcessingState[runId] = false;
		}
	}
}
