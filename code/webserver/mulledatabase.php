<?php include("mulleheader.php"); ?>	

	<section id="main">
	
		<?php
			$host="localhost"; // Host name 
			$username="root"; // Mysql username 
			$password="root"; // Mysql password 
			$db_name="mulle"; // Database name 
			$tbl_name="data"; // Table name
	
			// Connect to server and select database.
			$con = mysql_connect("$host", "$username", "$password")or die("cannot connect"); 
			mysql_select_db("$db_name")or die("cannot select DB");
			$sql="SELECT * FROM $tbl_name";
			$result=mysql_query($sql,$con);
		?>
		
		<table border=3 width="650" style="margin-left:15px;background-color:#D8BFD8;border-color:#292929;">
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

<?php include("mullefooter.php"); ?>