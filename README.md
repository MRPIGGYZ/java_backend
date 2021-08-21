 详情请见各个push以及merge操作的说明
 
 2021.8.21
 目前已经实现：
 
     用户注册、登录、删除、通过用户名查找、通过email查找等几个基本逻辑。密码加密部分已完成。
     注册：127.0.0.1:8010/user/register，方法为POST，所需参数为String name，String password和String email，返回String（调试用）。
     登录：127.0.0.1:8010/user/login，方法为POST，所需参数为String name和String password，返回String（调试用）
     删除：127.0.0.1:8010/user/deleteuser，方法为DELETE，应当在登录后调用，所需参数为String name，返回String（调试用）
     几个查找逻辑就不列出了。

     智能问答机器人接口。然而openedu接口是挂的，还没测试，前端可以先写页面。
     127.0.0.1:8010/clientquestion，方法为GET，所需参数为String inputQuestion和String course（非必须），返回内容未知。

     实体搜索API
     127.0.0.1:8010/search/entity，方法为GET，所需参数为String course和String searchKey，返回ArrayList。

     习题搜索API
     127.0.0.1:8010/search/exercises，方法为GET，所需参数为String uriName，返回ArrayList。
     
2021.8.21更新
 
     实体详情API
     127.0.0.1:8010/entity/course，方法为GET，所需参数为String name和String course，返回HashMap。
     127.0.0.1:8010/entity/ncourse，因未知原因暂无法使用，方法为GET，所需参数为String name，返回HashMap。

