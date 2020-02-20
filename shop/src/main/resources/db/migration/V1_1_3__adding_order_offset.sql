alter table order_ add order_timestamp integer after created_at;

update order_ set order_timestamp = -120;