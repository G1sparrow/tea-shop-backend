-- 创建数据库
CREATE DATABASE IF NOT EXISTS tea_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE tea_shop;

-- 用户表
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    address VARCHAR(255) COMMENT '地址',
    role ENUM('USER') DEFAULT 'USER' COMMENT '角色',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='用户表';

-- 商品分类枚举值
-- GREEN_TEA: 绿茶
-- OOLONG_TEA: 乌龙茶
-- BLACK_TEA: 红茶
-- WHITE_TEA: 白茶
-- PUERH_TEA: 普洱茶
-- TEA_SET: 茶具

-- 管理员表
CREATE TABLE admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL COMMENT '管理员用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    name VARCHAR(50) COMMENT '管理员姓名',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='管理员表';

-- 商品表
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '商品名称',
    description TEXT COMMENT '商品描述',
    price DECIMAL(10,2) NOT NULL COMMENT '价格',
    image_url VARCHAR(500) COMMENT '图片URL',
    category ENUM('GREEN_TEA', 'OOLONG_TEA', 'BLACK_TEA', 'WHITE_TEA', 'PUERH_TEA', 'TEA_SET') COMMENT '商品分类',
    stock INT DEFAULT 0 COMMENT '库存',
    status BOOLEAN DEFAULT TRUE COMMENT '上架状态',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='商品表';

-- 购物车表
CREATE TABLE carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    quantity INT NOT NULL DEFAULT 1 COMMENT '数量',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) COMMENT='购物车表';

-- 订单表
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) UNIQUE NOT NULL COMMENT '订单号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '总金额',
    status ENUM('PENDING_PAYMENT', 'PAID', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING_PAYMENT' COMMENT '订单状态',
    shipping_address VARCHAR(255) COMMENT '收货地址',
    shipping_phone VARCHAR(20) COMMENT '收货人电话',
    shipping_name VARCHAR(50) COMMENT '收货人姓名',
    freight DECIMAL(10,2) DEFAULT 0.00 COMMENT '运费',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) COMMENT='订单表';

-- 订单项表
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    quantity INT NOT NULL COMMENT '数量',
    price DECIMAL(10,2) NOT NULL COMMENT '单价（下单时价格）',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) COMMENT='订单项表';

-- 创建索引
CREATE INDEX idx_products_category ON products(category);
CREATE INDEX idx_products_status ON products(status);
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_carts_user_id ON carts(user_id);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);

-- 插入初始用户数据
INSERT INTO users (username, password, phone, email, address, role) VALUES
('zhangsan', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '13800138001', 'zhangsan@example.com', '北京市朝阳区茶叶街1号', 'USER'),
('lisi', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '13800138002', 'lisi@example.com', '上海市浦东新区茶香路2号', 'USER');

-- 插入初始管理员数据
INSERT INTO admins (username, password, phone, email, name) VALUES
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '13800138000', 'admin@teashop.com', '管理员');

-- 插入初始商品数据
INSERT INTO products (name, description, price, category, stock, status) VALUES
-- 绿茶
('西湖龙井', '明前特级 · 豆香馥郁', 128.00, 'GREEN_TEA', 100, TRUE),
('碧螺春', '形美、色艳、香浓、味醇', 118.00, 'GREEN_TEA', 80, TRUE),
('信阳毛尖', '细、圆、光、直、多白毫', 108.00, 'GREEN_TEA', 90, TRUE),
('白毫银针', '毫香蜜韵 · 满披白毫', 199.00, 'GREEN_TEA', 50, TRUE),
('茉莉龙珠', '七窨一提 · 花香入骨', 89.00, 'GREEN_TEA', 120, TRUE),

-- 乌龙茶
('安溪铁观音', '兰花香 · 观音韵', 98.00, 'OOLONG_TEA', 70, TRUE),
('凤凰单丛', '鸭屎香 · 高香持久', 168.00, 'OOLONG_TEA', 60, TRUE),
('大红袍', '岩骨花香 · 醇厚回甘', 228.00, 'OOLONG_TEA', 40, TRUE),
('冻顶乌龙', '醇厚浓郁 · 天然奶香', 158.00, 'OOLONG_TEA', 55, TRUE),

-- 红茶
('云南普洱', '陈香醇厚 · 勐海熟茶', 156.00, 'BLACK_TEA', 65, TRUE),
('正山小种', '松烟香 · 桂圆汤', 135.00, 'BLACK_TEA', 75, TRUE),
('金骏眉', '芽头肥壮 · 花果香', 299.00, 'BLACK_TEA', 30, TRUE),
('祁门红茶', '群芳最 · 蜜香悠长', 148.00, 'BLACK_TEA', 85, TRUE),
('滇红', '香高味浓 · 汤色红艳', 138.00, 'BLACK_TEA', 95, TRUE),

-- 白茶
('白牡丹', '芽叶连枝 · 毫心肥壮', 188.00, 'WHITE_TEA', 45, TRUE),
('寿眉', '滋味醇厚 · 毫香显露', 78.00, 'WHITE_TEA', 100, TRUE),

-- 普洱茶
('勐海熟普', '陈香浓郁 · 汤色红浓', 258.00, 'PUERH_TEA', 40, TRUE),
('易武生普', '香气高扬 · 回甘生津', 328.00, 'PUERH_TEA', 35, TRUE),

-- 茶具
('紫砂壶', '宜兴紫砂 · 手工制作', 299.00, 'TEA_SET', 25, TRUE),
('汝窑茶具', '宋代汝窑工艺 · 天青色', 499.00, 'TEA_SET', 20, TRUE),
('玻璃茶具', '耐高温玻璃 · 透明美观', 89.00, 'TEA_SET', 60, TRUE);

-- 插入初始订单数据
INSERT INTO orders (order_no, user_id, total_amount, status, shipping_address, shipping_phone, shipping_name, freight) VALUES
('TEA202604200001', 2, 236.00, 'PAID', '北京市朝阳区茶叶街1号', '13800138001', '张三', 8.00),
('TEA202604200002', 3, 156.00, 'SHIPPED', '上海市浦东新区茶香路2号', '13800138002', '李四', 0.00),
('TEA202604200003', 2, 467.00, 'PENDING_PAYMENT', '北京市朝阳区茶叶街1号', '13800138001', '张三', 8.00);

-- 插入初始订单项数据
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 128.00),  -- 西湖龙井
(1, 6, 1, 98.00),   -- 安溪铁观音
(1, 21, 1, 10.00),  -- 运费（虚拟商品）
(2, 11, 1, 156.00), -- 云南普洱
(3, 4, 1, 199.00),  -- 白毫银针
(3, 14, 1, 148.00), -- 祁门红茶
(3, 21, 1, 10.00);  -- 运费（虚拟商品）

-- 茶叶溯源表
CREATE TABLE tea_tracing (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL COMMENT '商品ID',
    origin VARCHAR(255) NOT NULL COMMENT '产地',
    harvest_time DATE NOT NULL COMMENT '采摘时间',
    process TEXT COMMENT '工艺描述',
    tea_variety VARCHAR(100) COMMENT '茶树品种',
    quality_report VARCHAR(500) COMMENT '质检报告URL',
    tea_images TEXT COMMENT '茶园实拍图片URL（多个用逗号分隔）',
    growth_video VARCHAR(500) COMMENT '生长周期短视频URL',
    qr_code VARCHAR(500) COMMENT '溯源二维码URL',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) COMMENT='茶叶溯源表';

-- 茶叶品鉴表
CREATE TABLE tea_tasting (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL COMMENT '商品ID',
    fragrance VARCHAR(100) COMMENT '香型',
    taste TEXT COMMENT '口感描述',
    steep_resistance INT COMMENT '耐泡度（1-10级）',
    suitable_tea_set VARCHAR(255) COMMENT '适合茶具',
    brewing_suggestion TEXT COMMENT '冲泡建议',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) COMMENT='茶叶品鉴表';

-- cc
CREATE TABLE brewing_guide (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL COMMENT '商品ID',
    water_temperature INT NOT NULL COMMENT '水温（℃）',
    tea_amount DECIMAL(5,2) NOT NULL COMMENT '投茶量（克）',
    brewing_time INT NOT NULL COMMENT '冲泡时间（秒）',
    brewing_times INT NOT NULL COMMENT '冲泡次数',
    tea_set_recommendation VARCHAR(255) COMMENT '茶具推荐',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) COMMENT='冲泡指南表';

-- 创建索引
CREATE INDEX idx_tea_tracing_product_id ON tea_tracing(product_id);
CREATE INDEX idx_tea_tasting_product_id ON tea_tasting(product_id);
CREATE INDEX idx_brewing_guide_product_id ON brewing_guide(product_id);

-- 插入茶叶溯源示例数据
INSERT INTO tea_tracing (product_id, origin, harvest_time, process, tea_variety, quality_report, tea_images, growth_video, qr_code) VALUES
(1, '浙江省杭州市西湖区龙井村', '2025-03-15', '采摘后经萎凋、揉捻、发酵、干燥等工艺制成', '群体种', 'https://example.com/reports/qx_longjing_2025.pdf', 'https://example.com/images/tea_garden1.jpg,https://example.com/images/tea_garden2.jpg', 'https://example.com/videos/growth_longjing.mp4', 'https://example.com/qrcode/tracing_1.png'),
(2, '江苏省苏州市吴中区洞庭山', '2025-03-20', '采摘后经杀青、揉捻、搓团显毫、烘干等工艺制成', '碧螺春群体种', 'https://example.com/reports/biluochun_2025.pdf', 'https://example.com/images/tea_garden3.jpg,https://example.com/images/tea_garden4.jpg', 'https://example.com/videos/growth_biluochun.mp4', 'https://example.com/qrcode/tracing_2.png'),
(3, '河南省信阳市浉河区', '2025-04-01', '采摘后经杀青、揉捻、解块、初烘、摊凉、复烘等工艺制成', '信阳群体种', 'https://example.com/reports/xinyangmaojian_2025.pdf', 'https://example.com/images/tea_garden5.jpg,https://example.com/images/tea_garden6.jpg', 'https://example.com/videos/growth_maojian.mp4', 'https://example.com/qrcode/tracing_3.png');

-- 插入茶叶品鉴示例数据
INSERT INTO tea_tasting (product_id, fragrance, taste, steep_resistance, suitable_tea_set, brewing_suggestion) VALUES
(1, '豆香', '鲜爽甘醇，回味悠长', 8, '玻璃杯、盖碗', '水温80-85℃，冲泡时间1-2分钟'),
(2, '花果香', '鲜醇甘甜，香气清雅', 7, '玻璃杯、紫砂壶', '水温75-80℃，冲泡时间1分钟'),
(3, '栗香', '鲜爽醇厚，回甘明显', 9, '玻璃杯、瓷壶', '水温85-90℃，冲泡时间1-2分钟');

-- 插入冲泡指南示例数据
INSERT INTO brewing_guide (product_id, water_temperature, tea_amount, brewing_time, brewing_times, tea_set_recommendation) VALUES
(1, 85, 3.00, 90, 3, '玻璃杯或盖碗'),
(2, 80, 2.50, 60, 4, '玻璃杯或紫砂壶'),
(3, 90, 3.50, 120, 5, '玻璃杯或瓷壶');

-- 茶器表
CREATE TABLE tea_sets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '茶器名称',
    desc TEXT COMMENT '茶器描述',
    price DECIMAL(10,2) NOT NULL COMMENT '价格',
    material VARCHAR(100) COMMENT '材质',
    category VARCHAR(50) COMMENT '分类',
    status BOOLEAN DEFAULT TRUE COMMENT '上架状态',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='茶器表';

-- 插入初始茶器数据
INSERT INTO tea_sets (name, desc, price, material, category, status) VALUES
('宜兴紫砂壶', '手工制作，透气性好，适合冲泡乌龙茶', 299.00, '紫砂', 'pot', TRUE),
('汝窑盖碗', '宋代汝窑工艺，天青色，适合各种茶类', 199.00, '陶瓷', 'set', TRUE),
('玻璃公道杯', '耐高温玻璃，透明美观，便于观察茶汤', 89.00, '玻璃', 'cup', TRUE),
('鸡翅木茶盘', '天然鸡翅木，纹理美观，排水性好', 399.00, '木材', 'tray', TRUE),
('青瓷茶杯套装', '龙泉青瓷，釉色温润，送礼佳品', 259.00, '陶瓷', 'set', TRUE),
('不锈钢茶漏', '食品级不锈钢，过滤效果好，耐用', 29.00, '不锈钢', 'set', TRUE),
('茶夹', '竹制茶夹，用于夹取茶杯，卫生方便', 19.00, '竹子', 'set', TRUE),
('茶匙', '铜制茶匙，用于取茶，美观实用', 39.00, '铜', 'set', TRUE);

-- 创建索引
CREATE INDEX idx_tea_sets_category ON tea_sets(category);
CREATE INDEX idx_tea_sets_status ON tea_sets(status);