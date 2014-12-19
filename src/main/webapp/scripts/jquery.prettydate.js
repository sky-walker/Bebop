;(function($) {

$.prettyDate = {
	template: function(source, params) {
		if ( arguments.length == 1 ) 
			return function() {
				var args = $.makeArray(arguments);
				args.unshift(source);
				return $.prettyDate.template.apply( this, args );
			};
		if ( arguments.length > 2 && params.constructor != Array  ) {
			params = $.makeArray(arguments).slice(1);
		}
		if ( params.constructor != Array ) {
			params = [ params ];
		}
		$.each(params, function(i, n) {
			source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
		});
		return source;
	},
	
	now: function() {
		return new Date();
	},
	
	// Takes an ISO time and returns a string representing how
	// long ago the date represents.
	format: function(time) {
		var date = new Date((time || "").replace(/-/g,"/").replace(/[TZ]/g," ")),
			diff = ($.prettyDate.now().getTime() - date.getTime()) / 1000,
			day_diff = Math.floor(diff / 86400);
		if ( isNaN(day_diff) || day_diff < 0 || day_diff >= 183 )
		//if ( isNaN(day_diff) || day_diff < 0 || day_diff > 60 )
			return;
		
		var messages = $.prettyDate.messages;
		return day_diff == 0 && (
				diff < 60 && messages.now(Math.floor( diff)) ||
				diff < 120 && messages.minute ||
				diff < 3600 && messages.minutes(Math.floor( diff / 60 )) ||
				diff < 7200 && messages.hour ||
				diff < 86400 && messages.hours(Math.floor( diff / 3600 ))) ||
				day_diff == 1 && messages.yesterday ||
				day_diff == 2 && messages.before_yesterday ||
				day_diff < 7 && messages.days(day_diff) ||
				day_diff < 31 && messages.weeks(Math.floor(day_diff / 7)) ||
				day_diff < 183 && messages.months(Math.floor(day_diff / 30)
				);
	}
	
};

$.prettyDate.messages = {
	now: $.prettyDate.template("{0}秒前"),
	minute: "1分钟前",
	minutes: $.prettyDate.template("{0}分钟前"),
	hour: "1小时前",
	hours: $.prettyDate.template("{0}小时前"),
	yesterday: "昨天",
	before_yesterday:"前天",
	days: $.prettyDate.template("{0}天前"),
	weeks: $.prettyDate.template("{0}个星期前"),
	months : $.prettyDate.template("{0}个月前")
};
	
$.fn.prettyDate = function(options) {
	options = $.extend({
		value: function() {
			return $(this).attr("title");
		},
		interval: 5000
	}, options);
	var elements = this;
	function format() {
		elements.each(function() {
			var date = $.prettyDate.format(options.value.apply(this));
			if ( date && $(this).text() != date )
				$(this).text( date );
		});
	}
	format();
	if (options.interval)
		setInterval(format, options.interval);
	return this;
};
})(jQuery);