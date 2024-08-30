/**
 * fetch.js
 */
window.onload = function() {
	const promise = new Promise((fullfiled,reject) => {
		// fullfiled: 작업이 성공적으로 완료되면 호출됩니다.
		// reject	: 작업이 실패하면 호출됩니다.
		setTimeout(() => {
			reject("처리 실패");	
		},2000);
	});
	console.log(promise);
	
	let btn1 = document.querySelector('#btn1');
	let btn2 = document.querySelector('#btn2');
	let btn3 = document.querySelector('#btn3');
	
	// fetch 문자 받기
	btn1.addEventListener('click',function() {
		// URL로 GET 요청, 쿼리스트링으로 text=client 데이터 전송
		fetch("fetch1?text=client")
		.then( (response) => response.text() ) // 서버 응답을 텍스트로 변환
		.then( data => {
			console.log(data);
			let result = document.querySelector('#result');
			result.innerHTML = data;
		});
	});
		
	// fetch 객체 받기
	btn2.addEventListener('click',function() {
		let ob = {
			name : "아무개" , age : 0 , phone : "010-1111-2222"
		};
		
		// POST 요청
		fetch("fetch2" // 요청 경로
				, {
					method : 'POST',
					body : JSON.stringify(ob),
					headers : { // 요청 헤더, 요청의 Content-Type을 JSON으로 설정
						"Content-type" : "application/json;charset=utf-8"
					}
				}
		)
		// fetch 요청이 성공적으로 완료되면 then 블럭이 실행되고,
		// 실패하면 catch 블럭이 실행
		.then( (response) => response.json() ) // 서버 응답을 JSON으로
		.then( data => {
			console.log(data.name);
			console.log(data.age);
			
			let result = document.querySelector('#result');
			result.innerHTML = `이름 : ${data.name}, 나이 : ${data.age}`
								+ `, 전화번호 : ${data.phone}`;
		})
		.catch(error => {
			console.log('Error : ',error);
		});
	});
	
	// 객체 배열 주고 받기
	btn3.addEventListener('click',function() {
		const info = [
			{ name : "Kim" , age : 20 },
			{ name : "Lee" , age : 30 },
			{ name : "Park" , age : 40 }
		];
		
		fetch("fetch3"
			, {
				method : "POST",
				body : JSON.stringify(info),
				headers : {
					"Content-type" : "application/json;charset=utf-8"
				}
			}
		)
		.then ( (response) => response.json() )
		.then (data => {
			console.log(data);
			let result = document.querySelector('#result');
			let str = '';
			
			for (dt of data) {
				str += `이름 : ${dt.name}, 나이 : ${dt.age} <br>`;
			}
			result.innerHTML = str;
		})
	});
}