USE [master]
GO
/****** Object:  Database [Codecademy]    Script Date: 16/01/2022 22:29:18 ******/
CREATE DATABASE [Codecademy]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Codecademy', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLDEV2019\MSSQL\DATA\Codecademy.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Codecademy_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLDEV2019\MSSQL\DATA\Codecademy_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
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
/****** Object:  Table [dbo].[Address]    Script Date: 16/01/2022 22:29:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Address](
	[AddressID] [int] IDENTITY(1,1) NOT NULL,
	[Street] [nvarchar](255) NOT NULL,
	[HouseNumber] [int] NOT NULL,
	[CityID] [int] NOT NULL,
	[PostalCode] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_Address] PRIMARY KEY CLUSTERED 
(
	[AddressID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Certificate]    Script Date: 16/01/2022 22:29:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Certificate](
	[CertificateID] [int] IDENTITY(1,1) NOT NULL,
	[EnrollmentID] [int] NOT NULL,
	[Grade] [int] NOT NULL,
	[EmployeeName] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_Certificate] PRIMARY KEY CLUSTERED 
(
	[CertificateID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[City]    Script Date: 16/01/2022 22:29:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[City](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[City] [nvarchar](225) NOT NULL,
	[Country] [nvarchar](225) NOT NULL,
 CONSTRAINT [PK_CityCountry] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Contact]    Script Date: 16/01/2022 22:29:18 ******/
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
/****** Object:  Table [dbo].[ContentItem]    Script Date: 16/01/2022 22:29:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ContentItem](
	[ContentID] [int] IDENTITY(1,1) NOT NULL,
	[Title] [nvarchar](255) NOT NULL,
	[Description] [nvarchar](1024) NOT NULL,
	[CreationDate] [date] NOT NULL,
	[Status] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_ContentItem] PRIMARY KEY CLUSTERED 
(
	[ContentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Course]    Script Date: 16/01/2022 22:29:18 ******/
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
/****** Object:  Table [dbo].[CourseRecommendation]    Script Date: 16/01/2022 22:29:18 ******/
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
/****** Object:  Table [dbo].[Enrollment]    Script Date: 16/01/2022 22:29:18 ******/
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
/****** Object:  Table [dbo].[Module]    Script Date: 16/01/2022 22:29:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Module](
	[ContentID] [int] NOT NULL,
	[CourseName] [nvarchar](225) NULL,
	[Version] [int] NOT NULL,
	[PositionInCourse] [int] NOT NULL,
	[ContactEmail] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_Module] PRIMARY KEY CLUSTERED 
(
	[ContentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Progress]    Script Date: 16/01/2022 22:29:18 ******/
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
/****** Object:  Table [dbo].[Speaker]    Script Date: 16/01/2022 22:29:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Speaker](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](225) NOT NULL,
	[OrganizationName] [nvarchar](225) NOT NULL,
 CONSTRAINT [PK_Speaker_1] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Student]    Script Date: 16/01/2022 22:29:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Student](
	[Email] [nvarchar](255) NOT NULL,
	[Name] [nvarchar](255) NOT NULL,
	[Birthdate] [date] NOT NULL,
	[Gender] [char](1) NOT NULL,
	[AddressID] [int] NOT NULL,
 CONSTRAINT [PK_Student] PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Webcast]    Script Date: 16/01/2022 22:29:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Webcast](
	[ContentID] [int] NOT NULL,
	[URL] [nvarchar](450) NOT NULL,
	[Duration] [int] NOT NULL,
	[Views] [int] NOT NULL,
	[SpeakerID] [int] NULL,
 CONSTRAINT [PK_Webcast] PRIMARY KEY CLUSTERED 
(
	[ContentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Address] ON 
GO
INSERT [dbo].[Address] ([AddressID], [Street], [HouseNumber], [CityID], [PostalCode]) VALUES (10, N'''s-Gravenhaagse Bos', 10, 7, N'2594 BD')
GO
INSERT [dbo].[Address] ([AddressID], [Street], [HouseNumber], [CityID], [PostalCode]) VALUES (11, N'Nieuwezijds Voorburgwal', 147, 8, N'1012 RJ')
GO
INSERT [dbo].[Address] ([AddressID], [Street], [HouseNumber], [CityID], [PostalCode]) VALUES (13, N'Lovensdijkstraat', 61, 9, N'4818 AJ')
GO
INSERT [dbo].[Address] ([AddressID], [Street], [HouseNumber], [CityID], [PostalCode]) VALUES (14, N'Museumstraat', 1, 8, N'1071 XX')
GO
INSERT [dbo].[Address] ([AddressID], [Street], [HouseNumber], [CityID], [PostalCode]) VALUES (15, N'Singel', 9, 10, N'8321 GT')
GO
INSERT [dbo].[Address] ([AddressID], [Street], [HouseNumber], [CityID], [PostalCode]) VALUES (16, N'Domplein', 21, 11, N'3512 JC')
GO
INSERT [dbo].[Address] ([AddressID], [Street], [HouseNumber], [CityID], [PostalCode]) VALUES (17, N'Overblaak', 70, 12, N'3011 MH')
GO
INSERT [dbo].[Address] ([AddressID], [Street], [HouseNumber], [CityID], [PostalCode]) VALUES (18, N'Provincialeweg', 102, 13, N'1506 MD')
GO
INSERT [dbo].[Address] ([AddressID], [Street], [HouseNumber], [CityID], [PostalCode]) VALUES (19, N'Prins Hendriklaan', 50, 11, N'3583 EP')
GO
SET IDENTITY_INSERT [dbo].[Address] OFF
GO
SET IDENTITY_INSERT [dbo].[Certificate] ON 
GO
INSERT [dbo].[Certificate] ([CertificateID], [EnrollmentID], [Grade], [EmployeeName]) VALUES (9, 17, 8, N'Kees Flodder')
GO
INSERT [dbo].[Certificate] ([CertificateID], [EnrollmentID], [Grade], [EmployeeName]) VALUES (10, 19, 10, N'Melle Smit')
GO
INSERT [dbo].[Certificate] ([CertificateID], [EnrollmentID], [Grade], [EmployeeName]) VALUES (11, 18, 10, N'Melle Smit')
GO
INSERT [dbo].[Certificate] ([CertificateID], [EnrollmentID], [Grade], [EmployeeName]) VALUES (12, 20, 10, N'Kees Flodder')
GO
INSERT [dbo].[Certificate] ([CertificateID], [EnrollmentID], [Grade], [EmployeeName]) VALUES (14, 23, 7, N'Melle Smit')
GO
INSERT [dbo].[Certificate] ([CertificateID], [EnrollmentID], [Grade], [EmployeeName]) VALUES (15, 24, 8, N'Kees Flodder')
GO
INSERT [dbo].[Certificate] ([CertificateID], [EnrollmentID], [Grade], [EmployeeName]) VALUES (16, 25, 7, N'Melle Smit')
GO
INSERT [dbo].[Certificate] ([CertificateID], [EnrollmentID], [Grade], [EmployeeName]) VALUES (17, 26, 9, N'Kees Flodder')
GO
SET IDENTITY_INSERT [dbo].[Certificate] OFF
GO
SET IDENTITY_INSERT [dbo].[City] ON 
GO
INSERT [dbo].[City] ([ID], [City], [Country]) VALUES (8, N'Amsterdam', N'Netherlands')
GO
INSERT [dbo].[City] ([ID], [City], [Country]) VALUES (9, N'Breda', N'Netherlands')
GO
INSERT [dbo].[City] ([ID], [City], [Country]) VALUES (12, N'Rotterdam', N'Netherlands')
GO
INSERT [dbo].[City] ([ID], [City], [Country]) VALUES (7, N'The Hague', N'Netherlands')
GO
INSERT [dbo].[City] ([ID], [City], [Country]) VALUES (10, N'Urk', N'Netherlands')
GO
INSERT [dbo].[City] ([ID], [City], [Country]) VALUES (11, N'Utrecht', N'Netherlands')
GO
INSERT [dbo].[City] ([ID], [City], [Country]) VALUES (13, N'Zaandam', N'Netherlands')
GO
SET IDENTITY_INSERT [dbo].[City] OFF
GO
INSERT [dbo].[Contact] ([Email], [Name]) VALUES (N'a.devries@gmail.com', N'Alex de Vries')
GO
INSERT [dbo].[Contact] ([Email], [Name]) VALUES (N'k.flodder@yahoo.nl', N'Kees Flodder')
GO
INSERT [dbo].[Contact] ([Email], [Name]) VALUES (N'm.smit@gmail.com', N'Melle Smit')
GO
SET IDENTITY_INSERT [dbo].[ContentItem] ON 
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (1, N'Getting Started', N'Getting started with objec oriented programming', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (2, N'Variables', N'An introduction to variables', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (3, N'Classes', N'An introduction to classes', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (4, N'Conditional statements', N'An introduction to conditional statements', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (5, N'Loops', N'An introduction to loops', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (6, N'Java Programming basics', N'A webcast explaining the basics of programming in Java dzlfskhj', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (7, N'HashMaps', N'An introduction to HashMaps', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (8, N'Unit Testing', N'An introduction to unit testing', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (9, N'Introduction Databases', N'An introduction to relational databases', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (10, N'Subqueries', N'An introduction to subqueries', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (11, N'Agregate Functions', N'An introduction to agregate functions', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (12, N'Views', N'An introduction to views', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (13, N'Foreign Keys', N'An introduction to foreign keys', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (14, N'SQL Constraints', N'An introduction to SQL constraints', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (15, N'Building a Database', N'A module teaching you how to build a simple database', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (16, N'Test', N'A test to check your knowledge', CAST(N'2022-01-15' AS Date), N'ACTIVE')
GO
INSERT [dbo].[ContentItem] ([ContentID], [Title], [Description], [CreationDate], [Status]) VALUES (21, N'SQL Basics', N'Basics of SQL', CAST(N'2022-01-16' AS Date), N'ACTIVE')
GO
SET IDENTITY_INSERT [dbo].[ContentItem] OFF
GO
INSERT [dbo].[Course] ([CourseName], [Subject], [Description], [Difficulty]) VALUES (N'Object Oriented Programming with Java part 1', N'Object oriented programming in Java', N'An introduction to object oriented programming in Java', N'EASY')
GO
INSERT [dbo].[Course] ([CourseName], [Subject], [Description], [Difficulty]) VALUES (N'Object Oriented Programming with Java part 2', N'Object oriented programming with Java', N'A continuation of Object Oriented Programming with Java part 1', N'MEDIUM')
GO
INSERT [dbo].[Course] ([CourseName], [Subject], [Description], [Difficulty]) VALUES (N'Relational Databases SQL', N'Relational Databases', N'A course teaching the basics of Relational Databases using SQL Server Management Studio', N'EASY')
GO
INSERT [dbo].[CourseRecommendation] ([CourseName], [RecommendedCourse]) VALUES (N'Object Oriented Programming with Java part 1', N'Object Oriented Programming with Java part 2')
GO
SET IDENTITY_INSERT [dbo].[Enrollment] ON 
GO
INSERT [dbo].[Enrollment] ([ID], [Enrolldate], [Email], [CourseName]) VALUES (24, CAST(N'2022-01-16' AS Date), N'e.jansen@gmail.com', N'Relational Databases SQL')
GO
INSERT [dbo].[Enrollment] ([ID], [Enrolldate], [Email], [CourseName]) VALUES (18, CAST(N'2022-01-16' AS Date), N'h.degraver@mm.com', N'Object Oriented Programming with Java part 1')
GO
INSERT [dbo].[Enrollment] ([ID], [Enrolldate], [Email], [CourseName]) VALUES (19, CAST(N'2022-01-16' AS Date), N'h.degraver@mm.com', N'Object Oriented Programming with Java part 2')
GO
INSERT [dbo].[Enrollment] ([ID], [Enrolldate], [Email], [CourseName]) VALUES (20, CAST(N'2022-01-16' AS Date), N'h.degraver@mm.com', N'Relational Databases SQL')
GO
INSERT [dbo].[Enrollment] ([ID], [Enrolldate], [Email], [CourseName]) VALUES (26, CAST(N'2022-01-16' AS Date), N'j.bos@gmail.com', N'Relational Databases SQL')
GO
INSERT [dbo].[Enrollment] ([ID], [Enrolldate], [Email], [CourseName]) VALUES (23, CAST(N'2022-01-16' AS Date), N'n.dejong@gmail.com', N'Object Oriented Programming with Java part 1')
GO
INSERT [dbo].[Enrollment] ([ID], [Enrolldate], [Email], [CourseName]) VALUES (16, CAST(N'2021-06-06' AS Date), N'o.denbeste@gmail.com', N'Relational Databases SQL')
GO
INSERT [dbo].[Enrollment] ([ID], [Enrolldate], [Email], [CourseName]) VALUES (17, CAST(N'2021-07-07' AS Date), N'o.denbeste@gmail.com', N'Relational Databases SQL')
GO
INSERT [dbo].[Enrollment] ([ID], [Enrolldate], [Email], [CourseName]) VALUES (25, CAST(N'2022-01-16' AS Date), N'r.vandenberg@gmail.com', N'Object Oriented Programming with Java part 1')
GO
INSERT [dbo].[Enrollment] ([ID], [Enrolldate], [Email], [CourseName]) VALUES (27, CAST(N'2022-01-16' AS Date), N's.vandijk@gmail.com', N'Object Oriented Programming with Java part 1')
GO
INSERT [dbo].[Enrollment] ([ID], [Enrolldate], [Email], [CourseName]) VALUES (15, CAST(N'2021-11-21' AS Date), N'we.vanburen@gmail.com', N'Object Oriented Programming with Java part 1')
GO
SET IDENTITY_INSERT [dbo].[Enrollment] OFF
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (1, N'Object Oriented Programming with Java part 1', 1, 1, N'm.smit@gmail.com')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (2, N'Object Oriented Programming with Java part 1', 1, 2, N'm.smit@gmail.com')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (3, N'Object Oriented Programming with Java part 1', 1, 3, N'm.smit@gmail.com')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (4, N'Object Oriented Programming with Java part 1', 1, 4, N'm.smit@gmail.com')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (5, N'Object Oriented Programming with Java part 1', 1, 5, N'm.smit@gmail.com')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (7, N'Object Oriented Programming with Java part 2', 1, 1, N'a.devries@gmail.com')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (8, N'Object Oriented Programming with Java part 2', 1, 2, N'a.devries@gmail.com')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (9, N'Relational Databases SQL', 1, 1, N'k.flodder@yahoo.nl')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (10, N'Relational Databases SQL', 1, 2, N'k.flodder@yahoo.nl')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (11, N'Relational Databases SQL', 1, 3, N'k.flodder@yahoo.nl')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (12, N'Relational Databases SQL', 1, 4, N'k.flodder@yahoo.nl')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (13, N'Relational Databases SQL', 1, 5, N'k.flodder@yahoo.nl')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (14, N'Relational Databases SQL', 1, 6, N'k.flodder@yahoo.nl')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (15, N'Relational Databases SQL', 1, 7, N'k.flodder@yahoo.nl')
GO
INSERT [dbo].[Module] ([ContentID], [CourseName], [Version], [PositionInCourse], [ContactEmail]) VALUES (16, N'Relational Databases SQL', 1, 8, N'k.flodder@yahoo.nl')
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'e.jansen@gmail.com', 9, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'e.jansen@gmail.com', 10, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'e.jansen@gmail.com', 11, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'e.jansen@gmail.com', 12, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'e.jansen@gmail.com', 13, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'e.jansen@gmail.com', 14, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'e.jansen@gmail.com', 15, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'e.jansen@gmail.com', 16, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 1, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 2, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 3, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 4, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 5, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 6, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 7, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 8, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 9, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 10, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 11, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 12, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 13, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 14, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 15, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'h.degraver@mm.com', 16, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'j.bos@gmail.com', 9, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'j.bos@gmail.com', 10, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'j.bos@gmail.com', 11, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'j.bos@gmail.com', 12, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'j.bos@gmail.com', 13, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'j.bos@gmail.com', 14, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'j.bos@gmail.com', 15, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'j.bos@gmail.com', 16, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'n.dejong@gmail.com', 1, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'n.dejong@gmail.com', 2, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'n.dejong@gmail.com', 3, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'n.dejong@gmail.com', 4, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'n.dejong@gmail.com', 5, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'o.denbeste@gmail.com', 9, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'o.denbeste@gmail.com', 10, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'o.denbeste@gmail.com', 11, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'o.denbeste@gmail.com', 12, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'o.denbeste@gmail.com', 13, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'o.denbeste@gmail.com', 14, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'o.denbeste@gmail.com', 15, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'o.denbeste@gmail.com', 16, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'r.vandenberg@gmail.com', 1, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'r.vandenberg@gmail.com', 2, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'r.vandenberg@gmail.com', 3, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'r.vandenberg@gmail.com', 4, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'r.vandenberg@gmail.com', 5, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N's.vandijk@gmail.com', 1, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N's.vandijk@gmail.com', 2, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N's.vandijk@gmail.com', 3, 32)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N's.vandijk@gmail.com', 4, 58)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N's.vandijk@gmail.com', 5, 0)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'we.vanburen@gmail.com', 1, 100)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'we.vanburen@gmail.com', 2, 50)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'we.vanburen@gmail.com', 3, 0)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'we.vanburen@gmail.com', 4, 0)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'we.vanburen@gmail.com', 5, 0)
GO
INSERT [dbo].[Progress] ([StudentEmail], [ContentID], [Percentage]) VALUES (N'we.vanburen@gmail.com', 6, 100)
GO
SET IDENTITY_INSERT [dbo].[Speaker] ON 
GO
INSERT [dbo].[Speaker] ([ID], [Name], [OrganizationName]) VALUES (4, N'Kees Flodder', N'Codecademy')
GO
INSERT [dbo].[Speaker] ([ID], [Name], [OrganizationName]) VALUES (1, N'Melle Smit', N'Codecademy')
GO
SET IDENTITY_INSERT [dbo].[Speaker] OFF
GO
INSERT [dbo].[Student] ([Email], [Name], [Birthdate], [Gender], [AddressID]) VALUES (N'e.jansen@gmail.com', N'Emma Jansen', CAST(N'1999-02-05' AS Date), N'F', 15)
GO
INSERT [dbo].[Student] ([Email], [Name], [Birthdate], [Gender], [AddressID]) VALUES (N'h.degraver@mm.com', N'Henk de Graver', CAST(N'1924-02-29' AS Date), N'M', 13)
GO
INSERT [dbo].[Student] ([Email], [Name], [Birthdate], [Gender], [AddressID]) VALUES (N'j.bos@gmail.com', N'Jos Bos', CAST(N'1975-12-31' AS Date), N'O', 18)
GO
INSERT [dbo].[Student] ([Email], [Name], [Birthdate], [Gender], [AddressID]) VALUES (N'j.devries@gmail.com', N'Julia de Vries', CAST(N'1985-01-08' AS Date), N'F', 16)
GO
INSERT [dbo].[Student] ([Email], [Name], [Birthdate], [Gender], [AddressID]) VALUES (N'n.dejong@gmail.com', N'Noah de Jong', CAST(N'1990-07-25' AS Date), N'O', 14)
GO
INSERT [dbo].[Student] ([Email], [Name], [Birthdate], [Gender], [AddressID]) VALUES (N'o.denbeste@gmail.com', N'Ottelien den Beste', CAST(N'2000-01-01' AS Date), N'F', 11)
GO
INSERT [dbo].[Student] ([Email], [Name], [Birthdate], [Gender], [AddressID]) VALUES (N'r.vandenberg@gmail.com', N'Robin van den Berg', CAST(N'2001-03-05' AS Date), N'O', 17)
GO
INSERT [dbo].[Student] ([Email], [Name], [Birthdate], [Gender], [AddressID]) VALUES (N's.vandijk@gmail.com', N'Sam van Dijk', CAST(N'1993-04-09' AS Date), N'O', 19)
GO
INSERT [dbo].[Student] ([Email], [Name], [Birthdate], [Gender], [AddressID]) VALUES (N'we.vanburen@gmail.com', N'W.A. van Buren', CAST(N'1967-04-27' AS Date), N'M', 10)
GO
INSERT [dbo].[Webcast] ([ContentID], [URL], [Duration], [Views], [SpeakerID]) VALUES (6, N'https://www.codecademy.com/webcasts/javabasic', 120, 2500, 1)
GO
INSERT [dbo].[Webcast] ([ContentID], [URL], [Duration], [Views], [SpeakerID]) VALUES (21, N'https://www.codecademy.com/webcasts/SQLbasics', 120, 25000, 4)
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [AK_City_Country]    Script Date: 16/01/2022 22:29:18 ******/
ALTER TABLE [dbo].[City] ADD  CONSTRAINT [AK_City_Country] UNIQUE NONCLUSTERED 
(
	[City] ASC,
	[Country] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_CourseRecommendation]    Script Date: 16/01/2022 22:29:18 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_CourseRecommendation] ON [dbo].[CourseRecommendation]
(
	[CourseName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [AK_EmailPlusCoursePlusDate]    Script Date: 16/01/2022 22:29:18 ******/
ALTER TABLE [dbo].[Enrollment] ADD  CONSTRAINT [AK_EmailPlusCoursePlusDate] UNIQUE NONCLUSTERED 
(
	[Email] ASC,
	[CourseName] ASC,
	[Enrolldate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_Enrollment]    Script Date: 16/01/2022 22:29:18 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_Enrollment] ON [dbo].[Enrollment]
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Speaker__3C2511680C306AAB]    Script Date: 16/01/2022 22:29:18 ******/
ALTER TABLE [dbo].[Speaker] ADD UNIQUE NONCLUSTERED 
(
	[Name] ASC,
	[OrganizationName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [AK_URL]    Script Date: 16/01/2022 22:29:18 ******/
ALTER TABLE [dbo].[Webcast] ADD  CONSTRAINT [AK_URL] UNIQUE NONCLUSTERED 
(
	[URL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Progress] ADD  CONSTRAINT [DF_Progress_Percentage]  DEFAULT ((0)) FOR [Percentage]
GO
ALTER TABLE [dbo].[Address]  WITH CHECK ADD  CONSTRAINT [FK_Address_City] FOREIGN KEY([CityID])
REFERENCES [dbo].[City] ([ID])
GO
ALTER TABLE [dbo].[Address] CHECK CONSTRAINT [FK_Address_City]
GO
ALTER TABLE [dbo].[Certificate]  WITH CHECK ADD  CONSTRAINT [FK_Certificate_Enrollment] FOREIGN KEY([EnrollmentID])
REFERENCES [dbo].[Enrollment] ([ID])
ON DELETE CASCADE
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
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Student]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_Contact] FOREIGN KEY([ContactEmail])
REFERENCES [dbo].[Contact] ([Email])
ON UPDATE CASCADE
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
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_Course]
GO
ALTER TABLE [dbo].[Progress]  WITH CHECK ADD  CONSTRAINT [FK_Progress_ContentItem] FOREIGN KEY([ContentID])
REFERENCES [dbo].[ContentItem] ([ContentID])
GO
ALTER TABLE [dbo].[Progress] CHECK CONSTRAINT [FK_Progress_ContentItem]
GO
ALTER TABLE [dbo].[Progress]  WITH CHECK ADD  CONSTRAINT [FK_Progress_Student] FOREIGN KEY([StudentEmail])
REFERENCES [dbo].[Student] ([Email])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Progress] CHECK CONSTRAINT [FK_Progress_Student]
GO
ALTER TABLE [dbo].[Student]  WITH CHECK ADD  CONSTRAINT [FK_Student_Address] FOREIGN KEY([AddressID])
REFERENCES [dbo].[Address] ([AddressID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Student] CHECK CONSTRAINT [FK_Student_Address]
GO
ALTER TABLE [dbo].[Webcast]  WITH CHECK ADD  CONSTRAINT [FK_Webcast_ContentItem] FOREIGN KEY([ContentID])
REFERENCES [dbo].[ContentItem] ([ContentID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Webcast] CHECK CONSTRAINT [FK_Webcast_ContentItem]
GO
ALTER TABLE [dbo].[Webcast]  WITH CHECK ADD  CONSTRAINT [FK_Webcast_Speaker] FOREIGN KEY([SpeakerID])
REFERENCES [dbo].[Speaker] ([ID])
GO
ALTER TABLE [dbo].[Webcast] CHECK CONSTRAINT [FK_Webcast_Speaker]
GO
USE [master]
GO
ALTER DATABASE [Codecademy] SET  READ_WRITE 
GO
