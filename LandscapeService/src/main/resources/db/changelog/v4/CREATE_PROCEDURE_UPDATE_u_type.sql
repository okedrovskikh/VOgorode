create or replace procedure all_rows_update_u_type(new_u_type user_types, portion bigint, timestep integer)
    language plpgsql as
'
    declare
        f_start   bigint := 1;
        size      bigint := nextval(pg_get_serial_sequence(''users'', ''id''));
        loop_size bigint;
    begin
        for j in 1..ceil(size::float8 / portion)
            loop
                loop_size = least(size - f_start, portion);
                for i in 0..loop_size
                    loop
                        update users set u_type = new_u_type where id = i + f_start;
                    end loop;
                raise notice ''% - Committed % rows. Migration paused for % sec.'', current_timestamp, loop_size, timestep;
                f_start = f_start + loop_size + 1;
                if f_start >= size then
                    return ;
                end if;
                perform pg_sleep(timestep);
            end loop;
    end;
';
