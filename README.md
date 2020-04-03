create database timecoder;

select *
from timecoder.episode e join timecoder.theme t on t.episode_id = e.id
order by episode_id, `timestamp` desc

select *
from
episode

select *
from theme

select *
from hibernate_sequence

insert into episode (
    id,
    is_started,
    name)
values (
    56,
    false,
    "Привет");

CREATE DATABASE timecoder CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


Local run with Jacoco 

java -javaagent:jib/jacoco/jacocoagent.jar=port=36321,output=tcpserver,address=0.0.0.0 -jar build/libs/timecoder-api-service-1.4.0.jar