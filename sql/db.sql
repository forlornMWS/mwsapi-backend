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

insert into mws_api.interface_info (id, name, url, description, requestHeader, responseHeader, status, createTime,
                                    updateTime, isDelete, method, userId, requestParams)
values (1, 'getUuid', 'http://localhost:8090/api/util/uuid', '获取uuid', '{"Content-Type":"application/json"}',
        '{"Content-Type":"application/json"}', 1, '2023-09-03 16:09:13', '2023-10-01 16:53:19', 0, 'GET', 1, ''),
       (2, 'md5WithSalt', 'http://localhost:8090/api/util/md5WithSalt', '使用MD5加密字符串，盐自定义', '{
"Content-Type":"application/json"
}', '{
  "Content-Type": "application/json"
}', 1, '2023-09-03 16:58:13', '2023-10-01 16:53:19', 0, 'GET', 1, '[
  {
    "name": "salt",
    "type": "string"
  },
  {
    "name": "str",
    "type": "string"
  }
]'),
       (3, 'md5', 'http://localhost:8090/api/util/md5', '获取md5加密字符串，不带盐',
        '{"Content-Type":"application/json"}', '{
  "Content-Type": "application/json"
}', 1, '2023-09-30 19:37:03', '2023-09-30 19:37:03', 0, 'GET', 1, null);

-- 用户调用接口关系表
create table if not exists mws_api.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户Id',
    `interfaceInfoId` bigint not null comment '接口Id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常 ，1-禁用',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口关系表';

insert into mws_api.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete)
values  (1, 1, 1, 22, 6, 0, '2023-09-08 23:02:08', '2023-09-30 19:37:49', 0),
        (2, 1, 2, 15, 5, 0, '2023-09-29 17:05:30', '2023-09-29 23:17:57', 0),
        (3, 2, 3, 23, 12, 0, '2023-09-30 19:34:58', '2023-09-30 19:37:49', 0),
        (4, 1, 3, 1, 1, 0, '2023-09-30 19:34:58', '2023-09-30 19:37:49', 0);