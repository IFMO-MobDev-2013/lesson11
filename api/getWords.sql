create proc getWords @cat varchar(MAX)
as
Begin
declare @id int
select @id= c_id from [ermakov].[categories] where [c_en]=@cat
SELECT *
FROM     ermakov.categories INNER JOIN
                  ermakov.words ON ermakov.categories.c_id = ermakov.words.c_id
WHERE  (ermakov.categories.c_id = @id)
end