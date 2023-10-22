SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

create table store_website
(
    website_id       smallint unsigned auto_increment comment 'Website ID'
        primary key,
    code             varchar(32)                   null comment 'Code',
    name             varchar(64)                   null comment 'Website Name',
    sort_order       smallint unsigned default '0' not null comment 'Sort Order',
    default_group_id smallint unsigned default '0' not null comment 'Default Group ID',
    is_default       smallint unsigned default '0' null comment 'Defines Is Website Default',
    constraint STORE_WEBSITE_CODE
        unique (code)
)
    comment 'Websites' engine = InnoDB;

create index STORE_WEBSITE_DEFAULT_GROUP_ID
    on store_website (default_group_id);

create index STORE_WEBSITE_SORT_ORDER
    on store_website (sort_order);


create table customer_entity
(
    entity_id                 int unsigned auto_increment comment 'Entity ID'
        primary key,
    website_id                smallint unsigned                           null comment 'Website ID',
    email                     varchar(255)                                null comment 'Email',
    group_id                  smallint unsigned default '0'               not null comment 'Group ID',
    increment_id              varchar(50)                                 null comment 'Increment ID',
    store_id                  smallint unsigned default '0'               null comment 'Store ID',
    created_at                timestamp         default CURRENT_TIMESTAMP not null comment 'Created At',
    updated_at                timestamp         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'Updated At',
    is_active                 smallint unsigned default '1'               not null comment 'Is Active',
    disable_auto_group_change smallint unsigned default '0'               not null comment 'Disable automatic group change based on VAT ID',
    created_in                varchar(255)                                null comment 'Created From',
    prefix                    varchar(40)                                 null comment 'Name Prefix',
    firstname                 varchar(255)                                null comment 'First Name',
    middlename                varchar(255)                                null comment 'Middle Name/Initial',
    lastname                  varchar(255)                                null comment 'Last Name',
    suffix                    varchar(40)                                 null comment 'Name Suffix',
    dob                       date                                        null comment 'Date of Birth',
    password_hash             varchar(128)                                null comment 'Password_hash',
    rp_token                  varchar(128)                                null comment 'Reset password token',
    rp_token_created_at       datetime                                    null comment 'Reset password token creation time',
    default_billing           int unsigned                                null comment 'Default Billing Address',
    default_shipping          int unsigned                                null comment 'Default Shipping Address',
    taxvat                    varchar(50)                                 null comment 'Tax/VAT Number',
    confirmation              varchar(64)                                 null comment 'Is Confirmed',
    gender                    smallint unsigned                           null comment 'Gender',
    failures_num              smallint          default 0                 null comment 'Failure Number',
    first_failure             timestamp                                   null comment 'First Failure',
    lock_expires              timestamp                                   null comment 'Lock Expiration Date',
    ncli                      int unsigned                                null comment 'NCLI',
    session_cutoff            timestamp                                   null comment 'Session Cutoff Time',
    constraint CUSTOMER_ENTITY_EMAIL_WEBSITE_ID
        unique (email, website_id),
    constraint CUSTOMER_ENTITY_WEBSITE_ID_STORE_WEBSITE_WEBSITE_ID
        foreign key (website_id) references store_website (website_id)
            on delete set null
)
    comment 'Customer Entity' engine = InnoDB;

create index CUSTOMER_ENTITY_FIRSTNAME
    on customer_entity (firstname);

create index CUSTOMER_ENTITY_LASTNAME
    on customer_entity (lastname);

create index CUSTOMER_ENTITY_STORE_ID
    on customer_entity (store_id);

create index CUSTOMER_ENTITY_WEBSITE_ID
    on customer_entity (website_id);

INSERT INTO store_website (website_id, code, name, sort_order, default_group_id, is_default) VALUES (0, 'admin', 'Admin', 0, 0, 0);
INSERT INTO store_website (website_id, code, name, sort_order, default_group_id, is_default) VALUES (1, 'base', 'My e-commerce', 1, 1, 1);
INSERT INTO store_website (website_id, code, name, sort_order, default_group_id, is_default) VALUES (2, 'base2', 'My e-commerce2', 2, 1, 0);

INSERT INTO customer_entity (entity_id, website_id, email, group_id, increment_id, store_id, created_at, updated_at, is_active, disable_auto_group_change, created_in, prefix, firstname, middlename, lastname, suffix, dob, password_hash, rp_token, rp_token_created_at, default_billing, default_shipping, taxvat, confirmation, gender, failures_num, first_failure, lock_expires, ncli, session_cutoff)
VALUES (null, 1, 'good@example.com', 1, null, 1, '2016-06-01 08:21:39', '2020-08-01 03:19:43', 1, 0, 'English', null, 'First', null, 'Last', null, null, 'aae4663c41443e9b674805af07da1c6f247341b267935221c3d8f809d6e03d54:JhPKQs5x4uEC5GS4:2', null, null, null, null, null, null, null, 0, null, null, 0, null);


