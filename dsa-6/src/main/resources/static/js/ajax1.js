/**
 * ajax1 자바스크립트
 */

$(document).ready(function() {
	$('#equalsButton').click(test6);
});

 function test1() {
	alert("test1() 함수 실행");
	
	$.ajax({
		url : 'ajaxTest1',			// 서버의 요청 경로
		type : 'get',				// 요청 메서드
		success : function() {		// 요청이 성공적으로 완료되었을때
			alert('실행 성공');		// 호출되는 콜백함수
		},
		error : function(e) {		// 요청이 실패했을 때
			alert('실행 실패');		// 호출되는 콜백함수
			alert(JSON.stringify(e));
		}
	});
 }
 
 function test2() {
	$.ajax({
		url : 'ajaxTest2',
		type : 'post',
		data : {str : '클라이언트에서 보낸 메세지'}, // 서버에 전송할 데이터 저장
		success : function() {
			alert('실행 성공');
		},
		error : function() {
			alert('실행 실패');
		}
	});
 }
 
 function test3() {
	$.ajax({
		url : 'ajaxTest3',
		type : 'get',
		dataType : 'text', // 서버로부터 반환되는 데이터 형식을 저장
						   // 받은 데이터 타입 text, json
						   // (가능한 값 : json, xml, html, text...)
		success : function(msg) {
			console.log(msg);
		},
		error : function() {
			alert('실행 실패');
		}
	});
 }
 
function test4() {
	let numData = {num1 : $('#num1').val(), num2 : $('#num2').val()};
	
	$.ajax({
		url : 'ajaxTest4',
		type : 'post',
		data : numData,
		dataType : 'text',
		success : function(msg) {
			$('#num3').val(msg);
		},
		error : function() {
			alert('계산 실패');
		}
	});
}

function test5() {
	let numData = {num4 : $('#num4').val(), num5 : $('#num5').val()};
	
	$.ajax({
		url : 'ajaxTest5',
		type : 'post',
		data : numData,
		datatype : 'text',
		success : function(msg) {
			$('#num6').val(msg);
		},
		error : function(e) {
			alert(e.responseText);
		}
	});
}

function test6() {
	let numData = {
		num7 : $('#num7').val(),
		num8 : $('#num8').val(),
		op : $('#op').val()
	};
	
	$.ajax({
		url : 'ajaxTest6',
		type : 'post',
		data : numData,
		success : function(msg) {	// 요청 성공시
			$('#num9').val(msg);
		},
		error : function(e) {		// 요청 실패시
			alert(e.responseText);
		},
		complete : function() {		// 요청 완료시
			alert('요청 완료');
		}
	});
}