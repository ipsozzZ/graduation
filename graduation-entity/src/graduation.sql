use graduation;

create table album
(
    album_id    bigint auto_increment
        primary key,
    album_name  varchar(500)     not null comment '用户名',
    user_id     bigint           not null comment '相册所属用户',
    album_intro varchar(2000)    null comment '相册分类描述',
    album_type  tinyint          not null comment '图片是否对外显示，0：不显示；1：显示',
    del_flag    int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark      varchar(2000)    null comment '摘要',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间'
)
    comment '相册表' charset = utf8;

create table article
(
    article_id    bigint auto_increment
        primary key,
    article_name  varchar(500)     not null comment '标题',
    article_cover varchar(1000)    not null comment '文章封面',
    cate_id       bigint           not null comment '文章分类id',
    user_id       bigint           not null comment '文章发布者',
    `like`        int(8) default 0 null comment '文章点赞数',
    content       text             null comment '文章内容',
    del_flag      int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark        varchar(2000)    null comment '摘要',
    create_time   datetime         null comment '创建时间',
    update_time   datetime         null comment '更新时间',
    comment       int(8) default 0 null
)
    comment '文章表' charset = utf8;

create table cate
(
    cate_id     bigint auto_increment
        primary key,
    cate_name   varchar(500)     not null comment '分类名',
    user_id     bigint           not null comment '分类所属用户',
    dict_id     bigint           null comment '字典表id',
    del_flag    int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark      varchar(2000)    null comment '摘要',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间'
)
    comment '分类表，根据字典表为提供cate_id的表分类' charset = utf8;

create table comment
(
    comment_id   bigint auto_increment
        primary key,
    comment_name varchar(500)     not null comment '用户名',
    user_id      bigint           not null comment '评论用户',
    level        int(8) default 0 null comment '评论分级，0：主评或者留言；1：子评论或者留言评论',
    `like`       int(8) default 0 null comment '评论点赞数',
    parent_id    bigint           null comment '父评论id或者留言id',
    article_id   bigint           null comment '文章id',
    content      text             null comment '评论内容',
    del_flag     int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark       varchar(2000)    null comment '摘要',
    create_time  datetime         null comment '创建时间',
    update_time  datetime         null comment '更新时间'
)
    comment '评论表' charset = utf8;

create table contact
(
    contact_id   bigint auto_increment
        primary key,
    contact_name varchar(500)     not null comment '备注名',
    relationship varchar(500)     not null comment '人物关系',
    cate_id      bigint           not null comment '分类id',
    phone        varchar(50)      null comment '联系人号码',
    email        varchar(1000)    null comment '联系人邮件',
    front        varchar(1000)    null comment '联系人头像',
    home         varchar(1000)    null comment '联系人个人主页',
    user_id      bigint           not null comment '通讯录所属用户',
    del_flag     int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark       varchar(2000)    null comment '摘要',
    create_time  datetime         null comment '创建时间',
    update_time  datetime         null comment '更新时间'
)
    comment '联系人表' charset = utf8;

create table dict
(
    dict_id     bigint auto_increment
        primary key,
    dict_name   varchar(100)     not null comment '需要分类得功能，管理员管理',
    del_flag    int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark      varchar(2000)    null comment '摘要',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间',
    constraint dict_name
        unique (dict_name)
)
    comment '字典表' charset = utf8;

create table link
(
    link_id     bigint auto_increment
        primary key,
    link_name   varchar(500)     not null comment '备注',
    address     varchar(1000)    not null comment '链接地址',
    user_id     bigint           not null comment '模板所属用户',
    is_show     int(8) default 0 null comment '是否对外显示，0：不显示；1显示',
    type        int(8)           not null comment '链接类型，0：标签；1：友情链接',
    del_flag    int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark      varchar(2000)    null comment '摘要',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间'
)
    comment '友情链接和标签表' charset = utf8;

create table new_service
(
    new_id      bigint auto_increment
        primary key,
    new_name    varchar(500)     not null comment '新服务标题',
    state       int(8)           not null comment '链接类型，0：标签；1：友情链接',
    `like`      int(8) default 0 null comment '赞',
    unlike      int(8) default 0 null comment '踩',
    content     text             null comment '服务详情',
    del_flag    int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark      varchar(2000)    null comment '摘要',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间'
)
    comment '扩展功能表' charset = utf8;

create table notice
(
    notice_id   bigint auto_increment
        primary key,
    notice_name varchar(500)     not null comment '通知标题',
    content     varchar(5000)    not null comment '通知内容',
    user_id     bigint           not null comment '所属用户',
    del_flag    int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark      varchar(2000)    null comment '摘要',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间'
)
    comment '通知表' charset = utf8;

create table picture
(
    picture_id   bigint auto_increment
        primary key,
    picture_name varchar(500)      not null comment '用户名',
    album_id     bigint            not null comment '相册id',
    is_show      tinyint default 1 null comment '图片是否显示，0：不显示；1：显示',
    picture      varchar(1000)     null comment '图片地址',
    intro        varchar(1000)     null comment '图片简介',
    del_flag     int(8)  default 1 null comment '用于进行数据逻辑删除',
    remark       varchar(2000)     null comment '摘要',
    create_time  datetime          null comment '创建时间',
    update_time  datetime          null comment '更新时间'
)
    comment '相册图片表' charset = utf8;

create table template
(
    template_id   bigint auto_increment
        primary key,
    template_name varchar(500)     not null comment '标题',
    cate_id       bigint           not null comment '分类id',
    user_id       bigint           not null comment '模板所属用户',
    `like`        int(8) default 0 null comment '模板点赞数',
    is_show       int(8) default 0 null comment '是否对外显示，0：不显示；1显示',
    content       text             null comment '模板内容',
    del_flag      int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark        varchar(2000)    null comment '摘要',
    create_time   datetime         null comment '创建时间',
    update_time   datetime         null comment '更新时间'
)
    comment '模板表' charset = utf8;

create table user
(
    user_id     bigint auto_increment
        primary key,
    user_name   varchar(500)              not null comment '用户名',
    user_phone  varchar(50)               null comment '用户手机',
    user_email  varchar(1000)             null comment '用户邮箱',
    user_front  varchar(1000) default '#' null comment '用户头像',
    intro       varchar(2000)             null comment '用户简介',
    user_type   tinyint                   not null comment '用户类型，0：管理员，1：服务消费者',
    salt        varchar(32)               null comment '盐',
    user_home   varchar(1000)             null comment '用户个人主页连接',
    del_flag    int(8)        default 1   null comment '用于进行数据逻辑删除',
    remark      varchar(2000)             null comment '摘要',
    create_time datetime                  null comment '创建时间',
    update_time datetime                  null comment '更新时间',
    constraint user_phone
        unique (user_phone)
)
    comment '用户表' charset = utf8;

use graduation;

create table album
(
    album_id    bigint auto_increment
        primary key,
    album_name  varchar(500)     not null comment '用户名',
    user_id     bigint           not null comment '相册所属用户',
    album_intro varchar(2000)    null comment '相册分类描述',
    album_type  tinyint          not null comment '图片是否对外显示，0：不显示；1：显示',
    del_flag    int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark      varchar(2000)    null comment '摘要',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间'
)
    comment '相册表' charset = utf8;

create table article
(
    article_id    bigint auto_increment
        primary key,
    article_name  varchar(500)     not null comment '标题',
    article_cover varchar(1000)    not null comment '文章封面',
    cate_id       bigint           not null comment '文章分类id',
    user_id       bigint           not null comment '文章发布者',
    `like`        int(8) default 0 null comment '文章点赞数',
    content       text             null comment '文章内容',
    del_flag      int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark        varchar(2000)    null comment '摘要',
    create_time   datetime         null comment '创建时间',
    update_time   datetime         null comment '更新时间',
    comment       int(8) default 0 null
)
    comment '文章表' charset = utf8;

create table cate
(
    cate_id     bigint auto_increment
        primary key,
    cate_name   varchar(500)     not null comment '分类名',
    user_id     bigint           not null comment '分类所属用户',
    dict_id     bigint           null comment '字典表id',
    del_flag    int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark      varchar(2000)    null comment '摘要',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间'
)
    comment '分类表，根据字典表为提供cate_id的表分类' charset = utf8;

create table comment
(
    comment_id   bigint auto_increment
        primary key,
    comment_name varchar(500)     not null comment '用户名',
    user_id      bigint           not null comment '评论用户',
    level        int(8) default 0 null comment '评论分级，0：主评或者留言；1：子评论或者留言评论',
    `like`       int(8) default 0 null comment '评论点赞数',
    parent_id    bigint           null comment '父评论id或者留言id',
    article_id   bigint           null comment '文章id',
    content      text             null comment '评论内容',
    del_flag     int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark       varchar(2000)    null comment '摘要',
    create_time  datetime         null comment '创建时间',
    update_time  datetime         null comment '更新时间'
)
    comment '评论表' charset = utf8;

create table contact
(
    contact_id   bigint auto_increment
        primary key,
    contact_name varchar(500)     not null comment '备注名',
    relationship varchar(500)     not null comment '人物关系',
    cate_id      bigint           not null comment '分类id',
    phone        varchar(50)      null comment '联系人号码',
    email        varchar(1000)    null comment '联系人邮件',
    front        varchar(1000)    null comment '联系人头像',
    home         varchar(1000)    null comment '联系人个人主页',
    user_id      bigint           not null comment '通讯录所属用户',
    del_flag     int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark       varchar(2000)    null comment '摘要',
    create_time  datetime         null comment '创建时间',
    update_time  datetime         null comment '更新时间'
)
    comment '联系人表' charset = utf8;

create table dict
(
    dict_id     bigint auto_increment
        primary key,
    dict_name   varchar(100)     not null comment '需要分类得功能，管理员管理',
    del_flag    int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark      varchar(2000)    null comment '摘要',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间',
    constraint dict_name
        unique (dict_name)
)
    comment '字典表' charset = utf8;

create table link
(
    link_id     bigint auto_increment
        primary key,
    link_name   varchar(500)     not null comment '备注',
    address     varchar(1000)    not null comment '链接地址',
    user_id     bigint           not null comment '模板所属用户',
    is_show     int(8) default 0 null comment '是否对外显示，0：不显示；1显示',
    type        int(8)           not null comment '链接类型，0：标签；1：友情链接',
    del_flag    int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark      varchar(2000)    null comment '摘要',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间'
)
    comment '友情链接和标签表' charset = utf8;

create table new_service
(
    new_id      bigint auto_increment
        primary key,
    new_name    varchar(500)     not null comment '新服务标题',
    state       int(8)           not null comment '链接类型，0：标签；1：友情链接',
    `like`      int(8) default 0 null comment '赞',
    unlike      int(8) default 0 null comment '踩',
    content     text             null comment '服务详情',
    del_flag    int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark      varchar(2000)    null comment '摘要',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间'
)
    comment '扩展功能表' charset = utf8;

create table notice
(
    notice_id   bigint auto_increment
        primary key,
    notice_name varchar(500)     not null comment '通知标题',
    content     varchar(5000)    not null comment '通知内容',
    user_id     bigint           not null comment '所属用户',
    del_flag    int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark      varchar(2000)    null comment '摘要',
    create_time datetime         null comment '创建时间',
    update_time datetime         null comment '更新时间'
)
    comment '通知表' charset = utf8;

create table picture
(
    picture_id   bigint auto_increment
        primary key,
    picture_name varchar(500)      not null comment '用户名',
    album_id     bigint            not null comment '相册id',
    is_show      tinyint default 1 null comment '图片是否显示，0：不显示；1：显示',
    picture      varchar(1000)     null comment '图片地址',
    intro        varchar(1000)     null comment '图片简介',
    del_flag     int(8)  default 1 null comment '用于进行数据逻辑删除',
    remark       varchar(2000)     null comment '摘要',
    create_time  datetime          null comment '创建时间',
    update_time  datetime          null comment '更新时间'
)
    comment '相册图片表' charset = utf8;

create table template
(
    template_id   bigint auto_increment
        primary key,
    template_name varchar(500)     not null comment '标题',
    cate_id       bigint           not null comment '分类id',
    user_id       bigint           not null comment '模板所属用户',
    `like`        int(8) default 0 null comment '模板点赞数',
    is_show       int(8) default 0 null comment '是否对外显示，0：不显示；1显示',
    content       text             null comment '模板内容',
    del_flag      int(8) default 1 null comment '用于进行数据逻辑删除，0：记录已进行逻辑删除；1：记录存在数据库',
    remark        varchar(2000)    null comment '摘要',
    create_time   datetime         null comment '创建时间',
    update_time   datetime         null comment '更新时间'
)
    comment '模板表' charset = utf8;

create table user
(
    user_id     bigint auto_increment
        primary key,
    user_name   varchar(500)              not null comment '用户名',
    user_phone  varchar(50)               null comment '用户手机',
    user_email  varchar(1000)             null comment '用户邮箱',
    user_front  varchar(1000) default '#' null comment '用户头像',
    intro       varchar(2000)             null comment '用户简介',
    user_type   tinyint                   not null comment '用户类型，0：管理员，1：服务消费者',
    salt        varchar(32)               null comment '盐',
    user_home   varchar(1000)             null comment '用户个人主页连接',
    del_flag    int(8)        default 1   null comment '用于进行数据逻辑删除',
    remark      varchar(2000)             null comment '摘要',
    create_time datetime                  null comment '创建时间',
    update_time datetime                  null comment '更新时间',
    constraint user_phone
        unique (user_phone)
)
    comment '用户表' charset = utf8;

