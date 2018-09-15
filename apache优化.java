public class apache{
	//hahad
	public void windows下定时重启apache和MySQL(){
		采用at命令添加计划任务
		1、在c:盘根目录新建一个apacheautostart.bat文件，然后右键编辑，将文件内容设为如下后保存：
		//apacheautostart.bat文件内容：
		@ECHO OFF  
		net stop apache2  
		net start apache2  
		net stop mysql  
		net start mysql   
		上面的apache2与mysql为服务名称。
		
		开始”－》以管理员的身份运行“cmd”－》执行命令“at 04:00 c:\apacheautostart.bat”
		这样每天早晨4点系统就会自动重启apache与mysql。
		（附加说明：04:00为重启时间，c:\autostartserver.bat为第一步的bat文件地址）
		mysql的重新启动，也通过上面at命令方法重新启动了
	}
	public void windows下apache优化(){
		通过cmd进入apache的安装目录bin目录下执行命令。
		//查看当前安装模块mpm(多路处理器)：httpd -l  
		Compiled in modules:
			  core.c
			  mod_win32.c
			  mpm_winnt.c
			  http_core.c
			  mod_so.c
		/***
		 * 在复杂的网络环境中，浏览器是百花齐放，各式各样。
		 * 目前还有微信和QQ等内嵌浏览器，使用中我们的Apache会遇到不同的问题，
		 * 今天就遇到了一个微信浏览器访问站点导致网站变慢以至apache挂起的情况，
		 * 试验中我们也发现IE10浏览器也经常会导致同样的问题。	
		 */
		//1、打开Apache的\conf\httpd.conf 查找
		#AcceptFilter http none
		#AcceptFilter https none
		修改为
		AcceptFilter http none
		AcceptFilter https none
		默认添加了这个配置只是注释掉了，我们去掉#号，开启配置。
		此处可以解决某些浏览器导致apache慢或者假死不响应的情况，提高兼容性。
		
		//2、Apache2.4下\conf\original\extra 找到httpd-mpm.conf 查找
		修改为：
		<IfModule mpm_winnt_module>
		    ThreadsPerChild      500
		    MaxRequestsPerChild  10000
		    Win32DisableAcceptEx
		</IfModule>
		进程自apache启动，可以同时起多少线程(ThreadsPerChild)。
		MaxRequestsPerChild的含义是单个子进程累计最多处理到少个请求，默认0，不限制的意思，可能会导致内存泄露，
		超过该值则退出重启apache。
		ThreadsPerChild 数目一般100-500
		acceptEx()是一个微软的WinSock2API， 通过使用accept() API提供了性能改善。
		一些防病毒软件或虚拟专用网络软件会干扰AcceptEX()的正确操作。
		可以关闭AcceptEx()：Win32DisableAcceptEx。
		1.Apache在编译时内部有一个硬性的限制"ThreadLimit 20000"(对于mpm_winnt是"ThreadLimit 15000")，你不能超越这个限制。
		2.将MaxRequestsPerChild设置成非零值有两个好处：
		a)可以防止(偶然的)内存泄漏无限进行，从而耗尽内存。
		b)给进程一个有限寿命，从而有助于当服务器负载减轻的时候减少活动进程的数量。
		
		//二、在httpd.conf中，去掉注释 Include conf/extra/httpd-default.conf

		编辑 httpd-default.conf
		Timeout 20                  #默认是300，缩小该参数就会减少同时连接数
		KeepAlive On                #默认是On，该参数为是否保持活连接，目前网站中一个页面一般会包含多个文件，所以相应用户访问时会有多个请求，因此开启可以提高服务器性能。
		MaxKeepAliveRequests 100    #默认是100，最大的活连接请求数，可以根据网页实际包含的文件数目自行调节。
		KeepAliveTimeout 5          #默认是5，活动连接的超时时间，一般只要设置成小于Timeout即可。
		
		//3、在httpd.conf 查找
		备注：如果加了AcceptFilter https none
		在不能使用ssl的情况下，应该注释为#AcceptFilter https none
		
		//4、优化mysql
		修改mysql的my.ini文件再重启服务
		log-slow-queries = slow.log  //windwos下直接写这句，不带路径时候自动生成到 mysql的data目录下
		long_query_time = 5  //超过5秒的查询都记录日志（这其实是优化php的，这个开启mysql日制的方法值得备忘一下）
		修改MySQL的相关配置，找到my.ini文件。
		#mysql数据库的访问端口,如果要修改端口,就在这里修改
		port=3306
		
		#不带路径时候自动生成到 mysql的data目录下
		log-slow-queries = slow.log
		#超过5秒的查询都记录日志
		long_query_time = 5
		#不进行域名反解析,注意由此带来的权限/授权问题
		skip-name-resolve
		#索引缓存,根据内存大小而定,如果是独立的db服务器,可以设置高达80%的内存总量
		key_buffer = 512M
		#连接排队列表总数
		back_log = 200
		#每个线程排序所需的缓冲
		 sort_buffer_size = 4M
		#查询结果缓存
		 query_cache_size = 128M
	}
	
	public void 从命令行cmd启动apache能知道错误在哪一行(){
		apache的安装路径：D:\Apache24\
		cmd进去到黑窗口，apache_http_server这个是你的apache的名称
		D:\Apache24\bin>httpd.exe -w -n "apache_http_server" -k start
	}
	public void 监测apache运行状态(){
		//开启Apache的server status监测
		从httpd.conf 打开 status_module
		#LoadModule status_module modules/mod_status.so
		修改成
		LoadModule status_module modules/mod_status.so
		
		而apache2.2以上版本中的Order、Allow等命令在新版本中也可以得到兼容，
		实现这个兼容功能的模块就是 mod_access_compat。
		所以Load这个模块后，apache2.4就能识别这些语句了。
		从httpd.conf 打开mod_access_compat
		LoadModule access_compat_module modules/mod_access_compat.so
		默认是注释掉的，修改成打开状态。
		
		ExtendedStatus On      ##获得一个完整的报告与当前状态信息
		<IfModule status_module> 
        	<Location /server-status> ##访问地址
                SetHandler server-status
                Order Deny,Allow
                Deny from all
                Allow from all ##允许访问apache运行状态的IP, all代表不限制ip
            </Location>
        </IfModule>
        
                       如果看不了尝试修改Directory：修改如下：
	     <Directory >
	    	 Options FollowSymLinks
	    	 AllowOverride None
	    	 Order deny,allow
	         allow from all
	         Satisfy all
	     </Directory>
        
		使用http://127.0.0.1/server-status来访问，如果需要自动更新，可以用http://127.0.0.1/server-status?refresh=N，N是更新时间，默认是秒
		http://127.0.0.1/server-status?refresh=5  ##server-status可以自定义地址，5秒自动刷新
		成功案例：http://127.0.0.1/server-status
			
		server-status 的输出中每个字段所代表的意义如下：
		字段                       说明
		Server Version       Apache 服务器的版本。
		Server Built            Apache 服务器编译安装的时间。
		Current Time          目前的系统时间。
		Restart Time           Apache 重新启动的时间。
		Parent Server Generation       Apache 父程序 (parent process) 的世代编号，就是 httpd 接收到 SIGHUP 而重新启动的次数。
		Server uptime         Apache 启动后到现在经过的时间。
		Total accesses        到目前为此 Apache 接收的联机数量及传输的数据量。
		CPU Usage            目前 CPU 的使用情形。
		_SWSS....       所有 Apache process 目前的状态。每一个字符表示一个程序，最多可以显示 256 个程序的状态。
		Scoreboard Key       上述状态的说明。以下为每一个字符符号所表示的意义：
		 
		* _：等待连结中。
		* S：启动中。
		* R： 正在读取要求。
		* W：正在送出回应。
		* K：处于保持联机的状态。
		* D：正在查找 DNS。
		* C：正在关闭连结。
		* L：正在写入记录文件。
		* G：进入正常结束程序中。
		* I：处理闲置。
		* .：尚无此程序。
		 
		Srv       本程序与其父程序的世代编号。
		PID       本程序的 process id。
		Acc       分别表示本次联机、本程序所处理的存取次数。
		M       该程序目前的状态。
		CPU       该程序所耗用的 CPU 资源。
		SS       距离上次处理要求的时间。
		Req       最后一次处理要求所耗费的时间，以千分之一秒为单位。
		Conn       本次联机所传送的数据量。
		Child       由该子程序所传送的数据量。
		Slot       由该 Slot 所传送的数据量。
		Client       客户端的地址。
		VHost       属于哪一个虚拟主机或本主机的 IP。
		Request       联机所提出的要求信息。
		 
		查看Apache的请求数和开启Apache Server Status 
		在Linux下查看Apache的负载情况，最简单有有效的方式就是查看Apache Server Status，在没有开启Apache Server Status的情况下，或安装的是其他的Web Server，比如Nginx的时候，下面的命令就体现出作用了。
		ps -ef|grep httpd|wc -l命令
		#ps -ef|grep httpd|wc -l
		1388
		统计httpd进程数，连个请求会启动一个进程，使用于Apache服务器。
		表示Apache能够处理1388个并发请求，这个值Apache可根据负载情况自动调整，我这组服务器中每台的峰值曾达到过2002。
		 
		netstat -nat|grep -i “80″|wc -l命令
		#netstat -nat|grep -i “80″|wc -l
		4341
		netstat -an会打印系统当前网络链接状态，而grep -i “80″是用来提取与80端口有关的连接的, wc -l进行连接数统计。
		最终返回的数字就是当前所有80端口的请求总数。
		 
		netstat -na|grep ESTABLISHED|wc -l命令
		#netstat -na|grep ESTABLISHED|wc -l         ---------个人测试此命令比较准确
		376
		netstat -an会打印系统当前网络链接状态，而grep ESTABLISHED 提取出已建立连接的信息。 然后wc -l统计。
		最终返回的数字就是当前所有80端口的已建立连接的总数。
		 
		netstat -nat||grep ESTABLISHED|wc -   可查看所有建立连接的详细记录
		 
		查看Apache的并发请求数及其TCP连接状态：
		Linux命令：
		netstat -n | awk '/^tcp/ {++S[$NF]} END {for(a in S) print a, S[a]}'
		（这条语句非常不错）
		返回结果示例：
		LAST_ACK 5
		SYN_RECV 30
		ESTABLISHED 1597
		FIN_WAIT1 51
		FIN_WAIT2 504
		TIME_WAIT 1057
		其中的SYN_RECV表示正在等待处理的请求数；ESTABLISHED表示正常数据传输状态；TIME_WAIT表示处理
		完毕，等待超时结束的请求数。
	}
	
	
	
	
	
	
	
}