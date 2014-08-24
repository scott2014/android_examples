//TencentNews start
var TencentNews = {};
TencentNews.post = function(url, data, success, failed) {
	alert('before query');
		if ( typeof window.JsInterface != 'undefined') {
			window.JsInterface.queryData(url, data, success, failed);
		}
};
//end TencentNews
var jsUtil = {
	bind : function(el, evt, func) {
		if (el.addEventListener) {
			el.addEventListener(evt, func, false);
		} else if (el.attachEvent) {
			el.attachEvent('on' + evt, func);
		}
	}
};

//start webCellManager
var webCellManager = {
	shortcutTimer : null,
	hasInited : false,
	init : function() {
		if (webCellManager.hasInited) {
			return;
		}
		jsUtil.bind(document, 'DOMContentLoaded', function() {
			setTimeout(function() {
				var imgs = document.getElementsByTagName('img');
				var count = imgs.length;
				for (var i = 0; i < count; i++) {
					jsUtil.bind(imgs[i], 'load', function() {
						webCellManager.onWebCellUIChanged();
					});
				}
				webCellManager.hasInited = true;
			}, 1000);
		});
	},
	onWebCellReady : function() {
		webCellManager.init();
		if ( typeof window.JsInterface != 'undefined') {
			window.JsInterface.onWebCellReady();
		}
	},
	onWebCellError : function() {
		if ( typeof window.JsInterface != 'undefined') {
			window.JsInterface.onWebCellError();
		}
	},
	onWebCellUIChanged : function() {
		if (webCellManager.shortcutTimer) {
			clearTimeout(webCellManager.shortcutTimer);
		}
		webCellManager.shortcutTimer = setTimeout(function() {
			webCellManager.shortcutTimer = null;
		if ( typeof window.JsInterface != 'undefined') {
			window.JsInterface.onWebCellUIChanged();
		}
		}, 1000)
	},

	//begin native-web interactive protocol
	nativeDidFinishLoad : function() {
		webCellManager.onWebCellReady();
	},
	channelDidAppear : function() {

	},
	channelDidDisappear : function() {

	},
	channelDidRefreshData : function() {

	},
	themeChanged : function(theme) {

	},
	loginStatueChanged : function() {

	},
	//end native-web interactive protocol
};
//end webCellManager