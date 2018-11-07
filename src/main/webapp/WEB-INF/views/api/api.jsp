<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

</head>

<body class="hold-transition skin-blue sidebar-mini">
	<section class="invoice">
		<div class="row">
			<div class="col-xs-12 table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>序号</th>
							<th>接口名称</th>
							<th>接口地址</th>
							<th>参数</th>
							<th>请求方式</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1 - √</td>
							<td>根据用户名查找用户可访问项目列表</td>
							<td>/api/project/list/{userName}</td>
							<td>
								<ul>
									<li>username: 用户名，必填。</li>
								</ul>
							</td>
							<td>
								GET
							</td>
						</tr>
						<tr>
							<td>2 - √</td>
							<td>查看某项目的所有报表模板</td>
							<td>/api/template/project/{id}</td>
							<td>
								<ul>
									<li>id:项目Id，必填</li>
									<li>userName: 用户名，必填。</li>
									<li>sortField：排序字段，选填，默认值为ID</li>
									<li>direction：升序或降序。选填，默认值为ASC</li>
									<li>pageIndex：当前页索引，选填，默认值为1</li>
									<li>pageSize：每页显示条目，选填，默认值为10</li>
								</ul>
							</td>
							<td>GET</td>
						</tr>
						<tr>
							<td>3 - √</td>
							<td>查看某报表模板的数据编辑记录</td>
							<td>/api/template/version/{id}</td>
							<td>
								<ul>
									<li>id:模板Id，必填</li>
									<li>userName: 用户名，必填。</li>
									<li>pageIndex：当前页索引，选填，默认值为1</li>
									<li>pageSize：每页显示条目，选填，默认值为10</li>
								</ul>
							</td>
							<td>GET</td>
						</tr>
						<tr>
							<td>4 - √</td>
							<td>根据用户分页查看报表模板列表</td>
							<td>/api/template/template/{userName}</td>
							<td>
								<ul>									
									<li>userName: 用户名，必填。</li>
									<li>pageIndex：当前页索引，选填，默认值为1</li>
									<li>pageSize：每页显示条目，选填，默认值为10</li>
								</ul>
							</td>
							<td>GET</td>
						</tr>
						<tr>
							<td>5 - √</td>
							<td>根据报表ID查看报表模板详情</td>
							<td>/api/template/detail/{templateId}</td>
							<td>
								<ul>		
									<li>templateId:报表模板Id，必填</li>							
									<li>userName: 用户名，必填。</li>
								</ul>
							</td>
							<td>GET</td>
						</tr>
						<tr>
							<td>6 - √</td>
							<td>根据报表模板ID分页查看报表生成记录</td>
							<td>/api/report/list/{templateId}</td>
							<td>
								<ul>		
									<li>templateId:报表模板Id，必填</li>							
									<li>userName: 用户名，必填。</li>
									<li>pageIndex：当前页索引，选填，默认值为1</li>
									<li>pageSize：每页显示条目，选填，默认值为10</li>
								</ul>
							</td>
							<td>GET</td>
						</tr>
						<tr>
							<td>7 - √</td>
							<td>通过Iframe访问报表</td>
							<td>/iframe/view</td>
							<td>
								<ul>		
									<li>templateId:报表模板Id，必填</li>							
									<li>userName: 用户名，必填。</li>
								</ul>
							</td>
							<td>Iframe</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- /.col -->
		</div>
	</section>
</body>
</html>