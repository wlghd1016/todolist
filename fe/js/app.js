(function (window) {
	// Your starting point. Enjoy the ride!
	'use strict';

	$.ajax({
        type: "GET",
        url:"/api/todos",
        dataType : "json",
        success: function(data){
						for( let i in data){
								let html = "<li>"
									+"<div class=\"view\">"
										+"<input class=\"toggle\" type=\"checkbox\""
										+"id=\""+data[i].id+"\">"
										+"<label>"+data[i].todo+"</label>"
										+"<button class=\"destroy\"></button>"
									+"</div>"
									+"<input class=\"edit\" value=\"Rule the web\">"
								+"</li>";

								$('.todo-list').prepend( html );
								if( data[i].completed == 1){
										$('li:first').addClass('completed');
										$('li:first .toggle').prop("checked",true);
							}
						}
						todoCount();

        },
        error: function(xhr, status, error) {
            alert(error);
        }
	});

	function todoCount(){
			let todo_count = $('.todo-list li').not($(".completed"));
			$('.todo-count strong').text(todo_count.length)
	}

	$('.new-todo').keyup(function(e){
		if (e.keyCode == 13){

			if($(this).val()){
			let data={
				completed : 0 ,
				todo : $(this).val()
			};

			 $.ajax({
				 	type: "POST",
		 			contentType: "application/json",
		 			url:"/api/todos",
		 			data:JSON.stringify(data),
		 			success: function(data){
						let html = "<li>"
							+"<div class=\"view\">"
								+"<input class=\"toggle\" type=\"checkbox\""
								+"id=\""+data.id+"\">"
								+"<label>"+data.todo+"</label>"
								+"<button class=\"destroy\"></button>"
							+"</div>"
							+"<input class=\"edit\" value=\"Rule the web\">"
						+"</li>";

						$('.todo-list').prepend( html );
						todoCount()
					}
			 });
		 };
		};
	});

	$(document).on('change','.toggle',function(){
		let checked = $(this).is(":checked") ? 1 : 0;
		let $this=$(this)
		let data = {
			completed : checked,
			todo : $(this).parent().children("label").text()
		};

		$.ajax({
			type: "PUT",
			contentType: "application/json",
			url:"/api/todos/"+$(this).attr('id'),
			data:JSON.stringify(data),
			success: function(data){
					if(checked){
							$this.parent().parent().addClass("completed");
					}else {
							$this.parent().parent().removeClass("completed");
					}
					todoCount()
			}

		});
	});

	$(document).on('click','.destroy',function(){
		console.log($(this).parent().children(".toggle").attr('id'));
		let $this=$(this);

		$.ajax({
			type: "DELETE",
			url:"/api/todos/"+$(this).parent().children(".toggle").attr('id'),
			success: function(){
					$this.parent().parent().remove();
					todoCount()
			}

		});
	});

	$('.filters li a').on('click', function(e){
			e.preventDefault();

			let filters = $(this).text();
			$('.selected').removeClass("selected");
			$(this).addClass("selected");
			if( filters == "Active"){
				$('.todo-list li').show();
				$('.todo-list .completed').hide();
			}else if( filters == "Completed"){
				$('.todo-list li').hide();
				$('.todo-list .completed').show();
			}else{
				$('.todo-list li').show();
			}
	});

	$('.clear-completed').on('click', function(e){
		let completed = $('.completed');
		$('.completed').each(function(e){
			let $this = $(this);
			$.ajax({
				type: "DELETE",
				url:"/api/todos/"+$(this).children().children(".toggle").attr('id'),
				success: function(){
						$this.remove();
						todoCount()
				}
			});
		});
	});


})(window);
