/**
 * 目前代码是在以前的代码上添加和修改, 后来才添加的注释
 * 可以在svn上看原来代码
 * @Namespace: Tencent News for Android
 * @auther wangzhishou@qq.com
 * @date 2013/04/26
 */
var tna = {};
tna.videoSourceOnDemand = 1;    
tna.videoSourceLive = 2;        


/**
 * 更新评论数据
 * @param {count} 需要显示的评论数
 */
tna.updateCommentCount = function (count) {
	var commentContainer = document.getElementById('comment_count');
	var labels = commentContainer.getElementsByTagName('label');
	count = parseInt(count);
	if (labels.length > 0) {
		var label = labels[0];
		if(count > 0) {
			commentContainer.style.display = "";
			label.innerHTML = count;
		} else {
			commentContainer.style.display = "none";
		}
	}
};

/**
 * 调整单个图片大小
 * @param {Element} 要调整的图片元素
 * @param {Element} 要调整的图片元素的父级元素
 * @remark 
   在android2.2版本一下，有时候获取的页面宽度为0，需要native传入
 */
tna.adjustImageSize = function(parent) {
	var img = parent.querySelector('img');
	if(!img) {
		return false;	
	}
	
	var bodyWidth = document.body.attributes['data-webview-width'].nodeValue / window.devicePixelRatio;
	var box = document.getElementById("content");	
	var bodyStyle = window.getComputedStyle(box, null);
	var contentW = bodyWidth - parseInt(bodyStyle['margin-left']) - parseInt(bodyStyle['margin-right']);
	var imgNaturalW = img.attributes['data-natural-width'].nodeValue;
	var imgNaturalH = img.attributes['data-natural-height'].nodeValue;
	if (imgNaturalW == 0) {
		return;
	}
	var paraW = Math.max(parent.offsetWidth, contentW);
	var paraH = imgNaturalH * (paraW/imgNaturalW);
	parent.style.height = paraH + "px";
	parent.style.maxHeight = paraW + "px";		
	var modPlayer = img.parentNode;
	modPlayer.style.height = Math.min(paraH, paraW) + "px";	
};

/**
 * 调整所有图片大小
 */
tna.adjustAllImageSize = function () {			
	var paraItems = document.querySelectorAll('.image,.video');
	for (var i=0; i<paraItems.length; i++) {
		var para = paraItems[i];
		tna.adjustImageSize(para);
	}
}

/**
 * 从相册返回后，移动到相应图片位置
 */
tna.moveImageToClient = function (index) {
	var imagePara = null;
	var imgItems = document.querySelectorAll('.image img');
	for (var i=0; i<imgItems.length; i++) {
		var imgItem = imgItems[i];
		if (imgItem.attributes['index']) {
			var imgIndex = imgItem.attributes['index'].nodeValue;
			if (imgIndex == index) {
				imagePara = imgItem.parentNode;
				break;
			}
		}
	}	
	if (!imagePara) {
		return;
	}
	var scrollTop = document.documentElement.scrollTop;
	var offset    = (document.documentElement.clientHeight - imagePara.clientHeight) / 2;
	var y = tna.getElementViewTop(imagePara) + scrollTop - offset;
	window.scrollTo(0, y);
};

/**
 * 点击图片预览
 */
tna.onClickDelegate = function (e) {
	var target = e.target;
	var parent = Q.parent(target, function(p) {
		return (p.tagName.toLowerCase() == "div") ? true : false;
	});	
	if (target.tagName.toLowerCase() == 'img' && Q.hasClass(parent, 'image') && !Q.hasClass(parent, 'imageText')) {
		tna.onImageClicked(e);
	}
	if(tna.isImageOrVideoClick(target)) {
		toggleImage.clickController(target);
		return;
	}
	if(parent && tna.isImageOrVideoClick(parent)) {		
		toggleImage.clickController(parent);
		return;		
	}
	if(Q.hasClass(target, 'voteText')) {
		toggleImage.clickController(target);
		return;
	}
}

/**
 * 判断点击的元素是否是图片或视频
 * 是否可以点击加载图片
 */
tna.isImageOrVideoClick = function (target) {
	if(!target) {
		return false;
	}
	return Q.hasClass(target, 'downloadFailure') || Q.hasClass(target, 'imageText') || Q.hasClass(target, 'videoText');
};

/**
 * 点击图片放大图片，相册显示图片
 */
tna.onImageClicked = function (e) {
	var currImage = e.target.src;
	var imgIndex = e.target.attributes['index'].nodeValue;
	if (window.TencentNews) {
		window.TencentNews.zoomImage(currImage, imgIndex);
	}
}

/**
 * 检测是否在两屏区域内，然后预加载图片
 */
tna.detectImageMovement = function (scrollDir) {
	var viewH = document.documentElement.clientHeight;	
	var scrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
	if (viewH == 0) {
		viewH = document.body.attributes['data-webview-height'].nodeValue / window.devicePixelRatio;
	}
	
	var imgItems = document.querySelectorAll('.image img, .mod_player img');
	var imgLen = imgItems.length;
	for (var i=0; i<imgLen; i++) {
		var imgNode = imgItems[i];
		var parentNode = Q.parent(imgNode, function(p) {
			return (p.tagName.toLowerCase() == "div") ? true : false;
		});			
		if(parentNode && Q.hasClass(parentNode, "video")) {
			if (window.TencentNews && window.TencentNews.showVideoImage) {
					window.TencentNews.showVideoImage();
			}			
		} else {
			var imgViewTop = tna.getElementViewTop(imgNode);
			var middleView = imgViewTop + parentNode.offsetHeight / 2;
			if((middleView > scrollTop - viewH) && middleView < (2*viewH + scrollTop)) {
				if (window.TencentNews && window.TencentNews.imageNeedsPrepare) {
					var imgIndex = imgNode.attributes['index'].nodeValue;
					window.TencentNews.imageNeedsPrepare(imgIndex);
				}	
			}
		}
	}
}

/**
 * 视频点击播放
 */
tna.onVideoClicked = function (e) {
	var divItem = this;
	var currVid = divItem.attributes['vid'].nodeValue;
	var currVType = divItem.attributes['vtype'].nodeValue;
	var currVUrl = divItem.attributes['vurl'].nodeValue;
		
	if(currVType == tna.videoSourceLive) {
		var playBtn = divItem.querySelector('span');
		if(playBtn.style.display != 'inline') {
			return false;
		}
	}
	
	if (window.TencentNews) {
		window.TencentNews.playVideo(currVid, currVType, currVUrl);
	}
}

/**
 * 监听webview内滚动条滚动
 */
tna.lastBodyScrollTop = 0;
tna.scrollDirDown = 1;
tna.scrollDirUp = 2;
tna.timer = null;
tna.onScroll = function (e) {
	var scrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
    if (scrollTop == tna.lastBodyScrollTop) {
        return;
    }    
    var scrollDir = (scrollTop > tna.lastBodyScrollTop) ? tna.scrollDirDown : tna.scrollDirUp;
    tna.lastBodyScrollTop = scrollTop;
	var latency = 300;
    if (tna.timer) {
        clearTimeout(tna.timer);
    }
    tna.timer = setTimeout( function(){
        tna.timer = null;
        tna.detectImageMovement(scrollDir);
    }, latency);
}

function countDownTimerCallback(delta) { 
    //var nD = Math.floor(delta/(60 * 60 * 24));  
    var nH = Math.floor(delta/(60*60))/* % 24*/;  
    var nM = Math.floor(delta/(60)) % 60;  
    var nS = Math.floor(delta) % 60;
    var deltaStr = /*nD+'天'+*/nH+'时'+nM+'分'+nS+'秒';
	
	document.getElementById('countdown').innerHTML = '离直播还有'+deltaStr;
}

function startCountDownTimer(currTimeStr, beginTimeStr, endTimeStr, callback) {
    var ctime = new Date(currTimeStr);
    var btime = new Date(beginTimeStr);
    var etime = new Date(endTimeStr);

    if(etime.getTime() - ctime.getTime() <= 0) {
    	document.getElementById('countdown').innerHTML = '直播已结束';
    	return;
    }
    
    var delta = (btime.getTime() - ctime.getTime()) / 1000;//second
    if (delta < 0) {
        delta = 0;
    }
    callback(delta);

	if (delta == 0) {
		document.getElementById('countdown').innerHTML = '';

		var playBtn = document.querySelector('.mod_player span');
		playBtn.style.display = 'inline';
	}

    var intId = setInterval(function() {
    	var delta = (btime.getTime() - ctime.getTime()) / 1000;
        if (delta < 0) {
        	delta = 0;
        }
        callback(delta);

        if (delta == 0) {
        	clearInterval(intId);

			document.getElementById('countdown').innerHTML = '';

			var playBtn = document.querySelector('.mod_player span');
			playBtn.style.display = 'inline';
        }
        
        ctime.setTime(ctime.getTime()+1000);
    }, 1000);
}

tna.getElementViewTop = function (element) {
	var actualTop = element.offsetTop;
	var current = element.offsetParent;
	while (current !== null) {
		actualTop += current.offsetTop;
		current = current.offsetParent;
	}
	if (document.compatMode == "BackCompat") {
		var elementScrollTop = document.body.scrollTop;
	} 
	else {
		var elementScrollTop = document.documentElement.scrollTop; 
	}
	return actualTop - elementScrollTop;
}

window.onload = function() {
	if(!Number(isTextMode)) {
		tna.init();	
	}	
	tna.heightLightRelaNews();
	var button = Q("RssMedia");
	if(button) {
		tna.heightLightElement(button);
	}
	document.onclick = tna.onClickDelegate;
	tna.initVideo();
};

/**
 * 监听webview长按事件
 */
tna.longClick = false;

/**
 * 视频区域内容初始化
 * 第一次初始化的时候显示正在加载中......
 */
tna.initVideo = function() {
	var divItems = document.querySelectorAll('.mod_player');
	var divItemsCount = divItems.length;
    for (var i=0; i<divItemsCount; i++) {
        var divItem = divItems[i];
		divItem.onclick = tna.onVideoClicked;
		var qtype = divItem.attributes['vtype'].nodeValue;
		if (qtype == tna.videoSourceLive) {
			var background = divItem.querySelector('img');
			background.src = "./images/livefloor.png";
			var countdown = document.getElementById('countdown');
			if (!tna.initVideo.isInit && countdown.innerHTML == "") {
				document.getElementById('countdown').innerHTML = '正在加载...';
			}
			var playBtn = divItem.querySelector('span');
		}
	}
	tna.initVideo.isInit = true;
	var touchEndTimer = null;
	var playBtn = document.querySelector('.mod_player');
	if (playBtn) {
		var aTags = playBtn.getElementsByTagName('a');
		playBtn.addEventListener('touchstart', function(e) {
			if (aTags.length > 0) {
				var aTag = aTags[0];
				aTag.className = 'highlighted';
			}
		});
		playBtn.addEventListener('touchmove', function(e) {
			if (touchEndTimer) {
            	clearTimeout(touchEndTimer);
            }
            touchEndTimer = setTimeout(function() {
            	touchEndTimer = null;
            	if (aTags.length > 0) {
					var aTag = aTags[0];
					aTag.className = '';
				}
            }, 400);
		});
		playBtn.addEventListener('touchend', function(e) {
			if (aTags.length > 0) {
				var aTag = aTags[0];
				aTag.className = '';
			}
		});
		playBtn.addEventListener('touchcancel', function(e) {
			if (aTags.length > 0) {
				var aTag = aTags[0];
				aTag.className = '';
			}
		});
	}
};

tna.initVideo.isInit = false;

/**
 * 高亮相关新闻部分
 */
tna.heightLightTouchEndTimer = null;
tna.heightLightRelaNews = function() {
	var aList = document.querySelectorAll('.relatedNews a');
	var count = aList.length;
	var touchEndTimer = null;
	for (var i = 0; i < count; i++) {
		var a = aList[i];
		var parentNode = Q.parent(a, function(p) {
			return (p.tagName.toLowerCase() == "li") ? true : false;
		});	
		a.addEventListener('touchstart', tna.heightLightTouchStart(parentNode), false);
		a.addEventListener('touchmove', tna.heightLightTouchMove(parentNode), false);
		a.addEventListener('touchend', tna.heightLightTouchEnd(parentNode), false);
		a.addEventListener('touchcancel', tna.heightLightTouchEnd(parentNode), false);
		a.addEventListener('click', tna.heightLightClick(a, parentNode), false);		
		var id = Q.getQuery(a.href, "id");
		if(id && window.nativeStorage){
			if(window.nativeStorage.get(id)) {
				Q.addClass(a, "visited");
			};
		}	
	}
};
tna.heightLightClick = function(a, parentNode) {
	return function(e) {
		var id = Q.getQuery(a.href, "id");
		Q.addClass(a, "visited");
		if(id && window.nativeStorage){
			window.nativeStorage.set(id, "1");
		}		
	}	
};
tna.heightLightTouchStart = function(parentNode) {
	return function(e) {
		if (parentNode) {
			Q.addClass(parentNode, "heightLight");
		}		
	}
};
tna.heightLightTouchMove = function(parentNode) {
	return function(e) {
		if (tna.heightLightTouchEndTimer) {
			clearTimeout(tna.heightLightTouchEndTimer);
		}
		tna.heightLightTouchEndTimer = setTimeout(function() {
			tna.heightLightTouchEndTimer = null;
			if (parentNode) {
				parentNode.style.backgroundColor = "";
			}
		}, 400);		
	}
};
tna.heightLightTouchEnd = function(parentNode) {
	return function(e) {
		if (parentNode) {
			parentNode.style.backgroundColor = "";
		}		
	}
};

tna.heightLightElement = function(button) {
	if(button) {
		button.addEventListener('touchstart', function(e) {
			Q.addClass(button, "heightLight");
		}, false);
		button.addEventListener('touchmove', function(e) {
			if (tna.heightLightTouchEndTimer) {
				clearTimeout(tna.heightLightTouchEndTimer);
			}
			tna.heightLightTouchEndTimer = setTimeout(function() {
				tna.heightLightTouchEndTimer = null;
				if (button) {
					Q.removeClass(button, "heightLight");
				}
			}, 400);		
		}, false);
		button.addEventListener('touchend', tna.heightLightElementTouchEnd(button), false);
		button.addEventListener('touchcancel', tna.heightLightElementTouchEnd(button), false);		
		button.addEventListener('click', function() {
			if(button.id == "RssMedia") {
				if (window.TencentNews && window.TencentNews.setFristRssMedia) {
					Q.removeClass(button, "blue-border");
					var rssTooltips = Q("RssTooltips");
					if(rssTooltips) {
						rssTooltips.style.display = "none";
					}
					window.TencentNews.setFristRssMedia();
				}				
			}
		}, false);
	}
};

tna.heightLightElementTouchEnd = function(button) {
	return function(e) {
		if (button) {
			Q.removeClass(button, "heightLight");
		}		
	}
};

/**
 * 页面打开以后初始化
 */
tna.init = function() {
	tna.adjustAllImageSize();	
	window.onscroll = function() {
		if (!tna.longClick) {	
			tna.onScroll();
		}
		tna.longClick = false;
	};	
	tna.detectImageMovement(tna.scrollDirDown);	
};

/**
 * 页面打开以后初始化
 */
tna.reset = function() {
	window.onscroll= null;	
};

/**
 * android回调函数，调用图片显示
 * 如果是文字模式， 移除文字模式样式
 */
function replaceImage(imgId, imgSrc) {
	if (imgSrc == '') {
		return;
	}
	var imgLen = document.images.length;
	for (var i=0; i<imgLen; i++) {
		var imgNode = document.images[i];
		if (imgNode.id == imgId) {
			var parentNode = Q.parent(imgNode, function(p) {
				return (p.tagName.toLowerCase() == "div") ? true : false;
			});
			var loading = parentNode.querySelector('.loading');
			if(loading) {
				Q.removeClass(parentNode, "loadingText");
				parentNode.removeChild(loading);
			}
			if(imgSrc == "101") {
				Q.removeClass(parentNode, 'downloadFailure');
				Q.addClass(parentNode, 'downloadFailure');
				return;
			}			
			imgNode.src = imgSrc;
			if(Q.hasClass(parentNode, 'imageText')) {
				toggleImage.removeHandler(parentNode);
				tna.adjustImageSize(parentNode);
			}
			if(Q.hasClass(parentNode, 'videoText')) {
				toggleImage.removeHandler(parentNode);
				tna.adjustImageSize(parentNode);
				tna.initVideo();
			}
			var modPlayer = imgNode.parentNode;
			if (Q.hasClass(modPlayer, 'mod_player')) {
	        	var delay = 0.12;
	        	setTimeout(function() {
	        		var playBtn = modPlayer.querySelectorAll('span');
					for( var j = 0, m = playBtn.length; j < m; j++ ) {
						var tmpBtn = playBtn[j];
						if(tmpBtn.className && tmpBtn.className.indexOf("video_icon") > -1) {
							tmpBtn.style.display = 'block';
						} else {
							tmpBtn.style.display = 'inline';							
						}					
					}
	        	}, delay*1000);
			}
		}
	}
}

function setVideoLiveStatus(progid, status, btime, etime, ctime) {
	if (status == -1) {
		document.getElementById('countdown').innerHTML = '直播源不存在';
		return;
	}	
	startCountDownTimer(ctime, btime, etime, countDownTimerCallback);
}

/** 
 * 调整文字大小
 */
function changefont(fontsize){
    if(fontsize < 1 || fontsize > 4)return;
    var contentDiv = document.getElementById('content');
    contentDiv.className = 'main fontSize' + fontsize;
}

function scrollToTop() {
	window.scrollTo(0, 0);
}
