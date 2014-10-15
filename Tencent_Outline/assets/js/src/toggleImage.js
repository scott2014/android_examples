/**
 * 文字模式图片切换
 */
var toggleImage = function(window) {	
	/**
	 * 全局模版变量
	 */
	var view = {
		/**
		 * loadingHTML字段
		 */
		loadHTML : [		
		  '<div class="bar1"></div>',
		  '<div class="bar2"></div>',
		  '<div class="bar3"></div>',
		  '<div class="bar4"></div>',
		  '<div class="bar5"></div>',
		  '<div class="bar6"></div>',
		  '<div class="bar7"></div>',
		  '<div class="bar8"></div>'		
		].join("")		
	};

	return {	
		/**
		 * 绑定事件
		 */
		bindEvent : function() {
			var paraItems = document.querySelectorAll('.imageText,.videoText');
			for (var i = 0, n = paraItems.length; i < n ; i++) {
				var element = paraItems[i];
				element.addEventListener("click", toggleImage.clickHandler, false);			
			}
		},
		
		/**
		 * 动态创建一个loading动画
		 */
		 createHTML : function(element) {
			 var e = document.createElement("div");
			 e.className = "loading";
			 e.innerHTML = view.loadHTML;
			 element.appendChild(e);
		 },
		 
		 /**
		  * 添加loading框, 只有在文字模式的时候添加
		  */
		 addLoading : function(target) {
			if(!Q.hasClass(target, "loadingText")) {
				toggleImage.createHTML(target);
				Q.addClass(target, "loadingText");
			}
		 },
		
		/**
		 * 隐藏
		 */
		clickHandler : function(e) {
			var target = e.target;
			toggleImage.clickController(target);		
		},
		
		/**
		 * 点击事件业务逻辑处理类
		 */
		clickController : function(target) {			
			if(Q.hasClass(target, "downloadFailure")) {				
				Q.removeClass(target, 'downloadFailure');
			}			
			if(Q.hasClass(target, "video")) {				
				var divItem = target.querySelector('.mod_player');
				var qtype = divItem.attributes['vtype'].nodeValue;
				if (qtype == tna.videoSourceLive) {	
					tna.adjustImageSize(target);
					tna.initVideo();				
					if(Q.hasClass(target, "videoText")) {		
						Q.removeClass(target, 'videoText');
					}
					return false;
				}
				if (window.TencentNews && window.TencentNews.showVideoImage) {
					window.TencentNews.showVideoImage();
				}
				toggleImage.addLoading(target);	
			} else if(Q.hasClass(target, "image")) {
				var imgNode = target.querySelector('img');
				if (imgNode && window.TencentNews && window.TencentNews.imageNeedsPrepare) {
					var imgIndex = imgNode.attributes['index'].nodeValue;
					window.TencentNews.imageNeedsPrepare(imgIndex);
				}
				toggleImage.addLoading(target);	
			} else if(Q.hasClass(target, "voteText") || Q.hasClass(target, "voteContainer")) {
				toggleImage.addLoading(target);	
				toggleImage.loadVoteHtml(target);
			}
		},
		
		/**
		 * 移除添加的事件
		 */
		removeHandler : function(element) {
			if(Q.hasClass(element, "imageText")) {		
				Q.removeClass(element, 'imageText');
			}
			if(Q.hasClass(element, "videoText")) {		
				Q.removeClass(element, 'videoText');
			}	
			element.removeEventListener("click", toggleImage.clickHandler, false);
		},
		
		/**
		 * 移除loading过程
		 */
		removeLoading : function(target) {
			Q.removeClass(target, "loadingText");
			var loading = target.querySelector('.loading');
			if(loading) {
				target.removeChild(loading);
			}			
		},
		
		/**
		 * 读取投票的信息
		 */
		loadVoteHtml : function(target) {			
			if(voteConfig && voteConfig.voteJsUrl) {
				var jsUrl = voteConfig.voteJsUrl;
				Q.loadJsFile(jsUrl, function(success, link ) {
				   if(success) {
						loadVoteJsComplete && loadVoteJsComplete();							
						if(Q.hasClass(target, "downloadFailure")) {				
							Q.removeClass(target, 'downloadFailure');
						}	
						if(Q.hasClass(target, "voteText")) {		
							Q.removeClass(target, 'voteText');
						}
						toggleImage.removeLoading(target);	
				   } else {
						toggleImage.removeLoading(target);					   
						Q.removeClass(target, 'downloadFailure');
						Q.addClass(target, 'downloadFailure');
				   }
				});
			}
		}
	};
}(window);