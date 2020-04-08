/****** JiSlider version 0.5.3 ******/
// tested on Safari 8, Chrome 44, Firefox 44

(function ($) {
	$.fn.JiSlider = function (options) {
		// prohibit stacking
		var JiSlider = this;
		var then = new Date().getTime();
		// var UP = 'up', DOWN = 'down';

		// animation
		var Animate = function (slides, start, auto, time, stay, easing, reverse) {
			this.slider = JiSlider;
			this.slides = slides;
			this.width = this.slider.width();
			this.ul = this.slider.find('ul');
			this.index = start;
			this.auto = auto;
			this.time = time;
			this.stay = stay;
			this.easing = easing;
			this.reverse = reverse ? -1 : 1;
			this.play = setInterval(this.autoroll.bind(this), this.stay);
		}

		Animate.prototype = {
			init: function (options) {
				$.extend(this, options);
			},
			roll: function (time) {
				var left = -(this.index * this.width);

				ul.css({
					'-webkit-transform': 'translateX(' + left + 'px)',
					'-webkit-transition': '-webkit-transform ' + time / 1000 + 's ' + this.easing,
					'-ms-transform': 'translateX(' + left + 'px)',
					'-ms-transition': '-ms-transform ' + time / 1000 + 's ' + this.easing,
					'transform': 'translateX(' + left + 'px)',
					'transition': 'transform ' + time / 1000 + 's ' + this.easing,
				});

				this.check();

				if (this.controller) {
					this.controller.find('.jislider__button[data-index=' + this.index + ']').addClass('jislider__on').css('background-color', setting.color);
					this.controller.find('.jislider__button[data-index!=' + this.index + ']').removeClass('jislider__on').css('background-color', 'transparent');
				}
			},
			autoroll: function () {
				if (this.auto)
					this.index += this.reverse;
				this.roll(this.time);
			},
			check: function () {
				var t = this;
				if (this.index > this.slides) {
					this.index = 1;
					setTimeout(function () {
						t.roll(0);
					}, this.time);
				} else if (this.index < 1) {
					this.index = slides;
					setTimeout(function () {
						t.roll(0);
					}, this.time);
				}
			},
			control: function (index) {
				if (this.timeCheck()) {
					this.index = index;
					this.reset();
					this.roll(this.time);
				}
			},
			timeCheck: function () {
				var now = new Date().getTime();
				if (now - then > this.time) {
					then = now;
					return true;
				} else {
					return false;
				}
			},
			reset: function () {
				clearInterval(this.play);
				this.play = setInterval(this.autoroll.bind(this), this.stay);
			},
			resize: function (width) {
				this.width = width;
				this.reset();
				this.roll(0);
			}
		}

		// jquery element variable
		var ul = this.find('ul');
		var li = this.find('ul li');

		// setting
		var setting = $.extend({
			auto: true,
			start: 1,
			time: 600,
			stay: 3000,
			controller: true,
			easing: 'ease',
			color: '#fff',
			reverse: false,
		}, options);
		var jw = this.width();
		var slides = this.find('ul li').length;

		if (setting.start > slides) {
			throw "Start value is bigger than number of slides";
		}

		// slider setup
		var first = li.first().clone();
		var last = li.last().clone();
		ul.prepend(last);
		ul.append(first);

		this.css({
			position: 'relative',
			overflow: 'hidden'
		});

		ul.css({
			width: (100 * (slides + 2)) + '%',
			'-webkit-transform': 'translateX(' + -(setting.start * jw) + 'px)',
			'-ms-transform': 'translateX(' + -(setting.start * jw) + 'px)',
			'transform': 'translateX(' + -(setting.start * jw) + 'px)',
		});

		// selecting li tags with two clones
		li = this.find('ul li');
		var img = this.find('ul li img');

		li.css({
			width: (100 / (slides + 2)) + '%',
		});

		img.each(function () {
			var div = $('<div>', {'class': 'jislider__img'}).css({
				backgroundImage: 'url(' + $(this).attr('src') + ')',
			});
			$(this).after(div);
			$(this).remove();
		});

		// animation
		var animate = new Animate(slides, setting.start, setting.auto, setting.time, setting.stay, setting.easing, setting.reverse);

		// controller
		if (setting.controller) {
			var leftArrow = $('<div>', {'class': 'jislider__left-arrow'}).click(function () {
				animate.control(--animate.index);
			});
			var leftArrowTop = $('<div>', {'class': 'jislider__left-arrow__top'}).css({
				backgroundColor: setting.color,
			});
			var leftArrowBottom = $('<div>', {'class': 'jislider__left-arrow__bottom'}).css({
				backgroundColor: setting.color,
			});

			var rightArrow = $('<div>', {'class': 'jislider__right-arrow'}).click(function () {
				animate.control(++animate.index);
			});
			var rightArrowTop = $('<div>', {'class': 'jislider__right-arrow__top'}).css({
				backgroundColor: setting.color,
			});
			var rightArrowBottom = $('<div>', {'class': 'jislider__right-arrow__bottom'}).css({
				backgroundColor: setting.color,
			});

			var controller = $('<div>', {'class': 'jislider__controller'}).css({
				width: 20 * slides,
			});
			
			var buttons = new Array();
			for (var i = 0; i < slides; i++) {
				buttons[i] = $('<div>', {'class': 'jislider__button', 'data-index': (i + 1)}).css({
					border: '1px solid ' + setting.color,
				}).click(function () {
					var index = $(this).data('index')
					animate.control(index)
				});
			}
			this.append(leftArrow, rightArrow, controller);
			leftArrow.append(leftArrowTop, leftArrowBottom);
			rightArrow.append(rightArrowTop, rightArrowBottom);
			controller.append(buttons);
			animate.init({controller: controller});
		}

		animate.roll(0);

		$(window).resize(function () {
			jw = JiSlider.width();
			animate.resize(jw);
		});

		return this;
	}
}(jQuery));