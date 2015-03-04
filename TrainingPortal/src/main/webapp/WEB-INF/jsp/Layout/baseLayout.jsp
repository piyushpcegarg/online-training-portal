<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp" %>
<c:set var="localeFromSession">${pageContext.response.locale}</c:set>
<sec:authorize access="authenticated" var="authenticated"/>
<!DOCTYPE HTML>
<html>

<head>
  <title>Online Training Portal</title>
  <meta name="description" content="website description" />
  <meta name="keywords" content="website keywords, website keywords" />
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  
	<%-- Get Common Javascript List --%>
	<tiles:importAttribute name="jsList" ignore="true"/>    
	<c:forEach var="js" items="${jsList}">
	      <script type="text/javascript" src="<%=request.getContextPath()%><c:out value="${js}"/>"></script>
	</c:forEach>
	
	<%-- Get Page Specific Javascript List --%>
	<tiles:importAttribute name="pageSpecificJsList" ignore="true"/>    
	<c:forEach var="js" items="${pageSpecificJsList}">
	      <script type="text/javascript" src="<%=request.getContextPath()%><c:out value="${js}"/>"></script>
	</c:forEach>
	
	<%-- Add Hindi language support - start  --%>
	<script type="text/javascript">
        pramukhIME.addLanguage(PramukhIndic,"hindi"); 
    </script>
    <%-- Add Hindi language support - End  --%>
		
	<%-- Get CSS List --%>
	<tiles:importAttribute name="cssList" ignore="true"/>    
	<c:forEach var="css" items="${cssList}">
	      <link rel="stylesheet" href="<%=request.getContextPath()%><c:out value="${css}"/>" type="text/css" media="screen" title="no title" />
	</c:forEach>

</head>

<body>
  <div id="main">
    <!-- START HEADER -->
	<tiles:insertAttribute name="header" />
	<!-- END HEADER -->
	
    <div id="site_content">
    
	<!--  MAIN CONTENT/BODY (THIS IS DYNAMIC) : START-->
    	<div id="content">
    	<tiles:insertAttribute name="body" />
    	</div>  
    <!--  MAIN CONTENT/BODY (THIS IS DYNAMIC) : END-->
    
    </div>
    <!-- START FOOTER -->
	<tiles:insertAttribute name="footer" />
	<!-- END FOOTER -->
  </div>
  
   <!-- javascript at the bottom for fast page loading -->
  <script type="text/javascript">
    $(document).ready(function() {
    	window.history.forward();
      $('ul.sf-menu').sooperfish();
      //Restrict Cut Copy Paste functionality in application - start 
      $(document).bind("cut copy paste",function(e) {
	      e.preventDefault();
	  });
      //Restrict Cut Copy Paste functionality in application - End
      //Disable autocomplete to all forms - Start
      $('form').attr('autocomplete', 'off');
      //Disable autocomplete to all forms - End
    });
    
   	// Initialize jquery.i18n plugin load message files 
    	loadInternationalization("${localeFromSession}");
  </script>
  
  <!-- The below code is for auto logout from the application , if user is inactive for 20 minutes - start  -->
  <!-- inactivity: 1200000 //20 Minute default (how long before showing the notice)
	sessionAlive: 300000, //5 minutes default how often to hit alive_url
	alive_url: 'contextPathJs+'/home', //send alive ping to this url -->
	 
  <c:if test="${authenticated}">
	<script type="text/javascript" charset="utf-8">
		$(document).ready(function() {
			$(document).idleTimeout({
				noconfirm : 10000,
				alive_url: contextPathJs+'/home',
				redirect_url: contextPathJs+'/logout'
			});
		});
	</script>
	</c:if>
  
</body>
</html>