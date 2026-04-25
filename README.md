# 茶叶售卖系统后端

基于Spring Boot的茶叶售卖系统后端服务，提供完整的电商功能包括用户管理、商品管理、购物车和订单管理。

## 技术栈

- **后端框架**: Spring Boot 2.7.0
- **数据库**: MySQL 8.0
- **持久层**: Spring Data JPA
- **安全框架**: Spring Security (BCrypt加密)
- **API文档**: RESTful API
- **构建工具**: Maven
- **序列化**: JSON

## 项目结构

```
src/main/java/com/teashop/
├── TeaShopApplication.java           # 主启动类
├── config/                         # 配置类
│   └── WebConfig.java              # Web配置（CORS、密码编码器）
├── controller/                     # 控制器层
│   ├── UserController.java         # 用户管理控制器
│   ├── ProductController.java      # 商品管理控制器
│   ├── CartController.java         # 购物车管理控制器
│   └── OrderController.java        # 订单管理控制器
├── service/                        # 业务逻辑层
│   ├── UserService.java            # 用户服务
│   ├── ProductService.java         # 商品服务
│   ├── CartService.java            # 购物车服务
│   └── OrderService.java           # 订单服务
├── repository/                     # 数据访问层
│   ├── UserRepository.java         # 用户数据访问
│   ├── ProductRepository.java      # 商品数据访问
│   ├── CartRepository.java         # 购物车数据访问
│   ├── OrderRepository.java        # 订单数据访问
│   └── OrderItemRepository.java    # 订单项数据访问
├── entity/                         # 实体类
│   ├── User.java                   # 用户实体
│   ├── Product.java                # 商品实体
│   ├── Cart.java                   # 购物车实体
│   ├── Order.java                  # 订单实体
│   └── OrderItem.java              # 订单项实体
├── dto/                            # 数据传输对象
│   ├── request/                    # 请求DTO
│   │   ├── LoginRequest.java       # 登录请求
│   │   ├── RegisterRequest.java    # 注册请求
│   │   ├── AddToCartRequest.java   # 添加购物车请求
│   │   └── CreateOrderRequest.java # 创建订单请求
│   └── response/                   # 响应DTO
│       ├── ApiResponse.java        # 统一响应格式
│       ├── UserResponse.java       # 用户响应
│       ├── ProductResponse.java    # 商品响应
│       ├── OrderResponse.java      # 订单响应
│       └── OrderItemResponse.java  # 订单项响应
└── exception/                      # 异常处理
    ├── BusinessException.java       # 业务异常
    ├── ResourceNotFoundException.java # 资源未找到异常
    └── GlobalExceptionHandler.java # 全局异常处理器
```

## 数据库设计

### 用户表 (users)
- id: 主键
- username: 用户名（唯一）
- password: 密码（加密存储）
- phone: 手机号
- email: 邮箱
- address: 地址
- createTime: 创建时间
- updateTime: 更新时间

### 商品表 (products)
- id: 主键
- name: 商品名称
- description: 描述
- price: 价格
- imageUrl: 图片URL
- category: 分类（绿茶/乌龙茶/红茶等）
- stock: 库存
- status: 状态（上架/下架）
- createTime: 创建时间
- updateTime: 更新时间

### 购物车表 (carts)
- id: 主键
- user_id: 用户外键
- product_id: 商品外键
- quantity: 数量
- createTime: 创建时间
- updateTime: 更新时间

### 订单表 (orders)
- id: 主键
- order_no: 订单号
- user_id: 用户外键
- total_amount: 总金额
- status: 订单状态
- shipping_address: 收货地址
- shipping_phone: 收货电话
- shipping_name: 收货人姓名
- freight: 运费
- createTime: 创建时间
- updateTime: 更新时间

### 订单项表 (order_items)
- id: 主键
- order_id: 订单外键
- product_id: 商品外键
- quantity: 数量
- price: 单价（下单时价格）
- createTime: 创建时间


## 配置说明

### application.yml
```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/tea_shop?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
    username: root
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true
```

## 启动说明

1. 确保已安装Java 17+和MySQL
2. 创建数据库 `tea_shop`
3. 执行 `src/main/resources/data.sql` 初始化数据库表结构和初始数据
4. 修改 `application.yml` 中的数据库连接信息
5. 运行 `mvn spring-boot:run` 或使用 `start.bat` 脚本启动，或打包后运行jar文件

