-- Tabela de Usuários
create table customers
(
    id            BIGINT AUTO_INCREMENT primary key,
    full_name     VARCHAR(255) not null,
    document      VARCHAR(50)  not null unique,
    email         VARCHAR(255) not null unique,
    customer_type VARCHAR(20)  not null,
    created_at    TIMESTAMP    not null default current_timestamp,
    updated_at    TIMESTAMP    not null default current_timestamp on update current_timestamp,

    constraint chk_user_type check (customer_type in ('COMMON', 'MERCHANT'))
);

create table wallets
(
    customer_id      BIGINT AUTO_INCREMENT primary key,
    balance_in_cents BIGINT    not null default 0,
    created_at       TIMESTAMP not null default current_timestamp,
    updated_at       TIMESTAMP not null default current_timestamp on update current_timestamp,

    constraint fk_wallet_customer foreign key (customer_id) references customers (id) on delete cascade,
    constraint chk_balance check (balance_in_cents >= 0)
);