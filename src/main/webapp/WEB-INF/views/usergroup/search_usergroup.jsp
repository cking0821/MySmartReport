<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	

<!-- Main content -->

	<div class="box-body">
		<div class="box">
			<!-- /.box-header -->
			<table id="userGroupTable"
				class="table table-bordered table-striped table-hover">
				<thead>
					<tr>
						<th>选择</th>
						<th>id</th>
						<th>用户组代码</th>
						<th>用户组名称</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="userGroupItem" items="${userGroups }" varStatus="status">
						<tr>	
							<td>
								<input type="checkbox" id="${userGroupItem.id }" value="${userGroupItem.groupName }">
							</td>
							<td>${userGroupItem.id }</td>									
							<td>${userGroupItem.groupCode }</td>
							<td>${userGroupItem.groupName }</td>							
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
		<!-- /.box-body -->
	</div>

<SCRIPT>
	var selectedUserGroupRow = null;
	var table; 

	$('#userGroupTable').DataTable({
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
	
	 	
	
	$(function() {
		$('#userModal').on('hidden.bs.modal', function () {
			freshMenu();
		})
		
		table = $('#userGroupTable').DataTable();
	     
	    $('#userGroupTable tbody').on('click', 'tr', function () {
	    	selectedUserGroupRow = table.row( this ).data();
	    } );
	    
	   
	   
	});
	
	
	function selectUserGroup(){
		var checkedUserGroups = [];
		
		//获取选中的用户
	   $("#userGroupTable tr").find("td:first input:checkbox").each(function () {
	        var ischecked = $(this).prop("checked");
	        
	        if(ischecked){
	        	 var groupId =  $(this).attr('id'); 
	        	 var groupName = $(this).attr('value'); 
	        	 checkedUserGroups.push({ groupId: groupId, groupName: groupName});
	        }
	    });
		   
		
		updateSelectedUserGroups(checkedUserGroups);
		
		
// 		  $(":checkbox:checked", "#userTable").each(function () {
// 	            var tablerow = $(this).parent("tr");
	            
// 	            console.log(tablerow);
	            
// 	            var userId = tablerow[1];
// 	            var userName = tablerow[2];
// 	            var nickName = tablerow[3];
// 	            checkedUserGroups.push({ userId: userId, userName: userName, nickName: nickName });
// 	        });
		  
		console.log(checkedUserGroups);
	}
	
	
</SCRIPT>



