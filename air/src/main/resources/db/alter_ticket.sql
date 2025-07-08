USE air_ticket;
 
-- 添加image_url字段
ALTER TABLE ticket ADD COLUMN image_url VARCHAR(255) DEFAULT NULL COMMENT '航班图片URL'; 