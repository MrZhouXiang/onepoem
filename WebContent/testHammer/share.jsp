<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head lang="en">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1">
        <meta name="msapplication-tap-highlight" content="no"/>
        <link rel="stylesheet" href="../testHammer/style.css">
        <link rel="stylesheet" href="../testHammer/index.css">

        <title>图片合成</title>
        <style>
            .container {
                max-width: 900px;
                margin: 0 auto;
            }

            .panes.wrapper {
                /*max-height: 400px;
                 max-width: 800px;*/
                /*width: 100px;*/
                /*background: #FFFFFF;*/
                margin: 0px auto;
                position: absolute;
                z-index: 3;
                top: 0;
                left: 0;
                bottom: 0;
                right: 0;
                overflow: visible;
            }

            .panes {
                width: 100px;
                height: 100px;
                overflow: visible;
            }

            .pane {
                width: 100%;
                height: 100%;
                position: absolute;
                z-index: 1;
                /*left: 0;*/
                /*top: 0;*/
                /*text-align: center;*/
                /*font: bold 60px/250px 'Open Sans', Helvetica, Arial, sans-serif;*/
                /*color: #fff;*/
            }
            .over {
                z-index: 1;
                border: 2px solid #008CBA;
            }
            .panes.animate > .pane {
                transition: all .3s;
                -webkit-transition: all .3s;
            }

        </style>

    </head>
    <body style="width: 100%;height:100%">
        <div class="row splash">
            <div class="try">
                <div class="device">
                    <!-- <div style="width:100%;height:100px;background-color: #FFF;z-index: 3;">
                    </div> -->
                    <div id="bg_list" class="panes wrapper over " >

                        <!-- <div class="pane " style="background:url(img_lead_0.png);background-size:100% 100%;"></div>
                        <div class="pane " style="background:url(img_lead_1.png);background-size:100% 100%;"></div>
                        <div class="pane " style="background:url(img_lead_2.png);background-size:100% 100%;"></div>
                        <div class="pane " style="background:url(img_lead_3.png);background-size:100% 100%;"></div>
                        <div class="pane " style="background:url(img_lead_4.png);background-size:100% 100%;"></div>
                        -->
                    </div>

                    <div class="device-button" ></div>
                    <div class="device-screen-wrapper">
                        <div class="device-screen">
                            <img id="bg_img" src="../testHammer/img_lead_2.png" />
                            <img id="hitarea" src="../testHammer/ic_launcher72.png"></img>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="../testHammer/hammer.js"></script>
        <script>
            var reqAnimationFrame = (function() {

                return window[Hammer.prefixed(window, "requestAnimationFrame")] ||
                function(callback) {
                    setTimeout(callback, 1000 / 60);
                }

            })();
            var imgs = ["../testHammer/img_lead_0.png", "../testHammer/img_lead_1.png", "../testHammer/img_lead_2.png", "../testHammer/img_lead_3.png", "../testHammer/img_lead_4.png"];
            showBg();
            function showBg() {
                var innerHtml = "";
                for (var i = 0; i < imgs.length; i++) {
                    var html = ' <div class="pane " style="background:url(' + imgs[i] + ');background-size:100% 100%;"></div>';
                    innerHtml += html;
                }

                document.getElementById("bg_list").innerHTML = innerHtml;
                // alert(document.getElementById("bg_list").innerHTML)
            }

            function dirProp(direction, hProp, vProp) {

                return (direction & Hammer.DIRECTION_HORIZONTAL) ? hProp : vProp
            }

            /**
             * Carousel
             * @param container
             * @param direction
             * @constructor
             */
            function HammerCarousel(container, direction) {
                // alert("HammerCarousel")
                this.container = container;
                this.direction = direction;

                this.panes = Array.prototype.slice.call(this.container.children, 0);
                this.containerSize = this.container[dirProp(direction, 'offsetWidth', 'offsetHeight')];

                this.currentIndex = 2;

                this.hammer = new Hammer.Manager(this.container);
                this.hammer.add(new Hammer.Pan({
                    direction : this.direction,
                    threshold : 10
                }));
                this.hammer.on("panstart panmove panend pancancel", Hammer.bindFn(this.onPan, this));

                this.show(this.currentIndex);
            }


            HammerCarousel.prototype = {
                /**
                 * show a pane
                 * @param {Number} showIndex
                 * @param {Number} [percent] percentage visible
                 * @param {Boolean} [animate]
                 */
                show : function(showIndex, percent, animate) {

                    showIndex = Math.max(0, Math.min(showIndex, this.panes.length - 1));
                    percent = percent || 0;

                    var className = this.container.className;

                    if (animate) {
                        if (className.indexOf('animate') === -1) {
                            this.container.className += ' animate';
                            // alert(showIndex)
                            document.querySelector("#bg_img").src = imgs[showIndex];

                        }
                    } else {
                        if (className.indexOf('animate') !== -1) {
                            this.container.className = className.replace('animate', '').trim();
                        }
                    }

                    var paneIndex,
                        pos,
                        translate;
                    for ( paneIndex = 0; paneIndex < this.panes.length; paneIndex++) {
                        pos = (this.containerSize / 100) * (((paneIndex - showIndex) * 100) + percent);
                        if (this.direction & Hammer.DIRECTION_HORIZONTAL) {
                            translate = 'translate3d(' + pos + 'px, 0, 0)';
                        } else {
                            translate = 'translate3d(0, ' + pos + 'px, 0)'
                        }
                        this.panes[paneIndex].style.transform = translate;
                        this.panes[paneIndex].style.mozTransform = translate;
                        this.panes[paneIndex].style.webkitTransform = translate;
                    }

                    this.currentIndex = showIndex;

                },

                /**
                 * handle pan
                 * @param {Object} ev
                 */
                onPan : function(ev) {
                    var delta = dirProp(this.direction, ev.deltaX, ev.deltaY);
                    var percent = (100 / this.containerSize) * delta;
                    var animate = false;

                    if (ev.type == 'panend' || ev.type == 'pancancel') {
                        if (Math.abs(percent) > 20 && ev.type == 'panend') {
                            this.currentIndex += (percent < 0) ? 1 : -1;
                        }
                        percent = 0;
                        animate = true;
                    }

                    this.show(this.currentIndex, percent, animate);
                }
            };

            // the horizontal pane scroller
            var outer = new HammerCarousel(document.querySelector(".panes.wrapper"), Hammer.DIRECTION_HORIZONTAL);

            // each pane should contain a vertical pane scroller
            Hammer.each(document.querySelectorAll(".pane .panes"), function(container) {
                // setup the inner scroller
                var inner = new HammerCarousel(container, Hammer.DIRECTION_VERTICAL);
                // only recognize the inner pan when the outer is failing.
                // they both have a threshold of some px
                outer.hammer.get('pan').requireFailure(inner.hammer.get('pan'));
            });

        </script>
        <script src="../testHammer/index.js"></script>

    </body>
</html>
	