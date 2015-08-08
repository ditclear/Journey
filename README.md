# Journey
**Journey - have a safe flight(功能包含百度地图定位，指南针，本地天气，记录景象和画廊效果)**

###一个记录旅行景色的app,功能还在改进中，还会加入其它的效果，第三方控件来进行效果的优化。

1. 使用了百度地图sdk进行位置定位
2. 使用了百度天气的api来根据本地location进行天气查询
3. 有拍照和保存本地
4. 从本地文件夹中取出并使用瀑布流的效果显示（感谢郭神，使用了lrucache来处理OOM）
5. 有照片查看功能，单击单个照片可以进行查看，使用了viewpager进行滑动
6. 图片可进行手势缩放

> TODO：
> 
> 1.准备加入滤镜功能,使用[android-gpuimage](https://github.com/CyberAgent/android-gpuimage "GpuImage")对图像进行处理
> 
> 2.将百度地图换为高德地图

欢迎各位进行参考，有用的话不妨star一下
