### 实验环境

- Ubuntu 20.04.2 LTS (WSL 2)
- Docker version 20.10.5 (WSL 2 based engine)
- HA-Proxy version 2.3.8
- MySQL 5.7.8 (docker)
- Redis 6.2.1 (docker)
- Gatling 3.5.1

### 实验步骤

首先，基于实验1实现的学生信息管理系统做了以下修改：

1. 将系统数据库由H2修改为MySQL，MySQL使用docker镜像搭建。MySQL中数据表结构与实验1相同，此处不再赘述。
2. 使用jib插件将系统容器化。
3. 接入HA-Proxy搭建一个水平扩展的集群系统。
4. 接入Redis缓存。Redis使用docker镜像搭建。
5. 使用Gatling压测工具进行性能测试。

值得一提的是，为了更好的体现出实验结果，我们对系统做了一些修改：

1. 关闭数据库中院系、籍贯两个字段的索引。

2. 随机生成100,000条学生数据插入数据库。

3. 测试时使用较复杂的查询语句，实际使用的语句为

   ```sql
   SELECT student FROM Student student WHERE student.nativePlace.name LIKE '%guangdong%' OR student.department.name LIKE '%computer%' ORDER BY student.birthDate ASC
   ```

### 实验设置

实验时，每个服务器节点的启动指令类似于

```shell
docker run -d --name student-with-cache-1 -p 8081:8080 --cpus=0.5 student-with-cache
```

我们测试了以下四种配置：

- 2个服务器节点，关闭cache
- 2个服务器节点，开启cache
- 4个服务器节点，关闭cache
- 4个服务器节点，开启cache

每组配置使用Gatling测试5次，每次的`atOnceUsers`为100。

实验的所有数据存放于`./raw_data`文件夹，实验使用的镜像存放于 [student-with-cache](https://hub.docker.com/repository/docker/ershierdu/student-with-cache)和[student-without-cache](https://hub.docker.com/repository/docker/ershierdu/student-without-cache)。

### 实验结果与分析

我们对每种配置的5次测试的`mean response time`再求平均值，得到以以下数据

| 配置                     | average(`mean response time`) |
| ------------------------ | ----------------------------- |
| 2个服务器节点，关闭cache | 7603.6 ms                     |
| 2个服务器节点，开启cache | 2790.8 ms                     |
| 4个服务器节点，关闭cache | 7343.0 ms                     |
| 4个服务器节点，开启cache | 1877.6 ms                     |

从上述结果可以看出：

- **增加服务器节点能提高系统性能，但在关闭cache时效果不明显。**

  在开启cache与否的两种情况下，4个服务器节点相比2个服务器节点的平均响应时间分别降低了32.7%和3.4%。后者提升较小的主要原因是：对于学生管理系统这一场景，在没有开启cache时SQL语句的执行是主要瓶颈。实验时所有服务器节点都连接到同一个MySQL服务器，因此不管有多少个服务器节点，MySQL数据库都需要执行相同数量的请求。因此增加服务器节点数量没有显著效果。

- **在该场景中，使用cache效果显著。**

  在2个服务器节点和4个服务器节点两种情况下，开启cache相比关闭cache的平均响应时间分别降低了63.3%和74.4%。

