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

2021.8.27更新
     下面列举了所有当前称得上完善的接口，可放（）心（）使用，相对不完善的接口我会在8.28-8.29完成并更新，上文内容请直接忽视。所有POST请求请用x-www-form-urlencoded并把参数放在body内。GET请求随意。获取的json请求里面内容比较清晰且简单，所以就不具体列举了，输出出来看一下就行。
     
     用户部分
     注册：无需token，java.mrpiggyz.ltd:8080/user/register，方法为POST，所需参数为String name，String password和String email。返回json。
     登录：无需token，java.mrpiggyz.ltd:8080/user/login，方法为POST，所需参数为String name和String password。返回json，内含token，请记录下此token，并在访问需要token的接口时将token放入header中，因为token代表着发送请求的前端账号。token会在60小时后失效，若在此期修改密码，请重新登录（后续会优化）。只要进行了登录，token就会发生变化，请记录下最新token。
     删除：需要token，java.mrpiggyz.ltd:8080/user/deleteuser，方法为DELETE，所需参数为token对应账号的密码String password。返回json。
     
     实体搜索
     搜索：需要token，java.mrpiggyz.ltd:8080/entitysearch，方法为GET，所需参数为String course和String searchKey。返回json。（然而目前openedu这个接口挂了）
     调用搜索记录：需要token，java.mrpiggyz.ltd:8080/entitysearch/history，方法为GET，无需参数。返回json，内含最近十条实体搜索记录。
     
     习题搜索
     搜索：需要token，java.mrpiggyz.ltd:8080/exercisessearch，方法为GET，所需参数为String uriName。返回json。
     调用搜索记录：需要token，java.mrpiggyz.ltd:8080/exercisessearch/history，方法为GET，无需参数。返回json，内含最近十条实体搜索记录。
