CREATE DATABASE JavaDB
GO
USE JavaDB
GO

CREATE TABLE Article
(
	IDArticle INT PRIMARY KEY IDENTITY,
	Title NVARCHAR(300),
	Actor NVARCHAR(300),
	Director NVARCHAR(900),
	PicturePath NVARCHAR(90),
	Genre NVARCHAR(90)
)
GO

CREATE PROCEDURE createArticle
	@Title NVARCHAR(300),
	@Actor NVARCHAR(300),
	@Director NVARCHAR(900),
	@PicturePath NVARCHAR(90),
	@Genre NVARCHAR(90),
	@Id INT OUTPUT
AS 
BEGIN 
	INSERT INTO Article VALUES(@Title, @Actor, @Director, @PicturePath, @Genre)
	SET @Id = SCOPE_IDENTITY()
END
GO

CREATE PROCEDURE updateArticle
	@Title NVARCHAR(300),
	@Actor NVARCHAR(300),
	@Director NVARCHAR(900),
	@PicturePath NVARCHAR(90),
	@Genre NVARCHAR(90),
	@IdArticle INT
	 
AS 
BEGIN 
	UPDATE Article SET 
		Title = @Title,
		Actor = @Actor,
		Director = @Director,
		PicturePath = @PicturePath,
		Genre = @Genre		
	WHERE 
		IDArticle = @IdArticle
END
GO


CREATE PROCEDURE deleteArticle
	@IdArticle INT	 
AS 
BEGIN 
	DELETE  
	FROM 
			Article
	WHERE 
		IDArticle = @IdArticle
END
GO

CREATE PROCEDURE selectArticle
	@IdArticle INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Article
	WHERE 
		IDArticle = @IdArticle
END
GO

CREATE PROCEDURE selectArticles
AS 
BEGIN 
	SELECT * FROM Article
END
GO


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[IDUser] [int] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](50) NOT NULL,
	[Password] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IDUser] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

insert into [User](Username,[Password])values('admin','admin')

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[loginUser]
@user nvarchar(50),
@pass nvarchar(50)
As
begin
select * from [User] as u where u.[Password] = @pass and u.[Username]=@user
end
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[registerUser]
@u nvarchar(50),
@p nvarchar(50)
As
begin
insert into [User](Username,[Password])values(@u,@p)
end
GO