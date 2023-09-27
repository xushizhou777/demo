#
#todo 你的建表语句,包含索引
CREATE DATABASE IF NOT EXISTS `hg-demo`;
use `hg-demo`;
CREATE TABLE `order` (
                         `order_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
                         `user_id` INT NOT NULL COMMENT '用户ID',
                         `order_number` VARCHAR(50) NOT NULL COMMENT '订单号',
                         `order_amount` DECIMAL(10,2) NOT NULL COMMENT '订单金额',
                         `shipping_address` VARCHAR(100) NOT NULL COMMENT '收获地址',
                         `contact_number` VARCHAR(20) NOT NULL COMMENT '电话',
                         `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
                         `payment_method` VARCHAR(20) NOT NULL COMMENT '支付方式',
                         `payment_status` VARCHAR(20) NOT NULL COMMENT '支付状态',
                         `order_status` VARCHAR(20) NOT NULL COMMENT '订单状态'
) COMMENT '订单表';
CREATE UNIQUE INDEX idx_order_number ON `order` (`order_number`);
#订单号常用来查询

CREATE TABLE `order_details` (
                                 `detail_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
                                 `order_id` INT NOT NULL COMMENT '订单id',
                                 `product_id` INT NOT NULL COMMENT '商品id',
                                 `product_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
                                 `quantity` INT NOT NULL COMMENT '商品数量',
                                 `price` DECIMAL(10,2) NOT NULL COMMENT '商品价格',
                                 `subtotal` DECIMAL(10,2) NOT NULL COMMENT '总价',
                                 FOREIGN KEY (`order_id`) REFERENCES `order`(`order_id`)
) COMMENT '订单详情表';

CREATE TABLE `payment_record` (
                                  `payment_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
                                  `order_id` INT NOT NULL COMMENT '订单id',
                                  `payment_method` VARCHAR(20) NOT NULL COMMENT '支付方式',
                                  `amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
                                  `payment_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '支付时间',
                                  FOREIGN KEY (`order_id`) REFERENCES `order`(`order_id`)
) COMMENT '支付详情表';


# 由于 order 表中的数据量比较大，同时根据业务逻辑，一个用户的订单数据可能会集中在某个时间段内产生，因此可以考虑采用按照时间进行水平分库的方式来进行分库分表设计。
# 分库键：根据创建时间将订单数据按照时间范围进行分布到不同的库中。
# 分表键：在每个库中按照用户ID对订单数据进行水平拆分。
#