# File Manager

这个是一个用java简单实现的gui文件管理器。

## 运行

FileManager.jar程序可直接运行作业文件管理器软件。

## 项目目的

运用面向对象程序设计思想，基于Java文件管理和I/O框架，实现基于图形界面的GUI文件管理器。

## 项目内容

1.  实现文件夹创建、删除、进入。
2.  实现当前文件夹下的内容罗列。
3.  实现文件拷贝和文件夹拷贝（文件夹拷贝指深度拷贝，包括所有子目录和文件）。
4.  实现指定文件的加密和解密。
5.  实现指定文件和文件夹的压缩。
6.  实现压缩文件的解压。
7.  文件管理器具有图形界面。

## 项目结构

MVC结构

##### 模型Model

f ileManage类，实现对文件的访问、新建、删除、复制、加解密、解压缩等操作处理，将所有对底层文件的操作权限限制在Model一层。

##### 视图View

GUI类，创建可视化面板，在JFrame的基础上创建JPanel来容纳可视化部件。

##### 控制 Controller

Controller类，实现对可视化界面鼠标事件的处理，链接GUI和fileManager。

 

## 程序实现流程设计

基于MVC模式，可视化面板提供可视化操作框和按钮等，controller接受到GUI中的鼠标点击事件等，发出操作文件的命令，在Model一层对文件进行新建、删除等操作。
