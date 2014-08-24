package com.tencent.news.utils;

import com.tencent.news.shareprefrence.SpConfig;
import com.tencent.news.shareprefrence.SpForbidenCommentNews;
import com.tencent.news.shareprefrence.SpNewsHadRead;
import com.tencent.news.shareprefrence.SpTopicHadVoted;
import com.tencent.news.shareprefrence.SpUserHelp;

public class SharedPrefManager {
	
	//程序更新擦除数据
	public static void updataDeleteAll(){
		SpConfig.delCheckUpdateFlag();
		SpConfig.delSilentDownloadFlag();
		SpForbidenCommentNews.delAll();
		SpNewsHadRead.delAll();
		SpUserHelp.delAllHelp();
		SpTopicHadVoted.delVoteOptidAll();
	}
}
