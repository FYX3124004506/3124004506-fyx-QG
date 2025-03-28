

# QG第二周学习笔记

## HTML

HTML是一门语言，所有的网页都是用HTML这门语言编写出来的 HTML(HyperText Markup Language):超文本标记语言
超文本:超越了文本的限制，比普通文本更强大。除了文字信息，还可以定义图片、音频、视频等内容 标记语言:由标签构成的语言
HTML 运行在浏览器上，HTML标签由浏览器来解析
HTML 标签都是预定义好的。例如:使用<img>展示图片·

W3C标准:网页主要由三部分组成(前端三剑客)
 结构:HTML 

表现: CSS

行为:JavaScript

快速入门

<html>
    <head>
        <title>html入门</title>
    </head>
​    <body>
​        <font color="red">原神</font>
​    </body>
</html>

标签：(https://www.runoob.com/tags/ref-byfunc.html)

## CSS

### css导入方式

CSS导入HTML有三种方式:

1. 内联样式:在标签内部使用style属性，属性值是css属性键值对

<div style="color: red">Hello CSS~</div>

2. 内部样式: 定义<style>标签，在标签内部定义css样式

<style type="text/css"> div{
color: red;
</style>

3. 外部样式: 定义link标签，引入外部的css文件
   <link rel="stylesheet" href="demo.css">

  demo.css:	
  	div{	
  color: red;
  }

### css选择器

CSS选择器
概念:选择器是选取需设置样式的元素(标签)
div{
color: red;

}

分类:
1.元素选择器
元素名称{color: red;}	div{color: red;}	

2.id选择器	#name{color: red;}	
#id属性值{color.red;}	<div id="name">hello css2</div>	

3.类选择器

.class属性值{color: red;}

.cls{color: red;}

<div class="cls">hello css3</div>

### CSS属性

[CSS 参考手册](https://www.w3school.com.cn/cssref/index.asp)

## JavaScript

### 引入方式

1、内部脚本（将JS代码定义在HTML页面中）

```
<script>
    alert('hello js');
</script>
```

2、外部脚本（将JS代码定义在外部JS文件中，然后引入到HTML页面中）

外部文件（可以任意放置，任意数量，一般放body后面）

```
alert('hello js');
```

引入外部js文件

```
<script src="../js/demo.js"></script>
```

注意：

1、外部脚本不能包含<script>标签

2、<script>标签不能自闭合

### 基础语法

#### 书写语法

每行代码分号可有可无，一般写

大括号表示代码块

#### 输出语句

windows.alert("hello JS ~");//弹出警告框

document.write("hello JS ");//写入html输出

console.log("hello JS");//写入浏览器控制台

#### 变量

var test =20;

test = “张三”;

弱类型：变量可存放不同类型的值

1、作用域：全局变量

2、变量可以重复定义，被覆盖

let关键字定义变量不能重复声明，var可以

const 定义常量

#### 数据类型

##### 原始类型

number:数字,任何数字

string:字符，字符串，单引号，双引号均可

boolean:布尔类型

null:空类型

undefined：声明的变量未初始化时，变量默认值是undefined

##### 基本类型

#### 运算符

==会自动类型转换

===全等于

string转为number：按照字面量，如果不是数字，转为NaN,一般使用parseInt转换为数字

var str=+"abc";

boolean:1/0

#### 流程控制语句

#### 函数

通过function关键字定义

function Functionname(参数（不需要写数据类型）){代码 return a+b;}

或者

var Functionname=function 参数（不需要写数据类型）){代码 return a+b;}

### 常用对象

#### Array

var 变量名=new Array(元素列表);//方式1

var 变量名=[元素列表]//方式2

访问：

arr[索引]=值

arr[0]=1；

length 长度

push 添加元素

push(5)

splice 删除元素

splice(0,1)

#### String

var 变量名=new String(s);//方式1

var 变量名=s;//方式二

方法：[JavaScript String 参考手册](https://www.w3school.com.cn/jsref/jsref_obj_string.asp)

charAt();

IndexOf();

trim();去除字符前后两端的空白字符

自定义对象：

var person = {
  firstName:"John",
  lastName:"Doe",
  age:50,
  eyeColor:"blue"

函数名称:function（形参列表）{}

};

#### RegExp(正则表达式)

2. 正则表达式
   .概念:正则表达式定义了字符串组成的规则定义:
     1.直接量:注意不要加引号
     var reg = /^\w{6,12}$/;
  2. 创建 RegExp对象
     var reg = new RegExp("^\\w{6,12}$");

  方法:
  · test(str): 判断指定字符串是否符合规则，返回true或false

语法
^表示开始

 $:表示结束
[]:代表某个范围内的单个字符，比如:[0-9] 单个数字字符

.:代表任意单个字符，除了换行和行结束符
\w: 代表单词字符:字母、数字、下划线_，相当于[A-Za-20-9_」

\d:代表数字字符:相当于[0-9]

量词:
+:至少一个	var reg = /^\w+S/;	
*:0个或多个

?:零个或一个

(x):x个
{m.):至少m个
{m,n):至少m个，最多n个

### BOM

BOM

Browser Object Model浏览器对象模型

JavaScript将浏览器的各个组成部分封装为对象组成:

#### Window: 浏览器窗口对象

window.alert("abc");

获取其他的BOM对象

方法：

alert()；警告框

confirm()；显示有确认取消按钮的对话框

setInterval();指定周期调用函数或者计算表达式

setTimeout();指定毫秒后调用函数或者计算表达式

#### Navigator:浏览器对象

#### Screen:屏幕对象

#### History:历史记录对象

history.方法()

back();

forward();

#### Location: 地址栏对象

location.方法();

href设置或返回完整的URL

例如：location.href="https//..."

document.write("文字")；

### DOM

#### js通过DOM，可以对HTML进行操作了

改变HTML元素的内容

改变HTML元素的样式(CSS)

对HTML DOM 事件作出反应添加和删除HTML元素



#### Document Object Model文档对象模型

将标记语言的各个组成部分封装为对象
Document:整个文档对象 

Element:元素对象 

使用Document对象的方法获取

1. getElementByld: 根据id属性值获取，返回一个Element对象
2. getElementsByTagName:根据标签名称获取，返回Element对象数组
3. getElementsByName:根据name属性值获取，返回Element对象数组
4. getElemehtsByClassName:根据class属性值获取，返回Element对象数组

常见html element对象的使用:



Attribute:属性对象

Text:文本对象

Comment: 注释对象



### 事件监听

html元素上的事情:

1、按钮被点击

2、鼠标移动到元素上

3、按下键盘按键

监听：js在事件被检测到时执行代码

事件绑定
事件绑定有两种方式
.方式一:通过 HTML标签中的事件属性进行绑定
<input type="button" onclick='on()'>
function on(){
alert("我被点了");}

.方式二:通过 DOM 元素属性绑定
<input type="button" id="btn">
document.getElementByld("btn"),onclick =function (){ alert("我被点了")}

常见事件：

[HTML DOM 事件](https://www.w3school.com.cn/jsref/dom_obj_event.asp)