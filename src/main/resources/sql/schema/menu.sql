DROP TABLE IF EXISTS "menu";
CREATE TABLE "menu"
(
    "id"   INT(0)       NOT NULL AUTO_INCREMENT COMMENT '菜单唯一标识',
    "pid"  INT(0)       NOT NULL COMMENT '父菜单唯一标识',
    "name" VARCHAR(200) NOT NULL COMMENT '菜单名称',
    PRIMARY KEY ("id")
);

INSERT INTO "menu"
VALUES (1, 0, '系统权限菜单');
INSERT INTO "menu"
VALUES (2, 1, '控制面板');
INSERT INTO "menu"
VALUES (3, 1, '权限管理');
INSERT INTO "menu"
VALUES (4, 3, '用户维护');
INSERT INTO "menu"
VALUES (5, 3, '角色维护');
INSERT INTO "menu"
VALUES (6, 3, '菜单维护');
INSERT INTO "menu"
VALUES (7, 1, '业务审核');
INSERT INTO "menu"
VALUES (8, 7, '实名认证审核');
INSERT INTO "menu"
VALUES (9, 7, '广告审核');
INSERT INTO "menu"
VALUES (10, 7, '项目审核');
INSERT INTO "menu"
VALUES (11, 1, '业务管理');
INSERT INTO "menu"
VALUES (12, 11, '资质维护');
INSERT INTO "menu"
VALUES (13, 11, '分类管理');
INSERT INTO "menu"
VALUES (14, 11, '流程管理');
INSERT INTO "menu"
VALUES (15, 11, '广告管理');
INSERT INTO "menu"
VALUES (16, 11, '消息模板');
INSERT INTO "menu"
VALUES (17, 11, '项目分类');
INSERT INTO "menu"
VALUES (18, 11, '项目标签');
INSERT INTO "menu"
VALUES (19, 1, '参数管理');
