流畅度优化
认识
    流畅度在此指的是可滑动列表在滑动时的流畅度，流畅度优化就是是列表滑动更加流畅，以期望带来像留存率、停留时长等业务指标的收益。
    流畅度优化是有确定的衡量指标--fps（每秒帧数），fps越大滑动越流畅
相关技术点
    view的工作原理，measure layout draw 自定义view等
    屏幕刷新机制 VSync Choreographer fps计算
    渲染流程 UIThread和RenderThread，CPU和GPU分别经过那些步骤
    滑动列表RV的原理，四级缓存、onCreateViewHolder和onBindViewHolder的调用时机等
优化工具
    Systrace 性能数据采样和分析工具，通过生成Systrace文件帮助分析问题
        https://www.androidperformance.com/2019/05/28/Android-Systrace-About
    GPU呈现模型分析 以滚动直方图的形式直观的显示渲染界面窗口帧所花费的时间
        https://developer.android.com/studio/profile/dev-options-rendering?hl=zh-cn
优化方案
    解决所有帧的公共问题---重度绘制