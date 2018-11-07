<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	

<!-- Main content -->

	<div class="box-body">
		<div class="box">
			<!-- /.box-header -->
			<table id="userTable"
				class="table table-bordered table-striped table-hover">
				<thead>
					<tr>
						<th>选择</th>
						<th>id</th>
						<th>用户名</th>
						<th>昵称</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="userItem" items="${users }" varStatus="status">
						<tr>	
							<td>
								<input type="checkbox" id="${userItem.id }" value="${userItem.userName }">
							</td>	
							<td>${userItem.id }</td>						
							<td>${userItem.userName }</td>
							<td>${userItem.nickName }</td>
							<td>
								<c:if test="${userItem.userStatus=='NORMAL' }">
									正常
								</c:if>
								<c:if test="${userItem.userStatus=='LOCK' }">
									冻结
								</c:if>
								<c:if test="${userItem.userStatus=='DELETE' }">
									已删除
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
		<!-- /.box-body -->
	</div>

<SCRIPT>
	var selectedUserRow = null;
	var table; 

	$('#userTable').DataTable({
		'paging' : true,
		'lengthChange' : false,
		'searching' : true,
		'ordering' : true,
		'info' : true,
		'autoWidth' : true,
		"columnDefs": [
            {
                "targets": [ 1 ],
                "visible": false,
                "searchable": false
            }
        ]
	});
	
	 //$("div.toolbar").html('<b>Custom tool bar! Text/images etc.</b>');
	
	
	$(function() {
		$('#userModal').on('hidden.bs.modal', function () {
			freshMenu();
		})
		
		table = $('#userTable').DataTable();
	     
	    $('#userTable tbody').on('click', 'tr', function () {
	    	selectedUserRow = table.row( this ).data();
	    	
	    	
	        //var data = table.row( this ).data();
	        //alert( 'You clicked on '+data[0]+'\'s row' );
	    } );
	    
	   
	   
	});
	
	
	function selectUsers(){
		var checkedUsers = [];
		
		//获取选中的用户
	   $("#userTable tr").find("td:first input:checkbox").each(function () {
	        var ischecked = $(this).prop("checked");
	        
	        if(ischecked){
	        	 var userId =  $(this).attr('id'); 
	        	 var userName = $(this).attr('value'); 
	        	 checkedUsers.push({ userId: userId, userName: userName});
	        }
	    });
		   
		
		updateSelectedUsers(checkedUsers);
		
		
// 		  $(":checkbox:checked", "#userTable").each(function () {
// 	            var tablerow = $(this).parent("tr");
	            
// 	            console.log(tablerow);
	            
// 	            var userId = tablerow[1];
// 	            var userName = tablerow[2];
// 	            var nickName = tablerow[3];
// 	            checkedUsers.push({ userId: userId, userName: userName, nickName: nickName });
// 	        });
		  
		console.log(checkedUsers);
	}
	
	
</SCRIPT>



