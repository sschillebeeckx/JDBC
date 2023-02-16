create or replace procedure get_persons_lastname(firstLetter IN char, person_list out sys_refcursor)
AS
BEGIN
    open person_list for
        SELECT *
        FROM
            abispersons left outer join abiscompanies on pa_cono = cono
        WHERE plname like concat(firstLetter,'%');

END;
/

create or replace procedure update_course_duration(course_id in number, newdur in number)
AS
begin
update ABISCOURSES
set cdur=newdur
where cid=course_id;
end;
/

create or replace procedure nrOf_persons_company(company_name in char, resultval out number)
AS
begin
    select count(*)
    into resultval
    from abispersons inner join abiscompanies on pa_cono=cono
    where upper(coname) like '%' ||upper(trim(company_name)) || '%';
end;
/

