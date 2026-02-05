# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 概述

本仓库是一系列独立的 Java 示例项目集合，用于演示各种 Java 技术和概念。每个子目录都是独立的项目，拥有各自的构建配置。

## 构建命令

大多数项目使用 Maven，需要在各项目目录内执行构建命令：

```bash
# 构建指定项目
cd <project-name>
mvn clean install

# 运行测试
mvn test

# 运行单个测试类
mvn test -Dtest=ClassName

# 运行单个测试方法
mvn test -Dtest=ClassName#methodName
```

## 项目结构

所有项目目标为 **Java 8**，使用 Maven 进行依赖管理。

### Java 核心示例
- **juc-demo** - Java 并发包(JUC)示例：CountDownLatch、CyclicBarrier、Semaphore、ReentrantLock、StampedLock、Atomic 类、volatile、Future
- **stream-demo** - Java Stream API 示例
- **nio-demo** - Java NIO：Buffer、Channel、Charset、Pipe、Scatter/Gather、网络 NIO

### 设计模式
- **design-demo** - 设计模式实现：单例、工厂、策略、观察者、装饰器、命令、外观、模板、状态、适配器、动态代理

### 网络编程
- **netty-demo** - 基于 Netty 的 RPC 实现，包含独立的客户端/服务端模块，采用主从 Reactor 模式
- **tcpip-demo** - 基于 Spring Boot 的 TCP/IP 客户端-服务端示例

### Spring/Spring Boot
- **starter-demo** - 自定义 Spring Boot Starter 实现（autoconfigurer + starter + demo-project）
- **mybatis-demo** - MyBatis 集成示例
- **thymeleaf-demo** - Thymeleaf 模板引擎示例
- **shiro-demo** - Apache Shiro 安全框架示例

### 其他
- **distributedlock-demo** - 分布式锁示例
- **io-demo** - Java I/O 示例
- **tree-demo** - 树形数据结构实现

## 关键架构模式

### netty-demo 架构
多模块 Maven 项目，包含 RpcServer 和 RpcClient：
- 使用 NioEventLoopGroup 作为 boss 组（1个线程）和 worker 组
- CustomChannelInitializer 配置处理管道
- CustomRpcServerHandler/CustomRpcClientHandler 处理业务逻辑

### starter-demo 结构
遵循 Spring Boot Starter 规范：
- `demo-spring-boot-starter-autoconfigurer` - 包含自动配置、属性类和服务
- `demo-spring-boot-starter` - 空模块，依赖 autoconfigurer
- `demo-project` - 使用该 starter 的示例项目
- 通过 `META-INF/spring.factories` 注册自动配置
