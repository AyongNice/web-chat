create table auth_session
(
    id          int auto_increment
        primary key,
    email       varchar(255)                        null comment '用户邮箱',
    auth_code   varchar(255)                        null comment '授权码',
    expiredDate timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '过期时间',
    createDate  timestamp default CURRENT_TIMESTAMP null comment '创建时间'
);

create index expiredDate
    on auth_session (expiredDate);

create table chat_message
(
    id             bigint auto_increment
        primary key,
    open_id        varchar(50)             null,
    friend_id      varchar(50)             null,
    content_type   varchar(10)             null,
    content_Status varchar(10)             null,
    message        text                    null,
    timestamp      varchar(20)             null,
    message_type   varchar(10) default '1' null comment '消息类型 1 单聊 。0 群聊'
);

create index chat_message_sender_id_receiver_id_index
    on chat_message (open_id, friend_id);

create table circle
(
    id           int auto_increment
        primary key,
    openId       varchar(255)                        null comment '用户ID',
    username     varchar(255)                        null comment '用户名',
    avatar       varchar(255)                        null comment '用户头像',
    message      text                                null comment '消息内容',
    illustration json                                null comment '插图 (数组)',
    createTime   timestamp default CURRENT_TIMESTAMP null comment '创建时间'
);

create index openId
    on circle (openId);

create table friend_circle
(
    id          varchar(50)                         not null
        primary key,
    message     text                                not null,
    upload_list varchar(255)                        null,
    bucket_path varchar(255)                        null,
    user_id     int                                 not null,
    created_at  timestamp default CURRENT_TIMESTAMP null,
    user_name   varchar(50)                         null,
    constraint id
        unique (id)
);

create table friend_circle_comment
(
    id              varchar(50)                        not null comment '主键ID'
        primary key,
    circle_id       varchar(50)                        not null comment '朋友圈ID',
    user_id         varchar(50)                        not null comment '评论者ID',
    user_name       varchar(100)                       not null comment '评论者姓名',
    comment_message text                               not null comment '评论内容',
    created_at      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    receiver_id     varchar(50)                        null comment '接收者ID',
    receiver_name   varchar(100)                       null comment '接收者姓名'
)
    comment '朋友圈评论表';

create table friend_list
(
    id       int auto_increment
        primary key,
    openId   varchar(255) null comment '用户ID',
    friendId varchar(255) null comment '朋友ID',
    note     text         null comment '备注',
    msgs     json         null comment '消息 (数组)'
);

create index openId
    on friend_list (openId);

create table friend_request
(
    id            int auto_increment
        primary key,
    openId        varchar(255)                        null comment '用户ID',
    replyTo       varchar(255)                        null comment '回复用户ID',
    correlationId varchar(255)                        null comment '添加对象ID',
    command       varchar(255)                        null comment '命令',
    email         varchar(255)                        null comment '用户邮箱',
    username      varchar(255)                        null comment '用户名',
    avatar        varchar(255)                        null comment '用户头像',
    signature     text                                null comment '个性签名',
    genders       varchar(50)                         null comment '性别',
    birthday      date                                null comment '生日',
    profession    varchar(255)                        null comment '职业',
    city          json                                null comment '城市 (数组)',
    status        int                                 null comment '状态',
    location      json                                null comment '经纬度位置 (数组)',
    photo_album   json                                null comment '相册 (数组)',
    createDate    timestamp default CURRENT_TIMESTAMP null comment '创建时间'
);

create index email
    on friend_request (email);

create table group_chats
(
    id           varchar(50)  not null
        primary key,
    sender_id    int          not null,
    message      text         not null,
    content_type varchar(255) not null,
    timestamp    varchar(20)  null,
    group_id     varchar(50)  null
);

create table group_list
(
    id        varchar(50)                           not null
        primary key,
    member_id varchar(255)                          not null,
    leader    int                                   not null,
    join_date timestamp   default CURRENT_TIMESTAMP null,
    name      varchar(50) default '新建群聊'        null
);

create table like_circle
(
    id         varchar(50)                         not null
        primary key,
    circle_id  varchar(50)                         not null,
    user_id    varchar(50)                         not null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    avatar     varchar(255)                        null
);

create table memes
(
    id        int auto_increment
        primary key,
    image_url varchar(255) not null,
    file_name varchar(50)  null
);

create table system_message
(
    id         int auto_increment
        primary key,
    send_id    varchar(50)                         not null,
    receive_id varchar(50)                         not null,
    remark     varchar(255)                        null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    status     int       default 0                 null
)
    comment '0 代同意、1 已同意 2 已拒绝';

create table user
(
    id        varchar(50)  null,
    phone     char(11)     null,
    telephone varchar(16)  null,
    address   varchar(64)  null,
    enabled   tinyint      null,
    username  varchar(255) null,
    password  varchar(255) null,
    avatar    varchar(255) null,
    remark    varchar(255) null,
    roleId    int          null,
    signature varchar(50)  null,
    email     varchar(50)  null
);


INSERT INTO `ayong-chat`.user (id, phone, telephone, address, enabled, username, password, avatar, remark, roleId, signature, email) VALUES ('1', '18568887789', '029-82881234', '深圳南山', 1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'http://img0.baidu.com/it/u=1256565661,167011207&fm=253&app=138&f=JPEG?w=800&h=800', null, 6, '哥抽到不是烟,是寂寞', '254006666@qq.com');
INSERT INTO `ayong-chat`.user (id, phone, telephone, address, enabled, username, password, avatar, remark, roleId, signature, email) VALUES ('2', '18568123489', '029-82123434', '海口美兰', 1, 'libai', 'e10adc3949ba59abbe56e057f20f883e', 'http://img2.baidu.com/it/u=229296327,549891795&fm=253&app=138&f=JPEG?w=800&h=800', null, 1, '哥抽到不是烟,是寂寞', '354009999@qq.com');
INSERT INTO `ayong-chat`.user (id, phone, telephone, address, enabled, username, password, avatar, remark, roleId, signature, email) VALUES ('3', '18568123666', '029-82111555', '广州番禺', 1, 'hanyu', 'e10adc3949ba59abbe56e057f20f883e', 'http://img1.baidu.com/it/u=2873820942,330954827&fm=253&app=138&f=JPEG?w=800&h=800', null, 1, '哥抽到不是烟,是寂寞', '654001666@qq.com');
INSERT INTO `ayong-chat`.user (id, phone, telephone, address, enabled, username, password, avatar, remark, roleId, signature, email) VALUES ('4', '18568123377', '029-82111333', '广州天河', 1, 'liuzongyuan', 'e10adc3949ba59abbe56e057f20f883e', 'http://img1.baidu.com/it/u=2915295560,2583690955&fm=253&app=138&f=JPEG?w=800&h=800', null, 1, '哥抽到不是烟,是寂寞', '154008888@qq.com');
INSERT INTO `ayong-chat`.user (id, phone, telephone, address, enabled, username, password, avatar, remark, roleId, signature, email) VALUES ('1860690520689827840', null, null, null, null, 'ayong', '81dc9bdb52d04dc20036dbd8313ed055', 'http://img1.baidu.com/it/u=2915295560,2583690955&fm=253&app=138&f=JPEG?w=800&h=800', null, null, null, '1540018883@qq.com');

