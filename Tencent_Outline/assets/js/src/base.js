/**
 * 投票相关js
 * @auther wangzhishou@qq.com
 * @date 2012-11-14
 */
 /**
 * 声明Q包, 并获取Dom对象的封装
 * @name Q
 * @namespace Q
 * @version 0.0.1
 * @description  Q来自QQ的简写，更少的字符，方便书写。
 * @param {string | HTMLElement} element 页面元素对象或元素对象的id
 * @return {Element} dom元素
 */
var Q = Q || function(element) {
	if (!element) {
		return null;
	}
	if (element.constructor == String) {
		element = document.getElementById(element);
	}
	return element;
};

/**
 * 获得元素的父节点
 * @name Q.parent
 * @function
 * @example Q.parent(element[, fun])
 * <pre>
 * <h4>获取元素指定标签的最近的祖先元素：</h4>
 * Q.parent(element, function(p) {
 * 		return (p.tagName == tagName) ? true : false;
 * });
 * <h4>获取目标元素指定元素className最近的祖先元素：</h4>
 * Q.parent(element, function(p) {
 * 		return Q.hasClass(p, "className");
 * });
 * </pre>
 * @param {HTMLElement|string} element   目标元素或目标元素的id 
 * @param {Function} fun 可选参数, 默认是null, 匹配自定义条件的函数, 返回匹配条件的Boolean值。
 * @returns {HTMLElement|null} 父元素，如果找不到父元素，返回null
 */
Q.parent = function(element, fun) {
	element = Q(element);
	fun = fun ? fun : function() {
		return true;
	};
	while ((element = element.parentNode) && element.nodeType == 1) {
		if (fun(element)) {
			return element;
		}
	}
	return null;
};


/**
 * 删除目标字符串两端的空白字符
 * @name Q.trim
 * @function
 * @grammar Q.trim(str)
 * @param {string} str 目标字符串
 * @remark
 * 不支持删除单侧空白字符
 *
 * @returns {string} 删除两端空白字符后的字符串
 */
Q.trim = function(str) {
	var pattern = new RegExp("(^[\\s\\t\\xa0\\u3000]+)|([\\u3000\\xa0\\s\\t]+$)", "g");
	return String(str).replace(pattern, '');
};

/**
 * 为元素添加className
 * @name Q.addClass
 * @example Q.addClass(element, classNames)
 * @param {HTMLElement|string} element 目标元素或目标元素的id
 * @param {string} classNames 要添加的className，允许同时添加多个class，中间使用空白符分隔 *
 * @return {HTMLElement} 目标元素
 */
Q.addClass = function(element, classNames) {
    element = Q(element);

    var classArray = classNames.split(/\s+/),
        result = element.className,
        oldClass = " " + result + " ",
        i = 0,
        l = classArray.length;

    for (; i < l; i++) {
        if (oldClass.indexOf(" " + classArray[i] + " ") < 0) {
            result += (result ? ' ' : '') + classArray[i];
        }
    }

    element.className = Q.trim(result);
    return element;
};

/**
 * 移除元素的className
 * @name Q.removeClass
 * @example Q.removeClass(element[, classNames])
 * @param {HTMLElement|string} element 目标元素或目标元素的id
 * @param {string} classNames 可选参数，要移除的className，允许同时移除多个class，中间使用空白符分隔, 如果为空, 元素cLassName置为空
 * @return {HTMLElement} 目标元素
 */
Q.removeClass = function(element, classNames) {
    element = Q(element);
    var oldClass = element.className.split(/\s+/);
    var newClass = classNames.split(/\s+/);
    var len = oldClass.length;
    var n = newClass.length;
    for (var i = 0; i < n; i++) {
        while (len--) {
            if (oldClass[len] == newClass[i]) {
                oldClass.splice(len, 1);
                break;
            }
        }
    }
    element.className = oldClass.join(' ');
    return element;
};

/**
 * 判断元素是否拥有指定的className
 * @name Q.hasClass
 * @function
 * @example Q.hasClass(element, classNames)
 * @param {HTMLElement|string} element 目标元素或目标元素的id
 * @param {string} classNames 要判断的className，可以是用空格拼接的多个className
 * @see Q.addClass, Q.removeClass
 *
 * @returns {Boolean} 是否拥有指定的className，如果要查询的classname有一个或多个不在元素的className中，返回false
 */
Q.hasClass = function(element, classNames) {

	element = Q(element);
	
	if (!element.nodeType === 1) {
		return false;
	}

	var classArray = Q.trim(classNames).split(/\s+/),
		len = classArray.length;

	className = element.className.split(/\s+/).join(" ");

	while (len--) {
		if (!(new RegExp("(^| )" + classArray[len] + "( |$)")).test(className)) {
			return false;
		}
	}
	return true;
};

/**
 * 将字符串解析成json对象。
 * @name Q.jsonDecode
 * @example
   Q.jsonDecode(data)
 * @param {string} data 需要解析的字符串
 * @remark
 * 不会自动祛除空格。        
 * @returns {JSON} 解析结果json对象
 */
Q.jsonDecode = function (data) {
    return (new Function("return (" + data + ")"))();
};


/**
 * 设置或读取目标元素的style样式值
 * @param {HTMLElement|string} element 目标元素或目标元素的id
 * @param {string} property 要设置的样式属性名
 * @param {string} value 要设置的样式值
 * @remark 不常用的属性没有进行修正
 */
 Q.css = function (element, property, value) {
	 element = Q(element);
	 var toCamelCase = Q.toCamelCase;
	 var isSet = value ? true : false;
	 property = toCamelCase(property);
	 if (isSet) {
		if (property == 'float') {
			property = 'cssFloat';
		}
		element.style[property] = value;
	 } else {
		if (document.defaultView && document.defaultView.getComputedStyle) {
		    var value = null;

		    if (property == 'float') {
		        property = 'cssFloat';
		    }

		    var computed = element.ownerDocument.defaultView.getComputedStyle(element, '');
		    if (computed) { // test computed before touching for safari
		        value = computed[property];
		    }
		    return element.style[property] || value;
		} else { // default to inline only
		    return element.style[property];
		}
	 }
 };

/**
 * 将目标字符串进行驼峰化处理
 * @name Q.toCamelCase
 * @example 
   Q.toCamelCase("font-size"); 
 * @param {string} source 目标字符串
 * @remark
 * 支持单词以“-_”分隔             
 * @returns {string} 驼峰化处理后的字符串
 */
Q.toCamelCase = function(source) {
	if (source.indexOf('-') < 0) {
		return source;
	}
	return source.replace(/[-][^-]/g, function(match) {
		return match.charAt(1).toUpperCase();
	});
};


/**
 * 根据参数名从目标URL中获取参数值
 * @name Q.getQuery
 * @example Q.getQuery(url, key)
 * @param {string} url 目标URL
 * @param {string} key 要获取的参数名
 *             
 * @returns {string|null} - 获取的参数值，获取不到时返回null
 */
Q.getQuery = function (url, key) {
    var reg = new RegExp("(^|&|\\?|#)" + key + "=([^&#]*)(&|$|#)", "");
    var match = url.match(reg);
    if (match) {
        return match[2];
    }    
    return null;
};

/**
 * 异步加载
 * @name Q.loadJs
 * @example Q.loadJs(path, fn, scope)
 * @param {string} path 目标js文件URL
 * @param {Function} fn 回调函数
 * @param {Object} scope 执行域
 */
Q.loadJsFile = function(path, fn, scope) {
	var dl      = document.createElement("script");
	dl.type     = "text/javascript";
	dl.async    = true;
	dl.onload = function() {
		fn.call( scope || window, true, dl);
	};
	dl.onerror = function() {
		fn.call( scope || window, false, dl);
	};
	dl.src = path;
	var s  = document.getElementsByTagName('script')[0]; 
	s.parentNode.insertBefore(dl, s);	
	return dl;	
};
			
/**
 * 屏幕轻触事件模拟
 * @param {HtmlElement} element 需要触发事件的页面元素
 * @param {Function} callback 事件触发回调函数
 * @example:
	var myDiv = document.getElementById('elem');
	Q.tap(myDiv, function(e) {
	    if (e.type === "cancel") {
	        // The event was canceled, like when a phonecall comes in
	    } else {
	        // Look at the touches object
	        console.log(e.touches);
	    } 
	});
 */
Q.tap = function(element, callback) {

	var version, didMove, tapCancelTime, startTime, endTime, _bind;

	version = "1.0.1";
	tapCancelTime = 2 * 1000;

	_bind = function(fn, me) { return function() { return fn.apply(me, arguments); }; };

	if ('ontouchstart' in document.documentElement) {
		element.addEventListener('touchstart',_bind(function(e) {
			startTime = new Date().getTime();
			didMove = false;
		}, element));
	
		element.addEventListener('touchmove',_bind(function(e) {
			didMove = true;
		}, element));
	
		element.addEventListener('touchend',_bind(function(e) {
			endTime = new Date().getTime();
			if(!didMove && ((endTime - startTime) < tapCancelTime)) {
				callback(e);
			}
		}, element));
	
		element.addEventListener('touchcancel',_bind(function(e) {
			callback(e);
		}, element));
	} else {
		element.addEventListener('click', _bind(function(e) {
			callback(e);
		}, element));
	}
};
