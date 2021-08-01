CREATE TABLE s_user
(
    id          int PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    user_id     varchar(20) NOT NULL unique COMMENT '用户id',
    user_name   varchar(128) COMMENT '姓名',
    head_image  varchar(128) COMMENT '头像地址',
    age         int COMMENT '年龄',
    sex         tinyint COMMENT '性别 0-女 1-男',
    mail        varchar(128) COMMENT '邮箱',
    passwd      varchar(128) COMMENT '密码',
    salt        varchar(12) COMMENT '盐',
    create_time datetime,
    update_time datetime
) COMMENT ='用户';


CREATE TABLE s_product
(
    id          int PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    product_id  varchar(20) NOT NULL unique COMMENT '商品id',
    title       varchar(128) COMMENT '商品描述',
    detail      varchar(256) COMMENT '商品详情',
    cover_image varchar(128) COMMENT '商品图片地址',
    amount      decimal COMMENT '商品价格',
    stock       int COMMENT '商品库存',
    create_time datetime,
    update_time datetime
) COMMENT =' 商品';


CREATE TABLE s_order
(
    id               int PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    order_id         varchar(64) NOT NULL unique COMMENT '订单id',
    order_state      varchar(20) COMMENT '状态 0-已支付 1-未支付 2-取消',
    order_type       varchar(20) COMMENT '订单类型 0-普通 1-促销 2-其他',
    user_id          varchar(20) NOT NULL COMMENT '用户id',
    total_amount     decimal COMMENT '订单总金额',
    pay_amount       decimal COMMENT '实际支付金额',
    valid            varchar(2) COMMENT '订单有效标志',
    receiver_address varchar(200) COMMENT '收货地址',
    create_time      datetime,
    update_time      datetime
) COMMENT =' 订单';



CREATE TABLE s_order_item
(
    id          int PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    order_id    varchar(64) NOT NULL COMMENT '订单id',
    product_id  varchar(20) NOT NULL COMMENT '商品id',
    buy_num     int COMMENT '购买数量',
    price       decimal COMMENT '购买时价格',
    create_time datetime,
    update_time datetime
) COMMENT =' 订单项';