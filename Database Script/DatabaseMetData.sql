USE [master]
GO
/****** Object:  Database [Codecademy]    Script Date: 28/12/2021 15:20:40 ******/
CREATE DATABASE [Codecademy]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'CodeCademy', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLDEV2019\MSSQL\DATA\CodeCademy.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'CodeCademy_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLDEV2019\MSSQL\DATA\CodeCademy_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Codecademy] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Codecademy].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Codecademy] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Codecademy] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Codecademy] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Codecademy] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Codecademy] SET ARITHABORT OFF 
GO
ALTER DATABASE [Codecademy] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Codecademy] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Codecademy] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Codecademy] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Codecademy] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Codecademy] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Codecademy] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Codecademy] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Codecademy] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Codecademy] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Codecademy] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Codecademy] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Codecademy] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Codecademy] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Codecademy] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Codecademy] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Codecademy] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Codecademy] SET RECOVERY FULL 
GO
ALTER DATABASE [Codecademy] SET  MULTI_USER 
GO
ALTER DATABASE [Codecademy] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Codecademy] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Codecademy] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Codecademy] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Codecademy] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Codecademy] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'Codecademy', N'ON'
GO
ALTER DATABASE [Codecademy] SET QUERY_STORE = OFF
GO
USE [Codecademy]
GO
/****** Object:  Table [dbo].[Certificate]    Script Date: 28/12/2021 15:20:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Certificate](
	[CertificateID] [int] NOT NULL,
	[EnrollmentID] [int] NOT NULL,
	[Grade] [decimal](3, 1) NOT NULL,
	[EmployeeName] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_Certificate] PRIMARY KEY CLUSTERED 
(
	[CertificateID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Contact]    Script Date: 28/12/2021 15:20:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Contact](
	[Email] [nvarchar](255) NOT NULL,
	[Name] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_Contact] PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ContentItem]    Script Date: 28/12/2021 15:20:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ContentItem](
	[ContentID] [int] IDENTITY(1,1) NOT NULL,
	[CreationDate] [date] NOT NULL,
	[Description] [nvarchar](1024) NOT NULL,
	[Status] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_ContentItem] PRIMARY KEY CLUSTERED 
(
	[ContentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Course]    Script Date: 28/12/2021 15:20:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Course](
	[CourseName] [nvarchar](225) NOT NULL,
	[Subject] [nvarchar](255) NOT NULL,
	[Description] [nvarchar](1024) NULL, 
	[Difficulty] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_Course] PRIMARY KEY CLUSTERED 
(
	[CourseName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CourseRecommendation]    Script Date: 28/12/2021 15:20:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CourseRecommendation](
	[CourseName] [nvarchar](225) NOT NULL,
	[RecommendedCourse] [nvarchar](225) NOT NULL,
 CONSTRAINT [PK_CourseRecommendation] PRIMARY KEY CLUSTERED 
(
	[CourseName] ASC,
	[RecommendedCourse] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Enrollment]    Script Date: 28/12/2021 15:20:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Enrollment](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Enrolldate] [date] NOT NULL,
	[Email] [nvarchar](255) NOT NULL,
	[CourseName] [nvarchar](225) NOT NULL,
 CONSTRAINT [PK_Enrollment] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Module]    Script Date: 28/12/2021 15:20:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Module](
	[ContentID] [int] NOT NULL,
	[CourseName] [nvarchar](225) NULL,
	[Title] [nvarchar](225) NOT NULL,
	[Version] [int] NOT NULL,
	[PositionInCourse] [int] NOT NULL,
	[ContactEmail] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_Module] PRIMARY KEY CLUSTERED 
(
	[ContentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Organization]    Script Date: 28/12/2021 15:20:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Organization](
	[Name] [nvarchar](225) NOT NULL,
 CONSTRAINT [PK_Organization_1] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Progress]    Script Date: 28/12/2021 15:20:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Progress](
	[StudentEmail] [nvarchar](255) NOT NULL,
	[ContentID] [int] NOT NULL,
	[Percentage] [int] NOT NULL,
 CONSTRAINT [PK_Progress] PRIMARY KEY CLUSTERED 
(
	[StudentEmail] ASC,
	[ContentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Speaker]    Script Date: 28/12/2021 15:20:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Speaker](
	[Name] [nvarchar](225) NOT NULL,
	[OrganizationName] [nvarchar](225) NOT NULL,
 CONSTRAINT [PK_Speaker] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Student]    Script Date: 28/12/2021 15:20:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Student](
	[Email] [nvarchar](255) NOT NULL,
	[Name] [nvarchar](255) NOT NULL,
	[Birthdate] [date] NOT NULL,
	[Gender] [char](1) NOT NULL,
	[Address] [nvarchar](255) NOT NULL,
	[City] [nvarchar](255) NOT NULL,
	[Country] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_Student] PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Webcast]    Script Date: 28/12/2021 15:20:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Webcast](
	[ContentID] [int] NOT NULL,
	[Title] [nvarchar](255) NOT NULL,
	[URL] [nvarchar](450) NOT NULL,
	[Description] [nvarchar](1024) NOT NULL,
	[Duration] [int] NOT NULL,
	[SpeakerName] [nvarchar](225) NOT NULL,
 CONSTRAINT [PK_Webcast] PRIMARY KEY CLUSTERED 
(
	[ContentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Contact] ([Email], [Name]) VALUES (N'eduardrozeboom@teleworm.us', N'Eduard Rozeboom')
GO
INSERT [dbo].[Contact] ([Email], [Name]) VALUES (N'henk@politiekatwijk.com', N'Henk Boema')
GO
INSERT [dbo].[Contact] ([Email], [Name]) VALUES (N'herwinsaalmink@rhyta.com', N'Herwin Saalmink')
GO
SET IDENTITY_INSERT [dbo].[ContentItem] ON 
GO
INSERT [dbo].[ContentItem] ([ContentID], [CreationDate], [Description], [Status]) VALUES (2, CAST(N'2020-12-19' AS Date), N'Hello', N'CONCEPT')
GO
INSERT [dbo].[ContentItem] ([ContentID], [CreationDate], [Description], [Status]) VALUES (4, CAST(N'2020-12-31' AS Date), N'A course teaching you how to download more RAM', N'CONCEPT')
GO
INSERT [dbo].[ContentItem] ([ContentID], [CreationDate], [Description], [Status]) VALUES (5, CAST(N'2021-06-03' AS Date), N'system.out.println("Hello, World");', N'ARCHIVED')
GO
INSERT [dbo].[ContentItem] ([ContentID], [CreationDate], [Description], [Status]) VALUES (7, CAST(N'2004-07-29' AS Date), N'An introduction to JavaScript', N'ARCHIVED')
GO
INSERT [dbo].[ContentItem] ([ContentID], [CreationDate], [Description], [Status]) VALUES (8, CAST(N'2021-01-31' AS Date), N'Printing Hello World in Assembly Language', N'ACTIVE')
GO
SET IDENTITY_INSERT [dbo].[ContentItem] OFF
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Title], [Version], [PositionInCourse], [ContactEmail]) VALUES (2, NULL, N'JAVA SQL Connection', 1, 1, N'eduardrozeboom@teleworm.us')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Title], [Version], [PositionInCourse], [ContactEmail]) VALUES (4, NULL, N'How to Download more RAM', 4, 20, N'herwinsaalmink@rhyta.com')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Title], [Version], [PositionInCourse], [ContactEmail]) VALUES (5, NULL, N'Hello World in Java', 13, 13, N'eduardrozeboom@teleworm.us')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Title], [Version], [PositionInCourse], [ContactEmail]) VALUES (7, NULL, N'Intro JavaScript', 2, 5, N'henk@politiekatwijk.com')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Title], [Version], [PositionInCourse], [ContactEmail]) VALUES (8, NULL, N'Hello World in Assembly', 6, 242, N'herwinsaalmink@rhyta.com')
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_CourseRecommendation]    Script Date: 28/12/2021 15:20:40 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_CourseRecommendation] ON [dbo].[CourseRecommendation]
(
	[CourseName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_Enrollment]    Script Date: 28/12/2021 15:20:40 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_Enrollment] ON [dbo].[Enrollment]
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [AK_Title_Version]    Script Date: 28/12/2021 15:20:40 ******/
ALTER TABLE [dbo].[Module] ADD  CONSTRAINT [AK_Title_Version] UNIQUE NONCLUSTERED 
(
	[Title] ASC,
	[Version] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [AK_Title]    Script Date: 28/12/2021 15:20:40 ******/
ALTER TABLE [dbo].[Webcast] ADD  CONSTRAINT [AK_Title] UNIQUE NONCLUSTERED 
(
	[Title] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [AK_URL]    Script Date: 28/12/2021 15:20:40 ******/
ALTER TABLE [dbo].[Webcast] ADD  CONSTRAINT [AK_URL] UNIQUE NONCLUSTERED 
(
	[URL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Certificate]  WITH CHECK ADD  CONSTRAINT [FK_Certificate_Enrollment] FOREIGN KEY([EnrollmentID])
REFERENCES [dbo].[Enrollment] ([ID])
GO
ALTER TABLE [dbo].[Certificate] CHECK CONSTRAINT [FK_Certificate_Enrollment]
GO
ALTER TABLE [dbo].[CourseRecommendation]  WITH CHECK ADD  CONSTRAINT [FK_CourseRecommendation_Course] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([CourseName])
GO
ALTER TABLE [dbo].[CourseRecommendation] CHECK CONSTRAINT [FK_CourseRecommendation_Course]
GO
ALTER TABLE [dbo].[CourseRecommendation]  WITH CHECK ADD  CONSTRAINT [FK_CourseRecommendation_Course1] FOREIGN KEY([RecommendedCourse])
REFERENCES [dbo].[Course] ([CourseName])
GO
ALTER TABLE [dbo].[CourseRecommendation] CHECK CONSTRAINT [FK_CourseRecommendation_Course1]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Course] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([CourseName])
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Course]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Student] FOREIGN KEY([Email])
REFERENCES [dbo].[Student] ([Email])
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Student]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_Contact] FOREIGN KEY([ContactEmail])
REFERENCES [dbo].[Contact] ([Email])
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_Contact]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_ContentItem] FOREIGN KEY([ContentID])
REFERENCES [dbo].[ContentItem] ([ContentID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_ContentItem]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_Course] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([CourseName])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[Progress]  WITH CHECK ADD  CONSTRAINT [FK_Progress_ContentItem] FOREIGN KEY([ContentID])
REFERENCES [dbo].[ContentItem] ([ContentID])
GO
ALTER TABLE [dbo].[Progress] CHECK CONSTRAINT [FK_Progress_ContentItem]
GO
ALTER TABLE [dbo].[Progress]  WITH CHECK ADD  CONSTRAINT [FK_Progress_Student] FOREIGN KEY([StudentEmail])
REFERENCES [dbo].[Student] ([Email])
GO
ALTER TABLE [dbo].[Progress] CHECK CONSTRAINT [FK_Progress_Student]
GO
ALTER TABLE [dbo].[Speaker]  WITH CHECK ADD  CONSTRAINT [FK_Speaker_Organization] FOREIGN KEY([OrganizationName])
REFERENCES [dbo].[Organization] ([Name])
GO
ALTER TABLE [dbo].[Speaker] CHECK CONSTRAINT [FK_Speaker_Organization]
GO
ALTER TABLE [dbo].[Webcast]  WITH CHECK ADD  CONSTRAINT [FK_Webcast_ContentItem] FOREIGN KEY([ContentID])
REFERENCES [dbo].[ContentItem] ([ContentID])
GO
ALTER TABLE [dbo].[Webcast] CHECK CONSTRAINT [FK_Webcast_ContentItem]
GO
ALTER TABLE [dbo].[Webcast]  WITH CHECK ADD  CONSTRAINT [FK_Webcast_Speaker] FOREIGN KEY([SpeakerName])
REFERENCES [dbo].[Speaker] ([Name])
GO
ALTER TABLE [dbo].[Webcast] CHECK CONSTRAINT [FK_Webcast_Speaker]
GO
USE [master]
GO
ALTER DATABASE [Codecademy] SET  READ_WRITE 
GO