<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function(){
		
		$(window).scroll(function(event){
		
		  	var scrollBottom = $("body").height() - $(window).height() - $(window).scrollTop();
			
		  	if(scrollBottom == 0){
		  		var html = "";
		  		for(var i=0; i<10; i++){
		  			html += "<h3>늘리기~~~~~~~~~~~~~~~~~~~~~~~~~</h3>";
		  		}
		  		$("#scrollvar").append(html);
		  	}
		  	
		}); // end of $(window).scroll---------
		
		
	}); // end of ready-----------
</script>

<div id="scrollvar">

</div>
	