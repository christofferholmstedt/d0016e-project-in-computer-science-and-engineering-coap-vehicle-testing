<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>D0016E Project</title>
<link href="./styles.css" rel="stylesheet" type="text/css"/>
</head>
	
<body>
<section id="all">

	<section id="head"><BR>
		<h1>D0016E Project: Mulle Android Server</h1>
	</section>
	
	<section id="nav">
		<h3 style="margin-left:10px;">Links</h3>
		<EM><li style="margin-left:5px;"><a href="http://mulle.csproject.org">
		PHP</a></li></EM><BR>
		<EM><li style="margin-left:5px;"><a href="http://eistec.se/mulle.php">
		Eistec</a></li></EM><BR>
		<EM><li style="margin-left:5px;"><a href="http://eistec.se/docs/wiki/index.php?title=Main_Page">
		Eistec Mulle support forum</a></li></EM>
	</section>
		
	<section id="main">
	
					<?php
						$host="localhost"; // Host name 
						$username="mulle"; // Mysql username 
						$password="mulle"; // Mysql password 
						$db_name="mulle"; // Database name 
						$tbl_name="data"; // Table name
				
						// Connect to server and select database.
						$con = mysql_connect("$host", "$username", "$password")or die("cannot connect"); 
						mysql_select_db("$db_name")or die("cannot select DB");
						$sql="SELECT * FROM $tbl_name";
						$result=mysql_query($sql,$con);
					?>
					
					<table border=3 width="650" style="margin-left:10px;background-color:#D8BFD8;border-color:#292929;">
					<BR><caption><h3>Retrieved data from database</h3></caption>
					<tr>
					<th><EM>id</EM></th>
					<th><EM>type</EM></th>
					<th><EM>value</EM></th>
					<th><EM>datetime</EM></th>
					<th><EM>host</EM></th>
					</tr>
					
					<?php
						while($rows=mysql_fetch_array($result)){
					?>
					
					<tr>
					<td><? echo $rows['id']; ?></td>
					<td><? echo $rows['type']; ?></td>
					<td><? echo $rows['value']; ?></td>
					<td><? echo $rows['datetime']; ?></td>
					<td><? echo $rows['host']; ?></td>
					
					<?php
						}
					?>
					
					</table>
					</td>
					</tr>
					</table>
					
					<?php
						mysql_close($con);
					?>
			</section>

	<section id="foot">
		<!--<img src="../images/mulle.gif" height="70" width="70" align= "right" style="margin-top:10px;margin-right:10px;"/>
		<img src="../images/mulle_v3.1-212x180.gif" height="70" width="70" align= "left" style="margin-top:10px;margin-left:20px;" />-->
		<BR><BR><a href="mailto:tcdaa-9@googlegroups.com" style="margin-left:255px;"><EM>Send email to the project group</EM></a>
	</section>
				
</section>		
</body>
</html>
