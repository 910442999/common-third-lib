## 说明

## 1、版本说明

# 1.0.4

-删除tencent_cloud_chat_uikit-4.0.8 直接使用pub.dev仓库中的tencent_cloud_chat_uikit: ^5.0.0即可，
图片,视频播放页面关闭和下载按钮大的问题 ，官方解释如下：v1 的组件是用的 material2 的样式来做的，新版 V2，是基于 material3的。现在Flutter新项目默认用的是 material3。
应用一旦指定样式，全局生效，不能在组件中单独指定。
修改方式如下：main.dart 入口处，MaterialApp 中theme: ThemeData 指定 useMaterial3: false

-删除flutter_plugin_record_plus-0.0.20 直接使用pub.dev仓库中的flutter_plugin_record_plus-0.0.21即可，官方版本已修复

# 1.0.3

-删除umeng_common_sdk-1.2.8 直接使用pub.dev仓库中的umeng_common_sdk-1.2.9即可，官方版本已修复
-升级tencent_cloud_chat_uikit-4.0.6至tencent_cloud_chat_uikit-4.0.8
-升级flutter_plugin_record_plus-0.0.19至flutter_plugin_record_plus-0.0.20

# 1.0.2

-升级tencent_cloud_chat_uikit-4.0.5至tencent_cloud_chat_uikit-4.0.6

# 1.0.1

-升级tencent_cloud_chat_uikit-4.0.4至tencent_cloud_chat_uikit-4.0.5

# 1.0.0

修改升级flutter sdk 导致以下sdk报错问题

-增加 umeng_common_sdk-1.2.8

-增加 mobsms-1.1.10

-增加 flutter_plugin_record_plus-0.0.19

优化以下代码
-增加 tencent_cloud_chat_uikit-4.0.4，优化视频播放页面关闭和下载按钮

