<?php include("mulleheader.php"); ?>	

		<section id="main">
		
		<?php 
		if ($_GET["change"] == On){
			passthru('/usr/bin/python /Users/Sophia/www/testsender.py 1 2>&1'); 
			echo 'status: ON';
		}
		else if ($_GET["change"] == Off){
			passthru('/usr/bin/python /Users/Sophia/www/testsender.py 1 2>&1');
			echo 'status: OFF'; 
		}
		?>
		
		<BR>
		<BR>
		<form method="get"
		      enctype="application/x-www-form-urlencoded"
		      action="./mullebuttons.php">
		 <fieldset>
		  <legend> Lamp </legend>
		  <p><label> <input type=radio name=change value="On"> On </label></p>
		  <p><label> <input type=radio name=change value="Off"> Off </label></p>
		 </fieldset>
		 <p><button>Submit Change</button><p>
		</form>
	</section>

<?php include("mullefooter.php"); ?>
