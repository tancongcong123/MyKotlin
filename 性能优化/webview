[AndroidX webkit]
    dependencies {
        implementation "androidx.webkit:webkit:1.6.0"
    }
    Android官方将定义的webview api放在AndroidX webkit库中，以维持webview的频繁更新，并在webview上有添加中间层和AndroidX webkit进行衔接，这样只要安装了拥有中间层的webview就能使用新版本中的webview特性。
        WebviewFeature.isFeatureSupported(WebviewFeature.START+SAFE_BROWSING){
            webviewCompat.startSafeBrowsing(appContext, callback);
        }
    代理功能支持
        if (WebViewFeature.isFeatureSupported(WebViewFeature.PROXY_OVERRIDE)) {
            ProxyConfig proxyConfig = new ProxyConfig.Builder()
                .addProxyRule("localhost:7890") //添加要用于所有 URL 的代理
                .addProxyRule("localhost:1080") //优先级低于第一个代理，仅在上一个失败时应用
                .addDirect()                    //当前面的代理失败时，不使用代理直连
                .addBypassRule("www.baidu.com") //该网址不使用代理，直连服务
                .addBypassRule("*.cn")          //以.cn结尾的网址不使用代理
                .build();
            Executor executor = ...
            Runnable listener = ...
            ProxyController.getInstance().setProxyOverride(proxyConfig, executor, listener);
        ｝
    白名单功能
        使用setReverseBypassEnabled(true) 方法将addBypassRule 添加的 URL 转变为使用代理服务器，而其他的 URL 则直连服务
    安全的webview和native通信支持
        AndroidX提供了功能强大的接口addWebMessageListener兼顾了安全和性能的问题
        示例中将JavaScript对象replyObject注入到匹配allowedOriginRules的上下文中，这样只有在可信的网站中才能使用此对象，也就防止了不明来源的网络攻击和对该对象的利用
        native通过JavaScriptReplyProxy.onPostMessage发送消息给webview，在web端接收消息并处理
        // App (in Java)
        WebMessageListener myListener = new WebMessageListener() {
            @Override
            public void onPostMessage(WebView view, WebMessageCompat message, Uri sourceOrigin,
            boolean isMainFrame, JavaScriptReplyProxy replyProxy) {
                // do something about view, message, sourceOrigin and isMainFrame.
                replyProxy.postMessage("Got it!");
            }
        };
        HashSet<String> allowedOriginRules = new HashSet<>(Arrays.asList("[https://example.com](https://example.com/ "https://example.com")"));
        // Add WebMessageListeners.
        WebViewCompat.addWebMessageListener(webView, "replyObject", allowedOriginRules,myListener);
    文件传递
        AndroidX weblit提供了字节流传递机制
            ***********************native传递文件给webview***********************
            ----------------------- App (in Java)
            WebMessageListener myListener = new WebMessageListener() {
                @Override
                public void onPostMessage(WebView view, WebMessageCompat message, Uri sourceOrigin,
                boolean isMainFrame, JavaScriptReplyProxy replyProxy) {
                    // Communication is setup, send file data to web.
                    if (WebViewFeature.isFeatureSupported(WebViewFeature.WEB_MESSAGE_ARRAY_BUFFER)) {
                        // Suppose readFileData method is to read content from file.
                        byte[] fileData = readFileData("myFile.dat");
                        replyProxy.postMessage(fileData);
                    }
                }
            }
            ----------------------- Web page (in JavaScript)
            myObject.onmessage = function(event) {
                if (event.data instanceof ArrayBuffer) {
                    const data = event.data;  // Received file content from app.
                    const dataView = new DataView(data);
                    // Consume file content by using JavaScript DataView to access ArrayBuffer.
                }
            }
            myObject.postMessage("Setup!");
            ***********************webview传递文件给native***********************
            ----------------------- Web page (in JavaScript)
            const response = await fetch('example.jpg');
            if (response.ok) {
                const imageData = await response.arrayBuffer();
                myObject.postMessage(imageData);
            }
            ----------------------- App (in Java)
            WebMessageListener myListener = new WebMessageListener() {
                @Override
                public void onPostMessage(WebView view, WebMessageCompat message, Uri sourceOrigin,
                boolean isMainFrame, JavaScriptReplyProxy replyProxy) {
                    if (message.getType() == WebMessageCompat.TYPE_ARRAY_BUFFER) {
                        byte[] imageData = message.getArrayBuffer();
                        // do something like draw image on ImageView.
                    }
                }
            };
[web加载过程]
    初始化Native App组件---初始化webview
        优化：预加载webview [WebviewCacheHolder]
    初始化Hybrid---根据调起协议传入的参数，校验解压下发到本地的Hybrid模板，webview.loadurl之后，触发对Hybrid模板头部和body的解析
    加载正文数据和渲染页面---家在解析页面js文件，发起数据请求，解析数据，构造dom结构，触发渲染流程
    加载图片---执行图片请求，写入缓存，进行渲染
    --------------------
    优化
        1 预置离线包 
            1.1 精简并抽取公共的js和css文件作为通用模板，每套模板有版本号，App定时静默更新
            1.2 可以将js和css还有一些图片内联到一个文件中，减少io操作
        2 并行请求
            h5加载模板文件时，有native请求数据，在通过js将数据传给h5
        3 预加载
            3.1 在预加载webview的时候就让其预热加载模板
            3.2 对于feed流，通过一些策略去预加载正文数据，理想情况可以直接使用缓存数据
        4 延迟加载
            对于一些非必要的网络请求、js调用、埋点上报等，都可以延后执行
        5 静态页面直出
            5.1 有后端对正文数据和前端代码进行整合，直出首屏内容，这样直出的HTML文件就不需要解析JSON、构造dom，应用css，直接进行渲染
            5.2 对于字体大小、夜间模式等需要预留一些占位符用于后续动态回填
        6 复用webview
            在webview使用完之后清空数据，等下次使用直接注入数据进行复用
        7 视觉优化
            activity切换过程中的白屏，mDrawDuringWindowsAnimating控制window在执行动画时候是否允许进行绘制，默认是false，可以通过反射修改这个属性