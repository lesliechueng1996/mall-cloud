-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
CREATE TABLE "user".t_menu
(
    id serial NOT NULL,
    parent_id integer,
    create_time timestamp without time zone NOT NULL,
    title character varying(100) NOT NULL,
    level integer NOT NULL,
    sort integer NOT NULL,
    name character varying(100) NOT NULL,
    icon character varying(200),
    hidden boolean NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE "user".t_menu
    IS '后台菜单表';

COMMENT ON COLUMN "user".t_menu.parent_id
    IS '父级ID';

COMMENT ON COLUMN "user".t_menu.create_time
    IS '创建时间';

COMMENT ON COLUMN "user".t_menu.title
    IS '菜单名称';

COMMENT ON COLUMN "user".t_menu.level
    IS '菜单级数';

COMMENT ON COLUMN "user".t_menu.sort
    IS '菜单排序';

COMMENT ON COLUMN "user".t_menu.name
    IS '前端名称';

COMMENT ON COLUMN "user".t_menu.icon
    IS '前端图标';

COMMENT ON COLUMN "user".t_menu.hidden
    IS '前端隐藏';


-- ----------------------------
-- Table structure for t_resource_category
-- ----------------------------
CREATE TABLE "user".t_resource_category
(
    id serial NOT NULL,
    create_time timestamp without time zone NOT NULL,
    name character varying(200) NOT NULL,
    sort integer NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE "user".t_resource_category
    IS '资源分类表';

COMMENT ON COLUMN "user".t_resource_category.create_time
    IS '创建时间';

COMMENT ON COLUMN "user".t_resource_category.name
    IS '分类名称';

COMMENT ON COLUMN "user".t_resource_category.sort
    IS '排序';


-- ----------------------------
-- Table structure for t_resource
-- ----------------------------
CREATE TABLE "user".t_resource
(
    id serial NOT NULL,
    create_time timestamp without time zone NOT NULL,
    name character varying(200) NOT NULL,
    url character varying(200) NOT NULL,
    description text,
    category_id integer NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE "user".t_resource
    IS '后台资源表';

COMMENT ON COLUMN "user".t_resource.create_time
    IS '创建时间';

COMMENT ON COLUMN "user".t_resource.name
    IS '资源名称';

COMMENT ON COLUMN "user".t_resource.url
    IS '资源URL';

COMMENT ON COLUMN "user".t_resource.description
    IS '描述';

COMMENT ON COLUMN "user".t_resource.category_id
    IS '资源分类ID';

CREATE INDEX t_resource_category_id_index
    ON "user".t_resource USING btree
    (category_id ASC NULLS LAST)
;

COMMENT ON INDEX "user".t_resource_category_id_index
    IS '资源分类ID索引';



-- ----------------------------
-- Table structure for t_role
-- ----------------------------
CREATE TABLE "user".t_role
(
    id serial NOT NULL,
    create_time timestamp without time zone NOT NULL,
    name character varying(100) NOT NULL,
    description text,
    admin_count integer NOT NULL,
    status integer NOT NULL DEFAULT 1,
    sort integer NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE "user".t_role
    IS '后台用户角色表';

COMMENT ON COLUMN "user".t_role.create_time
    IS '创建时间';

COMMENT ON COLUMN "user".t_role.name
    IS '名称';

COMMENT ON COLUMN "user".t_role.description
    IS '描述';

COMMENT ON COLUMN "user".t_role.admin_count
    IS '后台用户数量';

COMMENT ON COLUMN "user".t_role.status
    IS '启用状态：0->禁用；1->启用';



-- ----------------------------
-- Table structure for t_role_menu_relation
-- ----------------------------
CREATE TABLE "user".t_role_menu_relation
(
    id serial NOT NULL,
    role_id integer NOT NULL,
    menu_id integer NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE "user".t_role_menu_relation
    IS '后台角色菜单关系表';

COMMENT ON COLUMN "user".t_role_menu_relation.role_id
    IS '角色ID';

COMMENT ON COLUMN "user".t_role_menu_relation.menu_id
    IS '菜单ID';

CREATE INDEX t_role_menu_relation_role_id_index
    ON "user".t_role_menu_relation USING btree
    (role_id ASC NULLS LAST)
;

CREATE INDEX t_role_menu_relation_menu_id_index
    ON "user".t_role_menu_relation USING btree
    (menu_id ASC NULLS LAST)
;



-- ----------------------------
-- Table structure for t_role_resource_relation
-- ----------------------------
CREATE TABLE "user".t_role_resource_relation
(
    id serial NOT NULL,
    role_id integer NOT NULL,
    resource_id integer NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE "user".t_role_resource_relation
    IS '后台角色资源关系表';

COMMENT ON COLUMN "user".t_role_resource_relation.role_id
    IS '角色ID';

COMMENT ON COLUMN "user".t_role_resource_relation.resource_id
    IS '资源ID';

CREATE INDEX t_role_resource_relation_role_id_index
    ON "user".t_role_resource_relation USING btree
    (role_id ASC NULLS LAST)
;

CREATE INDEX t_role_resource_relation_resource_id_index
    ON "user".t_role_resource_relation USING btree
    (resource_id ASC NULLS LAST)
;



-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
CREATE TABLE "user".t_admin
(
    id serial NOT NULL,
    create_time timestamp without time zone NOT NULL,
    username character varying(64) NOT NULL,
    password character varying(64) NOT NULL,
    icon text,
    email character varying(100),
    nick_name character varying(100),
    note text,
    login_time timestamp without time zone,
    status integer NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);

COMMENT ON TABLE "user".t_admin
    IS '后台用户表';

COMMENT ON COLUMN "user".t_admin.create_time
    IS '创建时间';

COMMENT ON COLUMN "user".t_admin.username
    IS '用户名';

COMMENT ON COLUMN "user".t_admin.password
    IS '密码';

COMMENT ON COLUMN "user".t_admin.icon
    IS '头像';

COMMENT ON COLUMN "user".t_admin.email
    IS '邮箱';

COMMENT ON COLUMN "user".t_admin.nick_name
    IS '昵称';

COMMENT ON COLUMN "user".t_admin.note
    IS '备注信息';

COMMENT ON COLUMN "user".t_admin.login_time
    IS '最后登录时间';

COMMENT ON COLUMN "user".t_admin.status
    IS '帐号启用状态：0->禁用；1->启用';

CREATE INDEX t_admin_username_index
    ON "user".t_admin USING btree
    (username ASC NULLS LAST)
;



-- ----------------------------
-- Table structure for t_admin_login_log
-- ----------------------------
CREATE TABLE "user".t_admin_login_log
(
    id serial NOT NULL,
    admin_id integer NOT NULL,
    create_time timestamp without time zone NOT NULL,
    ip character varying(200),
    address text,
    user_agent text,
    PRIMARY KEY (id)
);

COMMENT ON TABLE "user".t_admin_login_log
    IS '后台用户登录日志表';

COMMENT ON COLUMN "user".t_admin_login_log.admin_id
    IS '用户ID';

COMMENT ON COLUMN "user".t_admin_login_log.create_time
    IS '创建时间';

COMMENT ON COLUMN "user".t_admin_login_log.ip
    IS 'IP';

COMMENT ON COLUMN "user".t_admin_login_log.address
    IS '地址';

COMMENT ON COLUMN "user".t_admin_login_log.user_agent
    IS '浏览器类型';


-- ----------------------------
-- Table structure for t_admin_role_relation
-- ----------------------------
CREATE TABLE "user".t_admin_role_relation
(
    id serial NOT NULL,
    admin_id integer NOT NULL,
    role_id integer NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE "user".t_admin_role_relation
    IS '后台用户和角色关系表';

COMMENT ON COLUMN "user".t_admin_role_relation.admin_id
    IS '用户ID';

COMMENT ON COLUMN "user".t_admin_role_relation.role_id
    IS '角色ID';