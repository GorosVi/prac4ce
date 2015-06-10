$(document).ready(function() {

	/* Practice #7 - to 09.06.15 */

	$('#OpenDataBtnHide').on('click',function(){
		$('#OpenData .block + .response').html("");
	});
	$('#OpenDataGetLog').on('click',function(){
		$('#OpenData .block + .response').load( encodeURI("/opendata?"+
		                                        "answer="+$("#WordAnswer").val()));
	});

	/* Practice #6 - to xx.05.15 */

	$('#WordLabBtnHide').on('click',function(){
		$('#WordLab .block + .response').html("");
	});
	$('#WordLabGetQuestion').on('click',function(){
		$('#WordLab .block + .response').load( "/wordlab");
	});
	$('#WordAnswerBtn').on('click',function(){
		$('#WordLab .block + .response').load( encodeURI("/wordlab?"+
		                                        "answer="+$("#WordAnswer").val()));
	});


	/* Practice #5 - to 11.05.15 */

	$('#StruLabBtnHide').on('click',function(){
		$('#StruLab .block + .response').html("");
	});
	$('#StruLabViewStats').on('click',function(){
		$('#StruLab .block + .response').load( "/structlab?action=1");
	});
	$('#StruLabCallExams').on('click',function(){
		$('#StruLab .block + .response').load( "/structlab?action=2");
	});
	$('#StruLabKillStudents').on('click',function(){
		$('#StruLab .block + .response').load( "/structlab?action=3");
	});
	$('#StruLabCreDep').on('click',function(){
		$('#StruLab .block + .response').load( encodeURI("/structlab?action=0"+
		                         "&dname="+$("#StruLabDepName").val()));
	});


	/* Practice #4 - to 28.04.15 */

	$('#UniTableBtn').on('click',function(){
		$('#UniTable .block + .response').load( encodeURI("/unitable?"+
		                      "min="+$("#UniMin").val()+
		                     "&max="+$("#UniMax").val()));
	});
	$('#UniTableBtnAdData').on('click',function(){
		$('#UniTable .block + .response').load( encodeURI("/unitable?"+
		                      "min="+$("#UniMin").val()+
		                     "&max="+$("#UniMax").val()+
		                     "&extmode=true"));
	});
	$('#UniTableBtnHide').on('click',function(){
		$('#UniTable .block + .response').html("");
	});


	var MaxCellID = 0;
	$('#BuBtnAdd').on('click',function(){
		MaxCellID++;
		$('#BudgetList').append("\
			<li>\
			<input type=\"text\" id=\"BuDesc"+MaxCellID+"\" placeholder=\"Статья\">\ &nbsp;\
			<input type=\"text\" id=\"BudSum"+MaxCellID+"\" placeholder=\"Сумма\">\
			</li>"
		);
	});
	$('#BuBtnCalc').on('click',function(){
		var Request = "Count="+MaxCellID;
		for (Count = 0; Count <= MaxCellID; Count++){
			Request += "&Desc"+Count+"="+encodeURI($('#BuDesc'+Count).val());
			Request += "&Sum" +Count+"="+encodeURI($('#BudSum'+Count).val());
		}
		$('#ListBudget .block + .response').load("/listbudget?"+Request);
	});
	$('#BuBtnHide').on('click',function(){
		$('#ListBudget .block + .response').html("");
	});
	$('#BuBtnReset').on('click',function(){
		MaxCellID = 0;
		$('#BudgetList').html("\
			<li>\
			<input type=\"text\" id=\"BuDesc0\" placeholder=\"Статья\">\
			&nbsp;\
			<input type=\"text\" id=\"BudSum0\" placeholder=\"Сумма\">\
			</li>"
		);
	});


	/*  Colors - lab from lecture at 28.04.15 */

	$('#ColorTableBtn').on('click',function(){
		$('#ColorTable .block + .response').load( encodeURI("/colortable?"+
		                      "min="+$("#ColorMin").val()+
		                     "&max="+$("#ColorMax").val()));
	});
	$('#ColorTableBtnHide').on('click',function(){
		$('#ColorTable .block + .response').html("");
	});


	/* Practice #3 - to 07.04.15 */

	$('#NearTenBtn').on('click',function(){
		$('#NearTen .block + .response').load( encodeURI("/nearten?"+
		                         "M="+$("#NearTenM").val()+
		                        "&N="+$("#NearTenN").val()));
	});
	$('#NearTenBtnHide').on('click',function(){
		$('#NearTen .block + .response').html("");
	});



	$('#SquareRootsBtn').on('click',function(){
		$('#SquareRoots .block + .response').load( encodeURI("/squaroots?"+
		                         "A="+$("#SquareRootsA").val()+
		                        "&B="+$("#SquareRootsB").val()+
		                        "&C="+$("#SquareRootsC").val()));
	});
	$('#SquareRootsBtnHide').on('click',function(){
		$('#SquareRoots .block + .response').html("");
	});



	$('#RandomArrayBtn').on('click',function(){
		$('#RandomArrays .block + .response').load( "/randarr");
	});
	$('#RandomArrayBtn2').on('click',function(){
		$('#RandomArrays .block + .response').load( "/randarr?extmode=true");
	});
	$('#RandomArrayBtnHide').on('click',function(){
		$('#RandomArrays .block + .response').html("");
	});


	$(".tile").hover(function() {
			$(this).stop().animate({backgroundColor: "#E1ADC9", borderColor: "#BF6797" }, 200);
			$(".num",this).stop().animate({color: "#B76391"}, 200);
			$("a,.desc",this).stop().animate({color: "#55002E"}, 200);
		},function() {
			$(this).stop().animate({ backgroundColor: "#E6FBBD", borderColor: "#B9DC77" }, 200);
			$(".num",this).stop().animate({color: "#B3D374"}, 200);
			$("a,.desc",this).stop().animate({color: "#406200"}, 200);
	});

	var linkCallback = 0;
	$('a[href^="#"]').click( function(event){
		if (event.which == 2)
			return;
		linkCallback = 1;
		url =  $(this).attr('href');
		if (window.location.hash == url)
			$(window).trigger('hashchange');
		else
			window.location.hash = url;
		return false;
	});
	$(window).on('hashchange', function(){
		var url = window.location.hash;
		$("div.task").hide();
		if (url != ""){
			if (url == "#dash")
				url = "";
			$("div.task" + url).fadeIn();
			if (linkCallback == 1){
				$('html, body').stop().animate({ scrollLeft: 0,
				                scrollTop:$("div.task" + url).offset().top
			 	                }, 300);
				linkCallback = 0;
			} else
				window.scrollTo(0,$("div.task" + url).offset().top);
			if (url == "")
				$("div.task#index").hide();
		} else
			$("div.task#index").fadeIn();
	}).trigger('hashchange');
});