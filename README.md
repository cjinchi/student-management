本项目是《[软件体系结构](https://github.com/njuics/sa-2021)》课程的作业，使用spring-boot实现一个学生信息管理系统。`rest`分支是项目的RESTful版本。

### 使用&开发文档

使用与开发文档请参考[`master`分支](https://github.com/cjinchi/student-management/tree/master)，此处不再赘述。

### API

#### 查询多个学生

- URL：`/students?q=keyword`
- Method：`GET`
- 备注：URL中`?q=keyword`可以省略。若指定了关键词，则返回学号、姓名、籍贯、性别或院系包含关键词的学生列表；若没有指定，则返回所有学生。

#### 查询单个学生

- URL：`/students/{id}`
- Method：`GET`
- 备注：`id`是指数据库自动分配的`id`列，请注意与`student_id`（学号）的区分

#### 新增学生

- URL：`/students`

- Method：`POST`

- 备注：需要在请求体中提供新增学生的信息，例如：

  ```json
  {
  	"studentId": "190000010",
  	"name": "林银朱",
  	"birthDate": "1999-11-13",
  	"gender": "女",
  	"nativePlace": "河北省",
  	"department": "物理学院"
  }
  ```

#### 更新学生

- URL：`/students/{id}`
- Method：`PUT`
- 备注：需要在请求体中提供更新后的学生信息，与新增学生类似

#### 删除学生

- URL：`/students/{id}`
- Method：`DELETE`

### 说明

RESTful版本主要参考了[3]中simplified版本的实现，绝大部分逻辑与其相同，主要做了以下修改：

- 新增删除方法，删除成功时返回`200`，资源不存在时返回`404`

- 项目中默认插入了10条随机生成的数据，供测试使用

  ```sql
  INSERT INTO students VALUES (0,'190000001', '贺菲琳', '1997-08-24','女','河南省','电子科学与工程学院（示范性微电子学院）');
  INSERT INTO students VALUES (1,'190000002', '向南星', '2001-10-23','女','山西省','匡亚明学院');
  INSERT INTO students VALUES (2,'190000003', '米荷语', '1994-03-01','男','四川省','医学院');
  INSERT INTO students VALUES (3,'190000004', '黎梦蓉', '1996-07-08','男','江西省','文学院');
  INSERT INTO students VALUES (4,'200000005', '司玥书', '1990-05-24','女','福建省','天文与空间科学学院');
  INSERT INTO students VALUES (5,'200000006', '栾尔云', '1994-01-07','女','河南省','工程管理学院');
  INSERT INTO students VALUES (6,'190000007', '劳含菲', '1998-11-01','女','宁夏回族自治区','建筑与城市规划学院');
  INSERT INTO students VALUES (7,'160000008', '金白寒', '1992-06-06','女','新疆维吾尔自治区','软件学院');
  INSERT INTO students VALUES (8,'190000009', '钭婷瑄', '2001-02-21','男','黑龙江省','医学院');
  INSERT INTO students VALUES (9,'190000010', '林银朱', '1999-11-13','女','河北省','物理学院');
  ```

- 无论要更新的资源是否存在，[3]中的`PUT`接口都返回`204`（No Content），而本项目参考[RFC 7231](https://tools.ietf.org/html/rfc7231#page-26)修改为以下逻辑

  - 若资源不存在，且用户指定的`id`与数据库默认的下一个`id`不相同，则返回`409`（Conflict）
  - 若资源不存在，且用户指定的`id`与数据库默认的下一个`id`相同，则创建资源并返回`201`（Created）
  - 若资源存在，且更新资源成功，则返回`200`（OK）
  - 若资源存在，且更新资源失败，则返回`400`（Bad Request）

  具体逻辑如下

  ```java
  @PutMapping("/students/{id}")
  ResponseEntity<?> updateStudent(@RequestBody Student student, @PathVariable Integer id) {
  	boolean isStudentPresent = repository.findById(id).isPresent();
      
  	if (!isStudentPresent && id != repository.getMaxId() + 1) {
          // resource is not existing && resource cannot be created, return 409
  		return ResponseEntity.status(409).build();
  	}
  	
  	try {
          // create or update resource
  		// ...
  	}
  	catch (URISyntaxException e) {
          // fail to update resource, return 400
  		return ResponseEntity.badRequest().body("Unable to update " + student);
  	}
  
  	if (isStudentPresent) {
          // resource is existing && succeed to update, return 200
  		return ResponseEntity.ok().location(uri).build();
  	}
  	else {
          // resource is not existing && succeed to create, return 201
  		return ResponseEntity.created(uri).build();
  	}
  }
  ```

### 参考资料

1. https://www.bilibili.com/video/BV1GE411G7hu?p=4
2. https://tools.ietf.org/html/rfc7231#page-26
3. https://github.com/spring-projects/spring-hateoas-examples



