# 茶叶售卖系统数据库设计文档

## 1. 数据库基本信息
- 数据库名称: tea_shop
- 字符集: utf8mb4
- 排序规则: utf8mb4_unicode_ci

## 2. 表结构说明

### 2.1 users (用户表)
| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 主键 |
| username | VARCHAR(50) | UNIQUE, NOT NULL | 用户名 |
| password | VARCHAR(255) | NOT NULL | 密码（BCrypt加密） |
| phone | VARCHAR(20) | | 手机号 |
| email | VARCHAR(100) | | 邮箱 |
| address | VARCHAR(255) | | 地址 |
| role | ENUM('USER','ADMIN') | DEFAULT 'USER' | 角色 |
| create_time | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 2.2 products (商品表)
| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 主键 |
| name | VARCHAR(100) | NOT NULL | 商品名称 |
| description | TEXT | | 商品描述 |
| price | DECIMAL(10,2) | NOT NULL | 价格 |
| image_url | VARCHAR(500) | | 图片URL |
| category | ENUM('GREEN_TEA','OOLONG_TEA','BLACK_TEA','WHITE_TEA','PUERH_TEA','TEA_SET') | | 商品分类 |
| stock | INT | DEFAULT 0 | 库存 |
| status | BOOLEAN | DEFAULT TRUE | 上架状态 |
| create_time | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

商品分类说明：
- GREEN_TEA: 绿茶
- OOLONG_TEA: 乌龙茶
- BLACK_TEA: 红茶
- WHITE_TEA: 白茶
- PUERH_TEA: 普洱茶
- TEA_SET: 茶具

### 2.3 carts (购物车表)
| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 主键 |
| user_id | BIGINT | NOT NULL, FOREIGN KEY(users.id) | 用户ID |
| product_id | BIGINT | NOT NULL, FOREIGN KEY(products.id) | 商品ID |
| quantity | INT | NOT NULL, DEFAULT 1 | 数量 |
| create_time | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

### 2.4 orders (订单表)
| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 主键 |
| order_no | VARCHAR(50) | UNIQUE, NOT NULL | 订单号 |
| user_id | BIGINT | NOT NULL, FOREIGN KEY(users.id) | 用户ID |
| total_amount | DECIMAL(10,2) | NOT NULL | 总金额 |
| status | ENUM('PENDING_PAYMENT','PAID','SHIPPED','DELIVERED','CANCELLED') | DEFAULT 'PENDING_PAYMENT' | 订单状态 |
| shipping_address | VARCHAR(255) | | 收货地址 |
| shipping_phone | VARCHAR(20) | | 收货人电话 |
| shipping_name | VARCHAR(50) | | 收货人姓名 |
| freight | DECIMAL(10,2) | DEFAULT 0.00 | 运费 |
| create_time | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

订单状态说明：
- PENDING_PAYMENT: 待付款
- PAID: 已付款
- SHIPPED: 已发货
- DELIVERED: 已送达
- CANCELLED: 已取消

### 2.5 order_items (订单项表)
| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 主键 |
| order_id | BIGINT | NOT NULL, FOREIGN KEY(orders.id) | 订单ID |
| product_id | BIGINT | NOT NULL, FOREIGN KEY(products.id) | 商品ID |
| quantity | INT | NOT NULL | 数量 |
| price | DECIMAL(10,2) | NOT NULL | 单价（下单时价格） |
| create_time | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |

## 3. 索引设计
- idx_products_category: products表的category字段索引
- idx_products_status: products表的status字段索引
- idx_orders_user_id: orders表的user_id字段索引
- idx_orders_status: orders表的status字段索引
- idx_carts_user_id: carts表的user_id字段索引
- idx_order_items_order_id: order_items表的order_id字段索引

## 4. 外键约束
- carts.user_id → users.id (CASCADE删除)
- carts.product_id → products.id (CASCADE删除)
- orders.user_id → users.id (CASCADE删除)
- order_items.order_id → orders.id (CASCADE删除)
- order_items.product_id → products.id (CASCADE删除)

## 5. 初始数据
数据库包含以下初始数据：
- 3个用户（1个管理员，2个普通用户）
- 21个商品（涵盖各种茶叶和茶具）
- 3个订单示例
- 7个订单项示例