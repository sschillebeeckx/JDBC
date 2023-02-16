create procedure get_persons_lastname @firstLetter nvarchar(2)
AS
BEGIN
     set nocount on;
        SELECT *
        FROM
            abispersons left outer join abiscompanies on pa_cono = cono
        WHERE plname like concat(@firstLetter,'%');
     return;
END;




