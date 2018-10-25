select * from profile;

select * from FIELD_PERMISSIONS;

select distinct name from FIELD_PERMISSIONS order by name asc;

select p.name profile, f.field field, f.readable readable, f.editable editable from profile p, field_permissions f where p.name = f.name group by p.name, f.field, f.readable, f.editable order by p.name asc;

select * from field_permissions f left join profile p on f.name = p.name;

