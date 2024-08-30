/*
ajax2.js
*/
$(document).ready(function() {
	
	// 서버로 각각의 Parameter 전달
	$('#formButton1').click(function() {
		// 폼의 데이터를 읽어오기
		let n = $('#name1').val();
		let a = $('#age1').val();
		let p = $('#phone1').val();
		
		// 유효성 검사
		if (n == '') {
			alert('이름을 입력하세요');
			return;
		}
		if(a == '' || isNaN(a)) {
			alert('나이를 숫자로 입력하세요.');
			return;
		}
		
		// 사용자가 입력한 내용을 서버로 전송
		$.ajax({
			url : 'insert1',
			type : 'post',
			data : { name : n, age : a, phone : p },
			success : function() {
				alert('저장되었습니다.');
				$('#name1').val('');
				$('#age1').val('0');
				$('#phone1').val('');
			},
			error : function() {
				alert('저장 실패');
			}
		});
	});
	
	// 서버로 DTO객체 전달1
	$('#formButton2').click(function() {
		let n = $('#name2').val();
		let a = $('#age2').val();
		let p = $('#phone2').val();
		
		let ob = {name: n, age: a, phone: p};
		
		$.ajax({
			url : 'insert2',
			type : 'post',
			data : ob,
			success : function() {
				alert('저장되었습니다.');
				$('#name2').val('');
				$('#age2').val('0');
				$('#phone2').val('');
			},
			error : function() {
				alert('저장 실패');
			}
		});
	});
	
	// 서버로 DTO객체 전달2
	$('#formButton3').click(function() {
		$.ajax({
			url : 'insert3',
			type : 'post',
			// 폼 데이터를 URL 인코딩된 문자열로 직렬화
			// ex. 경로?name=john&age=30&phone=1234
			data : $('#writeForm3').serialize(),
			success : function() {
				alert('저장되었습니다.');
				$('#name3').val('');
				$('#age3').val(0);
				$('#phone3').val('');
			},
			error : function() {
				alert('저장 실패');
			}
		});
	});
	
	// 버튼 이벤트 등록
	$('#bt1').on('click', getObject1);
	$('#bt2').on('click', getObject2);
	$('#bt3').on('click', getList);
	$('#bt4').on('click', sendArray);
	$('#bt5').on('click', sendObjectArray);
});

// 서버에서 객체 받기 요청 (String으로 받기)
function getObject1() {
	$.ajax({
		url : 'getObject',
		type : 'get',
		dataType : 'text',
		success : function(ob) {
			$('#outputDiv1').html(ob);
		},
		error : function(e) {
			alert(JSON.stringify(e));
		}
	});
}

// 서버에서 객체 받기 요청 (JSON으로 받기)
function getObject2() {
	$.ajax({
		url : 'getObject',
		type : 'get',
		dataType : 'json',
		success : function(ob) {
			let str = `
						받은 객체 : ${ob}<br>
						이름 : ${ob.name}<br>
						나이 : ${ob.age}<br>
						전화 : ${ob.phone}
					  `;
			$('#outputDiv2').html(str);
		},
		error : function(e) {
			alert(JSON.stringify(e));
		}
	});
}

// 서버로부터 리스트 받기
function getList() {
	$.ajax({
		url : 'getList',
		type : 'get',
		dataType : 'json',
		success : function(list) {
			let str = '<table>';
			$(list).each(function(idx,ob) {
				str += `<tr>
							<td>${idx}</td>
							<td>${ob.name}</td>
							<td>${ob.age}</td>
							<td>${ob.phone}</td>
						</tr>`;
			});
			str += '</table>';
			$('#outputDiv3').html(str);
		},
		error : function() {
			alert('실패');
		}
	});
}

// 서버로 배열 보내기
function sendArray() {
	let ar = ['aaa','bbb','ccc'];
	
	$.ajax({
		url : 'sendArray',
		type : 'post',
		/** 
		 * traditional true >
		 * 	배열을 ar=aaa&ar=bbb&ar=ccc로 변환
		 * traditional false >
		 * 	배열을 ar[]=aaa&ar[]=bbb&ar[]=ccc로 변환
		*/
		traditional : true,
		data : { ar : ar },
		success : function() {
			alert('저장 성공');
		},
		error : function() {
			alert('저장 실패');
		}
	});
}

// 클라이언트 - 서버  JSON 주고 받기
function sendObjectArray() {
	// 객체 배열
	let ar = [
		{ name : 'aaa', age : 11, phone: '1111' },
		{ name : 'bbb', age : 22, phone: '2222' }
	];
	
	$.ajax({
		url : 'sendObjectArray',
		type : 'post',
		data : { ar : JSON.stringify(ar) },
		success : function(str) {
			alert('저장 성공');
			let plist = JSON.parse(str);
			
			let result = '<table>';
			$(plist).each(function(idx,person) {
				result += `<tr>
							<td>${idx}</td>
							<td>${person.name}</td>
							<td>${person.age}</td>
							<td>${person.phone}</td>
						  </tr>`;
			});
			result += '</table>';
			$('#outputDiv4').html(result);
		},
		error : function() {
			alert('저장 실패');
		}
	});
}