use mws_api;

-- 接口信息
create table if not exists mws_api.`interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '名称',
    `url` varchar(512) not null comment '接口地址',
    `requestParams` text null comment '请求参数',
    `description` varchar(256) null comment '描述',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    `status` int default 0 not null comment '接口状态(0-关闭，1-开启）',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)',
    `method` varchar(256) not null comment '请求类型',
    `userId` bigint not null comment '创建人id'
) comment '接口信息';

insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('梁绍辉', 'www.shaneka-glover.io', '毛航', '魏烨磊', '傅烨伟', 0, '何鹏飞', 534281580);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('胡烨磊', 'www.darline-kiehn.net', '陆聪健', '龙越泽', '余立辉', 0, '莫修杰', 75063);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('史浩然', 'www.norbert-brakus.name', '邱峻熙', '石文轩', '贾展鹏', 0, '沈志强', 4);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('叶伟宸', 'www.tanna-dare.name', '高鑫鹏', '金涛', '孙胤祥', 0, '宋懿轩', 4875483);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('王明哲', 'www.dedra-mcglynn.co', '顾浩', '邱文昊', '吕鹏涛', 0, '孟健柏', 249843102);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('卢鸿煊', 'www.elsie-leuschke.info', '卢思', '黄煜祺', '于皓轩', 0, '李泽洋', 550800);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('黎煜城', 'www.abraham-dibbert.biz', '何弘文', '赵风华', '杨文昊', 0, '徐鹭洋', 316790537);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('刘黎昕', 'www.brandon-satterfield.info', '袁锦程', '彭明哲', '高鑫鹏', 0, '石正豪', 93009858);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('吕瑾瑜', 'www.lisbeth-frami.com', '余昊强', '熊思聪', '陈博文', 0, '方博文', 885548);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('曹苑博', 'www.pei-ortiz.name', '郝绍辉', '黎文博', '罗浩宇', 0, '钟潇然', 47);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('程博超', 'www.norman-krajcik.co', '姜笑愚', '冯绍辉', '万懿轩', 0, '赖明哲', 4);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('吴伟泽', 'www.sabine-ziemann.com', '王睿渊', '邵擎苍', '苏烨华', 0, '贺建辉', 5);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('廖智辉', 'www.kanisha-auer.name', '彭思淼', '莫明', '卢乐驹', 0, '洪烨霖', 53407953);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('廖峻熙', 'www.salvatore-halvorson.info', '石志强', '史烨霖', '邵鹏煊', 0, '任文', 9874294826);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('马琪', 'www.corie-price.co', '孟聪健', '段文昊', '朱胤祥', 0, '余哲瀚', 9789884557);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('任梓晨', 'www.joetta-sipes.com', '胡弘文', '魏俊驰', '万伟宸', 0, '唐睿渊', 187672);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('陆靖琪', 'www.hilton-brown.com', '薛思淼', '严博文', '尹鹏飞', 0, '范嘉熙', 7);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('孙子涵', 'www.kimbery-robel.name', '胡天磊', '沈乐驹', '熊鑫磊', 0, '朱烨霖', 18725);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('江文轩', 'www.rosalia-braun.biz', '林明', '何嘉熙', '段熠彤', 0, '史越泽', 9681754);
insert into mws_api.`interface_info` (`name`, `url`, `description`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('侯昊强', 'www.marcellus-swift.net', '秦钰轩', '吕君浩', '姜立轩', 0, '陈梓晨', 694);